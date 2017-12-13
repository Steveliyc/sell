package com.lyc.exc.dao;

import com.lyc.exc.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lyc94 on 2017/12/13.
 */
public interface IOrderMasterDao extends JpaRepository<OrderMaster, String>{

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);


}
