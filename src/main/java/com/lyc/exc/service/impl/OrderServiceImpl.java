package com.lyc.exc.service.impl;

import com.lyc.exc.converter.OrderMaster2OrderDtoConverter;
import com.lyc.exc.dao.IOrderDetailDao;
import com.lyc.exc.dao.IOrderMasterDao;
import com.lyc.exc.dao.IProductInfoDao;
import com.lyc.exc.dataobject.OrderDetail;
import com.lyc.exc.dataobject.OrderMaster;
import com.lyc.exc.dataobject.ProductInfo;
import com.lyc.exc.dto.CartDTO;
import com.lyc.exc.dto.OrderDTO;
import com.lyc.exc.enums.OrderStatusEnum;
import com.lyc.exc.enums.PayStatusEnum;
import com.lyc.exc.enums.ResultEnum;
import com.lyc.exc.exception.SellException;
import com.lyc.exc.service.OrderService;
import com.lyc.exc.service.PayService;
import com.lyc.exc.service.ProductInfoService;
import com.lyc.exc.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyc94 on 2017/12/13.
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private IOrderMasterDao iOrderMasterDao;
    @Autowired
    private IOrderDetailDao iOrderDetailDao;
    @Autowired
    private PayService payService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();

        BigDecimal orderAmount = new BigDecimal("0");
        List<CartDTO> cartDTOList = new ArrayList<CartDTO>();
        //1.查询商品
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算总价
            orderAmount = productInfo.getProductPrice().
                    multiply(new BigDecimal(orderDetail.getProductQuantity())).
                    add(orderAmount);

            //3.写入数据库(OrderDetail)
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            iOrderDetailDao.save(orderDetail);

            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);
        }
        //写入数据库(OrderMaster)
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        iOrderMasterDao.save(orderMaster);
        //4.减少库存
        productInfoService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = iOrderMasterDao.findOne(orderId);
        if(orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = iOrderDetailDao.findByOrderId(orderMaster.getOrderId());
        if(CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> page = iOrderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDtoConverter.convert(page.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList, pageable, page.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancle(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态不正确，orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCLE.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster orderMasterResult = iOrderMasterDao.save(orderMaster);
        if(orderMasterResult == null) {
            log.error("【取消订单】更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        //返还库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情，orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_STOCK_EMPTY);
        }
        List<CartDTO> cartDTOList = new ArrayList<CartDTO>();
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        for(OrderDetail orderDetail : orderDetailList) {
            cartDTOList.add(new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity()));
        }
        productInfoService.increaseStock(cartDTOList);
        //已支付，需要退款
        if(orderDTO.getOrderStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //TODO
            payService.refund(orderDTO);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确，orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster orderMasterResult = iOrderMasterDao.save(orderMaster);
        if(orderMasterResult == null) {
            log.error("【完结订单】更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付】订单状态不正确，orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付】订单支付状态不正确，orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster orderMasterResult = iOrderMasterDao.save(orderMaster);
        if(orderMasterResult == null) {
            log.error("【订单支付】更新支付状态失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }
}
