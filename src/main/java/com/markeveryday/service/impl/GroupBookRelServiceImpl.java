package com.markeveryday.service.impl;

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
        groupBookRelDao.saveOrUpdate(groupBookRel);
    }

    @Override
    public void deleteByGroupId(Long groupId) {

        ConditionAndSet andSet = new ConditionAndSet();
        andSet.put("groupId", groupId);
        andSet.put("deleteStatus", false);
        List<GroupBookRel> groupBookRelList = groupBookRelDao.findByProperties(andSet);
        if (CollectionUtils.isEmpty(groupBookRelList)) {
            return;
        }
        for (GroupBookRel rel : groupBookRelList) {
            rel.setDeleteStatus(true);
            rel.setModTime(new Date());
            groupBookRelDao.saveOrUpdate(rel);
        }


    }
}
