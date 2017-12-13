package com.lyc.exc.dao;

import com.lyc.exc.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lyc94 on 2017/12/10.
 */
public interface IProductInfoDao extends JpaRepository<ProductInfo, String>{

    List<ProductInfo> findByProductStatus(Integer productStatus);
}
