package com.ust.shbay.manager.entity.vo.showroom;

import lombok.Data;
/**
 * 展厅接待次数、取消次数、预约未到访次数
 */
@Data
public class ShowroomBookingNum {

    // 展厅名称
    private String showroomName;

    // 取值：1“接待次数”、-2“取消次数”、-1“预约未到访次数”、0“预约中”
    private String status;

    // 次数
    private Integer number;
}
