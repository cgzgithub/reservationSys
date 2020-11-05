package com.ust.shbay.manager.api.controller.dto.apartment;

import com.ust.shbay.manager.entity.TRelation;
import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
@Data
@EqualsAndHashCode(callSuper=false)
public class ApartmentapplyEdit extends BaseToken {

    @NotNull(message = "id不能为空！")
    private Integer id;

    //单位id
    @NotNull(message = "单位不能为空！")
    private Integer companyOriginId;

    //个人姓名
    @NotBlank(message = "个人姓名不能为空！")
    private String name;

    //手机号
    @NotBlank(message = "手机号不能为空！")
    @Pattern(regexp = "1[3456789][0-9]{9}",message = "手机号不符合格式")
    private String phone;

    //国籍
    private String nationality;

    //证件号
    @NotBlank(message = "证件号不能为空！")
    private String idNumber;

    //租房类型
    @NotNull(message = "租房类型不能为空！")
    private Integer houseType;

    //备注
    private String description;

    //1.企业委托书
    @NotNull(message = "企业委托书不能为空！")
    private List<TRelation> trustInstrumentUrl;

    //2.企业营业执照或事业企业法人登记证
    @NotNull(message = "企业营业执照或事业企业法人登记证不能为空！")
    private List<TRelation> businessLicenseUrl;

    //3.企业承诺书
    @NotNull(message = "企业承诺书不能为空！")
    private List<TRelation> pledgeUrl;

    //4.经办人身份证、入住人身份证、法人身份证
    @NotNull(message = "经办人身份证、入住人身份证、法人身份证不能为空！")
    private List<TRelation> agentIdCardUrl;

    //5.《上海湾区科创中心人才公寓申请表》一式四份
    @NotNull(message = "《上海湾区科创中心人才公寓申请表》不能为空！")
    private List<TRelation> applicationUrl;

    //6.申请人身份证、户口薄或户籍证明
    @NotNull(message = "申请人身份证、户口薄或户籍证明不能为空！")
    private List<TRelation> applicantIdCardUrl;

    //7.劳动（聘用）合同（受理之日起至少有一年的有效期）
    @NotNull(message = "劳动（聘用）合同不能为空！")
    private List<TRelation> laborContractUrl;

    //8.学历、学位、专业技术职称或职业技能等级证书
    @NotNull(message = "学历、学位、专业技术职称或职业技能等级证书不能为空！")
    private List<TRelation> certificateUrl;

    //9.其他材料（如社保记录等）
    @NotNull(message = "其他材料不能为空！")
    private List<TRelation> otherMaterialsUrl;

}
