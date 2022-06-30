package com.alone.sell.form;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Chri61
 * @Date 2022/3/26 0026
 */
@Getter
@Setter
public class ProductForm {

    private String productId;
    //商品名称
    private String productName;
    //商品单价
    private BigDecimal productPrice;
    //库存
    private Integer productStock;
    //描述
    private String productDescription;
    //小图
    private String productIcon;
    //商品种类编号
    private Integer categoryType;
}
