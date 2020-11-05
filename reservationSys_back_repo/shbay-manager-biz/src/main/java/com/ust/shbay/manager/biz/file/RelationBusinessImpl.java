package com.ust.shbay.manager.biz.file;

import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.manager.dao.RelationMapper;
import com.ust.shbay.manager.entity.TRelation;
import com.ust.shbay.manager.entity.vo.TRelationVO;
import com.ust.shbay.service.file.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RelationBusinessImpl implements RelationBusiness {

    @Autowired
    private RelationMapper relationMapper;

    @Autowired
    private FileService fileService;

    @Override
    public void deleteRelation(Integer originalId, String type) {

        List<TRelation> tRelationList = relationMapper.selectByOriginalIdAndType(originalId, type);
        if (tRelationList.size() > 0) {
            for (TRelation tRelation : tRelationList) {
                TRelation entity = new TRelation();
                entity.setId(tRelation.getId());
                entity.setDelFlag(0);
                relationMapper.updateByPrimaryKeySelective(entity);
            }
        }
    }

    @Override
    public void insertRelation(TRelation tRelation) {

        relationMapper.insertSelective(tRelation);
    }

    @Override
    public List<String> searchRelation(String type, Integer originalId) {
        List<String> strings = relationMapper.selecturlByOriginalIdAndType(type, originalId);
        return strings;
    }

    @Override
    public List<TRelationVO> searchRelationList(String type, Integer originalId) {
        List<TRelation> relationList = relationMapper.selectByOriginalIdAndType(originalId,type);
        List<TRelationVO> tRelationVOList = new ArrayList<>();
        if(relationList.size() > 0){
            for(TRelation tRelation : relationList){
                TRelationVO tRelationVO = new TRelationVO();
                BeanUtils.copyProperties(tRelation,tRelationVO);
                tRelationVO.setDownloadUrl(fileService.download(tRelation.getFileUrl()));
                tRelationVOList.add(tRelationVO);
            }
        }
        return tRelationVOList;
    }

    @Override
    public void deleteAttachment(Integer originalId, String type, String fileUrl) {
        List<TRelation> tRelationList = relationMapper.selectByOriginalIdAndTypeAndUrl(originalId, type,fileUrl);
        if (tRelationList != null && tRelationList.size() > 0) {
            for (TRelation tRelation : tRelationList) {
                TRelation entity = new TRelation();
                BeanUtils.copyProperties(tRelation, entity);
                entity.setDelFlag(Constant.Status.DEL);
                relationMapper.updateByPrimaryKeySelective(entity);
            }
        }
    }

    /**
     * 新传入附件和数据库原有附件取差集，删除数据库差集，添加传入差集
     * @param list1
     * @param list2
     * @param type
     * @param originalId
     * @param account
     */
    @Override
    public void updateAttachment(List<String> list1,List<TRelation> list2,
                          String type,Integer originalId, String account){

        //如果全为空，不处理
        if((list1 == null || list1.size() == 0)&&(list2 == null || list2.size() == 0)){
            return;
        }

        //如果数据库数据为空，全部插入
        if(list1 == null || list1.size() == 0){
            for (TRelation tRelation : list2) {
                tRelation.setType(type);
                tRelation.setCreateUser(account);
                tRelation.setUpdateUser(account);
                tRelation.setOriginalId(originalId);
                insertRelation(tRelation);
            }
            return;
        }

        //如果传入附件为空，数据库数据全部删除
        if(list2 == null || list2.size() == 0){
            for (String url : list1) {
                deleteAttachment(originalId,type,url);
            }
            return;
        }

        //传入TRelation集合转url集合
        List<String> newUrlList=list2.stream().map(TRelation::getFileUrl).collect(Collectors.toList());
        //与数据库取交集
        List<String> copyList1 = new ArrayList(list1);
        list1.retainAll(newUrlList);//list1为交集，newUrlList未动（取交集的部分）
        newUrlList.removeAll(list1);//差集（newUrlList取出的差集要插入到里面）需插入
        //新增url集合转回新增附件集合
        List<TRelation> newTRelationList = new ArrayList<>();
        for(TRelation tRelation : list2){
            if(newUrlList.contains(tRelation.getFileUrl())){
                newTRelationList.add(tRelation);
            }
        }

        if(newTRelationList.size() > 0){
            //需要插入的操作
            for (TRelation tRelation : newTRelationList) {
                tRelation.setType(type);
                tRelation.setCreateUser(account);
                tRelation.setUpdateUser(account);
                tRelation.setOriginalId(originalId);
                insertRelation(tRelation);
            }
        }

        copyList1.removeAll(list1);//差集（list1中除去与list2交集的部分）需删除
        if(copyList1.size() > 0){
            for (String fileUrl : copyList1) {
                deleteAttachment(originalId,type,fileUrl);
            }
        }
    }
}
