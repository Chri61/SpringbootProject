package com.alone.sell.controller.manage;

import com.alone.sell.common.enums.ResultEnum;
import com.alone.sell.dataobject.ProductCategory;
import com.alone.sell.form.CategoryForm;
import com.alone.sell.service.IProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 卖家 商品种类管理
 *
 * @author Chri61
 * @Date 2022/3/27 0027
 */
@Slf4j
@Controller
@RequestMapping("seller/category")
public class SellerCategoryController {

    @Autowired
    private IProductCategoryService iProductCategoryService;

    @GetMapping("list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> categoryList = iProductCategoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }


    @GetMapping("index")
    public ModelAndView index(Integer categoryId, Map<String, Object> map) {
        if (categoryId != null) {
            ProductCategory category = iProductCategoryService.findById(categoryId);
            map.put("category", category);
        }
        List<ProductCategory> categoryList = iProductCategoryService.findAll();
        List<Integer> categoryIdList = categoryList.stream().map(e -> e.getCategoryId()).collect(Collectors.toList());
        map.put("categoryIdList", categoryIdList);
        return new ModelAndView("category/index", map);
    }

    @PostMapping("save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            log.error("【卖家端种类更新】发生异常");
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }

        ProductCategory category = new ProductCategory();
        BeanUtils.copyProperties(categoryForm, category);


        iProductCategoryService.save(category);
        map.put("msg", ResultEnum.SUCCESS.getMsg());
        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("common/success", map);
    }
}
