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
    public class AActivity implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "activity_id", type = IdType.AUTO)
    private Long activityId;

            /**
            * 活动名称
            */
    private String activityName;

            /**
            * 人群ID
            */
    private Long audienceId;

            /**
            * 创建人ID
            */
    private Long createUserId;

            /**
            * 创建人
            */
    private String createUserName;

            /**
            * 审批人ID
            */
    private Long approveUserId;

            /**
            * 审批人
            */
    private String approveUserName;

            /**
            * 模板消息ID
            */
    private Integer msgTemplateId;

            /**
            * 优惠券ID
            */
    private Long couponId;

            /**
            * 优惠券名称
            */
    private Long couponName;

            /**
            * 模板消息名称
            */
    private String templateName;

            /**
            * 模板消息类型 0 短信， 1、微信， 2 app push, 3、邮件
            */
    private Integer templateType;

            /**
            * 审批状态 0 未审核， 1 通过， 2 不通过
            */
    private Integer approveStatus;

            /**
            * 审批备注
            */
    private String approveComment;

            /**
            * 活动有效期，起始时间
            */
    private LocalDateTime startTime;

            /**
            * 活动有效期，截止时间
            */
    private LocalDateTime endTime;

            /**
            * 创建时间
            */
    private LocalDateTime createTime;

            /**
            * 审核时间
            */
    private LocalDateTime approveTime;

            /**
            * 是否删除， 0=未删除，1=已删除
            */
    private Integer isDeleted;


}
