package com.markeveryday.service.impl;

import com.markeveryday.commons.db.ConditionFactory;
import com.markeveryday.dao.CategoryDao;
import com.markeveryday.model.Category;
import com.markeveryday.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author liming
 */
@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Category findById(Long id) {
        if (id == null) {
            return null;
        }
        return categoryDao.findById(id);
    }

    /**
     * 保存一个分类,如果已经存在则更新
     */
    @Override
    public void save(Category category) {
        Assert.notNull(category, "category to save can't be null");
        if (category.getCreateTime() == null) {
            category.setCreateTime(new Date());
        }
        category.setModTime(new Date());
        categoryDao.save(category);

    }

    /**
     * 获得所有分类
     */
    @Override
    public List<Category> getAllCategories() {
        return categoryDao.findByProperties(ConditionFactory.and("deleteStatus", false));
    }

    @Override
    public void deleteById(Long categoryId) {
        categoryDao.delete(findById(categoryId));
    }
}
