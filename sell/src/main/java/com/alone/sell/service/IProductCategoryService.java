package com.alone.sell.service;

import com.alone.sell.dataobject.ProductCategory;

import java.util.List;

public interface IProductCategoryService {

    //通过id查找
    ProductCategory findById(Integer categoryId);

    //查询所有
    List<ProductCategory> findAll();

    //客户端查询商品
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTyleList);

    ProductCategory save(ProductCategory productCategory);

}
