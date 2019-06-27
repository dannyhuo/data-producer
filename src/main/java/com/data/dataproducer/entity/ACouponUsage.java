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
    public class ACouponUsage implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "coupon_usage_id", type = IdType.AUTO)
    private Long couponUsageId;

            /**
            * 优惠券码，唯一
            */
    private String couponCode;

            /**
            * 用户ID
            */
    private Long userId;

            /**
            * 订单ID
            */
    private Long orderId;

            /**
            * 抵扣金额
            */
    private BigDecimal discountAmount;

            /**
            * 用券时间
            */
    private LocalDateTime useTime;


}
