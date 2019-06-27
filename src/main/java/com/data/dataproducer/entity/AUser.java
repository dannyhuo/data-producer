package com.data.dataproducer.entity;

    import com.baomidou.mybatisplus.annotation.IdType;
    import java.time.LocalDate;
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
* @since 2019-05-31
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Builder
    public class AUser implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

            /**
            * 用户名
            */
    private String userName;

            /**
            * 密码
            */
    private String password;

            /**
            * 真实姓名
            */
    private String realName;

            /**
            * M/F
            */
    private String gender;

            /**
            * 生日
            */
    private LocalDate birthday;

            /**
            * 电话
            */
    private String phone;

            /**
            * 邮箱
            */
    private String email;

            /**
            * 微信openid
            */
    private String openId;

            /**
            * union id
            */
    private String unionId;

            /**
            * card id, 身份证号
            */
    private String cardId;

            /**
            * 会员等级，0:普通会员，1:白银会员，2:黄金会员，3:白金会员，4:钻石会员
            */
    private Integer memberLevel;

            /**
            * 会员成长值
            */
    private Integer growthValue;

            /**
            * 职业
            */
    private String profession;

            /**
            * 爱好
            */
    private String hobby;

            /**
            * 区县
            */
    private String county;

            /**
            * 城市
            */
    private String city;

            /**
            * 省份
            */
    private String provice;

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
