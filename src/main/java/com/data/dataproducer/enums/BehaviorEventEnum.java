package com.data.dataproducer.enums;

/**
 * 行为事件
 *
 * @author danny
 *
 * @date 2019/6/28 7:49 PM
 *
 */
public enum BehaviorEventEnum {

    /**
     * 浏览
     */
    VIEW (0, "VIEW"),

    /**
     * 点击
     */
    CLICK (1, "CLICK"),

    /**
     * 搜索
     */
    SEARCH (2, "SEARCH"),

    /**
     * 加购物车
     */
    ADD_CART (3, "ADD_CART"),

    /**
     * 收藏
     */
    FAVORITE (4, "FAVORITE"),

    /**
     * 下单
     */
    ORDER (5, "ORDER"),

    /**
     * 取消订单
     */
    CANCEL (6, "CANCEL"),

    /**
     * 支付
     */
    PAY (7, "PAY"),


    /**
     * 申请退款
     */
    REFUND (8, "REFUND");


    private final int value;
    private String agg;

    BehaviorEventEnum(int value, String agg) {
        this.value = value;
        this.agg = agg;
    }

    /**
     * 根据code查到枚举
     * @param value
     * @return
     */
    public static BehaviorEventEnum getEnum(int value) {
        for (BehaviorEventEnum c : BehaviorEventEnum.values()) {
            if (value == c.value) {
                return c;
            }
        }
        return VIEW;
    }

    public int value() {
        return value;
    }

    public String getName () {
        return agg;
    }



}
