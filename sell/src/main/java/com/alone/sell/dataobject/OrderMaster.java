package com.alone.sell.dataobject;

import com.alone.sell.common.enums.OrderStatusEnum;
import com.alone.sell.common.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Chri61
 * @Date 2021/12/30 0030
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    //订单号
    @Id
    private String orderId;
    //买家名字
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    //总金额
    private BigDecimal orderAmount;
    //订单状态，默认为0
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    //支付状态，默认是等待支付0
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    private Date createTime;
    private Date updateTime;
}
