package com.data.dataproducer.factory;

import com.data.dataproducer.entity.*;
import com.data.dataproducer.entity.bo.OrderBO;
import com.data.dataproducer.enums.BooleanEnum;
import com.data.dataproducer.enums.StoreAuditEnum;
import com.data.dataproducer.enums.RefundStatusEnum;
import com.data.dataproducer.enums.StoreTypeEnum;
import com.data.dataproducer.util.MD5Util;
import com.data.dataproducer.util.UCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author danny
 * @date 2019/5/31 9:51 AM
 */
@Slf4j
@Component
public class AOrderFactory {

    @Autowired
    private RandomFactory randomFactory;

    /**
     * 支付失败率分母配置
     */
    @Value("${data.producer.order.pay.failed.denominator}")
    private int payFailedDenominator;

    /**
     * 退款失败率分母配置
     */
    @Value("${data.producer.order.refund.failed.denominator}")
    private int refundFailedDenominator;

    /**
     * 最大支付的账号的数量
     */
    @Value("${data.producer.order.payment.card.count.max}")
    private int maxPayCartCount;

    /**
     * 每个产品的最大购买份数
     */
    @Value("${data.producer.order.per.product.quntity.max}")
    private int maxProductQuntity;

    /**
     * 生成订单数据
     * @param user
     * @param products
     * @return
     */
    public OrderBO produceOrder (AUser user, List<AProduct> products, AStore store) {
        //生成主订单
        AOrder.AOrderBuilder builder = AOrder.builder();
        builder.userId(user.getUserId());
        builder.contactMobile(randomFactory.randomMobile());
        builder.orderPlatform(randomFactory.randomPlatform());
        builder.contactName(randomFactory.randomFimilyAndName());
        //默认未支付
        builder.isPayed(BooleanEnum.FALSE.value());
        //设置门店
        if (null != store) {
            builder.storeId(store.getStoreId());
            if (null != store.getStoreType() &&
                    store.getStoreType() == StoreTypeEnum.OFF_LINE.value()) {
                String city = store.getCity() == null ? "" : store.getCity();
                builder.comment(store.getProvince() + city + store.getContry());
            } else {
                builder.comment(store.getStoreName());
            }
        }

        //订单明细
        List<AOrderDetail> details = new ArrayList<>();
        BigDecimal totalAmount = new BigDecimal(0);
        for (AProduct product : products) {
            AOrderDetail.AOrderDetailBuilder orderDetail = AOrderDetail.builder();
            orderDetail.productId(product.getProductId());
            orderDetail.price(product.getSellPrice());
            int quantity = randomFactory.randomId(randomFactory.randomId(maxProductQuntity)) + 1;
            orderDetail.quantity(quantity);
            BigDecimal amount = product.getSellPrice().multiply(BigDecimal.valueOf(quantity));
            orderDetail.totalAmount(amount);
            totalAmount = totalAmount.add(amount);
            details.add(orderDetail.build());
        }
        builder.totalAmount(totalAmount);

        OrderBO order = new OrderBO();
        order.setOrder(builder.build());
        order.setOrderDetails(details);

        return order;
    }


    /**
     * 生成支付单
     * @param order
     * @return
     */
    public AOrderPayment producePayment (AOrder order) {
        AOrderPayment.AOrderPaymentBuilder builder = AOrderPayment.builder();
        builder.orderId(order.getOrderId());
        // 支付=应付
        builder.payAmount(order.getDueAmount());
        //每用户默认有${配置}张卡，随机使用某张卡
        String cardNo = order.getUserId().toString() + randomFactory.randomId(maxPayCartCount);
        builder.payCardNo(MD5Util.md5(cardNo));
        builder.payStyle(randomFactory.randomPayStyle());
        builder.payNo(UCodeUtil.produce());
        //随机设置${配置}分之一支付失败率
        boolean failed = randomFactory.randomId(payFailedDenominator) % payFailedDenominator == 0;
        if (failed) {
            builder.status(StoreAuditEnum.FAILED.value());
        } else {
            builder.status(StoreAuditEnum.SUCCESS.value());
        }

        return builder.build();
    }


    /**
     * 生成退款单
     * @param payment
     * @return
     */
    public AOrderRefund produceRefund (AOrderPayment payment) {
        AOrderRefund.AOrderRefundBuilder builder = AOrderRefund.builder();
        builder.orderPaymentId(payment.getOrderPaymentId());
        builder.refundCardNo(payment.getPayCardNo());
        //默认随机设置${配置}分之一退款失败率
        boolean failed = randomFactory.randomId(refundFailedDenominator) % refundFailedDenominator == 0;
        if (failed) {
            builder.status(RefundStatusEnum.FAILED.value());
        } else {
            builder.status(RefundStatusEnum.SUCCESS.value());
        }
        builder.refundAmount(payment.getPayAmount());
        return builder.build();
    }

}
