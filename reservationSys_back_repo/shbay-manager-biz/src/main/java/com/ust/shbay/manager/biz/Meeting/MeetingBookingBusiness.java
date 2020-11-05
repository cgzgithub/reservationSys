package com.ust.shbay.manager.biz.Meeting;

import com.github.pagehelper.PageInfo;
import com.ust.shbay.manager.biz.Meeting.bo.*;
import com.ust.shbay.manager.biz.Meeting.vo.MeetingBookingAnalysisVo;
import com.ust.shbay.manager.biz.Meeting.vo.MeetingBookingTimeVO;
import com.ust.shbay.manager.biz.Meeting.vo.MeetingBookingVO;
import com.ust.shbay.manager.biz.Meeting.vo.RoadshowBookingTimeVO;
import com.ust.shbay.manager.entity.Meeting;
import com.ust.shbay.manager.entity.MeetingEnclosure;
import com.ust.shbay.manager.entity.vo.meeting.MeetingVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface MeetingBookingBusiness {


    /**
     * 新增会议室
     *
     */
    void  addMeetingRoom(MeetingRoomAddBO meetingRoomAddBO);

    /**
     * 编辑会议室
     *
     */
    void  editMeetingRoom(MeetingRoomEditBo meetingRoomEditBo);

    void delMeetingRoom(MeetingRoomDelBo meetingRoomDelBo);

    PageInfo<Meeting> selectByDelFlag(MeetingRoomQueryBO meetingRoomQueryBO);

    void openMeetingRoom(MeetingRoomOpenBo meetingRoomOpenBo);

    Integer addMeetingEnclosure(MeetingEnclosureAddBO meetingEnclosureAddBO);

    Integer editMeetingEnclosure(MeetingEnclosureEditBO meetingEnclosureEditBO);

    void delMeetingEnclosure(MeetingEnclosureDelBO meetingEnclosureDelBO);

    /**
     * 根据会议室id查询附件
     * @param meetingId
     * @return
     */
    List<MeetingEnclosure> getEnclosuresByMeetingId(Integer meetingId);

    /**
     * 获取全部路演厅或全部会议室
     * @return
     */
    List<MeetingVO> getAllMeetingRoomByType(Boolean type,String syAccountId);

    /**
     * 查询路演厅预约时间
     * @param meetingBookingTimeQueryBO
     * @return
     */
    List<RoadshowBookingTimeVO> queryBookingTime(MeetingBookingTimeQueryBO meetingBookingTimeQueryBO);

    /**
     * 查询会议室预约时间
     * @param meetingBookingTimeQueryBO
     * @return
     */
    List<MeetingBookingTimeVO> queryMeetingBookingTime(MeetingBookingTimeQueryBO meetingBookingTimeQueryBO);

    void addMeetingBooking(MeetingBookingAddBO meetingBookingAddBO);

    PageInfo<MeetingBookingVO> queryMeetingBooking(MeetingBookingQueryBO meetingBookingQueryBO);

    void editMeetingBooking(MeetingBookingEditBO meetingBookingEditBO);

    void cancelMeetingBooking(MeetingBookingCancelBO meetingBookingCancelBO);

    void noVisit(MeetingBookingNoVisitBo meetingBookingNoVisitBo);

    void visit(MeetingBookingVisitBo meetingBookingVisitBo);

    MeetingBookingAnalysisVo analysis(MeetingBookingAnalysisBo meetingBookingAnalysisBo);

    /**
     * 导出分析数据
     * @param meetingBookingAnalysisBo
     * @param response
     */
    void exportAnalysisExcel(MeetingBookingAnalysisBo meetingBookingAnalysisBo, HttpServletResponse response);

    //判断是否能够取消，距离开始10分钟不能取消
    Boolean timeDecide(TimeDecideBO timeDecideBO);
}
