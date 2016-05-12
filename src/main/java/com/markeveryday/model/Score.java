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

import com.markeveryday.bean.ScoreType;

/**
 * 得分实体
 *
 * @author liming
 */
@Entity
@Table(name = "T_SCORE")
public class Score {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ScoreType type;

    @Column(name = "SCORE")
    private Integer score;

    @Column(name = "DELETE_STATUS")
    private Boolean deleteStatus;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "MOD_TIME")
    private Date modTime;

    public Score() {
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

    public ScoreType getType() {
        return type;
    }

    public void setType(ScoreType type) {
        this.type = type;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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
