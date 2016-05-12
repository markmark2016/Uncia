package com.markeveryday.dao.impl;

import org.springframework.stereotype.Repository;

import com.markeveryday.dao.RemarkDao;
import com.markeveryday.model.Remark;
import com.markeveryday.commons.db.AbstractBaseDao;

/**
 * Remark DAO 实现
 *
 * @author liming
 */
@Repository
public class RemarkDaoImpl extends AbstractBaseDao<Remark> implements RemarkDao {
}
