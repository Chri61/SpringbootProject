package com.alone.sell.dataobject.mapper;

import org.apache.ibatis.annotations.Insert;

import java.util.Map;

/**
 * @author Chri61
 * @Date 2022/4/1 0001
 */
public interface ProductCategoryMapper {

    @Insert("insert into product_category(category_parent_id,category_name, category_type) values (#{category_parent_id, jdbcType=VARCHAR},#{category_name, jdbcType=VARCHAR}, #{category_type, jdbcType=INTEGER})")
    int insert(Map<String, Object> map);

}
