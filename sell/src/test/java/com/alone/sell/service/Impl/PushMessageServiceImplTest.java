package com.alone.sell.service.Impl;

import com.alone.sell.dto.OrderDTO;
import com.alone.sell.service.IOrderService;
import com.alone.sell.service.IPushMessageService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class PushMessageServiceImplTest {

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IPushMessageService iPushMessageService;

    @Test
    public void orderStatus() {
        OrderDTO orderDTO = iOrderService.findById("1577784216953705106");

        iPushMessageService.orderStatus(orderDTO);
    }
}