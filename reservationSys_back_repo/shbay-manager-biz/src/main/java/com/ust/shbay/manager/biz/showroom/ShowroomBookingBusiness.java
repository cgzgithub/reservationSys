package com.ust.shbay.manager.biz.showroom;

import com.github.pagehelper.PageInfo;
import com.ust.shbay.manager.biz.showroom.bo.*;
import com.ust.shbay.manager.biz.showroom.vo.BookingTimeVO;
import com.ust.shbay.manager.biz.showroom.vo.ShowroomBookingAnalysisVO;
import com.ust.shbay.manager.biz.showroom.vo.ShowroomBookingVO;
import com.ust.shbay.manager.entity.Guide;
import com.ust.shbay.manager.entity.Showroom;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ShowroomBookingBusiness {

    /**
     * 新增展厅
     * @param showroomAddBO
     */
    void addShowroom(ShowroomAddBO showroomAddBO);

    /**
     * 编辑展厅
     * @param showroomEditBO
     */
    void editShowroom(ShowroomEditBO showroomEditBO);

    /**
     * 删除展厅
     * @param showroomDelBO
     */
    void delShowroom(ShowroomDelBO showroomDelBO);

    /**
     * 获取展厅列表
     * @param showroomQueryBO
     * @return
     */
    PageInfo<Showroom> getAllShowroomList(ShowroomQueryBO showroomQueryBO);

    /**
     * 开放展厅
     * @param showroomOpenBO
     */
    void openShowroom(ShowroomOpenBO showroomOpenBO);

    /**
     * 新增讲解员
     *
     * @param guideAddBo
     */
    void addGuide(GuideAddBo guideAddBo);

    /**
     * 删除讲解员
     *
     * @param guideDelBO
     */
    void delGuide(GuideDelBO guideDelBO);

    /**
     * 更改讲解员
     *
     * @param guideEditBO
     */
    void editGuide(GuideEditBO guideEditBO);

    /**
     * 查询讲解员
     *
     */
    List<Guide> selectByDelFlag();

    /**
     * 查询展厅预约时间
     * @param showroomBookingTimeQueryBO
     * @return
     */
    List<BookingTimeVO> queryBookingTime(ShowroomBookingTimeQueryBO showroomBookingTimeQueryBO);

    /**
     * 添加展厅预约
     * @param showroomBookingAddBO
     */
    void addShowroomBooking(ShowroomBookingAddBO showroomBookingAddBO);

    /**
     * 按条件查询预约情况
     * @param showroomBookingQueryBO
     * @return
     */
    PageInfo<ShowroomBookingVO> queryShowroomBooking(ShowroomBookingQueryBO showroomBookingQueryBO);

    /**
     * 更新展厅预约信息
     * @param showroomBookingEditBO
     */
    void editShowroomBooking(ShowroomBookingEditBO showroomBookingEditBO);

    /**
     * 取消展厅预约
     * @param showroomBookingCancelBO
     */
    void cancelShowroomBooking(ShowroomBookingCancelBO showroomBookingCancelBO);

    /**
     * 预约未到访
     * @param showroomBookingNoVisitBO
     */
    void noVisit(ShowroomBookingNoVisitBO showroomBookingNoVisitBO);

    /**
     * 到访
     * @param showroomBookingVisitBO
     */
    void visit(ShowroomBookingVisitBO showroomBookingVisitBO);

    /**
     * 数据分析
     * @param showroomBookingAnalysisBO
     * @return
     */
    ShowroomBookingAnalysisVO analysis(ShowroomBookingAnalysisBO showroomBookingAnalysisBO);

    /**
     * 导出分析数据
     * @param showroomBookingAnalysisBO
     * @param response
     */
    void exportAnalysisExcel(ShowroomBookingAnalysisBO showroomBookingAnalysisBO, HttpServletResponse response);

    /**
     * 获取默认讲解员id
     * @return
     */
    Integer getDefaultGuide();
}
