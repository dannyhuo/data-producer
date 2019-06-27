package com.data.dataproducer.service;

import com.data.dataproducer.entity.AProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import com.data.dataproducer.entity.AProductAudit;
import com.data.dataproducer.entity.AProductSoldout;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author danny
 * @since 2019-05-30
 */
public interface IAProductService extends IService<AProduct> {

    /**
     * 产品审核
     * @param product
     * @param audit
     * @return
     */
    boolean audit (AProduct product, AProductAudit audit);

    /**
     * 产品下架
     * @param product
     * @param soldout
     * @return
     */
    boolean soldout (AProduct product, AProductSoldout soldout);

}
