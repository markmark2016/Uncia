package com.markeveryday.service.impl;

import com.markeveryday.commons.db.ConditionFactory;
import com.markeveryday.dao.GroupUserRelDao;
import com.markeveryday.model.GroupUserRel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by liming on 16/5/15.
 */
public class GroupUserRelServiceImpl implements GroupUserRelService {

    @Autowired
    private GroupUserRelDao groupUserRelDao;

    /**
     * 根据组id获取用户列表
     */
    @Override
    public List<GroupUserRel> getUserRelsByGroupId(Long groupId) {
        Assert.notNull(groupId, "groupId to find users can't be null.");
        return groupUserRelDao.findByProperties(ConditionFactory.and("groupId", groupId));
    }
}
