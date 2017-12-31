package com.lyc.exc.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by lyc94 on 2017/12/17.
 */
@Data
public class OrderForm {
    @NotEmpty(message = "姓名必填")
    private String name;

    @NotEmpty(message = "手机号必填")
    private String phone;

    @NotEmpty(message = "地址必填")
    private String address;

    @NotEmpty(message = "openId必填")
    private String openId;

    @NotEmpty(message = "购物车不能为空")
    private String items;
}
