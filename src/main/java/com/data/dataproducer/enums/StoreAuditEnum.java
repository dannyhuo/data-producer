package com.data.dataproducer.enums;

/**
 * @author danny
 * @date 2019/6/3 7:49 PM
 */
public enum StoreAuditEnum {

    /**
     * 审核成功
     */
    SUCCESS (0, "SUCCESS"),

    /**
     * 审核失败
     */
    FAILED (1, "FAILED");


    private final int value;
    private String agg;

    StoreAuditEnum(int value, String agg) {
        this.value = value;
        this.agg = agg;
    }

    /**
     * 根据code查到枚举
     * @param value
     * @return
     */
    public static StoreAuditEnum getEnum(int value) {
        for (StoreAuditEnum c : StoreAuditEnum.values()) {
            if (value == c.value) {
                return c;
            }
        }
        return SUCCESS;
    }

    public int value() {
        return value;
    }

    public String getName () {
        return agg;
    }



}
