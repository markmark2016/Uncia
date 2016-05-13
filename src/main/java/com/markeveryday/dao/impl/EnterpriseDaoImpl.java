package com.markeveryday.dao.impl;

import org.springframework.stereotype.Repository;

import com.markeveryday.commons.db.AbstractBaseDao;
import com.markeveryday.dao.EnterpriseDao;
import com.markeveryday.model.Enterprise;

/**
 * Enterprise DAO 实现
 *
 * @author liming
 */
@Repository
public class EnterpriseDaoImpl extends AbstractBaseDao<Enterprise> implements EnterpriseDao {
}
