package com.alone.sell.controller.manage;

import com.alone.sell.common.exception.SellException;
import com.alone.sell.common.enums.ResultEnum;
import com.alone.sell.common.utils.KeyUtil;
import com.alone.sell.dataobject.ProductCategory;
import com.alone.sell.dataobject.ProductInfo;
import com.alone.sell.form.ProductForm;
import com.alone.sell.service.IProductCategoryService;
import com.alone.sell.service.IProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 卖家商品页
 *
 * @author Chri61
 * @Date 2022/3/26 0026
 */
@Slf4j
@Controller
@RequestMapping("seller/product")
public class SellerProductController {

    @Autowired
    private IProductInfoService iProductInfoService;
    @Autowired
    private IProductCategoryService iProductCategoryService;

    /**
     * 商品列表
     *
     * @param page 第几页
     * @param size 每页显示的个数
     * @return
     */
    @GetMapping("list")
    public ModelAndView list(@RequestParam(defaultValue = "1", value = "page") Integer page,
                             @RequestParam(defaultValue = "10", value = "size") Integer size,
                             Map<String, Object> map) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInfoPage = iProductInfoService.findAll(request);
        map.put("productInfoPage", productInfoPage);
        //当前页
        map.put("currentPate", page);
        map.put("size", size);
        //总页数
        map.put("totalPages", productInfoPage.getTotalPages());
        return new ModelAndView("/product/list", map);
    }

    /**
     * 商品下架
     *
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("off_sale")
    public ModelAndView offSale(String productId, Map<String, Object> map) {
        try {
            iProductInfoService.offSale(productId);
        } catch (SellException e) {
            log.error("【卖家端商品下架】发生异常", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 商品上架
     *
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("on_sale")
    public ModelAndView onSale(String productId, Map<String, Object> map) {
        try {
            iProductInfoService.onSale(productId);
        } catch (SellException e) {
            log.error("【卖家端商品上架】发生异常", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 商品详情
     *
     * @return
     */
    @GetMapping("index")
    public ModelAndView index(String productId, Map<String, Object> map) {
        if (StringUtils.isNotEmpty(productId)) {
            ProductInfo productInfo = iProductInfoService.findById(productId);
            map.put("productInfo", productInfo);
        }
        //查询所有的类目
        List<ProductCategory> categoryList = iProductCategoryService.findAll();
        map.put("categoryList", categoryList);

        return new ModelAndView("product/index", map);
    }

    /**
     * 添加和更新商品
     *
     * @param productForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            log.error("【卖家端商品更新】发生异常");
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");

        ProductInfo productInfo = new ProductInfo();
        if (StringUtils.isNotEmpty(productForm.getProductId())) {
            //修改商品，先查出这个商品
            productInfo = iProductInfoService.findById(productForm.getProductId());
        } else {
            productForm.setProductId(KeyUtil.getUniqueKey());
        }
        BeanUtils.copyProperties(productForm, productInfo);
        //更新或添加的操作
        try {
            iProductInfoService.save(productInfo);
        } catch (SellException e) {
            log.error("【卖家端商品更新】发生异常，修改或保存出错");
            map.put("msg", e.getMessage());
            return new ModelAndView("common/error", map);
        }

        return new ModelAndView("common/success", map);
    }
}
