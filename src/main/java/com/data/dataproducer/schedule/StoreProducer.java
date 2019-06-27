package com.data.dataproducer.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.data.dataproducer.config.DataCacheConfig;
import com.data.dataproducer.entity.ACoupon;
import com.data.dataproducer.entity.ACouponDetail;
import com.data.dataproducer.entity.AStore;
import com.data.dataproducer.entity.AStoreAudit;
import com.data.dataproducer.enums.StoreStatusEnum;
import com.data.dataproducer.enums.StoreTypeEnum;
import com.data.dataproducer.factory.ACouponFactory;
import com.data.dataproducer.factory.AStoreFactory;
import com.data.dataproducer.factory.RandomFactory;
import com.data.dataproducer.service.IACouponDetailService;
import com.data.dataproducer.service.IACouponService;
import com.data.dataproducer.service.IAStoreAuditService;
import com.data.dataproducer.service.IAStoreService;
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
public class StoreProducer {

    @Autowired
    private AStoreFactory aStoreFactory;

    @Autowired
    private IAStoreService iaStoreService;

    @Autowired
    private DataCacheConfig dataCache;

    @Autowired
    private RandomFactory randomFactory;

    @Autowired
    private IAStoreAuditService iaStoreAuditService;

    /**
     * 申请开店
     */
    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void produceStore () {

        int id = randomFactory.randomId(5);

        //商家店铺占比五分之三
        AStore store = aStoreFactory.produceStore(StoreTypeEnum.getEnum(id));

        iaStoreService.save(store);

        //缓存最大门店ID
        dataCache.setMaxStoreId(store.getStoreId());
    }

    /**
     * 店铺审核
     */
    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void produceStoreAudit () {

        QueryWrapper<AStore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", StoreStatusEnum.WAITING_OPERATION);

        List<AStore> stores = iaStoreService.list(queryWrapper);

        for (AStore store : stores) {
            AStoreAudit storeAudit = aStoreFactory.produceStoreAudit(store);
            iaStoreService.audit(store, storeAudit);
        }
    }
}
