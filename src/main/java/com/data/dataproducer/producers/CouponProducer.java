package com.data.dataproducer.producers;

import com.data.dataproducer.config.DataCacheConfig;
import com.data.dataproducer.entity.ACoupon;
import com.data.dataproducer.entity.ACouponDetail;
import com.data.dataproducer.factory.ACouponFactory;
import com.data.dataproducer.factory.RandomFactory;
import com.data.dataproducer.service.IACouponDetailService;
import com.data.dataproducer.service.IACouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author danny
 * @date 2019/5/30 7:27 PM
 */
@Slf4j
@Component
public class CouponProducer {

    @Autowired
    private ACouponFactory aCouponFactory;

    @Autowired
    private IACouponService iaCouponService;

    @Autowired
    private IACouponDetailService iaCouponDetailService;

    @Autowired
    private RandomFactory randomFactory;

    @Autowired
    private DataCacheConfig dataShare;

    @Value("${data.producer.coupon.cover.maxPercent}")
    private float maxCoverPercent;

    @Value("${data.producer.coupon.cover.minPercent}")
    private float minCoverPercent;

    @Value("${data.producer.coupon.enable.auto}")
    private boolean enableAutoSendCoupon;

    @Value("${data.producer.coupon.maxNum}")
    private int couponMaxNum;

    @Value("${data.producer.coupon.minNum}")
    private int couponMinNum;


    private int sendCouponBatchSize = 5000;

    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void produceCoupon () throws InterruptedException {
        if (!enableAutoSendCoupon) {
            return;
        }

        //做活动，创建优惠券
        ACoupon coupon = aCouponFactory.produceCoupon();
        if (!iaCouponService.save(coupon)) {
            return;
        }
        log.info("Coupon of '{}' created, will send coupon to users after 1 minutes.", coupon);
        //创建活动后，等待1分钟
        Thread.sleep(1000 * 60);

        //发券
        long base = dataShare.getMaxUserId() / 100;
        int min = (int) (base * minCoverPercent);
        min = min > couponMinNum ? couponMinNum : min;
        int max = (int) (base * maxCoverPercent);
        max = max > couponMaxNum ? couponMaxNum : max;

        int users = randomFactory.randomId(max - min) + min;
        log.info("Will send coupon to user, Covered user's is " + users);
        int totalPage = users % sendCouponBatchSize == 0 ?
                users / sendCouponBatchSize : users / sendCouponBatchSize + 1;
        log.info("Will send coupon by {} batches.", totalPage);
        for (int i = 0; i < totalPage; i++) {
            int size = sendCouponBatchSize;
            if (i + 1 == totalPage) {
                size = users % sendCouponBatchSize;
            }
            List<ACouponDetail> couponDetails = aCouponFactory.produceSendCoupon(coupon, dataShare.getMaxUserId(), size);
            iaCouponDetailService.saveBatch(couponDetails);
        }
        log.info("Send coupon[{}] finished.", coupon.getCouponId());
    }
}
