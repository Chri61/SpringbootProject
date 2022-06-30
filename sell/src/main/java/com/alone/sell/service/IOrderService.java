package com.alone.sell.service;

import com.alone.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Chri61
 * @Date 2021/12/30 0030
 */
public interface IOrderService {

    //创建订单
    OrderDTO create(OrderDTO OrderDTO);

    //查询单个订单
    OrderDTO findById(String orderId);

    //根据买家查询订单列表
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    //取消订单
    OrderDTO cancel(OrderDTO OrderDTO);

    //完结订单
    OrderDTO finish(OrderDTO OrderDTO);

    //支付订单
    OrderDTO paid(OrderDTO OrderDTO);


    //查询所有订单列表
    Page<OrderDTO> findList(Pageable pageable);
}
