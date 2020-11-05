package com.ust.shbay.manager.api.controller;

import com.github.pagehelper.PageInfo;
import com.ust.shbay.common.dto.ResponseEntity;
import com.ust.shbay.manager.api.controller.dto.contract.*;
import com.ust.shbay.manager.biz.contract.ContractBusiness;
import com.ust.shbay.manager.biz.contract.bo.*;
import com.ust.shbay.manager.entity.vo.ContractNodeVO;
import com.ust.shbay.manager.entity.vo.ContractVO;
import com.ust.shbay.service.base.BaseController;
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

@Api(tags = "合同管理")
@RestController
@RequestMapping("api/contract/")
public class ContractController extends BaseController {

    @Autowired
    private ContractBusiness contractBusiness;

    @ApiOperation(value = "查询合同列表")
    @PostMapping("/getContractList")
    public ResponseEntity<PageInfo> getContractList(@RequestBody ContractQuery contractQuery) {
        ContractQueryBO contractQueryBO = new ContractQueryBO();
        BeanUtils.copyProperties(contractQuery, contractQueryBO);
        setBaseAccount(contractQueryBO);
        PageInfo<ContractVO> pageInfo = contractBusiness.getContractList(contractQueryBO);
        return ResponseEntity.buildSuccEntity(pageInfo);
    }

    @ApiOperation(value = "新增合同")
    @PostMapping("add/contract")
    public ResponseEntity addContract(@Valid @RequestBody ContractAdd contractAdd, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ContractAddBO contractAddBO = new ContractAddBO();
        BeanUtils.copyProperties(contractAdd, contractAddBO);
        setBaseAccount(contractAddBO);
        contractBusiness.addContract(contractAddBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "编辑合同")
    @PostMapping("edit/contract")
    public ResponseEntity editContract(@Valid @RequestBody ContractEdit contractEdit, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ContractEditBO contractEditBO = new ContractEditBO();
        BeanUtils.copyProperties(contractEdit, contractEditBO);
        setBaseAccount(contractEditBO);
        contractBusiness.editContract(contractEditBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "删除合同")
    @PostMapping("del/Contract")
    public ResponseEntity delContract(@RequestBody ContractDel contractDel) {
        ContractDelBO contractDelBO = new ContractDelBO();
        BeanUtils.copyProperties(contractDel, contractDelBO);
        setBaseAccount(contractDelBO);
        contractBusiness.delContractDel(contractDelBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "通过合同id查询合同付款节点列表")
    @PostMapping("/getNodeListById")
    public ResponseEntity<List> getNodeListById(@RequestBody ContractNodeQuery contractNodeQuery) {
        ContractNodeQueryBO contractNodeQueryBO = new ContractNodeQueryBO();
        BeanUtils.copyProperties(contractNodeQuery, contractNodeQueryBO);
        setBaseAccount(contractNodeQueryBO);
        List<ContractNodeVO> nodeList = contractBusiness.getNodeListById(contractNodeQuery.getContractId());
        return ResponseEntity.buildSuccEntity(nodeList);
    }

    @ApiOperation(value = "新增合同付款节点")
    @PostMapping("add/contractNode")
    public ResponseEntity addContractNode(@Valid @RequestBody ContractNodeAdd contractNodeAdd, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ContractNodeAddBO contractNodeAddBO = new ContractNodeAddBO();
        BeanUtils.copyProperties(contractNodeAdd, contractNodeAddBO);
        setBaseAccount(contractNodeAddBO);
        contractBusiness.addContractNode(contractNodeAddBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "编辑合同付款节点")
    @PostMapping("edit/contractNode")
    public ResponseEntity editContractNode(@Valid @RequestBody ContractNodeEdit contractNodeEdit, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ContractNodeEditBO contractNodeEditBO = new ContractNodeEditBO();
        BeanUtils.copyProperties(contractNodeEdit, contractNodeEditBO);
        setBaseAccount(contractNodeEditBO);
        contractBusiness.editContractNode(contractNodeEditBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "删除合同付款节点")
    @PostMapping("del/ContractNode")
    public ResponseEntity delContractNode(@RequestBody ContractNodeDel contractNodeDel) {
        ContractNodeDelBO contractNodeDelBO = new ContractNodeDelBO();
        BeanUtils.copyProperties(contractNodeDel, contractNodeDelBO);
        setBaseAccount(contractNodeDelBO);
        contractBusiness.delContractDelNode(contractNodeDelBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "导出合同数据")
    @PostMapping(value = "exportContractExcel" , produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportContractExcel(@RequestBody ContractQuery contractQuery, HttpServletResponse response){
        ContractQueryBO contractQueryBO = new ContractQueryBO();
        BeanUtils.copyProperties(contractQuery,contractQueryBO);
        setBaseAccount(contractQueryBO);
        contractBusiness.exportContractExcel(contractQueryBO,response);
    }

}
