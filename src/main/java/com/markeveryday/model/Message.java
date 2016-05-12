package com.markeveryday.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.markeveryday.bean.MessageType;

/**
 * 消息实体
 *
 * @author liming
 */
@Entity
@Table(name = "T_MESSAGE")
public class Message {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "INTERACT_ID")
    private Long interactId;

    @Column(name = "TYPE")
    private MessageType type;

    @Column(name = "CHECKED")
    private Boolean checked;
    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "MOD_TIME")
    private Date modTime;

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getInteractId() {
        return interactId;
    }

    public void setInteractId(Long interactId) {
        this.interactId = interactId;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
