package com.lyc.exc.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.xml.internal.ws.api.PropertySet;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by lyc94 on 2017/12/10.
 */
@Data
public class ProductInfoVO {

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;

}
