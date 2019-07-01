package com.data.dataproducer.enums;

/**
 * @author danny
 * @date 2019/6/29 8:44 PM
 */
public enum PlatformEnum {

    /**
     * PC端
     */
    PC (0, "VIEW"),

    /**
     * APP端
     */
    APP (1, "CLICK"),

    /**
     * H5页
     */
    H5 (2, "SEARCH"),

    /**
     * 微信
     */
    WECHAT (3, "ADD_CART");


    private final int value;
    private String agg;

    PlatformEnum(int value, String agg) {
        this.value = value;
        this.agg = agg;
    }

    /**
     * 根据code查到枚举
     * @param value
     * @return
     */
    public static PlatformEnum getEnum(int value) {
        for (PlatformEnum c : PlatformEnum.values()) {
            if (value == c.value) {
                return c;
            }
        }
        return PC;
    }

    public int value() {
        return value;
    }

    public String getName () {
        return agg;
    }

}
