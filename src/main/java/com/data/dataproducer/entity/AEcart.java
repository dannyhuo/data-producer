package com.data.dataproducer.entity;

    import java.math.BigDecimal;
    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import java.io.Serializable;

    import lombok.Builder;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

    /**
    * <p>
    * 
    * </p>
    *
    * @author danny
    * @since 2019-06-28
    */
    @Data
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class AEcart implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "cart_id", type = IdType.AUTO)
    private Long cartId;

            /**
            * 产品ID
            */
    private Integer productId;

            /**
            * 产品品类【冗余】
            */
    private String categoryCode;

            /**
            * 所属用户ID
            */
    private Long userId;

            /**
            * 产品所属门店ID，存储入驻商家店铺，入驻商家订单需拆单
            */
    private Integer storeId;

            /**
            * 加入购物车时销售价
            */
    private BigDecimal sellPrice;

            /**
            * 加入购物车时市场价
            */
    private BigDecimal marketingPrice;

            /**
            * 数量
            */
    private Integer quntity;

            /**
            * 是否已下单, 0 未提交订单， 1 已提交订单
            */
    private Integer isOrdered;

            /**
            * 备注
            */
    private String comment;

            /**
            * 创建时间
            */
    private LocalDateTime createTime;

            /**
            * 修改时间
            */
    private LocalDateTime updateTime;

            /**
            * 是否删除， 0=未删除，1=已删除
            */
    private Integer isDeleted;


}
