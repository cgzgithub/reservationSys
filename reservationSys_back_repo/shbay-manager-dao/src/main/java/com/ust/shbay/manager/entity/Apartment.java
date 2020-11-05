package com.ust.shbay.manager.entity;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

public class Apartment {
    private Integer id;

    private String number;

    private String addressRidgepole;

    private String addressNumber;

    private String addressRoom;

    private String residentialAreaName;

    @Column(precision = 10, scale = 2)
    private BigDecimal area;

    private Integer houseType;

    private Integer status;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getAddressRidgepole() {
        return addressRidgepole;
    }

    public void setAddressRidgepole(String addressRidgepole) {
        this.addressRidgepole = addressRidgepole == null ? null : addressRidgepole.trim();
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber == null ? null : addressNumber.trim();
    }

    public String getAddressRoom() {
        return addressRoom;
    }

    public void setAddressRoom(String addressRoom) {
        this.addressRoom = addressRoom == null ? null : addressRoom.trim();
    }

    public String getResidentialAreaName() {
        return residentialAreaName;
    }

    public void setResidentialAreaName(String residentialAreaName) {
        this.residentialAreaName = residentialAreaName == null ? null : residentialAreaName.trim();
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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