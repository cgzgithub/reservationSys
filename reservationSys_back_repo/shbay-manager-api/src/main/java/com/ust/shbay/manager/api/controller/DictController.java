package com.ust.shbay.manager.api.controller;


import com.github.pagehelper.PageInfo;
import com.ust.shbay.common.dto.ResponseEntity;
import com.ust.shbay.manager.api.controller.dto.dict.DictInfoQueryType;
import com.ust.shbay.manager.api.controller.dto.dict.*;
import com.ust.shbay.manager.biz.dict.DictBusiness;
import com.ust.shbay.manager.biz.dict.bo.*;
import com.ust.shbay.manager.entity.DictInfo;
import com.ust.shbay.manager.entity.DictType;
import com.ust.shbay.service.base.BaseController;
import com.ust.shbay.service.base.BaseUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = "数据字典")
@RestController
@RequestMapping("api/dict/")
public class DictController extends BaseController {

    @Autowired
    private DictBusiness dictBusiness;

    @ApiOperation(value = "按条件查询字典")
    @PostMapping("getDictTypeList")
    public ResponseEntity getDictTypeList(@RequestBody DictTypeQuery dictTypeQuery) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        List<DictType> list = dictBusiness.getDictTypeListByTitle(dictTypeQuery.getTitle());
        return ResponseEntity.buildSuccEntity(list);
    }

//    @ApiOperation(value = "新增字典")
//    @PostMapping("addDictType")
//    public ResponseEntity addDictType(@RequestBody DictTypeAdd dictTypeAdd) {
//        DictTypeAddBO dictTypeAddBO = new DictTypeAddBO();
//        BeanUtils.copyProperties(dictTypeAdd, dictTypeAddBO);
//        setBaseAccount(dictTypeAddBO);
//        dictBusiness.insertDictType(dictTypeAddBO);
//        return ResponseEntity.buildSuccEntity();
//    }

    @ApiOperation(value = "编辑字典")
    @PostMapping("editDictType")
    public ResponseEntity editDictType(@RequestBody DictTypeEdit dictTypeEdit) {
        DictTypeEditBO dictTypeEditBO = new DictTypeEditBO();
        BeanUtils.copyProperties(dictTypeEdit, dictTypeEditBO);
        setBaseAccount(dictTypeEditBO);
        dictBusiness.updateDictType(dictTypeEditBO);
        return ResponseEntity.buildSuccEntity();
    }

//    @ApiOperation(value = "删除字典")
//    @PostMapping("deleteDictType")
//    public ResponseEntity deleteDictType(@RequestBody DictTypeDelete dictTypeDelete) {
//        DictTypeDeleteBO dictTypeDeleteBO = new DictTypeDeleteBO();
//        BeanUtils.copyProperties(dictTypeDelete, dictTypeDeleteBO);
//        setBaseAccount(dictTypeDeleteBO);
//        dictBusiness.deleteDictType(dictTypeDeleteBO);
//        return ResponseEntity.buildSuccEntity();
//    }

    @ApiOperation(value = "新增字典数据")
    @PostMapping("addDictInfo")
    public ResponseEntity addDictInfo(@RequestBody DictInfoAdd dictInfoAdd) {
        DictInfoAddBO dictInfoAddBO = new DictInfoAddBO();
        BeanUtils.copyProperties(dictInfoAdd, dictInfoAddBO);
        setBaseAccount(dictInfoAddBO);
        dictBusiness.insertDictInfo(dictInfoAddBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "编辑字典数据")
    @PostMapping("editDictInfo")
    public ResponseEntity editDictInfo(@RequestBody DictInfoEdit dictInfoEdit) {
        DictInfoEditBO dictInfoEditBO = new DictInfoEditBO();
        BeanUtils.copyProperties(dictInfoEdit, dictInfoEditBO);
        setBaseAccount(dictInfoEditBO);
        dictBusiness.updateDictInfo(dictInfoEditBO);
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "删除字典数据")
    @PostMapping("deleteDictInfo")
    public ResponseEntity deleteDictInfo(@RequestBody DictInfoDelete dictInfoDelete) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        dictBusiness.deleteDictInfo(dictInfoDelete.getIds(),baseUser.getAccount());
        return ResponseEntity.buildSuccEntity();
    }

    @ApiOperation(value = "按条件查询字典数据")
    @PostMapping("getDictInfoByCondition")
    public ResponseEntity<PageInfo> getDictInfoByCondition(@RequestBody DictInfoQuery dictInfoQuery) {
        DictInfoQueryBO dictInfoQueryBO = new DictInfoQueryBO();
        BeanUtils.copyProperties(dictInfoQuery, dictInfoQueryBO);
        setBaseAccount(dictInfoQueryBO);
        PageInfo<DictInfo> pageInfo = dictBusiness.getDictInfoByCondition(dictInfoQueryBO);
        return ResponseEntity.buildSuccEntity(pageInfo);
    }

    @ApiOperation(value = "通过字典type获取字典对应数据")
    @PostMapping("getDictInfoByType")
    public ResponseEntity<List> getDictInfoByType(@RequestBody DictInfoQueryType dictInfoQueryType) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        List<DictInfo> dictInfoList = dictBusiness.getDictInfoByType(dictInfoQueryType.getType());
        return ResponseEntity.buildSuccEntity(dictInfoList);
    }

}
