package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    void update(CategoryDTO categoryDTO);

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void save(CategoryDTO categoryDTO);

    Boolean delete(Long id);

    void enableOrDisable(Long id, Integer status);

    List<Category> list(Integer type);
}
