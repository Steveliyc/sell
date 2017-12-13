package com.lyc.exc.dao;

import com.lyc.exc.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lyc94 on 2017/12/4.
 */
public interface IProductCategoryDao extends JpaRepository<ProductCategory, Integer>{
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
