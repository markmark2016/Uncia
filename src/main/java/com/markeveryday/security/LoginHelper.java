package com.markeveryday.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 登录帮助类
 *
 * @author liming
 */
public class LoginHelper {

    public static User getLoginUser() {
        User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            user = (User) principal;
        }
        return user;
    }

}
