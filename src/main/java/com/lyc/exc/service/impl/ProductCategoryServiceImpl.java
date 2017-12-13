package com.lyc.exc.service.impl;

import com.lyc.exc.dao.IProductCategoryDao;
import com.lyc.exc.dataobject.ProductCategory;
import com.lyc.exc.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目
 * Created by lyc94 on 2017/12/6.
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private IProductCategoryDao iProductCategoryDao;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return iProductCategoryDao.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return iProductCategoryDao.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return iProductCategoryDao.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return iProductCategoryDao.save(productCategory);
    }
}
