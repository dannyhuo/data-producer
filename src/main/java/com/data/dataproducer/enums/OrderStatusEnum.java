package com.data.dataproducer.enums;

/**
 * @author danny
 * @date 2019/6/3 7:49 PM
 */
public enum OrderStatusEnum {

    /**
     * 未使用
     */
    NOT_PAY (0, "NOT_PAY"),

    /**
     * 已支付
     */
    PAYED (1, "PAYED"),

    /**
     * 已退款
     */
    REFUNDED (2, "REFUNDED"),;


    private final int value;
    private String agg;

    OrderStatusEnum(int value, String agg) {
        this.value = value;
        this.agg = agg;
    }

    /**
     * 根据code查到枚举，默认返回DEDUCTION
     * @param value
     * @return
     */
    public static OrderStatusEnum getEnum(int value) {
        for (OrderStatusEnum c : OrderStatusEnum.values()) {
            if (value == c.value) {
                return c;
            }
        }
        return NOT_PAY;
    }

    public int value() {
        return value;
    }

    public String getName () {
        return agg;
    }



}
