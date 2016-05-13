package com.markeveryday.controller;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.markeveryday.security.LoginHelper;

/**
 * API controller
 *
 * @author liming
 */
@Controller
@RequestMapping("/api")
public class ApiController {

    @RequestMapping("/currentLoginUser")
    @ResponseBody
    private User getCurrentLoginUser() {
        return LoginHelper.getLoginUser();
    }

}
