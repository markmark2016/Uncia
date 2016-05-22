package com.markeveryday.service.impl;

import com.markeveryday.commons.db.ConditionAndSet;
import com.markeveryday.commons.db.ConditionFactory;
import com.markeveryday.commons.db.ConditionSet;
import com.markeveryday.dao.EnterpriseDao;
import com.markeveryday.dao.GroupDao;
import com.markeveryday.model.Group;
import com.markeveryday.model.GroupBookRel;
import com.markeveryday.security.LoginHelper;
import com.markeveryday.service.CommunityGroupRelService;
import com.markeveryday.service.GroupBookRelService;
import com.markeveryday.service.GroupService;
import com.markeveryday.service.GroupUserRelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author liming
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private GroupBookRelService groupBookRelService;

    @Autowired
    private GroupUserRelService groupUserRelService;

    @Autowired
    private CommunityGroupRelService communityGroupRelService;


    /**
     * 根据企业id获取企业下的组信息
     *
     * @param enterpriseId 企业id
     * @return 组列表或者null
     */
    @Override
    public List<Group> findByEnterpriseId(Long enterpriseId) {
        Assert.notNull(enterpriseId, "enterpriseId to find groups can't be null.");
        ConditionAndSet andSet = ConditionAndSet.newInstance();
        andSet.put("enterpriseId", enterpriseId);
        andSet.put("deleteStatus", false);

        return groupDao.findByProperties(andSet);
    }

    @Override
    public void saveGroup(Group group) {
        Assert.notNull(group, "Group to save can't be null.");
        if (group.getId() == null || (group.getId() != null && group.getId() == 0L)) {
            group.setCreateTime(new Date());
            group.setModTime(new Date());
            group.setDeleteStatus(false);
            groupDao.save(group);
        } else {
            group.setModTime(new Date());
            groupDao.saveOrUpdate(group);
        }


    }

    @Override
    public void deleteGroup(Long groupId) {
        Assert.notNull(groupId, "groupId for deleting can't be null.");
        Group group = groupDao.findById(groupId);
        if (group != null) {
            group.setDeleteStatus(true);
            group.setModTime(new Date());
            groupDao.save(group);
        }

        groupBookRelService.deleteByGroupId(groupId);
        groupUserRelService.deleteByGroupId(groupId);
        communityGroupRelService.deleteByGroupId(groupId);
        // TODO remark,enterprise

    }
}
