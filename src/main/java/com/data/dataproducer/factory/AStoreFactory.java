package com.data.dataproducer.factory;

import com.data.dataproducer.entity.*;
import com.data.dataproducer.enums.*;
import com.data.dataproducer.util.UCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author danny
 * @date 2019/5/31 9:50 AM
 */
@Component
public class AStoreFactory {

    @Autowired
    private RandomFactory randomFactory;

    @Autowired
    private List<AArea> areas;

    /**
     * 门店
     * @return
     */
    public AStore produceStore (StoreTypeEnum storeType) {

        AStore.AStoreBuilder builder = AStore.builder();
        builder.contacts(randomFactory.randomFimilyAndName());
        builder.contactNumber(randomFactory.randomMobile());
        builder.storeType(storeType.value());
        builder.comment(storeType.getName());

        switch (storeType) {
            case ON_LINE:
                builder.storeName(randomFactory.randomChinese(5) + "旗舰店");
                break;
            case ON_LINE_FRANCHISED_OUTLET:
                builder.storeName(randomFactory.randomChinese(9) + "店");
                break;
            case OFF_LINE:
                AArea area = areas.get(randomFactory.randomId(areas.size()));
                builder.contry(area.getContry());
                builder.province(area.getProvince());
                builder.city(area.getCity());

                AArea area2 = areas.get(randomFactory.randomId(areas.size()));
                String roadName = area2.getCity().replaceAll("市","").replaceAll("地区", "") + "路";
                builder.address(roadName + randomFactory.randomId(1000) + "号");

                builder.storeName(area.getCity() + roadName + "分店");
                break;
                default:
                    break;
        }

        return builder.build();
    }


    /**
     * 生成店铺审核对象
     * @param store
     * @return
     */
    public AStoreAudit produceStoreAudit (AStore store) {
        AStoreAudit.AStoreAuditBuilder builder = AStoreAudit.builder();
        builder.storeId(store.getStoreId());
        builder.auditPersion(randomFactory.randomFimilyAndName());
        //默认设置为五十分之一门店审核不通过
        boolean auditNotOkay = randomFactory.randomId(50) % 50 == 0;
        if (auditNotOkay) {
            builder.auditResult(StoreAuditEnum.FAILED.value());
            builder.comment(StoreAuditEnum.FAILED.name());
            //审核不通过，将门店状态改为审核不通过
            store.setStatus(StoreStatusEnum.AUDIT_NOT_PASS.value());
        } else {
            builder.auditResult(StoreAuditEnum.SUCCESS.value());
            builder.comment(StoreAuditEnum.SUCCESS.name());
            //审核通过，将状态改为营业中
            store.setStatus(StoreStatusEnum.IN_OPERATION.value());
        }

        return builder.build();
    }
}
