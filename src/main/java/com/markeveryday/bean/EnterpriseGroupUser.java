package com.markeveryday.bean;

import com.markeveryday.model.Enterprise;

import java.util.List;

/**
 * 企业-组-用户 bean
 *
 * @author liming
 */
public class EnterpriseGroupUser {

    private Enterprise enterprise;
    private List<GroupUser> groupUsers;

    public EnterpriseGroupUser() {
    }

    public List<GroupUser> getGroupUsers() {
        return groupUsers;
    }

    public void setGroupUsers(List<GroupUser> groupUsers) {
        this.groupUsers = groupUsers;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }
}
