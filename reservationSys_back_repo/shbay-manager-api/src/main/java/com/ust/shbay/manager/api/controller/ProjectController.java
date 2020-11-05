package com.ust.shbay.manager.api.controller;

import com.github.pagehelper.PageInfo;
import com.ust.shbay.common.dto.ResponseEntity;
import com.ust.shbay.manager.api.controller.dto.project.*;
import com.ust.shbay.manager.biz.project.ProjectBusiness;
import com.ust.shbay.manager.biz.project.bo.*;
import com.ust.shbay.manager.biz.project.vo.ProjectVO;
import com.ust.shbay.manager.entity.vo.ProjectDictVO;
import com.ust.shbay.manager.entity.vo.SearchArchivingVO;
import com.ust.shbay.manager.entity.vo.StageNodeDictVO;
import com.ust.shbay.manager.entity.vo.StageNodeVO;
import com.ust.shbay.service.base.BaseController;
import com.ust.shbay.service.base.BaseUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "项目管理")
@RestController
@RequestMapping("api/project/")
public class ProjectController extends BaseController {

    @Autowired
    private ProjectBusiness projectBusiness;

    @ApiOperation(value = "查询项目列表")
    @PostMapping("/getAllList")
    public ResponseEntity<PageInfo> getAllList(@RequestBody ProjectQuery projectQuery) {
        ProjectQueryBO projectQueryBO = new ProjectQueryBO();
        BeanUtils.copyProperties(projectQuery, projectQueryBO);
        setBaseAccount(projectQueryBO);
        PageInfo<ProjectVO> pageInfo = projectBusiness.getAllList(projectQueryBO);
        return ResponseEntity.buildSuccEntity(pageInfo);
    }

    @ApiOperation(value = "新增项目")
    @PostMapping("add/project")
    public ResponseEntity addProject(@Valid @RequestBody ProjectAdd projectAdd, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ProjectAddBO projectAddBO = new ProjectAddBO();
        BeanUtils.copyProperties(projectAdd, projectAddBO);
        setBaseAccount(projectAddBO);
        projectBusiness.addProject(projectAddBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "编辑项目基本信息")
    @PostMapping("edit/project")
    public ResponseEntity editProject(@Valid @RequestBody ProjectEdit projectEdit, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ProjectEditBO projectEditBO = new ProjectEditBO();
        BeanUtils.copyProperties(projectEdit, projectEditBO);
        setBaseAccount(projectEditBO);
        projectBusiness.editProject(projectEditBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "关闭项目")
    @PostMapping("del/project")
    public ResponseEntity delProject(@RequestBody ProjectDel projectDel) {
        ProjectDelBO projectDelBO = new ProjectDelBO();
        BeanUtils.copyProperties(projectDel, projectDelBO);
        setBaseAccount(projectDelBO);
        projectBusiness.delProject(projectDelBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "开放项目")
    @PostMapping("open/project")
    public ResponseEntity openProject(@RequestBody ProjectDel projectDel) {
        ProjectDelBO projectDelBO = new ProjectDelBO();
        BeanUtils.copyProperties(projectDel, projectDelBO);
        setBaseAccount(projectDelBO);
        projectBusiness.openProject(projectDelBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "查询项目全部阶段及节点记录信息")
    @PostMapping("/getStageNodeList")
    public ResponseEntity getStageNodeList(@RequestBody StageNodeQuery stageNodeQuery) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        List<StageNodeVO> list = projectBusiness.getStageNodeList(stageNodeQuery.getProjectId());
        return ResponseEntity.buildSuccEntity(list);
    }

    @ApiOperation(value = "查询当前阶段最后一个可自定义节点")
    @PostMapping("/getLastCustomNode")
    public ResponseEntity getLastCustomNode(@RequestBody StageQuery stageQuery) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        StageNodeVO stageNodeVO = projectBusiness.getLastCustomNode(stageQuery.getStageId(), stageQuery.getProjectId());
        return ResponseEntity.buildSuccEntity(stageNodeVO);
    }

    @ApiOperation(value = "添加阶段及节点记录信息")
    @PostMapping("/addStageNodeInfo")
    public ResponseEntity addStageNodeInfo(@Valid @RequestBody StageNodeAdd stageNodeAdd, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        StageNodeAddBO stageNodeAddBO = new StageNodeAddBO();
        BeanUtils.copyProperties(stageNodeAdd, stageNodeAddBO);
        setBaseAccount(stageNodeAddBO);
        projectBusiness.addStageNodeInfo(stageNodeAddBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "编辑自定义节点记录信息")
    @PostMapping("/editStageNodeInfo")
    public ResponseEntity editStageNodeInfo(@Valid @RequestBody StageNodeEdit stageNodeEdit, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        StageNodeEditBO stageNodeEditBO = new StageNodeEditBO();
        BeanUtils.copyProperties(stageNodeEdit, stageNodeEditBO);
        setBaseAccount(stageNodeEditBO);
        projectBusiness.editStageNodeInfo(stageNodeEditBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "删除自定义节点记录信息")
    @PostMapping("/delStageNodeInfo")
    public ResponseEntity delStageNodeInfo(@RequestBody StageNodeDel stageNodeDel) {
        StageNodeDelBO stageNodeDelBO = new StageNodeDelBO();
        BeanUtils.copyProperties(stageNodeDel, stageNodeDelBO);
        setBaseAccount(stageNodeDelBO);
        projectBusiness.delStageNodeInfo(stageNodeDelBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "新增资料")
    @PostMapping("add/archiving")
    public ResponseEntity addArchiving(@Valid @RequestBody ArchivingAdd archivingAdd, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ArchivingAddBO archivingAddBO = new ArchivingAddBO();
        BeanUtils.copyProperties(archivingAdd, archivingAddBO);
        setBaseAccount(archivingAddBO);
        projectBusiness.addArchiving(archivingAddBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "查询资料")
    @PostMapping("search/archiving")
    public ResponseEntity searchArchiving(@RequestBody ArchivingSearch archivingSearch) {
        ArchivingSearchBO archivingSearchBO = new ArchivingSearchBO();
        BeanUtils.copyProperties(archivingSearch, archivingSearchBO);
        setBaseAccount(archivingSearchBO);
        PageInfo<SearchArchivingVO> searchArchivingVOPageInfo=projectBusiness.searchArchiving(archivingSearchBO);
        return ResponseEntity.buildSuccEntity(searchArchivingVOPageInfo);
    }

    @ApiOperation(value = "编辑资料")
    @PostMapping("edit/archiving")
    public ResponseEntity editArchiving(@Valid @RequestBody ArchivingEdit archivingEdit, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ArchivingEditBO archivingEditBO = new ArchivingEditBO();
        BeanUtils.copyProperties(archivingEdit, archivingEditBO);
        setBaseAccount(archivingEditBO);
        projectBusiness.editArchiving(archivingEditBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "删除资料")
    @PostMapping("del/archiving")
    public ResponseEntity delArchiving(@RequestBody ArchivingDel archivingDel) {
        ArchivingDelBO archivingDelBO = new ArchivingDelBO();
        BeanUtils.copyProperties(archivingDel, archivingDelBO);
        setBaseAccount(archivingDelBO);
        projectBusiness.delArchiving(archivingDelBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "查询项目阶段节点字典")
    @PostMapping("/getStageNodeDictList")
    public ResponseEntity getStageNodeDictList(@RequestBody StageNodeDictQuery stageNodeDictQuery) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        List<StageNodeDictVO> list = projectBusiness.getStageNodeDictList(stageNodeDictQuery.getProjectType());
        return ResponseEntity.buildSuccEntity(list);
    }

    @ApiOperation(value = "查询项目阶段字典")
    @PostMapping("/getStageDictList")
    public ResponseEntity getStageDictList(@RequestBody StageNodeDictQuery stageNodeDictQuery) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        List<ProjectDictVO> list = projectBusiness.getStageDictList(stageNodeDictQuery.getProjectType());
        return ResponseEntity.buildSuccEntity(list);
    }

    @ApiOperation(value = "查询项目节点字典")
    @PostMapping("/getNodeDictList")
    public ResponseEntity getNodeDictList(@RequestBody StageNodeDictQuery stageNodeDictQuery) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        List<ProjectDictVO> list = projectBusiness.getNodeDictList(stageNodeDictQuery.getProjectType());
        return ResponseEntity.buildSuccEntity(list);
    }

    @ApiOperation(value = "导出项目数据")
    @PostMapping(value = "exportProjectExcel" , produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportProjectExcel(@RequestBody ProjectQuery projectQuery, HttpServletResponse response){
        ProjectQueryBO projectQueryBO = new ProjectQueryBO();
        BeanUtils.copyProperties(projectQuery,projectQueryBO);
        setBaseAccount(projectQueryBO);
        projectBusiness.exportProjectExcel(projectQueryBO,response);
    }

}