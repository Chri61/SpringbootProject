package com.alone.sell.repository;

import com.alone.sell.common.utils.KeyUtil;
import com.alone.sell.dataobject.OrderMaster;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class OrderMasterRepositoryTest {

    @Test
    void ter3454(){
        System.out.println(1231);
    }

    @Autowired
    private OrderMasterRepository orderMasterRepository;
    private final String OPEN_ID = "110110";

    @Test
    void save() {
        OrderMaster orderMaster = new OrderMaster();
        String orderId = KeyUtil.getUniqueKey();
        orderMaster.setOrderId(orderId);
        orderMaster.setBuyerName("他如千瓦时群所群");
        orderMaster.setBuyerPhone("123456789123");
        orderMaster.setBuyerAddress("廻我好柔荑花");
        orderMaster.setBuyerOpenid(OPEN_ID);
        orderMaster.setOrderAmount(new BigDecimal(0.01));
        orderMasterRepository.save(orderMaster);
    }

    @Test
    void findByBuyerOpenid() {
        PageRequest pageRequest = PageRequest.of(1, 3);
        Page<OrderMaster> byBuyerOpenid = orderMasterRepository.findByBuyerOpenid(OPEN_ID, pageRequest);
        System.out.println(byBuyerOpenid.getTotalElements());
        log.info("{}", byBuyerOpenid);
    }
}