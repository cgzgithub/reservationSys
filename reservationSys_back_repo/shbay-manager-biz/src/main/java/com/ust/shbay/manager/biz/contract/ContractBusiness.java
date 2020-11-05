package com.ust.shbay.manager.biz.contract;


import com.github.pagehelper.PageInfo;
import com.ust.shbay.manager.biz.contract.bo.*;
import com.ust.shbay.manager.entity.vo.ContractNodeVO;
import com.ust.shbay.manager.entity.vo.ContractVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ContractBusiness {

    /**
     * 通过条件查询合同列表
     * @param contractQueryBO
     * @return
     */
    PageInfo<ContractVO> getContractList(ContractQueryBO contractQueryBO);

    /**
     * 添加合同信息
     * @param contractAddBO
     */
    void addContract(ContractAddBO contractAddBO);

    /**
     * 编辑合同信息
     * @param contractEditBO
     */
    void editContract(ContractEditBO contractEditBO);

    /**
     * 删除合同信息
     * @param contractDelBO
     */
    void delContractDel(ContractDelBO contractDelBO);

    /**
     * 通过合同id查询合同节点列表
     * @param contractId
     * @return
     */
    List<ContractNodeVO> getNodeListById(Integer contractId);

    /**
     * 新增付款节点
     * @param contractNodeAddBO
     */
    void addContractNode(ContractNodeAddBO contractNodeAddBO);

    /**
     * 编辑付款节点
     * @param contractNodeEditBO
     */
    void editContractNode(ContractNodeEditBO contractNodeEditBO);

    /**
     * 删除付款节点
     * @param contractNodeDelBO
     */
    void delContractDelNode(ContractNodeDelBO contractNodeDelBO);

    /**
     * 删除项目全部合同和合同节点
     * @param projectId
     * @param account
     */
    void delProjectContract(Integer projectId,String account);

    /**
     * 导出合同数据
     * @param contractQueryBO
     * @param response
     */
    void exportContractExcel(ContractQueryBO contractQueryBO, HttpServletResponse response);

    /**
     * 查询项目累计已签订合同金额和项目累计已结算金额
     * @param projectId
     * @return
     */
    Map<String,String> getMoneyByProjectId(Integer projectId);
}
