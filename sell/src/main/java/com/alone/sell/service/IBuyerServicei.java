package com.alone.sell.service;

import com.alone.sell.dto.OrderDTO;

public interface IBuyerServicei {

    OrderDTO findOneOrder(String openid, String orderId);

    OrderDTO cancelOrder(String openid, String orderId);
}
