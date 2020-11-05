package com.ust.shbay.manager.biz.file;

import com.ust.shbay.manager.entity.TRelation;
import com.ust.shbay.manager.entity.vo.TRelationVO;


import java.util.List;

public interface RelationBusiness {

    /**
     * 通过原信息id删除与其关联的文件id
     * @param originalId
     */
    void deleteRelation(Integer originalId,String type);

    void insertRelation(TRelation tRelation);

    List<String> searchRelation(String trustInstrument, Integer originalId);

    List<TRelationVO> searchRelationList(String type, Integer originalId);

    void deleteAttachment(Integer originalId,String type,String fileUrl);

    /**
     * 新传入附件和数据库原有附件取差集，删除数据库差集，添加传入差集
     * @param list1 数据库原有url列表
     * @param list2 传入附件列表
     * @param type
     * @param originalId
     * @param account
     */
    void updateAttachment(List<String> list1,List<TRelation> list2,
                          String type,Integer originalId, String account);
}
