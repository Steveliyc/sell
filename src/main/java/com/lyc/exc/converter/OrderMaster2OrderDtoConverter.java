package com.lyc.exc.converter;

import com.lyc.exc.dataobject.OrderMaster;
import com.lyc.exc.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyc94 on 2017/12/17.
 */
public class OrderMaster2OrderDtoConverter {

    public static OrderDTO convert(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
        List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>();
        for(OrderMaster orderMaster : orderMasterList) {
            orderDTOs.add(convert(orderMaster));
        }
        return orderDTOs;
    }
}
