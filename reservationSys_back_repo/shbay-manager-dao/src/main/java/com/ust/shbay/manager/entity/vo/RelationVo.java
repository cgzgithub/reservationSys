package com.ust.shbay.manager.entity.vo;

import lombok.Data;

import java.util.List;
@Data
public class RelationVo {
    //1.企业委托书
    private List<String> trustInstrumentUrl;
    //2.企业营业执照或事业企业法人登记证
    private List<String> businessLicenseUrl;
    //3.企业承诺书
    private List<String> pledgeUrl;
    //4.经办人身份证、入住人身份证、法人身份证
    private List<String> agentIdCardUrl;
    //5.《上海湾区科创中心人才公寓申请表》一式四份
    private List<String> applicationUrl;
    //6.申请人身份证、户口薄或户籍证明
    private List<String> applicantIdCardUrl;
    //7.劳动（聘用）合同（受理之日起至少有一年的有效期）
    private List<String> laborContractUrl;
    //  8.学历、学位、专业技术职称或职业技能等级证书
    private List<String> certificateUrl;
    //9.其他材料（如社保记录等）
    private List<String> otherMaterialsUrl;
}
