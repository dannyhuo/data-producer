package com.data.dataproducer.service.impl;

import com.data.dataproducer.entity.AProduct;
import com.data.dataproducer.entity.AProductAudit;
import com.data.dataproducer.entity.AProductSoldout;
import com.data.dataproducer.mapper.AProductMapper;
import com.data.dataproducer.service.IAProductAuditService;
import com.data.dataproducer.service.IAProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.data.dataproducer.service.IAProductSoldoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author danny
 * @since 2019-05-30
 */
@Service
public class AProductServiceImpl extends ServiceImpl<AProductMapper, AProduct> implements IAProductService {

    @Autowired
    private IAProductService iaProductService;
    @Autowired
    private IAProductAuditService iaProductAuditService;
    @Autowired
    private IAProductSoldoutService iaProductSoldoutService;

    @Override
    public boolean audit(AProduct product, AProductAudit audit) {

        boolean flag = iaProductAuditService.save(audit);

        if (flag) {
            flag = iaProductService.updateById(product);
        }

        return flag;
    }

    @Override
    public boolean soldout(AProduct product, AProductSoldout soldout) {

        boolean flag = iaProductSoldoutService.save(soldout);

        if (flag) {
            flag = iaProductService.updateById(product);
        }

        return flag;
    }
}
