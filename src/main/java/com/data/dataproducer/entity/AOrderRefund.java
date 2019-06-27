package com.data.dataproducer.entity;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;

    import java.math.BigDecimal;
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
    public class AOrderRefund implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "order_refund_id", type = IdType.AUTO)
    private Long orderRefundId;

            /**
            * 支付单号
            */
    private Long orderPaymentId;

            /**
            * 退款金额
            */
    private BigDecimal refundAmount;

            /**
            * 退款卡号
            */
    private String refundCardNo;

            /**
            * 退款回执单号编号
            */
    private String payNo;

            /**
            * 退款状态，0成功，1失败
            */
    private Integer status;

            /**
            * 退款时间
            */
    private LocalDateTime refundTime;


}
