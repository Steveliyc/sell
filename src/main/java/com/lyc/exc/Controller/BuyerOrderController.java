package com.lyc.exc.Controller;

import com.lyc.exc.converter.OrderForm2OrderDtoConverter;
import com.lyc.exc.dto.OrderDTO;
import com.lyc.exc.enums.ResultEnum;
import com.lyc.exc.exception.SellException;
import com.lyc.exc.form.OrderForm;
import com.lyc.exc.service.BuyerService;
import com.lyc.exc.service.OrderService;
import com.lyc.exc.utils.ResultVOUtil;
import com.lyc.exc.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyc94 on 2017/12/17.
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;
    /**
     * 创建订单
     * @param orderForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确，orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDtoConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO result = orderService.create(orderDTO);
        Map<String, String> obj = new HashMap<>();
        obj.put("orderId", result.getOrderId());
        return ResultVOUtil.success(obj);
    }

    /**
     * 订单列表
     * @param openId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openId") String openId,
                                         @RequestParam(value = "page", defaultValue = "0")Integer page,
                                         @RequestParam(value = "size", defaultValue = "10")Integer size) {
        if(StringUtils.isEmpty(openId)) {
            log.error("【订单列表】openId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderDTO> resultPage = orderService.findList(openId, pageRequest);
        return ResultVOUtil.success(resultPage.getContent());
    }

    /**
     * 订单详情
     * @param openId
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openId")String openId,
                                        @RequestParam("orderId")String orderId) {
        if(StringUtils.isEmpty(openId) || StringUtils.isEmpty(orderId)) {
            log.error("【订单详情】openId或者orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = buyerService.findOrderOne(openId, orderId);
        return ResultVOUtil.success(orderDTO);
    }

    /**
     * 取消订单
     * @param openId
     * @param orderId
     * @return
     */
    @PostMapping("/cancle")
    public ResultVO<String> cancle(@RequestParam("openId")String openId,
                                   @RequestParam("orderId")String orderId) {
        if(StringUtils.isEmpty(openId) || StringUtils.isEmpty(orderId)) {
            log.error("【订单取消】openId或者orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        buyerService.cacnleOrder(openId, orderId);
        return ResultVOUtil.success();
    }
}
