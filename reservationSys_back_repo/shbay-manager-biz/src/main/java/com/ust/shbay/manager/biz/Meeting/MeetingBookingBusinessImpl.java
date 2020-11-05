package com.ust.shbay.manager.biz.Meeting;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.dto.SmsConfig;
import com.ust.shbay.common.exception.SException;
import com.ust.shbay.common.utils.DateUtil;
import com.ust.shbay.common.utils.ExcelUtil;
import com.ust.shbay.common.utils.SmsUtil;
import com.ust.shbay.manager.biz.Meeting.bo.*;
import com.ust.shbay.manager.biz.Meeting.vo.*;
import com.ust.shbay.manager.biz.file.RelationBusiness;
import com.ust.shbay.manager.biz.showroom.vo.ChildBookingTime;
import com.ust.shbay.manager.biz.visitCompany.VisitCompanyBusiness;
import com.ust.shbay.manager.dao.*;
import com.ust.shbay.manager.entity.*;
import com.ust.shbay.manager.entity.vo.meeting.CompanyTypeAndTimes;
import com.ust.shbay.manager.entity.vo.meeting.MeetingVO;
import com.ust.shbay.manager.entity.vo.TRelationVO;
import com.ust.shbay.manager.entity.vo.showroom.ReceptionNumAndDate;
import com.ust.shbay.service.file.FileService;
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
public class MeetingBookingBusinessImpl implements MeetingBookingBusiness {

    @Autowired
    private MeetingMapper meetingMapper;
    @Autowired
    private MeetingBookingMapper meetingBookingMapper;
    @Autowired
    private RelationBusiness relationBusiness;
    @Autowired
    private DictInfoMapper dictInfoMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private VisitCompanyBusiness visitCompanyBusiness;
    @Autowired
    private MeetingEnclosureMapper meetingEnclosureMapper;
    @Autowired
    private SettingService settingService;
    @Autowired
    private SyRoleMapper syRoleMapper;

    /**
     * 添加会议室
     *
     * @param meetingRoomAddBO
     */

    @Override
    @Transactional
    public void addMeetingRoom(MeetingRoomAddBO meetingRoomAddBO) {
        //添加会议室
        Meeting meeting = new Meeting();
        BeanUtils.copyProperties(meetingRoomAddBO, meeting);
        meeting.setDelFlag(Constant.Status.NORMAL);
        meeting.setCreateUser(meetingRoomAddBO.getAccount());
        meeting.setUpdateUser(meetingRoomAddBO.getAccount());
        meetingMapper.insertSelective(meeting);
        //添加会议室（路演厅）摆放要求
        if (meetingRoomAddBO.getPutPicUrls() != null && meetingRoomAddBO.getPutPicUrls().size() > 0) {
            for (TRelation tRelation : meetingRoomAddBO.getPutPicUrls()) {
                tRelation.setType(Constant.RELATION_TYPE.MEETING);
                tRelation.setCreateUser(meetingRoomAddBO.getAccount());
                tRelation.setUpdateUser(meetingRoomAddBO.getAccount());
                tRelation.setOriginalId(meeting.getId());
                relationBusiness.insertRelation(tRelation);
            }
        }
        //添加会议室（路演厅）附件
        if (meetingRoomAddBO.getEnclosureIds() != null && meetingRoomAddBO.getEnclosureIds().size() > 0) {
            MeetingEnclosureExample example = new MeetingEnclosureExample();
            MeetingEnclosureExample.Criteria criteria = example.createCriteria();
            criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
            criteria.andIdIn(meetingRoomAddBO.getEnclosureIds());
            MeetingEnclosure record = new MeetingEnclosure();
            record.setMeetingId(meeting.getId());
            record.setUpdateUser(meetingRoomAddBO.getAccount());
            meetingEnclosureMapper.updateByExampleSelective(record, example);
        }
    }

    //编辑会议室
    @Override
    @Transactional
    public void editMeetingRoom(MeetingRoomEditBo meetingRoomEditBo) {
        Integer meetingId = meetingRoomEditBo.getId();
        Meeting meeting = meetingMapper.selectMeetingId(meetingId);
        if (meeting != null) {
            Meeting entity = new Meeting();
            BeanUtils.copyProperties(meetingRoomEditBo, entity);
            entity.setUpdateUser(meetingRoomEditBo.getAccount());
            meetingMapper.updateByPrimaryKeySelective(entity);
        } else {
            throw new SException("会议室不存在");
        }
        //更新摆放要求图片
        List<String> oldList = relationBusiness.searchRelation(Constant.RELATION_TYPE.MEETING,
                meetingRoomEditBo.getId());
        relationBusiness.updateAttachment(oldList, meetingRoomEditBo.getPutPicUrls(),
                Constant.RELATION_TYPE.MEETING, meetingRoomEditBo.getId(), meetingRoomEditBo.getAccount());
//        // 删除摆放要求
//        relationBusiness.deleteRelation(meetingRoomEditBo.getId(), Constant.RELATION_TYPE.MEETING);
//        if (meetingRoomEditBo.getPutPicUrls() != null && meetingRoomEditBo.getPutPicUrls().size() > 0) {
//            //添加摆放要求
//            for (String url : meetingRoomEditBo.getPutPicUrls()) {
//                TRelation tRelation = new TRelation();
//                tRelation.setType(Constant.RELATION_TYPE.MEETING);
//                tRelation.setFileUrl(url);
//                tRelation.setCreateUser(meetingRoomEditBo.getAccount());
//                tRelation.setUpdateUser(meetingRoomEditBo.getAccount());
//                tRelation.setOriginalId(meeting.getId());
//                relationBusiness.insertRelation(tRelation);
//            }
//        }
    }

    //删除会议室
    @Override
    @Transactional
    public void delMeetingRoom(MeetingRoomDelBo meetingRoomDelBo) {
        Integer meetingid = meetingRoomDelBo.getId();
        Meeting meeting = meetingMapper.selectMeetingId(meetingid);
        if (meeting != null) {
            //查询是否有预约
            MeetingBookingExample example = new MeetingBookingExample();
            MeetingBookingExample.Criteria criteria = example.createCriteria();
            criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
            criteria.andStatusEqualTo(Constant.BOOKING_STATUS.IS_BOOKING);
            criteria.andMeetingIdEqualTo(meetingRoomDelBo.getId());
            List<MeetingBooking> meetingBookingList = meetingBookingMapper.selectByExample(example);
            if (meetingBookingList.size() > 0) {
                throw new SException("目前会议室有预约，无法删除！");
            }
            meeting.setUpdateUser(meetingRoomDelBo.getAccount());
            meeting.setDelFlag(Constant.Status.DEL);
            meetingMapper.updateByPrimaryKeySelective(meeting);
        } else {
            throw new SException("会议室不存在");
        }
//        relationBusiness.deleteRelation(meetingid, Constant.RELATION_TYPE.MEETING);
    }

    @Override
    public PageInfo<Meeting> selectByDelFlag(MeetingRoomQueryBO meetingRoomQueryBO) {
        if (meetingRoomQueryBO.getPageNumber() != null || meetingRoomQueryBO.getPageSize() != null) {
            PageHelper.startPage(meetingRoomQueryBO.getPageNumber(), meetingRoomQueryBO.getPageSize());
        }
        List<Meeting> meetingList = meetingMapper.selectALL();
        PageInfo pageInfo = new PageInfo(meetingList);
        List<MeetingVO> meetingVOList = new ArrayList<>();
        if (meetingList.size() > 0) {
            for (Meeting meeting : meetingList) {
                MeetingVO meetingVO = new MeetingVO();
                BeanUtils.copyProperties(meeting, meetingVO);
                //关联会议室摆放要求
                List<TRelationVO> tRelationList = relationBusiness.searchRelationList(Constant.RELATION_TYPE.MEETING,
                        meeting.getId());
                meetingVO.setTRelation(tRelationList);
                //关联附件
                List<MeetingEnclosure> meetingEnclosures = meetingEnclosureMapper.selectByMeetingId(meeting.getId());
                meetingVO.setEnclosureList(meetingEnclosures);
                meetingVOList.add(meetingVO);
            }
        }
        pageInfo.setList(meetingVOList);
        return pageInfo;
    }

    @Override
    public void openMeetingRoom(MeetingRoomOpenBo meetingRoomOpenBo) {
        Integer meetingid = meetingRoomOpenBo.getId();
        Meeting meeting = meetingMapper.selectMeetingId(meetingid);
        meeting.setUpdateUser(meetingRoomOpenBo.getAccount());
        meeting.setDelFlag(Constant.Status.NORMAL);
        meetingMapper.updateByPrimaryKeySelective(meeting);
    }

    /**
     * 根据会议室id查询附件
     *
     * @param meetingId
     * @return
     */
    @Override
    public List<MeetingEnclosure> getEnclosuresByMeetingId(Integer meetingId) {
        List<MeetingEnclosure> list = meetingEnclosureMapper.selectByMeetingId(meetingId);
        return list;
    }

    @Override
    public Integer addMeetingEnclosure(MeetingEnclosureAddBO meetingEnclosureAddBO) {
        MeetingEnclosure meetingEnclosure = new MeetingEnclosure();
        BeanUtils.copyProperties(meetingEnclosureAddBO, meetingEnclosure);
        meetingEnclosure.setDelFlag(Constant.Status.NORMAL);
        meetingEnclosure.setCreateUser(meetingEnclosureAddBO.getAccount());
        meetingEnclosure.setUpdateUser(meetingEnclosureAddBO.getAccount());
        meetingEnclosureMapper.insertSelective(meetingEnclosure);
        return meetingEnclosure.getId();
    }

    @Override
    public Integer editMeetingEnclosure(MeetingEnclosureEditBO meetingEnclosureEditBO) {
        MeetingEnclosure meetingEnclosure = meetingEnclosureMapper.selectById(meetingEnclosureEditBO.getId());
        if (meetingEnclosure != null) {
            BeanUtils.copyProperties(meetingEnclosureEditBO, meetingEnclosure);
            meetingEnclosure.setUpdateUser(meetingEnclosureEditBO.getAccount());
            meetingEnclosureMapper.updateSelective(meetingEnclosure);
            return meetingEnclosure.getId();
        } else {
            throw new SException("附件不存在！");
        }
    }

    @Override
    public void delMeetingEnclosure(MeetingEnclosureDelBO meetingEnclosureDelBO) {
        MeetingEnclosure meetingEnclosure = meetingEnclosureMapper.selectById(meetingEnclosureDelBO.getId());
        if (meetingEnclosure != null) {

            //查询是否有预约
            MeetingBookingExample example = new MeetingBookingExample();
            MeetingBookingExample.Criteria criteria = example.createCriteria();
            criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
            criteria.andStatusEqualTo(Constant.BOOKING_STATUS.IS_BOOKING);
            criteria.andMeetingIdEqualTo(meetingEnclosure.getMeetingId());
            List<MeetingBooking> meetingBookingList = meetingBookingMapper.selectByExample(example);
            if (meetingBookingList.size() > 0) {
                throw new SException("该会议室有预约，无法删除附件！");
            }

            meetingEnclosure.setUpdateUser(meetingEnclosureDelBO.getAccount());
            meetingEnclosure.setDelFlag(Constant.Status.DEL);
            meetingEnclosureMapper.updateSelective(meetingEnclosure);
        } else {
            throw new SException("附件不存在！");
        }
    }

    /**
     * 获取全部路演厅或全部会议室
     *
     * @return
     */
    @Override
    public List<MeetingVO> getAllMeetingRoomByType(Boolean type, String syAccountId) {

        //获取全部路演厅或全部会议室
        MeetingExample example = new MeetingExample();
        MeetingExample.Criteria criteria = example.createCriteria();

        if (type) {
            //会议室
            criteria.andTypeNotEqualTo(Constant.DICT_INFO_ID.ROADSHOWId);
        } else {
            //路演厅
            criteria.andTypeEqualTo(Constant.DICT_INFO_ID.ROADSHOWId);
        }

        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<Meeting> meetingList = meetingMapper.selectByExample(example);
        List<MeetingVO> meetingVOList = new ArrayList<>();
        if (meetingList.size() > 0) {
            for (Meeting meeting : meetingList) {
                //小程序无法查看内部会议室
                if (syAccountId != null) {
                    List<SyRole> roles = syRoleMapper.selectByAccountId(syAccountId);
                    if ((roles == null || roles.size() <= 0) &&
                            meeting.getType() == Constant.DICT_INFO_ID.INSIDEMEETINGId) {
                        continue;
                    }
                }
                MeetingVO meetingVO = new MeetingVO();
                BeanUtils.copyProperties(meeting, meetingVO);
                //关联会议室摆放要求
                List<TRelationVO> tRelationList = relationBusiness.searchRelationList(Constant.RELATION_TYPE.MEETING,
                        meeting.getId());
                meetingVO.setTRelation(tRelationList);
                //关联附件
                List<MeetingEnclosure> meetingEnclosures = meetingEnclosureMapper.selectByMeetingId(meeting.getId());
                meetingVO.setEnclosureList(meetingEnclosures);
                meetingVOList.add(meetingVO);
            }
        }
        return meetingVOList;
    }

    /**
     * 查询路演厅预约时间
     *
     * @param meetingBookingTimeQueryBO
     * @return
     */
    @Override
    public List<RoadshowBookingTimeVO> queryBookingTime(MeetingBookingTimeQueryBO meetingBookingTimeQueryBO) {

        List<RoadshowBookingTimeVO> bookingTimeVOList = new ArrayList<>();
        if (meetingBookingTimeQueryBO.getMeetingId() == null) {
            //查询全部路演厅1天的预约情况
            List<MeetingVO> meetingList = getAllMeetingRoomByType(false, null);
            if (meetingList.size() > 0) {
                for (Meeting meeting : meetingList) {
                    meetingBookingTimeQueryBO.setMeetingId(meeting.getId());
                    RoadshowBookingTimeVO bookingTimeVO = getRoadshowBookingTimeVO(meetingBookingTimeQueryBO, 0);
                    bookingTimeVOList.add(bookingTimeVO);
                }
            } else {
                throw new SException("没有路演厅数据！");
            }
        } else {
            //获取单个路演厅n天的预约情况
            for (int i = 0; i < meetingBookingTimeQueryBO.getNum(); i++) {
                RoadshowBookingTimeVO bookingTimeVO = getRoadshowBookingTimeVO(meetingBookingTimeQueryBO, i);
                bookingTimeVOList.add(bookingTimeVO);
            }
        }
        return bookingTimeVOList;
    }

    //获取路演厅每日情况
    private RoadshowBookingTimeVO getRoadshowBookingTimeVO(MeetingBookingTimeQueryBO meetingBookingTimeQueryBO, int i) {
        RoadshowBookingTimeVO bookingTimeVO = new RoadshowBookingTimeVO();
        List<RoadshowChildBookingTime> roadshowChildBookingTimeList = new ArrayList<>();
        //日期
        Date date = DateUtil.getPastDate(meetingBookingTimeQueryBO.getVisitDate(), i);
        bookingTimeVO.setBookingDate(date);

        //获得路演厅名称及上午、下午开放时间
        String amBeginTime = "", amEndTime = "", pmBeginTime = "", pmEndTime = "";
        Meeting meeting = meetingMapper.selectMeetingId(meetingBookingTimeQueryBO.getMeetingId());
        if (meeting == null || meeting.getDelFlag() == Constant.Status.DEL) {
//            throw new SException("路演厅不存在！");
            return null;
        } else {
            amBeginTime = meeting.getAmBeginTime();
            amEndTime = meeting.getAmEndTime();
            pmBeginTime = meeting.getPmBeginTime();
            pmEndTime = meeting.getPmEndTime();
            bookingTimeVO.setMeetingName(meeting.getName());
            bookingTimeVO.setMeetingId(meeting.getId());
            bookingTimeVO.setSeatNum(meeting.getSeatNum());
        }

        //该日期为工作日还是周末
        bookingTimeVO.setBookingWeek(DateUtil.getWeek(date));
        if (DateUtil.getWeek(date).equals("星期六") || DateUtil.getWeek(date).equals("星期日")) {
            //周末
            roadshowChildBookingTimeList = getRoadshowChildBookingTimeList(amBeginTime, amEndTime, pmBeginTime, pmEndTime,
                    Constant.BOOKING_TIME_STATUS.CANNOT_BOOKING, Constant.BOOKING_TIME_STATUS.CANNOT_BOOKING);
        } else {
            //工作日
            //判断预约日期，分三种情况，分别设置上、下午的预约状态
            //预约时间大于两个月，无法预约
            //预约时间小于七天，可以正常查看，无法预约
            //预约日期大于七天小于两个月，可以正常查看，可以预约
            long betweenDate = (date.getTime() - (new Date()).getTime()) / (60 * 60 * 24 * 1000);
            if (betweenDate > 60) {
                roadshowChildBookingTimeList = getRoadshowChildBookingTimeList(amBeginTime, amEndTime, pmBeginTime, pmEndTime,
                        Constant.BOOKING_TIME_STATUS.CANNOT_BOOKING, Constant.BOOKING_TIME_STATUS.CANNOT_BOOKING);
            } else if (betweenDate < 6) {
                //获取该日期的预约情况
                List<MeetingBooking> meetingBookingList = getByVisitDate(date, meetingBookingTimeQueryBO.getMeetingId());
                if (meetingBookingList.size() < 0) {
//                    throw new SException(date + "路演厅查询申请记录出现异常！");
                    roadshowChildBookingTimeList = getRoadshowChildBookingTimeList(amBeginTime, amEndTime, pmBeginTime, pmEndTime,
                            Constant.BOOKING_TIME_STATUS.CANNOT_BOOKING, Constant.BOOKING_TIME_STATUS.CANNOT_BOOKING);
                } else if (meetingBookingList.size() == 0) {
                    //如果没有预约，全部设为不可预约。如果有预约，已预约的时间段设为已预约，未预约的时间段设为不可预约
                    roadshowChildBookingTimeList = getRoadshowChildBookingTimeList(amBeginTime, amEndTime, pmBeginTime, pmEndTime,
                            Constant.BOOKING_TIME_STATUS.CANNOT_BOOKING, Constant.BOOKING_TIME_STATUS.CANNOT_BOOKING);
                } else if (meetingBookingList.size() == 1) {
                    //有一次预约
                    MeetingBooking meetingBooking = meetingBookingList.get(0);
                    String beginTime = meetingBooking.getBeginTime();
                    String endTime = meetingBooking.getEndTime();
                    if (Integer.valueOf(beginTime.substring(0, beginTime.indexOf(":"))) < 12
                            && Integer.valueOf(endTime.substring(0, beginTime.indexOf(":"))) > 12) {
                        //此次预定为全天
                        roadshowChildBookingTimeList = getRoadshowChildBookingTimeList(amBeginTime, amEndTime, pmBeginTime, pmEndTime,
                                Constant.BOOKING_TIME_STATUS.IS_BOOKED, Constant.BOOKING_TIME_STATUS.IS_BOOKED);
                    } else {
                        //此次预定为半天
                        if (Integer.valueOf(beginTime.substring(0, beginTime.indexOf(":"))) < 12) {
                            roadshowChildBookingTimeList = getRoadshowChildBookingTimeList(amBeginTime, amEndTime, pmBeginTime, pmEndTime,
                                    Constant.BOOKING_TIME_STATUS.IS_BOOKED, Constant.BOOKING_TIME_STATUS.CANNOT_BOOKING);
                        } else {
                            roadshowChildBookingTimeList = getRoadshowChildBookingTimeList(amBeginTime, amEndTime, pmBeginTime, pmEndTime,
                                    Constant.BOOKING_TIME_STATUS.CANNOT_BOOKING, Constant.BOOKING_TIME_STATUS.IS_BOOKED);
                        }
                    }
                } else if (meetingBookingList.size() >= 2) {
                    //有两次预约
                    roadshowChildBookingTimeList = getRoadshowChildBookingTimeList(amBeginTime, amEndTime, pmBeginTime, pmEndTime,
                            Constant.BOOKING_TIME_STATUS.IS_BOOKED, Constant.BOOKING_TIME_STATUS.IS_BOOKED);
                }
//                else if (meetingBookingList.size() > 2) {
//                    throw new SException(date + "路演厅查询申请记录出现异常！");
//                }
            } else {
                //获取该日期的预约情况
                List<MeetingBooking> meetingBookingList = getByVisitDate(date, meetingBookingTimeQueryBO.getMeetingId());
                if (meetingBookingList.size() < 0) {
//                    throw new SException(date + "路演厅查询申请记录出现异常！");
                } else if (meetingBookingList.size() == 0) {
                    //如果没有预约，全部设为可预约。如果有预约，已预约的时间段设为已预约，未预约的时间段设为可预约
                    roadshowChildBookingTimeList = getRoadshowChildBookingTimeList(amBeginTime, amEndTime, pmBeginTime, pmEndTime,
                            Constant.BOOKING_TIME_STATUS.CAN_BOOKING, Constant.BOOKING_TIME_STATUS.CAN_BOOKING);
                } else if (meetingBookingList.size() == 1) {
                    //有一次预约
                    MeetingBooking meetingBooking = meetingBookingList.get(0);
                    String beginTime = meetingBooking.getBeginTime();
                    String endTime = meetingBooking.getEndTime();
                    if (Integer.valueOf(beginTime.substring(0, beginTime.indexOf(":"))) < 12
                            && Integer.valueOf(endTime.substring(0, beginTime.indexOf(":"))) > 12) {
                        //此次预定为全天
                        roadshowChildBookingTimeList = getRoadshowChildBookingTimeList(amBeginTime, amEndTime, pmBeginTime, pmEndTime,
                                Constant.BOOKING_TIME_STATUS.IS_BOOKED, Constant.BOOKING_TIME_STATUS.IS_BOOKED);
                    } else {
                        //此次预定为半天
                        if (Integer.valueOf(beginTime.substring(0, beginTime.indexOf(":"))) < 12) {
                            roadshowChildBookingTimeList = getRoadshowChildBookingTimeList(amBeginTime, amEndTime, pmBeginTime, pmEndTime,
                                    Constant.BOOKING_TIME_STATUS.IS_BOOKED, Constant.BOOKING_TIME_STATUS.CAN_BOOKING);
                        } else {
                            roadshowChildBookingTimeList = getRoadshowChildBookingTimeList(amBeginTime, amEndTime, pmBeginTime, pmEndTime,
                                    Constant.BOOKING_TIME_STATUS.CAN_BOOKING, Constant.BOOKING_TIME_STATUS.IS_BOOKED);
                        }
                    }
                } else if (meetingBookingList.size() >= 2) {
                    //有两次预约
                    roadshowChildBookingTimeList = getRoadshowChildBookingTimeList(amBeginTime, amEndTime, pmBeginTime, pmEndTime,
                            Constant.BOOKING_TIME_STATUS.IS_BOOKED, Constant.BOOKING_TIME_STATUS.IS_BOOKED);
                }
//                else if (meetingBookingList.size() > 2) {
//                    throw new SException(date + "路演厅查询申请记录出现异常！");
//                }
            }
        }
        bookingTimeVO.setChildBookingTimeList(roadshowChildBookingTimeList);
        return bookingTimeVO;
    }

    /**
     * 通过预约日期查询预约记录
     *
     * @param visitDate
     * @return
     */
    private List<MeetingBooking> getByVisitDate(Date visitDate, Integer meetingId) {
        MeetingBookingExample example = new MeetingBookingExample();
        MeetingBookingExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        criteria.andStatusNotEqualTo(Constant.BOOKING_STATUS.CANCEL_BOOKING);
        criteria.andVisitDateEqualTo(visitDate);
        criteria.andMeetingIdEqualTo(meetingId);
        List<MeetingBooking> meetingBookingList = meetingBookingMapper.selectByExample(example);
        return meetingBookingList;
    }

    /**
     * 获取每日上、下午预定情况
     *
     * @return
     */
    private List<RoadshowChildBookingTime> getRoadshowChildBookingTimeList(String amBeginTime, String amEndTime,
                                                                           String pmBeginTime, String pmEndTime,
                                                                           Integer amStatus, Integer pmStatus) {
        List<RoadshowChildBookingTime> roadshowChildBookingTimeList = new ArrayList<>();
        //上午
        RoadshowChildBookingTime roadshowChildBookingTime = new RoadshowChildBookingTime();
        roadshowChildBookingTime.setAmOrPm("上午");
        roadshowChildBookingTime.setBeginTime(amBeginTime);
        roadshowChildBookingTime.setEndTime(amEndTime);
        roadshowChildBookingTime.setStatus(amStatus);
        roadshowChildBookingTimeList.add(roadshowChildBookingTime);
        //下午
        RoadshowChildBookingTime roadshowChildBookingTime2 = new RoadshowChildBookingTime();
        roadshowChildBookingTime2.setAmOrPm("下午");
        roadshowChildBookingTime2.setBeginTime(pmBeginTime);
        roadshowChildBookingTime2.setEndTime(pmEndTime);
        roadshowChildBookingTime2.setStatus(pmStatus);
        roadshowChildBookingTimeList.add(roadshowChildBookingTime2);
        return roadshowChildBookingTimeList;
    }

    /**
     * 查询会议室预约时间
     *
     * @param meetingBookingTimeQueryBO
     * @return
     */
    @Override
    public List<MeetingBookingTimeVO> queryMeetingBookingTime(MeetingBookingTimeQueryBO meetingBookingTimeQueryBO) {
        List<MeetingBookingTimeVO> bookingTimeVOList = new ArrayList<>();
        if (meetingBookingTimeQueryBO.getMeetingId() == null) {
            //查询全部会议室1天的预约情况
            List<MeetingVO> meetingList = getAllMeetingRoomByType(true, meetingBookingTimeQueryBO.getSyAccountId());
            if (meetingList.size() > 0) {
                for (Meeting meeting : meetingList) {
                    meetingBookingTimeQueryBO.setMeetingId(meeting.getId());
                    MeetingBookingTimeVO bookingTimeVO = getMeetingBookingTimeVO(meetingBookingTimeQueryBO, 0);
                    bookingTimeVOList.add(bookingTimeVO);
                }
            } else {
                throw new SException("没有会议室数据！");
            }
        } else {
            //获取单个会议室n天的预约情况
            for (int i = 0; i < meetingBookingTimeQueryBO.getNum(); i++) {
                MeetingBookingTimeVO bookingTimeVO = getMeetingBookingTimeVO(meetingBookingTimeQueryBO, i);
                bookingTimeVOList.add(bookingTimeVO);
            }
        }
        return bookingTimeVOList;
    }

    //获取会议室每日情况
    private MeetingBookingTimeVO getMeetingBookingTimeVO(MeetingBookingTimeQueryBO meetingBookingTimeQueryBO, int i) {
        MeetingBookingTimeVO bookingTimeVO = new MeetingBookingTimeVO();
        List<ChildBookingTime> childBookingTimeList = new ArrayList<>();
        //日期
        Date date = DateUtil.getPastDate(meetingBookingTimeQueryBO.getVisitDate(), i);
        bookingTimeVO.setBookingDate(date);

        //获得会议室名称及开放时间
        List<String> allOpenTimeList = new ArrayList<>();
        Meeting meeting = meetingMapper.selectMeetingId(meetingBookingTimeQueryBO.getMeetingId());
        if (meeting == null || meeting.getDelFlag() == Constant.Status.DEL) {
//            throw new SException("会议室不存在！");
            return null;
        } else {
            allOpenTimeList = getAllOpenTimeList(meeting.getAmBeginTime(), meeting.getAmEndTime(),
                    meeting.getPmBeginTime(), meeting.getPmEndTime());
            bookingTimeVO.setMeetingName(meeting.getName());
            bookingTimeVO.setMeetingId(meeting.getId());
            bookingTimeVO.setSeatNum(meeting.getSeatNum());
        }

        //会议室一天内的开始时间，以半小时为单位
        List<String> allBeginTimes = DateUtil.getAllMeetingTimeList();
        allBeginTimes.remove(allBeginTimes.size() - 1);

        List<String> allBeginTimesNew = DateUtil.getAllMeetingTimeList();
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
            //每日的预约情况，分三种情况
            //一种是今天及以前，可以正常查看、无法预约
            //一种是今天以后2个月内，可以正常查看、预约
            //一种是2个月后，无法预约
            long betweenDate = (date.getTime() - (new Date()).getTime()) / (60 * 60 * 24 * 1000);
            if (betweenDate > 60) {
                //2个月后
                //不可预订
                allCannotBookingBeginTimes = allBeginTimes;
                childBookingTimeList = getChildBookingTime(allBeginTimesNew, allCannotBookingBeginTimes, null, null);
            } else if (date.before(new Date()) || (DateUtil.getDateStr(date)).equals(DateUtil.getDateStr(new Date()))) {
                //今天及以前
                //查询预约情况
                List<MeetingBooking> meetingBookingList = getByVisitDate(date, meetingBookingTimeQueryBO.getMeetingId());
                if (meetingBookingList.size() > 0) {
                    for (MeetingBooking meetingBooking : meetingBookingList) {
                        List<String> allTimeList = DateUtil.getTimeList();
                        //持续时间
                        List<String> durationList = allTimeList.subList(allTimeList.indexOf(meetingBooking.getBeginTime()),
                                allTimeList.indexOf(meetingBooking.getEndTime()));
                        //已预订时间
                        allIsBookedBeginTimes.addAll(durationList);
                    }
                    //不可预订
                    if (allBeginTimes != null && allIsBookedBeginTimes != null)
                        allBeginTimes.removeAll(allIsBookedBeginTimes);
                    allCannotBookingBeginTimes = allBeginTimes;
                    childBookingTimeList = getChildBookingTime(allBeginTimesNew, allCannotBookingBeginTimes, allIsBookedBeginTimes, null);
                } else {
                    //全部不可预订
                    allCannotBookingBeginTimes = allBeginTimes;
                    childBookingTimeList = getChildBookingTime(allBeginTimesNew, allCannotBookingBeginTimes, null, null);
                }
            } else {
                //2个月内
                //查询预约情况
                List<MeetingBooking> meetingBookingList = getByVisitDate(date, meetingBookingTimeQueryBO.getMeetingId());
                if (meetingBookingList.size() > 0) {
                    for (MeetingBooking meetingBooking : meetingBookingList) {
                        List<String> allTimeList = DateUtil.getTimeList();
                        //持续时间
                        List<String> durationList = allTimeList.subList(allTimeList.indexOf(meetingBooking.getBeginTime()),
                                allTimeList.indexOf(meetingBooking.getEndTime()));
                        //已预订时间
                        allIsBookedBeginTimes.addAll(durationList);
                        //不可预订时间,会议间隔半小时
                        allCannotBookingBeginTimes.add(allTimeList.get(allTimeList.indexOf(meetingBooking.getBeginTime()) - 1));
                        allCannotBookingBeginTimes.add(allTimeList.get(allTimeList.indexOf(meetingBooking.getEndTime())));
                    }
                    //可预订
                    if (allOpenTimeList != null && allIsBookedBeginTimes != null)
                        allOpenTimeList.removeAll(allIsBookedBeginTimes);
                    if (allOpenTimeList != null && allCannotBookingBeginTimes != null)
                        allOpenTimeList.removeAll(allCannotBookingBeginTimes);
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
        List<String> allTimeList = DateUtil.getAllMeetingTimeList();
        for (String s : allBeginTimes) {
            if (allCannotBookingBeginTimes != null && allCannotBookingBeginTimes.contains(s)) {
                ChildBookingTime childBookingTime = new ChildBookingTime();
                childBookingTime.setStatus(Constant.BOOKING_TIME_STATUS.CANNOT_BOOKING);
                childBookingTime.setBeginTime(s);
                childBookingTime.setEndTime(allTimeList.get(allTimeList.indexOf(s) + 1));
                childBookingTimeList.add(childBookingTime);
                continue;
            } else if (allIsBookedBeginTimes != null && allIsBookedBeginTimes.contains(s)) {
                ChildBookingTime childBookingTime = new ChildBookingTime();
                childBookingTime.setStatus(Constant.BOOKING_TIME_STATUS.IS_BOOKED);
                childBookingTime.setBeginTime(s);
                childBookingTime.setEndTime(allTimeList.get(allTimeList.indexOf(s) + 1));
                childBookingTimeList.add(childBookingTime);
                continue;
            } else if (allCanBookingBeginTimes != null && allCanBookingBeginTimes.contains(s)) {
                ChildBookingTime childBookingTime = new ChildBookingTime();
                childBookingTime.setStatus(Constant.BOOKING_TIME_STATUS.CAN_BOOKING);
                childBookingTime.setBeginTime(s);
                childBookingTime.setEndTime(allTimeList.get(allTimeList.indexOf(s) + 1));
                childBookingTimeList.add(childBookingTime);
                continue;
            }
        }
        return childBookingTimeList;
    }

    /**
     * 获取一天中的会议室开放时间点
     *
     * @return
     */
    private List<String> getAllOpenTimeList(String amBeginTime, String amEndTime, String pmBeginTime, String pmEndTime) {
        //会议室允许设置的最大时间
        List<String> allTimeList = DateUtil.getAllMeetingTimeList();

        //上、下午开放时间点
        List<String> amOpenTimeList = allTimeList.subList(allTimeList.indexOf(amBeginTime), allTimeList.indexOf(amEndTime));
        List<String> pmOpenTimeList = allTimeList.subList(allTimeList.indexOf(pmBeginTime), allTimeList.indexOf(pmEndTime));

        //求并集
        amOpenTimeList.removeAll(pmOpenTimeList);
        amOpenTimeList.addAll(pmOpenTimeList);
        return amOpenTimeList;
    }

    /**
     * 添加会议预约
     *
     * @param meetingBookingAddBO
     */
    @Override
    public void addMeetingBooking(MeetingBookingAddBO meetingBookingAddBO) {

        Meeting meeting = meetingMapper.selectMeetingId(meetingBookingAddBO.getMeetingId());
        if (meeting == null || meeting.getDelFlag() == Constant.Status.DEL) {
            throw new SException("会议室不存在或已关闭，无法预约");
        }

        //校验同一时间段不能多次预订
        checkBookingTime(meetingBookingAddBO.getMeetingId(), meetingBookingAddBO.getVisitDate(),
                meetingBookingAddBO.getBeginTime(), meetingBookingAddBO.getEndTime());

        //处理附件
        String enclosures = "";
        if (meetingBookingAddBO.getEnclosureList() != null && meetingBookingAddBO.getEnclosureList().size() > 0) {
            for (MeetingEnclosure meetingEnclosure : meetingBookingAddBO.getEnclosureList()) {
                if (meetingEnclosure.getNum() > 0) {
                    //校验数量
                    MeetingEnclosure old = meetingEnclosureMapper.selectById(meetingEnclosure.getId());
                    if (old == null) {
                        throw new SException("附件" + meetingEnclosure.getName() + "不存在！");
                    }
                    if (old.getNum() < meetingEnclosure.getNum()) {
                        throw new SException("附件" + meetingEnclosure.getName() + "数量不足！");
                    }
                    enclosures += meetingEnclosure.getName() + ":" + meetingEnclosure.getNum() + ";";
                }
            }
        }

        MeetingBooking meetingBooking = new MeetingBooking();
        BeanUtils.copyProperties(meetingBookingAddBO, meetingBooking);
        meetingBooking.setDelFlag(Constant.Status.NORMAL);
        meetingBooking.setStatus(Constant.BOOKING_STATUS.IS_BOOKING);
        meetingBooking.setCreateUser(meetingBookingAddBO.getAccount());
        meetingBooking.setUpdateUser(meetingBookingAddBO.getAccount());
        meetingBooking.setAccountId(meetingBookingAddBO.getSyAccountId());
        meetingBooking.setEnclosures(enclosures);
        meetingBookingMapper.insertSelective(meetingBooking);

        //添加来访公司
        //转为name集合
        List<String> nameList = meetingBookingAddBO.getVisitCompanyList().stream().map(VisitCompany::getName).collect(Collectors.toList());
        visitCompanyBusiness.save(nameList, meetingBooking.getId(), Constant.BOOKING_TYPE.MEETING_BOOKING, meetingBookingAddBO.getAccount());

        SendSmsResponse sendSmsResponse = new SendSmsResponse();
        try {
            //短信通知,参数超过20字符会报错
            String name = "", position = "";
            if (meeting.getName() != null && meeting.getName().length() > 20) {
                name = meeting.getName().substring(0, 20);
            } else {
                name = meeting.getName();
            }
            if (meeting.getPosition() != null && meeting.getPosition().length() > 20) {
                position = meeting.getPosition().substring(0, 20);
            } else {
                position = meeting.getPosition();
            }
            String time = DateUtil.getDateStr2(meetingBookingAddBO.getVisitDate()) + " " +
                    meetingBookingAddBO.getBeginTime() + "-" + meetingBookingAddBO.getEndTime();
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("time", time);
            map.put("position", position);
            SmsConfig smsConfig = settingService.getSmsConf("message");

            SmsUtil smsUtil = new SmsUtil();
            sendSmsResponse = smsUtil.sendMessage(meetingBookingAddBO.getBookingPhone(), smsConfig, map);
        } catch (ClientException e) {
            throw new SException(sendSmsResponse.getMessage());
        }
    }

    //校验是否同一时间段是否重复预约
    private void checkBookingTime(Integer meetingId, Date visitDate, String beginTime, String endTime) {
        MeetingBookingExample example = new MeetingBookingExample();
        MeetingBookingExample.Criteria criteria = example.createCriteria();
        criteria.andMeetingIdEqualTo(meetingId);
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        criteria.andStatusNotEqualTo(Constant.BOOKING_STATUS.CANCEL_BOOKING);
        criteria.andVisitDateEqualTo(visitDate);
        List<MeetingBooking> meetingBookingList = meetingBookingMapper.selectByExample(example);
        if (meetingBookingList != null && meetingBookingList.size() > 0) {
            for (MeetingBooking meetingBooking : meetingBookingList) {
                //一天24小时
                List<String> timeList = DateUtil.getTimeList();
                String oldBeginTime = meetingBooking.getBeginTime();
                String oldEndTime = meetingBooking.getEndTime();
                //时间取交集
                List<String> oldList = timeList.subList(timeList.indexOf(oldBeginTime) - 1, timeList.indexOf(oldEndTime) + 1);
                List<String> newList = timeList.subList(timeList.indexOf(beginTime), timeList.indexOf(endTime));
                if (oldList != null && oldList.size() > 0) {
                    for (String s : oldList) {
                        if (newList.contains(s)) {
                            throw new SException("该时间已经预约，请重新选择时间！");
                        }
                    }
                }
            }
        }
    }

    /**
     * 按条件查询预约情况
     *
     * @param meetingBookingQueryBO
     */
    @Override
    public PageInfo<MeetingBookingVO> queryMeetingBooking(MeetingBookingQueryBO meetingBookingQueryBO) {
        //处理查询条件
        MeetingBookingExample example = new MeetingBookingExample();
        MeetingBookingExample.Criteria criteria = example.createCriteria();
        //账号id
        if (meetingBookingQueryBO.getAccountId() != null) {
            criteria.andAccountIdEqualTo(meetingBookingQueryBO.getAccountId());
        }
        //预约日期
        if (meetingBookingQueryBO.getVisitDate() != null) {
            criteria.andVisitDateEqualTo(meetingBookingQueryBO.getVisitDate());
        }
        //预约时间段
        if (meetingBookingQueryBO.getBeginDate() != null && meetingBookingQueryBO.getEndDate() != null) {
            criteria.andVisitDateBetween(meetingBookingQueryBO.getBeginDate(),
                    meetingBookingQueryBO.getEndDate());
        }
        //状态(-2:取消，-1:未到访,0:预约中,1:到访)
        if (meetingBookingQueryBO.getStatus() != null) {
            criteria.andStatusEqualTo(meetingBookingQueryBO.getStatus());
        }
        //预约通道（0:小程序，1:web端）
        if (meetingBookingQueryBO.getPassageway() != null) {
            criteria.andPassagewayEqualTo(meetingBookingQueryBO.getPassageway());
        }
        //会议室
        if (meetingBookingQueryBO.getMeetingId() != null) {
            criteria.andMeetingIdEqualTo(meetingBookingQueryBO.getMeetingId());
        }
        //预约人
        if (meetingBookingQueryBO.getBookingPersion() != null && !"".equals(meetingBookingQueryBO.getBookingPersion())) {
            criteria.andBookingPersionLike("%" + meetingBookingQueryBO.getBookingPersion() + "%");
        }
        //单位名称
        if (meetingBookingQueryBO.getCompanyName() != null && !"".equals(meetingBookingQueryBO.getCompanyName())) {
            List<VisitCompany> visitCompanyList = visitCompanyBusiness.getByName(meetingBookingQueryBO.getCompanyName(),
                    Constant.BOOKING_TYPE.MEETING_BOOKING);
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
        PageHelper.startPage(meetingBookingQueryBO.getPageNumber(), meetingBookingQueryBO.getPageSize());
        List<MeetingBooking> meetingBookingList = meetingBookingMapper.selectByExample(example);
        //返回对象
        PageInfo pageInfo = new PageInfo(meetingBookingList);
        List<MeetingBookingVO> meetingBookingVOList = new ArrayList<>();
        for (MeetingBooking meetingBooking : meetingBookingList) {
            MeetingBookingVO meetingBookingVO = new MeetingBookingVO();
            BeanUtils.copyProperties(meetingBooking, meetingBookingVO);
            //关联会议室名称
            if (meetingBookingVO.getMeetingId() != null) {
                Meeting meeting = meetingMapper.selectMeetingId(meetingBookingVO.getMeetingId());
                if (meeting != null && meeting.getDelFlag() == Constant.Status.NORMAL) {
                    meetingBookingVO.setMeetingName(meeting.getName());
                }
            }
//            //关联单位类型
//            if (meetingBookingVO.getCompanyTypeId() != null) {
//                DictInfo dictInfo = dictInfoMapper.selectByPrimaryKey(meetingBookingVO.getCompanyTypeId());
//                if (dictInfo != null && dictInfo.getDelFlag() == Constant.Status.NORMAL) {
//                    meetingBookingVO.setCompanyTypeName(dictInfo.getTitle());
//                }
//            }
            //关联取消原因
            if (meetingBookingVO.getCancelReasonId() != null) {
                DictInfo dictInfo = dictInfoMapper.selectByPrimaryKey(meetingBookingVO.getCancelReasonId());
                if (dictInfo != null && dictInfo.getDelFlag() == Constant.Status.NORMAL) {
                    meetingBookingVO.setCancelReasonName(dictInfo.getTitle());
                }
            }
            //关联到访图片
            List<TRelationVO> tRelationList = relationBusiness.searchRelationList(Constant.RELATION_TYPE.MEETINGBOOKING,
                    meetingBookingVO.getId());
            meetingBookingVO.setTRelationList(tRelationList);
            //关联路演厅摆放要求
            if (meetingBookingVO.getPutRequirementPicId() != null) {
                meetingBookingVO.setPutDownloadUrl(fileService.download(meetingBookingVO.getPutRequirementPicId()));
            }
            //关联是否为路演厅
            if (meetingBookingVO.getMeetingId() != null) {
                Integer meetingTypeId = meetingMapper.selectMeetingTypeId(meetingBookingVO.getMeetingId());
                if (meetingTypeId != null && meetingTypeId.equals(Constant.DICT_INFO_ID.ROADSHOWId)) {
                    meetingBookingVO.setIsRoadshow(true);
                } else {
                    meetingBookingVO.setIsRoadshow(false);
                }
            }
            //关联来访单位
            List<VisitCompany> visitCompanyList = visitCompanyBusiness.getByBookingId(meetingBookingVO.getId(),
                    Constant.BOOKING_TYPE.MEETING_BOOKING);
            meetingBookingVO.setVisitCompanyList(visitCompanyList);
            meetingBookingVOList.add(meetingBookingVO);
        }
        pageInfo.setList(meetingBookingVOList);
        return pageInfo;
    }

    /**
     * 更新会议预约信息
     *
     * @param meetingBookingEditBO
     */
    @Transactional
    @Override
    public void editMeetingBooking(MeetingBookingEditBO meetingBookingEditBO) {
        MeetingBooking meetingBooking = meetingBookingMapper.selectById(meetingBookingEditBO.getId());
        if (meetingBooking != null) {
            MeetingBooking entity = new MeetingBooking();
            BeanUtils.copyProperties(meetingBookingEditBO, entity);
            entity.setUpdateUser(meetingBookingEditBO.getAccount());
            meetingBookingMapper.update(entity);

            //更新来访公司
            List<String> nameList = meetingBookingEditBO.getVisitCompanyList().stream().map(VisitCompany::getName).collect(Collectors.toList());
            visitCompanyBusiness.save(nameList, meetingBooking.getId(), Constant.BOOKING_TYPE.MEETING_BOOKING, meetingBookingEditBO.getAccount());

        } else {
            throw new SException("会议室预约申请不存在！");
        }
    }

    /**
     * 取消会议预约
     *
     * @param meetingBookingCancelBO
     */
    @Override
    public void cancelMeetingBooking(MeetingBookingCancelBO meetingBookingCancelBO) {
        MeetingBooking meetingBooking = meetingBookingMapper.selectById(meetingBookingCancelBO.getId());
        if (meetingBooking != null) {
            MeetingBooking entity = new MeetingBooking();
            BeanUtils.copyProperties(meetingBookingCancelBO, entity);
            entity.setCancelTime(new Date());
            entity.setUpdateUser(meetingBookingCancelBO.getAccount());
            entity.setStatus(Constant.BOOKING_STATUS.CANCEL_BOOKING);
            meetingBookingMapper.update(entity);
        } else {
            throw new SException("会议预约申请不存在！");
        }
    }

    /**
     * 预约未到访
     *
     * @param meetingBookingNoVisitBo
     */
    @Override
    public void noVisit(MeetingBookingNoVisitBo meetingBookingNoVisitBo) {
        MeetingBooking meetingBooking = meetingBookingMapper.selectById(meetingBookingNoVisitBo.getId());
        if (meetingBooking != null) {
            MeetingBooking entity = new MeetingBooking();
            entity.setId(meetingBookingNoVisitBo.getId());
            entity.setUpdateUser(meetingBookingNoVisitBo.getAccount());
            entity.setStatus(Constant.BOOKING_STATUS.ISNOT_VISITED);
            meetingBookingMapper.update(entity);
        } else {
            throw new SException("会议预约申请不存在！");
        }
    }

    /**
     * 到访
     *
     * @param meetingBookingVisitBo
     */
    @Override
    @Transactional
    public void visit(MeetingBookingVisitBo meetingBookingVisitBo) {
        MeetingBooking meetingBooking = meetingBookingMapper.selectById(meetingBookingVisitBo.getId());
        if (meetingBooking != null) {
            MeetingBooking entity = new MeetingBooking();
            BeanUtils.copyProperties(meetingBookingVisitBo, entity);
            entity.setId(meetingBookingVisitBo.getId());
            entity.setUpdateUser(meetingBookingVisitBo.getAccount());
            entity.setStatus(Constant.BOOKING_STATUS.IS_VISITED);
            meetingBookingMapper.update(entity);
        } else {
            throw new SException("会议预约不存在！");
        }

        //更新到访图片
        //查询原有到访图片
        if (meetingBookingVisitBo.getImgUrlList() != null && meetingBookingVisitBo.getImgUrlList().size() > 3) {
            throw new SException("到访图片不能超过三张！");
        }
        List<String> oldList = relationBusiness.searchRelation(Constant.RELATION_TYPE.MEETINGBOOKING,
                meetingBookingVisitBo.getId());
        relationBusiness.updateAttachment(oldList, meetingBookingVisitBo.getImgUrlList(),
                Constant.RELATION_TYPE.MEETINGBOOKING, meetingBookingVisitBo.getId(), meetingBookingVisitBo.getAccount());
//        // 删除原到访图片
//        relationBusiness.deleteRelation(meetingBookingVisitBo.getId(), Constant.RELATION_TYPE.MEETINGBOOKING);
//        if (meetingBookingVisitBo.getImgUrlList() != null && meetingBookingVisitBo.getImgUrlList().size() > 0) {
//            //添加到访图片
//            for (String url : meetingBookingVisitBo.getImgUrlList()) {
//                TRelation tRelation = new TRelation();
//                tRelation.setType(Constant.RELATION_TYPE.MEETINGBOOKING);
//                tRelation.setFileUrl(url);
//                tRelation.setCreateUser(meetingBookingVisitBo.getAccount());
//                tRelation.setUpdateUser(meetingBookingVisitBo.getAccount());
//                tRelation.setOriginalId(meetingBookingVisitBo.getId());
//                relationBusiness.insertRelation(tRelation);
//            }
//        }
    }

    //数据分析
    @Override
    public MeetingBookingAnalysisVo analysis(MeetingBookingAnalysisBo meetingBookingAnalysisBo) {
        MeetingBookingAnalysisVo meetingBookingAnalysisVo = new MeetingBookingAnalysisVo();
        if (meetingBookingAnalysisBo.getBeginDate() != null && meetingBookingAnalysisBo.getEndDate() != null) {

            if (meetingBookingAnalysisBo.getBeginDate() != null && meetingBookingAnalysisBo.getEndDate() != null) {
                //查询会议室
                List<Meeting> meetingList = meetingMapper.selectAllByDate(
                        meetingBookingAnalysisBo.getBeginDate(), meetingBookingAnalysisBo.getEndDate());
                if (meetingList.size() > 0) {
                    //会议室
                    meetingBookingAnalysisVo.setMeetingList(meetingList);

//                    //公司类型与次数
//                    List<CompanyTypeAndTimes> companyTypeAndTimesList = meetingBookingMapper.getCompanyTypeAndTimes(
//                            meetingBookingAnalysisBo.getBeginDate(), meetingBookingAnalysisBo.getEndDate());
//                    meetingBookingAnalysisVo.setCompanyTypeAndTimesList(companyTypeAndTimesList);

                    //各会议室每日接待人员数
                    String beginDate = DateUtil.getDateStr(meetingBookingAnalysisBo.getBeginDate());
                    String endDate = DateUtil.getDateStr(meetingBookingAnalysisBo.getEndDate());
                    List<MeetingBookingAnalysisVo.EverydayPersonTime> everydayPersonTimeList = new ArrayList<>();
                    for (Meeting meeting : meetingList) {
                        MeetingBookingAnalysisVo.EverydayPersonTime everydayPersonTime = meetingBookingAnalysisVo.new EverydayPersonTime();
                        List<ReceptionNumAndDate> receptionNumAndDateList = meetingBookingMapper.getReceptionNumAndDate(
                                beginDate, endDate, meeting.getId());
                        if (receptionNumAndDateList.size() > 0) {
                            List<Date> visitDateList = receptionNumAndDateList.stream().map(p -> p.getVisitDate()).collect(Collectors.toList());
                            List<String> dateStrList = new ArrayList<>();
                            for (Date date : visitDateList) {
//                                dateStrList.add(DateUtil.getNextDateStr(date));
                                dateStrList.add(DateUtil.getDateStr(date));
                            }

                            List<Integer> numList = receptionNumAndDateList.stream().map(p -> p.getNumber()).collect(Collectors.toList());
                            everydayPersonTime.setXAxis(dateStrList);
                            Map<String, Object> seriesMap = new HashMap<>();
                            seriesMap.put("name", meeting.getName());
                            seriesMap.put("data", numList);
                            everydayPersonTime.setSeries(seriesMap);
                        }
                        everydayPersonTimeList.add(everydayPersonTime);
                    }
                    meetingBookingAnalysisVo.setEverydayPersonTimeList(everydayPersonTimeList);

                    //会议室使用率
                    List<MeetingBookingAnalysisVo.MeetingUse> meetingUseList = new ArrayList<>();
                    //计算时间段内的开放日期
                    List<Date> dateList = meetingMapper.selectVisitDateList(beginDate, endDate);
                    //去除周末
                    List<Date> newDateList = new ArrayList<>();
                    for (Date date : dateList) {
                        if (!DateUtil.getWeek(date).equals("星期六") && !DateUtil.getWeek(date).equals("星期日")) {
                            newDateList.add(date);
                        }
                    }
                    if (newDateList.size() > 0) {
                        for (Meeting meeting : meetingList) {
                            MeetingBookingAnalysisVo.MeetingUse meetingUse = meetingBookingAnalysisVo.new MeetingUse();
                            //查询会议室全部开放时长
                            String amBeginTime = meeting.getAmBeginTime();
                            String amEndTime = meeting.getAmEndTime();
                            String pmBeginTime = meeting.getPmBeginTime();
                            String pmEndTime = meeting.getPmEndTime();
                            List<String> allTimes = DateUtil.getTimeList();
                            //该会议室在查询时间内的全部开放时长
                            Integer allHour = ((allTimes.indexOf(amEndTime) - allTimes.indexOf(amBeginTime)) +
                                    (allTimes.indexOf(pmEndTime) - allTimes.indexOf(pmBeginTime))) * newDateList.size() / 2;

                            //查询会议室的使用记录
                            List<MeetingBooking> meetingBookingVOList = meetingBookingMapper.getMeetingBookingTime(
                                    meetingBookingAnalysisBo.getBeginDate(), meetingBookingAnalysisBo.getEndDate(), meeting.getId());

                            //计算会议室使用时长
                            Integer useHalfHour = 0;
                            if (meetingBookingVOList.size() > 0) {
                                for (MeetingBooking meetingBooking : meetingBookingVOList) {
                                    useHalfHour += allTimes.indexOf(meetingBooking.getEndTime()) -
                                            allTimes.indexOf(meetingBooking.getBeginTime());
                                }
                            }
                            //计算会议室空闲时长
                            Integer unUseHour = allHour - useHalfHour / 2;

                            Map<String, Integer> map = new HashMap<>();
                            map.put("使用时长（小时）", useHalfHour / 2);
                            map.put("空闲时长（小时）", unUseHour);
                            meetingUse.setMeetingName(meeting.getName());
                            meetingUse.setMap(map);
                            meetingUseList.add(meetingUse);
                            meetingBookingAnalysisVo.setMeetingUseList(meetingUseList);
                        }
                    }
                }
            }
        } else {
            throw new SException("请输入查询时间！");
        }
        return meetingBookingAnalysisVo;
    }

    /**
     * 导出分析数据
     *
     * @param meetingBookingAnalysisBo
     * @param response
     */
    @Override
    public void exportAnalysisExcel(MeetingBookingAnalysisBo meetingBookingAnalysisBo, HttpServletResponse response) {

        //查询分析数据
        List<String[]> list = getAnalysisData(meetingBookingAnalysisBo);

        //下载Excel
        List<ExcelUtil.SheetData> sheetDataList = new ArrayList<>();
        ExcelUtil excelUtil = new ExcelUtil();

        //表头
        List<String> columnList = Arrays.asList("会议主题", "单位名称", "预约人", "手机号", "会议室名称", "预约日期", "开始时间",
                "结束时间", "预约参会人数", "预约通道", "预约状态", "实际参会人数", "评价", "取消通道", "取消原因", "取消时间");

        if (list == null || list.size() <= 0) {
            throw new SException("该时间范围内暂无数据！");
        }

        ExcelUtil.SheetData sheetData = excelUtil.new SheetData();
        sheetData.setSheetName("会议室预约数据统计");
        sheetData.setColumnList(columnList);
        sheetData.setDataList(list);
        sheetDataList.add(sheetData);

        String fileName = "meeting.xls";
        excelUtil.write(sheetDataList, fileName, response);
    }

    /**
     * 查询统计数据
     *
     * @param meetingBookingAnalysisBo
     * @return
     */
    private List<String[]> getAnalysisData(MeetingBookingAnalysisBo meetingBookingAnalysisBo) {
        List<String[]> dataList = new ArrayList<>();
        if (meetingBookingAnalysisBo.getBeginDate() != null && meetingBookingAnalysisBo.getEndDate() != null) {
            //查询预约数据
            MeetingBookingQueryBO meetingBookingQueryBO = new MeetingBookingQueryBO();
            meetingBookingQueryBO.setBeginDate(meetingBookingAnalysisBo.getBeginDate());
            meetingBookingQueryBO.setEndDate(meetingBookingAnalysisBo.getEndDate());
            meetingBookingQueryBO.setPageNumber(0);
            meetingBookingQueryBO.setPageSize(0);
            List<MeetingBookingVO> list = queryMeetingBooking(meetingBookingQueryBO).getList();

            //处理预约数据
            for (MeetingBookingVO meetingBookingVO : list) {
                String[] arr = new String[16];
                arr[0] = meetingBookingVO.getName();
                if (meetingBookingVO.getVisitCompanyList() != null && meetingBookingVO.getVisitCompanyList().size() > 0) {
                    List<String> nameList = meetingBookingVO.getVisitCompanyList().stream().
                            map(VisitCompany::getName).collect(Collectors.toList());
                    String names = "";
                    for (int i = 0; i < nameList.size(); i++) {
                        if (i != nameList.size() - 1) {
                            names += nameList.get(i) + "、";
                        } else {
                            names += nameList.get(i);
                        }
                    }
                    arr[1] = names;
                }
//                arr[2] = meetingBookingVO.getCompanyTypeName();
                arr[2] = meetingBookingVO.getBookingPersion();
                arr[3] = meetingBookingVO.getBookingPhone();
                arr[4] = meetingBookingVO.getMeetingName();
                if (meetingBookingVO.getVisitDate() != null) {
                    arr[5] = DateUtil.getDateStr(meetingBookingVO.getVisitDate());
                }
                arr[6] = meetingBookingVO.getBeginTime();
                arr[7] = meetingBookingVO.getEndTime();
                if (meetingBookingVO.getBookingNumber() != null) {
                    arr[8] = meetingBookingVO.getBookingNumber().toString();
                }
                if (meetingBookingVO.getPassageway() == null) {
                    arr[9] = "";
                } else if (meetingBookingVO.getPassageway() == 0) {
                    arr[9] = "小程序";
                } else {
                    arr[9] = "电脑端";
                }
                if (meetingBookingVO.getStatus() == 0) {
                    arr[10] = "预约中";
                } else if (meetingBookingVO.getStatus() == 1) {
                    arr[10] = "到访";
                } else if (meetingBookingVO.getStatus() == -1) {
                    arr[10] = "未到访";
                } else if (meetingBookingVO.getStatus() == -2) {
                    arr[10] = "已取消";
                }
                if (meetingBookingVO.getActualNum() != null) {
                    arr[11] = meetingBookingVO.getActualNum().toString();
                }
                if (meetingBookingVO.getAppraise() != null) {
                    arr[12] = meetingBookingVO.getAppraise().toString();
                }
                if (meetingBookingVO.getCancelChannel() == null) {
                    arr[13] = "";
                } else if (meetingBookingVO.getCancelChannel() == 0) {
                    arr[13] = "小程序";
                } else {
                    arr[13] = "电脑端";
                }
                arr[14] = meetingBookingVO.getCancelReasonName();
                if (meetingBookingVO.getCancelTime() != null) {
                    arr[15] = DateUtil.getTimeStr(meetingBookingVO.getCancelTime());
                }

                dataList.add(arr);
            }
        } else {
            throw new SException("请输入查询时间！");
        }
        return dataList;
    }


    //判断是否能够取消，距离开始10分钟不能取消
    @Override
    public Boolean timeDecide(TimeDecideBO timeDecideBO) {
        return DateUtil.timeDecide(timeDecideBO.getVisitDate(), timeDecideBO.getBeginTime());
    }

}


