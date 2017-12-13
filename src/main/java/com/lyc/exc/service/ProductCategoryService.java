package com.lyc.exc.service;

import com.lyc.exc.dataobject.ProductCategory;

import java.util.List;

/**
 * Created by lyc94 on 2017/12/6.
 */
public interface ProductCategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
