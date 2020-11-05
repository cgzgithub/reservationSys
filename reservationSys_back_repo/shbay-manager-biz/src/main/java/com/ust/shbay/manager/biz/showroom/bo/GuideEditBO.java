package com.ust.shbay.manager.biz.showroom.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
@Data
@EqualsAndHashCode(callSuper=false)
public class GuideEditBO extends BaseUser {
    private  Integer id;
    //讲解人手机号
    private String phone;
    //讲解人姓名
    private String name;
    //


}
