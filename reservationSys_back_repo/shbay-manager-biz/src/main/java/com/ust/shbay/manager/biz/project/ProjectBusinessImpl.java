package com.ust.shbay.manager.biz.project;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.exception.SException;
import com.ust.shbay.common.utils.DateUtil;
import com.ust.shbay.common.utils.ExcelUtil;
import com.ust.shbay.manager.biz.contract.ContractBusiness;
import com.ust.shbay.manager.biz.file.RelationBusiness;
import com.ust.shbay.manager.biz.project.bo.*;
import com.ust.shbay.manager.biz.project.vo.ProjectVO;
import com.ust.shbay.manager.entity.vo.*;
import com.ust.shbay.manager.dao.*;
import com.ust.shbay.manager.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class ProjectBusinessImpl implements ProjectBusiness {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private NodeDictMapper nodeDictMapper;

    @Autowired
    private StageDictMapper stageDictMapper;

    @Autowired
    private StageNodeInfoMapper stageNodeInfoMapper;

    @Autowired
    private ProjectMaterialMapper projectMaterialMapper;

    @Autowired
    private RelationBusiness relationBusiness;

    @Autowired
    private ContractBusiness contractBusiness;

    public Project checkProject(Integer projectId) {
        Project project = projectMapper.selectById(projectId);
        if (project == null) {
            throw new SException("项目不存在！");
        } else if (project != null && project.getDelFlag() == Constant.Status.DEL) {
            throw new SException("项目已被关闭，相关操作无法继续进行！");
        } else {
            return project;
        }
    }

    /**
     * 通过条件查询项目
     *
     * @param projectQueryBO
     * @return
     */
    @Override
    public PageInfo<ProjectVO> getAllList(ProjectQueryBO projectQueryBO) {
        ProjectExample example = new ProjectExample();
        ProjectExample.Criteria criteria = example.createCriteria();

        //项目类型
        if (projectQueryBO.getProjectType() != null) {
            criteria.andProjectTypeEqualTo(projectQueryBO.getProjectType());
        }
        //项目阶段字典id
        if (projectQueryBO.getStageIds() != null && projectQueryBO.getStageIds().size() > 0) {
            criteria.andStageIdIn(projectQueryBO.getStageIds());
        }
        //项目阶段节点字典id
        if (projectQueryBO.getStageNodeIds() != null && projectQueryBO.getStageNodeIds().size() > 0) {
            criteria.andStageNodeIdIn(projectQueryBO.getStageNodeIds());
        }
        //项目编号
        if (projectQueryBO.getNumber() != null && !"".equals(projectQueryBO.getNumber())) {
            criteria.andNumberLike("%" + projectQueryBO.getNumber() + "%");
        }
        //项目名称
        if (projectQueryBO.getName() != null && !"".equals(projectQueryBO.getName())) {
            criteria.andNameLike("%" + projectQueryBO.getName() + "%");
        }
        //项目负责人
        if (projectQueryBO.getPerson() != null && !"".equals(projectQueryBO.getPerson())) {
            criteria.andPersonLike("%" + projectQueryBO.getPerson() + "%");
        }
        //删除标记
        if (projectQueryBO.getDelFlag() != null && projectQueryBO.getDelFlag().size() > 0) {
            criteria.andDelFlagIn(projectQueryBO.getDelFlag());
        }
//        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);

        example.setOrderByClause("update_time desc");

        PageHelper.startPage(projectQueryBO.getPageNumber(), projectQueryBO.getPageSize());
        List<Project> projectList = projectMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(projectList);
        List<ProjectVO> projectVOList = new ArrayList<>();
        for (Project project : projectList) {
            ProjectVO projectVO = new ProjectVO();
            BeanUtils.copyProperties(project, projectVO);
            //计算项目周期
            if (project.getEndTime() != null && project.getBeginTime() != null) {
                String cycle = DateUtil.getDateDiff(project.getEndTime(), project.getBeginTime());
                projectVO.setCycle(cycle);
            }
            //获取当前项目阶段名
            if (project.getStageId() != null) {
                StageDict stageDict = stageDictMapper.selectById(project.getStageId());
                projectVO.setStageName(stageDict.getStageName());
                //当前阶段为可自定义节点的阶段，如工程项目的施工阶段
                if (stageDict.getStageType() == Constant.STAGE_TYPE.STAGE_IS_CUSTOM) {
                    //获取最后一个自定义节点
                    StageNodeInfoExample example1 = new StageNodeInfoExample();
                    StageNodeInfoExample.Criteria criteria1 = example1.createCriteria();
                    criteria1.andProjectIdEqualTo(project.getId());
                    criteria1.andStageIdEqualTo(project.getStageId());
                    criteria1.andTypeEqualTo(Constant.STAGE_OR_NODE.TYPE_IS_CUSTOM_NODE);
                    criteria1.andDelFlagEqualTo(Constant.Status.NORMAL);
                    example1.setOrderByClause("id asc");
                    //查询项目阶段和节点的记录信息
                    List<StageNodeInfo> stageAndNodeInfoList = stageNodeInfoMapper.selectByExample(example1);
                    if (stageAndNodeInfoList.size() > 0) {
                        projectVO.setStageNodeName(stageAndNodeInfoList.get(0).getNodeName());
                    }
                }
            }
            //获取当前项目阶段节点名
            if (project.getStageNodeId() != null) {
                String stageNodeName = nodeDictMapper.selectNameById(project.getStageNodeId());
                projectVO.setStageNodeName(stageNodeName);
            }

            //项目总投资额
            if(project.getTotalInvestment() != null){
                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                numberFormat.setMaximumFractionDigits(2); // 最大小数位数
                numberFormat.setMinimumFractionDigits(2); // 最小小数位数
                projectVO.setTotalInvestmentStr(numberFormat.format(project.getTotalInvestment()));
            }
            Map<String,String> map = contractBusiness.getMoneyByProjectId(project.getId());
            //查询项目累计已签订合同金额
            projectVO.setTotalContractMoneyStr(map.get("totalContractMoneyStr"));
            //查询项目累计已结算金额
            projectVO.setTotalPaidMoneyStr(map.get("totalPaidMoneyStr"));

            projectVOList.add(projectVO);
        }
        pageInfo.setList(projectVOList);
        return pageInfo;

    }

    /**
     * 新增项目
     *
     * @param projectAddBO
     */
    public void addProject(ProjectAddBO projectAddBO) {
        //添加项目
        Project project = new Project();
        BeanUtils.copyProperties(projectAddBO, project);
        project.setDelFlag(Constant.Status.NORMAL);
        project.setCreateUser(projectAddBO.getAccount());
        project.setUpdateUser(projectAddBO.getAccount());
        //获取项目初始阶段
        List<StageDict> stageDictList = stageDictMapper.selectByProjectType(projectAddBO.getProjectType());
        if (stageDictList != null && stageDictList.size() > 0) {
            project.setStageId(stageDictList.get(0).getId());
            //获取项目初始阶段节点
            List<NodeDict> nodeDictList = nodeDictMapper.selectByStageDictId(stageDictList.get(0).getId());
            if (nodeDictList != null && nodeDictList.size() > 0) {
                project.setStageNodeId(nodeDictList.get(0).getId());
            }
        }
        projectMapper.insertSelective(project);
    }

    /**
     * 编辑项目
     *
     * @param projectEditBO
     */
    public void editProject(ProjectEditBO projectEditBO) {
        //查询项目是否存在
        checkProject(projectEditBO.getId());

        Project entity = new Project();
        BeanUtils.copyProperties(projectEditBO, entity);
        entity.setUpdateUser(projectEditBO.getAccount());
        projectMapper.updateSelective(entity);
    }

    /**
     * 删除项目
     *
     * @param projectDelBO
     */
//    @Transactional
    @Override
    public void delProject(ProjectDelBO projectDelBO) {
        //查询项目是否存在
        Project project = checkProject(projectDelBO.getId());

        project.setDelFlag(Constant.Status.DEL);
        project.setUpdateUser(projectDelBO.getAccount());
        projectMapper.updateSelective(project);

//        //删除项目关联的阶段和节点信息
//        delProjectStageNodeInfo(projectDelBO);
//
//        //删除项目关联的资料
//        delProjectArchiving(projectDelBO);
//
//        //删除项目关联的合同及合同付款节点
//        contractBusiness.delProjectContract(projectDelBO.getId(), projectDelBO.getAccount());
    }

    /**
     * 开放项目
     *
     * @param projectDelBO
     */
    @Override
    public void openProject(ProjectDelBO projectDelBO) {
        Project project = projectMapper.selectById(projectDelBO.getId());
        if (project != null) {
            project.setDelFlag(Constant.Status.NORMAL);
            project.setUpdateUser(projectDelBO.getAccount());
            projectMapper.updateSelective(project);
        } else {
            throw new SException("项目不存在！");
        }
    }

//    //删除项目关联的阶段和节点信息
//    private void delProjectStageNodeInfo(ProjectDelBO projectDelBO) {
//        StageNodeInfoExample example = new StageNodeInfoExample();
//        StageNodeInfoExample.Criteria criteria = example.createCriteria();
//        criteria.andProjectIdEqualTo(projectDelBO.getId());
//        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
//
//        StageNodeInfo record = new StageNodeInfo();
//        record.setDelFlag(Constant.Status.DEL);
//        record.setUpdateUser(projectDelBO.getAccount());
//        stageNodeInfoMapper.updateByExampleSelective(record, example);
//    }
//
//    //删除项目关联的资料
//    private void delProjectArchiving(ProjectDelBO projectDelBO) {
//        ProjectMaterialExample example = new ProjectMaterialExample();
//        ProjectMaterialExample.Criteria criteria = example.createCriteria();
//        criteria.andProjectIdEqualTo(projectDelBO.getId());
//        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
//
//        ProjectMaterial record = new ProjectMaterial();
//        record.setDelFlag(Constant.Status.DEL);
//        record.setUpdateUser(projectDelBO.getAccount());
//        projectMaterialMapper.updateByExampleSelective(record, example);
//    }


    /**
     * 查询项目阶段节点字典
     *
     * @param projectType
     * @return
     */
    @Override
    public List<StageNodeDictVO> getStageNodeDictList(Integer projectType) {
        List<StageNodeDictVO> list = new ArrayList<>();
        List<StageDict> stageDictList = stageDictMapper.selectByProjectType(projectType);
        if (stageDictList != null && stageDictList.size() > 0) {
            for (StageDict stageDict : stageDictList) {
                StageNodeDictVO stageNodeDictVO = new StageNodeDictVO();
                stageNodeDictVO.setId(stageDict.getId());
                stageNodeDictVO.setNodeName(stageDict.getStageName());
                stageNodeDictVO.setChildList(nodeDictMapper.selectByStageDictId(stageDict.getId()));
                list.add(stageNodeDictVO);
            }
        }
        return list;
    }

    /**
     * 查询项目阶段节点字典
     *
     * @param projectType
     * @return
     */
    @Override
    public List<ProjectDictVO> getStageDictList(Integer projectType) {
        List<ProjectDictVO> list = new ArrayList<>();
        List<StageDict> stageDictList = stageDictMapper.selectByProjectType(projectType);
        if (stageDictList != null && stageDictList.size() > 0) {
            for (StageDict stageDict : stageDictList) {
                ProjectDictVO projectDictVO = new ProjectDictVO();
                projectDictVO.setText(stageDict.getStageName());
                projectDictVO.setValue(stageDict.getId());
                list.add(projectDictVO);
            }
        }
        return list;
    }

    /**
     * 查询项目阶段节点字典
     *
     * @param projectType
     * @return
     */
    @Override
    public List<ProjectDictVO> getNodeDictList(Integer projectType) {
        List<ProjectDictVO> list = nodeDictMapper.selectByProjectType(projectType);
        return list;
    }

    /**
     * 通过项目id查询项目的流程、节点信息
     *
     * @param projectId
     * @return
     */
    @Override
    public List<StageNodeVO> getStageNodeList(Integer projectId) {
        List<StageNodeVO> list = new ArrayList<>();
        //通过项目id查询项目类型
        Project project = projectMapper.selectById(projectId);
        if (project != null) {
            if (project.getProjectType().equals(Constant.PROJECT_TYPE.PROJECT)) {
                //工程项目
                //获取全部项目阶段
                List<StageDict> stageDictList = stageDictMapper.selectByProjectType(project.getProjectType());
                //有记录的项目阶段
                StageNodeInfoExample example = new StageNodeInfoExample();
                StageNodeInfoExample.Criteria criteria = example.createCriteria();
                criteria.andProjectIdEqualTo(projectId);
                criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
                example.setOrderByClause("id asc");
                //查询项目阶段和节点的记录信息
                List<StageNodeInfo> stageAndNodeInfoList = stageNodeInfoMapper.selectByExample(example);
                List<Integer> stageIds = stageAndNodeInfoList.stream().map(StageNodeInfo::getStageId).collect(Collectors.toList());
                for (StageDict stageDict : stageDictList) {
                    StageNodeVO stageNodeVO = new StageNodeVO();
                    stageNodeVO.setDictName(stageDict.getStageName());
                    stageNodeVO.setStageType(stageDict.getStageType());
                    //判断是否为最后一个阶段
                    if (stageDict.getId() == stageDictList.get(stageDictList.size() - 1).getId()) {
                        stageNodeVO.setIsLast(true);
                    } else {
                        stageNodeVO.setIsLast(false);
                    }
                    //判断是否有记录
                    if (stageIds.contains(stageDict.getId())) {
                        //有记录
                        stageNodeVO.setIsFinished(true);
                        for (StageNodeInfo stageNodeInfo : stageAndNodeInfoList) {
                            if (stageNodeInfo.getStageId() == stageDict.getId() &&
                                    stageNodeInfo.getType() == Constant.STAGE_OR_NODE.TYPE_STAGE) {
                                stageNodeVO.setId(stageNodeInfo.getId());
                                stageNodeVO.setPerson(stageNodeInfo.getPerson());
                                stageNodeVO.setFinishTime(stageNodeInfo.getFinishTime());
                                break;
                            }

                        }
                        //判断阶段是否可自定义节点
                        if (stageDict.getStageType() == Constant.STAGE_TYPE.STAGE_IS_CUSTOM) {
                            List<StageNodeVO> childList = new ArrayList<>();
                            for (StageNodeInfo stageNodeInfo : stageAndNodeInfoList) {
                                if (stageNodeInfo.getStageId() == stageDict.getId()
                                        && stageNodeInfo.getType() == Constant.STAGE_OR_NODE.TYPE_IS_CUSTOM_NODE) {
                                    StageNodeVO child = new StageNodeVO();
                                    child.setId(stageNodeInfo.getId());
                                    child.setDictName(stageNodeInfo.getNodeName());
                                    child.setPerson(stageNodeInfo.getPerson());
                                    child.setFinishTime(stageNodeInfo.getFinishTime());
                                    child.setIsFinished(true);
                                    child.setIsLast(false);
                                    childList.add(child);
                                }
                            }
                            stageNodeVO.setChildList(childList);
                        }

                    } else {
                        //没有记录
                        stageNodeVO.setIsFinished(false);
                    }
                    //添加阶段资料
                    List<TRelationVO> relationVOList = searchArchiving(projectId, stageDict.getId(), null);
                    stageNodeVO.setRelationVOList(relationVOList);
                    list.add(stageNodeVO);
                }
            } else {
                //其它项目
                //获取全部项目阶段
                List<StageDict> stageDictList = stageDictMapper.selectByProjectType(project.getProjectType());
                for (StageDict stageDict : stageDictList) {
                    StageNodeVO stageNodeVO = new StageNodeVO();
                    stageNodeVO.setDictName(stageDict.getStageName());
                    stageNodeVO.setStageType(stageDict.getStageType());
                    stageNodeVO.setIsLast(false);
                    //获取本阶段的资料
                    List<TRelationVO> relationVOList = searchArchiving(projectId, stageDict.getId(), null);
                    stageNodeVO.setRelationVOList(relationVOList);
                    //获取全部项目阶段节点
                    List<NodeDict> nodeDictList = nodeDictMapper.selectByStageDictId(stageDict.getId());
                    //获取本阶段的节点记录
                    StageNodeInfoExample example = new StageNodeInfoExample();
                    StageNodeInfoExample.Criteria criteria = example.createCriteria();
                    criteria.andProjectIdEqualTo(projectId);
                    criteria.andStageIdEqualTo(stageDict.getId());
                    criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
                    example.setOrderByClause("id asc");
                    List<StageNodeInfo> nodeInfoList = stageNodeInfoMapper.selectByExample(example);
                    //遍历全部节点
                    List<StageNodeVO> childList = new ArrayList<>();
                    for (int i = 0; i < nodeDictList.size(); i++) {
                        StageNodeVO child = new StageNodeVO();
                        //判断节点是否为项目最后一个节点
                        if (stageDict.getId() == stageDictList.get(stageDictList.size() - 1).getId() &&
                                i == nodeDictList.size() - 1) {
                            child.setIsLast(true);
                        } else {
                            child.setIsLast(false);
                        }
                        //阶段未开始
                        if (nodeInfoList.size() == 0) {
                            stageNodeVO.setIsFinished(false);
                            child.setDictName(nodeDictList.get(i).getNodeName());
                            child.setIsFinished(false);
                        }
                        //阶段已结束
                        else if (nodeInfoList.size() == nodeDictList.size()) {
                            stageNodeVO.setIsFinished(true);
                            child.setId(nodeInfoList.get(i).getId());
                            child.setDictName(nodeDictList.get(i).getNodeName());
                            child.setPerson(nodeInfoList.get(i).getPerson());
                            child.setFinishTime(nodeInfoList.get(i).getFinishTime());
                            child.setIsFinished(true);
                        }
                        //阶段进行中
                        else if (nodeInfoList.size() < nodeDictList.size()) {
                            stageNodeVO.setIsFinished(true);
                            if (i < nodeInfoList.size()) {
                                //已结束的节点
                                child.setId(nodeInfoList.get(i).getId());
                                child.setDictName(nodeDictList.get(i).getNodeName());
                                child.setPerson(nodeInfoList.get(i).getPerson());
                                child.setFinishTime(nodeInfoList.get(i).getFinishTime());
                                child.setIsFinished(true);
                            } else {
                                //未开始的节点
                                child.setDictName(nodeDictList.get(i).getNodeName());
                                child.setIsFinished(false);
                            }
                        }
                        //获取本阶段节点的资料
                        List<TRelationVO> childRelationVOList = searchArchiving(projectId, stageDict.getId(),
                                nodeDictList.get(i).getId());
                        child.setRelationVOList(childRelationVOList);
                        childList.add(child);
                        stageNodeVO.setChildList(childList);
                    }
                    list.add(stageNodeVO);
                }
            }
        }
        return list;
    }

    //查询某项目阶段节点的资料
    private List<TRelationVO> searchArchiving(Integer projectId, Integer stageId, Integer stageNodeId) {
        ProjectMaterialExample projectMaterialExample = new ProjectMaterialExample();
        ProjectMaterialExample.Criteria criteria = projectMaterialExample.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        criteria.andStageIdEqualTo(stageId);

        if (stageNodeId != null) {
            criteria.andStageNodeIdEqualTo(stageNodeId);
        } else {
            criteria.andStageNodeIdIsNull();
        }

        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        projectMaterialExample.setOrderByClause("update_time desc");
        List<ProjectMaterial> projectMaterialList = projectMaterialMapper.selectByExample(projectMaterialExample);

        List<TRelationVO> relationVoList = new ArrayList();
        if (projectMaterialList != null && projectMaterialList.size() > 0) {
            for (int i = 0; i < projectMaterialList.size(); i++) {
                List<TRelationVO> tRelationList = relationBusiness.searchRelationList(Constant.RELATION_TYPE.PROJECT_MATERIALS, projectMaterialList.get(i).getId());
                if (tRelationList != null && tRelationList.size() > 0) {
                    relationVoList.removeAll(tRelationList);
                    relationVoList.addAll(tRelationList);
                }
            }
        }
        return relationVoList;
    }


    /**
     * 查询当前阶段最后一个可自定义节点
     *
     * @param stageId
     * @param projectId
     * @return
     */
    @Override
    public StageNodeVO getLastCustomNode(Integer stageId, Integer projectId) {
        StageNodeVO stageNodeVO = new StageNodeVO();
        //查询本阶段字典
        StageDict stageDict = stageDictMapper.selectById(stageId);
        stageNodeVO.setStageType(stageDict.getStageType());
        //本阶段节点自定义
        if (stageDict != null && stageDict.getStageType().equals(Constant.STAGE_TYPE.STAGE_IS_CUSTOM)) {
            //查询节点信息
            StageNodeInfoExample example = new StageNodeInfoExample();
            StageNodeInfoExample.Criteria criteria = example.createCriteria();
            criteria.andProjectIdEqualTo(projectId);
            criteria.andStageIdEqualTo(stageId);
            criteria.andTypeEqualTo(Constant.STAGE_OR_NODE.TYPE_IS_CUSTOM_NODE);
            criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
            example.setOrderByClause("id desc");
            List<StageNodeInfo> nodeInfoList = stageNodeInfoMapper.selectByExample(example);
            if (nodeInfoList != null && nodeInfoList.size() > 0) {
                stageNodeVO.setId(nodeInfoList.get(0).getId());
                stageNodeVO.setDictName(nodeInfoList.get(0).getNodeName());
                stageNodeVO.setPerson(nodeInfoList.get(0).getPerson());
                stageNodeVO.setFinishTime(nodeInfoList.get(0).getFinishTime());
            }
        }
        return stageNodeVO;
    }


    /**
     * 添加自定义节点记录信息
     *
     * @param stageNodeAddBO
     */
    @Transactional
    @Override
    public void addStageNodeInfo(StageNodeAddBO stageNodeAddBO) {
        //校验项目
        checkProject(stageNodeAddBO.getProjectId());

        StageDict stageDict = stageDictMapper.selectById(stageNodeAddBO.getStageId());
        if (stageDict.getStageType().equals(Constant.STAGE_TYPE.STAGE_IS_CUSTOM)) {
            //该阶段节点为自定义节点
            if (stageNodeAddBO.getNodeName() != null) {
                //本次操作为添加自定义节点
                //添加自定义节点记录
                stageNodeAddBO.setType(Constant.STAGE_OR_NODE.TYPE_IS_CUSTOM_NODE);
                saveInfo(stageNodeAddBO);
            } else {
                //本次操作为向下阶段流转
                //添加阶段记录
                stageNodeAddBO.setType(Constant.STAGE_OR_NODE.TYPE_STAGE);
                saveInfo(stageNodeAddBO);

                //获取下一个阶段、节点
                Map<String, Integer> map = getNextStageOrNode(stageNodeAddBO.getStageId(), stageNodeAddBO.getStageNodeId());
                editProjectStageNode(map, stageNodeAddBO.getAccount(), stageNodeAddBO.getProjectId());
            }
        } else {
            //该阶段节点为非自定义节点
            if (stageNodeAddBO.getStageNodeId() != null) {
                //添加非自定义节点记录
                stageNodeAddBO.setType(Constant.STAGE_OR_NODE.TYPE_ISNOT_CUSTOM_NODE);
                saveInfo(stageNodeAddBO);

                //获取下一个阶段、节点
                Map<String, Integer> map = getNextStageOrNode(stageNodeAddBO.getStageId(), stageNodeAddBO.getStageNodeId());
                editProjectStageNode(map, stageNodeAddBO.getAccount(), stageNodeAddBO.getProjectId());
            } else {
                //本次操作为向下阶段流转
                //添加阶段记录
                stageNodeAddBO.setType(Constant.STAGE_OR_NODE.TYPE_STAGE);
                saveInfo(stageNodeAddBO);

                //获取下一个阶段、节点
                Map<String, Integer> map = getNextStageOrNode(stageNodeAddBO.getStageId(), stageNodeAddBO.getStageNodeId());
                editProjectStageNode(map, stageNodeAddBO.getAccount(), stageNodeAddBO.getProjectId());
            }
        }

        //添加资料
        if (stageNodeAddBO.getRelationList() != null && stageNodeAddBO.getRelationList().size() > 0) {
            ArchivingAddBO archivingAddBO = new ArchivingAddBO();
            archivingAddBO.setProjectId(stageNodeAddBO.getProjectId());
            archivingAddBO.setStageId(stageNodeAddBO.getStageId());
            archivingAddBO.setStageNodeId(stageNodeAddBO.getStageNodeId());
            archivingAddBO.setUrl(stageNodeAddBO.getRelationList());
            archivingAddBO.setAccount(stageNodeAddBO.getAccount());
            addArchiving(archivingAddBO);
        }

    }

    //添加阶段/节点记录信息
    private void saveInfo(StageNodeAddBO stageNodeAddBO) {
        StageNodeInfoExample example = new StageNodeInfoExample();
        StageNodeInfoExample.Criteria criteria = example.createCriteria();
        criteria.andProjectIdEqualTo(stageNodeAddBO.getProjectId());
        criteria.andTypeEqualTo(stageNodeAddBO.getType());
        criteria.andStageIdEqualTo(stageNodeAddBO.getStageId());
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<StageNodeInfo> stageNodeInfoList = new ArrayList<>();
        if (stageNodeAddBO.getType().equals(Constant.STAGE_OR_NODE.TYPE_STAGE)) {
            stageNodeInfoList = stageNodeInfoMapper.selectByExample(example);
        } else if (stageNodeAddBO.getType().equals(Constant.STAGE_OR_NODE.TYPE_ISNOT_CUSTOM_NODE)) {
            criteria.andStageNodeIdEqualTo(stageNodeAddBO.getStageNodeId());
            stageNodeInfoList = stageNodeInfoMapper.selectByExample(example);
        }
//        else if(stageNodeAddBO.getType().equals(Constant.STAGE_OR_NODE.TYPE_IS_CUSTOM_NODE)){
//            criteria.andNodeNameEqualTo(stageNodeAddBO.getNodeName());
//            stageInfoList = stageNodeInfoMapper.selectByExample(example);
//        }
        if (stageNodeInfoList != null && stageNodeInfoList.size() > 0) {
            //已有数据
            StageNodeInfo record = new StageNodeInfo();
            BeanUtils.copyProperties(stageNodeAddBO, record);
            record.setFinishTime(new Date());
            record.setUpdateUser(stageNodeAddBO.getAccount());
            record.setId(stageNodeInfoList.get(0).getId());
            stageNodeInfoMapper.updateSelective(record);
        } else {
            //添加数据
            StageNodeInfo record = new StageNodeInfo();
            BeanUtils.copyProperties(stageNodeAddBO, record);
            record.setDelFlag(Constant.Status.NORMAL);
            record.setCreateUser(stageNodeAddBO.getAccount());
            record.setUpdateUser(stageNodeAddBO.getAccount());
            record.setFinishTime(new Date());
            stageNodeInfoMapper.insertSelective(record);
        }
    }

    //获取本阶段下一个节点或下一个阶段、节点
    private Map<String, Integer> getNextStageOrNode(Integer stageId, Integer nodeId) {
        Map<String, Integer> map = new HashMap<>();
        Integer nextStageId = null, nextNodeId = null;
        if (nodeId != null) {
            //获取当前阶段
            //当前节点是否为最后一个节点，如果不是获取本阶段下一个节点
            List<Integer> nodeIdList = nodeDictMapper.selectIdsByStageDictId(stageId);
            if (nodeIdList.size() > 0) {
                //节点下标
                int i = nodeIdList.indexOf(nodeId);
                if (i >= 0 && i < nodeIdList.size() - 1) {
                    //本阶段下一个节点
                    nextNodeId = nodeIdList.get(i + 1);
                } else if (i == nodeIdList.size() - 1) {
                    /**
                     * 否则获取下一个阶段
                     * 判断本阶段是否为最后一个阶段，如果是最后一个阶段，不处理
                     * 如果不是，获取下一阶段id
                     * 判断下一个阶段是否为可自定义节点阶段，如果是不处理,
                     * 如果下一个阶段不是可自定义节点阶段，获取本阶段第一个节点
                     */
                    return getNextStageNode(stageId);
                }
            }
        } else {
            //获取下一个阶段,同上
            return getNextStageNode(stageId);
        }
        map.put("nextStageId", nextStageId);
        map.put("nextNodeId", nextNodeId);
        return map;
    }

    //获取下一个阶段、节点
    private Map<String, Integer> getNextStageNode(Integer stageId) {
        Map<String, Integer> map = new HashMap<>();
        Integer nextStageId = null, nextNodeId = null;
        StageDict stageDict = stageDictMapper.selectById(stageId);
        if (stageDict != null) {
            List<Integer> stageIds = stageDictMapper.selectIdsByProjectType(stageDict.getProjectType());
            //阶段下标
            int j = stageIds.indexOf(stageId);
            if (j >= 0 && j < stageIds.size() - 1) {
                //不是最后阶段，获取下一个阶段
                nextStageId = stageIds.get(j + 1);
                if (stageDictMapper.selectById(stageIds.get(j + 1)).getStageType().equals(Constant.STAGE_TYPE.STAGE_ISNOT_CUSTOM)) {
                    //获取本阶段第一个节点
                    List<NodeDict> nextNodeDictList = nodeDictMapper.selectByStageDictId(nextStageId);
                    if (nextNodeDictList != null && nextNodeDictList.size() > 0) {
                        nextNodeId = nextNodeDictList.get(0).getId();
                    }
                }
            }
        }
        map.put("nextStageId", nextStageId);
        map.put("nextNodeId", nextNodeId);
        return map;
    }

    //修改项目当前阶段、节点
    private void editProjectStageNode(Map<String, Integer> map, String account, Integer projectId) {
        Project entity = new Project();
        entity.setStageId(map.get("nextStageId"));
        entity.setStageNodeId(map.get("nextNodeId"));
        entity.setUpdateUser(account);
        entity.setId(projectId);
        projectMapper.updateSelective(entity);
    }

    /**
     * 编辑自定义节点记录信息
     *
     * @param stageNodeEditBO
     */
    @Override
    public void editStageNodeInfo(StageNodeEditBO stageNodeEditBO) {
        //校验项目
        checkProject(stageNodeEditBO.getProjectId());

        StageNodeInfo stageNodeInfo = stageNodeInfoMapper.selectById(stageNodeEditBO.getId());
        if (stageNodeInfo != null) {
            StageNodeInfo record = new StageNodeInfo();
            BeanUtils.copyProperties(stageNodeEditBO, record);
            record.setUpdateUser(stageNodeEditBO.getAccount());
            record.setFinishTime(new Date());
            stageNodeInfoMapper.updateSelective(record);
        } else {
            throw new SException("该节点不存在！");
        }

    }

    /**
     * 删除自定义节点记录信息
     *
     * @param stageNodeDelBO
     */
    @Override
    public void delStageNodeInfo(StageNodeDelBO stageNodeDelBO) {
        StageNodeInfo stageNodeInfo = stageNodeInfoMapper.selectById(stageNodeDelBO.getId());
        if (stageNodeInfo != null) {
            //校验项目
            checkProject(stageNodeInfo.getProjectId());

            StageNodeInfo record = new StageNodeInfo();
            BeanUtils.copyProperties(stageNodeDelBO, record);
            record.setUpdateUser(stageNodeDelBO.getAccount());
            record.setFinishTime(new Date());
            record.setDelFlag(Constant.Status.DEL);
            stageNodeInfoMapper.updateSelective(record);
        } else {
            throw new SException("该节点不存在！");
        }
    }

    /**
     * 添加资料
     *
     * @param archivingAddBO
     */
    @Transactional
    @Override
    public void addArchiving(ArchivingAddBO archivingAddBO) {
        //校验项目
        checkProject(archivingAddBO.getProjectId());

        ProjectMaterial projectMaterial = new ProjectMaterial();
        BeanUtils.copyProperties(archivingAddBO, projectMaterial);
        projectMaterial.setDelFlag(Constant.Status.NORMAL);
        projectMaterial.setCreateUser(archivingAddBO.getAccount());
        projectMaterial.setUpdateUser(archivingAddBO.getAccount());
        projectMaterialMapper.insertSelective(projectMaterial);
        if (archivingAddBO.getUrl() != null && archivingAddBO.getUrl().size() > 0) {
            for (TRelation relation : archivingAddBO.getUrl()) {
                TRelation tRelation = new TRelation();
                tRelation.setType(Constant.RELATION_TYPE.PROJECT_MATERIALS);
                tRelation.setFileUrl(relation.getFileUrl());
                tRelation.setFileName(relation.getFileName());
                tRelation.setCreateUser(archivingAddBO.getAccount());
                tRelation.setUpdateUser(archivingAddBO.getAccount());
                tRelation.setOriginalId(projectMaterial.getId());
                relationBusiness.insertRelation(tRelation);
            }
        }
    }

    /**
     * 查询资料
     *
     * @param archivingSearchBO
     */
    @Override
    public PageInfo<SearchArchivingVO> searchArchiving(ArchivingSearchBO archivingSearchBO) {
        ProjectMaterialExample projectMaterialExample = new ProjectMaterialExample();
        ProjectMaterialExample.Criteria criteria = projectMaterialExample.createCriteria();
        if (archivingSearchBO.getProjectType() != null) {
            List<Integer> projectIds = projectMapper.selectByType(archivingSearchBO.getProjectType());
            if (projectIds != null && projectIds.size() > 0) {
                criteria.andProjectIdIn(projectIds);
            } else {
                return null;
            }
        }
        if (archivingSearchBO.getProjectId() != null) {
            criteria.andProjectIdEqualTo(archivingSearchBO.getProjectId());
        }
        if (archivingSearchBO.getStageId() != null) {
            criteria.andStageIdEqualTo(archivingSearchBO.getStageId());
        }
        if (archivingSearchBO.getStageNodeId() != null) {
            criteria.andStageNodeIdEqualTo(archivingSearchBO.getStageNodeId());
        }
        if (archivingSearchBO.getCreateUser() != null && !"".equals(archivingSearchBO.getCreateUser())) {
            criteria.andCreateUserLike("%" + archivingSearchBO.getCreateUser() + "%");
        }
        if (archivingSearchBO.getUpdateUser() != null && !"".equals(archivingSearchBO.getUpdateUser())) {
            criteria.andUpdateUserLike("%" + archivingSearchBO.getUpdateUser() + "%");
        }
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        projectMaterialExample.setOrderByClause("update_time desc");
        PageHelper.startPage(archivingSearchBO.getPageNumber(), archivingSearchBO.getPageSize());
        List<ProjectMaterial> projectMaterialList = projectMaterialMapper.selectByExample(projectMaterialExample);
        PageInfo pageInfo = new PageInfo(projectMaterialList);
        List<SearchArchivingVO> searchArchivingVOList = new ArrayList();
        for (int i = 0; i < projectMaterialList.size(); i++) {
            SearchArchivingVO searchArchivingVO = new SearchArchivingVO();
            List<TRelationVO> tRelationList = relationBusiness.searchRelationList(Constant.RELATION_TYPE.PROJECT_MATERIALS, projectMaterialList.get(i).getId());
            searchArchivingVO.setUrl(tRelationList);
            searchArchivingVO.setId(projectMaterialList.get(i).getId());
            searchArchivingVO.setStageId(projectMaterialList.get(i).getStageId());
            searchArchivingVO.setStageNodeId(projectMaterialList.get(i).getStageNodeId());
            searchArchivingVO.setCreateTime(projectMaterialList.get(i).getCreateTime());
            searchArchivingVO.setCreateUser(projectMaterialList.get(i).getCreateUser());
            searchArchivingVO.setUpdateTime(projectMaterialList.get(i).getUpdateTime());
            searchArchivingVO.setUpdateUser(projectMaterialList.get(i).getUpdateUser());
            searchArchivingVO.setProjectId(projectMaterialList.get(i).getProjectId());
            if (searchArchivingVO.getStageId() != null) {
                searchArchivingVO.setStageName(stageDictMapper.selectNameById(searchArchivingVO.getStageId()));
            }
            if (searchArchivingVO.getStageNodeId() != null) {
                searchArchivingVO.setStageNodeName(nodeDictMapper.selectNameById(searchArchivingVO.getStageNodeId()));
            }
            if (searchArchivingVO.getProjectId() != null) {
                searchArchivingVO.setProjectName(projectMapper.selectById(searchArchivingVO.getProjectId()).getName());
            }
            searchArchivingVOList.add(searchArchivingVO);
        }
        pageInfo.setList(searchArchivingVOList);
        return pageInfo;
    }

    /**
     * 编辑资料
     *
     * @param archivingEditBO
     */
    @Transactional
    @Override
    public void editArchiving(ArchivingEditBO archivingEditBO) {
        //校验项目
        checkProject(archivingEditBO.getProjectId());

        ProjectMaterial projectMaterial = projectMaterialMapper.selectById(archivingEditBO.getId());
        if (projectMaterial != null) {
            //修改记录
            ProjectMaterial record = new ProjectMaterial();
            BeanUtils.copyProperties(archivingEditBO, record);
            record.setUpdateUser(archivingEditBO.getAccount());
            projectMaterialMapper.updateSelective(record);
            //修改关联url
            List<String> oldList = relationBusiness.searchRelation(Constant.RELATION_TYPE.PROJECT_MATERIALS, archivingEditBO.getId());
            relationBusiness.updateAttachment(oldList, archivingEditBO.getUrl(),
                    Constant.RELATION_TYPE.PROJECT_MATERIALS, archivingEditBO.getId(), archivingEditBO.getAccount());
        } else {
            throw new SException("该资料不存在！");
        }
    }

    /**
     * 删除资料
     *
     * @param archivingDelBO
     */
    @Transactional
    @Override
    public void delArchiving(ArchivingDelBO archivingDelBO) {
        ProjectMaterial projectMaterial = projectMaterialMapper.selectById(archivingDelBO.getId());
        if (projectMaterial != null) {
            //校验项目
            checkProject(projectMaterial.getProjectId());

            //删除记录
            ProjectMaterial record = new ProjectMaterial();
            BeanUtils.copyProperties(archivingDelBO, record);
            record.setUpdateUser(archivingDelBO.getAccount());
            record.setDelFlag(Constant.Status.DEL);
            projectMaterialMapper.updateSelective(record);
            //删除关联url
            relationBusiness.deleteRelation(archivingDelBO.getId(), Constant.RELATION_TYPE.PROJECT_MATERIALS);
        } else {
            throw new SException("该资料不存在！");
        }
    }

    /**
     * 导出项目数据
     *
     * @param projectQueryBO
     * @param response
     */
    @Override
    public void exportProjectExcel(ProjectQueryBO projectQueryBO, HttpServletResponse response) {

        //查询项目列表
        List<ProjectVO> projectVOList = getAllList(projectQueryBO).getList();
        List<String> columnList = new ArrayList<>();

        //表头
        columnList = Arrays.asList("项目编号", "项目名称", "负责人", "联系电话", "项目开始时间", "项目结束时间", "项目周期", "项目阶段",
                "阶段细分流程", "项目总投资额（￥）", "项目累计已签订合同金额（￥）", "项目累计已结算金额（￥）", "状态");

        if (projectVOList.size() <= 0) {
            throw new SException("暂无数据！");
        }
        //数据
        List<String[]> dataList = new ArrayList<>();
        for (ProjectVO projectVO : projectVOList) {
            String[] arr = new String[13];
            arr[0] = projectVO.getNumber();
            arr[1] = projectVO.getName();
            arr[2] = projectVO.getPerson();
            arr[3] = projectVO.getPhone();
            if (projectVO.getBeginTime() != null) {
                arr[4] = DateUtil.getDateStr(projectVO.getBeginTime());
            }
            if (projectVO.getEndTime() != null) {
                arr[5] = DateUtil.getDateStr(projectVO.getEndTime());
            }
            arr[6] = projectVO.getCycle();
            arr[7] = projectVO.getStageName();
            arr[8] = projectVO.getStageNodeName();
            arr[9] = projectVO.getTotalInvestmentStr();
            arr[10] = projectVO.getTotalContractMoneyStr();
            arr[11] = projectVO.getTotalPaidMoneyStr();
            if (projectVO.getDelFlag() != null && projectVO.getDelFlag() == 1) {
                arr[12] = "正常";
            } else {
                arr[12] = "已关闭";
            }

            dataList.add(arr);
        }

        //下载excel
        List<ExcelUtil.SheetData> sheetDataList = new ArrayList<>();
        ExcelUtil excelUtil = new ExcelUtil();
        ExcelUtil.SheetData sheetData = excelUtil.new SheetData();
        sheetData.setSheetName("项目数据");
        sheetData.setColumnList(columnList);
        sheetData.setDataList(dataList);
        sheetDataList.add(sheetData);
        String fileName = "project.xls";
        excelUtil.write(sheetDataList, fileName, response);
    }

}
