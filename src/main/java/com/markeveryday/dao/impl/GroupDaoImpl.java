package com.markeveryday.dao.impl;

import org.springframework.stereotype.Repository;

import com.markeveryday.commons.db.AbstractBaseDao;
import com.markeveryday.dao.GroupDao;
import com.markeveryday.model.Group;

/**
 * Group DAO 实现
 *
 * @author liming
 */
@Repository
public class GroupDaoImpl extends AbstractBaseDao<Group> implements GroupDao {
}
