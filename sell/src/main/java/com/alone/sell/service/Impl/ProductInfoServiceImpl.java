package com.alone.sell.service.Impl;

import com.alone.sell.common.exception.SellException;
import com.alone.sell.common.enums.ProductStatusEnum;
import com.alone.sell.common.enums.ResultEnum;
import com.alone.sell.dataobject.ProductInfo;
import com.alone.sell.dto.CartDTO;
import com.alone.sell.repository.ProductInfoRepository;
import com.alone.sell.service.IProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Chri61
 * @Date 2021/12/26 0026
 */
@Service
public class ProductInfoServiceImpl implements IProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo findById(String productId) {
        Optional<ProductInfo> byId = productInfoRepository.findById(productId);
        return byId.orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartProductList) {
        for (CartDTO cartDTO : cartProductList) {
            Optional<ProductInfo> byId = productInfoRepository.findById(cartDTO.getProductId());
            ProductInfo productInfo = byId.orElse(null);
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //增加库存
            int kc = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(kc);
            productInfoRepository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartProductList) {
        for (CartDTO cartDTO : cartProductList) {
            Optional<ProductInfo> byId = productInfoRepository.findById(cartDTO.getProductId());
            ProductInfo productInfo = byId.orElse(null);
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //减库存
            int kc = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (kc < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(kc);
            productInfoRepository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        Optional<ProductInfo> byId = productInfoRepository.findById(productId);
        ProductInfo productInfo = byId.orElse(null);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatus().equals(ProductStatusEnum.UP)) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return productInfoRepository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        Optional<ProductInfo> byId = productInfoRepository.findById(productId);
        ProductInfo productInfo = byId.orElse(null);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatus().equals(ProductStatusEnum.DOWN)) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return productInfoRepository.save(productInfo);
    }
}
