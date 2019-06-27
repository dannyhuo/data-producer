package com.data.dataproducer.enums;

/**
 * @author danny
 * @date 2019/6/3 7:49 PM
 */
public enum StoreTypeEnum {

    /**
     * 线上门店
     */
    ON_LINE (0, "ON_LINE"),

    /**
     * 线下门店
     */
    OFF_LINE (1, "OFF_LINE"),

    /**
     * 官网入驻商铺
     */
    ON_LINE_FRANCHISED_OUTLET(2, "ON_LINE_FRANCHISED_OUTLET");


    private final int value;
    private String agg;

    StoreTypeEnum(int value, String agg) {
        this.value = value;
        this.agg = agg;
    }

    /**
     * 根据code查到枚举，默认返回DEDUCTION
     * @param value
     * @return
     */
    public static StoreTypeEnum getEnum(int value) {
        for (StoreTypeEnum c : StoreTypeEnum.values()) {
            if (value == c.value) {
                return c;
            }
        }
        return ON_LINE_FRANCHISED_OUTLET;
    }

    public int value() {
        return value;
    }

    public String getName () {
        return agg;
    }



}
