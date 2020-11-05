package com.ust.shbay.manager.api.controller;


import com.github.pagehelper.PageInfo;
import com.ust.shbay.common.dto.ResponseEntity;
import com.ust.shbay.manager.api.controller.dto.meeting.*;
import com.ust.shbay.manager.biz.Meeting.MeetingBookingBusiness;
import com.ust.shbay.manager.biz.Meeting.bo.*;
import com.ust.shbay.manager.biz.Meeting.vo.MeetingBookingAnalysisVo;
import com.ust.shbay.manager.biz.Meeting.vo.MeetingBookingTimeVO;
import com.ust.shbay.manager.biz.Meeting.vo.MeetingBookingVO;
import com.ust.shbay.manager.biz.Meeting.vo.RoadshowBookingTimeVO;
import com.ust.shbay.manager.entity.Meeting;
import com.ust.shbay.manager.entity.MeetingEnclosure;
import com.ust.shbay.manager.entity.vo.meeting.MeetingVO;
import com.ust.shbay.service.base.BaseController;
import com.ust.shbay.service.base.BaseUser;
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
import java.util.List;

@Api(tags = "会议室预定")
@RestController
@RequestMapping("api/meeting/")
public class MeetingBookingController extends BaseController {

    @Autowired
    private MeetingBookingBusiness meetingBookingBusiness;

    @ApiOperation(value = "新增会议室")
    @PostMapping("add/meetingRoom")
    public ResponseEntity addMeetingRoom(@Valid @RequestBody MeetingRoomAdd meetingRoomAdd, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        MeetingRoomAddBO meetingRoomAddBO = new MeetingRoomAddBO();
        BeanUtils.copyProperties(meetingRoomAdd, meetingRoomAddBO);
        setBaseAccount(meetingRoomAddBO);
        meetingBookingBusiness.addMeetingRoom(meetingRoomAddBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "编辑会议室")
    @PostMapping("edit/meetingRoom")
    public ResponseEntity editMeetingRoom(@Valid @RequestBody MeetingRoomEdit meetingRoomEdit, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        MeetingRoomEditBo meetingRoomEditBo = new MeetingRoomEditBo();
        BeanUtils.copyProperties(meetingRoomEdit, meetingRoomEditBo);
        setBaseAccount(meetingRoomEditBo);
        meetingBookingBusiness.editMeetingRoom(meetingRoomEditBo);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "删除会议室")
    @PostMapping("del/meetingRoom")
    public ResponseEntity delMeetingRoom(@RequestBody MeetingRoomDel meetingRoomDel) {
        MeetingRoomDelBo meetingRoomDelBo = new MeetingRoomDelBo();
        BeanUtils.copyProperties(meetingRoomDel, meetingRoomDelBo);
        setBaseAccount(meetingRoomDelBo);
        meetingBookingBusiness.delMeetingRoom(meetingRoomDelBo);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "查询会议室")
    @PostMapping("getAllList")
    public ResponseEntity<PageInfo> getAllList(@RequestBody MeetingRoomQuery meetingRoomQuery) {
        MeetingRoomQueryBO meetingRoomQueryBO = new MeetingRoomQueryBO();
        BeanUtils.copyProperties(meetingRoomQuery, meetingRoomQueryBO);
        setBaseAccount(meetingRoomQueryBO);
        PageInfo<Meeting> pageInfo = meetingBookingBusiness.selectByDelFlag(meetingRoomQueryBO);
        return ResponseEntity.buildSuccEntity(pageInfo);
    }

    @ApiOperation(value = "开放会议室")
    @PostMapping("open/meetingroom")
    public ResponseEntity openMeetingRoom(@RequestBody MeetingRoomOpen meetingRoomOpen) {
        MeetingRoomOpenBo meetingRoomOpenBo = new MeetingRoomOpenBo();
        BeanUtils.copyProperties(meetingRoomOpen, meetingRoomOpenBo);
        setBaseAccount(meetingRoomOpenBo);
        meetingBookingBusiness.openMeetingRoom(meetingRoomOpenBo);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "查询会议室附件")
    @PostMapping("getEnclosuresByMeetingId")
    public ResponseEntity getEnclosuresByMeetingId(@RequestBody MeetingEnclosureQuery meetingEnclosureQuery) {
        List<MeetingEnclosure> list = meetingBookingBusiness.getEnclosuresByMeetingId(meetingEnclosureQuery.getMeetingId());
        return ResponseEntity.buildSuccEntity(list);
    }

    @ApiOperation(value = "新增会议室附件")
    @PostMapping("add/meetingEnclosure")
    public ResponseEntity addMeetingEnclosure(@Valid @RequestBody MeetingEnclosureAdd meetingEnclosureAdd, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        MeetingEnclosureAddBO meetingEnclosureAddBO = new MeetingEnclosureAddBO();
        BeanUtils.copyProperties(meetingEnclosureAdd, meetingEnclosureAddBO);
        setBaseAccount(meetingEnclosureAddBO);
        Integer enclosureId = meetingBookingBusiness.addMeetingEnclosure(meetingEnclosureAddBO);
        return ResponseEntity.buildSuccEntity(enclosureId);
    }

    @ApiOperation(value = "编辑会议室附件")
    @PostMapping("edit/meetingEnclosure")
    public ResponseEntity editMeetingEnclosure(@Valid @RequestBody MeetingEnclosureEdit meetingEnclosureEdit, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        MeetingEnclosureEditBO meetingEnclosureEditBO = new MeetingEnclosureEditBO();
        BeanUtils.copyProperties(meetingEnclosureEdit, meetingEnclosureEditBO);
        setBaseAccount(meetingEnclosureEditBO);
        Integer enclosureId =  meetingBookingBusiness.editMeetingEnclosure(meetingEnclosureEditBO);
        return ResponseEntity.buildSuccEntity(enclosureId);
    }

    @ApiOperation(value = "删除会议室附件")
    @PostMapping("del/meetingEnclosure")
    public ResponseEntity delMeetingEnclosure(@RequestBody MeetingEnclosureDel meetingEnclosureDel) {
        MeetingEnclosureDelBO meetingEnclosureDelBO = new MeetingEnclosureDelBO();
        BeanUtils.copyProperties(meetingEnclosureDel, meetingEnclosureDelBO);
        setBaseAccount(meetingEnclosureDelBO);
        meetingBookingBusiness.delMeetingEnclosure(meetingEnclosureDelBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "获取全部路演厅或全部会议室")
    @PostMapping("getAllMeetingRoomByType")
    public ResponseEntity getAllMeetingRoomByType(@RequestBody MeetingRoomQuery meetingRoomQuery) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        List<MeetingVO> list = meetingBookingBusiness.getAllMeetingRoomByType(meetingRoomQuery.getType(),baseUser.getSyAccountId());
        return ResponseEntity.buildSuccEntity(list);
    }

    @ApiOperation(value = "查询路演厅预约时间")
    @PostMapping("/roadshowBooking/queryTime")
    public ResponseEntity queryBookingTime(@Valid @RequestBody MeetingBookingTimeQuery bookingTimeQuery, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        MeetingBookingTimeQueryBO bookingTimeQueryBO = new MeetingBookingTimeQueryBO();
        BeanUtils.copyProperties(bookingTimeQuery,bookingTimeQueryBO);
        setBaseAccount(bookingTimeQueryBO);
        List<RoadshowBookingTimeVO> list =meetingBookingBusiness.queryBookingTime(bookingTimeQueryBO);
        return ResponseEntity.buildSuccEntity(list);
    }

    @ApiOperation(value = "查询会议室预约时间")
    @PostMapping("/meetingBooking/queryTime")
    public ResponseEntity queryMeetingBookingTime(@Valid @RequestBody MeetingBookingTimeQuery bookingTimeQuery, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        MeetingBookingTimeQueryBO bookingTimeQueryBO = new MeetingBookingTimeQueryBO();
        BeanUtils.copyProperties(bookingTimeQuery,bookingTimeQueryBO);
        setBaseAccount(bookingTimeQueryBO);
        List<MeetingBookingTimeVO> list = meetingBookingBusiness.queryMeetingBookingTime(bookingTimeQueryBO);
        return ResponseEntity.buildSuccEntity(list);
    }

    @ApiOperation(value = "新增预约")
    @PostMapping("/booking/add")
    public ResponseEntity addMeetingBooking(@Valid @RequestBody MeetingBookingAdd meetingBookingAdd, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        MeetingBookingAddBO meetingBookingAddBO = new MeetingBookingAddBO();
        BeanUtils.copyProperties(meetingBookingAdd, meetingBookingAddBO);
        setBaseAccount(meetingBookingAddBO);
        meetingBookingBusiness.addMeetingBooking(meetingBookingAddBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "查询预约列表")
    @PostMapping("/booking/query")
    public ResponseEntity<PageInfo> queryMeetingBooking(@RequestBody MeetingBookingQuery meetingBookingQuery) {
        MeetingBookingQueryBO meetingBookingQueryBO = new MeetingBookingQueryBO();
        BeanUtils.copyProperties(meetingBookingQuery, meetingBookingQueryBO);
        setBaseAccount(meetingBookingQueryBO);
        PageInfo<MeetingBookingVO> pageInfo = meetingBookingBusiness.queryMeetingBooking(meetingBookingQueryBO);
        return ResponseEntity.buildSuccEntity(pageInfo);
    }

    @ApiOperation(value = "编辑预约信息")
    @PostMapping("/booking/edit")
    public ResponseEntity editMeetingBooking(@RequestBody MeetingBookingEdit meetingBookingEdit) {
        MeetingBookingEditBO meetingBookingEditBO = new MeetingBookingEditBO();
        BeanUtils.copyProperties(meetingBookingEdit, meetingBookingEditBO);
        setBaseAccount(meetingBookingEditBO);
        meetingBookingBusiness.editMeetingBooking(meetingBookingEditBO);
        return ResponseEntity.buildSuccEntity();
    }


    @ApiOperation(value = "取消预约")
    @PostMapping("/booking/cancel")
    public ResponseEntity cancelMeetingBooking(@Valid @RequestBody MeetingBookingCancel meetingBookingCancel, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        MeetingBookingCancelBO meetingBookingCancelBO = new MeetingBookingCancelBO();
        BeanUtils.copyProperties(meetingBookingCancel, meetingBookingCancelBO);
        setBaseAccount(meetingBookingCancelBO);
        meetingBookingBusiness.cancelMeetingBooking(meetingBookingCancelBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "未到访")
    @PostMapping("/booking/noVisit")
    public ResponseEntity NoVisit(@RequestBody MeetingBookingNoVisit meetingBookingNoVisit) {
        MeetingBookingNoVisitBo meetingBookingNoVisitBo = new MeetingBookingNoVisitBo();
        BeanUtils.copyProperties(meetingBookingNoVisit, meetingBookingNoVisitBo);
        setBaseAccount(meetingBookingNoVisitBo);
        meetingBookingBusiness.noVisit(meetingBookingNoVisitBo);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "到访")
    @PostMapping("/booking/visit")
    public ResponseEntity visit(@Valid @RequestBody MeetingBookingVisit meetingBookingVisit, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        MeetingBookingVisitBo meetingBookingVisitBo = new MeetingBookingVisitBo();
        BeanUtils.copyProperties(meetingBookingVisit, meetingBookingVisitBo);
        setBaseAccount(meetingBookingVisitBo);
        meetingBookingBusiness.visit(meetingBookingVisitBo);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "数据分析")
    @PostMapping("/booking/analysis")
    public ResponseEntity analysis(@RequestBody MeetingBookingAnalysis meetingBookingAnalysis ) {
        MeetingBookingAnalysisBo meetingBookingAnalysisBo=new  MeetingBookingAnalysisBo();
        BeanUtils.copyProperties(meetingBookingAnalysis,meetingBookingAnalysisBo);
        setBaseAccount(meetingBookingAnalysisBo);
        MeetingBookingAnalysisVo meetingBookingAnalysisVo=meetingBookingBusiness.analysis(meetingBookingAnalysisBo);
        return ResponseEntity.buildSuccEntity(meetingBookingAnalysisVo);
    }

    @ApiOperation(value = "导出分析数据")
    @PostMapping(value = "exportAnalysisExcel" , produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportAnalysisExcel(@RequestBody MeetingBookingAnalysis meetingBookingAnalysis, HttpServletResponse response){
        MeetingBookingAnalysisBo meetingBookingAnalysisBo=new  MeetingBookingAnalysisBo();
        BeanUtils.copyProperties(meetingBookingAnalysis,meetingBookingAnalysisBo);
        setBaseAccount(meetingBookingAnalysisBo);
        meetingBookingBusiness.exportAnalysisExcel(meetingBookingAnalysisBo,response);
    }

    //判断是否能够取消，距离开始10分钟不能取消
    @ApiOperation(value = "判断预约状态")
    @PostMapping("/booking/timeDecide")
    public ResponseEntity timeDecide(@RequestBody TimeDecide timeDecide) {
        TimeDecideBO timeDecideBO = new TimeDecideBO();
        BeanUtils.copyProperties(timeDecide, timeDecideBO);
        setBaseAccount(timeDecideBO);
        Boolean b = meetingBookingBusiness.timeDecide(timeDecideBO);
        return ResponseEntity.buildSuccEntity(b);
    }




}