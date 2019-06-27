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
    public class AProduct implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "product_id", type = IdType.AUTO)
    private Integer productId;

            /**
            * 产品名称
            */
    private String productName;

    /**
     * 所属门店ID
     */
    private Integer storeId;

            /**
            * 销售价
            */
    private BigDecimal sellPrice;

            /**
            * 市场价
            */
    private BigDecimal marketingPrice;

            /**
            * 产品品类
            */
    private String categoryCode;

            /**
            * 状态，0：待审核， 1：审核未通过， 2：审核通过， 3：已上架， 4：已下架
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


}
