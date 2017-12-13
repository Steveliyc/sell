package com.lyc.exc.service;

import com.lyc.exc.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品信息
 * Created by lyc94 on 2017/12/10.
 */
public interface ProductInfoService {
    ProductInfo findOne(String productId);

    /** 查询所有在售商品 */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存

    //减库存

}
