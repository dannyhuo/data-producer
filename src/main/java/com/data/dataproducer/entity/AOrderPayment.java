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
    public class AOrderPayment implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "order_payment_id", type = IdType.AUTO)
    private Long orderPaymentId;

            /**
            * 订单ID
            */
    private Long orderId;

            /**
            * 实付金额
            */
    private BigDecimal payAmount;

            /**
            * 支付方式，微信，支付宝，银行卡
            */
    private String payStyle;

            /**
            * 支付卡号
            */
    private String payCardNo;

            /**
            * 支付回执单号
            */
    private String payNo;

            /**
            * 支付状态，0成功，1失败
            */
    private Integer status;

            /**
            * 支付时间
            */
    private LocalDateTime paymentTime;


}
