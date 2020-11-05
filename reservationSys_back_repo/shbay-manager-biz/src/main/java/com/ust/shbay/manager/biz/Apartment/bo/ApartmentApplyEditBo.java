package com.ust.shbay.manager.biz.Apartment.bo;

import com.ust.shbay.manager.entity.TRelation;
import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@Data
@EqualsAndHashCode(callSuper=false)
public class ApartmentApplyEditBo  extends BaseUser {
    private  Integer id;
    //单位
    private Integer companyOriginId;
    //个人姓名
    private String name;
    //手机号
    private String phone;
    //国籍
    private String nationality;
    //证件号
    private String idNumber;
    //租房类型
    private Integer houseType;
    //备注
    private String description;
    //1.企业委托书
    private List<TRelation> trustInstrumentUrl;
    //2.企业营业执照或事业企业法人登记证
    private List<TRelation> businessLicenseUrl;
    //3.企业承诺书
    private List<TRelation> pledgeUrl;
    //4.经办人身份证、入住人身份证、法人身份证
    private List<TRelation> agentIdCardUrl;
    //5.《上海湾区科创中心人才公寓申请表》一式四份
    private List<TRelation> applicationUrl;
    //6.申请人身份证、户口薄或户籍证明
    private List<TRelation> applicantIdCardUrl;
    //7.劳动（聘用）合同（受理之日起至少有一年的有效期）
    private List<TRelation> laborContractUrl;
    //  8.学历、学位、专业技术职称或职业技能等级证书
    private List<TRelation> certificateUrl;
    //9.其他材料（如社保记录等）
    private List<TRelation> otherMaterialsUrl;
}
