package com.alone.sell.service.Impl;

import com.alone.sell.common.exception.SellException;
import com.alone.sell.common.enums.ResultEnum;
import com.alone.sell.common.utils.JsonUtil;
import com.alone.sell.common.utils.MathUtil;
import com.alone.sell.dto.OrderDTO;
import com.alone.sell.service.IOrderService;
import com.alone.sell.service.IPayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Chri61
 * @Date 2022/1/10 0010
 */
@Service
@Slf4j
public class PayServiceImpl implements IPayService {

    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private IOrderService iOrderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        //发起支付
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName("微信点餐订单");
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信支付】发起支付 request={}", JsonUtil.objToJson(payRequest));
        PayResponse pay = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付 response={}", JsonUtil.objToJson(pay));

        return pay;
    }

    @Override
    @Transactional
    public void notify(String notifyData) {
        //1.验证签名
        //2.验证支付的状态
        //3.验证支付金额
        //4.验证支付人（下单人）
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知，payResponse={}", JsonUtil.objToJson(payResponse));

        String orderId = payResponse.getOrderId();
        //查询订单
        OrderDTO orderDTO = iOrderService.findById(orderId);
        if (orderDTO == null) {
            log.error("【微信支付】异步通知，订单不存在 orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断金额是否一致（0.1  和   0.10 有时也会不一样）
        if (!MathUtil.equals(orderDTO.getOrderAmount().doubleValue(), payResponse.getOrderAmount())) {
            log.error("【微信支付】异步通知，订单金额不一致 orderId={}，微信通知金额={}，系统金额={}",
                    orderId, payResponse.getOrderAmount(), orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //修改订单支付状态
        iOrderService.paid(orderDTO);
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】退款 request={}", JsonUtil.objToJson(refundRequest));
        RefundResponse response = bestPayService.refund(refundRequest);
        log.info("【微信支付】退款 response={}", JsonUtil.objToJson(response));
        return response;
    }
}
