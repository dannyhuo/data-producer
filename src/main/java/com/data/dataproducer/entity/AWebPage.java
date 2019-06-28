package com.data.dataproducer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

    /**
    * <p>
    * 
    * </p>
    *
    * @author danny
     *
    * @since 2019-06-28
     *
    */
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Builder
    public class AWebPage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * key
    */
    private Integer pageType;

        /**
         * page url 格式
         */
    private String pageUrl;

        /**
         * page 页名称
         */
    private String pageName;

}
