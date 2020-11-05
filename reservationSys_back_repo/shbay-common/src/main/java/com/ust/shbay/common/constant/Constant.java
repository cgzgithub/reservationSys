package com.ust.shbay.common.constant;

/**
 *
 */
public class Constant {

    //当前用户，用于登录后存储用户信息
    public static final String CURRENT_USER = "CURRENT_USER";

    // 账户信息
    public static final String SESSION_ACCOUNT = "session_account";

    // 渠道
    public static final String SESSION_CHANNEL = "session_channel";

    public static class Sys {
        // 请求成功
        public static final Integer SUCCESS = 1;

        // 请求失败
        public static final Integer FAILED = 0;

    }

    //用于数据库表中的del_flag字段
    public static class Status {

        // 正常状态
        public static final Integer NORMAL = 1;

        // 删除状态
        public static final Integer DEL = 0;

    }


    // 角色类型
    public static class ROLE_TYPE {
        //普通角色
        public static final Integer COMMOM = 0;
        //管理员
        public static final Integer MANAGER = 1;
        //PC端默认角色
        public static final Integer BACK_DEFAULT = 2;
    }

    // 附件类型
    public static class RELATION_TYPE {
        //meeting
        public static final String MEETING = "meeting";
        //meetingBooking
        public static final String MEETINGBOOKING = "meetingBooking";
        //showroomBooking
        public static final String SHOWROOMBooking = "showroomBooking";

        //apartment
        public static final String APARTMENT = "apartment";
        //trustInstrument
        public static final String TRUST_INSTRUMENT = "trustInstrument";
        //businessLicense
        public static final String BUSSINESS_LICENSE = "businessLicense";
        //pledge
        public static final String PLEDGE = "pledge";
        //agentIdCard
        public static final String AGENT_ID_CARD = "agentIdCard";
        //application
        public static final String APPLICATION = "application";
        //applicantIdCard
        public static final String AppLICANT_ID_CARD = "applicantIdCard";
        //laborContract
        public static final String LABOR_CONTRACT = "laborContract";
        //certificate
        public static final String CERTIFICATE = "certificate";
        //otherMaterials
        public static final String OTHER_MATERIALS = "otherMaterials";

        //项目附件
        public static final String PROJECT_MATERIALS = "projectMaterials";
        //合同附件
        public static final String CONTRACT_MATERIAL = "contractMaterial";

    }

    // 单位时间段是否可预约的状态
    public static class BOOKING_TIME_STATUS {
        //可以预约
        public static final Integer CAN_BOOKING = 0;
        //不可预约
        public static final Integer CANNOT_BOOKING = -1;
        //已经预约
        public static final Integer IS_BOOKED = 1;
    }

    // 来访单位名称类型
    public static class VISIT_COMPANY_NAME_TYPE {
        //中文
        public static final Integer CHINESE = 0;
        //英文
        public static final Integer ENGLISH = 1;
    }

    // 预约申请的状态
    public static class BOOKING_STATUS {
        //预约已取消
        public static final Integer CANCEL_BOOKING = -2;
        //未到访
        public static final Integer ISNOT_VISITED = -1;
        //预约中
        public static final Integer IS_BOOKING = 0;
        //到访
        public static final Integer IS_VISITED = 1;
    }

    // 预约通道
    public static class BOOKING_PASSAGEWAY {
        //小程序
        public static final Integer APP = 0;
        //web
        public static final Integer WEB = 1;
    }

    // 预约类型
    public static class BOOKING_TYPE {
        //会议室预约
        public static final Integer MEETING_BOOKING = 0;
        //展厅预约
        public static final Integer SHOWROOM_BOOKING = 1;
    }

    // 字典数据id，t_dict_info
    public static class DICT_INFO_ID {
        //内部会议室
        public static final Integer INSIDEMEETINGId = 1;
        //路演厅
        public static final Integer ROADSHOWId = 2;
        //合同状态-完成
        public static final Integer PROJECT_CONTRACT_Id = 19;
    }

    //公寓申请状态
    public static class APARTMENT_STATUS_TYPE {
        //审核不通过
        public static final Integer FAILED_REVIEW = -1;
        //未提审
        public static final Integer NOT_SUBMITTED_REVIEW = 0;
        //复核
        public static final Integer APARTMENT_TEAM_REVIEW = 1;
        //湾区分管审核
        public static final Integer AUDIT_UNIT_REVIEW = 2;
        //湾区总经理审核
        public static final Integer AUDIT_UNIT_LEADER_REVIEW = 3;
        //镇分管审核
        public static final Integer LEADER_CHARGE_REVIEW = 4;
        //审核通过
        public static final Integer APPROVED = 5;
    }

    //公寓合同状态
    public static class APARTMENT_CONTRACT_STATUS {

        //合同执行中
        public static final Integer EXECUTING = 0;

        //提前解约
        public static final Integer EARLY_TERMINATION = -1;

        //合同到期
        public static final Integer EXPIRE = -2;
    }

    //公寓合同状态
    public static class APARTMENT_STATUS {

        //待出租
        public static final Integer FOR_RENT = 0;

        //已出租
        public static final Integer LEASED = 1;
    }


    // 项目类型
    public static class PROJECT_TYPE {
        //工程项目
        public static final Integer PROJECT = 0;
        //规划调整
        public static final Integer PLAN = 1;
        //土地前期出让
        public static final Integer SELL = 2;
        //项目前期报建
        public static final Integer Project_Application = 3;
    }

    // 项目流程节点信息表，信息分类
    public static class STAGE_OR_NODE {
        //阶段
        public static final Integer TYPE_STAGE = 0;
        //非自定义节点
        public static final Integer TYPE_ISNOT_CUSTOM_NODE = 1;
        //自定义节点
        public static final Integer TYPE_IS_CUSTOM_NODE = 2;
    }

    // 阶段类型：0不可自定义节点，1可自定义节点如施工阶段
    public static class STAGE_TYPE {
        //不可自定义节点的阶段
        public static final Integer STAGE_ISNOT_CUSTOM = 0;
        //可自定义节点的阶段
        public static final Integer STAGE_IS_CUSTOM = 1;
    }

    //菜单path
    public static class MENU_PATH {
        //复核
        public static final String REVIEWLIST_SELF = "reviewlist/self";
        //湾区审核
        public static final String REVIEWLIST_APARTMENT = "reviewlist/apartment";
        //湾区总经理审核
        public static final String REVIEWLIST_COMPANY  = "reviewlist/company";
        //分管领导审核列表
        public static final String REVIEWLIST_LEADER = "reviewlist/leader";
    }

}
