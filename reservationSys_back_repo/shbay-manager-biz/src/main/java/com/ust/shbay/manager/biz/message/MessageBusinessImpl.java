package com.ust.shbay.manager.biz.message;

import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.utils.DateUtil;
import com.ust.shbay.manager.biz.Apartment.ApartmentBusiness;
import com.ust.shbay.manager.biz.Apartment.bo.ApplyApartmentSearchBo;
import com.ust.shbay.manager.biz.Meeting.MeetingBookingBusiness;
import com.ust.shbay.manager.biz.Meeting.bo.MeetingBookingQueryBO;
import com.ust.shbay.manager.biz.Meeting.vo.MeetingBookingVO;
import com.ust.shbay.manager.biz.message.vo.MessageVO;
import com.ust.shbay.manager.biz.showroom.ShowroomBookingBusiness;
import com.ust.shbay.manager.biz.showroom.bo.ShowroomBookingQueryBO;
import com.ust.shbay.manager.biz.showroom.vo.ShowroomBookingVO;
import com.ust.shbay.manager.dao.SyMenuMapper;
import com.ust.shbay.manager.entity.SyMenu;
import com.ust.shbay.manager.entity.vo.ApartmentApplyVo;
import com.ust.shbay.service.base.BaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MessageBusinessImpl implements MessageBusiness {

    @Autowired
    private MeetingBookingBusiness meetingBookingBusiness;

    @Autowired
    private ShowroomBookingBusiness showroomBookingBusiness;

    @Autowired
    private SyMenuMapper syMenuMapper;

    @Autowired
    private ApartmentBusiness apartmentBusiness;

    /**
     * 获取人才公寓审批消息和会议室、展厅、路演厅预约消息
     *
     * @param baseUser
     * @return
     */
    public List<MessageVO> getMessageList(BaseUser baseUser) {
        List<MessageVO> messageVOList = new ArrayList<>();
        //查看个人会议室预约信息
        List<MeetingBookingVO> meetingBookingVOList = getMeetingBookingList(baseUser);
        if (meetingBookingVOList.size() > 0) {
            for (MeetingBookingVO meetingBookingVO : meetingBookingVOList) {
                MessageVO messageVO = new MessageVO();
                messageVO.setTime(meetingBookingVO.getCreateTime());
                if (meetingBookingVO.getIsRoadshow()) {
                    messageVO.setType("路演厅预约");
                    messageVO.setContent("您有一个路演厅预约申请，预约时间：" + DateUtil.getDateStr(meetingBookingVO.getVisitDate()) + " " +
                            meetingBookingVO.getBeginTime() + "-" + meetingBookingVO.getEndTime() + "; "
                            + "预约地点：" + meetingBookingVO.getMeetingName());
                } else {
                    messageVO.setType("会议室厅预约");
                    messageVO.setContent("您有一个会议室预约申请，预约时间：" + DateUtil.getDateStr(meetingBookingVO.getVisitDate()) + " "
                            + meetingBookingVO.getBeginTime() + "-" + meetingBookingVO.getEndTime() + "; "
                            + "预约地点：" + meetingBookingVO.getMeetingName());
                }
                messageVOList.add(messageVO);
            }
        }

        //查看个人展厅预约信息
        List<ShowroomBookingVO> showroomBookingList = getShowroomBookingList(baseUser);
        if (showroomBookingList.size() > 0) {
            for (ShowroomBookingVO showroomBookingVO : showroomBookingList) {
                MessageVO messageVO = new MessageVO();
                messageVO.setTime(showroomBookingVO.getCreateTime());
                messageVO.setType("展厅预约");
                messageVO.setContent("您有一个展厅预约申请，预约时间：" + DateUtil.getDateStr(showroomBookingVO.getVisitDate()) + " "
                        + showroomBookingVO.getBeginTime() + "-" + showroomBookingVO.getEndTime() + "; "
                        + "预约地点：" + showroomBookingVO.getShowroomName());
                messageVOList.add(messageVO);
            }
        }

        //查看个人公寓审批列表
        //查询当前用户的菜单权限
        List<SyMenu> menuList = syMenuMapper.selectAccountMenu(baseUser.getSyAccountId());
        if (menuList.size() > 0) {
            for (SyMenu syMenu : menuList) {
                if (syMenu.getPath().equals(Constant.MENU_PATH.REVIEWLIST_SELF)) {
                    //人才公寓复核
                    List<ApartmentApplyVo> applyList = searchApplyApartment(Constant.APARTMENT_STATUS_TYPE.APARTMENT_TEAM_REVIEW);
                    if (applyList.size() > 0) {
                        for (ApartmentApplyVo apartmentApplyVo : applyList) {
                            MessageVO messageVO = new MessageVO();
                            messageVO.setTime(apartmentApplyVo.getApplyDate());
                            messageVO.setType("人才公寓复核");
                            messageVO.setContent("您有一条公寓申请待审核，申请人：" + apartmentApplyVo.getName() + "; "
                                    + "电话：" + apartmentApplyVo.getPhone() + ";"
                            );
                            messageVOList.add(messageVO);
                        }
                    }
                } else if (syMenu.getPath().equals(Constant.MENU_PATH.REVIEWLIST_APARTMENT)) {
                    //湾区分管审核
                    List<ApartmentApplyVo> applyList = searchApplyApartment(Constant.APARTMENT_STATUS_TYPE.AUDIT_UNIT_REVIEW);
                    if (applyList.size() > 0) {
                        for (ApartmentApplyVo apartmentApplyVo : applyList) {
                            MessageVO messageVO = new MessageVO();
                            messageVO.setTime(apartmentApplyVo.getApplyDate());
                            messageVO.setType("湾区分管审核");
                            messageVO.setContent("您有一条公寓申请待审核，申请人：" + apartmentApplyVo.getName() + "; "
                                    + "电话：" + apartmentApplyVo.getPhone() + ";"
                            );
                            messageVOList.add(messageVO);
                        }
                    }
                } else if (syMenu.getPath().equals(Constant.MENU_PATH.REVIEWLIST_COMPANY)) {
                    //湾区总经理审核
                    List<ApartmentApplyVo> applyList = searchApplyApartment(Constant.APARTMENT_STATUS_TYPE.AUDIT_UNIT_LEADER_REVIEW);
                    if (applyList.size() > 0) {
                        for (ApartmentApplyVo apartmentApplyVo : applyList) {
                            MessageVO messageVO = new MessageVO();
                            messageVO.setTime(apartmentApplyVo.getApplyDate());
                            messageVO.setType("湾区总经理审核");
                            messageVO.setContent("您有一条公寓申请待审批，申请人：" + apartmentApplyVo.getName() + "; "
                                    + "电话：" + apartmentApplyVo.getPhone() + ";"
                            );
                            messageVOList.add(messageVO);
                        }
                    }
                } else if (syMenu.getPath().equals(Constant.MENU_PATH.REVIEWLIST_LEADER)) {
                    //镇分管审核
                    List<ApartmentApplyVo> applyList = searchApplyApartment(Constant.APARTMENT_STATUS_TYPE.LEADER_CHARGE_REVIEW);
                    if (applyList.size() > 0) {
                        for (ApartmentApplyVo apartmentApplyVo : applyList) {
                            MessageVO messageVO = new MessageVO();
                            messageVO.setTime(apartmentApplyVo.getApplyDate());
                            messageVO.setType("镇分管审核");
                            messageVO.setContent("您有一条公寓申请待审批，申请人：" + apartmentApplyVo.getName() + "; "
                                    + "电话：" + apartmentApplyVo.getPhone() + ";"
                            );
                            messageVOList.add(messageVO);
                        }
                    }
                }

            }
        }

        //排序
        sortList(messageVOList);
        return messageVOList;
    }

    //查看个人会议室预约信息
    private List<MeetingBookingVO> getMeetingBookingList(BaseUser baseUser) {
        MeetingBookingQueryBO meetingBookingQueryBO = new MeetingBookingQueryBO();
        meetingBookingQueryBO.setAccountId(baseUser.getSyAccountId());
        meetingBookingQueryBO.setPageSize(0);
        meetingBookingQueryBO.setPageNumber(0);
        meetingBookingQueryBO.setStatus(Constant.BOOKING_STATUS.IS_BOOKING);
        List<MeetingBookingVO> meetingBookingVOList = meetingBookingBusiness.queryMeetingBooking(meetingBookingQueryBO).getList();
        return meetingBookingVOList;
    }

    //查看个人展厅预约信息
    private List<ShowroomBookingVO> getShowroomBookingList(BaseUser baseUser) {
        ShowroomBookingQueryBO showroomBookingQueryBO = new ShowroomBookingQueryBO();
        showroomBookingQueryBO.setAccountId(baseUser.getSyAccountId());
        showroomBookingQueryBO.setPageSize(0);
        showroomBookingQueryBO.setPageNumber(0);
        showroomBookingQueryBO.setStatus(Constant.BOOKING_STATUS.IS_BOOKING);
        List<ShowroomBookingVO> showroomBookingList = showroomBookingBusiness.queryShowroomBooking(showroomBookingQueryBO).getList();
        return showroomBookingList;
    }

    //查看个人公寓审批列表
    List<ApartmentApplyVo> searchApplyApartment(Integer status) {
        ApplyApartmentSearchBo applyApartmentSearchBo = new ApplyApartmentSearchBo();
        applyApartmentSearchBo.setPageNumber(0);
        applyApartmentSearchBo.setPageSize(0);
        applyApartmentSearchBo.setStatus(status);
        List<ApartmentApplyVo> list = apartmentBusiness.searchApplyApartment(applyApartmentSearchBo).getList();
        return list;
    }

    //排序
    private void sortList(List<MessageVO> list) {
        {    //排序方法
            Collections.sort(list, new Comparator<MessageVO>() {
                @Override
                public int compare(MessageVO o1, MessageVO o2) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        // format.format(o1.getTime()) 表示 date转string类型 如果是string类型就不要转换了
                        Date dt1 = format.parse(format.format(o1.getTime()));
                        Date dt2 = format.parse(format.format(o2.getTime()));
                        // 这是由大向小排序   如果要由小向大转换比较符号就可以
                        if (dt1.getTime() < dt2.getTime()) {
                            return 1;
                        } else if (dt1.getTime() > dt2.getTime()) {
                            return -1;
                        } else {
                            return 0;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
        }
    }

}
