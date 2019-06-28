package com.data.dataproducer.config;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.Random;

/**
 * @author danny
 * @date 2019/5/31 10:23 PM
 */
@Slf4j
@Data
@Configuration
public class DataCacheConfig implements Serializable {

    private Random random = new Random();

    private Long idStart = 10000L;

    private Long maxUserId = idStart;

    private Integer maxProductId = idStart.intValue();

    private Long maxOrderId = idStart;

    private Long maxOrderPaymentId = idStart;

    private Integer maxStoreId = idStart.intValue();

    /**
     * 缓存最大用户ID
     * @param maxUserId
     */
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
        int interval = maxUserId.intValue() - idStart.intValue();
        return randomInt(interval > 1 ? interval : 1) + idStart.intValue() + 1;
    }

    /**
     * 缓存最大产品ID
     * @param maxProductId
     */
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
        int interval = maxProductId.intValue() - idStart.intValue();
        return randomInt(interval > 1 ? interval : 1) + idStart.intValue() + 1;
    }

    /**
     * 缓存最大订单ID
     * @param maxOrderId
     */
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
        int interval = maxOrderId.intValue() - idStart.intValue();
        return randomInt(interval > 1 ? interval : 1) + idStart + 1;
    }

    /**
     * 缓存最大支付单ID
     * @param maxOrderPaymentId
     */
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
        int interval = maxOrderPaymentId.intValue() - idStart.intValue();
        return randomInt(interval > 1 ? interval : 1) + idStart + 1;
    }

    /**
     * 缓存最大门店ID
     * @param maxStoreId
     */
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
        int interval = maxStoreId.intValue() - idStart.intValue();
        return randomInt(interval > 1 ? interval : 1) + idStart.intValue();
    }

    /**
     * 生成随机数
     * @param max
     * @return
     */
    public int randomInt (int max) {
        if (max < 1) {
            return 1;
        }

        return random.nextInt(max);

    }
}
