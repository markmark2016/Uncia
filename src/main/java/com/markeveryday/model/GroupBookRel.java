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

import com.markeveryday.bean.ReadStatus;

/**
 * 组与图书联系实体
 *
 * @author liming
 */
@Entity
@Table(name = "T_GROUP_BOOK_REL")
public class GroupBookRel {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BOOK_ID")
    private Long bookId;

    @Column(name = "GROUP_ID")
    private Long groupId;

    @Column(name = "SLOGN")
    private String slogn;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private ReadStatus status;

    @Column(name = "DELETE_STATUS")
    private Boolean deleteStatus;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "MOD_TIME")
    private Date modTime;

    public GroupBookRel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getSlogn() {
        return slogn;
    }

    public void setSlogn(String slogn) {
        this.slogn = slogn;
    }

    public ReadStatus getStatus() {
        return status;
    }

    public void setStatus(ReadStatus status) {
        this.status = status;
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
