package com.data.dataproducer.enums;

/**
 * @author danny
 * @date 2019/6/3 7:49 PM
 */
public enum MsgTypeEnum {

    /**
     * 短信模板
     */
    SMS (0, "FALSE"),

    /**
     * 微信
     */
    WECHAT (1, "TRUE"),

    /**
     * app push
     */
    APP_PUSH (2, "TRUE"),

    /**
     * 邮件
     */
    EMAIL (3, "TRUE");


    private final int value;
    private String agg;

    MsgTypeEnum(int value, String agg) {
        this.value = value;
        this.agg = agg;
    }

    /**
     * 根据code查到枚举
     * @param value
     * @return
     */
    public static MsgTypeEnum getEnum(int value) {
        for (MsgTypeEnum c : MsgTypeEnum.values()) {
            if (value == c.value) {
                return c;
            }
        }
        return SMS;
    }

    public int value() {
        return value;
    }

    public String getName () {
        return agg;
    }



}
