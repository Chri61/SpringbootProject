package com.alone.sell.converter;

import com.alone.sell.dataobject.OrderMaster;
import com.alone.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Chri61
 * @Date 2022/1/7 0007
 */
public class OrderMasterToOrderDTOConverter {

    public static OrderDTO converter(OrderMaster orderMaster) {
        OrderDTO OrderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, OrderDTO);
        return OrderDTO;
    }

    public static List<OrderDTO> converter(List<OrderMaster> list) {
        return list.stream().map(e ->
                converter(e)
        ).collect(Collectors.toList());
    }


}
