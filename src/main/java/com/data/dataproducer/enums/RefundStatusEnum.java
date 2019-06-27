package com.data.dataproducer.enums;

/**
 * @author danny
 * @date 2019/6/3 7:49 PM
 */
public enum RefundStatusEnum {

    /**
     * 退款成功
     */
    SUCCESS (0, "SUCCESS"),

    /**
     * 退款失败
     */
    FAILED (1, "FAILED");


    private final int value;
    private String agg;

    RefundStatusEnum(int value, String agg) {
        this.value = value;
        this.agg = agg;
    }

    /**
     * 根据code查到枚举
     * @param value
     * @return
     */
    public static RefundStatusEnum getEnum(int value) {
        for (RefundStatusEnum c : RefundStatusEnum.values()) {
            if (value == c.value) {
                return c;
            }
        }
        return FAILED;
    }

    public int value() {
        return value;
    }

    public String getName () {
        return agg;
    }



}
