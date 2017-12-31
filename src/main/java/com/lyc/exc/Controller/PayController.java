package com.lyc.exc.Controller;

import com.lly835.bestpay.model.PayResponse;
import com.lyc.exc.dto.OrderDTO;
import com.lyc.exc.enums.ResultEnum;
import com.lyc.exc.exception.SellException;
import com.lyc.exc.service.OrderService;
import com.lyc.exc.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by lyc94 on 2017/12/25.
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId")String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) {
        //查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //发起支付
        PayResponse payResponse = payService.create(orderDTO);
        map.put("payResponse", payResponse);
        map.put("returnUrl",returnUrl);

        return new ModelAndView("pay/create", map);
    }

    /**
     * 微信异步通知
     */
    @PostMapping("/notify")
    public ModelAndView notifyUrl(@RequestBody String notifyData) {
        payService.notify(notifyData);

        //返回给微信返回结果，不然微信异步通知会一直调用此方法
        return new ModelAndView("pay/success");
    }
}
