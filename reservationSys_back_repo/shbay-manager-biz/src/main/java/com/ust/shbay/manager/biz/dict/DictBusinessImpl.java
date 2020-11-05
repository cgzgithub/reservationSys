package com.ust.shbay.manager.biz.dict;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.exception.SException;
import com.ust.shbay.manager.biz.dict.bo.*;
import com.ust.shbay.manager.dao.DictInfoMapper;
import com.ust.shbay.manager.dao.DictTypeMapper;
import com.ust.shbay.manager.entity.DictInfo;
import com.ust.shbay.manager.entity.DictInfoExample;
import com.ust.shbay.manager.entity.DictType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class DictBusinessImpl implements DictBusiness {

    @Autowired
    private DictTypeMapper dictTypeMapper;

    @Autowired
    private DictInfoMapper dictInfoMapper;

    /**
     * 添加字典
     *
     * @param dictTypeAddBO
     * @return
     */
    @Override
    public void insertDictType(DictTypeAddBO dictTypeAddBO) {
        if (dictTypeAddBO.getType() == null) {
            throw new SException("字典类型Type不能为空！");
        }
        if (dictTypeMapper.findByType(dictTypeAddBO.getType()) != null) {
            throw new SException("字典类型Type已存在！");
        }
        DictType dictType = new DictType();
        BeanUtils.copyProperties(dictTypeAddBO, dictType);
        dictType.setDelFlag(Constant.Status.NORMAL);
        dictType.setCreateUser(dictTypeAddBO.getAccount());
        dictType.setUpdateUser(dictTypeAddBO.getAccount());
        dictTypeMapper.insertSelective(dictType);
    }

    /**
     * 编辑字典
     *
     * @param dictTypeEditBO
     * @return
     */
    @Override
    public void updateDictType(DictTypeEditBO dictTypeEditBO) {
//        if (dictTypeEditBO.getType() == null) {
//            throw new SException("字典类型Type不能为空！");
//        }
        //查询字典是否存在
        DictType dictType = dictTypeMapper.selectByPrimaryKey(dictTypeEditBO.getId());
        if (dictType != null) {
//            // 若type修改判断唯一
//            if (!dictType.getType().equals(dictTypeEditBO.getType()) && dictTypeMapper.findByType(dictTypeEditBO.getType()) != null) {
//                throw new SException("字典类型Type已存在！");
//            }
            DictType entity = new DictType();
            BeanUtils.copyProperties(dictTypeEditBO, entity);
            entity.setUpdateUser(dictTypeEditBO.getAccount());
            dictTypeMapper.updateByPrimaryKeySelective(entity);
        } else {
            throw new SException("字典不存在！");
        }
    }

    /**
     * 删除字典
     *
     * @param dictTypeDeleteBO
     * @return
     */
    @Transactional
    @Override
    public void deleteDictType(DictTypeDeleteBO dictTypeDeleteBO) {

        //查询字典是否存在
        DictType dictType = dictTypeMapper.selectByPrimaryKey(dictTypeDeleteBO.getId());
        if (dictType != null) {
            dictType.setDelFlag(Constant.Status.DEL);
            dictType.setUpdateUser(dictTypeDeleteBO.getAccount());
            dictTypeMapper.updateByPrimaryKeySelective(dictType);
            //删除字典数据
            List<Integer> ids = dictInfoMapper.selectIdsByTypeId(dictTypeDeleteBO.getId());
            deleteDictInfo(ids,dictTypeDeleteBO.getAccount());
        } else {
            throw new SException("字典不存在！");
        }
    }

    /**
     * 查询全部字典
     *
     * @return
     */
    @Override
    public List<DictType> getDictTypeListByTitle(String title) {
        if(title != null && !"".equals(title)){
            title = "%"+title+"%";
        }
        List<DictType> dictTypeList = dictTypeMapper.getDictTypeListByTitle(title);
        return dictTypeList;
    }

    /**
     * 添加字典对应数据
     *
     * @param dictInfoAddBO
     * @return
     */
    @Override
    public void insertDictInfo(DictInfoAddBO dictInfoAddBO) {
        DictType dictType = dictTypeMapper.selectByPrimaryKey(dictInfoAddBO.getDictTypeId());
        if (dictType == null) {
            throw new SException("字典不存在！");
        }
        DictInfo dictInfo = new DictInfo();
        BeanUtils.copyProperties(dictInfoAddBO, dictInfo);
        dictInfo.setDelFlag(Constant.Status.NORMAL);
        dictInfo.setCreateUser(dictInfoAddBO.getAccount());
        dictInfo.setUpdateUser(dictInfoAddBO.getAccount());
        dictInfoMapper.insertSelective(dictInfo);
    }

    /**
     * 编辑字典对应数据
     *
     * @param dictInfoEditBO
     * @return
     */
    @Override
    public void updateDictInfo(DictInfoEditBO dictInfoEditBO) {
        //查询字典数据是否存在
        DictInfo dictInfo = dictInfoMapper.selectByPrimaryKey(dictInfoEditBO.getId());
        if (dictInfo != null) {
            DictInfo entity = new DictInfo();
            BeanUtils.copyProperties(dictInfoEditBO, entity);
            entity.setUpdateUser(dictInfoEditBO.getAccount());
            dictInfoMapper.updateByPrimaryKeySelective(entity);
        } else {
            throw new SException("字典数据不存在！");
        }
    }

    /**
     * 删除字典对应数据
     *
     * @param ids
     * @param account
     * @return
     */
    @Override
    public void deleteDictInfo(List<Integer> ids, String account) {

        if (ids != null &&  ids.size() > 0) {
            for (Integer id : ids) {
                //查询字典数据是否存在
                DictInfo dictInfo = dictInfoMapper.selectByPrimaryKey(id);
                if (dictInfo != null) {
                    if(dictInfo.getIsSys() != null && dictInfo.getIsSys() == 1){
                        throw new SException("字典中包含系统数据，删除后会导致系统异常！");
                    }
                    dictInfo.setDelFlag(Constant.Status.DEL);
                    dictInfo.setUpdateUser(account);
                    dictInfoMapper.updateByPrimaryKeySelective(dictInfo);
                }
            }
        }
    }

    /**
     * 按条件查询字典数据
     *
     * @param dictInfoQueryBO
     * @return
     */
    @Override
    public PageInfo<DictInfo> getDictInfoByCondition(DictInfoQueryBO dictInfoQueryBO) {

        DictInfoExample example = new DictInfoExample();
        DictInfoExample.Criteria criteria = example.createCriteria();
        if (dictInfoQueryBO.getStatus() != null) {
            criteria.andStatusEqualTo(dictInfoQueryBO.getStatus());
        }
        if (dictInfoQueryBO.getDictTypeId() != null) {
            criteria.andDictTypeIdEqualTo(dictInfoQueryBO.getDictTypeId());
        }
        if (dictInfoQueryBO.getTitle() != null && !"".equals(dictInfoQueryBO.getTitle())) {
            criteria.andTitleLike("%" + dictInfoQueryBO.getTitle() + "%");
        }
        //排序
        if (dictInfoQueryBO.getSort() != null && !"".equals(dictInfoQueryBO.getSort())
                && dictInfoQueryBO.getOrder() != null && !"".equals(dictInfoQueryBO.getOrder())) {
            if (dictInfoQueryBO.getSort().equals("sortOrder")) {
                example.setOrderByClause("sort_order " + dictInfoQueryBO.getOrder());
            } else if (dictInfoQueryBO.getSort().equals("createTime")) {
                example.setOrderByClause("create_time " + dictInfoQueryBO.getOrder());
            } else {
                example.setOrderByClause(dictInfoQueryBO.getSort() + " " + dictInfoQueryBO.getOrder());
            }
        }else{
            example.setOrderByClause("create_time desc");
        }

        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);

        PageHelper.startPage(dictInfoQueryBO.getPageNumber(), dictInfoQueryBO.getPageSize());
        List<DictInfo> dictInfoList = dictInfoMapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo(dictInfoList);
        return pageInfo;
    }

    /**
     * 通过字典type获取字典对应数据
     *
     * @param type
     * @return
     */
    @Override
    public List<DictInfo> getDictInfoByType(String type) {
        DictType dictType = dictTypeMapper.findByType(type);
        if (dictType == null) {
            throw new SException("字典不存在！");
        }
        DictInfoExample example = new DictInfoExample();
        DictInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDictTypeIdEqualTo(dictType.getId());
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<DictInfo> list = dictInfoMapper.selectByExample(example);
        return list;
    }
}
