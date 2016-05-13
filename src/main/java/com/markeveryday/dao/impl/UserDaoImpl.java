package com.markeveryday.dao.impl;

import org.springframework.stereotype.Repository;

import com.markeveryday.commons.db.AbstractBaseDao;
import com.markeveryday.dao.UserDao;
import com.markeveryday.model.User;

/**
 * User DAO 实现
 *
 * @author liming
 */
@Repository
public class UserDaoImpl extends AbstractBaseDao<User> implements UserDao {
}
