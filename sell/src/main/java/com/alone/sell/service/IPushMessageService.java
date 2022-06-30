package com.alone.sell.service;

import com.alone.sell.dto.OrderDTO;

/**
 * @author Chri61
 * @Date 2022/3/31 0031
 */
public interface IPushMessageService {

    //订单状态变更消息
    void orderStatus(OrderDTO orderDTO);

    //


}
