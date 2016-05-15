package com.markeveryday.service.impl;

import com.markeveryday.commons.db.ConditionFactory;
import com.markeveryday.dao.EnterpriseDao;
import com.markeveryday.model.Enterprise;
import com.markeveryday.service.EnterpriseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author liming
 */
@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Override
    public Enterprise findByAccountId(Long accountId) {
        Assert.notNull(accountId, "accountId to find enterprise can't be null.");
        return enterpriseDao.findUniqueByProperties(ConditionFactory.and("accountId", accountId));
    }
}
