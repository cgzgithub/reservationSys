package com.ust.shbay.manager.api.controller;


import com.github.pagehelper.PageInfo;
import com.ust.shbay.common.dto.ResponseEntity;
import com.ust.shbay.manager.api.controller.dto.apartment.*;
import com.ust.shbay.manager.api.controller.dto.company.CompanyAdd;
import com.ust.shbay.manager.api.controller.dto.company.CompanyDel;
import com.ust.shbay.manager.api.controller.dto.company.CompanyEdit;
import com.ust.shbay.manager.api.controller.dto.company.CompanyQuery;
import com.ust.shbay.manager.biz.Apartment.ApartmentBusiness;
import com.ust.shbay.manager.biz.Apartment.bo.*;
import com.ust.shbay.manager.entity.vo.ApartmentApplyRelationVo;
import com.ust.shbay.manager.entity.vo.ApartmentReviewVO;
import com.ust.shbay.manager.entity.vo.ApartmentTotalAndRemainder;
import com.ust.shbay.manager.entity.*;
import com.ust.shbay.manager.entity.vo.ApartmentApplyVo;
import com.ust.shbay.service.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Api(tags = "公寓预约")
@RestController
@RequestMapping("api/apartment/")
public class ApartmentController extends BaseController {

    @Autowired
    private ApartmentBusiness apartmentBusiness;

    //增加公寓
    @ApiOperation(value = "增加公寓")
    @PostMapping("add/apartment")
    public ResponseEntity addApartment(@Valid @RequestBody ApartmentAdd apartmentAdd, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ApartmentAddBO apartmentAddBO = new ApartmentAddBO();
        BeanUtils.copyProperties(apartmentAdd, apartmentAddBO);
        setBaseAccount(apartmentAddBO);
        apartmentBusiness.addApartment(apartmentAddBO);
        return ResponseEntity.buildSuccEntity();
    }

    //编辑公寓
    @ApiOperation(value = "编辑公寓")
    @PostMapping("edit/apartment")
    public ResponseEntity editApartment(@Valid @RequestBody ApartmentEdit apartmentEdit, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ApartmentEditBO apartmentEditBO = new ApartmentEditBO();
        BeanUtils.copyProperties(apartmentEdit, apartmentEditBO);
        setBaseAccount(apartmentEditBO);
        apartmentBusiness.editApartment(apartmentEditBO);
        return ResponseEntity.buildSuccEntity();
    }

    //删除公寓
    @ApiOperation(value = "删除公寓")
    @PostMapping("del/apartment")
    public ResponseEntity delMeetingRoom(@RequestBody ApartmentDel apartmentDel) {
        ApartmentDelBo apartmentDelBo = new ApartmentDelBo();
        BeanUtils.copyProperties(apartmentDel, apartmentDelBo);
        setBaseAccount(apartmentDelBo);
        apartmentBusiness.delApartment(apartmentDelBo);
        return ResponseEntity.buildSuccEntity();
    }

    //条件查询
    @ApiOperation(value = "条件查询公寓")
    @PostMapping("getApartmentByCondition")
    public ResponseEntity<PageInfo> getApartmentByCondition(@RequestBody ApartmentQuery apartmentQuery) {
        ApartmentQueryBo apartmentQueryBo = new ApartmentQueryBo();
        BeanUtils.copyProperties(apartmentQuery, apartmentQueryBo);
        setBaseAccount(apartmentQueryBo);
        PageInfo<Apartment> pageInfo = apartmentBusiness.getApartmentByCondition(apartmentQueryBo);
        return ResponseEntity.buildSuccEntity(pageInfo);
    }

    //增加公司
    @ApiOperation(value = "新增公司")
    @PostMapping("add/company")
    public ResponseEntity addCompany(@Valid @RequestBody CompanyAdd companyAdd, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        CompanyAddBo companyAddBo = new CompanyAddBo();
        BeanUtils.copyProperties(companyAdd, companyAddBo);
        setBaseAccount(companyAddBo);
        apartmentBusiness.addCompany(companyAddBo);
        return ResponseEntity.buildSuccEntity();
    }

    //删除公司
    @ApiOperation(value = "删除公司")
    @PostMapping("del/company")
    public ResponseEntity delCompany(@RequestBody CompanyDel companyDel) {
        CompanyDelBo companyDelBo = new CompanyDelBo();
        BeanUtils.copyProperties(companyDel, companyDelBo);
        setBaseAccount(companyDelBo);
        apartmentBusiness.delCompany(companyDelBo);
        return ResponseEntity.buildSuccEntity();
    }

    //编辑公司
    @ApiOperation(value = "编辑公司")
    @PostMapping("edit/company")
    public ResponseEntity editCompany(@Valid @RequestBody CompanyEdit companyEdit, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        CompanyEditBo companyEditoBo = new CompanyEditBo();
        BeanUtils.copyProperties(companyEdit, companyEditoBo);
        setBaseAccount(companyEditoBo);
        apartmentBusiness.editCompany(companyEditoBo);
        return ResponseEntity.buildSuccEntity();
    }

    //查询公司列表
    @ApiOperation(value = "查询公司列表")
    @PostMapping("/company/query")
    public ResponseEntity<PageInfo> queryCompanyList(@RequestBody CompanyQuery companyQuery) {
        CompanyQueryBo companyQueryBo = new CompanyQueryBo();
        BeanUtils.copyProperties(companyQuery, companyQueryBo);
        setBaseAccount(companyQueryBo);
        PageInfo<ApartmentTotalAndRemainder> pageInfo = apartmentBusiness.queryCompanyList(companyQueryBo);
        return ResponseEntity.buildSuccEntity(pageInfo);
    }

    //新增公寓申请
    @ApiOperation(value = "新增公寓申请")
    @PostMapping("/add/Apartmentapply")
    public ResponseEntity addApplyApartment(@Valid @RequestBody ApplyApartmentAdd applyApartmentadd, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ApplyApartmentAddBo applyApartmentaddBo = new ApplyApartmentAddBo();
        BeanUtils.copyProperties(applyApartmentadd, applyApartmentaddBo);
        setBaseAccount(applyApartmentaddBo);
        apartmentBusiness.addApplyApartment(applyApartmentaddBo);
        return ResponseEntity.buildSuccEntity();
    }

    //查询公寓审核历史列表
    @ApiOperation(value = "查询公寓审核历史列表")
    @PostMapping("/queryReviewList")
    public ResponseEntity<PageInfo> queryReviewList(@RequestBody ApartmentReviewHiQuery apartmentReviewHiQuery) {
        ApartmentReviewHiQueryBO apartmentReviewHiQueryBO = new ApartmentReviewHiQueryBO();
        BeanUtils.copyProperties(apartmentReviewHiQuery, apartmentReviewHiQueryBO);
        setBaseAccount(apartmentReviewHiQueryBO);
        PageInfo<ApartmentReviewVO> pageInfo = apartmentBusiness.queryReview(apartmentReviewHiQueryBO);
        return ResponseEntity.buildSuccEntity(pageInfo);
    }

    @ApiOperation(value = "查询公寓申请列表")
    @PostMapping("/queryApplyList")
    public ResponseEntity<PageInfo> searchApplyApartment(@RequestBody ApplyApartmentSearch applyApartmentSearch) {
        ApplyApartmentSearchBo applyApartmentSearchBo = new ApplyApartmentSearchBo();
        BeanUtils.copyProperties(applyApartmentSearch, applyApartmentSearchBo);
        setBaseAccount(applyApartmentSearchBo);
        PageInfo<ApartmentApplyVo> pageInfo = apartmentBusiness.searchApplyApartment(applyApartmentSearchBo);
        return ResponseEntity.buildSuccEntity(pageInfo);
    }

    @ApiOperation(value = "编辑公寓申请列表")
    @PostMapping("/edit/Apartmentapplylist")
    public ResponseEntity editApartmentapplylist(@Valid @RequestBody ApartmentapplyEdit apartmentapplyEdit, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ApartmentApplyEditBo apartmentapplyEditBo = new ApartmentApplyEditBo();
        BeanUtils.copyProperties(apartmentapplyEdit, apartmentapplyEditBo);
        setBaseAccount(apartmentapplyEditBo);
        apartmentBusiness.editApartmentapplylist(apartmentapplyEditBo);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "克隆公寓申请")
    @PostMapping("/clone/Apartmentapply")
    public ResponseEntity cloneApplyApartment(@RequestBody ApplyApartmentClone applyApartmentClone) {
        ApplyApartmentCloneBO applyApartmentCloneBO = new ApplyApartmentCloneBO();
        BeanUtils.copyProperties(applyApartmentClone, applyApartmentCloneBO);
        setBaseAccount(applyApartmentCloneBO);
        apartmentBusiness.cloneApplyApartment(applyApartmentCloneBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "提审")
    @PostMapping("/arraignment/Apartmentapply")
    public ResponseEntity arraignmentApartmentapply(@RequestBody Apartmentapplyarraignment apartmentapplyarraignment) {
        ApartmentapplyarraignmentBo apartmentapplyarraignmentBo = new ApartmentapplyarraignmentBo();
        BeanUtils.copyProperties(apartmentapplyarraignment, apartmentapplyarraignmentBo);
        setBaseAccount(apartmentapplyarraignmentBo);
        apartmentBusiness.arraignmentApartmentapply(apartmentapplyarraignmentBo);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "查看公寓申请详情")
    @PostMapping("/see/Apartmentapply")
    public ResponseEntity seeApartmentapply(@RequestBody ApartmentapplySee apartmentapplySee) {
        ApartmentapplySeeBo apartmentapplySeeBo = new ApartmentapplySeeBo();
        BeanUtils.copyProperties(apartmentapplySee, apartmentapplySeeBo);
        setBaseAccount(apartmentapplySeeBo);
        ApartmentApplyRelationVo a = apartmentBusiness.seeApartmentapply(apartmentapplySeeBo);
        return ResponseEntity.buildSuccEntity(a);
    }

    @ApiOperation(value = "审核，通过或不通过")
    @PostMapping("/pass/ApartmentReview")
    public ResponseEntity passApartmentreview(@Valid @RequestBody ApartmentReviewPass apartmentReviewPass, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ApartmentReviewPassBo apartmentReviewPassBo = new ApartmentReviewPassBo();
        BeanUtils.copyProperties(apartmentReviewPass, apartmentReviewPassBo);
        setBaseAccount(apartmentReviewPassBo);
        apartmentBusiness.passApartmentreview(apartmentReviewPassBo);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "保存合同数据、合同到期、提前解约")
    @PostMapping("/saveContract")
    public ResponseEntity saveContract(@Valid @RequestBody ApartmentContract apartmentContract, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ApartmentContractBO apartmentContractBO = new ApartmentContractBO();
        BeanUtils.copyProperties(apartmentContract, apartmentContractBO);
        setBaseAccount(apartmentContractBO);
        apartmentBusiness.saveContract(apartmentContractBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "导出申请列表")
    @PostMapping(value = "exportApplyExcel" , produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportApplyExcel(@RequestBody ApplyApartmentSearch applyApartmentSearch, HttpServletResponse response){
        ApplyApartmentSearchBo applyApartmentSearchBo = new ApplyApartmentSearchBo();
        BeanUtils.copyProperties(applyApartmentSearch,applyApartmentSearchBo);
        setBaseAccount(applyApartmentSearchBo);
        apartmentBusiness.exportApplyExcel(applyApartmentSearchBo,response);
    }
}
