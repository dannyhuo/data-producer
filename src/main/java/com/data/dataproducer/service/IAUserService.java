package com.data.dataproducer.service;

import com.data.dataproducer.entity.AUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author danny
 * @since 2019-05-31
 */
public interface IAUserService extends IService<AUser> {

    boolean login (AUser user);

}
