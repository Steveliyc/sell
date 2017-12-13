package com.lyc.exc.dataobject;

import com.lyc.exc.enums.OrderStatusEnum;
import com.lyc.exc.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lyc94 on 2017/12/13.
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    /** 买家微信openId */
    private String buyerOpenid;

    private BigDecimal orderAmount;

    /** 订单状态，默认为新下单: 0 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态，默认为: 0 未支付*/
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    private Date createTime;

    private Date updateTime;
}
