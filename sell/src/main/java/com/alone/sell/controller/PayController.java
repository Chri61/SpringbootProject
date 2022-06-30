package com.alone.sell.controller;

import com.alone.sell.common.exception.SellException;
import com.alone.sell.common.enums.ResultEnum;
import com.alone.sell.dto.OrderDTO;
import com.alone.sell.service.IOrderService;
import com.alone.sell.service.IPayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author Chri61
 * @Date 2022/1/10 0010
 */
@Controller
@RequestMapping("pay")
public class PayController {

    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IPayService iPayService;

    @GetMapping("create")
    public ModelAndView create(String orderId, String returnUrl, Map<String, Object> map) {
        //1.查询订单
        OrderDTO orderMasterDTO = iOrderService.findById(orderId);
        if (orderMasterDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //发起支付
        PayResponse payResponse = iPayService.create(orderMasterDTO);
        map.put("payResponse", payResponse);
        //这里的url如果没有Encode，则需要Encode一下
        map.put("returnUrl", returnUrl);

        return new ModelAndView("pay/create", map);
    }

    /**
     * 微信支付异步通知
     *
     * @param notifyData xml格式的字符串
     */
    @PostMapping
    public ModelAndView notify(@RequestBody String notifyData) {
        iPayService.notify(notifyData);
        //返回给微信异步通知处理的结果
        return new ModelAndView("pay/success");
    }

    @GetMapping("ttt")
    public ModelAndView t34(Map<String, Object> map) {
        System.out.println("进来了吗");
        map.put("orderId", "2323io4j23j");
        return new ModelAndView("pay/create", map);
    }

}
