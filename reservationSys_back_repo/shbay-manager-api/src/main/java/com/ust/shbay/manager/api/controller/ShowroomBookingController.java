package com.ust.shbay.manager.api.controller;


import com.github.pagehelper.PageInfo;
import com.ust.shbay.common.dto.ResponseEntity;
import com.ust.shbay.manager.api.controller.dto.showroom.*;
import com.ust.shbay.manager.biz.showroom.ShowroomBookingBusiness;
import com.ust.shbay.manager.biz.showroom.bo.*;
import com.ust.shbay.manager.biz.showroom.vo.BookingTimeVO;
import com.ust.shbay.manager.biz.showroom.vo.ShowroomBookingAnalysisVO;
import com.ust.shbay.manager.biz.showroom.vo.ShowroomBookingVO;
import com.ust.shbay.manager.entity.Guide;
import com.ust.shbay.manager.entity.Showroom;
import com.ust.shbay.service.base.BaseController;
import com.ust.shbay.service.base.BaseToken;
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

@Api(tags = "展厅预约")
@RestController
@RequestMapping("api/showroom/")
public class ShowroomBookingController extends BaseController {

    @Autowired
    private ShowroomBookingBusiness  showroomBookingBusiness;

    @ApiOperation(value = "新增展厅")
    @PostMapping("add/showroom")
    public ResponseEntity addShowroom(@Valid @RequestBody ShowroomAdd showroomAdd, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ShowroomAddBO showroomAddBO = new ShowroomAddBO();
        BeanUtils.copyProperties(showroomAdd, showroomAddBO);
        setBaseAccount(showroomAddBO);
        showroomBookingBusiness.addShowroom(showroomAddBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "编辑展厅")
    @PostMapping("edit/showroom")
    public ResponseEntity editShowroom(@Valid @RequestBody ShowroomEdit showroomEdit, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ShowroomEditBO showroomEditBO = new ShowroomEditBO();
        BeanUtils.copyProperties(showroomEdit, showroomEditBO);
        setBaseAccount(showroomEditBO);
        showroomBookingBusiness.editShowroom(showroomEditBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "删除展厅")
    @PostMapping("del/showroom")
    public ResponseEntity delMeetingRoom(@RequestBody ShowroomDel showroomDel) {
        ShowroomDelBO showroomDelBO = new ShowroomDelBO();
        BeanUtils.copyProperties(showroomDel, showroomDelBO);
        setBaseAccount(showroomDelBO);
        showroomBookingBusiness.delShowroom(showroomDelBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "查询展厅")
    @PostMapping("getAllShowroomList")
    public ResponseEntity<PageInfo> getAllShowroomList(@RequestBody ShowroomQuery showroomQuery) {
        ShowroomQueryBO showroomQueryBO = new ShowroomQueryBO();
        BeanUtils.copyProperties(showroomQuery, showroomQueryBO);
        setBaseAccount(showroomQueryBO);
        PageInfo<Showroom> pageInfo = showroomBookingBusiness.getAllShowroomList(showroomQueryBO);
        return ResponseEntity.buildSuccEntity(pageInfo);
    }

    @ApiOperation(value = "开放展厅")
    @PostMapping("open/showroom")
    public ResponseEntity openShowroom(@RequestBody ShowroomOpen showroomOpen) {
        ShowroomOpenBO showroomOpenBO = new ShowroomOpenBO();
        BeanUtils.copyProperties(showroomOpen, showroomOpenBO);
        setBaseAccount(showroomOpenBO);
        showroomBookingBusiness.openShowroom(showroomOpenBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "新增讲解员")
    @PostMapping("/guide/add")
    public ResponseEntity addGuide(@Valid @RequestBody GuideAdd guideAdd, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        GuideAddBo guideAddBo=new GuideAddBo();
        BeanUtils.copyProperties(guideAdd, guideAddBo);
        setBaseAccount(guideAddBo);
        showroomBookingBusiness.addGuide(guideAddBo);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "编辑讲解员")
    @PostMapping("/guide/edit")
    public ResponseEntity editGuide(@Valid @RequestBody GuideEdit guideEdit, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        GuideEditBO guideEditBO=new GuideEditBO();
        BeanUtils.copyProperties(guideEdit, guideEditBO);
        setBaseAccount(guideEditBO);
        showroomBookingBusiness.editGuide(guideEditBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "删除讲解员")
    @PostMapping("/guide/delete")
    public ResponseEntity delGuide(@RequestBody GuideDel guideDel) {
        GuideDelBO guideDelBO=new GuideDelBO();
        BeanUtils.copyProperties(guideDel,guideDelBO);
        setBaseAccount(guideDelBO);
        showroomBookingBusiness.delGuide(guideDelBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "获取讲解员列表")
    @PostMapping("/guide/getAllList")
    public ResponseEntity getAllList(@RequestBody BaseToken token) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        List<Guide> list =showroomBookingBusiness.selectByDelFlag();
        return ResponseEntity.buildSuccEntity(list);
    }

    @ApiOperation(value = "查询展厅预约时间")
    @PostMapping("/booking/queryTime")
    public ResponseEntity queryBookingTime(@Valid @RequestBody ShowroomBookingTimeQuery roadshowBookingTimeQuery, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ShowroomBookingTimeQueryBO showroomBookingTimeQueryBO = new ShowroomBookingTimeQueryBO();
        BeanUtils.copyProperties(roadshowBookingTimeQuery, showroomBookingTimeQueryBO);
        setBaseAccount(showroomBookingTimeQueryBO);
        List<BookingTimeVO> list =showroomBookingBusiness.queryBookingTime(showroomBookingTimeQueryBO);
        return ResponseEntity.buildSuccEntity(list);
    }

    @ApiOperation(value = "新增预约")
    @PostMapping("/booking/add")
    public ResponseEntity addShowroomBooking(@Valid @RequestBody ShowroomBookingAdd showroomBookingAdd,
                                             BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ShowroomBookingAddBO showroomBookingAddBO = new ShowroomBookingAddBO();
        BeanUtils.copyProperties(showroomBookingAdd, showroomBookingAddBO);
        setBaseAccount(showroomBookingAddBO);
        showroomBookingBusiness.addShowroomBooking(showroomBookingAddBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "查询预约列表")
    @PostMapping("/booking/query")
    public ResponseEntity<PageInfo> queryShowroomBooking(@RequestBody ShowroomBookingQuery showroomBookingQuery) {
        ShowroomBookingQueryBO showroomBookingQueryBO = new ShowroomBookingQueryBO();
        BeanUtils.copyProperties(showroomBookingQuery, showroomBookingQueryBO);
        setBaseAccount(showroomBookingQueryBO);
        PageInfo<ShowroomBookingVO> pageInfo = showroomBookingBusiness.queryShowroomBooking(showroomBookingQueryBO);
        return ResponseEntity.buildSuccEntity(pageInfo);
    }

    @ApiOperation(value = "编辑预约信息")
    @PostMapping("/booking/edit")
    public ResponseEntity editShowroomBooking(@RequestBody ShowroomBookingEdit showroomBookingEdit) {
        ShowroomBookingEditBO showroomBookingEditBO = new ShowroomBookingEditBO();
        BeanUtils.copyProperties(showroomBookingEdit, showroomBookingEditBO);
        setBaseAccount(showroomBookingEditBO);
        showroomBookingBusiness.editShowroomBooking(showroomBookingEditBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "取消预约")
    @PostMapping("/booking/cancel")
    public ResponseEntity cancelShowroomBooking(@Valid @RequestBody ShowroomBookingCancel showroomBookingCancel,
                                                BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ShowroomBookingCancelBO showroomBookingCancelBO = new ShowroomBookingCancelBO();
        BeanUtils.copyProperties(showroomBookingCancel, showroomBookingCancelBO);
        setBaseAccount(showroomBookingCancelBO);
        showroomBookingBusiness.cancelShowroomBooking(showroomBookingCancelBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "未到访")
    @PostMapping("/booking/noVisit")
    public ResponseEntity NoVisit(@RequestBody ShowroomBookingNoVisit showroomBookingNoVisit) {
        ShowroomBookingNoVisitBO showroomBookingNoVisitBO = new ShowroomBookingNoVisitBO();
        BeanUtils.copyProperties(showroomBookingNoVisit, showroomBookingNoVisitBO);
        setBaseAccount(showroomBookingNoVisitBO);
        showroomBookingBusiness.noVisit(showroomBookingNoVisitBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "到访")
    @PostMapping("/booking/visit")
    public ResponseEntity visit(@Valid @RequestBody ShowroomBookingVisit showroomBookingVisit,
                                BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ShowroomBookingVisitBO showroomBookingVisitBO = new ShowroomBookingVisitBO();
        BeanUtils.copyProperties(showroomBookingVisit, showroomBookingVisitBO);
        setBaseAccount(showroomBookingVisitBO);
        showroomBookingBusiness.visit(showroomBookingVisitBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "数据分析")
    @PostMapping("/booking/analysis")
    public ResponseEntity analysis(@RequestBody ShowroomBookingAnalysis showroomBookingAnalysis) {
        ShowroomBookingAnalysisBO showroomBookingAnalysisBO = new ShowroomBookingAnalysisBO();
        BeanUtils.copyProperties(showroomBookingAnalysis, showroomBookingAnalysisBO);
        setBaseAccount(showroomBookingAnalysisBO);
        ShowroomBookingAnalysisVO showroomBookingAnalysisVO = showroomBookingBusiness.analysis(showroomBookingAnalysisBO);
        return ResponseEntity.buildSuccEntity(showroomBookingAnalysisVO);
    }

    @ApiOperation(value = "导出分析数据")
    @PostMapping(value = "exportAnalysisExcel" , produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportAnalysisExcel(@RequestBody ShowroomBookingAnalysis showroomBookingAnalysis, HttpServletResponse response){
        ShowroomBookingAnalysisBO showroomBookingAnalysisBO = new ShowroomBookingAnalysisBO();
        BeanUtils.copyProperties(showroomBookingAnalysis,showroomBookingAnalysisBO);
        setBaseAccount(showroomBookingAnalysisBO);
        showroomBookingBusiness.exportAnalysisExcel(showroomBookingAnalysisBO,response);
    }

    @ApiOperation(value = "获取默认讲解员id")
    @PostMapping("/getDefaultGuide")
    public ResponseEntity getDefaultGuide(@RequestBody BaseToken baseToken) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        Integer defaultGuideId = showroomBookingBusiness.getDefaultGuide();
        return ResponseEntity.buildSuccEntity(defaultGuideId);
    }
}
