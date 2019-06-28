package com.data.dataproducer.entity.bo;

import com.data.dataproducer.entity.AOrder;
import com.data.dataproducer.entity.AOrderDetail;
import lombok.Data;

import java.util.List;

/**
 * @author danny
 * @date 2019/5/31 10:59 PM
 */
@Data
public class OrderBO {

    private AOrder order;

    private List<AOrderDetail> orderDetails;
}
