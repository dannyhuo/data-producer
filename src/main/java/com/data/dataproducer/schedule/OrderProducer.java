package com.data.dataproducer.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.data.dataproducer.config.DataCacheConfig;
import com.data.dataproducer.entity.*;
import com.data.dataproducer.enums.BooleanEnum;
import com.data.dataproducer.enums.OrderStatusEnum;
import com.data.dataproducer.enums.ProductStatusEnum;
import com.data.dataproducer.enums.StoreTypeEnum;
import com.data.dataproducer.factory.AOrderFactory;
import com.data.dataproducer.factory.RandomFactory;
import com.data.dataproducer.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author danny
 * @date 2019/5/30 7:25 PM
 */
@Slf4j
@Component
public class OrderProducer {

    @Autowired
    private AOrderFactory aOrderFactory;
    @Autowired
    private RandomFactory randomFactory;
    @Autowired
    private DataCacheConfig dataCache;
    @Autowired
    private IAUserService iaUserService;
    @Autowired
    private IAProductService iaProductService;
    @Autowired
    private IAOrderService iaOrderService;
    @Autowired
    private IAOrderPaymentService iaOrderPaymentService;
    @Autowired
    private IAStoreService iaStoreService;
    @Autowired
    private IAOrderRefundService iaOrderRefundService;

    @Autowired
    private Map<Integer, Integer> hour24Max;

    /**
     * 未立即支付概率
     */
    @Value("${data.producer.order.not-immediate-pay.denominator}")
    private int notImmediatePayDenominator;



    /**
     * 系统定时自动下单
     */
    @Scheduled(fixedRate = 1000 * 10)
    public void autoProduceOrder () {
        int hour = LocalDateTime.now().getHour();
        Integer maxOrderCount = hour24Max.get(hour);
        int orderCount = randomFactory.randomId(maxOrderCount == null ? 5 : maxOrderCount) + 1;
        for (int i = 0; i < orderCount; i++) {
            this.autoOrder();
        }
    }


    /**
     * 系统定时自动随机支付
     */
    @Scheduled(fixedRate = 1000 * 60 * 1)
    public void autoProduceOrderPayment () {
        int orderCount = randomFactory.randomId(123L) + 1;
        for (int i = 0; i < orderCount; i++) {
            long orderId = dataCache.getRdmOrderId();
            this.autoPayment(iaOrderService.getById(orderId));
        }
    }

    /**
     * 自动退单
     */
    @Scheduled(fixedRate = 1000 * 1)
    public void autoProduceOrderRefund () {
        int hour = LocalDateTime.now().getHour();
        Integer maxOrderCount = hour24Max.get(hour);
        int orderCount = randomFactory.randomId(maxOrderCount == null ? 1 : maxOrderCount) + 1;
        //自动退单的峰值，为订单的十分之一
        int refundCount = orderCount / 10 + 1;
        for (int i = 0; i < refundCount; i++) {
            int paymentId = randomFactory.randomId(dataCache.getMaxOrderPaymentId() - dataCache.getIdStart()) + dataCache.getIdStart().intValue();
            AOrderPayment payment = iaOrderPaymentService.getById(paymentId);
            if (null == payment) {
                //支付单不存在，跳过
                continue;
            }
            QueryWrapper<AOrderRefund> queryWrapper = new QueryWrapper();
            queryWrapper.eq("order_payment_id", payment.getOrderPaymentId());
            List<AOrderRefund> rfd = iaOrderRefundService.list(queryWrapper);
            if (null != rfd && rfd.size() > 0) {
                //已退单，跳过
                continue;
            }

            //生成退款单
            AOrderRefund refund = aOrderFactory.produceRefund(payment);

            //退款
            iaOrderService.refund(payment, refund);
        }
    }

    /**
     * 系统自动下单
     * 随机从库里选取用户，生成订单和订单明细
     */
    private void autoOrder () {
        //随机选取下单用户
        int userId = dataCache.getRdmUserId();
        AUser user = iaUserService.getById(userId);
        if (null == user) {
            return;
        }

        Map<Integer, List<AProduct>> products = new HashMap<>(10);
        int quantity = randomFactory.randomId(randomFactory.randomId(10)) + 1;
        for (int i = 0; i < quantity; i++) {
            AProduct product = iaProductService.getById(dataCache.getRdmProductId());
            //产品不为空，且为上架状态
            if (null != product && null != product.getStatus()
                    && product.getStatus().equals(ProductStatusEnum.PUT_AWAY.value())) {
                Integer storeId = product.getStoreId();
                List<AProduct> tmpList;
                if (null == (tmpList = products.get(storeId))) {
                    tmpList = new ArrayList<>();
                    products.put(storeId, tmpList);
                }
                tmpList.add(product);
            }
        }

        if (products.isEmpty()) {
            //如果没有产品，则退回
            return;
        }

        Iterator<Map.Entry<Integer, List<AProduct>>> iter = products.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, List<AProduct>> entity = iter.next();
            Integer storeId = entity.getKey();
            if (null == storeId) {
                //如果是自营或线下类的产品，随机生成一家店
                storeId = dataCache.getRdmStoreId();
            }
            AStore store = iaStoreService.getById(storeId);

            //生成订单和订单明细
            AutoOrder order = aOrderFactory.produceOrder(user, entity.getValue(), store);
            iaOrderService.order(order);

            //根据配置，下单立即支付概率，下单后立即支付订单
            boolean notImdPay = randomFactory.randomId(notImmediatePayDenominator) % notImmediatePayDenominator == 0;
            if (!notImdPay) {
                this.autoPayment(order.getOrder());
            }
        }
    }

    /**
     * 支付单
     * @param order
     * @return
     */
    public boolean autoPayment (AOrder order) {
        AOrderPayment payment = aOrderFactory.producePayment(order);
        boolean payOkay = iaOrderService.pay(order, payment);
        if (payOkay) {
            dataCache.setMaxOrderPaymentId(payment.getOrderPaymentId());
        }
        return payOkay;
    }
}
