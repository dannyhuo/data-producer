package com.data.dataproducer.enums;

/**
 * @author danny
 * @date 2019/6/3 7:49 PM
 */
public enum ProductStatusEnum {

    /**
     * 待审核
     */
    WAIT_AUDIT (0, "WAIT_AUDIT"),

    /**
     * 审核未通过
     */
    PASS_AUDIT (1, "PASS_AUDIT"),

    /**
     * 审核已通过
     */
    NOT_PASS_AUDIT (2, "NOT_PASS_AUDIT"),

    /**
     * 已上架
     */
    PUT_AWAY (3, "PUT_AWAY"),

    /**
     * 已下架
     */
    SOLD_OUT (4, "SOLD_OUT");


    private final int value;
    private String agg;

    ProductStatusEnum(int value, String agg) {
        this.value = value;
        this.agg = agg;
    }

    /**
     * 根据code查到枚举
     * @param value
     * @return
     */
    public static ProductStatusEnum getEnum(int value) {
        for (ProductStatusEnum c : ProductStatusEnum.values()) {
            if (value == c.value) {
                return c;
            }
        }
        return WAIT_AUDIT;
    }

    public int value() {
        return value;
    }

    public String getName () {
        return agg;
    }



}
