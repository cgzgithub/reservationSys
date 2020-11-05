package com.ust.shbay.manager.biz.showroom;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.constant.SettingConstant;
import com.ust.shbay.common.dto.SmsConfig;
import com.ust.shbay.common.exception.SException;
import com.ust.shbay.common.utils.DateUtil;
import com.ust.shbay.common.utils.ExcelUtil;
import com.ust.shbay.common.utils.SmsUtil;
import com.ust.shbay.manager.biz.file.RelationBusiness;
import com.ust.shbay.manager.biz.showroom.bo.*;
import com.ust.shbay.manager.biz.showroom.vo.BookingTimeVO;
import com.ust.shbay.manager.biz.showroom.vo.ChildBookingTime;
import com.ust.shbay.manager.biz.showroom.vo.ShowroomBookingAnalysisVO;
import com.ust.shbay.manager.biz.showroom.vo.ShowroomBookingVO;
import com.ust.shbay.manager.biz.visitCompany.VisitCompanyBusiness;
import com.ust.shbay.manager.dao.*;
import com.ust.shbay.manager.entity.*;
import com.ust.shbay.manager.entity.vo.showroom.GuidePersonTime;
import com.ust.shbay.manager.entity.vo.showroom.ShowroomBookingNum;
import com.ust.shbay.manager.entity.vo.showroom.ReceptionNumAndDate;
import com.ust.shbay.manager.entity.vo.TRelationVO;
import com.ust.shbay.service.setting.SettingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShowroomBookingBusinessImpl implements ShowroomBookingBusiness {

    @Autowired
    private ShowroomMapper showroomMapper;

    @Autowired
    private GuideMapper guideMapper;

    @Autowired
    private ShowroomBookingMapper showroomBookingMapper;

    @Autowired
    private DictInfoMapper dictInfoMapper;

    @Autowired
    private RelationBusiness relationBusiness;

    @Autowired
    private TSettingMapper tSettingMapper;

    @Autowired
    private VisitCompanyBusiness visitCompanyBusiness;

    @Autowired
    private SettingService settingService;


    /**
     * 新增展厅
     *
     * @param showroomAddBO
     */
    @Override
    public void addShowroom(ShowroomAddBO showroomAddBO) {
        Showroom showroom = new Showroom();
        BeanUtils.copyProperties(showroomAddBO, showroom);
        showroom.setDelFlag(Constant.Status.NORMAL);
        showroom.setCreateUser(showroomAddBO.getAccount());
        showroom.setUpdateUser(showroomAddBO.getAccount());
        showroomMapper.insertSelective(showroom);
    }

    /**
     * 编辑展厅
     *
     * @param showroomEditBO
     */
    @Override
    public void editShowroom(ShowroomEditBO showroomEditBO) {
        Showroom showroom = showroomMapper.selectById(showroomEditBO.getId());
        if (showroom != null) {
            Showroom entity = new Showroom();
            BeanUtils.copyProperties(showroomEditBO, entity);
            entity.setUpdateUser(showroomEditBO.getAccount());
            showroomMapper.updateSelective(entity);
        } else {
            throw new SException("展厅不存在！");
        }
    }

    /**
     * 删除展厅
     *
     * @param showroomDelBO
     */
    @Override
    public void delShowroom(ShowroomDelBO showroomDelBO) {
        Showroom showroom = showroomMapper.selectById(showroomDelBO.getId());
        if (showroom != null) {
            //查询是否有预约
            ShowroomBookingExample example = new ShowroomBookingExample();
            ShowroomBookingExample.Criteria criteria = example.createCriteria();
            criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
            criteria.andStatusEqualTo(Constant.BOOKING_STATUS.IS_BOOKING);
            criteria.andShowroomIdEqualTo(showroomDelBO.getId());
            List<ShowroomBooking> showroomBookingList = showroomBookingMapper.selectByExample(example);
            if (showroomBookingList.size() > 0) {
                throw new SException("目前展厅有预约，无法删除！");
            }
            showroom.setUpdateUser(showroomDelBO.getAccount());
            showroom.setDelFlag(Constant.Status.DEL);
            showroomMapper.updateSelective(showroom);
        } else {
            throw new SException("展厅不存在！");
        }
    }

    /**
     * 获取展厅列表
     *
     * @param showroomQueryBO
     * @return
     */
    @Override
    public PageInfo<Showroom> getAllShowroomList(ShowroomQueryBO showroomQueryBO) {

        if (showroomQueryBO.getPageNumber() != null || showroomQueryBO.getPageSize() != null) {
            PageHelper.startPage(showroomQueryBO.getPageNumber(), showroomQueryBO.getPageSize());
        }
        List<Showroom> showroomList = new ArrayList<>();
        if (showroomQueryBO.getFlag() == null || showroomQueryBO.getFlag() == false) {
            //不包含删除数据，预约时使用
            showroomList = showroomMapper.selectAllByDelFlag();
        } else {
            //包含删除数据，展厅列表时使用
            showroomList = showroomMapper.selectAll();
        }

        PageInfo pageInfo = new PageInfo(showroomList);
        return pageInfo;
    }

    /**
     * 开放展厅
     *
     * @param showroomOpenBO
     */
    @Override
    public void openShowroom(ShowroomOpenBO showroomOpenBO) {
        Showroom showroom = new Showroom();
        showroom.setId(showroomOpenBO.getId());
        showroom.setUpdateUser(showroomOpenBO.getAccount());
        showroom.setDelFlag(Constant.Status.NORMAL);
        showroomMapper.updateSelective(showroom);
    }

    //增加解说员
    @Override
    public void addGuide(GuideAddBo guideAddBo) {
        Guide guide = new Guide();
        guide.setDelFlag(Constant.Status.NORMAL);
        guide.setCreateUser(guideAddBo.getAccount());
        guide.setUpdateUser(guideAddBo.getAccount());
        BeanUtils.copyProperties(guideAddBo, guide);
        guideMapper.insertSelective(guide);
    }

    //编辑讲解员
    @Override
    public void editGuide(GuideEditBO guideEditBO) {
        Integer guideId = guideEditBO.getId();
        Guide guide = guideMapper.selectByGuideId(guideId);
        if (guide != null) {
            Guide entity = new Guide();
            entity.setId(guideId);
            entity.setName(guideEditBO.getName());
            entity.setPhone(guideEditBO.getPhone());
            entity.setUpdateUser(guideEditBO.getAccount());
            guideMapper.updateByPrimaryKeySelective(entity);
        } else {
            throw new SException("讲解员不存在！");
        }
    }

    //    @Override
//    public List<SyRole> selectByBaseUser(BaseUser baseUser){
//        String companyId = baseUser.getCompanyId();
//        List<SyRole> syRoleList= syRoleMapper.selectByCompanyId(companyId);
//        return syRoleList;
//    }
    //讲解员列表
    @Override
    public List<Guide> selectByDelFlag() {
        List<Guide> guideList = guideMapper.selectALL();
        return guideList;
    }


    //删除解说员
    @Override
    public void delGuide(GuideDelBO guideDelBO) {
        Integer guideId = guideDelBO.getId();
        Guide guide = guideMapper.selectByGuideId(guideId);
        if (guide != null) {
            guide.setDelFlag(Constant.Status.DEL);
            guideMapper.updateByPrimaryKeySelective(guide);
        } else {
            throw new SException("讲解员不存在！");
        }
    }

    /**
     * 查询展厅预约时间
     *
     * @param showroomBookingTimeQueryBO
     * @return
     */
    @Override
    public List<BookingTimeVO> queryBookingTime(ShowroomBookingTimeQueryBO showroomBookingTimeQueryBO) {
        List<BookingTimeVO> bookingTimeVOList = new ArrayList<>();
        if (showroomBookingTimeQueryBO.getShowroomId() == null) {
            //查询全部展厅1天的预约情况
            List<Showroom> showroomList = showroomMapper.selectAllByDelFlag();
            if (showroomList.size() > 0) {
                for (Showroom showroom : showroomList) {
                    showroomBookingTimeQueryBO.setShowroomId(showroom.getId());
                    BookingTimeVO bookingTimeVO = getBookingTimeVO(showroomBookingTimeQueryBO, 0);
                    bookingTimeVOList.add(bookingTimeVO);
                }
            } else {
                throw new SException("没有展厅数据！");
            }
        } else {
            //获取单个会议室n天的预约情况
            for (int i = 0; i < showroomBookingTimeQueryBO.getNum(); i++) {
                BookingTimeVO bookingTimeVO = getBookingTimeVO(showroomBookingTimeQueryBO, i);
                bookingTimeVOList.add(bookingTimeVO);
            }
        }
        return bookingTimeVOList;
    }

    //获取展厅每日情况
    private BookingTimeVO getBookingTimeVO(ShowroomBookingTimeQueryBO showroomBookingTimeQueryBO, int i) {
        BookingTimeVO bookingTimeVO = new BookingTimeVO();
        List<ChildBookingTime> childBookingTimeList = new ArrayList<>();
        //日期
        Date date = DateUtil.getPastDate(showroomBookingTimeQueryBO.getVisitDate(), i);
        bookingTimeVO.setBookingDate(date);

        //获得会议室名称及开放时间
        List<String> allOpenTimeList = new ArrayList<>();
        List<String> todayOpenTimeList = new ArrayList<>();
        Showroom showroom = showroomMapper.selectById(showroomBookingTimeQueryBO.getShowroomId());
        if (showroom == null || showroom.getDelFlag() == Constant.Status.DEL) {
            return null;
        } else {
            //今天开放时间
            allOpenTimeList = getAllOpenTimeList(showroom.getAmBeginTime(), showroom.getAmEndTime(),
                    showroom.getPmBeginTime(), showroom.getPmEndTime());

            //今天剩余时间点
            todayOpenTimeList = getTodayOpenTimeList(showroom.getAmBeginTime(), showroom.getAmEndTime(),
                    showroom.getPmBeginTime(), showroom.getPmEndTime());
            bookingTimeVO.setShowroomId(showroom.getId());
            bookingTimeVO.setShowroomName(showroom.getName());
            bookingTimeVO.setSeatNum(showroom.getSeatNum());
        }

        //展厅一天内的开始时间,展厅以半小时为单位，每次半小时，以开始时间代替时间段
        List<String> allBeginTimes = DateUtil.getAllTimeList();
        allBeginTimes.remove(allBeginTimes.size() - 1);

        List<String> allBeginTimesNew = DateUtil.getAllTimeList();
        allBeginTimesNew.remove(allBeginTimesNew.size() - 1);

        //一天内不可预订的时间点（开始时间）
        List<String> allCannotBookingBeginTimes = new ArrayList<>();

        //一天内已预订的时间点（开始时间）
        List<String> allIsBookedBeginTimes = new ArrayList<>();

        //一天内可以预订的时间点（开始时间）
        List<String> allCanBookingBeginTimes = new ArrayList<>();

        //该日期为工作日还是周末
        bookingTimeVO.setBookingWeek(DateUtil.getWeek(date));
        if (DateUtil.getWeek(date).equals("星期六") || DateUtil.getWeek(date).equals("星期日")) {
            //周末
            allCannotBookingBeginTimes = allBeginTimes;
            childBookingTimeList = getChildBookingTime(allBeginTimesNew, allCannotBookingBeginTimes, null, null);
        } else {
            //工作日
            //每日的预约情况，分四种情况
            //一种是预约时间为今天，可以正常查看、已经过去的时间点无法预约，未到的时间点可以预约
            //一种是今天以前，可以正常查看、无法预约
            //一种是今天以后一个月内，可以正常查看、预约
            //一种是一个月后，无法预约
            long betweenDate = (date.getTime() - (new Date()).getTime()) / (60 * 60 * 24 * 1000);
            if (betweenDate > 30) {
                //一个月后
                //不可预订
                allCannotBookingBeginTimes = allBeginTimes;
                childBookingTimeList = getChildBookingTime(allBeginTimesNew, allCannotBookingBeginTimes, null, null);
            } else if (DateUtil.getDateStr(date).equals(DateUtil.getDateStr(new Date()))) {
                //今天
                //查询预约情况
                List<ShowroomBooking> showroomBookingList = getByVisitDate(date, showroomBookingTimeQueryBO.getShowroomId());
                if (showroomBookingList.size() > 0) {
                    //已预定
                    for (ShowroomBooking showroomBooking : showroomBookingList) {
                        allIsBookedBeginTimes.add(showroomBooking.getBeginTime());
                    }
                    //可预订
                    if (todayOpenTimeList != null && allIsBookedBeginTimes != null)
                        todayOpenTimeList.removeAll(allIsBookedBeginTimes);
                    if (allCanBookingBeginTimes != null && todayOpenTimeList != null)
                        allCanBookingBeginTimes = todayOpenTimeList;
                    //不可预订
                    if (allBeginTimes != null && allIsBookedBeginTimes != null)
                        allBeginTimes.removeAll(allIsBookedBeginTimes);
                    if (allBeginTimes != null && allCanBookingBeginTimes != null)
                        allBeginTimes.removeAll(allCanBookingBeginTimes);
                    allCannotBookingBeginTimes = allBeginTimes;
                    childBookingTimeList = getChildBookingTime(allBeginTimesNew, allCannotBookingBeginTimes, allIsBookedBeginTimes, allCanBookingBeginTimes);
                } else {
                    //可预订
                    allCanBookingBeginTimes = todayOpenTimeList;
                    //不可预订
                    if (allBeginTimes != null && allCanBookingBeginTimes != null)
                        allBeginTimes.removeAll(allCanBookingBeginTimes);
                    allCannotBookingBeginTimes = allBeginTimes;
                    childBookingTimeList = getChildBookingTime(allBeginTimesNew, allCannotBookingBeginTimes, null, allCanBookingBeginTimes);
                }
            } else if (date.before(new Date()) && !(DateUtil.getDateStr(date)).equals(DateUtil.getDateStr(new Date()))) {
                //今天以前
                //查询预约情况
                List<ShowroomBooking> showroomBookingList = getByVisitDate(date, showroomBookingTimeQueryBO.getShowroomId());
                if (showroomBookingList.size() > 0) {
                    //已预定
                    for (ShowroomBooking showroomBooking : showroomBookingList) {
                        allIsBookedBeginTimes.add(showroomBooking.getBeginTime());
                    }
                    //不可预订
                    if (allBeginTimes != null && allIsBookedBeginTimes != null)
                        allBeginTimes.removeAll(allIsBookedBeginTimes);
                    allCannotBookingBeginTimes = allBeginTimes;
                    childBookingTimeList = getChildBookingTime(allBeginTimesNew, allCannotBookingBeginTimes, allIsBookedBeginTimes, null);
                } else {
                    //不可预订
                    allCannotBookingBeginTimes = allBeginTimes;
                    childBookingTimeList = getChildBookingTime(allBeginTimesNew, allCannotBookingBeginTimes, null, null);
                }
            } else {
                //今天以后一个月内
                //查询预约情况
                List<ShowroomBooking> showroomBookingList = getByVisitDate(date, showroomBookingTimeQueryBO.getShowroomId());
                if (showroomBookingList.size() > 0) {
                    //已预定
                    for (ShowroomBooking showroomBooking : showroomBookingList) {
                        allIsBookedBeginTimes.add(showroomBooking.getBeginTime());
                    }
                    //可预订
                    if (allOpenTimeList != null && allIsBookedBeginTimes != null)
                        allOpenTimeList.removeAll(allIsBookedBeginTimes);
                    allCanBookingBeginTimes = allOpenTimeList;
                    //不可预订
                    if (allBeginTimes != null && allIsBookedBeginTimes != null)
                        allBeginTimes.removeAll(allIsBookedBeginTimes);
                    if (allBeginTimes != null && allCanBookingBeginTimes != null)
                        allBeginTimes.removeAll(allCanBookingBeginTimes);
                    allCannotBookingBeginTimes = allBeginTimes;
                    childBookingTimeList = getChildBookingTime(allBeginTimesNew, allCannotBookingBeginTimes, allIsBookedBeginTimes, allCanBookingBeginTimes);
                } else {
                    //可预订
                    allCanBookingBeginTimes = allOpenTimeList;
                    //不可预订
                    if (allBeginTimes != null && allCanBookingBeginTimes != null)
                        allBeginTimes.removeAll(allCanBookingBeginTimes);
                    allCannotBookingBeginTimes = allBeginTimes;
                    childBookingTimeList = getChildBookingTime(allBeginTimesNew, allCannotBookingBeginTimes, null, allCanBookingBeginTimes);
                }
            }
        }
        bookingTimeVO.setChildBookingTimeList(childBookingTimeList);

        return bookingTimeVO;
    }

    /**
     * 获取一天中的开放时间点，目前写死,09:00-10:30，13:30-16:00
     *
     * @return
     */
    private List<String> getAllOpenTimeList(String amBeginTime, String amEndTime, String pmBeginTime, String pmEndTime) {

        //展厅允许设置的最大时间
        List<String> allTimeList = DateUtil.getAllTimeList();

        //上、下午开放时间点
        List<String> amOpenTimeList = allTimeList.subList(allTimeList.indexOf(amBeginTime), allTimeList.indexOf(amEndTime));
        List<String> pmOpenTimeList = allTimeList.subList(allTimeList.indexOf(pmBeginTime), allTimeList.indexOf(pmEndTime));

        //求并集
        amOpenTimeList.removeAll(pmOpenTimeList);
        amOpenTimeList.addAll(pmOpenTimeList);
        return amOpenTimeList;
    }


    /**
     * 获取今天开放时间点
     *
     * @return
     */
    private List<String> getTodayOpenTimeList(String amBeginTime, String amEndTime, String pmBeginTime, String pmEndTime) {
        //上、下午开放时间点
        List<String> amOpenTimeList = new ArrayList<>();
        List<String> pmOpenTimeList = new ArrayList<>();
        //一天全部时间点
        List<String> timeList = DateUtil.getTimeList();

        //获取当前时间的下一个时间点，以半小时为单位
        String timeStr = DateUtil.getTimeStr();
        //判断当前时间点所处情况
        if ((timeList.subList(0, timeList.indexOf(amBeginTime))).contains(timeStr)) {//00:00-09:00，包含00:00，不包含09:00，其余一致
            amOpenTimeList = timeList.subList(timeList.indexOf(amBeginTime), timeList.indexOf(amEndTime));
            pmOpenTimeList = timeList.subList(timeList.indexOf(pmBeginTime), timeList.indexOf(pmEndTime));
            //求并集
            amOpenTimeList.removeAll(pmOpenTimeList);
            amOpenTimeList.addAll(pmOpenTimeList);
            return amOpenTimeList;
        } else if ((timeList.subList(timeList.indexOf(amBeginTime), timeList.indexOf(amEndTime))).contains(timeStr)) {//09:00-10:30
            amOpenTimeList = timeList.subList(timeList.indexOf(timeStr), timeList.indexOf(amEndTime));
            pmOpenTimeList = timeList.subList(timeList.indexOf(pmBeginTime), timeList.indexOf(pmEndTime));
            //求并集
            amOpenTimeList.removeAll(pmOpenTimeList);
            amOpenTimeList.addAll(pmOpenTimeList);
            return amOpenTimeList;
        } else if ((timeList.subList(timeList.indexOf(amEndTime), timeList.indexOf(pmBeginTime))).contains(timeStr)) {//10:30-13:30
            pmOpenTimeList = timeList.subList(timeList.indexOf(pmBeginTime), timeList.indexOf(pmEndTime));
            return pmOpenTimeList;
        } else if ((timeList.subList(timeList.indexOf(pmBeginTime), timeList.indexOf(pmEndTime))).contains(timeStr)) {//13:30-16:00
            pmOpenTimeList = timeList.subList(timeList.indexOf(timeStr), timeList.indexOf(pmEndTime));
            return pmOpenTimeList;
        } else {//16:00-23:30
            return null;
        }
    }

    /**
     * 通过预约日期查询预约记录
     *
     * @param visitDate
     * @return
     */
    private List<ShowroomBooking> getByVisitDate(Date visitDate, Integer showroomId) {
        ShowroomBookingExample example = new ShowroomBookingExample();
        ShowroomBookingExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        criteria.andShowroomIdEqualTo(showroomId);
        criteria.andStatusNotEqualTo(Constant.BOOKING_STATUS.CANCEL_BOOKING);
        criteria.andVisitDateEqualTo(visitDate);
        List<ShowroomBooking> showroomBookingList = showroomBookingMapper.selectByExample(example);
        return showroomBookingList;
    }

    /**
     * @param allBeginTimes              一天中全部时间点
     * @param allCannotBookingBeginTimes 一天中不可预订的时间点
     * @param allIsBookedBeginTimes      一天中已经预订的时间点
     * @param allCanBookingBeginTimes    一天中可以预订的时间点
     * @return
     */
    private List<ChildBookingTime> getChildBookingTime(List<String> allBeginTimes,
                                                       List<String> allCannotBookingBeginTimes,
                                                       List<String> allIsBookedBeginTimes,
                                                       List<String> allCanBookingBeginTimes) {
        List<ChildBookingTime> childBookingTimeList = new ArrayList<>();
        //获取允许的全部时间
        List<String> allTimeList = DateUtil.getAllTimeList();
        for (String s : allBeginTimes) {
            if (allCannotBookingBeginTimes != null && allCannotBookingBeginTimes.contains(s)) {
                ChildBookingTime childBookingTime = new ChildBookingTime();
                childBookingTime.setBeginTime(s);
                childBookingTime.setEndTime(allTimeList.get(allTimeList.indexOf(s) + 1));
                childBookingTime.setStatus(Constant.BOOKING_TIME_STATUS.CANNOT_BOOKING);
                childBookingTimeList.add(childBookingTime);
                continue;
            } else if (allIsBookedBeginTimes != null && allIsBookedBeginTimes.contains(s)) {
                ChildBookingTime childBookingTime = new ChildBookingTime();
                childBookingTime.setBeginTime(s);
                childBookingTime.setEndTime(allTimeList.get(allTimeList.indexOf(s) + 1));
                childBookingTime.setStatus(Constant.BOOKING_TIME_STATUS.IS_BOOKED);
                childBookingTimeList.add(childBookingTime);
                continue;
            } else if (allCanBookingBeginTimes != null && allCanBookingBeginTimes.contains(s)) {
                ChildBookingTime childBookingTime = new ChildBookingTime();
                childBookingTime.setBeginTime(s);
                childBookingTime.setEndTime(allTimeList.get(allTimeList.indexOf(s) + 1));
                childBookingTime.setStatus(Constant.BOOKING_TIME_STATUS.CAN_BOOKING);
                childBookingTimeList.add(childBookingTime);
                continue;
            }
        }
        return childBookingTimeList;
    }

    /**
     * 添加展厅预约
     *
     * @param showroomBookingAddBO
     */
//    @Transactional
    @Override
    public void addShowroomBooking(ShowroomBookingAddBO showroomBookingAddBO) {

        Showroom showroom = showroomMapper.selectById(showroomBookingAddBO.getShowroomId());
        if (showroom == null || showroom.getDelFlag() == Constant.Status.DEL) {
            throw new SException("展厅不存在或已关闭，无法预约");
        }

        //校验同一时间段不能多次预订
        checkBookingTime(showroomBookingAddBO.getShowroomId(), showroomBookingAddBO.getVisitDate(), showroomBookingAddBO.getBeginTime());

        //处理讲解员
        Integer guideId = showroomBookingAddBO.getGuideId();
        List<Integer> ids = guideMapper.selectAllIds();
        if (ids != null && ids.size() > 0) {
            if (guideId == null || (!ids.contains(guideId))) {
                //查询默认讲解员
                Integer defaultGuideId = getDefaultGuide();
                if (defaultGuideId == null) {
                    //没有默认讲解员
                    guideId = ids.get(0);
                } else {
                    guideId = defaultGuideId;
                }
            }
        } else {
            throw new SException("该展厅没有讲解员，无法预订！");
        }

        ShowroomBooking showroomBooking = new ShowroomBooking();
        BeanUtils.copyProperties(showroomBookingAddBO, showroomBooking);
        showroomBooking.setDelFlag(Constant.Status.NORMAL);
        showroomBooking.setStatus(Constant.BOOKING_STATUS.IS_BOOKING);
        showroomBooking.setCreateUser(showroomBookingAddBO.getAccount());
        showroomBooking.setUpdateUser(showroomBookingAddBO.getAccount());
        showroomBooking.setGuideId(guideId);
        showroomBooking.setAccountId(showroomBookingAddBO.getSyAccountId());
        showroomBookingMapper.insertSelective(showroomBooking);

        //添加来访公司
        //转为name集合
        List<String> nameList = showroomBookingAddBO.getVisitCompanyList().stream().map(VisitCompany::getName).collect(Collectors.toList());
        visitCompanyBusiness.save(nameList, showroomBooking.getId(), Constant.BOOKING_TYPE.SHOWROOM_BOOKING, showroomBookingAddBO.getAccount());

        //将下一个讲解员更新为默认讲解员
        if (ids.indexOf(guideId) == ids.size() - 1) {
            guideId = ids.get(0);
            saveDefaultGuide(guideId.toString(), showroomBookingAddBO.getAccount());
        } else if (ids.indexOf(guideId) >= 0 && ids.indexOf(guideId) < ids.size() - 1) {
            guideId = ids.get(ids.indexOf(guideId) + 1);
            saveDefaultGuide(guideId.toString(), showroomBookingAddBO.getAccount());
        }

        SendSmsResponse sendSmsResponse = new SendSmsResponse();
        try {
            //短信通知,参数超过20字符会报错
            String name = "",position = "";
            if (showroom.getName() != null && showroom.getName().length() > 20) {
                name = showroom.getName().substring(0,20);
            } else {
                name = showroom.getName();
            }
            if (showroom.getPosition() != null && showroom.getPosition().length() > 20) {
                position = showroom.getPosition().substring(0,20);
            } else {
                position = showroom.getPosition();
            }

            String time = DateUtil.getDateStr2(showroomBookingAddBO.getVisitDate()) + " " +
                    showroomBookingAddBO.getBeginTime() + "-" + showroomBookingAddBO.getEndTime();
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("time", time);
            map.put("position", position);
            SmsConfig smsConfig = settingService.getSmsConf("message");

            SmsUtil smsUtil = new SmsUtil();
            sendSmsResponse = smsUtil.sendMessage(showroomBookingAddBO.getBookingPhone(), smsConfig, map);
        } catch (ClientException e) {
            throw new SException(sendSmsResponse.getMessage());
        }
    }

    //校验是否同一时间段是否重复预约
    private void checkBookingTime(Integer showroomId, Date visitDate, String beginTime) {
        ShowroomBookingExample example = new ShowroomBookingExample();
        ShowroomBookingExample.Criteria criteria = example.createCriteria();
        criteria.andShowroomIdEqualTo(showroomId);
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        criteria.andStatusNotEqualTo(Constant.BOOKING_STATUS.CANCEL_BOOKING);
        criteria.andVisitDateEqualTo(visitDate);
        criteria.andBeginTimeEqualTo(beginTime);
        List<ShowroomBooking> showroomBookingList = showroomBookingMapper.selectByExample(example);
        if (showroomBookingList != null && showroomBookingList.size() > 0) {
            throw new SException("该时间已经预约，请重新选择时间！");
        }
    }

    /**
     * 按条件查询预约情况
     *
     * @param showroomBookingQueryBO
     * @return
     */
    @Override
    public PageInfo<ShowroomBookingVO> queryShowroomBooking(ShowroomBookingQueryBO showroomBookingQueryBO) {
        //处理查询条件
        ShowroomBookingExample example = new ShowroomBookingExample();
        ShowroomBookingExample.Criteria criteria = example.createCriteria();
        //账号id
        if (showroomBookingQueryBO.getAccountId() != null) {
            criteria.andAccountIdEqualTo(showroomBookingQueryBO.getAccountId());
        }
        //展厅
        if (showroomBookingQueryBO.getShowroomId() != null) {
            criteria.andShowroomIdEqualTo(showroomBookingQueryBO.getShowroomId());
        }
        //预约日期
        if (showroomBookingQueryBO.getVisitDate() != null) {
            criteria.andVisitDateEqualTo(showroomBookingQueryBO.getVisitDate());
        }
        //预约时间段
        if (showroomBookingQueryBO.getBeginDate() != null && showroomBookingQueryBO.getEndDate() != null) {
            criteria.andVisitDateBetween(showroomBookingQueryBO.getBeginDate(),
                    showroomBookingQueryBO.getEndDate());
        }
        //状态(-2:取消，-1:未到访,0:预约中,1:到访)
        if (showroomBookingQueryBO.getStatus() != null) {
            criteria.andStatusEqualTo(showroomBookingQueryBO.getStatus());
        }
        //预约通道（0:小程序，1:web端）
        if (showroomBookingQueryBO.getPassageway() != null) {
            criteria.andPassagewayEqualTo(showroomBookingQueryBO.getPassageway());
        }
        //讲解员
        if (showroomBookingQueryBO.getGuideId() != null) {
            criteria.andGuideIdEqualTo(showroomBookingQueryBO.getGuideId());
        }
        //预约人
        if (showroomBookingQueryBO.getBookingPersion() != null && !"".equals(showroomBookingQueryBO.getBookingPersion())) {
            criteria.andBookingPersionLike("%" + showroomBookingQueryBO.getBookingPersion() + "%");
        }
        //单位名称
        if (showroomBookingQueryBO.getCompanyName() != null && !"".equals(showroomBookingQueryBO.getCompanyName())) {
            List<VisitCompany> visitCompanyList = visitCompanyBusiness.getByName(showroomBookingQueryBO.getCompanyName(),
                    Constant.BOOKING_TYPE.SHOWROOM_BOOKING);
            if (visitCompanyList != null && visitCompanyList.size() > 0) {
                List<Integer> idList = visitCompanyList.stream().map(VisitCompany::getBookingId).collect(Collectors.toList());
                criteria.andIdIn(idList);
            } else {
                new PageInfo(null);
            }
        }
        //删除标记
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        //排序
        example.setOrderByClause("update_time desc");
        //分页
        PageHelper.startPage(showroomBookingQueryBO.getPageNumber(), showroomBookingQueryBO.getPageSize());
        List<ShowroomBooking> showroomBookingList = showroomBookingMapper.selectByExample(example);
        //返回对象
        PageInfo pageInfo = new PageInfo(showroomBookingList);
        List<ShowroomBookingVO> showroomBookingVOList = new ArrayList<>();
        for (ShowroomBooking showroomBooking : showroomBookingList) {
            ShowroomBookingVO showroomBookingVO = new ShowroomBookingVO();
            BeanUtils.copyProperties(showroomBooking, showroomBookingVO);
            //关联讲解员姓名
            if (showroomBookingVO.getGuideId() != null) {
                Guide guide = guideMapper.selectByGuideId(showroomBookingVO.getGuideId());
                if (guide != null && guide.getDelFlag() == Constant.Status.NORMAL) {
                    showroomBookingVO.setGuideName(guide.getName());
                }
            }
//            //关联单位类型
//            if (showroomBookingVO.getCompanyTypeId() != null) {
//                DictInfo dictInfo = dictInfoMapper.selectByPrimaryKey(showroomBookingVO.getCompanyTypeId());
//                if (dictInfo != null && dictInfo.getDelFlag() == Constant.Status.NORMAL) {
//                    showroomBookingVO.setCompanyTypeName(dictInfo.getTitle());
//                }
//            }
            //关联取消原因
            if (showroomBookingVO.getCancelReasonId() != null) {
                DictInfo dictInfo = dictInfoMapper.selectByPrimaryKey(showroomBookingVO.getCancelReasonId());
                if (dictInfo != null && dictInfo.getDelFlag() == Constant.Status.NORMAL) {
                    showroomBookingVO.setCancelReasonName(dictInfo.getTitle());
                }
            }

            //关联到访图片
            List<TRelationVO> tRelationList = relationBusiness.searchRelationList(Constant.RELATION_TYPE.SHOWROOMBooking,
                    showroomBookingVO.getId());
            showroomBookingVO.setTRelationList(tRelationList);

            //关联展厅名称
            if (showroomBookingVO.getShowroomId() != null) {
                Showroom showroom = showroomMapper.selectById(showroomBookingVO.getShowroomId());
                if (showroom != null && showroom.getDelFlag() == Constant.Status.NORMAL) {
                    showroomBookingVO.setShowroomName(showroom.getName());
                }
            }

            //关联来访单位
            List<VisitCompany> visitCompanyList = visitCompanyBusiness.getByBookingId(showroomBookingVO.getId(),
                    Constant.BOOKING_TYPE.SHOWROOM_BOOKING);
            showroomBookingVO.setVisitCompanyList(visitCompanyList);
            showroomBookingVOList.add(showroomBookingVO);
        }
        pageInfo.setList(showroomBookingVOList);
        return pageInfo;
    }

    /**
     * 更新展厅预约信息
     *
     * @param showroomBookingEditBO
     */
    @Transactional
    @Override
    public void editShowroomBooking(ShowroomBookingEditBO showroomBookingEditBO) {

        ShowroomBooking showroomBooking = showroomBookingMapper.selectById(showroomBookingEditBO.getId());
        if (showroomBooking != null) {
            ShowroomBooking entity = new ShowroomBooking();
            BeanUtils.copyProperties(showroomBookingEditBO, entity);
            entity.setUpdateUser(showroomBookingEditBO.getAccount());
            showroomBookingMapper.update(entity);

            //更新来访公司
            List<String> nameList = showroomBookingEditBO.getVisitCompanyList().stream().map(VisitCompany::getName).collect(Collectors.toList());
            visitCompanyBusiness.save(nameList, showroomBookingEditBO.getId(), Constant.BOOKING_TYPE.SHOWROOM_BOOKING, showroomBookingEditBO.getAccount());

        } else {
            throw new SException("展厅预约申请不存在！");
        }
    }

    /**
     * 取消展厅预约
     *
     * @param showroomBookingCancelBO
     */
    @Override
    public void cancelShowroomBooking(ShowroomBookingCancelBO showroomBookingCancelBO) {
        ShowroomBooking showroomBooking = showroomBookingMapper.selectById(showroomBookingCancelBO.getId());
        if (showroomBooking != null) {
            ShowroomBooking entity = new ShowroomBooking();
            BeanUtils.copyProperties(showroomBookingCancelBO, entity);
            entity.setCancelTime(new Date());
            entity.setUpdateUser(showroomBookingCancelBO.getAccount());
            entity.setStatus(Constant.BOOKING_STATUS.CANCEL_BOOKING);
            showroomBookingMapper.update(entity);
        } else {
            throw new SException("展厅预约申请不存在！");
        }
    }

    /**
     * 预约未到访
     *
     * @param showroomBookingNoVisitBO
     */
    @Override
    public void noVisit(ShowroomBookingNoVisitBO showroomBookingNoVisitBO) {
        ShowroomBooking showroomBooking = showroomBookingMapper.selectById(showroomBookingNoVisitBO.getId());
        if (showroomBooking != null) {
            ShowroomBooking entity = new ShowroomBooking();
            entity.setId(showroomBookingNoVisitBO.getId());
            entity.setUpdateUser(showroomBookingNoVisitBO.getAccount());
            entity.setStatus(Constant.BOOKING_STATUS.ISNOT_VISITED);
            showroomBookingMapper.update(entity);
        } else {
            throw new SException("展厅预约申请不存在！");
        }
    }

    /**
     * 到访
     *
     * @param showroomBookingVisitBO
     */
    @Override
    @Transactional
    public void visit(ShowroomBookingVisitBO showroomBookingVisitBO) {
        ShowroomBooking showroomBooking = showroomBookingMapper.selectById(showroomBookingVisitBO.getId());
        if (showroomBooking != null) {
            ShowroomBooking entity = new ShowroomBooking();
            BeanUtils.copyProperties(showroomBookingVisitBO, entity);
            entity.setUpdateUser(showroomBookingVisitBO.getAccount());
            entity.setStatus(Constant.BOOKING_STATUS.IS_VISITED);
            showroomBookingMapper.update(entity);
            //更新到访图片
            //查询原有到访图片
            if (showroomBookingVisitBO.getImgUrlList() != null && showroomBookingVisitBO.getImgUrlList().size() > 3) {
                throw new SException("到访图片不能超过三张！");
            }
            List<String> oldList = relationBusiness.searchRelation(Constant.RELATION_TYPE.SHOWROOMBooking,
                    showroomBookingVisitBO.getId());
            relationBusiness.updateAttachment(oldList, showroomBookingVisitBO.getImgUrlList(),
                    Constant.RELATION_TYPE.SHOWROOMBooking, showroomBookingVisitBO.getId(), showroomBookingVisitBO.getAccount());
//            relationBusiness.deleteRelation(showroomBookingVisitBO.getId(), Constant.RELATION_TYPE.SHOWROOMBooking);
//            if (showroomBookingVisitBO.getImgUrlList() != null && showroomBookingVisitBO.getImgUrlList().size() > 0) {
//                //添加到访图片
//                for (String url : showroomBookingVisitBO.getImgUrlList()) {
//                    TRelation tRelation = new TRelation();
//                    tRelation.setType(Constant.RELATION_TYPE.SHOWROOMBooking);
//                    tRelation.setFileUrl(url);
//                    tRelation.setCreateUser(showroomBookingVisitBO.getAccount());
//                    tRelation.setUpdateUser(showroomBookingVisitBO.getAccount());
//                    tRelation.setOriginalId(showroomBookingVisitBO.getId());
//                    relationBusiness.insertRelation(tRelation);
//                }
//            }
        } else {
            throw new SException("展厅预约申请不存在！");
        }
    }

    /**
     * 数据分析
     *
     * @param showroomBookingAnalysisBO
     * @return
     */
    @Override
    public ShowroomBookingAnalysisVO analysis(ShowroomBookingAnalysisBO showroomBookingAnalysisBO) {

        ShowroomBookingAnalysisVO showroomBookingAnalysisVO = new ShowroomBookingAnalysisVO();

        if (showroomBookingAnalysisBO.getBeginDate() != null && showroomBookingAnalysisBO.getEndDate() != null) {
            List<Showroom> showroomList = showroomMapper.selectAllByDate(
                    showroomBookingAnalysisBO.getBeginDate(), showroomBookingAnalysisBO.getEndDate());
            if (showroomList.size() > 0) {
                //展厅
                showroomBookingAnalysisVO.setShowroomList(showroomList);

                //查询该时间段展厅各状态的预约次数
                List<ShowroomBookingNum> showroomBookingNumList = showroomBookingMapper.getAllNumListByVisitDate(
                        showroomBookingAnalysisBO.getBeginDate(), showroomBookingAnalysisBO.getEndDate());
                //对list分组
                if (showroomBookingNumList.size() > 0) {
                    Map<String, List<ShowroomBookingNum>> map = showroomBookingNumList.stream().collect(Collectors.groupingBy(ShowroomBookingNum::getShowroomName));
                    showroomBookingAnalysisVO.setMap(map);
                }

                //各讲解员接待人次
                List<GuidePersonTime> guidePersonTimeList = showroomBookingMapper.getGuidePersonTimeListByVisitDate(
                        showroomBookingAnalysisBO.getBeginDate(), showroomBookingAnalysisBO.getEndDate());
                showroomBookingAnalysisVO.setGuidePersonTimeList(guidePersonTimeList);

                //每日接待人次
                //处理时间
                String beginDate = DateUtil.getDateStr(showroomBookingAnalysisBO.getBeginDate());
                String endDate = DateUtil.getDateStr(showroomBookingAnalysisBO.getEndDate());
                List<ShowroomBookingAnalysisVO.EverydayPersonTime> everydayPersonTimeList = new ArrayList<>();
                for (Showroom showroom : showroomList) {
                    ShowroomBookingAnalysisVO.EverydayPersonTime everydayPersonTime = showroomBookingAnalysisVO.new EverydayPersonTime();
                    List<ReceptionNumAndDate> receptionNumAndDateList = showroomBookingMapper.getReceptionNumAndDate(
                            beginDate, endDate, showroom.getId());
                    if (receptionNumAndDateList.size() > 0) {
                        List<Date> visitDateList = receptionNumAndDateList.stream().map(p -> p.getVisitDate()).collect(Collectors.toList());
                        List<String> dateStrList = new ArrayList<>();
                        for (Date date : visitDateList) {
//                            dateStrList.add(DateUtil.getNextDateStr(date));
                            dateStrList.add(DateUtil.getDateStr(date));
                        }
                        List<Integer> numList = receptionNumAndDateList.stream().map(p -> p.getNumber()).collect(Collectors.toList());
                        everydayPersonTime.setXAxis(dateStrList);
                        Map<String, Object> seriesMap = new HashMap<>();
                        seriesMap.put("name", showroom.getName());
                        seriesMap.put("data", numList);
                        everydayPersonTime.setSeries(seriesMap);
                    }
                    everydayPersonTimeList.add(everydayPersonTime);
                    showroomBookingAnalysisVO.setEverydayPersonTimeList(everydayPersonTimeList);
                }
            }
        } else {
            throw new SException("请输入查询时间！");
        }
        return showroomBookingAnalysisVO;
    }

    /**
     * 导出分析数据
     *
     * @param showroomBookingAnalysisBO
     * @param response
     */
    @Override
    public void exportAnalysisExcel(ShowroomBookingAnalysisBO showroomBookingAnalysisBO, HttpServletResponse response) {

        //查询分析数据
        List<String[]> list = getAnalysisData(showroomBookingAnalysisBO);

        //下载Excel
        List<ExcelUtil.SheetData> sheetDataList = new ArrayList<>();
        ExcelUtil excelUtil = new ExcelUtil();

        //表头
        List<String> columnList = Arrays.asList("单位名称", "预约人", "手机号", "展厅名称", "讲解员", "预约日期", "开始时间",
                "结束时间", "预约人数", "预约通道", "预约状态", "实到人数", "评价", "取消通道", "取消原因", "取消时间");

        if (list == null || list.size() <= 0) {
            throw new SException("该时间范围内暂无数据！");
        }

        ExcelUtil.SheetData sheetData = excelUtil.new SheetData();
        sheetData.setSheetName("展厅预约数据统计");
        sheetData.setColumnList(columnList);
        sheetData.setDataList(list);
        sheetDataList.add(sheetData);

        String fileName = "showroom.xls";
        excelUtil.write(sheetDataList, fileName, response);
    }

    /**
     * 查询统计数据
     *
     * @param showroomBookingAnalysisBO
     * @return
     */
    private List<String[]> getAnalysisData(ShowroomBookingAnalysisBO showroomBookingAnalysisBO) {

        List<String[]> dataList = new ArrayList<>();

        if (showroomBookingAnalysisBO.getBeginDate() != null && showroomBookingAnalysisBO.getEndDate() != null) {

            //查询预约数据
            ShowroomBookingQueryBO showroomBookingQueryBO = new ShowroomBookingQueryBO();
            showroomBookingQueryBO.setBeginDate(showroomBookingAnalysisBO.getBeginDate());
            showroomBookingQueryBO.setEndDate(showroomBookingAnalysisBO.getEndDate());
            showroomBookingQueryBO.setPageNumber(0);
            showroomBookingQueryBO.setPageSize(0);
            List<ShowroomBookingVO> list = queryShowroomBooking(showroomBookingQueryBO).getList();

            //处理预约数据
            for (ShowroomBookingVO showroomBookingVO : list) {
                String[] arr = new String[16];
                if (showroomBookingVO.getVisitCompanyList() != null && showroomBookingVO.getVisitCompanyList().size() > 0) {
                    List<String> nameList = showroomBookingVO.getVisitCompanyList().stream().
                            map(VisitCompany::getName).collect(Collectors.toList());
                    String names = "";
                    for (int i = 0; i < nameList.size(); i++) {
                        if (i != nameList.size() - 1) {
                            names += nameList.get(i) + "、";
                        } else {
                            names += nameList.get(i);
                        }
                    }
                    arr[0] = names;
                }
//                arr[1] = showroomBookingVO.getCompanyTypeName();
                arr[1] = showroomBookingVO.getBookingPersion();
                arr[2] = showroomBookingVO.getBookingPhone();
                arr[3] = showroomBookingVO.getShowroomName();
                arr[4] = showroomBookingVO.getGuideName();
                if (showroomBookingVO.getVisitDate() != null) {
                    arr[5] = DateUtil.getDateStr(showroomBookingVO.getVisitDate());
                }
                arr[6] = showroomBookingVO.getBeginTime();
                arr[7] = showroomBookingVO.getEndTime();
                if (showroomBookingVO.getBookingNumber() != null) {
                    arr[8] = showroomBookingVO.getBookingNumber().toString();
                }
                if (showroomBookingVO.getPassageway() == null) {
                    arr[9] = "";
                } else if (showroomBookingVO.getPassageway() == 0) {
                    arr[9] = "小程序";
                } else {
                    arr[9] = "电脑端";
                }
                if (showroomBookingVO.getStatus() == 0) {
                    arr[10] = "预约中";
                } else if (showroomBookingVO.getStatus() == 1) {
                    arr[10] = "到访";
                } else if (showroomBookingVO.getStatus() == -1) {
                    arr[10] = "未到访";
                } else if (showroomBookingVO.getStatus() == -2) {
                    arr[10] = "已取消";
                }
                if (showroomBookingVO.getActualNum() != null) {
                    arr[11] = showroomBookingVO.getActualNum().toString();
                }
                if (showroomBookingVO.getAppraise() != null) {
                    arr[12] = showroomBookingVO.getAppraise().toString();
                }
                if (showroomBookingVO.getCancelChannel() == null) {
                    arr[13] = "";
                } else if (showroomBookingVO.getCancelChannel() == 0) {
                    arr[13] = "小程序";
                } else {
                    arr[13] = "电脑端";
                }
                arr[14] = showroomBookingVO.getCancelReasonName();
                if (showroomBookingVO.getCancelTime() != null) {
                    arr[15] = DateUtil.getTimeStr(showroomBookingVO.getCancelTime());
                }

                dataList.add(arr);
            }
        } else {
            throw new SException("请输入查询时间！");
        }
        return dataList;
    }


    /**
     * 获取默认讲解员id
     *
     * @return
     */
    @Override
    public Integer getDefaultGuide() {
        //查询默认讲解员id
        String value = tSettingMapper.selectValueById(SettingConstant.SETTING_ID.DEFAULT_GUIDE);
        //查询全部讲解员id
        List<Integer> ids = guideMapper.selectAllIds();
        if (value != null && ids != null && ids.size() > 0 && ids.contains(Integer.parseInt(value))) {
            return Integer.parseInt(value);
        }
        return null;
    }

    private void saveDefaultGuide(String value, String account) {
        //查询默认讲解员
        TSetting old = tSettingMapper.selectById(SettingConstant.SETTING_ID.DEFAULT_GUIDE);
        if (old != null) {
            //编辑
            old.setValue(value);
            old.setUpdateUser(account);
            tSettingMapper.updateSelective(old);
        } else {
            //新增
            TSetting record = new TSetting();
            record.setId(SettingConstant.SETTING_ID.DEFAULT_GUIDE);
            record.setValue(value);
            record.setDesc("默认讲解员id");
            record.setCreateUser(account);
            record.setUpdateUser(account);
            tSettingMapper.insertSelective(record);
        }
    }
}
