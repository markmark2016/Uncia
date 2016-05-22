package com.markeveryday.model;

import com.markeveryday.utils.JsonHelpler;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 组实体
 *
 * @author liming
 */
@Entity
@Table(name = "T_GROUP")
public class Group {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GROUP_NAME")
    private String groupName;

    @Column(name = "SLOGAN")
    private String slogan;

    @Column(name = "ENTERPRISE_ID")
    private Long enterpriseId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATOR")
    private String creator;

    @Column(name = "DELETE_STATUS")
    private Boolean deleteStatus;
    /**
     * 开始时间
     */
    @Column(name = "START_TIME")
    private Date startTime;
    /**
     * 结束时间
     */
    @Column(name = "END_TIME")
    private Date endTime;
    /**
     * 最晚加入时间
     */
    @Column(name = "DEADLINE_TIME")
    private Date deadlineTime;

    /**
     * 打卡频率 frequency 天一次
     */
    @Column(name = "FREQUENCY")
    private Integer frequency;

    /**
     * 组列表是否可见 true 可见 false 不可见
     */
    @Column(name = "VISIABLE")
    private Boolean visiable;
    /**
     * true 无限制加入 false 开始后不能加入
     */
    @Column(name = "ADD_MEMBER_TYPE")
    private Boolean addMemberType;
    /**
     * 组书评是否可见 true 可见 false 不可见
     */
    @Column(name = "REMARK_VISIABLE")
    private Boolean remarkVisiable;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "MOD_TIME")
    private Date modTime;

    public Group() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(Date deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Boolean getVisiable() {
        return visiable;
    }

    public void setVisiable(Boolean visiable) {
        this.visiable = visiable;
    }

    public Boolean getAddMemberType() {
        return addMemberType;
    }

    public void setAddMemberType(Boolean addMemberType) {
        this.addMemberType = addMemberType;
    }

    public Boolean getRemarkVisiable() {
        return remarkVisiable;
    }

    public void setRemarkVisiable(Boolean remarkVisiable) {
        this.remarkVisiable = remarkVisiable;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    @Override
    public String toString() {
        return JsonHelpler.toJsonString(this);
    }
}
