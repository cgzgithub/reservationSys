package com.ust.shbay.manager.biz.Apartment;


import com.github.pagehelper.PageInfo;
import com.ust.shbay.manager.biz.Apartment.bo.*;
import com.ust.shbay.manager.entity.Apartment;
import com.ust.shbay.manager.entity.vo.ApartmentApplyRelationVo;
import com.ust.shbay.manager.entity.vo.ApartmentReviewVO;
import com.ust.shbay.manager.entity.vo.ApartmentTotalAndRemainder;
import com.ust.shbay.manager.entity.vo.ApartmentApplyVo;

import javax.servlet.http.HttpServletResponse;


public interface ApartmentBusiness {
    //新增公寓
    void addApartment(ApartmentAddBO apartmentAddBO);
    //编辑公寓
    void editApartment(ApartmentEditBO apartmentEditBO);

    void delApartment(ApartmentDelBo apartmentDelBo);

    PageInfo<Apartment> getApartmentByCondition(ApartmentQueryBo apartmentQueryBo);

    void addCompany(CompanyAddBo companyAddBo);

    void delCompany(CompanyDelBo companyDelBo);

    void editCompany(CompanyEditBo companyEditoBo);

    PageInfo<ApartmentTotalAndRemainder>  queryCompanyList(CompanyQueryBo companyQueryBo);

    void addApplyApartment(ApplyApartmentAddBo applyApartmentaddBo);

//    void cloneApplyApartment(ApplyApartmentCloneBO applyApartmentCloneBO);

    /**
     * 查询公寓审核历史列表
     * @param apartmentReviewHiQueryBO
     * @return
     */
    PageInfo<ApartmentReviewVO> queryReview(ApartmentReviewHiQueryBO apartmentReviewHiQueryBO);


    PageInfo<ApartmentApplyVo> searchApplyApartment(ApplyApartmentSearchBo applyApartmentSearchBo);

    void editApartmentapplylist(ApartmentApplyEditBo apartmentapplyEditBo);

    void cloneApplyApartment(ApplyApartmentCloneBO applyApartmentCloneBO);

    void arraignmentApartmentapply(ApartmentapplyarraignmentBo apartmentapplyarraignmentBo);

    ApartmentApplyRelationVo seeApartmentapply(ApartmentapplySeeBo apartmentapplySeeBo);

    void passApartmentreview(ApartmentReviewPassBo apartmentReviewPassBo);

    void saveContract(ApartmentContractBO apartmentContractBO);

    void exportApplyExcel(ApplyApartmentSearchBo applyApartmentSearchBo, HttpServletResponse response);
}
