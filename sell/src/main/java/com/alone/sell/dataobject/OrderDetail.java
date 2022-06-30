package com.alone.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author Chri61
 * @Date 2021/12/30 0030
 */
@Entity
@Data
public class OrderDetail {

    @Id
    private String detailId;
    //订单id
    private String orderId;
    //商品id
    private String productId;
    private String productName;
    //单价
    private BigDecimal productPrice;
    //商品数量
    private Integer productQuantity;
    //商品小图
    private String productIcon;


}
