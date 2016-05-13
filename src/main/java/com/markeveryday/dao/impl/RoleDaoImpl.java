package com.markeveryday.dao.impl;

import org.springframework.stereotype.Repository;

import com.markeveryday.commons.db.AbstractBaseDao;
import com.markeveryday.dao.RoleDao;
import com.markeveryday.model.Role;

/**
 * 用户角色DAO实现
 *
 * @author liming
 */
@Repository
public class RoleDaoImpl extends AbstractBaseDao<Role> implements RoleDao {
}
