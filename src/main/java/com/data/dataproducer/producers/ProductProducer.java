package com.data.dataproducer.producers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.data.dataproducer.config.DataCacheConfig;
import com.data.dataproducer.entity.AProduct;
import com.data.dataproducer.entity.AProductAudit;
import com.data.dataproducer.entity.AProductSoldout;
import com.data.dataproducer.entity.AStore;
import com.data.dataproducer.enums.ProductStatusEnum;
import com.data.dataproducer.enums.StoreStatusEnum;
import com.data.dataproducer.enums.StoreTypeEnum;
import com.data.dataproducer.factory.AProductFactory;
import com.data.dataproducer.factory.RandomFactory;
import com.data.dataproducer.service.IAProductAuditService;
import com.data.dataproducer.service.IAProductService;
import com.data.dataproducer.service.IAStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author danny
 * @date 2019/5/30 7:27 PM
 */
@Component
public class ProductProducer {

    @Autowired
    private RandomFactory randomFactory;

    @Autowired
    private AProductFactory aProductFactory;

    @Autowired
    private IAProductService iaProductService;

    @Autowired
    private IAProductAuditService iaProductAuditService;

    @Autowired
    private IAStoreService iaStoreService;

    @Autowired
    private DataCacheConfig dataCache;

    @Value("${data.producer.product.audit-not-pass.denominator}")
    private int auditNotPassDenominator;

    /**
     * 自动创建产品
     */
    @Scheduled(fixedRate = 1000 * 60 * 60 * 1)
    public void produceProduct () {
        int count = randomFactory.randomId(50L);
        List<AProduct> products = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            AProduct product = aProductFactory.produceProduct();

            //门店
            int storeId = dataCache.getRdmStoreId();

            AStore store = iaStoreService.getById(storeId);
            //门店类型不为空，且门店类型为官网入驻商铺，且状态为营业中
            if (null != store && null != store.getStoreType()
                && store.getStoreType().equals(StoreTypeEnum.ON_LINE_FRANCHISED_OUTLET.value())
                && store.getStatus() != null && store.getStatus().equals(StoreStatusEnum.IN_OPERATION.value())) {

                //对加盟店，设置专属门店。加盟店不会跨店铺销售
                product.setStoreId(store.getStoreId());
                product.setComment(store.getStoreName() + "专属产品");
            }

            //对于未设置门店的产品，表示是自营的线上和线下都可售的产品
            products.add(product);
        }
        iaProductService.saveBatch(products);

        //缓存当前最大的产品ID
        int size = products.size();
        if (size > 0) {
            AProduct product = products.get(size - 1);
            dataCache.setMaxProductId(product.getProductId());
        }
    }

    /**
     * 自动产品审核
     *
     */
    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void produceProductAudit () {
        LocalDateTime now = LocalDateTime.now();
        QueryWrapper<AProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", ProductStatusEnum.WAIT_AUDIT.value());
        //审核最近七天的产品
        queryWrapper.gt("create_time", now.plusDays(-7));
        List<AProduct> waitAuditProducts = iaProductService.list(queryWrapper);

        for (AProduct product: waitAuditProducts) {
            boolean auditNotPass = randomFactory.randomId(auditNotPassDenominator) % auditNotPassDenominator == 0;
            if (auditNotPass) {
                //审核不通过
                product.setStatus(ProductStatusEnum.NOT_PASS_AUDIT.value());
                product.setUpdateTime(now);
                AProductAudit audit = aProductFactory.productProductAudit(product, ProductStatusEnum.NOT_PASS_AUDIT);
                iaProductAuditService.save(audit);
                iaProductService.updateById(product);
            } else {
                //审核通过，产品改为上架
                product.setStatus(ProductStatusEnum.PUT_AWAY.value());
                product.setUpdateTime(now);
                AProductAudit audit = aProductFactory.productProductAudit(product, ProductStatusEnum.PASS_AUDIT);
                iaProductService.audit(product, audit);
            }
        }
    }


    /**
     * 产品自动下架
     *
     */
    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void produceProductSoldout () {
        long max = dataCache.getMaxProductId();
        long productId = randomFactory.randomId(max) + dataCache.getIdStart();
        AProduct product = iaProductService.getById(productId);

        //产品存在，且状态为上架状态
        if (null != product && null != product.getStatus()
                && product.getStatus().equals(ProductStatusEnum.PUT_AWAY.value())) {
            product.setStatus(ProductStatusEnum.SOLD_OUT.value());

            AProductSoldout soldout = aProductFactory.productProductSoldout(product);

            iaProductService.soldout(product, soldout);
        }
    }

}
