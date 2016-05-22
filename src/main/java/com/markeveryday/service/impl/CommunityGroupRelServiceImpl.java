package com.markeveryday.service.impl;

import com.markeveryday.commons.db.ConditionAndSet;
import com.markeveryday.dao.CommunityGroupRelDao;
import com.markeveryday.model.CommunityGroupRel;
import com.markeveryday.model.GroupBookRel;
import com.markeveryday.service.CommunityGroupRelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author liming
 */
@Service
public class CommunityGroupRelServiceImpl implements CommunityGroupRelService {


    @Autowired
    private CommunityGroupRelDao groupRelDao;


    @Override
    public void deleteByGroupId(Long groupId) {

        ConditionAndSet andSet = new ConditionAndSet();
        andSet.put("groupId", groupId);
        andSet.put("deleteStatus", false);
        List<CommunityGroupRel> communityGroupRels = groupRelDao.findByProperties(andSet);
        if (CollectionUtils.isEmpty(communityGroupRels)) {
            return;
        }
        for (CommunityGroupRel rel : communityGroupRels) {
            rel.setDeleteStatus(true);
            rel.setModTime(new Date());
            groupRelDao.saveOrUpdate(rel);
        }
    }
}
