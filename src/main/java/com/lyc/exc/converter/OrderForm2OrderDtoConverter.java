package com.lyc.exc.converter;

import com.alibaba.fastjson.JSONArray;
import com.lyc.exc.dataobject.OrderDetail;
import com.lyc.exc.dto.OrderDTO;
import com.lyc.exc.enums.ResultEnum;
import com.lyc.exc.exception.SellException;
import com.lyc.exc.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by lyc94 on 2017/12/17.
 */
@Slf4j
public class OrderForm2OrderDtoConverter {

    public static OrderDTO convert(OrderForm orderForm) {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenId());
        List<OrderDetail> orderDetailList = null;
        try {
            orderDetailList = JSONArray.parseArray(orderForm.getItems(), OrderDetail.class);
        } catch (Exception e) {
            log.error("【对象转换】错误,String={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
