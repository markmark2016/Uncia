package com.markeveryday.service;

import java.util.List;

import org.springframework.util.Assert;

import com.markeveryday.model.User;

/**
 * 用户 Service
 *
 * @author liming
 */
public interface UserService {

    /**
     * 根据Id查找用户
     *
     * @param id 用户Id
     *
     * @return 用户或者null
     */
    User findById(Long id);

    /**
     * 根据用户openid获取用户
     *
     * @param openid 用户openid
     *
     * @return 用户或者null
     */
    User findByOpenid(String openid);

    /**
     * 保存用户数据, 如果存在则更新,不存在则插入
     *
     * @param user 待保存的用户数据
     */
    void saveUser(User user);

    /**
     * 根据Id删除用户
     *
     * @param id
     */
    void deleteUserById(Long id);

    /**
     * 删除用户
     *
     * @param user
     */
    default void deleteUser(User user) {
        Assert.notNull(user, "待删除的用户不能为null.");
        deleteUserById(user.getId());
    }

    /**
     * 根据企业id查找用户列表
     * @param enterpriseId 企业id
     * @return 用户列表或者空集合
     */
    List<User>  findByEnterpriseId(Long enterpriseId);

    /**
     * 根据组id查找用户列表
     * @param groupId 小组id
     * @return 用户列表或者空集合
     */
    List<User> findByGroupId(Long groupId);


    /**
     * 获取所有用户
     *
     * @return
     */
    List<User> getAllUsers();

}
