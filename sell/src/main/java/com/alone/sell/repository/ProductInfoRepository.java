package com.alone.sell.repository;

import com.alone.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    //通过商品的状态查询
    List<ProductInfo> findByProductStatus(Integer productStatus);

}