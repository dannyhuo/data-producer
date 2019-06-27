package com.data.dataproducer.entity;

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
    public class ACouponDetail implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "coupon_detail_id", type = IdType.AUTO)
    private Long couponDetailId;

            /**
            * 优惠券
            */
    private Long couponId;

            /**
            * 优惠券码，唯一
            */
    private String couponCode;

            /**
            * 用户ID
            */
    private Long userId;

            /**
            * 支付状态，0未使用，1已使用
            */
    private Integer status;

            /**
            * 发券时间
            */
    private LocalDateTime sendTime;

            /**
            * 发券时间
            */
    private LocalDateTime usedTime;


}
