package com.data.dataproducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.data.dataproducer.proxy.jms.CouponUseMessageProxy;
import com.data.dataproducer.proxy.jms.UserOrderMessageProxy;
import com.data.dataproducer.config.DataCacheConfig;
import com.data.dataproducer.entity.*;
import com.data.dataproducer.entity.bo.OrderBO;
import com.data.dataproducer.enums.*;
import com.data.dataproducer.factory.ACouponFactory;
import com.data.dataproducer.mapper.AOrderMapper;
import com.data.dataproducer.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author danny
 * @since 2019-05-30
 */
@Service
public class AOrderServiceImpl extends ServiceImpl<AOrderMapper, AOrder> implements IAOrderService {

    @Autowired
    private DataCacheConfig dataShare;
    @Autowired
    private IAOrderService iaOrderService;
    @Autowired
    private IAOrderDetailService iaOrderDetailService;
    @Autowired
    private IAOrderPaymentService iaOrderPaymentService;
    @Autowired
    private IACouponDetailService iaCouponDetailService;
    @Autowired
    private IACouponUsageService iaCouponUsageService;
    @Autowired
    private IACouponService iaCouponService;
    @Autowired
    private ACouponFactory aCouponFactory;
    @Autowired
    private IAOrderRefundService iaOrderRefundService;

    @Autowired
    private UserOrderMessageProxy userOrderMessage;

    @Autowired
    private CouponUseMessageProxy couponUseMessage;

    @Value("${data.producer.order.refund.days.max}")
    private int refundMaxDays;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void order(OrderBO order) {
        //保存订单
        this.matchAndUseCoupon(order.getOrder());
        iaOrderService.save(order.getOrder());
        Long maxOrderId = dataShare.getMaxOrderId();
        if (order.getOrder().getOrderId() > maxOrderId) {
            dataShare.setMaxOrderId(order.getOrder().getOrderId());
        }

        //订单明细关联订单
        for (AOrderDetail orderDetail : order.getOrderDetails()) {
            orderDetail.setOrderId(order.getOrder().getOrderId());
        }

        //保存订单明细
        iaOrderDetailService.saveBatch(order.getOrderDetails());

        //通知
        userOrderMessage.orderAdvice(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean pay(AOrder order, AOrderPayment payment) {
        boolean payOkay = false;
        //订单存在，且未支付
        if (null != order && !order.getIsPayed().equals(BooleanEnum.TRUE.value())) {
            //如果支付成功，则修改支付状态
            if (payment.getStatus().equals(StoreAuditEnum.SUCCESS.value()) &&
                    iaOrderPaymentService.save(payment)) {
                //支付状态改为true
                order.setIsPayed(BooleanEnum.TRUE.value());

                //订单状态改为已支付
                order.setStatus(OrderStatusEnum.PAYED.value());
                order.setUpdateTime(LocalDateTime.now());
                order.setActualPayAmount(payment.getPayAmount());
                payOkay = iaOrderService.updateById(order);

                //通知
                userOrderMessage.payAdvice(order, payment);
            }
        }

        return payOkay;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean refund(AOrderPayment payment, AOrderRefund refund) {
        AOrder order = iaOrderService.getById(payment.getOrderId());

        //检查是否在可退款时间范围内
        LocalDateTime tmpDate = LocalDateTime.now().plusDays(0 - refundMaxDays);
        boolean inRefundTime = tmpDate.isBefore(payment.getPaymentTime());

        //订单存在，且已支付，未退款
        if (inRefundTime && null != order
                && order.getIsPayed().equals(BooleanEnum.TRUE.value())
                && order.getStatus().equals(OrderStatusEnum.PAYED.value())) {
            //保存退款单
            iaOrderRefundService.save(refund);

            //变更退款状态
            order.setStatus(OrderStatusEnum.REFUNDED.value());
            iaOrderService.updateById(order);

            //通知
            userOrderMessage.refundAdvice(order, payment, refund);
        }
        return false;
    }


    /**
     * 使用优惠券
     */
    public void matchAndUseCoupon (AOrder order) {
        QueryWrapper<ACouponDetail> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", order.getUserId());
        queryWrapper.eq("status", CouponStatusEnum.NOT_USED.value());
        List<ACouponDetail> couponDetails = iaCouponDetailService.list(queryWrapper);
        LocalDateTime now = LocalDateTime.now();
        for (ACouponDetail couponDetail : couponDetails) {
            ACoupon coupon = iaCouponService.getById(couponDetail.getCouponId());
            //优惠券在有效期内
            if (coupon.getStartTime().isBefore(now) && coupon.getEndTime().isAfter(now)) {
                //计算折扣和满减
                //折扣券
                if (coupon.getCouponType() == CouponTypeEnum.DISCOUNT.value()) {
                    BigDecimal dueAmount = order.getTotalAmount().multiply(coupon.getDiscount());
                    order.setDueAmount(dueAmount);
                    //计算折扣金额
                    order.setDiscountAmount(order.getTotalAmount().subtract(order.getDueAmount()));
                    //生成使用优惠券记录
                    ACouponUsage usedCoupon = aCouponFactory.produceUsedCoupon(order, couponDetail);
                    //保存入库
                    iaCouponUsageService.save(usedCoupon);

                    //修改优惠券使用状态
                    this.updateCouponUsed(couponDetail, now);

                    //通知
                    couponUseMessage.useCouponAdvice(order, couponDetail, usedCoupon);
                    break;
                }
                //满减券
                else if (coupon.getCouponType() == CouponTypeEnum.DEDUCTION.value()) {
                    if (order.getTotalAmount().compareTo(coupon.getDiscountSill()) == 1) {
                        BigDecimal dueAmount = order.getTotalAmount().subtract(coupon.getDiscountSill());
                        order.setDueAmount(dueAmount);
                        //计算折扣金额
                        order.setDiscountAmount(order.getTotalAmount().subtract(order.getDueAmount()));
                        //生成使用优惠券记录
                        ACouponUsage usedCoupon = aCouponFactory.produceUsedCoupon(order, couponDetail);
                        //保存入库
                        iaCouponUsageService.save(usedCoupon);
                        //修改优惠券使用状态
                        this.updateCouponUsed(couponDetail, now);

                        //通知
                        couponUseMessage.useCouponAdvice(order, couponDetail, usedCoupon);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 将优惠券变更为已使用状态
     * @param couponDetail
     */
    public void updateCouponUsed (ACouponDetail couponDetail, LocalDateTime usedTime) {
        couponDetail.setStatus(CouponStatusEnum.USED.value());
        couponDetail.setUsedTime(usedTime);
        iaCouponDetailService.updateById(couponDetail);
    }
}
