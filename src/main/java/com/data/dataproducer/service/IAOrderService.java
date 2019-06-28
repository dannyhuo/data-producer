package com.data.dataproducer.service;

import com.data.dataproducer.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;
import com.data.dataproducer.entity.bo.OrderBO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author danny
 * @since 2019-05-30
 */
public interface IAOrderService extends IService<AOrder> {

    void order (OrderBO order);

    boolean pay (AOrder order, AOrderPayment payment);

    boolean refund (AOrderPayment payment, AOrderRefund refund);

}
