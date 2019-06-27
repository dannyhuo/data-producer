package com.data.dataproducer.factory;

import com.data.dataproducer.entity.AProduct;
import com.data.dataproducer.entity.AProductAudit;
import com.data.dataproducer.entity.AProductCategory;
import com.data.dataproducer.entity.AProductSoldout;
import com.data.dataproducer.enums.CategoryLevelEnum;
import com.data.dataproducer.enums.ProductStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author danny
 * @date 2019/5/31 9:50 AM
 */
@Component
public class AProductFactory {

    @Autowired
    private RandomFactory randomFactory;

    @Autowired
    private List<AProductCategory> productCategory;

    /**
     * 售价与市场价的下浮边界
     */
    private BigDecimal lDiscount = BigDecimal.valueOf(0.5);
    /**
     * 售价与市场价的上浮边界
     */
    private BigDecimal hDiscount = BigDecimal.valueOf(1.1);

    /**
     * 生成产品,新创建产品，默认待审核
     * @return
     */
    public AProduct produceProduct () {
        AProduct.AProductBuilder builder = AProduct.builder();
        BigDecimal mktPrice = randomFactory.randomProductPrice();
        builder.marketingPrice(mktPrice);
        BigDecimal sellPrice = randomFactory.randomProductPrice(mktPrice.multiply(lDiscount), mktPrice.multiply(hDiscount));
        builder.sellPrice(sellPrice);

        AProductCategory category = productCategory.get(randomFactory.randomId(productCategory.size()));
        if (category.getCategoryLevel() == CategoryLevelEnum.LEVEL_THREE.value()) {
            builder.categoryCode(category.getCategoryCode());
        }

        builder.productName(randomFactory.randomProductName() + category.getCategoryName());
        return builder.build();
    }

    /**
     * 生成产品审核记录对象
     * @param product
     * @param auditStatus
     * @return
     */
    public AProductAudit productProductAudit (AProduct product, ProductStatusEnum auditStatus) {
        AProductAudit.AProductAuditBuilder builder = AProductAudit.builder();
        builder.productId(product.getProductId());
        builder.auditPersion(randomFactory.randomFimilyAndName());
        builder.auditResult(auditStatus.value());
        builder.coment(auditStatus.getName());
        return builder.build();
    }

    /**
     * 生成产品下架记录
     * @param product
     * @return
     */
    public AProductSoldout productProductSoldout (AProduct product) {
        AProductSoldout.AProductSoldoutBuilder builder = AProductSoldout.builder();
        String soldoutPersion = randomFactory.randomFimilyAndName();
        builder.soldoutPersion(soldoutPersion);
        builder.productId(product.getProductId());
        builder.coment(soldoutPersion + "已将【" + product.getProductName() + "】下架");
        return builder.build();
    }

}
