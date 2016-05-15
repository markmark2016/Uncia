package com.markeveryday.dao.impl;

import com.markeveryday.commons.db.AbstractBaseDao;
import com.markeveryday.dao.CategoryDao;
import com.markeveryday.dao.CommunityCategoryRelDao;
import com.markeveryday.model.Category;
import com.markeveryday.model.CommunityCategoryRel;

import org.springframework.stereotype.Repository;

/**
 * @author liming
 */
@Repository
public class CommunityCategoryRelDaoImpl extends AbstractBaseDao<CommunityCategoryRel>
        implements CommunityCategoryRelDao {
}
