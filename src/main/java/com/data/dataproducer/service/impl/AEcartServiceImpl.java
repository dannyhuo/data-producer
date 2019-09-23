package com.data.dataproducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.data.dataproducer.entity.AEcart;
import com.data.dataproducer.entity.AProduct;
import com.data.dataproducer.factory.RandomFactory;
import com.data.dataproducer.mapper.AEcartMapper;
import com.data.dataproducer.service.IAEcartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.data.dataproducer.service.IAProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author danny
 * @since 2019-06-28
 */
@Service
public class AEcartServiceImpl extends ServiceImpl<AEcartMapper, AEcart> implements IAEcartService {

    @Autowired
    private RandomFactory randomFactory;

    @Autowired
    private IAProductService iaProductService;

    @Autowired
    private IAEcartService iaEcartService;

    /**
     * 加入购物车
     * @return
     */
    @Override
    public AEcart addEcart (Integer productId, Long userId, int quantity) {
        if (null == productId) {
            throw new IllegalArgumentException("Product id is null");
        }
        AProduct product = iaProductService.getById(productId);

        QueryWrapper<AEcart> wrapper = new QueryWrapper();
        wrapper.eq("product_id", product);
        List<AEcart> productEcart = iaEcartService.list(wrapper);
        AEcart eCart;
        if (null == productEcart || productEcart.size() < 1) {
            AEcart.AEcartBuilder builder = AEcart.builder();
            builder.productId(product.getProductId());
            builder.categoryCode(product.getCategoryCode());
            builder.marketingPrice(product.getMarketingPrice());
            builder.sellPrice(product.getSellPrice());
            builder.storeId(product.getStoreId());
            builder.userId(userId);
            builder.quntity(quantity);
            eCart = builder.build();
        } else {
            eCart = productEcart.get(0);
            eCart.setCategoryCode(product.getCategoryCode());
            eCart.setMarketingPrice(product.getMarketingPrice());
            eCart.setSellPrice(product.getSellPrice());
            eCart.setStoreId(product.getStoreId());
            if (null == eCart.getQuntity()) {
                eCart.setQuntity(quantity);
            } else {
                eCart.setQuntity(eCart.getQuntity() + quantity);
            }
        }

        iaEcartService.saveOrUpdate(eCart);

        return eCart;
    }

}
