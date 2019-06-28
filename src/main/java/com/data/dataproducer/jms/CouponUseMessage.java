package com.data.dataproducer.jms;

import com.data.dataproducer.entity.ACouponDetail;
import com.data.dataproducer.entity.ACouponUsage;
import com.data.dataproducer.entity.AOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 使用优惠券通知
 *
 * @author danny
 *
 * @date 2019/6/28 5:06 PM
 */
@Slf4j
@Component
public class CouponUseMessage {

    /**
     * 用券消息通知
     * @param order 使用的订单
     * @param couponDetail 优惠券
     * @param usedCoupon 使用记录
     */
    public void useCouponAdvice (AOrder order, ACouponDetail couponDetail,
                                 ACouponUsage usedCoupon) {

    }


}
