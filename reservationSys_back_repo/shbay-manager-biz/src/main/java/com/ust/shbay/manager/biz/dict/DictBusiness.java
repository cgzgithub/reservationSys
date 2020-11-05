package com.ust.shbay.manager.biz.dict;


import com.github.pagehelper.PageInfo;
import com.ust.shbay.manager.biz.dict.bo.*;
import com.ust.shbay.manager.entity.DictInfo;
import com.ust.shbay.manager.entity.DictType;

import java.util.List;

public interface DictBusiness {

    /**
     * 添加字典
     * @param dictTypeAddBO
     * @return
     */
    void insertDictType(DictTypeAddBO dictTypeAddBO);

    /**
     * 编辑字典
     * @param dictTypeEditBO
     * @return
     */
    void updateDictType(DictTypeEditBO dictTypeEditBO);

    /**
     * 删除字典
     * @param dictTypeDeleteBO
     * @return
     */
    void deleteDictType(DictTypeDeleteBO dictTypeDeleteBO);

    /**
     * 查询全部字典
     * @param title
     * @return
     */
    List<DictType> getDictTypeListByTitle(String title);

    /**
     * 添加字典对应数据
     * @param dictInfoAddBO
     * @return
     */
    void insertDictInfo(DictInfoAddBO dictInfoAddBO);

    /**
     * 编辑字典对应数据
     * @param dictInfoEditBO
     * @return
     */
    void updateDictInfo(DictInfoEditBO dictInfoEditBO);

    /**
     * 删除字典对应数据
     * @param ids
     * @param account
     * @return
     */
    void deleteDictInfo(List<Integer> ids, String account);

    /**
     * 按条件查询字典数据
     * @param dictInfoQueryBO
     * @return
     */
    PageInfo<DictInfo> getDictInfoByCondition(DictInfoQueryBO dictInfoQueryBO);

    /**
     * 通过字典type获取字典对应数据
     * @param type
     * @return
     */
    List<DictInfo> getDictInfoByType(String type);
}
