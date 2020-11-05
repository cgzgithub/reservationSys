package com.ust.shbay.manager.entity.vo.meeting;

import com.ust.shbay.manager.entity.Meeting;
import com.ust.shbay.manager.entity.MeetingEnclosure;
import com.ust.shbay.manager.entity.vo.TRelationVO;
import lombok.Data;

import java.util.List;

@Data
public class MeetingVO extends Meeting {

    //摆放要求
    private List<TRelationVO>  tRelation;

    //附件列表
    private List<MeetingEnclosure> enclosureList;

    //会议室类型
    private String meetingTypeName;
}
