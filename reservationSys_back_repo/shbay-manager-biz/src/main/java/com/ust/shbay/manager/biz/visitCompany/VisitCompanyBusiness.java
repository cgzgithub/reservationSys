package com.ust.shbay.manager.biz.visitCompany;

import com.ust.shbay.manager.entity.VisitCompany;

import java.util.List;

public interface VisitCompanyBusiness {

    /**
     * 保存预约来访公司
     * @param companyNames
     * @param bookingId
     * @param type
     * @param account
     */
    void save(List<String> companyNames, Integer bookingId, Integer type, String account);

    /**
     * 删除预约来访公司
     * @param companyName
     * @param bookingId
     * @param type
     * @param account
     */
    void delete(String companyName, Integer bookingId, Integer type, String account);

    /**
     * 查询预约来访公司
     * @param bookingId
     * @param type
     * @return
     */
    List<VisitCompany> getByBookingId(Integer bookingId, Integer type);

    /**
     * 通过公司名、预约类型，模糊查询预约列表
     * @param companyName
     * @param type
     * @return
     */
    List<VisitCompany> getByName(String companyName, Integer type);
}
