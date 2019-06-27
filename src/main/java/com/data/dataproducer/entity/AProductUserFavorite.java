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
    public class AProductUserFavorite implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "product_user_favorite_id", type = IdType.AUTO)
    private Long productUserFavoriteId;

            /**
            * 用户ID
            */
    private Long userId;

            /**
            * 产品ID
            */
    private Long productId;

            /**
            * 产品ID
            */
    private String productName;

            /**
            * 0:收藏，1：取消收藏
            */
    private Integer action;

            /**
            * 收藏时间
            */
    private LocalDateTime favoriteTime;

            /**
            * 取消收藏时间
            */
    private LocalDateTime unFavoriteTime;


}
