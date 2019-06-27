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
* @since 2019-05-30
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Builder
    public class AUserLoginDetail implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "user_login_id", type = IdType.AUTO)
    private Long userLoginId;

            /**
            * 用户ID
            */
    private Long userId;

            /**
            * 0：登录成功，1：密码错误，2：验证码错误
            */
    private Integer loginStatus;

            /**
            * 0：app登录，1：pc登录，2：h5登录，3：小程序登录，4：微信公众号登录
            */
    private Integer loginPlatform;

            /**
            * 登录设备IP地址
            */
    private String ipAddress;

            /**
            * 登录ID
            */
    private String diviceId;

            /**
            * 浏览器名称
            */
    private String browser;

            /**
            * 用券时间
            */
    private LocalDateTime loginTime;


}
