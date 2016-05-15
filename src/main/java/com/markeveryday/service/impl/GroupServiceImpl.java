package com.markeveryday.service.impl;

import com.markeveryday.commons.db.ConditionFactory;
import com.markeveryday.dao.GroupDao;
import com.markeveryday.model.Group;
import com.markeveryday.service.GroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author liming
 */
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;

    /**
     * 根据企业id获取企业下的组信息
     *
     * @param enterpriseId 企业id
     * @return 组列表或者null
     */
    @Override
    public List<Group> findByEnterpriseId(Long enterpriseId) {
        Assert.notNull(enterpriseId, "enterpriseId to find groups can't be null.");
        return groupDao.findByProperties(ConditionFactory.and("enterpriseId", enterpriseId));
    }
}
