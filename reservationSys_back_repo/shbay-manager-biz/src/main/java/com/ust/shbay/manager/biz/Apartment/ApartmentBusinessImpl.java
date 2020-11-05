package com.ust.shbay.manager.biz.Apartment;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.exception.SException;
import com.ust.shbay.common.utils.DateUtil;
import com.ust.shbay.common.utils.ExcelUtil;
import com.ust.shbay.manager.biz.Apartment.bo.*;
import com.ust.shbay.manager.dao.*;
import com.ust.shbay.manager.entity.vo.*;
import com.ust.shbay.manager.biz.file.RelationBusiness;
import com.ust.shbay.manager.entity.*;
import com.ust.shbay.manager.entity.vo.ApartmentApplyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Service
public class ApartmentBusinessImpl implements ApartmentBusiness {
    @Autowired
    private ApartmentMapper apartmentMapper;
    @Autowired
    private RelationBusiness relationBusiness;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private ApartmentApplyMapper apartmentApplyMapper;
    @Autowired
    private ApartmentReviewMapper apartmentReviewMapper;
    @Autowired
    private DictInfoMapper dictInfoMapper;

    //增加公寓
    @Transactional
    @Override
    public void addApartment(ApartmentAddBO apartmentAddBO) {
        Apartment apartment = new Apartment();
        BeanUtils.copyProperties(apartmentAddBO, apartment);
        apartment.setDelFlag(Constant.Status.NORMAL);
        apartment.setCreateUser(apartmentAddBO.getAccount());
        apartment.setUpdateUser(apartmentAddBO.getAccount());
        apartmentMapper.insertSelective(apartment);
        //添加公寓照片url
        if (apartmentAddBO.getPutPicUrls() != null && apartmentAddBO.getPutPicUrls().size() > 0) {
            for (TRelation tRelation : apartmentAddBO.getPutPicUrls()) {
                tRelation.setType(Constant.RELATION_TYPE.APARTMENT);
                tRelation.setCreateUser(apartmentAddBO.getAccount());
                tRelation.setUpdateUser(apartmentAddBO.getAccount());
                tRelation.setOriginalId(apartment.getId());
                relationBusiness.insertRelation(tRelation);
            }
        }
    }

    //编辑公寓
    @Override
    @Transactional
    public void editApartment(ApartmentEditBO apartmentEditBO) {
        Integer apartmentid = apartmentEditBO.getId();
        Apartment apartment = apartmentMapper.selectById(apartmentid);
        if (apartment != null) {
            Apartment entity = new Apartment();
            BeanUtils.copyProperties(apartmentEditBO, entity);
            entity.setUpdateUser(apartmentEditBO.getAccount());
            apartmentMapper.updateByPrimaryKeySelective(entity);
        } else {
            throw new SException("公寓不存在！");
        }
        //查询原有照片
        List<String> oldList = relationBusiness.searchRelation(Constant.RELATION_TYPE.APARTMENT, apartmentEditBO.getId());
        relationBusiness.updateAttachment(oldList, apartmentEditBO.getPutPicUrls(),
                Constant.RELATION_TYPE.APARTMENT, apartmentEditBO.getId(), apartmentEditBO.getAccount());
//        relationBusiness.deleteRelation(apartmentEditBO.getId(), Constant.RELATION_TYPE.APARTMENT);
//        if (apartmentEditBO.getPutPicUrls() != null && apartmentEditBO.getPutPicUrls().size() > 0) {
//            //添加摆放要求
//            for (String url : apartmentEditBO.getPutPicUrls()) {
//                TRelation tRelation = new TRelation();
//                tRelation.setType(Constant.RELATION_TYPE.MEETING);
//                tRelation.setFileUrl(url);
//                tRelation.setCreateUser(apartmentEditBO.getAccount());
//                tRelation.setUpdateUser(apartmentEditBO.getAccount());
//                tRelation.setOriginalId(apartment.getId());
//                relationBusiness.insertRelation(tRelation);
//            }
//        }
    }

    //删除公寓
    @Override
    @Transactional
    public void delApartment(ApartmentDelBo apartmentDelBo) {
        Integer apartmentId = apartmentDelBo.getId();
        Apartment apartment = apartmentMapper.selectById(apartmentId);
        if (apartment != null) {
            apartment.setUpdateUser(apartmentDelBo.getAccount());
            apartment.setDelFlag(Constant.Status.DEL);
            apartmentMapper.updateByPrimaryKeySelective(apartment);
        } else {
            throw new SException("公寓不存在");
        }
        //删除公寓照片
        relationBusiness.deleteRelation(apartmentId, Constant.RELATION_TYPE.APARTMENT);
    }

    //条件查询公寓
    @Override
    public PageInfo<Apartment> getApartmentByCondition(ApartmentQueryBo apartmentQueryBo) {
        ApartmentExample example = new ApartmentExample();
        ApartmentExample.Criteria criteria = example.createCriteria();
        if (apartmentQueryBo.getStatus() != null && !"".equals(apartmentQueryBo.getStatus())) {
            criteria.andStatusEqualTo(apartmentQueryBo.getStatus());
        }
        if (apartmentQueryBo.getHouseType() != null && !"".equals(apartmentQueryBo.getHouseType())) {
            criteria.andHouseTypeEqualTo(apartmentQueryBo.getHouseType());
        }
        if (apartmentQueryBo.getResidentialAreaName() != null && !"".equals(apartmentQueryBo.getResidentialAreaName())) {
            criteria.andResidentialAreaNameLike("%" + apartmentQueryBo.getResidentialAreaName() + "%");
        }
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        example.setOrderByClause("update_time desc");
        PageHelper.startPage(apartmentQueryBo.getPageNumber(), apartmentQueryBo.getPageSize());
        List<Apartment> apartmentList = apartmentMapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo(apartmentList);
        List<ApartmentVO> apartmentVOList = new ArrayList<>();
        for (Apartment apartment : apartmentList) {
            ApartmentVO apartmentVO = new ApartmentVO();
            BeanUtils.copyProperties(apartment, apartmentVO);
            //关联照片
            List<TRelationVO> tRelationList = relationBusiness.searchRelationList(Constant.RELATION_TYPE.APARTMENT, apartment.getId());
            apartmentVO.setTRelation(tRelationList);
            //关联户型字典
            if (apartmentVO.getHouseType() != null) {
                apartmentVO.setHouseTypeName(dictInfoMapper.selectTitleById(apartmentVO.getHouseType()));
            }
            apartmentVOList.add(apartmentVO);
        }
        pageInfo.setList(apartmentVOList);

        return pageInfo;
    }

    //添加公司
    @Override
    public void addCompany(CompanyAddBo companyAddBo) {
        Company company = companyMapper.selectByCreditRecognitionId(companyAddBo.getCreditRecognitionId());
        if (company == null) {
            Company entity = new Company();
            BeanUtils.copyProperties(companyAddBo, entity);
            entity.setDelFlag(Constant.Status.NORMAL);
            entity.setCreateUser(companyAddBo.getAccount());
            entity.setUpdateUser(companyAddBo.getAccount());
            companyMapper.insertSelective(entity);
        } else {
            throw new SException("该公司已存在！");
        }
    }

    //删除公司
    @Override
    public void delCompany(CompanyDelBo companyDelBo) {
        Company company = companyMapper.selectById(companyDelBo.getId());
        if (company != null) {
            BeanUtils.copyProperties(companyDelBo, company);
            company.setDelFlag(Constant.Status.DEL);
            company.setUpdateUser(companyDelBo.getAccount());
            companyMapper.updateById(company);
        } else {
            throw new SException("该公司不存在！");
        }
    }

    //编辑公司
    @Override
    public void editCompany(CompanyEditBo companyEditoBo) {
        Integer companyEditoBoId = companyEditoBo.getId();
        Company company = companyMapper.selectById(companyEditoBoId);
        if (company != null) {
            Company entity = new Company();
            BeanUtils.copyProperties(companyEditoBo, entity);
            entity.setCreditRecognitionId(companyEditoBo.getCreditRecognitionId());
            entity.setUpdateUser(companyEditoBo.getAccount());
            entity.setCompanyName(companyEditoBo.getCompanyName());
            entity.setTotalNumber(companyEditoBo.getTotalNumber());
            companyMapper.updateById(entity);
        } else {
            throw new SException("该公司不存在！");
        }
    }

    /**
     * 按条件查询预约情况
     *
     * @param companyQueryBo
     * @return
     */
    @Override
    public PageInfo<ApartmentTotalAndRemainder> queryCompanyList(CompanyQueryBo companyQueryBo) {
        PageHelper.startPage(companyQueryBo.getPageNumber(), companyQueryBo.getPageSize());
        List<ApartmentTotalAndRemainder> apartmentTotalAndRemainderList = companyMapper.selectByCompanyName(companyQueryBo.getCompanyName());
        PageInfo pageInfo = new PageInfo(apartmentTotalAndRemainderList);
        pageInfo.setList(apartmentTotalAndRemainderList);
        return pageInfo;
    }

    //新增公寓申请
    @Override
    public void addApplyApartment(ApplyApartmentAddBo applyApartmentaddBo) {
        ApartmentApply apartmentApply = new ApartmentApply();
        BeanUtils.copyProperties(applyApartmentaddBo, apartmentApply);
        apartmentApply.setCompanyOriginId(apartmentApply.getCompanyOriginId());
        apartmentApply.setDelFlag(Constant.Status.NORMAL);
        apartmentApply.setCreateUser(applyApartmentaddBo.getAccount());
        apartmentApply.setUpdateUser(applyApartmentaddBo.getAccount());
        apartmentApplyMapper.insertSelective(apartmentApply);
        ////1.企业委托书ADD
        if (applyApartmentaddBo.getTrustInstrumentUrl() != null && applyApartmentaddBo.getTrustInstrumentUrl().size() > 0) {
            for (TRelation tRelation : applyApartmentaddBo.getTrustInstrumentUrl()) {
                tRelation.setType(Constant.RELATION_TYPE.TRUST_INSTRUMENT);
                tRelation.setCreateUser(applyApartmentaddBo.getAccount());
                tRelation.setUpdateUser(applyApartmentaddBo.getAccount());
                tRelation.setOriginalId(apartmentApply.getId());
                relationBusiness.insertRelation(tRelation);
            }
        }
        //2.企业营业执照或事业企业法人登记证
        if (applyApartmentaddBo.getBusinessLicenseUrl() != null && applyApartmentaddBo.getBusinessLicenseUrl().size() > 0) {
            for (TRelation tRelation : applyApartmentaddBo.getBusinessLicenseUrl()) {
                tRelation.setType(Constant.RELATION_TYPE.BUSSINESS_LICENSE);
                tRelation.setCreateUser(applyApartmentaddBo.getAccount());
                tRelation.setUpdateUser(applyApartmentaddBo.getAccount());
                tRelation.setOriginalId(apartmentApply.getId());
                relationBusiness.insertRelation(tRelation);
            }
        }
        //3.企业承诺书
        if (applyApartmentaddBo.getPledgeUrl() != null && applyApartmentaddBo.getPledgeUrl().size() > 0) {
            for (TRelation tRelation : applyApartmentaddBo.getPledgeUrl()) {
                tRelation.setType(Constant.RELATION_TYPE.PLEDGE);
                tRelation.setCreateUser(applyApartmentaddBo.getAccount());
                tRelation.setUpdateUser(applyApartmentaddBo.getAccount());
                tRelation.setOriginalId(apartmentApply.getId());
                relationBusiness.insertRelation(tRelation);
            }
        }
        //4.经办人身份证、入住人身份证、法人身份证
        if (applyApartmentaddBo.getAgentIdCardUrl() != null && applyApartmentaddBo.getAgentIdCardUrl().size() > 0) {
            for (TRelation tRelation : applyApartmentaddBo.getAgentIdCardUrl()) {
                tRelation.setType(Constant.RELATION_TYPE.AGENT_ID_CARD);
                tRelation.setCreateUser(applyApartmentaddBo.getAccount());
                tRelation.setUpdateUser(applyApartmentaddBo.getAccount());
                tRelation.setOriginalId(apartmentApply.getId());
                relationBusiness.insertRelation(tRelation);
            }
        }
        //5.《上海湾区科创中心人才公寓申请表》一式四份
        if (applyApartmentaddBo.getApplicationUrl() != null && applyApartmentaddBo.getApplicationUrl().size() > 0) {
            for (TRelation tRelation : applyApartmentaddBo.getApplicationUrl()) {
                tRelation.setType(Constant.RELATION_TYPE.APPLICATION);
                tRelation.setCreateUser(applyApartmentaddBo.getAccount());
                tRelation.setUpdateUser(applyApartmentaddBo.getAccount());
                tRelation.setOriginalId(apartmentApply.getId());
                relationBusiness.insertRelation(tRelation);
            }
        }
        //6.申请人身份证、户口薄或户籍证明
        if (applyApartmentaddBo.getAgentIdCardUrl() != null && applyApartmentaddBo.getApplicantIdCardUrl().size() > 0) {
            for (TRelation tRelation : applyApartmentaddBo.getApplicantIdCardUrl()) {
                tRelation.setType(Constant.RELATION_TYPE.AppLICANT_ID_CARD);
                tRelation.setCreateUser(applyApartmentaddBo.getAccount());
                tRelation.setUpdateUser(applyApartmentaddBo.getAccount());
                tRelation.setOriginalId(apartmentApply.getId());
                relationBusiness.insertRelation(tRelation);
            }
        }
        //7.劳动（聘用）合同（受理之日起至少有一年的有效期）
        if (applyApartmentaddBo.getLaborContractUrl() != null && applyApartmentaddBo.getLaborContractUrl().size() > 0) {
            for (TRelation tRelation : applyApartmentaddBo.getLaborContractUrl()) {
                tRelation.setType(Constant.RELATION_TYPE.LABOR_CONTRACT);
                tRelation.setCreateUser(applyApartmentaddBo.getAccount());
                tRelation.setUpdateUser(applyApartmentaddBo.getAccount());
                tRelation.setOriginalId(apartmentApply.getId());
                relationBusiness.insertRelation(tRelation);
            }
        }
        //  8.学历、学位、专业技术职称或职业技能等级证书
        if (applyApartmentaddBo.getCertificateUrl() != null && applyApartmentaddBo.getCertificateUrl().size() > 0) {
            for (TRelation tRelation : applyApartmentaddBo.getCertificateUrl()) {
                tRelation.setType(Constant.RELATION_TYPE.CERTIFICATE);
                tRelation.setCreateUser(applyApartmentaddBo.getAccount());
                tRelation.setUpdateUser(applyApartmentaddBo.getAccount());
                tRelation.setOriginalId(apartmentApply.getId());
                relationBusiness.insertRelation(tRelation);
            }
        }
        //9.其他材料（如社保记录等）
        if (applyApartmentaddBo.getOtherMaterialsUrl() != null && applyApartmentaddBo.getOtherMaterialsUrl().size() > 0) {
            for (TRelation tRelation : applyApartmentaddBo.getOtherMaterialsUrl()) {
                tRelation.setType(Constant.RELATION_TYPE.OTHER_MATERIALS);
                tRelation.setCreateUser(applyApartmentaddBo.getAccount());
                tRelation.setUpdateUser(applyApartmentaddBo.getAccount());
                tRelation.setOriginalId(apartmentApply.getId());
                relationBusiness.insertRelation(tRelation);
            }
        }
    }


    /**
     * 查询公寓审核历史列表
     *
     * @param apartmentReviewHiQueryBO
     * @return
     */
    @Override
    public PageInfo<ApartmentReviewVO> queryReview(ApartmentReviewHiQueryBO apartmentReviewHiQueryBO) {

        ApartmentReviewHiQueryDO apartmentReviewHiQueryDO = new ApartmentReviewHiQueryDO();
        BeanUtils.copyProperties(apartmentReviewHiQueryBO, apartmentReviewHiQueryDO);
        //处理公司名
        if (apartmentReviewHiQueryBO.getCompanyName() != null && !"".equals(apartmentReviewHiQueryBO.getCompanyName())) {
            apartmentReviewHiQueryDO.setCompanyName("%" + apartmentReviewHiQueryBO.getCompanyName() + "%");
        }
        //处理申请人
        if (apartmentReviewHiQueryBO.getName() != null && !"".equals(apartmentReviewHiQueryBO.getName())) {
            apartmentReviewHiQueryDO.setName("%" + apartmentReviewHiQueryBO.getName() + "%");
        }
        PageHelper.startPage(apartmentReviewHiQueryBO.getPageNumber(), apartmentReviewHiQueryBO.getPageSize());
        List<ApartmentReviewVO> list = apartmentReviewMapper.selectByCondition(apartmentReviewHiQueryDO);

        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }


    /**
     * 查询公寓申请列表
     *
     * @param applyApartmentSearchBo
     * @return
     */
    @Override
    public PageInfo<ApartmentApplyVo> searchApplyApartment(ApplyApartmentSearchBo applyApartmentSearchBo) {
        ApartmentApplyExample example = new ApartmentApplyExample();
        ApartmentApplyExample.Criteria criteria = example.createCriteria();
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        Calendar ca2 = Calendar.getInstance();
        cal.setTime(date);//设置起时间
        ca2.setTime(date);
        cal.add(Calendar.MONTH, 1);//增加一月
        ca2.add(Calendar.MONTH, 3);//增加3月
        if (applyApartmentSearchBo.getStatus() != null) {
            criteria.andStatusEqualTo(applyApartmentSearchBo.getStatus());
        }
        if (applyApartmentSearchBo.getContractStatus() != null) {
            criteria.andContractStatusEqualTo(applyApartmentSearchBo.getContractStatus());
        }
        //到期时间0为1个月内
        if (applyApartmentSearchBo.getDueDate() != null && applyApartmentSearchBo.getDueDate() == 0) {
            criteria.andContractEndDateLessThanOrEqualTo(cal.getTime());
        }
        //到期时间1为1-3个月内
        if (applyApartmentSearchBo.getDueDate() != null && applyApartmentSearchBo.getDueDate() == 1) {
            criteria.andContractEndDateBetween(cal.getTime(), ca2.getTime());
        }
        //到期时间2为3个月以上
        if (applyApartmentSearchBo.getDueDate() != null && applyApartmentSearchBo.getDueDate() == 2) {
            criteria.andContractEndDateGreaterThanOrEqualTo(ca2.getTime());
        }
        if (applyApartmentSearchBo.getHouseType() != null) {
            criteria.andHouseTypeEqualTo(applyApartmentSearchBo.getHouseType());
        }
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        //公司名称关联公司id
        if (applyApartmentSearchBo.getCompanyName() != null && !"".equals(applyApartmentSearchBo.getCompanyName())) {
            CompanyExample companyExample = new CompanyExample();
            CompanyExample.Criteria criteria1 = companyExample.createCriteria();
            criteria1.andCompanyNameLike('%' + applyApartmentSearchBo.getCompanyName() + '%');
            List<Company> apartmentApplyVoList = companyMapper.selectByExample(companyExample);
            if (apartmentApplyVoList.size() > 0) {
                List ID = new ArrayList();
                for (int i = 0; i < apartmentApplyVoList.size(); i++) {
                    ID.add(apartmentApplyVoList.get(i).getId());
                }
                criteria.andCompanyOriginIdIn(ID);
            } else {
                return new PageInfo();
            }
        }
        if (applyApartmentSearchBo.getName() != null && !"".equals(applyApartmentSearchBo.getName())) {
            criteria.andNameLike('%' + applyApartmentSearchBo.getName() + '%');
        }
        example.setOrderByClause("update_time desc");
        PageHelper.startPage(applyApartmentSearchBo.getPageNumber(), applyApartmentSearchBo.getPageSize());
        List<ApartmentApply> apartmentApplyList = apartmentApplyMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(apartmentApplyList);
        List<ApartmentApplyVo> apartmentApplyVoList = new ArrayList();
        //关联单位名称与房屋类型
        for (int i = 0; i < apartmentApplyList.size(); i++) {
            ApartmentApplyVo apartmentApplyVo = new ApartmentApplyVo();
            BeanUtils.copyProperties(apartmentApplyList.get(i), apartmentApplyVo);
            apartmentApplyVo.setCompanyName(companyMapper.selectCompanyNameById(apartmentApplyList.get(i).getCompanyOriginId()));
            apartmentApplyVo.setHouseTypeName(dictInfoMapper.selectTitleById(apartmentApplyList.get(i).getHouseType()));
            if ((apartmentApplyList.get(i).getContractBeginDate() != null && apartmentApplyList.get(i).getContractEndDate() != null)) {
                //相差天数
                long it = apartmentApplyList.get(i).getContractEndDate().getTime() / (1000 * 24 * 60 * 60) - apartmentApplyList.get(i).getContractBeginDate().getTime() / (1000 * 24 * 60 * 60);
                //年数
                long nd = 365;//每年毫秒数
                long nh = 30;//每月毫秒数
                long year = it / nd; // 计算差多少年 367/365=1
                long month = it % nd / nh;// 计算差多少月 366%365=2 2/30=0
                long day = it % nd % nh;// 计算差多少天
                if (year > 0) {
                    String TERM = year + "年" + month + "月" + day + "天";
                    apartmentApplyVo.setTerm(TERM);
                } else if (month > 0) {
                    String TERM = month + "月" + day + "天";
                    apartmentApplyVo.setTerm(TERM);
                } else if (day >= 0) {
                    String TERM = day + "天";
                    apartmentApplyVo.setTerm(TERM);
                }
            }
            //关联状态，到期提醒使用
            if (apartmentApplyVo.getContractEndDate() != null && apartmentApplyVo.getContractEndDate().before(cal.getTime())
                    && apartmentApplyVo.getContractStatus() == Constant.APARTMENT_CONTRACT_STATUS.EXECUTING) {
                //已过期或1月内，且合同状态为执行中
                apartmentApplyVo.setDueDate(0);
            } else if (apartmentApplyVo.getContractEndDate() != null && apartmentApplyVo.getContractEndDate().after(cal.getTime())
                    && apartmentApplyVo.getContractEndDate().before(ca2.getTime())
                    && apartmentApplyVo.getContractStatus() == Constant.APARTMENT_CONTRACT_STATUS.EXECUTING) {
                //1-3个月，且合同状态为执行中
                apartmentApplyVo.setDueDate(1);
            } else {
                //无合同或大于3个月或状态为已解约或已到期
                apartmentApplyVo.setDueDate(2);
            }
            //关联公寓地址
            if (apartmentApplyVo.getApartmentId() != null) {
                Apartment apartment = apartmentMapper.selectById(apartmentApplyVo.getApartmentId());
                if (apartment != null) {
                    apartmentApplyVo.setApartmentAddress(apartment.getResidentialAreaName()
                            + apartment.getAddressRidgepole() + "栋" + apartment.getAddressNumber() + "号"
                            + apartment.getAddressRoom()
                    );
                }
            }

            apartmentApplyVoList.add(apartmentApplyVo);
        }
        pageInfo.setList(apartmentApplyVoList);
        return pageInfo;
    }

//    //编辑附件函数
//    private void updateAttachment(List<String> list1,List<String> list2,String  type,ApartmentApplyEditBo apartmentApplyEditBo) {
//        //取交集
//        List<String> copyList1=new ArrayList(list1);
//        list1.retainAll(list2);//list1为交集，list2未动（取交集的部分）
//        list2.removeAll(list1);//差集（list2取出的差集要插入到里面）需插入
//        //需要插入的操作，插入list2
//        for (String url : list2) {
//            if (url!=null&&!"".equals(url)) {
//                TRelation tRelation = new TRelation();
//                tRelation.setType(type);
//                tRelation.setFileUrl(url);
//                tRelation.setCreateUser(apartmentApplyEditBo.getAccount());
//                tRelation.setUpdateUser(apartmentApplyEditBo.getAccount());
//                tRelation.setOriginalId(apartmentApplyEditBo.getId());
//                relationBusiness.insertRelation(tRelation);
//            }
//        }
//        copyList1.removeAll(list1);//差集（list1中除去与list2交集的部分）需删除
//        for (String fileUrl : copyList1) {
//           relationBusiness.deleteAttachment(apartmentApplyEditBo.getId(),type,fileUrl);
//        }
//    }

    /**
     * 编辑公寓申请
     *
     * @param apartmentapplyEditBo
     * @return
     */
    @Override
    @Transactional
    public void editApartmentapplylist(ApartmentApplyEditBo apartmentapplyEditBo) {
        ApartmentApply apartmentApply = apartmentApplyMapper.selectById(apartmentapplyEditBo.getId());
        if (apartmentApply != null) {
            ApartmentApply entity = new ApartmentApply();
            BeanUtils.copyProperties(apartmentapplyEditBo, entity);
            entity.setUpdateUser(apartmentapplyEditBo.getAccount());
            entity.setUpdateTime(new Date());
            apartmentApplyMapper.update(entity);
            RelationVo relationVo = serchApartmentMapper(apartmentapplyEditBo.getId());
            //创建一个附件对象
            Attachment attachment = new Attachment();
            BeanUtils.copyProperties(relationVo, attachment);
            //删除且增加附件内容
            relationBusiness.updateAttachment(attachment.getAgentIdCardUrl(), apartmentapplyEditBo.getAgentIdCardUrl(),
                    Constant.RELATION_TYPE.AGENT_ID_CARD, apartmentapplyEditBo.getId(), apartmentapplyEditBo.getAccount());
            relationBusiness.updateAttachment(attachment.getPledgeUrl(), apartmentapplyEditBo.getPledgeUrl(), Constant.RELATION_TYPE.PLEDGE,
                    apartmentapplyEditBo.getId(), apartmentapplyEditBo.getAccount());
            relationBusiness.updateAttachment(attachment.getTrustInstrumentUrl(), apartmentapplyEditBo.getTrustInstrumentUrl(),
                    Constant.RELATION_TYPE.TRUST_INSTRUMENT, apartmentapplyEditBo.getId(), apartmentapplyEditBo.getAccount());
            relationBusiness.updateAttachment(attachment.getOtherMaterialsUrl(), apartmentapplyEditBo.getOtherMaterialsUrl(),
                    Constant.RELATION_TYPE.OTHER_MATERIALS, apartmentapplyEditBo.getId(), apartmentapplyEditBo.getAccount());
            relationBusiness.updateAttachment(attachment.getLaborContractUrl(), apartmentapplyEditBo.getLaborContractUrl(),
                    Constant.RELATION_TYPE.LABOR_CONTRACT, apartmentapplyEditBo.getId(), apartmentapplyEditBo.getAccount());
            relationBusiness.updateAttachment(attachment.getCertificateUrl(), apartmentapplyEditBo.getCertificateUrl(),
                    Constant.RELATION_TYPE.CERTIFICATE, apartmentapplyEditBo.getId(), apartmentapplyEditBo.getAccount());
            relationBusiness.updateAttachment(attachment.getBusinessLicenseUrl(), apartmentapplyEditBo.getBusinessLicenseUrl(),
                    Constant.RELATION_TYPE.BUSSINESS_LICENSE, apartmentapplyEditBo.getId(), apartmentapplyEditBo.getAccount());
            relationBusiness.updateAttachment(attachment.getApplicationUrl(), apartmentapplyEditBo.getApplicationUrl(),
                    Constant.RELATION_TYPE.APPLICATION, apartmentapplyEditBo.getId(), apartmentapplyEditBo.getAccount());
            relationBusiness.updateAttachment(attachment.getApplicantIdCardUrl(), apartmentapplyEditBo.getApplicantIdCardUrl(),
                    Constant.RELATION_TYPE.AppLICANT_ID_CARD, apartmentapplyEditBo.getId(), apartmentapplyEditBo.getAccount());
        }
    }

    /**
     * 克隆不通过申请
     *
     * @param applyApartmentCloneBO
     * @return
     */
    @Override
    public void cloneApplyApartment(ApplyApartmentCloneBO applyApartmentCloneBO) {
        ApartmentApply apartmentApply = apartmentApplyMapper.selectById(applyApartmentCloneBO.getId());
        if (apartmentApply != null) {
            ApartmentApply entity = new ApartmentApply();
            entity.setCompanyOriginId(apartmentApply.getCompanyOriginId());
            entity.setName(apartmentApply.getName());
            entity.setPhone(apartmentApply.getPhone());
            entity.setNationality(apartmentApply.getNationality());
            entity.setIdNumber(apartmentApply.getIdNumber());
            entity.setHouseType(apartmentApply.getHouseType());
            entity.setStatus(apartmentApply.getStatus());
            entity.setCreateUser(applyApartmentCloneBO.getAccount());
            entity.setUpdateUser(applyApartmentCloneBO.getAccount());
            entity.setStatus(Constant.APARTMENT_STATUS_TYPE.NOT_SUBMITTED_REVIEW);
            apartmentApplyMapper.insertSelective(entity);
            //查询附件
            ApartmentApplyRelationVo attachment = serchApartmentApplyRelation(applyApartmentCloneBO.getId());
            //1.企业委托书ADD
            if (attachment.getTrustInstrumentUrl() != null && attachment.getTrustInstrumentUrl().size() > 0) {
                for (TRelationVO tRelationVO : attachment.getTrustInstrumentUrl()) {
                    TRelation tRelation = new TRelation();
                    tRelation.setType(Constant.RELATION_TYPE.TRUST_INSTRUMENT);
                    tRelation.setFileUrl(tRelationVO.getFileUrl());
                    tRelation.setFileName(tRelationVO.getFileName());
                    tRelation.setCreateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setUpdateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setOriginalId(entity.getId());
                    relationBusiness.insertRelation(tRelation);
                }
            }
            //2.企业营业执照或事业企业法人登记证
            if (attachment.getBusinessLicenseUrl() != null && attachment.getBusinessLicenseUrl().size() > 0) {
                for (TRelationVO tRelationVO : attachment.getBusinessLicenseUrl()) {
                    TRelation tRelation = new TRelation();
                    tRelation.setType(Constant.RELATION_TYPE.BUSSINESS_LICENSE);
                    tRelation.setFileUrl(tRelationVO.getFileUrl());
                    tRelation.setFileName(tRelationVO.getFileName());
                    tRelation.setCreateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setUpdateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setOriginalId(entity.getId());
                    relationBusiness.insertRelation(tRelation);
                }
            }
            //3.企业承诺书
            if (attachment.getPledgeUrl() != null && attachment.getPledgeUrl().size() > 0) {
                for (TRelationVO tRelationVO : attachment.getPledgeUrl()) {
                    TRelation tRelation = new TRelation();
                    tRelation.setType(Constant.RELATION_TYPE.PLEDGE);
                    tRelation.setFileUrl(tRelationVO.getFileUrl());
                    tRelation.setFileName(tRelationVO.getFileName());
                    tRelation.setCreateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setUpdateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setOriginalId(entity.getId());
                    relationBusiness.insertRelation(tRelation);
                }
            }
            //4.经办人身份证、入住人身份证、法人身份证
            if (attachment.getAgentIdCardUrl() != null && attachment.getAgentIdCardUrl().size() > 0) {
                for (TRelationVO tRelationVO : attachment.getAgentIdCardUrl()) {
                    TRelation tRelation = new TRelation();
                    tRelation.setType(Constant.RELATION_TYPE.AGENT_ID_CARD);
                    tRelation.setFileUrl(tRelationVO.getFileUrl());
                    tRelation.setFileName(tRelationVO.getFileName());
                    tRelation.setCreateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setUpdateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setOriginalId(entity.getId());
                    relationBusiness.insertRelation(tRelation);
                }
            }
            //5.《上海湾区科创中心人才公寓申请表》一式四份
            if (attachment.getApplicationUrl() != null && attachment.getApplicationUrl().size() > 0) {
                for (TRelationVO tRelationVO : attachment.getApplicationUrl()) {
                    TRelation tRelation = new TRelation();
                    tRelation.setType(Constant.RELATION_TYPE.APPLICATION);
                    tRelation.setFileUrl(tRelationVO.getFileUrl());
                    tRelation.setFileName(tRelationVO.getFileName());
                    tRelation.setCreateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setUpdateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setOriginalId(entity.getId());
                    relationBusiness.insertRelation(tRelation);
                }
            }
            //6.申请人身份证、户口薄或户籍证明
            if (attachment.getAgentIdCardUrl() != null && attachment.getApplicantIdCardUrl().size() > 0) {
                for (TRelationVO tRelationVO : attachment.getApplicantIdCardUrl()) {
                    TRelation tRelation = new TRelation();
                    tRelation.setType(Constant.RELATION_TYPE.AppLICANT_ID_CARD);
                    tRelation.setFileUrl(tRelationVO.getFileUrl());
                    tRelation.setFileName(tRelationVO.getFileName());
                    tRelation.setCreateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setUpdateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setOriginalId(entity.getId());
                    relationBusiness.insertRelation(tRelation);
                }
            }
            //7.劳动（聘用）合同（受理之日起至少有一年的有效期）
            if (attachment.getLaborContractUrl() != null && attachment.getLaborContractUrl().size() > 0) {
                for (TRelationVO tRelationVO : attachment.getLaborContractUrl()) {
                    TRelation tRelation = new TRelation();
                    tRelation.setType(Constant.RELATION_TYPE.LABOR_CONTRACT);
                    tRelation.setFileUrl(tRelationVO.getFileUrl());
                    tRelation.setFileName(tRelationVO.getFileName());
                    tRelation.setCreateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setUpdateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setOriginalId(entity.getId());
                    relationBusiness.insertRelation(tRelation);
                }
            }
            //  8.学历、学位、专业技术职称或职业技能等级证书
            if (attachment.getCertificateUrl() != null && attachment.getCertificateUrl().size() > 0) {
                for (TRelationVO tRelationVO : attachment.getCertificateUrl()) {
                    TRelation tRelation = new TRelation();
                    tRelation.setType(Constant.RELATION_TYPE.CERTIFICATE);
                    tRelation.setFileUrl(tRelationVO.getFileUrl());
                    tRelation.setFileName(tRelationVO.getFileName());
                    tRelation.setCreateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setUpdateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setOriginalId(entity.getId());
                    relationBusiness.insertRelation(tRelation);
                }
            }
            //9.其他材料（如社保记录等）
            if (attachment.getOtherMaterialsUrl() != null && attachment.getOtherMaterialsUrl().size() > 0) {
                for (TRelationVO tRelationVO : attachment.getOtherMaterialsUrl()) {
                    TRelation tRelation = new TRelation();
                    tRelation.setType(Constant.RELATION_TYPE.OTHER_MATERIALS);
                    tRelation.setFileUrl(tRelationVO.getFileUrl());
                    tRelation.setFileName(tRelationVO.getFileName());
                    tRelation.setCreateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setUpdateUser(applyApartmentCloneBO.getAccount());
                    tRelation.setOriginalId(entity.getId());
                    relationBusiness.insertRelation(tRelation);
                }
            }
        } else {
            throw new SException("申请不存在或者已删除！");
        }
    }

    /**
     * 提审
     *
     * @param apartmentapplyarraignmentBo
     * @return
     */
    @Override
    public void arraignmentApartmentapply(ApartmentapplyarraignmentBo apartmentapplyarraignmentBo) {
        Integer CompanyOriginalId = apartmentApplyMapper.selectCompanyOriginalIdbyId(apartmentapplyarraignmentBo.getId());
        Company company = companyMapper.selectById(CompanyOriginalId);
        if (company == null) {
            throw new SException("公司不存在！");
        }

//        //查询到该公司是否可以提审
//        Integer count = apartmentApplyMapper.selectCount(CompanyOriginalId);
//        //可以提审
//        if (company.getTotalNumber() != null && company.getTotalNumber() > count) {

//            //附件情况
//            RelationVo relationVo = serchApartmentMapper(apartmentapplyarraignmentBo.getId());

        ApartmentApply apartmentApply = apartmentApplyMapper.selectById(apartmentapplyarraignmentBo.getId());
        BeanUtils.copyProperties(apartmentapplyarraignmentBo, apartmentApply);
        //状态为0可提审
        if (apartmentApply.getStatus() == 0
//                    && relationVo.getAgentIdCardUrl() != null && !relationVo.getAgentIdCardUrl().isEmpty()
//                    && relationVo.getTrustInstrumentUrl() != null && !relationVo.getTrustInstrumentUrl().isEmpty()
//                    && relationVo.getApplicantIdCardUrl() != null && !relationVo.getApplicantIdCardUrl().isEmpty()
//                    && relationVo.getApplicationUrl() != null && !relationVo.getApplicationUrl().isEmpty()
//                    && relationVo.getBusinessLicenseUrl() != null && !relationVo.getBusinessLicenseUrl().isEmpty()
//                    && relationVo.getCertificateUrl() != null && !relationVo.getCertificateUrl().isEmpty()
//                    && relationVo.getOtherMaterialsUrl() != null && !relationVo.getOtherMaterialsUrl().isEmpty()
//                    && relationVo.getLaborContractUrl() != null && !relationVo.getLaborContractUrl().isEmpty()
//                    && relationVo.getPledgeUrl() != null && !relationVo.getPledgeUrl().isEmpty()
        ) {
            apartmentApply.setUpdateUser(apartmentapplyarraignmentBo.getAccount());
            apartmentApply.setApplyDate(new Date());
            apartmentApply.setStatus(Constant.APARTMENT_STATUS_TYPE.APARTMENT_TEAM_REVIEW);
            apartmentApplyMapper.update(apartmentApply);
        } else if (apartmentApply.getStatus() == -1) {
            throw new SException("审核未通过，不可提审！");
        } else if (apartmentApply.getStatus() > 0) {
            throw new SException("已提审！");
        }
//            else {
//                throw new SException("资料不齐全，不可提审！");
//            }
//        } else {
//            throw new SException("名额不足，不可提审！");
//        }
    }

    //根据id查询附件url
    private RelationVo serchApartmentMapper(Integer originalId) {
        List<String> agentidcard = relationBusiness.searchRelation(Constant.RELATION_TYPE.AGENT_ID_CARD, originalId);
        List<String> trustInstrument = relationBusiness.searchRelation(Constant.RELATION_TYPE.TRUST_INSTRUMENT, originalId);
        List<String> applicantIdCard = relationBusiness.searchRelation(Constant.RELATION_TYPE.AppLICANT_ID_CARD, originalId);
        List<String> application = relationBusiness.searchRelation(Constant.RELATION_TYPE.APPLICATION, originalId);
        List<String> businessLicense = relationBusiness.searchRelation(Constant.RELATION_TYPE.BUSSINESS_LICENSE, originalId);
        List<String> certificate = relationBusiness.searchRelation(Constant.RELATION_TYPE.CERTIFICATE, originalId);
        List<String> otherMaterials = relationBusiness.searchRelation(Constant.RELATION_TYPE.OTHER_MATERIALS, originalId);
        List<String> laborContract = relationBusiness.searchRelation(Constant.RELATION_TYPE.LABOR_CONTRACT, originalId);
        List<String> pledge = relationBusiness.searchRelation(Constant.RELATION_TYPE.PLEDGE, originalId);
        RelationVo relationVo = new RelationVo();
        relationVo.setAgentIdCardUrl(agentidcard);
        relationVo.setTrustInstrumentUrl(trustInstrument);
        relationVo.setApplicantIdCardUrl(applicantIdCard);
        relationVo.setApplicationUrl(application);
        relationVo.setBusinessLicenseUrl(businessLicense);
        relationVo.setCertificateUrl(certificate);
        relationVo.setOtherMaterialsUrl(otherMaterials);
        relationVo.setLaborContractUrl(laborContract);
        relationVo.setPledgeUrl(pledge);
        return relationVo;
    }

    //根据id查询附件
    private ApartmentApplyRelationVo serchApartmentApplyRelation(Integer originalId) {
        List<TRelationVO> agentidcard = relationBusiness.searchRelationList(Constant.RELATION_TYPE.AGENT_ID_CARD, originalId);
        List<TRelationVO> trustInstrument = relationBusiness.searchRelationList(Constant.RELATION_TYPE.TRUST_INSTRUMENT, originalId);
        List<TRelationVO> applicantIdCard = relationBusiness.searchRelationList(Constant.RELATION_TYPE.AppLICANT_ID_CARD, originalId);
        List<TRelationVO> application = relationBusiness.searchRelationList(Constant.RELATION_TYPE.APPLICATION, originalId);
        List<TRelationVO> businessLicense = relationBusiness.searchRelationList(Constant.RELATION_TYPE.BUSSINESS_LICENSE, originalId);
        List<TRelationVO> certificate = relationBusiness.searchRelationList(Constant.RELATION_TYPE.CERTIFICATE, originalId);
        List<TRelationVO> otherMaterials = relationBusiness.searchRelationList(Constant.RELATION_TYPE.OTHER_MATERIALS, originalId);
        List<TRelationVO> laborContract = relationBusiness.searchRelationList(Constant.RELATION_TYPE.LABOR_CONTRACT, originalId);
        List<TRelationVO> pledge = relationBusiness.searchRelationList(Constant.RELATION_TYPE.PLEDGE, originalId);
        ApartmentApplyRelationVo relationVo = new ApartmentApplyRelationVo();
        relationVo.setAgentIdCardUrl(agentidcard);
        relationVo.setTrustInstrumentUrl(trustInstrument);
        relationVo.setApplicantIdCardUrl(applicantIdCard);
        relationVo.setApplicationUrl(application);
        relationVo.setBusinessLicenseUrl(businessLicense);
        relationVo.setCertificateUrl(certificate);
        relationVo.setOtherMaterialsUrl(otherMaterials);
        relationVo.setLaborContractUrl(laborContract);
        relationVo.setPledgeUrl(pledge);
        return relationVo;
    }

    //查看公寓申请细节
    @Override
    public ApartmentApplyRelationVo seeApartmentapply(ApartmentapplySeeBo apartmentapplySeeBo) {
        //申请id
        Integer id = apartmentapplySeeBo.getId();
        //通过申请id查询t_apartment_apply的所有信息
        ApartmentApply apartmentApply = apartmentApplyMapper.selectById(id);
        //关联房屋类型
        String HouseTypeName = apartmentApplyMapper.selectHouseTypeNamebyHouseType(apartmentApply.getHouseType());
        //关联公司名称
        Integer companyId = apartmentApply.getCompanyOriginId();
        String companyName = apartmentApplyMapper.selectCompanyNameByCompanyId(companyId);
        //条件查询历史
        ApartmentReviewExample example = new ApartmentReviewExample();
        ApartmentReviewExample.Criteria criteria = example.createCriteria();
        criteria.andApplyIdEqualTo(id);
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<ApartmentReview> apartments = apartmentReviewMapper.selectByExample(example);
        //根据id查询附件
        ApartmentApplyRelationVo relationVo = serchApartmentApplyRelation(id);
        BeanUtils.copyProperties(apartmentApply, relationVo);
        relationVo.setCompanyName(companyName);
        relationVo.setHouseTypeName(HouseTypeName);
        relationVo.setApartmentReviewList(apartments);
        return relationVo;
    }

    //是否通过审核
    @Transactional
    @Override
    public void passApartmentreview(ApartmentReviewPassBo apartmentReviewPassBo) {
        ApartmentReview apartmentReview = new ApartmentReview();
        BeanUtils.copyProperties(apartmentReviewPassBo, apartmentReview);
        ApartmentApply apartmentApply = apartmentApplyMapper.selectById(apartmentReview.getApplyId());
        Date day = new Date();
        apartmentReview.setReviewTime(day);
        apartmentReview.setCreateUser(apartmentReviewPassBo.getAccount());
        apartmentReview.setUpdateUser(apartmentReviewPassBo.getAccount());
        apartmentReview.setReviewer(apartmentReviewPassBo.getAccount());
        apartmentReview.setStage(apartmentReviewPassBo.getStage());
        //插入之前先判断前面是否已经存在
        ApartmentReviewExample apartmentReviewExample = new ApartmentReviewExample();
        ApartmentReviewExample.Criteria criteria = apartmentReviewExample.createCriteria();
        criteria.andStageEqualTo(apartmentReviewPassBo.getStage());
        criteria.andApplyIdEqualTo(apartmentReview.getApplyId());
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<ApartmentReview> apartmentReviewList = apartmentReviewMapper.selectByExample(apartmentReviewExample);
        if (apartmentReviewList != null && apartmentReviewList.size() > 0) {
            throw new SException("此阶段已审核！");
        } else {
            apartmentReviewMapper.insertSelective(apartmentReview);
            //修改申请状态
            if (apartmentReview.getIsPass() == 0) {
                //不通过
                if (apartmentApply.getStatus() > 0) {
                    apartmentApply.setStatus(Constant.APARTMENT_STATUS_TYPE.FAILED_REVIEW);
                    apartmentApply.setUpdateUser(apartmentReviewPassBo.getAccount());
                    apartmentApply.setUpdateTime(new Date());
                    apartmentApplyMapper.update(apartmentApply);
                } else if (apartmentApply.getStatus() == -1) {
                    throw new SException("已是未通过状态！");
                } else if (apartmentApply.getStatus() == 0) {
                    throw new SException("未提审！");
                }
            } else if (apartmentReview.getIsPass() == 1) {
                //通过
                //名额判断
                if (apartmentApply.getStatus() == Constant.APARTMENT_STATUS_TYPE.APARTMENT_TEAM_REVIEW) {
                    //获取已占用的名额数量
                    Integer count = apartmentApplyMapper.selectCount(apartmentApply.getCompanyOriginId());
                    Company company = companyMapper.selectById(apartmentApply.getCompanyOriginId());
                    if (company == null) {
                        throw new SException("公司不存在！");
                    }

                    if (company.getTotalNumber() == null || company.getTotalNumber() <= count) {
                        throw new SException("超过公寓限额！");
                    }
                }
                if (apartmentApply.getStatus() > 0) {
                    if (apartmentApply.getStatus() < 5) {
                        apartmentApply.setStatus(apartmentApply.getStatus() + 1);
                    } else if (apartmentReviewPassBo.getStage() >= 5) {
                        throw new SException("审核已结束！");
                    }
                    apartmentApply.setUpdateTime(new Date());
                    apartmentApplyMapper.update(apartmentApply);
                } else if (apartmentApply.getStatus() == -1) {
                    throw new SException("已是未通过状态！");
                } else if (apartmentApply.getStatus() == 0) {
                    throw new SException("未提审！");
                }
            }
        }
    }

    @Transactional
    @Override
    public void saveContract(ApartmentContractBO apartmentContractBO) {

        //合同执行中，保存合同信息
        if (apartmentContractBO.getContractStatus() == null || apartmentContractBO.getContractStatus() == Constant.APARTMENT_CONTRACT_STATUS.EXECUTING) {
            Apartment apartment = apartmentMapper.selectById(apartmentContractBO.getApartmentId());
            if (apartment != null) {

                ApartmentApply apartmentApply = apartmentApplyMapper.selectById(apartmentContractBO.getId());
                if (apartmentApply != null) {
                    //如果原公寓不为空，修改原公寓状态
                    if (apartmentApply.getApartmentId() != null) {
                        Apartment old = apartmentMapper.selectById(apartmentApply.getApartmentId());

                        if (old != null) {
                            //校验新公寓状态
                            if (apartment.getStatus() == Constant.APARTMENT_STATUS.LEASED && !(apartment.getId() == old.getId())) {
                                throw new SException("公寓已出租，请重新分配！");
                            }
                            old.setStatus(Constant.APARTMENT_STATUS.FOR_RENT);
                            old.setUpdateUser(apartmentContractBO.getAccount());
                            old.setUpdateTime(new Date());
                            apartmentMapper.updateByPrimaryKeySelective(old);
                        } else {
                            //校验新公寓状态
                            if (apartment.getStatus() == Constant.APARTMENT_STATUS.LEASED) {
                                throw new SException("公寓已出租，请重新分配！");
                            }
                        }
                    }

                    //修改新公寓状态
                    apartment.setStatus(Constant.APARTMENT_STATUS.LEASED);
                    apartment.setUpdateUser(apartmentContractBO.getAccount());
                    apartment.setUpdateTime(new Date());
                    apartmentMapper.updateByPrimaryKeySelective(apartment);
                } else {
                    throw new SException("公寓申请不存在！");
                }

                //修改公寓申请，公寓及合同信息
                apartmentApply.setId(apartmentContractBO.getId());
                apartmentApply.setContractBeginDate(apartmentContractBO.getContractBeginDate());
                apartmentApply.setContractEndDate(apartmentContractBO.getContractEndDate());
                apartmentApply.setApartmentId(apartmentContractBO.getApartmentId());
                apartmentApply.setUpdateUser(apartmentContractBO.getAccount());
                apartmentApply.setUpdateTime(new Date());
                apartmentApply.setContractStatus(Constant.APARTMENT_CONTRACT_STATUS.EXECUTING);
                apartmentApplyMapper.update(apartmentApply);
            } else {
                throw new SException("公寓不存在！");
            }
        }
        //提前解约或合同到期
        else {
            ApartmentApply apartmentApply = apartmentApplyMapper.selectById(apartmentContractBO.getId());
            if (apartmentApply != null) {
                //释放公寓
                if (apartmentApply.getApartmentId() != null) {
                    Apartment old = apartmentMapper.selectById(apartmentApply.getApartmentId());
                    if (old != null && old.getStatus() == Constant.APARTMENT_STATUS.LEASED) {
                        old.setStatus(Constant.APARTMENT_STATUS.FOR_RENT);
                        old.setUpdateUser(apartmentContractBO.getAccount());
                        old.setUpdateTime(new Date());
                        apartmentMapper.updateByPrimaryKeySelective(old);
                    }
                }

                //修改合同状态
                apartmentApply.setContractStatus(apartmentContractBO.getContractStatus());
                apartmentApply.setUpdateUser(apartmentContractBO.getAccount());
                apartmentApply.setUpdateTime(new Date());
                apartmentApplyMapper.update(apartmentApply);
            } else {
                throw new SException("公寓申请不存在！");
            }
        }

    }

    /**
     * 导出数据
     *
     * @param applyApartmentSearchBo
     * @param response
     */
    @Override
    public void exportApplyExcel(ApplyApartmentSearchBo applyApartmentSearchBo, HttpServletResponse response) {

        //查询项目列表
        List<ApartmentApplyVo> apartmentApplyVoList = searchApplyApartment(applyApartmentSearchBo).getList();
        List<String> columnList = new ArrayList<>();

        //表头
        columnList = Arrays.asList("单位名称", "个人姓名", "证件号", "手机号", "国籍", "租房类型", "申请日期", "申请状态",
                "合同开始时间", "合同结束时间", "合同期限", "合同状态", "公寓地址", "备注");

        if (apartmentApplyVoList.size() <= 0) {
            throw new SException("暂无数据！");
        }
        //数据
        List<String[]> dataList = new ArrayList<>();
        for (ApartmentApplyVo apartmentApplyVo : apartmentApplyVoList) {
            String[] arr = new String[14];
            arr[0] = apartmentApplyVo.getCompanyName();
            arr[1] = apartmentApplyVo.getName();
            arr[2] = apartmentApplyVo.getIdNumber();
            arr[3] = apartmentApplyVo.getPhone();
            arr[4] = apartmentApplyVo.getNationality();
            arr[5] = apartmentApplyVo.getHouseTypeName();
            if (apartmentApplyVo.getApplyDate() != null) {
                arr[6] = DateUtil.getTimeStr(apartmentApplyVo.getApplyDate());
            }
            if (apartmentApplyVo.getStatus() == Constant.APARTMENT_STATUS_TYPE.FAILED_REVIEW) {
                arr[7] = "审核不通过";
            } else if (apartmentApplyVo.getStatus() == Constant.APARTMENT_STATUS_TYPE.NOT_SUBMITTED_REVIEW) {
                arr[7] = "未提审";
            } else if (apartmentApplyVo.getStatus() == Constant.APARTMENT_STATUS_TYPE.APARTMENT_TEAM_REVIEW) {
                arr[7] = "复核";
            } else if (apartmentApplyVo.getStatus() == Constant.APARTMENT_STATUS_TYPE.AUDIT_UNIT_REVIEW) {
                arr[7] = "湾区分管审核";
            } else if (apartmentApplyVo.getStatus() == Constant.APARTMENT_STATUS_TYPE.AUDIT_UNIT_LEADER_REVIEW) {
                arr[7] = "湾区总经理审核";
            } else if (apartmentApplyVo.getStatus() == Constant.APARTMENT_STATUS_TYPE.LEADER_CHARGE_REVIEW) {
                arr[7] = "镇分管审核";
            } else if (apartmentApplyVo.getStatus() == Constant.APARTMENT_STATUS_TYPE.APPROVED) {
                arr[7] = "审核通过";
            }
            if (apartmentApplyVo.getContractBeginDate() != null) {
                arr[8] = DateUtil.getDateStr(apartmentApplyVo.getContractBeginDate());
            }
            if (apartmentApplyVo.getContractEndDate() != null) {
                arr[9] = DateUtil.getDateStr(apartmentApplyVo.getContractEndDate());
            }
            arr[10] = apartmentApplyVo.getTerm();
            if (apartmentApplyVo.getContractStatus() == Constant.APARTMENT_CONTRACT_STATUS.EXECUTING) {
                arr[11] = "合同执行中";
            } else if (apartmentApplyVo.getContractStatus() == Constant.APARTMENT_CONTRACT_STATUS.EARLY_TERMINATION) {
                arr[11] = "提前解约";
            } else if (apartmentApplyVo.getContractStatus() == Constant.APARTMENT_CONTRACT_STATUS.EXPIRE) {
                arr[11] = "合同到期";
            }
            arr[12] = apartmentApplyVo.getApartmentAddress();
            arr[13] = apartmentApplyVo.getDescription();
            dataList.add(arr);
        }
        //下载excel
        List<ExcelUtil.SheetData> sheetDataList = new ArrayList<>();
        ExcelUtil excelUtil = new ExcelUtil();
        ExcelUtil.SheetData sheetData = excelUtil.new SheetData();
        sheetData.setSheetName("数据");
        sheetData.setColumnList(columnList);
        sheetData.setDataList(dataList);
        sheetDataList.add(sheetData);
        String fileName = "apartmentApply.xls";
        excelUtil.write(sheetDataList, fileName, response);
    }
}



