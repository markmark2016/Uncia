package com.markeveryday.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.markeveryday.security.LoginHelper;

/**
 * 账户相关
 *
 * @author liming
 */
@Controller
public class AccountController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "admin/login";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", LoginHelper.getLoginUser());
        return "error/403";
    }

}
