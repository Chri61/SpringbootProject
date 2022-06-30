package com.alone.sell.service.Impl;

import com.alone.sell.dataobject.ProductCategory;
import com.alone.sell.repository.ProductCategoryRepository;
import com.alone.sell.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Chri61
 * @Date 2021/12/26 0026
 */
@Service
public class ProductCategoryServiceImpl implements IProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryDao;

    @Override
    public ProductCategory findById(Integer categoryId) {
        Optional<ProductCategory> byId = productCategoryDao.findById(categoryId);
        return byId.orElse(null);
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryDao.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTyleList) {
        return productCategoryDao.findByCategoryTypeIn(categoryTyleList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryDao.save(productCategory);
    }

}
