package com.markeveryday.service.impl;

import com.markeveryday.bean.ReadStatus;
import com.markeveryday.commons.db.ConditionAndSet;
import com.markeveryday.dao.GroupBookRelDao;
import com.markeveryday.model.GroupBookRel;
import com.markeveryday.service.GroupBookRelService;

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
public class GroupBookRelServiceImpl implements GroupBookRelService {

    @Autowired
    private GroupBookRelDao groupBookRelDao;


    @Override
    public void save(GroupBookRel groupBookRel) {
        Assert.notNull(groupBookRel, "groupBookRel to save can't be null");
        if (groupBookRel.getId() == null) {
            groupBookRel.setCreateTime(new Date());
            groupBookRel.setModTime(new Date());
            groupBookRel.setSlogan("");
            groupBookRel.setDeleteStatus(false);
            groupBookRel.setStatus(ReadStatus.NOT_STARTED);
        } else {
            groupBookRel.setSlogan("");
            groupBookRel.setModTime(new Date());
        }
        groupBookRelDao.saveOrUpdate(groupBookRel);
    }

    @Override
    public void deleteByGroupId(Long groupId) {
        GroupBookRel rel = findByGroupId(groupId);
        if (rel != null) {
            rel.setDeleteStatus(true);
            save(rel);
        }

    }

    @Override
    public GroupBookRel findByGroupId(Long groupId) {
        ConditionAndSet andSet = new ConditionAndSet();
        andSet.put("groupId", groupId);
        andSet.put("deleteStatus", false);
        List<GroupBookRel> groupBookRelList = groupBookRelDao.findByProperties(andSet);
        if (CollectionUtils.isEmpty(groupBookRelList)) {
            return null;
        }
        return groupBookRelList.get(0);
    }
}
