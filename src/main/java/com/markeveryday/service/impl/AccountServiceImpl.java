package com.markeveryday.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.markeveryday.commons.db.ConditionFactory;
import com.markeveryday.commons.db.ConditionSet;
import com.markeveryday.dao.AccountDao;
import com.markeveryday.model.Account;
import com.markeveryday.service.AccountService;

/**
 * 账户服务实现层
 *
 * @author liming
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    /**
     * 根据账户名获取账户信息
     *
     * @param username
     *
     * @return
     */
    @Override
    public Account findByUsername(String username) {
        Assert.notNull(username, "username to find account can'e be null.");
        ConditionSet cs = ConditionFactory.and("username", username).put("enabled", true);
        return accountDao.findUniqueByProperties(cs);
    }
}
