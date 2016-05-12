package com.markeveryday.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.markeveryday.commons.db.ConditionAndSet;
import com.markeveryday.commons.db.ConditionSet;
import com.markeveryday.dao.UserDao;
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

    /**
     * 根据Id查找用户
     *
     * @param id 用户Id
     *
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
     *
     * @return 用户或者null
     */
    @Override
    public User findByOpenid(String openid) {
        Assert.notNull(openid, "openid 不能为null.");
        ConditionSet conditionSet = new ConditionAndSet();
        conditionSet.put("openid", openid);
        return userDao.findUniqueByProperties(conditionSet);
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
     *
     * @param id
     */
    @Override
    public void deleteUserById(Long id) {
        User user = findById(id);
        user.setDeleteStatus(true);
        user.setModTime(new Date());
        saveUser(user);
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}
