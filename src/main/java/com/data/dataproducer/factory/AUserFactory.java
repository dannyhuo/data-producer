package com.data.dataproducer.factory;

import com.data.dataproducer.entity.AArea;
import com.data.dataproducer.entity.AUser;
import com.data.dataproducer.entity.AUserLoginDetail;
import com.data.dataproducer.util.MD5Util;
import com.data.dataproducer.util.TimeUtil;
import com.data.dataproducer.util.UCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author danny
 * @date 2019/5/31 9:50 AM
 */
@Component
public class AUserFactory {

    @Autowired
    private RandomFactory randomFactory;

    private Random random = new Random();

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    /**
     * 批量生成UAser对象
     */
    public AUser produceUser() throws ParseException {
        AUser.AUserBuilder user = AUser.builder();

        //生日
        String birthNum = randomFactory.randomBirthday();
        Date birthDate = simpleDateFormat.parse(birthNum);
        user.birthday(TimeUtil.toLocalDate(birthDate));

        //随机生成地区
        AArea area = randomFactory.randomArea();
        user.cardId(randomFactory.randomCardId(area.getBm(), birthNum));
        user.city(area.getCity());
        user.provice(area.getProvince());
        user.county(area.getContry());

        //email
        user.email(randomFactory.randomEmail());

        //gender
        user.gender(randomFactory.randomGender());

        user.hobby(randomFactory.randomHobbies());

        user.profession(randomFactory.randomProfession());

        user.openId(UCodeUtil.produce());

        user.unionId(UCodeUtil.produce());

        user.realName(randomFactory.randomFimilyAndName());

        user.userName(UCodeUtil.produce());

        user.password(MD5Util.md5(UCodeUtil.produce()));

        user.phone(randomFactory.randomMobile());

        return user.build();

    }

    /**
     * 批量生成UAser对象
     * @param n
     */
    public List<AUser> produceUsers(int n) throws ParseException {
        if (n < 1) {
            return null;
        }
        List<AUser> users = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            users.add(produceUser());
        }
        return users;
    }

    /**
     * 批量生成UAser对象
     * @param n
     */
    public List<AUser> randomAUsers(int n) throws ParseException {
        if (n < 1) {
            return null;
        }
        int c = random.nextInt(n) + 1;
        List<AUser> users = new ArrayList<>();
        for (int i = 0; i < c; i++) {
            users.add(produceUser());
        }
        return users;
    }


    /**
     * 批量生成UAser对象
     * @param user
     */
    public AUserLoginDetail randomAUserLogin(AUser user) {
        AUserLoginDetail.AUserLoginDetailBuilder loginBuilder = AUserLoginDetail.builder();
        loginBuilder.browser(randomFactory.randomBrowser());
        loginBuilder.ipAddress(randomFactory.randomIp());
        loginBuilder.loginPlatform(randomFactory.randomPlatform());
        loginBuilder.loginStatus(randomFactory.randomLoginStatus(100));
        loginBuilder.userId(user.getUserId());
        loginBuilder.diviceId(randomFactory.randomDevice(user.getPassword()));
        return loginBuilder.build();
    }


}
