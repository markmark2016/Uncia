package com.markeveryday.dao.impl;

import org.springframework.stereotype.Repository;

import com.markeveryday.dao.AccountDao;
import com.markeveryday.model.Account;
import com.markeveryday.commons.db.AbstractBaseDao;

/**
 * Account DAO 实现
 *
 * @author liming
 */
@Repository
public class AccountDaoImpl extends AbstractBaseDao<Account> implements AccountDao {
}
