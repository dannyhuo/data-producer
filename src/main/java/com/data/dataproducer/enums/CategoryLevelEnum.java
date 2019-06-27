package com.data.dataproducer.enums;

/**
 * @author danny
 * @date 2019/6/3 7:49 PM
 */
public enum CategoryLevelEnum {

    /**
     * 一级分类
     */
    LEVEL_ONE (1, "一级分类"),

    /**
     * 二级分类
     */
    LEVEL_TWO (2, "二级分类"),

    /**
     * 三级分类
     */
    LEVEL_THREE (3, "三级分类");


    private final int value;
    private String agg;

    CategoryLevelEnum(int value, String agg) {
        this.value = value;
        this.agg = agg;
    }

    /**
     * 根据code查到枚举，默认返回DEDUCTION
     * @param value
     * @return
     */
    public static CategoryLevelEnum getEnum(int value) {
        for (CategoryLevelEnum c : CategoryLevelEnum.values()) {
            if (value == c.value) {
                return c;
            }
        }
        return LEVEL_ONE;
    }

    public int value() {
        return value;
    }

    public String getName () {
        return agg;
    }



}
