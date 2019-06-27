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
* @since 2019-05-30
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Builder
    public class AOrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "order_detail_id", type = IdType.AUTO)
    private Long orderDetailId;

            /**
            * 订单ID
            */
    private Long orderId;

            /**
            * 产品ID
            */
    private Integer productId;

            /**
            * 数量
            */
    private Integer quantity;

            /**
            * 单价
            */
    private BigDecimal price;

            /**
            * 总金额，单价*数量
            */
    private BigDecimal totalAmount;

            /**
            * 折扣金额
            */
    private BigDecimal discountAmount;

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
