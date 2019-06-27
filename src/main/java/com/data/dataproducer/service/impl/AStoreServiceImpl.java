package com.data.dataproducer.service.impl;

import com.data.dataproducer.entity.AStore;
import com.data.dataproducer.entity.AStoreAudit;
import com.data.dataproducer.mapper.AStoreMapper;
import com.data.dataproducer.service.IAStoreAuditService;
import com.data.dataproducer.service.IAStoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author danny
 * @since 2019-06-05
 */
@Service
public class AStoreServiceImpl extends ServiceImpl<AStoreMapper, AStore> implements IAStoreService {

    @Autowired
    private IAStoreService iaStoreService;

    @Autowired
    private IAStoreAuditService iaStoreAuditService;

    @Override
    public boolean audit(AStore store, AStoreAudit audit) {
        if (null == store || null == audit) {
            return false;
        }
        boolean flag = iaStoreAuditService.save(audit);

        if (flag) {
            flag = iaStoreService.updateById(store);
        }

        return flag;
    }
}
