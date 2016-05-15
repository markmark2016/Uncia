package com.markeveryday.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.markeveryday.commons.db.ConditionAndSet;
import com.markeveryday.commons.db.ConditionFactory;
import com.markeveryday.commons.db.ConditionSet;
import com.markeveryday.dao.UserDao;
import com.markeveryday.model.GroupUserRel;
import com.markeveryday.model.User;
import com.markeveryday.service.UserService;

/**
 * 用户Service实现
 *
 * @author liming
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private GroupUserRelService groupUserRelService;


    /**
     * 根据Id查找用户
     *
     * @param id 用户Id
     * @return 用户或者null
     */
    @Override
    public User findById(Long id) {
        Assert.notNull(id, "id 不能为null.");
        return userDao.findById(id);
    }


    /**
     * 根据用户openid获取用户
     *
     * @param openid 用户openid
     * @return 用户或者null
     */
    @Override
    public User findByOpenid(String openid) {
        Assert.notNull(openid, "openid 不能为null.");
        return userDao.findUniqueByProperties(ConditionFactory.and("openid", openid));
    }

    /**
     * 保存用户数据, 如果存在则更新,不存在则插入
     *
     * @param user 待保存的用户数据
     */
    @Override
    public void saveUser(User user) {
        Assert.notNull(user, "待存储的用户不能为null.");
        userDao.saveOrUpdate(user);
    }

    /**
     * 根据Id删除用户
     */
    @Override
    public void deleteUserById(Long id) {
        User user = findById(id);
        user.setDeleteStatus(true);
        user.setModTime(new Date());
        saveUser(user);
    }

    /**
     * 根据企业id查找用户列表
     *
     * @param enterpriseId 企业id
     * @return 用户列表或者空集合
     */
    @Override
    public List<User> findByEnterpriseId(Long enterpriseId) {
        Assert.notNull(enterpriseId, "enterpriseId to find users can't be null.");
        return userDao.findByProperties(ConditionFactory.and("enterpriseId", enterpriseId));
    }

    /**
     * 根据组id查找用户列表
     *
     * @param groupId 小组id
     * @return 用户列表或者空集合
     */
    // TODO 改为批量查询
    @Override
    public List<User> findByGroupId(Long groupId) {
        List<GroupUserRel> groupUserRels =
                groupUserRelService.getUserRelsByGroupId(groupId);
        if (CollectionUtils.isEmpty(groupUserRels)) {
            return null;
        }
        List<User> users = new ArrayList<>();
        for (GroupUserRel gur : groupUserRels) {
            users.add(findById(gur.getUserId()));
        }
        return users;
    }

    /**
     * 获取所有用户
     */
    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}
