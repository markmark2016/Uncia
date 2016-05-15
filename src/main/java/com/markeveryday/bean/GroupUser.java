package com.markeveryday.bean;

import com.markeveryday.model.Group;
import com.markeveryday.model.User;

import java.util.List;

/**
 * 用户与组bean
 *
 * @author liming
 */
public class GroupUser {
    private Group group;
    private List<User> users;

    public GroupUser() {
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
