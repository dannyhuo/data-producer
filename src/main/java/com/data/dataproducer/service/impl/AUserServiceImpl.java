package com.data.dataproducer.service.impl;

import com.data.dataproducer.entity.AUser;
import com.data.dataproducer.mapper.AUserMapper;
import com.data.dataproducer.service.IAUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author danny
 * @since 2019-05-31
 */
@Service
public class AUserServiceImpl extends ServiceImpl<AUserMapper, AUser> implements IAUserService {

    @Override
    public boolean login(AUser user) {

        return false;
    }
}
