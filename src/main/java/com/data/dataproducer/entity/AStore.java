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
* @since 2019-06-05
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Builder
    public class AStore implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "store_id", type = IdType.AUTO)
    private Integer storeId;

            /**
            * 店铺名称
            */
    private String storeName;

            /**
            * 省
            */
    private String province;

            /**
            * 市
            */
    private String city;

            /**
            * 区/县
            */
    private String contry;

    /**
     * 店铺状态。0、待营业，1、营业中，2、已关店
     */
    private Integer status;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String contactNumber;

    /**
     * 店铺类型
     */
    private Integer storeType;

            /**
            * 地址
            */
    private String address;

            /**
            * 备注
            */
    private String comment;

            /**
            * 开店时间
            */
    private LocalDateTime createTime;


}
