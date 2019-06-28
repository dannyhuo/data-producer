package com.data.dataproducer.factory;


import com.data.dataproducer.entity.AArea;
import com.data.dataproducer.util.MD5Util;
import com.data.dataproducer.util.UCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

/**
 * @author danny
 * @date 2019/5/30 7:40 PM
 */
@Component
public class RandomFactory {

    @Autowired
    private List<AArea> areas;

    @Autowired
    private List<String> family;

    @Autowired
    private List<Integer> mobilePrefix;

    @Autowired
    private List<String> emailSuffix;

    @Autowired
    private List<String> profession;

    @Autowired
    private List<String> hobby;

    @Autowired
    private List<String> browser;

    @Autowired
    private List<String> platform;
    @Autowired
    private List<String> payStyle;


    private Random random = new Random();
    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private String MALE = "M";
    private String FEMALE = "F";

    /**
     * 随机生成优惠券code
     * @return
     */
    public String randomCouponCode () {
        return null;
    }

    /**
     * 随机生成指定个数的中国字
     * @param n
     * @return
     */
    public String randomChinese (int n) {

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < n; i++) {
            //B0 + 0~39(16~55) 一级汉字所占区
            int highCode = (176 + Math.abs(random.nextInt(39)));

            //A1 + 0~93 每区有94个汉字
            int lowCode = (161 + Math.abs(random.nextInt(93)));

            byte[] b = new byte[2];
            b[0] = (Integer.valueOf(highCode)).byteValue();
            b[1] = (Integer.valueOf(lowCode)).byteValue();

            String word = null;
            try {
                word = new String(b, "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            buffer.append(word);
        }

        return buffer.toString();
    }


    /**
     * 随机生成生日
     * @return
     */
    public String randomBirthday () {

        int year = 1919 + random.nextInt(100);
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(29);
        if (month == 1 || month == 3 || month == 5 || month == 7
                || month == 8 || month == 10 || month == 12) {
            day += random.nextInt(3);
        } else if (month != 2) {
            day += random.nextInt(2);
        }

        return UCodeUtil.lpad(year + "", 4) +
                UCodeUtil.lpad(month + "", 2) +
                UCodeUtil.lpad(day + "", 2);
    }

    /**
     * 随机card_id
     * @param area
     * @return
     */
    public String randomCardId (int area) {
        return area + randomBirthday() +
                UCodeUtil.lpad(random.nextInt(9999) + "", 4);
    }

    /**
     * 随机card_id
     * @return
     */
    public String randomCardId () {
        int areaIndex = random.nextInt(areas.size());
        return randomCardId(areas.get(areaIndex).getBm() + "");
    }

    /**
     * 随机card_id
     * @param birthday
     * @return
     */
    public String randomCardId (String birthday) {
        int areaIndex = random.nextInt(areas.size());
        return randomCardId(areas.get(areaIndex).getBm(), birthday);
    }


    /**
     * 随机card_id
     * @param area
     * @param birthday
     * @return
     */
    public String randomCardId (int area, String birthday) {
        return area + birthday +
                UCodeUtil.lpad(random.nextInt(9999) + "", 4);
    }

    /**
     * 随机生成姓名
     * @return
     */
    public String randomFimilyAndName () {
        String f = family.get(random.nextInt(family.size()));
        int len = random.nextInt(2) + 1;
        return f + this.randomChinese(len);
    }

    /**
     * 随机mobile
     * @param prefix
     * @return
     */
    public String randomMobile (String prefix) {
        return prefix +
                UCodeUtil.lpad(random.nextInt(99999999) + "", 8);
    }

    /**
     * 随机mobile
     * @return
     */
    public String randomMobile () {
        int mIndex = random.nextInt(mobilePrefix.size());
        return mobilePrefix.get(mIndex) +
                UCodeUtil.lpad(random.nextInt(99999999) + "", 8);
    }


    /**
     * 随机邮箱
     * @param suffix 邮箱后缀
     * @return
     */
    public String randomEmail (String suffix) {
        return  UCodeUtil.produce() + suffix;
    }

    /**
     * 随机邮箱
     * @return
     */
    public String randomEmail () {
        int eIndex = random.nextInt(emailSuffix.size());
        return  UCodeUtil.produce() + emailSuffix.get(eIndex);
    }

    /**
     * 生成随机区域
     * @return
     */
    public AArea randomArea () {
        int aIndex = random.nextInt(areas.size());
        return  areas.get(aIndex);
    }

    /**
     * 生成随机职业
     * @return
     */
    public String randomProfession () {
        int aIndex = random.nextInt(profession.size());
        return  profession.get(aIndex);
    }

    /**
     * 生成随机兴趣爱好
     * @return
     */
    public String randomHobby () {
        int aIndex = random.nextInt(hobby.size());
        return  hobby.get(aIndex);
    }

    /**
     * 生成随机浏览器
     * @return
     */
    public String randomBrowser () {
        int aIndex = random.nextInt(browser.size());
        return  browser.get(aIndex);
    }

    /**
     * 生成随机IP
     * @return
     */
    public String randomIp () {
        return  random.nextInt(255) + "."
                + random.nextInt(255) + "."
                + random.nextInt(255) + "."
                + random.nextInt(255);
    }

    /**
     * 生成随机platform
     * @return
     */
    public int randomPlatform () {
        return random.nextInt(10);
    }

    /**
     * 生成随机兴趣爱好
     * @return
     */
    public String randomHobbies () {
        int count = random.nextInt(6) + 1;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < count; i++) {

            buffer.append(randomHobby());

            if (i < count - 1) {
                buffer.append(",");
            }
        }
        return  buffer.toString();
    }

    /**
     * 生成随机性别
     * @return
     */
    public String randomGender () {
        int gender = random.nextInt(2);

        return  gender == 0 ? MALE : FEMALE;
    }

    /**
     * 生成随机打折
     * @param maxDiscount 最大折扣
     * @return
     */
    public BigDecimal randomDiscount (Float maxDiscount) {
        float f = random.nextFloat();
        if (null != maxDiscount && f < maxDiscount) {
            return new BigDecimal(decimalFormat.format(maxDiscount));
        }
        return new BigDecimal(decimalFormat.format(f));
    }


    /**
     * 生成随机状态
     * 0:失败， 1:成功
     * @return
     */
    public int randomLoginStatus (int i) {
        int rand = random.nextInt(1024);

        return  rand % i == 0 ? 0 : 1;
    }

    /**
     * 生成随机数
     * @param i
     * @return
     */
    public int randomId (Long i) {
        if (i < 1) {
            return 0;
        }

        return  random.nextInt(i.intValue());
    }

    /**
     * 生成随机数
     * @param i
     * @return
     */
    public int randomId (int i) {
        if (i < 1) {
            return 0;
        }

        return  random.nextInt(i);
    }


    /**
     * 生成随机设备ID
     * @param base
     * @return
     */
    public String randomDevice (String base) {
        //默认最多16个设备
        int i = random.nextInt(16);

        return MD5Util.md5(base + i);
    }


    /**
     * 生成随机设备ID
     * @return
     */
    public String randomProductName () {
        //默认最多16字
        int i = random.nextInt(16);

        return randomChinese(i);
    }


    /**
     * 生成随机价格
     * @return
     */
    public BigDecimal randomProductPrice () {
        //最大不超过20000，最小不低于1
        int i = random.nextInt(20000) + 1;

        return BigDecimal.valueOf(i);
    }

    /**
     * 生成随机价格
     * @param max
     * @return
     */
    public BigDecimal randomProductPrice (BigDecimal max) {
        //最大不超过10000，最小不低于1
        int i = random.nextInt(max.intValue()) + 1;

        return BigDecimal.valueOf(i);
    }

    /**
     * 生成随机价格
     * @param min
     * @param max
     * @return
     */
    public BigDecimal randomProductPrice (BigDecimal min, BigDecimal max) {
        //最大不超过10000，最小不低于1
        int minValue = min.intValue();
        int i = random.nextInt(max.intValue() - minValue) + min.intValue();

        return BigDecimal.valueOf(i);
    }


    /**
     * 随机生成支付平台
     * @return
     */
    public String randomPayStyle () {
        int i = random.nextInt(payStyle.size());
        return payStyle.get(i);
    }


}
