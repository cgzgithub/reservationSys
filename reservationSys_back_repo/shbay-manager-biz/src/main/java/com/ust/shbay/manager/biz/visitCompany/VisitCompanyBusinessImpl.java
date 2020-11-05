package com.ust.shbay.manager.biz.visitCompany;

import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.manager.dao.VisitCompanyMapper;
import com.ust.shbay.manager.entity.VisitCompany;
import com.ust.shbay.manager.entity.VisitCompanyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VisitCompanyBusinessImpl implements VisitCompanyBusiness {

    @Autowired
    private VisitCompanyMapper visitCompanyMapper;

    /**
     * 保存预约来访公司
     *
     * @param companyNames
     * @param bookingId
     * @param type
     * @param account
     */
    @Override
    public void save(List<String> companyNames, Integer bookingId, Integer type, String account) {
        //查询原有数据
        List<String> oldNames = visitCompanyMapper.getNames(bookingId, type);
        if (oldNames != null && oldNames.size() > 0) {
            //有数据更新
            //与数据库取交集
            List<String> copyOldNames = new ArrayList(oldNames);
            oldNames.retainAll(companyNames);//取交集的部分，不变
            companyNames.removeAll(oldNames);//传入差集，需插入数据库
            copyOldNames.removeAll(oldNames);//数据库差集，需删除
            if (companyNames.size() > 0) {
                //需要插入的操作
                for (String companyName : companyNames) {
                    add(companyName, bookingId, type, account);
                }
            }
            if (copyOldNames.size() > 0) {
                //需要删除的操作
                for (String companyName : copyOldNames) {
                    delete(companyName, bookingId, type, account);
                }
            }
        } else {
            //没有数据，全部添加
            for (String companyName : companyNames) {
                add(companyName, bookingId, type, account);
            }
        }
    }

    //新增数据
    private void add(String companyName, Integer bookingId, Integer type, String account) {
        VisitCompany visitCompany = new VisitCompany();
        visitCompany.setType(type);
        visitCompany.setBookingId(bookingId);
        visitCompany.setName(companyName);
        visitCompany.setCreateUser(account);
        visitCompany.setUpdateUser(account);
        visitCompanyMapper.insertSelective(visitCompany);
    }

    /**
     * 删除预约来访公司
     * @param companyName
     * @param bookingId
     * @param type
     * @param account
     */
    @Override
    public void delete(String companyName, Integer bookingId, Integer type, String account) {
        VisitCompany record = new VisitCompany();
        record.setDelFlag(Constant.Status.DEL);
        record.setUpdateUser(account);

        VisitCompanyExample example = new VisitCompanyExample();
        VisitCompanyExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(type);
        criteria.andBookingIdEqualTo(bookingId);
        if(companyName != null){
            criteria.andNameEqualTo(companyName);
        }
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);

        visitCompanyMapper.updateByExampleSelective(record, example);
    }

    /**
     * 查询预约来访公司
     * @param bookingId
     * @param type
     * @return
     */
    @Override
    public List<VisitCompany> getByBookingId(Integer bookingId, Integer type){

        VisitCompanyExample example = new VisitCompanyExample();
        VisitCompanyExample.Criteria criteria = example.createCriteria();
        criteria.andBookingIdEqualTo(bookingId);
        criteria.andTypeEqualTo(type);
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<VisitCompany> visitCompanyList = visitCompanyMapper.selectByExample(example);
        return visitCompanyList;
    }

    /**
     * 通过公司名、预约类型，模糊查询预约列表
     * @param companyName
     * @param type
     * @return
     */
    @Override
    public List<VisitCompany> getByName(String companyName, Integer type){
        VisitCompanyExample example = new VisitCompanyExample();
        VisitCompanyExample.Criteria criteria = example.createCriteria();
        criteria.andNameLike("%" + companyName + "%");
        criteria.andTypeEqualTo(type);
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<VisitCompany> visitCompanyList = visitCompanyMapper.selectByExample(example);
        return visitCompanyList;
    }
}
