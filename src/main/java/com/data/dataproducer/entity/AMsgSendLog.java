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
    public class AMsgSendLog implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "send_log_id", type = IdType.AUTO)
    private Long sendLogId;

            /**
            * 模板名称
            */
    private String sendUserId;

            /**
            * 接收方，短信为手机号，邮件为邮箱等
            */
    private String receiver;

            /**
            * 模板消息内容
            */
    private String tmplateId;

            /**
            * 模板消息中占位符，占位符会在发送的时候用对应的参数替换【冗余】
            */
    private String placeholders;

            /**
            * 模板消息中的参数值
            */
    private String args;

            /**
            * 发送内容
            */
    private String content;

            /**
            * 消息类型 0 短信， 1、微信， 2 app push, 3、邮件
            */
    private Integer msgType;

            /**
            * 用券时间
            */
    private LocalDateTime sendTime;

    /**
     *  发送状态 0 失败， 1、成功
     */
    private Integer sendStatus;


}
