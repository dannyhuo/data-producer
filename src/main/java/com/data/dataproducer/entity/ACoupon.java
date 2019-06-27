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
    public class ACoupon implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "coupon_id", type = IdType.AUTO)
    private Long couponId;

            /**
            * 优惠券名称
            */
    private String couponName;

            /**
            * 优惠券类型，0：满减，如满100减5块， 1：折扣券
            */
    private Integer couponType;

            /**
            * 满减基数
            */
    private BigDecimal discountSill;

            /**
            * 打折， 9折为0.90
            */
    private BigDecimal discount;

            /**
            * 满减或立减金额
            */
    private BigDecimal discountAmount;

            /**
            * 审核状态，0待审核，1审核通过，2审核未通过
            */
    private Integer auditStatus;

            /**
            * 有效期，起始时间
            */
    private LocalDateTime startTime;

            /**
            * 有效期，截止时间
            */
    private LocalDateTime endTime;

            /**
            * 创建人
            */
    private String createBy;

            /**
            * 创建时间
            */
    private LocalDateTime createTime;


}
