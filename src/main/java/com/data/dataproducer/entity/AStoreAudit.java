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
    public class AStoreAudit implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * key
            */
            @TableId(value = "store_audit_id", type = IdType.AUTO)
    private Integer storeAuditId;

            /**
            * 店铺名称
            */
    private Integer storeId;

            /**
            * 审核结果
            */
    private Integer auditResult;

            /**
            * 审核人
            */
    private String auditPersion;

            /**
            * 审核人职位
            */
    private String position;

            /**
            * 备注
            */
    private String comment;

            /**
            * 审核时间
            */
    private LocalDateTime auditTime;


}
