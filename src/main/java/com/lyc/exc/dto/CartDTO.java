package com.lyc.exc.dto;

import lombok.Data;

/**
 * 购物车
 * Created by lyc94 on 2017/12/17.
 */
@Data
public class CartDTO {

    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
