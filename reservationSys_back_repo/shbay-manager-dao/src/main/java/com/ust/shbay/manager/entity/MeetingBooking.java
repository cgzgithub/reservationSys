package com.ust.shbay.manager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class MeetingBooking {
    private Integer id;

    private String name;

    private String accountId;

    private Integer meetingId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date visitDate;

    private String beginTime;

    private String endTime;

    private Integer bookingNumber;

    private Integer companyTypeId;

    private String bookingPersion;

    private String bookingPhone;

    private String putRequirementPicId;

    private String enclosures;

//    private Integer computerNum;
//
//    private Integer tecaNum;
//
//    private Integer projectionNum;
//
//    private Integer handMicrophoneNum;
//
//    private Integer gooseneckMicrophoneNum;

    private Integer status;

    private Integer passageway;

    private Integer cancelChannel;

    private Integer cancelReasonId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;

    private Integer actualNum;

    private Integer appraise;

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

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime == null ? null : beginTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Integer getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(Integer bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public Integer getCompanyTypeId() {
        return companyTypeId;
    }

    public void setCompanyTypeId(Integer companyTypeId) {
        this.companyTypeId = companyTypeId;
    }

    public String getBookingPersion() {
        return bookingPersion;
    }

    public void setBookingPersion(String bookingPersion) {
        this.bookingPersion = bookingPersion == null ? null : bookingPersion.trim();
    }

    public String getBookingPhone() {
        return bookingPhone;
    }

    public void setBookingPhone(String bookingPhone) {
        this.bookingPhone = bookingPhone == null ? null : bookingPhone.trim();
    }

    public String getPutRequirementPicId() {
        return putRequirementPicId;
    }

    public void setPutRequirementPicId(String putRequirementPicId) {
        this.putRequirementPicId = putRequirementPicId;
    }

    public String getEnclosures() {
        return enclosures;
    }

    public void setEnclosures(String enclosures) {
        this.enclosures = enclosures;
    }

    //    public Integer getComputerNum() {
//        return computerNum;
//    }
//
//    public void setComputerNum(Integer computerNum) {
//        this.computerNum = computerNum;
//    }
//
//    public Integer getTecaNum() {
//        return tecaNum;
//    }
//
//    public void setTecaNum(Integer tecaNum) {
//        this.tecaNum = tecaNum;
//    }
//
//    public Integer getProjectionNum() {
//        return projectionNum;
//    }
//
//    public void setProjectionNum(Integer projectionNum) {
//        this.projectionNum = projectionNum;
//    }
//
//    public Integer getHandMicrophoneNum() {
//        return handMicrophoneNum;
//    }
//
//    public void setHandMicrophoneNum(Integer handMicrophoneNum) {
//        this.handMicrophoneNum = handMicrophoneNum;
//    }
//
//    public Integer getGooseneckMicrophoneNum() {
//        return gooseneckMicrophoneNum;
//    }
//
//    public void setGooseneckMicrophoneNum(Integer gooseneckMicrophoneNum) {
//        this.gooseneckMicrophoneNum = gooseneckMicrophoneNum;
//    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPassageway() {
        return passageway;
    }

    public void setPassageway(Integer passageway) {
        this.passageway = passageway;
    }

    public Integer getCancelChannel() {
        return cancelChannel;
    }

    public void setCancelChannel(Integer cancelChannel) {
        this.cancelChannel = cancelChannel;
    }

    public Integer getCancelReasonId() {
        return cancelReasonId;
    }

    public void setCancelReasonId(Integer cancelReasonId) {
        this.cancelReasonId = cancelReasonId;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Integer getActualNum() {
        return actualNum;
    }

    public void setActualNum(Integer actualNum) {
        this.actualNum = actualNum;
    }

    public Integer getAppraise() {
        return appraise;
    }

    public void setAppraise(Integer appraise) {
        this.appraise = appraise;
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}