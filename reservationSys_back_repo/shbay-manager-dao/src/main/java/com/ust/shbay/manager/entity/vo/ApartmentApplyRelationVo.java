package com.ust.shbay.manager.entity.vo;

import com.ust.shbay.manager.entity.ApartmentReview;
import lombok.Data;

import java.util.List;

@Data
public class ApartmentApplyRelationVo {

    //单位id
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
    //单位名称
    private  String companyName;
    //租房类型名称
    private  String houseTypeName;
    //备注
    private String description;
    //1.企业委托书
    private List<TRelationVO> trustInstrumentUrl;
    //2.企业营业执照或事业企业法人登记证
    private List<TRelationVO> businessLicenseUrl;
    //3.企业承诺书
    private List<TRelationVO> pledgeUrl;
    //4.经办人身份证、入住人身份证、法人身份证
    private List<TRelationVO> agentIdCardUrl;
    //5.《上海湾区科创中心人才公寓申请表》一式四份
    private List<TRelationVO> applicationUrl;
    //6.申请人身份证、户口薄或户籍证明
    private List<TRelationVO> applicantIdCardUrl;
    //7.劳动（聘用）合同（受理之日起至少有一年的有效期）
    private List<TRelationVO> laborContractUrl;
    //  8.学历、学位、专业技术职称或职业技能等级证书
    private List<TRelationVO> certificateUrl;
    //9.其他材料（如社保记录等）
    private List<TRelationVO> otherMaterialsUrl;
    //申请或者审核历史
    private List<ApartmentReview> ApartmentReviewList;
}
