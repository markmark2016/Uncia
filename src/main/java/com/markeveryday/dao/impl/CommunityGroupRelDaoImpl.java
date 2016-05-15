package com.markeveryday.dao.impl;

import com.markeveryday.commons.db.AbstractBaseDao;
import com.markeveryday.dao.CommunityCategoryRelDao;
import com.markeveryday.dao.CommunityGroupRelDao;
import com.markeveryday.model.CommunityCategoryRel;
import com.markeveryday.model.CommunityGroupRel;

import org.springframework.stereotype.Repository;

/**
 * @author liming
 */
@Repository
public class CommunityGroupRelDaoImpl extends AbstractBaseDao<CommunityGroupRel>
        implements CommunityGroupRelDao {
}
