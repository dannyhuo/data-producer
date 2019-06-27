package com.data.dataproducer.enums;

/**
 * @author danny
 * @date 2019/6/3 7:49 PM
 */
public enum CouponTypeEnum {

    /**
     * 抵扣，满减
     */
    DEDUCTION (0, "a"),

    /**
     * 打折
     */
    DISCOUNT (1, "b");


    private final int value;
    private String agg;

    CouponTypeEnum(int value, String agg) {
        this.value = value;
        this.agg = agg;
    }

    /**
     * 根据code查到枚举，默认返回DEDUCTION
     * @param value
     * @return
     */
    public static CouponTypeEnum getEnum(int value) {
        for (CouponTypeEnum c : CouponTypeEnum.values()) {
            if (value == c.value) {
                return c;
            }
        }
        return DEDUCTION;
    }

    public int value() {
        return value;
    }

    public String getName () {
        return agg;
    }



}
