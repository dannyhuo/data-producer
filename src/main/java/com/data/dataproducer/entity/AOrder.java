package com.data.dataproducer.entity;

    import java.math.BigDecimal;
    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import java.io.Serializable;
    import java.util.List;

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
    public class AOrder implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

            /**
            * 门店ID，空表示线上订单
            */
    private Integer storeId;

            /**
            * 所属用户ID
            */
    private Long userId;

            /**
            * 销售金额
            */
    private BigDecimal totalAmount;

            /**
            * 折扣金额
            */
    private BigDecimal discountAmount;

            /**
            * 应付金额，销售-折扣
            */
    private BigDecimal dueAmount;

            /**
            * 实付金额
            */
    private BigDecimal actualPayAmount;

            /**
            * 联系人姓名
            */
    private String contactName;

            /**
            * 联系人手机号
            */
    private String contactMobile;

            /**
            * 下单平台， 0：线下门店，1：pc，2：app，3：微信，4：小程序，5：公众号，6：h5，7：wap，8：分销， 9：其它
            */
    private Integer orderPlatform;

            /**
            * 是否支付， 0=未支付，1=已支付
            */
    private Integer isPayed;

            /**
            * 订单状态， 未支付，已支付，已退款
            */
    private Integer status;

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


//    /**
//     * 详单
//     */
//    private List<AOrderDetail> details;
//
//
//    public void setOrderId (Long orderId) {
//        this.orderId = orderId;
//
//        if (null != details) {
//            for (AOrderDetail detail : details) {
//                detail.setOrderId(orderId);
//            }
//        }
//    }


}
