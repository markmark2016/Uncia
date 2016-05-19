package com.markeveryday.bean;

import com.markeveryday.model.Category;
import com.markeveryday.model.Community;

/**
 * @author liming
 */
public class CommunityCategoryBean {

    private Community community;
    private Category category;


    public CommunityCategoryBean() {
    }

    public CommunityCategoryBean(Community community, Category category) {
        this.category = category;
        this.community = community;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
