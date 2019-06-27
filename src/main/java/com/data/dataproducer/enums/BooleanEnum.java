package com.data.dataproducer.enums;

/**
 * @author danny
 * @date 2019/6/3 7:49 PM
 */
public enum BooleanEnum {

    /**
     * 否
     */
    FALSE (0, "FALSE"),

    /**
     * 是
     */
    TRUE (1, "TRUE");


    private final int value;
    private String agg;

    BooleanEnum(int value, String agg) {
        this.value = value;
        this.agg = agg;
    }

    /**
     * 根据code查到枚举，默认返回DEDUCTION
     * @param value
     * @return
     */
    public static BooleanEnum getEnum(int value) {
        for (BooleanEnum c : BooleanEnum.values()) {
            if (value == c.value) {
                return c;
            }
        }
        return FALSE;
    }

    public int value() {
        return value;
    }

    public String getName () {
        return agg;
    }



}
