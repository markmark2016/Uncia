package com.markeveryday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 *
 */
@Controller
@RequestMapping("/")
public class MarkMarkController {

    @RequestMapping("")
    public String index(Model model) {
        return "index";
    }

}
