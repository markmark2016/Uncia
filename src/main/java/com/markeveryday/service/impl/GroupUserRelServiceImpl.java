package com.markeveryday.service.impl;

import com.markeveryday.bean.GroupUser;
import com.markeveryday.commons.db.ConditionAndSet;
import com.markeveryday.commons.db.ConditionFactory;
import com.markeveryday.dao.GroupUserRelDao;
import com.markeveryday.model.GroupUserRel;
import com.markeveryday.service.GroupUserRelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author liming
 */
@Service
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

    @Override
    public void deleteByGroupId(Long groupId) {
        ConditionAndSet andSet = new ConditionAndSet();
        andSet.put("groupId", groupId);
        andSet.put("deleteStatus", false);
        List<GroupUserRel> groupUserRels = groupUserRelDao.findByProperties(andSet);
        if (CollectionUtils.isEmpty(groupUserRels)) {
            return;
        }
        for (GroupUserRel rel : groupUserRels) {
            rel.setDeleteStatus(true);
            rel.setModTime(new Date());
            groupUserRelDao.saveOrUpdate(rel);
        }

    }
}
