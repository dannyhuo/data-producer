package com.data.dataproducer.service;

import com.data.dataproducer.entity.AEcart;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author danny
 * @since 2019-06-28
 */
public interface IAEcartService extends IService<AEcart> {

    /**
     * 加入购物车
     * @param productId
     * @param userId
     * @param quantity
     * @return
     */
    AEcart addEcart (Integer productId, Long userId, int quantity);

}
