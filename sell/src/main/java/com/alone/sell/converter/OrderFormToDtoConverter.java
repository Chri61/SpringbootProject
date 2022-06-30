package com.alone.sell.converter;

import com.alone.sell.common.exception.SellException;
import com.alone.sell.common.enums.ResultEnum;
import com.alone.sell.dataobject.OrderDetail;
import com.alone.sell.dto.OrderDTO;
import com.alone.sell.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 表单转dto
 *
 * @author Chri61
 * @Date 2022/1/7 0007
 */
@Slf4j
public class OrderFormToDtoConverter {

    public static OrderDTO converter(OrderForm form) {

        OrderDTO OrderDTO = new OrderDTO();
        OrderDTO.setBuyerName(form.getName());
        OrderDTO.setBuyerAddress(form.getAddress());
        OrderDTO.setBuyerPhone(form.getPhone());
        OrderDTO.setBuyerOpenid(form.getOpenid());

        Gson gson = new Gson();
        List<OrderDetail> orderDetailList = new ArrayList<>();

        try {
            orderDetailList = gson.fromJson(form.getItems(),
                    new TypeToken<List<OrderDetail>>() {}.getType());
        } catch (JsonSyntaxException e) {
            log.error("【对象转换】错误，string={}", form.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO.setOrderDetailList(orderDetailList);
        return OrderDTO;
    }

}
