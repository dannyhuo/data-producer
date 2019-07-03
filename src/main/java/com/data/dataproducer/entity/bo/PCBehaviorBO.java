package com.data.dataproducer.entity.bo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author danny
 * @date 2019/7/1 5:01 PM
 */
@Data
@Builder
public class PCBehaviorBO {

    private String userId;
    private String userName;
    private String cookieId;
    private LocalDateTime time;
    private String url;
    private String title;

    private Long orderId;
    private Long paymentId;
    private Long refundId;
    private Integer quntity;
    /**
     * 事件
     */
    private String event;

    private Long productId;
    private String productName;
    private String keyWords;


}
