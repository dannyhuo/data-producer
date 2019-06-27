package com.data.dataproducer.service;

import com.data.dataproducer.entity.AStore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.data.dataproducer.entity.AStoreAudit;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author danny
 * @since 2019-06-05
 */
public interface IAStoreService extends IService<AStore> {

    boolean audit (AStore store, AStoreAudit audit);

}
