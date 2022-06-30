package com.alone.sell.controller;

import com.alone.sell.common.exception.ResponseBankException;
import com.alone.sell.vo.ResultVO;
import com.alone.sell.dataobject.ProductCategory;
import com.alone.sell.dataobject.ProductInfo;
import com.alone.sell.service.IProductCategoryService;
import com.alone.sell.service.IProductInfoService;
import com.alone.sell.vo.ProductInfoVO;
import com.alone.sell.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品
 *
 * @author Chri61
 * @Date 2021/12/27 0027
 */
@RestController
@RequestMapping("buyer/product")
public class BuyerProdcutController {

    @Autowired
    private IProductInfoService iProductInfoService;
    @Autowired
    private IProductCategoryService iProductCategoryService;

    @GetMapping("list")
    public ResultVO list() {
        //1.查询所有上架的商品
        List<ProductInfo> productInfoList = iProductInfoService.findUpAll();
        //查询所有类目，并组装商品。有两种方式：（1）传统方式
//        List<Integer> categoryTypeList = new ArrayList<>();
//        for (ProductInfo productInfo : productInfoList) {
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        //（2）JDK8 新特性，lambda
        List<Integer> typeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        //2.查询所有类目
        List<ProductCategory> productCategoryList = iProductCategoryService.findByCategoryTypeIn(typeList);

        List<ProductVO> voList = new ArrayList<>();
        //3.首先遍历类目
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO vo = new ProductVO();
            vo.setCategoryName(productCategory.getCategoryName());
            vo.setCategoryType(productCategory.getCategoryType());
            //遍历商品详情
            List<ProductInfoVO> infoVoList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO infoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, infoVO);
                    infoVoList.add(infoVO);
                }
            }
            vo.setCategoryFoods(infoVoList);
            voList.add(vo);
        }

        return ResultVO.success("成功", voList);
    }


    @GetMapping("test2")
    public void test2() {
        throw new ResponseBankException();
    }
}
