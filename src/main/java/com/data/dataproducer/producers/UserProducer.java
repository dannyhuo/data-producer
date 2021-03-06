package com.data.dataproducer.producers;

import com.data.dataproducer.config.DataCacheConfig;
import com.data.dataproducer.entity.AUser;
import com.data.dataproducer.entity.AUserLoginDetail;
import com.data.dataproducer.factory.AUserFactory;
import com.data.dataproducer.factory.RandomFactory;
import com.data.dataproducer.proxy.jms.UserRegisterMessageProxy;
import com.data.dataproducer.service.IAUserLoginDetailService;
import com.data.dataproducer.service.IAUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author danny
 * @date 2019/5/30 7:27 PM
 */
@Component
public class UserProducer {

    @Autowired
    private AUserFactory aUserFactory;

    @Autowired
    private IAUserService iaUserService;

    @Autowired
    private IAUserLoginDetailService iaUserLoginDetailService;

    @Autowired
    private RandomFactory randomFactory;

    private Long maxUserId = 10000L;

    @Autowired
    private DataCacheConfig dataShare;

    @Autowired
    private Map<Integer, Integer> hour24Max;

    @Autowired
    private UserRegisterMessageProxy userRegisterMessage;


    /**
     * 用户注册
     */
    @Scheduled(fixedRate = 200)
    public void produceUsers () {
        try {
            // 随机决定是否执行创建用户
            int rdflag = randomFactory.randomId(100);
            if (rdflag % 5 == 0) {
                return;
            }

            // 根据规则创建用户
            int hour = LocalDateTime.now().getHour();
            Integer maxUserCount = hour24Max.get(hour);
            maxUserCount = maxUserCount == null ? 1 : maxUserCount;
            if (maxUserCount < 1) {
                return;
            }

            int userCount = randomFactory.randomId(maxUserCount);
            if (userCount < 1) {
                return;
            }
            //注册用户
            List<AUser> users = aUserFactory.randomAUsers(userCount);
            iaUserService.saveBatch(users);
            int uc = users.size();

            //注册完登录
            List<AUserLoginDetail> loginDetails = new ArrayList<>();
            for (int i = 0; i < uc; i++) {
                AUser user = users.get(i);

                //通知
                userRegisterMessage.registerAdvice(user);

                if (null != user && null != user.getUserId()) {
                    if (user.getUserId() > maxUserId) {
                        maxUserId = user.getUserId();
                    }
                    loginDetails.add(aUserFactory.randomAUserLogin(user));
                }
            }
            iaUserLoginDetailService.saveBatch(loginDetails);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //记录当前最大的用户ID
        dataShare.setMaxUserId(maxUserId);
    }


    /**
     * 用户注册
     */
    @Scheduled(fixedRate = 10000)
    public void login () {
        long id = randomFactory.randomId(maxUserId);

        AUser user = iaUserService.getById(id);

        if (null != user) {
            AUserLoginDetail loginObj = aUserFactory.randomAUserLogin(user);
            iaUserLoginDetailService.save(loginObj);
        }
    }










}
