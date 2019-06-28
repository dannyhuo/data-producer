package com.data.dataproducer.proxy.jms;

import com.data.dataproducer.entity.AUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户注册通知
 *
 * @author danny
 *
 * @date 2019/6/28 4:48 PM
 *
 */
@Slf4j
@Component
public class UserRegisterMessageProxy {

    /**
     * 用户注册消息通知
     *
     * @param user
     */
    public void registerAdvice (AUser user) {

    }

}
