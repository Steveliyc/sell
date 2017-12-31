package com.lyc.exc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lyc.exc.dataobject.OrderDetail;
import com.lyc.exc.enums.OrderStatusEnum;
import com.lyc.exc.enums.PayStatusEnum;
import com.lyc.exc.serializer.Date2LongSerializer;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by lyc94 on 2017/12/13.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    /** 买家微信openId */
    private String buyerOpenid;

    private BigDecimal orderAmount;

    /** 订单状态，默认为新下单: 0 */
    private Integer orderStatus;

    /** 支付状态，默认为: 0 未支付*/
    private Integer payStatus;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
