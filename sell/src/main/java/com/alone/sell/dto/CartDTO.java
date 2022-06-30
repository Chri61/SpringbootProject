package com.alone.sell.dto;

import lombok.Data;

/**
 * @author Chri61
 * @Date 2021/12/31 0031
 */
@Data
public class CartDTO {
    //商品id
    private String productId;
    //商品数量
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
