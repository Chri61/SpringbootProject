package com.alone.sell.form;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Chri61
 * @Date 2022/3/27 0027
 */
@Getter
@Setter
public class CategoryForm {

    private Integer categoryId;

    private Integer categoryParentId;

    private String categoryName;

    private Integer categoryType;

}
