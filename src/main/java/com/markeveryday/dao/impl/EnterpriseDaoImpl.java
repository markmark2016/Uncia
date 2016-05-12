package com.markeveryday.dao.impl;

import org.springframework.stereotype.Repository;

import com.markeveryday.dao.EnterpriseDao;
import com.markeveryday.model.Enterprise;
import com.markeveryday.commons.db.AbstractBaseDao;

/**
 * Enterprise DAO 实现
 *
 * @author liming
 */
@Repository
public class EnterpriseDaoImpl extends AbstractBaseDao<Enterprise> implements EnterpriseDao {
}
