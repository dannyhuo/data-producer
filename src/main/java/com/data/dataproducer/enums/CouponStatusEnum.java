package com.data.dataproducer.enums;

/**
 * @author danny
 * @date 2019/6/3 7:49 PM
 */
public enum CouponStatusEnum {

    /**
     * 未使用
     */
    NOT_USED (0, "NOT_USED"),

    /**
     * 已使用
     */
    USED (1, "USED"),

    /**
     * 已过期
     */
    EXPIRED (2, "USED"),;


    private final int value;
    private String agg;

    CouponStatusEnum(int value, String agg) {
        this.value = value;
        this.agg = agg;
    }

    /**
     * 根据code查到枚举，默认返回DEDUCTION
     * @param value
     * @return
     */
    public static CouponStatusEnum getEnum(int value) {
        for (CouponStatusEnum c : CouponStatusEnum.values()) {
            if (value == c.value) {
                return c;
            }
        }
        return EXPIRED;
    }

    public int value() {
        return value;
    }

    public String getName () {
        return agg;
    }



}
