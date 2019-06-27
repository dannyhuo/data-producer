package com.data.dataproducer.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

/**
 * @author danny
 * @date 2019/5/31 10:23 PM
 */
@Configuration
@Getter
@Slf4j
public class DataCacheConfig {

    Random random = new Random();

    private Long idStart = 10000L;

    private Long maxUserId = idStart;

    private Integer maxProductId = idStart.intValue();

    private Long maxOrderId = idStart;

    private Long maxOrderPaymentId = idStart;

    private Integer maxStoreId = idStart.intValue();

    public synchronized void setMaxUserId (Long maxUserId) {
        if (null != maxUserId && maxUserId > this.maxUserId) {
            this.maxUserId = maxUserId;
        }
    }

    /**
     * 随机获取用户ID
     * @return
     */
    public int getRdmUserId () {
        return randomInt(maxUserId.intValue() - idStart.intValue()) + idStart.intValue() + 1;
    }

    public synchronized void setMaxProductId (Integer maxProductId) {
        if (null != maxProductId && maxProductId > this.maxProductId) {
            this.maxProductId = maxProductId;
        }
    }

    /**
     * 随机获取产品ID
     * @return
     */
    public int getRdmProductId () {
        return randomInt(maxProductId.intValue() - idStart.intValue()) + idStart.intValue() + 1;
    }

    public synchronized void setMaxOrderId (Long maxOrderId) {
        if (null != maxOrderId && maxOrderId > this.maxOrderId) {
            this.maxOrderId = maxOrderId;
        }
    }

    /**
     * 随机获取订单ID
     * @return
     */
    public long getRdmOrderId () {
        return randomInt(maxOrderId.intValue() - idStart.intValue()) + idStart + 1;
    }

    public synchronized void setMaxOrderPaymentId (Long maxOrderPaymentId) {
        if (null != maxOrderPaymentId && maxOrderPaymentId > this.maxOrderPaymentId) {
            this.maxOrderPaymentId = maxOrderPaymentId;
        }
    }

    /**
     * 随机获取支付ID
     * @return
     */
    public long getRdmPaymentId () {
        return randomInt(maxOrderPaymentId.intValue() - idStart.intValue()) + idStart + 1;
    }

    public synchronized void setMaxStoreId (Integer maxStoreId) {
        if (null != maxStoreId && maxStoreId > this.maxStoreId) {
            this.maxStoreId = maxStoreId;
        }
    }

    /**
     * 随机获取门店ID
     * @return
     */
    public int getRdmStoreId () {
        return randomInt(maxStoreId.intValue() - idStart.intValue()) + idStart.intValue();
    }


    public int randomInt (int max) {

        if (max < 1) {
            return 0;
        }
        return random.nextInt(max);

    }
}
