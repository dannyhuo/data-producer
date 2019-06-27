package com.data.dataproducer.entity;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;
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
    public class AArea implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "area_id", type = IdType.AUTO)
    private Long areaId;

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
            * 行政区代码
            */
    private Integer bm;

            /**
            * 备注
            */
    private String comment;


}
