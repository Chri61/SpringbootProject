package com.alone.sell.mybatis;

import com.alone.sell.dataobject.mapper.ProductCategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * If you need to start a full running server, we recommend that you use random ports.
 * If you use @SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT), an available
 * port is picked at random each time your test runs.
 *
 * 我们在测试使用 websocket的时候需要启动一个完整的服务器，而使用这个注解就是说每次测
 * 试都会选用一个随即可用的端口模拟启动一个完整的服务器，此时问题完美解决!!
 *
 * @author Chri61
 * @Date 2022/4/1 0001
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategroyTest {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Test
    public void ye34543(){
        Map<String, Object> map = new HashMap<>();
        map.put("category_parent_id", 1);
        map.put("category_name", "不好用");
        map.put("category_type", 13);
        productCategoryMapper.insert(map);
    }

}
