package com.ust.shbay.manager.biz.project;

import com.github.pagehelper.PageInfo;
import com.ust.shbay.manager.biz.project.bo.*;
import com.ust.shbay.manager.biz.project.vo.ProjectVO;
import com.ust.shbay.manager.entity.Project;
import com.ust.shbay.manager.entity.vo.ProjectDictVO;
import com.ust.shbay.manager.entity.vo.SearchArchivingVO;
import com.ust.shbay.manager.entity.vo.StageNodeDictVO;
import com.ust.shbay.manager.entity.vo.StageNodeVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ProjectBusiness {

    /**
     * 通过条件查询项目
     * @param projectQueryBO
     * @return
     */
    PageInfo<ProjectVO> getAllList(ProjectQueryBO projectQueryBO);

    /**
     * 新增项目
     * @param projectAddBO
     */
    void addProject(ProjectAddBO projectAddBO);

    /**
     * 编辑项目
     * @param projectEditBO
     */
    void editProject(ProjectEditBO projectEditBO);

    /**
     * 删除项目
     * @param projectDelBO
     */
    void delProject(ProjectDelBO projectDelBO);

    /**
     * 开放项目
     * @param projectDelBO
     */
    void openProject(ProjectDelBO projectDelBO);

    /**
     * 查询项目阶段节点字典
     * @param projectType
     * @return
     */
    List<StageNodeDictVO> getStageNodeDictList(Integer projectType);

    /**
     * 查询项目阶段字典
     * @param projectType
     * @return
     */
    List<ProjectDictVO> getStageDictList(Integer projectType);

    /**
     * 查询项目节点字典
     * @param projectType
     * @return
     */
    List<ProjectDictVO> getNodeDictList(Integer projectType);

    /**
     * 通过项目id查询项目的流程、节点信息
     * @param projectId
     * @return
     */
    List<StageNodeVO> getStageNodeList(Integer projectId);

    /**
     * 查询当前阶段最后一个可自定义节点
     * @param stageId
     * @param projectId
     * @return
     */
    StageNodeVO getLastCustomNode(Integer stageId,Integer projectId);

    /**
     * 添加自定义节点记录信息
     * @param stageNodeAddBO
     */
    void addStageNodeInfo(StageNodeAddBO stageNodeAddBO);

    /**
     * 编辑自定义节点记录信息
     * @param stageNodeEditBO
     */
    void editStageNodeInfo(StageNodeEditBO stageNodeEditBO);

    /**
     * 删除自定义节点记录信息
     * @param stageNodeDelBO
     */
    void delStageNodeInfo(StageNodeDelBO stageNodeDelBO);

    /**
     * 添加资料
     * @param archivingAddBO
     */
    void addArchiving(ArchivingAddBO archivingAddBO);

    /**
     * 查询资料
     * @param archivingSearchBO
     * @return
     */
    PageInfo<SearchArchivingVO>  searchArchiving(ArchivingSearchBO archivingSearchBO);

    /**
     * 编辑资料
     * @param archivingEditBO
     */
    void editArchiving(ArchivingEditBO archivingEditBO);

    /**
     * 删除资料
     * @param archivingDelBO
     */
    void delArchiving(ArchivingDelBO archivingDelBO);

    /**
     * 导出项目数据
     * @param projectQueryBO
     * @param response
     */
    void exportProjectExcel(ProjectQueryBO projectQueryBO, HttpServletResponse response);

    /**
     * 校验项目是否存在
     * @param projectId
     */
    Project checkProject(Integer projectId);
}
