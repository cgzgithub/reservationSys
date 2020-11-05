package com.ust.shbay.manager.entity.vo;

import com.ust.shbay.manager.entity.TRelation;
import lombok.Data;

@Data
public class TRelationVO extends TRelation {
    //七牛云url
    private String downloadUrl;
}
