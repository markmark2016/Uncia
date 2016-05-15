package com.markeveryday.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import com.markeveryday.bean.EnterpriseGroupUser;
import com.markeveryday.bean.GroupUser;
import com.markeveryday.model.Account;
import com.markeveryday.model.Enterprise;
import com.markeveryday.model.Group;
import com.markeveryday.security.LoginHelper;
import com.markeveryday.service.AccountService;
import com.markeveryday.service.EnterpriseService;
import com.markeveryday.service.GroupService;
import com.markeveryday.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * API controller
 *
 * @author liming
 */
@Controller
@RequestMapping("/api")
public class ApiController {


    @Autowired
    private UserService userService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private GroupService groupService;


    /**
     * 获取当前登录的企业账号下的所有用户信息
     */
    @RequestMapping(value = "/enterprise/users", method = RequestMethod.GET)
    @ResponseBody
    public List<com.markeveryday.model.User> getUsersByEnterprise() {

        Enterprise enterprise = getLoginEnterprise();
        if (enterprise == null) {
            return Collections.emptyList();
        }
        return userService.findByEnterpriseId(enterprise.getId());

    }

    /**
     * 获取当前登录的企业账号下的组信息
     */
    @RequestMapping(value = "/enterprise/groups", method = RequestMethod.GET)
    @ResponseBody
    public List<Group> getGroupsByEnterprise() {
        Enterprise enterprise = getLoginEnterprise();
        if (enterprise == null) {
            return Collections.emptyList();
        }
        return groupService.findByEnterpriseId(enterprise.getId());

    }

    /**
     * 获取当前登录的企业信息
     */
    @RequestMapping(value = "/enterprise", method = RequestMethod.GET)
    @ResponseBody
    public Enterprise getLoginEnterprise() {
        User user = LoginHelper.getLoginUser();
        if (user == null) {
            return null;
        }
        String enterpriseAdminUsername = user.getUsername();
        Account account = accountService.findByUsername(enterpriseAdminUsername);
        if (account != null) {
            return enterpriseService.findByAccountId(account.getId());
        }
        return null;
    }

    /**
     * 获取当前登录的企业的用户信息,包括组信息,企业信息
     *
     * @see #getUsersByEnterprise()
     */
    //TODO 改为批量查询
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public EnterpriseGroupUser getUsers() {
        Enterprise enterprise = getLoginEnterprise();
        if (enterprise == null) {
            return null;
        }
        List<Group> groups = groupService.findByEnterpriseId(enterprise.getId());
        List<GroupUser> groupUsers = new ArrayList<>();
        for (Group group : groups) {
            List<com.markeveryday.model.User> users =
                    userService.findByGroupId(group.getId());
            GroupUser gu = new GroupUser();
            gu.setGroup(group);
            gu.setUsers(users);
            groupUsers.add(gu);
        }
        EnterpriseGroupUser egu = new EnterpriseGroupUser();
        egu.setEnterprise(enterprise);
        egu.setGroupUsers(groupUsers);
        return egu;
    }


}
