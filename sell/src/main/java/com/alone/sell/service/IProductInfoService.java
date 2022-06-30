package com.alone.sell.service;

import com.alone.sell.dataobject.ProductInfo;
import com.alone.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IProductInfoService {

    //根据商品id查询
    ProductInfo findById(String productId);

    //查询在架商品的列表
    List<ProductInfo> findUpAll();

    //管理端，查询所有商品
    Page<ProductInfo> findAll(Pageable pageable);

    //更新与保存
    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartProductList);

    //减库存
    void decreaseStock(List<CartDTO> cartProductList);

    //上架
    ProductInfo onSale(String productId);

    //下架
    ProductInfo offSale(String productId);
}
