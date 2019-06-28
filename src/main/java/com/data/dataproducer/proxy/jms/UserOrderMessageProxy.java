package com.data.dataproducer.proxy.jms;

import com.data.dataproducer.entity.AOrder;
import com.data.dataproducer.entity.AOrderPayment;
import com.data.dataproducer.entity.AOrderRefund;
import com.data.dataproducer.entity.bo.OrderBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 订单消息通知
 *
 * @author danny
 *
 * @date 2019/6/28 4:48 PM
 *
 */
@Slf4j
@Component
public class UserOrderMessageProxy {


    /**
     * 下单消息通知
     * @param order
     */
    public void orderAdvice (OrderBO order) {

    }


    /**
     * 支付消息通知
     * @param order
     * @param payment
     */
    public void payAdvice (AOrder order, AOrderPayment payment) {

    }


    /**
     * 退款消息通知
     * @param order
     * @param payment
     * @param refund
     */
    public void refundAdvice (AOrder order, AOrderPayment payment, AOrderRefund refund) {

    }

}
