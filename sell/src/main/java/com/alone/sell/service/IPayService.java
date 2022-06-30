package com.alone.sell.service;

import com.alone.sell.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

public interface IPayService {

    //支付
    PayResponse create(OrderDTO OrderDTO);

    //异步通知
    void notify(String notifyData);

    //退款
    RefundResponse refund(OrderDTO orderDTO);
}
