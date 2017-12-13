package com.lyc.exc.dao;

import com.lyc.exc.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lyc94 on 2017/12/13.
 */
public interface IOrderDetailDao extends JpaRepository<OrderDetail,String>{

    List<OrderDetail> findByOrderId(String orderId);
}
