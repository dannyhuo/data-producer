package com.data.dataproducer.entity;

import jdk.internal.dynalink.linker.LinkerServices;
import lombok.Data;

import java.util.List;

/**
 * @author danny
 * @date 2019/5/31 10:59 PM
 */
@Data
public class AutoOrder {

    private AOrder order;

    private List<AOrderDetail> orderDetails;
}
