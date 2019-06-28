package com.data.dataproducer.entity;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author danny
* @since 2019-06-28
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class AMsgTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "template_id", type = IdType.AUTO)
    private Long templateId;

            /**
            * 模板名称
            */
    private String templateName;

            /**
            * 模板消息内容
            */
    private String content;

            /**
            * 模板消息类型 0 短信， 1、微信， 2 app push, 3、邮件
            */
    private Integer templateType;

            /**
            * 模板消息中占位符，占位符会在发送的时候用对应的参数替换
            */
    private String placeholders;

            /**
            * 用券时间
            */
    private LocalDateTime createTime;

            /**
            * 更新时间
            */
    private LocalDateTime updateTime;


}
