package com.lyc.exc.service.impl;

import com.lyc.exc.dao.IProductInfoDao;
import com.lyc.exc.dataobject.ProductInfo;
import com.lyc.exc.enums.ProductStatusEnum;
import com.lyc.exc.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lyc94 on 2017/12/10.
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService{

    @Autowired
    private IProductInfoDao iProductInfoDao;

    @Override
    public ProductInfo findOne(String productId) {
        return iProductInfoDao.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return iProductInfoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    /** 分页 */
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return iProductInfoDao.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return iProductInfoDao.save(productInfo);
    }
}
