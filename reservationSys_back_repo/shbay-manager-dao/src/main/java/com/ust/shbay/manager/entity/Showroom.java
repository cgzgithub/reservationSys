package com.ust.shbay.manager.entity;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

public class Showroom {
    private Integer id;

    private String name;

    private String position;

    @Column(precision = 10, scale = 2)
    private BigDecimal area;

    private Integer seatNum;

    private String amBeginTime;

    private String amEndTime;

    private String pmBeginTime;

    private String pmEndTime;

    private Integer delFlag;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Integer getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(Integer seatNum) {
        this.seatNum = seatNum;
    }

    public String getAmBeginTime() {
        return amBeginTime;
    }

    public void setAmBeginTime(String amBeginTime) {
        this.amBeginTime = amBeginTime == null ? null : amBeginTime.trim();
    }

    public String getAmEndTime() {
        return amEndTime;
    }

    public void setAmEndTime(String amEndTime) {
        this.amEndTime = amEndTime == null ? null : amEndTime.trim();
    }

    public String getPmBeginTime() {
        return pmBeginTime;
    }

    public void setPmBeginTime(String pmBeginTime) {
        this.pmBeginTime = pmBeginTime == null ? null : pmBeginTime.trim();
    }

    public String getPmEndTime() {
        return pmEndTime;
    }

    public void setPmEndTime(String pmEndTime) {
        this.pmEndTime = pmEndTime == null ? null : pmEndTime.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}