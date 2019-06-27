package com.data.dataproducer.enums;

/**
 * @author danny
 * @date 2019/6/3 7:49 PM
 */
public enum StoreStatusEnum {

    /**
     * 待营业
     */
    WAITING_OPERATION (0, "WAITING_OPERATION"),

    /**
     * 营业中
     */
    IN_OPERATION (1, "IN_OPERATION"),

    /**
     * 已关店
     */
    CLOSED (2, "CLOSED"),

    /**
     * 审核未通过
     */
    AUDIT_NOT_PASS (3, "CLOSED");


    private final int value;
    private String agg;

    StoreStatusEnum(int value, String agg) {
        this.value = value;
        this.agg = agg;
    }

    /**
     * 根据code查到枚举，默认返回DEDUCTION
     * @param value
     * @return
     */
    public static StoreStatusEnum getEnum(int value) {
        for (StoreStatusEnum c : StoreStatusEnum.values()) {
            if (value == c.value) {
                return c;
            }
        }
        return WAITING_OPERATION;
    }

    public int value() {
        return value;
    }

    public String getName () {
        return agg;
    }



}
