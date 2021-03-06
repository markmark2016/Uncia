package com.markeveryday.dao.impl;

import org.springframework.stereotype.Repository;

import com.markeveryday.commons.db.AbstractBaseDao;
import com.markeveryday.dao.RemarkDao;
import com.markeveryday.model.Remark;

/**
 * Remark DAO 实现
 *
 * @author liming
 */
@Repository
public class RemarkDaoImpl extends AbstractBaseDao<Remark> implements RemarkDao {
}
