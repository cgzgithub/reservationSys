package com.ust.shbay.manager.biz.contract;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.exception.SException;
import com.ust.shbay.common.utils.DateUtil;
import com.ust.shbay.common.utils.ExcelUtil;
import com.ust.shbay.manager.biz.contract.bo.*;
import com.ust.shbay.manager.biz.file.RelationBusiness;
import com.ust.shbay.manager.biz.project.ProjectBusiness;
import com.ust.shbay.manager.dao.DictInfoMapper;
import com.ust.shbay.manager.entity.*;
import com.ust.shbay.manager.entity.vo.ContractNodeVO;
import com.ust.shbay.manager.entity.vo.ContractVO;
import com.ust.shbay.manager.dao.ContractMapper;
import com.ust.shbay.manager.dao.ContractNodeMapper;
import com.ust.shbay.manager.entity.vo.ContractQueryDO;
import com.ust.shbay.manager.entity.vo.TRelationVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

@Service
public class ContractBusinessImpl implements ContractBusiness {

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private ContractNodeMapper contractNodeMapper;

    @Autowired
    private DictInfoMapper dictInfoMapper;

    @Autowired
    private RelationBusiness relationBusiness;

    @Autowired
    private ProjectBusiness projectBusiness;

    /**
     * 通过条件查询合同列表
     *
     * @param contractQueryBO
     * @return
     */
    @Override
    public PageInfo<ContractVO> getContractList(ContractQueryBO contractQueryBO) {
        ContractQueryDO contractQueryDO = new ContractQueryDO();
        BeanUtils.copyProperties(contractQueryBO, contractQueryDO);
        //合同编号
        if (contractQueryBO.getContractNum() != null && !"".equals(contractQueryBO.getContractNum())) {
            contractQueryDO.setContractNum("%" + contractQueryBO.getContractNum() + "%");
        }
        //项目编号
        if (contractQueryBO.getProjectNum() != null && !"".equals(contractQueryBO.getProjectNum())) {
            contractQueryDO.setProjectNum("%" + contractQueryBO.getProjectNum() + "%");
        }
        //合同名称
        if (contractQueryBO.getContractName() != null && !"".equals(contractQueryBO.getContractName())) {
            contractQueryDO.setContractName("%" + contractQueryBO.getContractName() + "%");
        }
        //项目名称
        if (contractQueryBO.getProjectName() != null && !"".equals(contractQueryBO.getProjectName())) {
            contractQueryDO.setProjectName("%" + contractQueryBO.getProjectName() + "%");
        }
        //甲方
        if (contractQueryBO.getFirstParty() != null && !"".equals(contractQueryBO.getFirstParty())) {
            contractQueryDO.setFirstParty("%" + contractQueryBO.getFirstParty() + "%");
        }
        //乙方
        if (contractQueryBO.getSecondParty() != null && !"".equals(contractQueryBO.getSecondParty())) {
            contractQueryDO.setSecondParty("%" + contractQueryBO.getSecondParty() + "%");
        }
        PageHelper.startPage(contractQueryBO.getPageNumber(), contractQueryBO.getPageSize());
        List<ContractVO> list = contractMapper.selectByCondition(contractQueryDO);
        PageInfo pageInfo = new PageInfo(list);
        if (list.size() > 0) {
            for (ContractVO contractVO : list) {
                //合同状态
                if (contractVO.getStatus() != null) {
                    contractVO.setContractStatusName(dictInfoMapper.selectTitleById(contractVO.getStatus()));
                }
                //合同类型
                if (contractVO.getContractType() != null) {
                    contractVO.setContractTypeName(dictInfoMapper.selectTitleById(contractVO.getContractType()));
                }

                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                numberFormat.setMaximumFractionDigits(2); // 最大小数位数
                numberFormat.setMinimumFractionDigits(2); // 最小小数位数

                //合同总金额
                if (contractVO.getTotalMoney() != null) {
                    contractVO.setTotalMoneyStr(numberFormat.format(contractVO.getTotalMoney()));
                }
                //累计已付金额
                if (contractVO.getTotalPaidMoney() != null) {
                    contractVO.setTotalPaidMoneyStr(numberFormat.format(contractVO.getTotalPaidMoney()));
                }
                //最终版结算价
                if (contractVO.getFinalMoney() != null) {
                    //最终版结算价
                    contractVO.setFinalMoneyStr(numberFormat.format(contractVO.getFinalMoney()));
                }
                //未支付金额
                if (contractVO.getTotalPaidMoney() != null) {
                    if (contractVO.getTotalMoney() != null) {
                        if (contractVO.getFinalMoney() != null) {
                            BigDecimal unpaidMoney = contractVO.getFinalMoney().subtract(contractVO.getTotalPaidMoney());
                            contractVO.setUnpaidMoneyStr(numberFormat.format(unpaidMoney));
                        } else {
                            BigDecimal unpaidMoney = contractVO.getTotalMoney().subtract(contractVO.getTotalPaidMoney());
                            contractVO.setUnpaidMoneyStr(numberFormat.format(unpaidMoney));
                        }
                    }
                } else {
                    if (contractVO.getTotalMoney() != null) {
                        if (contractVO.getFinalMoney() != null) {
                            contractVO.setUnpaidMoneyStr(numberFormat.format(contractVO.getFinalMoney()));
                        } else {
                            contractVO.setUnpaidMoneyStr(numberFormat.format(contractVO.getTotalMoney()));
                        }
                    }
                }

                //关联合同附件
                List<TRelationVO> tRelationList = relationBusiness.searchRelationList(Constant.RELATION_TYPE.CONTRACT_MATERIAL,
                        contractVO.getId());
                contractVO.setContractMaterials(tRelationList);
            }
        }
        return pageInfo;
    }

    /**
     * 添加合同
     *
     * @param contractAddBO
     */
    @Transactional
    @Override
    public void addContract(ContractAddBO contractAddBO) {

        //校验项目
        projectBusiness.checkProject(contractAddBO.getProjectId());

        Contract contract = new Contract();
        BeanUtils.copyProperties(contractAddBO, contract);
        contract.setDelFlag(Constant.Status.NORMAL);
        contract.setCreateUser(contractAddBO.getAccount());
        contract.setUpdateUser(contractAddBO.getAccount());
        contractMapper.insertSelective(contract);

        //合同附件
        if (contractAddBO.getContractMaterials() != null && contractAddBO.getContractMaterials().size() > 0) {
            for (TRelation tRelation : contractAddBO.getContractMaterials()) {
                tRelation.setType(Constant.RELATION_TYPE.CONTRACT_MATERIAL);
                tRelation.setCreateUser(contractAddBO.getAccount());
                tRelation.setUpdateUser(contractAddBO.getAccount());
                tRelation.setOriginalId(contract.getId());
                relationBusiness.insertRelation(tRelation);
            }
        }
    }

    /**
     * 编辑合同信息
     *
     * @param contractEditBO
     */
    @Transactional
    @Override
    public void editContract(ContractEditBO contractEditBO) {
        //查询合同是否存在
        Contract contract = contractMapper.selectById(contractEditBO.getId());
        if (contract != null) {
            //校验项目
            projectBusiness.checkProject(contract.getProjectId());

            //校验合同状态，合同完成后不能修改
            if (contract.getStatus() == Constant.DICT_INFO_ID.PROJECT_CONTRACT_Id) {
                throw new SException("合同已经完成，无法修改！");
            }

            Contract entity = new Contract();
            BeanUtils.copyProperties(contractEditBO, entity);
            entity.setUpdateUser(contractEditBO.getAccount());
            contractMapper.updateSelective(entity);
            //更新合同附件
            List<String> oldList = relationBusiness.searchRelation(Constant.RELATION_TYPE.CONTRACT_MATERIAL, contractEditBO.getId());
            relationBusiness.updateAttachment(oldList, contractEditBO.getContractMaterials(),
                    Constant.RELATION_TYPE.CONTRACT_MATERIAL, contractEditBO.getId(), contractEditBO.getAccount());
        } else {
            throw new SException("该合同不存在！");
        }
    }

    /**
     * 删除合同信息
     *
     * @param contractDelBO
     */
    @Transactional
    @Override
    public void delContractDel(ContractDelBO contractDelBO) {
        //查询合同是否存在
        Contract contract = contractMapper.selectById(contractDelBO.getId());
        if (contract != null) {
            //校验项目
            projectBusiness.checkProject(contract.getProjectId());

            BeanUtils.copyProperties(contractDelBO, contract);
            contract.setDelFlag(Constant.Status.DEL);
            contract.setUpdateUser(contractDelBO.getAccount());
            contractMapper.updateSelective(contract);
        } else {
            throw new SException("该合同不存在！");
        }

        //删除合同附件
        relationBusiness.deleteRelation(contractDelBO.getId(), Constant.RELATION_TYPE.CONTRACT_MATERIAL);

        //通过合同id删除合同付款节点
        ContractNodeExample example = new ContractNodeExample();
        ContractNodeExample.Criteria criteria = example.createCriteria();
        criteria.andContractIdEqualTo(contract.getId());
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);

        ContractNode contractNode = new ContractNode();
        contractNode.setDelFlag(Constant.Status.DEL);
        contractNode.setUpdateUser(contractDelBO.getAccount());
        contractNodeMapper.updateByExampleSelective(contractNode, example);
    }

    /**
     * 通过合同id查询合同节点列表
     *
     * @param contractId
     * @return
     */
    @Override
    public List<ContractNodeVO> getNodeListById(Integer contractId) {
        //查询合同是否存在
        Contract contract = contractMapper.selectById(contractId);
        if (contract != null) {
            ContractNodeExample example = new ContractNodeExample();
            ContractNodeExample.Criteria criteria = example.createCriteria();
            criteria.andContractIdEqualTo(contractId);
            criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
            example.setOrderByClause("update_time desc");
            List<ContractNode> nodeList = contractNodeMapper.selectByExample(example);
            List<ContractNodeVO> nodeVOList = new ArrayList<>();
            if (nodeList.size() > 0) {
                for (ContractNode contractNode : nodeList) {
                    ContractNodeVO contractNodeVO = new ContractNodeVO();
                    BeanUtils.copyProperties(contractNode, contractNodeVO);
                    NumberFormat numberFormat = NumberFormat.getNumberInstance();
                    numberFormat.setMaximumFractionDigits(2); // 最大小数位数
                    numberFormat.setMinimumFractionDigits(2); // 最小小数位数
                    //节点付款金额（千分数）
                    if (contractNode.getPaymentMoney() != null) {
                        contractNodeVO.setPaymentMoneyStr(numberFormat.format(contractNode.getPaymentMoney()));
                    }
                    nodeVOList.add(contractNodeVO);
                }
            }
            return nodeVOList;
        } else {
            throw new SException("该合同不存在！");
        }
    }

    /**
     * 新增付款节点
     *
     * @param contractNodeAddBO
     */
    @Override
    public void addContractNode(ContractNodeAddBO contractNodeAddBO) {
        //查询合同是否存在
        Contract contract = contractMapper.selectById(contractNodeAddBO.getContractId());
        if (contract != null) {
            //校验项目
            projectBusiness.checkProject(contract.getProjectId());

            //校验合同状态，合同完成后不能修改
            if (contract.getStatus() == Constant.DICT_INFO_ID.PROJECT_CONTRACT_Id) {
                throw new SException("合同已经完成，无法修改！");
            }

            ContractNode contractNode = new ContractNode();
            BeanUtils.copyProperties(contractNodeAddBO, contractNode);
            contractNode.setDelFlag(Constant.Status.NORMAL);
            contractNode.setCreateUser(contractNodeAddBO.getAccount());
            contractNode.setUpdateUser(contractNodeAddBO.getAccount());
            contractNodeMapper.insertSelective(contractNode);
        } else {
            throw new SException("该合同不存在！");
        }
    }

    /**
     * 编辑付款节点
     *
     * @param contractNodeEditBO
     */
    @Override
    public void editContractNode(ContractNodeEditBO contractNodeEditBO) {
        //查询合同是否存在
        Contract contract = contractMapper.selectById(contractNodeEditBO.getContractId());
        if (contract != null) {
            //校验项目
            projectBusiness.checkProject(contract.getProjectId());

            //校验合同状态，合同完成后不能修改
            if (contract.getStatus() == Constant.DICT_INFO_ID.PROJECT_CONTRACT_Id) {
                throw new SException("合同已经完成，无法修改！");
            }

            //查询合同节点是否存在
            ContractNode contractNode = contractNodeMapper.selectById(contractNodeEditBO.getId());
            if (contractNode != null) {
                ContractNode entity = new ContractNode();
                BeanUtils.copyProperties(contractNodeEditBO, entity);
                entity.setUpdateUser(contractNodeEditBO.getAccount());
                contractNodeMapper.updateSelective(entity);
            } else {
                throw new SException("该合同付款节点不存在！");
            }
        } else {
            throw new SException("该合同不存在！");
        }
    }

    /**
     * 删除付款节点
     *
     * @param contractNodeDelBO
     */
    @Override
    public void delContractDelNode(ContractNodeDelBO contractNodeDelBO) {
        //查询合同节点是否存在
        ContractNode contractNode = contractNodeMapper.selectById(contractNodeDelBO.getId());
        if (contractNode != null) {

            Contract contract = contractMapper.selectById(contractNode.getContractId());
            if (contract != null) {
                //校验项目
                projectBusiness.checkProject(contract.getProjectId());

                //校验合同状态，合同完成后不能修改
                if (contract.getStatus() == Constant.DICT_INFO_ID.PROJECT_CONTRACT_Id) {
                    throw new SException("合同已经完成，无法修改！");
                }

                BeanUtils.copyProperties(contractNodeDelBO, contractNode);
                contractNode.setDelFlag(Constant.Status.DEL);
                contractNode.setUpdateUser(contractNodeDelBO.getAccount());
                contractNodeMapper.updateSelective(contractNode);
            } else {
                throw new SException("该合同不存在！");
            }
        } else {
            throw new SException("该合同付款节点不存在！");
        }
    }

    /**
     * 删除项目全部合同和合同节点
     *
     * @param projectId
     * @param account
     */
    @Transactional
    @Override
    public void delProjectContract(Integer projectId, String account) {
        //校验项目
        projectBusiness.checkProject(projectId);

        //删除合同
        ContractExample example = new ContractExample();
        ContractExample.Criteria criteria = example.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        Contract record = new Contract();
        record.setDelFlag(Constant.Status.DEL);
        record.setUpdateUser(account);
        contractMapper.updateByExampleSelective(record, example);

        //查询项目全部合同付款节点
        List<Integer> ids = contractNodeMapper.selectNodeIdsByProjectId(projectId);

        //通过合同id删除合同付款节点
        if (ids != null && ids.size() > 0) {
            for (Integer id : ids) {
                ContractNode contractNode = new ContractNode();
                contractNode.setDelFlag(Constant.Status.DEL);
                contractNode.setUpdateUser(account);
                contractNode.setId(id);
                contractNodeMapper.updateSelective(contractNode);
            }
        }
    }

    /**
     * 导出合同数据
     *
     * @param contractQueryBO
     * @param response
     */
    @Override
    public void exportContractExcel(ContractQueryBO contractQueryBO, HttpServletResponse response) {

        //查询合同列表
        List<ContractVO> contractVOList = getContractList(contractQueryBO).getList();
        List<String> columnList = new ArrayList<>();

        //表头
        columnList = Arrays.asList("合同编号", "合同名称", "项目编号", "项目名称", "项目类型", "甲方", "乙方", "签订时间", "签订人", "开始时间", "结束时间",
                "合同类型", "合同状态", "合同总金额（￥）", "最终版结算价（￥）", "累计已付金额（￥）", "未支付金额（￥）");

        if (contractVOList.size() <= 0) {
            throw new SException("暂无数据！");
        }

        //数据
        List<String[]> dataList = new ArrayList<>();
        for (ContractVO contractVO : contractVOList) {
            String[] arr = new String[17];
            arr[0] = contractVO.getContractNum();
            arr[1] = contractVO.getContractName();
            arr[2] = contractVO.getProjectNum();
            arr[3] = contractVO.getProjectName();
            if (contractVO.getProjectType() == 0) {
                arr[4] = "工程项目";
            } else if (contractVO.getProjectType() == 1) {
                arr[4] = "规划调整";
            } else if (contractVO.getProjectType() == 2) {
                arr[4] = "土地前期出让";
            } else if (contractVO.getProjectType() == 3) {
                arr[4] = "项目前期报建";
            }
            arr[5] = contractVO.getFirstParty();
            arr[6] = contractVO.getSecondParty();
            if (contractVO.getSignTime() != null) {
                arr[7] = DateUtil.getDateStr(contractVO.getSignTime());
            }
            arr[8] = contractVO.getSignPerson();
            if (contractVO.getBeginTime() != null) {
                arr[9] = DateUtil.getDateStr(contractVO.getBeginTime());
            }
            if (contractVO.getEndTime() != null) {
                arr[10] = DateUtil.getDateStr(contractVO.getEndTime());
            }
            if (contractVO.getContractType() != null) {
                arr[11] = contractVO.getContractType().toString();
            }
            if (contractVO.getStatus() != null) {
                arr[12] = contractVO.getStatus().toString();
            }
            arr[13] = contractVO.getTotalMoneyStr();
            arr[14] = contractVO.getFinalMoneyStr();
            arr[15] = contractVO.getTotalPaidMoneyStr();
            arr[16] = contractVO.getUnpaidMoneyStr();

            dataList.add(arr);
        }

        //下载excel
        List<ExcelUtil.SheetData> sheetDataList = new ArrayList<>();
        ExcelUtil excelUtil = new ExcelUtil();
        ExcelUtil.SheetData sheetData = excelUtil.new SheetData();
        sheetData.setSheetName("合同数据");
        sheetData.setColumnList(columnList);
        sheetData.setDataList(dataList);
        sheetDataList.add(sheetData);
        String fileName = "contract.xls";
        excelUtil.write(sheetDataList, fileName, response);
    }

    /**
     * 查询项目累计已签订合同金额和项目累计已结算金额
     *
     * @param projectId
     * @return
     */
    @Override
    public Map<String, String> getMoneyByProjectId(Integer projectId) {
        Map<String, String> map = new HashMap<>();
        //项目累计已签订合同金额、项目累计已结算金额
        String totalContractMoneyStr = "";
        String totalPaidMoneyStr = "";
        ContractExample example = new ContractExample();
        ContractExample.Criteria criteria = example.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<Contract> contractList = contractMapper.selectByExample(example);
        if (contractList != null && contractList.size() > 0) {
            BigDecimal totalMoneys = new BigDecimal("0");
            BigDecimal totalPaidMoneys = new BigDecimal("0");
            for (Contract contract : contractList) {
                //查询项目合同总金额
                if (contract.getTotalMoney() != null) {
                    totalMoneys = totalMoneys.add(contract.getTotalMoney());
                }

                //查询已付金额
                ContractNodeExample example2 = new ContractNodeExample();
                ContractNodeExample.Criteria criteria2 = example2.createCriteria();
                criteria2.andContractIdEqualTo(contract.getId());
                criteria2.andDelFlagEqualTo(Constant.Status.NORMAL);
                List<ContractNode> contractNodeList = contractNodeMapper.selectByExample(example2);
                if (contractNodeList != null && contractNodeList.size() > 0) {
                    for (ContractNode contractNode : contractNodeList) {
                        if (contractNode.getPaymentMoney() != null) {
                            totalPaidMoneys = totalPaidMoneys.add(contractNode.getPaymentMoney());
                        }
                    }
                }
            }
            totalContractMoneyStr = totalMoneys.toString();
            totalPaidMoneyStr = totalPaidMoneys.toString();
        }
        map.put("totalContractMoneyStr", totalContractMoneyStr);
        map.put("totalPaidMoneyStr", totalPaidMoneyStr);
        return map;
    }
}
