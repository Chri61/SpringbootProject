package com.alone.sell.controller;

import com.alone.sell.vo.ResultVO;
import com.alone.sell.common.exception.SellException;
import com.alone.sell.common.enums.ResultEnum;
import com.alone.sell.converter.OrderFormToDtoConverter;
import com.alone.sell.dto.OrderDTO;
import com.alone.sell.form.OrderForm;
import com.alone.sell.service.IBuyerServicei;
import com.alone.sell.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chri61
 * @Date 2022/1/7 0007
 */
@Slf4j
@RestController
@RequestMapping("buyer/order")
public class BuyerOrderController {

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IBuyerServicei iBuyerServicei;

    /**
     * 创建订单
     */
    @RequestMapping("create")
    public ResultVO create(@Valid OrderForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确");
            //抛出异常后，返回具体的参数
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO dto = OrderFormToDtoConverter.converter(form);
        //判断购物车是否为空
        if (CollectionUtils.isEmpty(dto.getOrderDetailList())) {
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO OrderDTO = iOrderService.create(dto);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("orderId", OrderDTO.getOrderId());
        return ResultVO.success("成功", resultMap);
    }

    //订单列表
    @RequestMapping("list")
    public ResultVO list(String openid,
                         @RequestParam(defaultValue = "0") Integer page,
                         @RequestParam(defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<OrderDTO> list = iOrderService.findList(openid, pageRequest);
        return ResultVO.success("成功", list);
    }

    //订单详情
    @RequestMapping("detail")
    public ResultVO detail(String openid, String orderId) {
        OrderDTO oneOrder = iBuyerServicei.findOneOrder(openid, orderId);
        return ResultVO.success(oneOrder);
    }

    //取消订单
    @RequestMapping("cancel")
    public ResultVO cancel(String openid, String orderId) {
        iBuyerServicei.cancelOrder(openid, orderId);
        return ResultVO.success();
    }
}
