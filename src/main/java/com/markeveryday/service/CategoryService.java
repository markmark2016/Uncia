package com.markeveryday.service;

import com.markeveryday.model.Category;
import com.markeveryday.model.Community;

import java.util.List;


/**
 * @author liming
 */
public interface CategoryService {


    Category findById(Long id);


    /**
     * 保存一个分类,如果已经存在则更新
     */
    void save(Category category);


    /**
     * 获得所有分类
     */
    List<Category> getAllCategories();

    void deleteById(Long categoryId);
}
