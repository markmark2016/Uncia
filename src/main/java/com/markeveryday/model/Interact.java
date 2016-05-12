package com.markeveryday.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.markeveryday.bean.InteractType;

/**
 * 交互实体
 *
 * @author liming
 */
@Entity
@Table(name = "T_INTERACT")
public class Interact {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "REMARK_ID")
    private Long remarkId;
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private InteractType type;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "DELETE_STATUS")
    private Boolean deleteStatus;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "MOD_TIME")
    private Date modTime;

    public Interact() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(Long remarkId) {
        this.remarkId = remarkId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public InteractType getType() {
        return type;
    }

    public void setType(InteractType type) {
        this.type = type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }
}
