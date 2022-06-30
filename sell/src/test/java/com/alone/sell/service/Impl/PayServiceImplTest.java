package com.alone.sell.service.Impl;

import com.alone.sell.common.exception.SellException;
import com.alone.sell.common.enums.ResultEnum;
import com.alone.sell.dto.OrderDTO;
import com.alone.sell.service.IOrderService;
import com.alone.sell.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class PayServiceImplTest {

    @Autowired
    private IOrderService iOrderMasterService;

    @Autowired
    private IPayService iPayService;

    @Test
    void create() {

        //1.查询订单
        OrderDTO orderMasterDTO = iOrderMasterService.findById("1578483350903148619");
        if (orderMasterDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //发起支付
        iPayService.create(orderMasterDTO);
    }
}