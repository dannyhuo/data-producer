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
* @since 2019-06-04
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Builder
    public class AProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;

            /**
            * 产品品类名称
            */
    private String categoryName;

            /**
            * 产品品类编码
            */
    private String categoryCode;

            /**
            * 产品品类父编码
            */
    private String parentCategoryCode;

            /**
            * 产品品类级别，1级，2级，3级
            */
    private Integer categoryLevel;

            /**
            * 创建时间
            */
    private LocalDateTime createTime;


}
