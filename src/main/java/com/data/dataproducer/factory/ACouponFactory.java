package com.data.dataproducer.factory;

import com.data.dataproducer.entity.ACoupon;
import com.data.dataproducer.entity.ACouponDetail;
import com.data.dataproducer.entity.ACouponUsage;
import com.data.dataproducer.entity.AOrder;
import com.data.dataproducer.enums.CouponTypeEnum;
import com.data.dataproducer.util.UCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author danny
 * @date 2019/5/31 9:51 AM
 */
@Slf4j
@Component
public class ACouponFactory {

    @Value("${data.producer.coupon.max-discount}")
    private float maxDiscount;

    @Value("${data.producer.coupon.max-deduction}")
    private int maxDeduction;

    @Autowired
    private RandomFactory randomFactory;

    /**
     * 生成优惠券
     * @return
     */
    public ACoupon produceCoupon () {

        ACoupon.ACouponBuilder builder = ACoupon.builder();

        builder.couponName(randomFactory.randomChinese(randomFactory.randomId(10L)));

        builder.createBy(randomFactory.randomFimilyAndName());
        LocalDateTime now = LocalDateTime.now();
        builder.startTime(now);
        //结束时间，最短10天，最长99天
        LocalDateTime endTime = now.plusDays(randomFactory.randomId(90L) + 10);
        builder.endTime(endTime);

        CouponTypeEnum couponType = CouponTypeEnum.getEnum(randomFactory.randomId(2L));
        builder.couponType(couponType.value());
        switch (couponType) {
            case DISCOUNT:
                //折扣
                builder.discount(randomFactory.randomDiscount(maxDiscount));
                break;
            case DEDUCTION:
                //满减
                int randomDeduction = randomFactory.randomId(maxDeduction / 5) * 5;
                int sill = (randomFactory.randomId(maxDeduction) + 1) * 10;
                builder.discountSill(BigDecimal.valueOf(sill));
                if (new Float(randomDeduction) / new Float(sill) < maxDiscount) {
                    builder.discountAmount(BigDecimal.valueOf(sill * maxDiscount));
                } else {
                    builder.discountAmount(BigDecimal.valueOf(randomDeduction));
                }

                break;
        }

        return builder.build();
    }


    /**
     * 生成待发送的优惠券
     * @param coupon
     * @param maxUserId
     * @param size
     * @return
     */
    public List<ACouponDetail> produceSendCoupon (ACoupon coupon, Long maxUserId, int size) {
        List<ACouponDetail> couponDetails = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ACouponDetail.ACouponDetailBuilder builder = ACouponDetail.builder();
            CouponTypeEnum couponType = CouponTypeEnum.getEnum(coupon.getCouponType());
            int soaId = randomFactory.randomId(25);
            builder.couponCode(couponType.getName() + UCodeUtil.produce(soaId));
            builder.couponId(coupon.getCouponId());
            builder.userId( randomFactory.randomId(maxUserId) + 10000L);
            couponDetails.add(builder.build());
        }
        return couponDetails;
    }

    /**
     * 生成使用的优惠券
     * @param couponDetail
     * @return
     */
    public ACouponUsage produceUsedCoupon (AOrder order, ACouponDetail couponDetail) {
        ACouponUsage.ACouponUsageBuilder builder = ACouponUsage.builder();
        builder.couponCode(couponDetail.getCouponCode());
        builder.orderId(order.getOrderId());
        builder.discountAmount(order.getDiscountAmount());
        builder.userId(couponDetail.getUserId());
        builder.useTime(LocalDateTime.now());
        builder.orderId(order.getOrderId());
        return builder.build();
    }

}
