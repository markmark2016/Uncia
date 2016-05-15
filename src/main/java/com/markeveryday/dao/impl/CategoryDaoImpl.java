package com.markeveryday.dao.impl;

import com.markeveryday.commons.db.AbstractBaseDao;
import com.markeveryday.dao.CategoryDao;
import com.markeveryday.dao.CommunityDao;
import com.markeveryday.model.Category;
import com.markeveryday.model.Community;

import org.springframework.stereotype.Repository;

/**
 * @author liming
 */
@Repository
public class CategoryDaoImpl extends AbstractBaseDao<Category> implements CategoryDao {
}
