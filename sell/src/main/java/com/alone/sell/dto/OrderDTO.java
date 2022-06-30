package com.alone.sell.dto;

import com.alone.sell.common.enums.OrderStatusEnum;
import com.alone.sell.common.enums.PayStatusEnum;
import com.alone.sell.common.utils.EnumUtil;
import com.alone.sell.common.utils.serializer.DateToLongSerializer;
import com.alone.sell.dataobject.OrderDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Chri61
 * @Date 2021/12/30 0030
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    //订单号
    private String orderId;
    //买家名字
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    //总金额
    private BigDecimal orderAmount;
    //订单状态
    private Integer orderStatus;
    //支付状态
    private Integer payStatus;

    @JsonSerialize(using = DateToLongSerializer.class)
    private Date updateTime;
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date createTime;

    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
