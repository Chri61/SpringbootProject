package com.alone.sell.service.Impl;

import com.alone.sell.common.exception.SellException;
import com.alone.sell.common.enums.ResultEnum;
import com.alone.sell.dto.OrderDTO;
import com.alone.sell.service.IBuyerServicei;
import com.alone.sell.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Chri61
 * @Date 2022/3/17 0017
 */
@Service
@Slf4j
public class BuyerServiceImpl implements IBuyerServicei {

    @Autowired
    private IOrderService iOrderService;

    @Override
    public OrderDTO findOneOrder(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);

    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null) {
            log.error("【查询订单】查不到该订单，openid", openid);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return iOrderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = iOrderService.findById(orderId);
        if (orderDTO == null) {
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("【查询订单】订单的openid不一致，openid={}", openid);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROE);
        }
        return orderDTO;
    }
}
