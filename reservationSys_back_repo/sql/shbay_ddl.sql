/*
Navicat MySQL Data Transfer

Source Server         : 110idomp_user
Source Server Version : 50728
Source Host           : 47.103.72.110:3306
Source Database       : shbay

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2020-11-02 11:03:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sy_account
-- ----------------------------
DROP TABLE IF EXISTS `sy_account`;
CREATE TABLE `sy_account` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `account` varchar(50) NOT NULL DEFAULT '' COMMENT '账户名称(手机号）',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `del_flag` int(2) DEFAULT '1' COMMENT '0:删除  1:正常',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账号';

-- ----------------------------
-- Table structure for sy_account_role
-- ----------------------------
DROP TABLE IF EXISTS `sy_account_role`;
CREATE TABLE `sy_account_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account_id` varchar(32) NOT NULL DEFAULT '' COMMENT '账号ID',
  `role_id` varchar(32) NOT NULL DEFAULT '' COMMENT '角色ID',
  `del_flag` int(2) DEFAULT '1' COMMENT '0:删除  1:正常',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_account_id` (`account_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=249 DEFAULT CHARSET=utf8 COMMENT='账号角色';

-- ----------------------------
-- Table structure for sy_account_session
-- ----------------------------
DROP TABLE IF EXISTS `sy_account_session`;
CREATE TABLE `sy_account_session` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `company_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '公司ID',
  `account_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '账号ID',
  `account` varchar(50) NOT NULL DEFAULT '' COMMENT '账户号',
  `session_code` varchar(32) NOT NULL DEFAULT '' COMMENT '会话标识',
  `expire_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '失效时间',
  `channel` varchar(50) DEFAULT '' COMMENT '渠道: APP、MANAGER',
  `del_flag` int(2) DEFAULT '1' COMMENT '0:删除  1:正常',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_session_code` (`session_code`),
  KEY `idx_account_id` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4152 DEFAULT CHARSET=utf8 COMMENT='会话记录';

-- ----------------------------
-- Table structure for sy_menu
-- ----------------------------
DROP TABLE IF EXISTS `sy_menu`;
CREATE TABLE `sy_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级ID',
  `path` varchar(50) NOT NULL DEFAULT '' COMMENT '路径',
  `m_order` int(2) NOT NULL DEFAULT '0' COMMENT '排序',
  `component` varchar(50) DEFAULT NULL COMMENT '组件',
  `icon` varchar(20) DEFAULT NULL COMMENT '图标',
  `redirect` varchar(10) DEFAULT NULL COMMENT '跳转',
  `always_show` tinyint(1) DEFAULT NULL COMMENT '展示',
  `del_flag` int(2) DEFAULT '1' COMMENT '0:删除  1:正常',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='菜单';

-- ----------------------------
-- Table structure for sy_role
-- ----------------------------
DROP TABLE IF EXISTS `sy_role`;
CREATE TABLE `sy_role` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `role_name` varchar(255) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_desc` varchar(255) DEFAULT '' COMMENT '角色描述',
  `del_flag` int(2) DEFAULT '1' COMMENT '0:删除  1:正常',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '更新人',
  `role_type` int(2) NOT NULL DEFAULT '0' COMMENT '角色类型：0普通角色，1管理员，2PC端默认角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Table structure for sy_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sy_role_menu`;
CREATE TABLE `sy_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '角色id',
  `menu_id` int(11) NOT NULL DEFAULT '0' COMMENT '菜单id',
  `del_flag` int(2) DEFAULT '1' COMMENT '0:删除  1:正常',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1249 DEFAULT CHARSET=utf8 COMMENT='角色菜单';

-- ----------------------------
-- Table structure for t_apartment
-- ----------------------------
DROP TABLE IF EXISTS `t_apartment`;
CREATE TABLE `t_apartment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `number` varchar(32) NOT NULL COMMENT '公寓编号',
  `address_ridgepole` varchar(32) DEFAULT NULL COMMENT '详细地址-栋',
  `address_number` varchar(32) DEFAULT NULL COMMENT '详细地址-号',
  `address_room` varchar(32) DEFAULT NULL COMMENT '详细地址-房间号',
  `residential_area_name` varchar(255) DEFAULT NULL COMMENT '小区名称',
  `area` decimal(10,2) DEFAULT NULL COMMENT '公寓面积',
  `house_type` int(11) NOT NULL COMMENT '户型字典id，外键，关联数据字典表t_dict_info',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '公寓状态（1已出租，0待出租）',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_apartment_apply
-- ----------------------------
DROP TABLE IF EXISTS `t_apartment_apply`;
CREATE TABLE `t_apartment_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `company_origin_id` int(11) NOT NULL COMMENT '单位id',
  `name` varchar(32) NOT NULL COMMENT '个人姓名',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `nationality` varchar(32) DEFAULT NULL COMMENT '国籍',
  `id_number` varchar(255) DEFAULT NULL COMMENT '证件号',
  `house_type` int(11) NOT NULL DEFAULT '11' COMMENT '租房类型,外键,关联t_dict_info',
  `apply_date` datetime DEFAULT NULL COMMENT '申请日期',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '申请状态：-1：审核不通过、0：未提审、2：复核、3：湾区分管审核、4：镇分管审核、5：审核通过）默认0',
  `contract_begin_date` date DEFAULT NULL COMMENT '合同开始日期',
  `contract_end_date` date DEFAULT NULL COMMENT '合同到期日期',
  `contract_status` int(2) DEFAULT NULL COMMENT '合同状态（0：合同执行中:-1提前解约，-2合同到期）',
  `apartment_id` int(11) DEFAULT NULL COMMENT '房子id',
  `description` varchar(1024) DEFAULT NULL COMMENT '备注',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_apartment_review
-- ----------------------------
DROP TABLE IF EXISTS `t_apartment_review`;
CREATE TABLE `t_apartment_review` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `apply_id` int(11) NOT NULL COMMENT '公寓申请id',
  `stage` int(2) NOT NULL COMMENT '审核阶段（1：复核、2：湾区分管审核、3:湾区总经理审核、4：镇分管审核）',
  `is_pass` int(2) NOT NULL COMMENT '是否通过(1：通过，0不通过）',
  `reviewer` varchar(32) NOT NULL COMMENT '审核人',
  `review_time` datetime DEFAULT NULL COMMENT '审核时间',
  `review_opinion` varchar(512) DEFAULT NULL COMMENT '审核意见',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_cdate
-- ----------------------------
DROP TABLE IF EXISTS `t_cdate`;
CREATE TABLE `t_cdate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_company
-- ----------------------------
DROP TABLE IF EXISTS `t_company`;
CREATE TABLE `t_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公司id',
  `credit_recognition_id` varchar(255) NOT NULL COMMENT '企业识别编码',
  `company_name` varchar(255) NOT NULL,
  `total_number` int(11) NOT NULL COMMENT '最大申请总数',
  `del_flag` int(2) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_contract
-- ----------------------------
DROP TABLE IF EXISTS `t_contract`;
CREATE TABLE `t_contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_id` int(11) NOT NULL COMMENT '项目id，外键，关联项目表',
  `contract_num` varchar(255) DEFAULT NULL COMMENT '合同编号',
  `contract_name` varchar(255) DEFAULT NULL COMMENT '合同名称',
  `contract_type` int(11) DEFAULT NULL COMMENT '合同类型，关联数据字典表t_dict_info',
  `first_party` varchar(255) DEFAULT NULL COMMENT '甲方',
  `second_party` varchar(255) DEFAULT NULL COMMENT '乙方',
  `sign_time` date DEFAULT NULL COMMENT '合同签订时间',
  `sign_person` varchar(32) DEFAULT NULL COMMENT '签订人',
  `begin_time` date DEFAULT NULL COMMENT '开始时间',
  `end_time` date DEFAULT NULL COMMENT '结束时间',
  `status` int(11) DEFAULT NULL COMMENT '合同状态',
  `total_money` decimal(14,2) DEFAULT NULL COMMENT '合同总金额（￥）',
  `final_money` decimal(14,2) DEFAULT NULL COMMENT '最终结算价(￥）',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_contract_node
-- ----------------------------
DROP TABLE IF EXISTS `t_contract_node`;
CREATE TABLE `t_contract_node` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `contract_id` int(11) NOT NULL COMMENT '合同id，外键，关联合同表',
  `payment_node` varchar(255) DEFAULT NULL COMMENT '付款节点名称',
  `payment_money` decimal(14,2) DEFAULT NULL COMMENT '付款金额（￥）',
  `payment_time` datetime DEFAULT NULL COMMENT '付款时间',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_dict_info
-- ----------------------------
DROP TABLE IF EXISTS `t_dict_info`;
CREATE TABLE `t_dict_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0删除）',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `dict_type_id` int(11) NOT NULL COMMENT '字典id',
  `sort_order` decimal(10,2) DEFAULT NULL COMMENT '排序值',
  `status` int(2) DEFAULT '1' COMMENT '状态(1启用 -1禁用)',
  `title` varchar(255) DEFAULT NULL COMMENT '字典数据名',
  `value` varchar(255) NOT NULL COMMENT '字典数据值',
  `is_sys` int(2) NOT NULL DEFAULT '0' COMMENT '字典是否被系统使用(0未被使用，1被使用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `t_dict_type`;
CREATE TABLE `t_dict_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0删除）',
  `update_user` varchar(32) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `description` varchar(255) DEFAULT NULL COMMENT '字典描述',
  `title` varchar(255) DEFAULT NULL COMMENT '字典名称',
  `type` varchar(255) NOT NULL COMMENT '字典type,唯一',
  `sort_order` decimal(10,2) DEFAULT NULL COMMENT '排序值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_guide
-- ----------------------------
DROP TABLE IF EXISTS `t_guide`;
CREATE TABLE `t_guide` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) NOT NULL COMMENT '讲解员姓名',
  `phone` varchar(32) DEFAULT NULL COMMENT '电话',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_meeting
-- ----------------------------
DROP TABLE IF EXISTS `t_meeting`;
CREATE TABLE `t_meeting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) NOT NULL COMMENT '会议室名称',
  `type` int(11) NOT NULL COMMENT '会议室类型（外键，关联数据字典表t_type_info）',
  `position` varchar(255) DEFAULT NULL COMMENT '会议室位置',
  `area` decimal(10,2) DEFAULT NULL COMMENT '会议室面积',
  `seat_num` int(11) DEFAULT NULL COMMENT '最大座位数',
  `am_begin_time` varchar(20) NOT NULL COMMENT '上午开放时间',
  `am_end_time` varchar(20) NOT NULL COMMENT '上午关闭时间',
  `pm_begin_time` varchar(20) NOT NULL COMMENT '下午开放时间',
  `pm_end_time` varchar(20) NOT NULL COMMENT '下午关闭时间',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_meeting_booking
-- ----------------------------
DROP TABLE IF EXISTS `t_meeting_booking`;
CREATE TABLE `t_meeting_booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account_id` varchar(32) NOT NULL COMMENT '账号id',
  `name` varchar(255) DEFAULT NULL COMMENT '会议主题',
  `meeting_id` int(11) NOT NULL COMMENT '外键，会议室id（关联会议室表t_meeting）',
  `visit_date` date NOT NULL COMMENT '预定日期',
  `begin_time` varchar(20) NOT NULL COMMENT '开始时间',
  `end_time` varchar(20) NOT NULL COMMENT '结束时间',
  `booking_number` int(11) DEFAULT NULL COMMENT '参会人数',
  `company_type_id` int(11) DEFAULT NULL COMMENT '单位类型id，外键，关联数据字典表t_dict_info',
  `booking_persion` varchar(32) DEFAULT NULL COMMENT '预约人',
  `booking_phone` varchar(32) DEFAULT NULL COMMENT '预约人电话',
  `enclosures` varchar(1024) DEFAULT NULL,
  `put_requirement_pic_id` varchar(1024) DEFAULT NULL COMMENT '路演厅摆放要求图片id',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '状态(-2:取消，-1:未到访,0:预约中,1:到访)',
  `passageway` int(2) DEFAULT NULL COMMENT '预约通道（0:小程序，1:web端）',
  `cancel_channel` int(2) DEFAULT NULL COMMENT '取消通道（0:小程序，1:web端）',
  `cancel_reason_id` int(11) DEFAULT NULL COMMENT '取消原因id，外键，关联数据字典表t_dict_info',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
  `actual_num` int(11) DEFAULT NULL COMMENT '实到人数',
  `appraise` int(2) DEFAULT NULL COMMENT '评价',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_meeting_enclosure
-- ----------------------------
DROP TABLE IF EXISTS `t_meeting_enclosure`;
CREATE TABLE `t_meeting_enclosure` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `meeting_id` int(11) DEFAULT NULL,
  `name` varchar(50) NOT NULL COMMENT '附件名',
  `num` int(11) NOT NULL COMMENT '数量',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_node_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_node_dict`;
CREATE TABLE `t_node_dict` (
  `id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'id',
  `node_name` varchar(255) NOT NULL COMMENT '节点名称',
  `stage_dict_id` int(11) NOT NULL COMMENT '流程字典id，关联t_node_dict',
  `sort_value` int(11) NOT NULL COMMENT '排序值',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_project
-- ----------------------------
DROP TABLE IF EXISTS `t_project`;
CREATE TABLE `t_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `number` varchar(255) NOT NULL COMMENT '项目编号',
  `name` varchar(255) NOT NULL COMMENT '项目名称',
  `project_type` int(2) NOT NULL COMMENT '项目类型（0：工程项目，1：规划调整，2：土地前期出让，3：项目前期报建）',
  `person` varchar(32) DEFAULT NULL COMMENT '项目负责人',
  `phone` varchar(32) DEFAULT NULL COMMENT '联系电话',
  `begin_time` date DEFAULT NULL COMMENT '开始时间',
  `end_time` date DEFAULT NULL COMMENT '结束时间',
  `stage_id` int(11) DEFAULT NULL COMMENT '项目当前阶段id，关联流程字典表t_stage_dict',
  `stage_node_id` int(11) DEFAULT NULL COMMENT '当前阶段节点id,关联流程节点字典表t_node_dict',
  `total_investment` decimal(14,2) DEFAULT NULL COMMENT '项目总投资额',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_project_material
-- ----------------------------
DROP TABLE IF EXISTS `t_project_material`;
CREATE TABLE `t_project_material` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_id` int(11) unsigned NOT NULL COMMENT '项目id，外键，关联项目表t_project ',
  `stage_id` int(11) DEFAULT NULL COMMENT '项目阶段字典id，外键，关联字典表t_stage_dict',
  `stage_node_id` int(11) DEFAULT NULL COMMENT '阶段节点字典id，外键，关联字典表t_node_dict',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_relation`;
CREATE TABLE `t_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` varchar(50) NOT NULL COMMENT '类型 （meeting、meetingBooking、showroomBooking)',
  `original_id` int(11) NOT NULL COMMENT '原信息id',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `file_url` varchar(1024) NOT NULL COMMENT '关联文件id',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1857 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_setting
-- ----------------------------
DROP TABLE IF EXISTS `t_setting`;
CREATE TABLE `t_setting` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `module` varchar(32) NOT NULL,
  `value` varchar(255) NOT NULL COMMENT '配置值',
  `desc` varchar(255) DEFAULT NULL,
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_showroom
-- ----------------------------
DROP TABLE IF EXISTS `t_showroom`;
CREATE TABLE `t_showroom` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) NOT NULL COMMENT '展厅名称',
  `position` varchar(255) DEFAULT NULL COMMENT '展厅位置',
  `area` decimal(10,2) DEFAULT NULL COMMENT '展厅面积',
  `seat_num` int(11) DEFAULT NULL COMMENT '最大座位数',
  `am_begin_time` varchar(20) NOT NULL COMMENT '上午开放时间',
  `am_end_time` varchar(20) NOT NULL COMMENT '上午关闭时间',
  `pm_begin_time` varchar(20) NOT NULL COMMENT '下午开放时间',
  `pm_end_time` varchar(20) NOT NULL COMMENT '下午关闭时间',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_showroom_booking
-- ----------------------------
DROP TABLE IF EXISTS `t_showroom_booking`;
CREATE TABLE `t_showroom_booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account_id` varchar(32) NOT NULL COMMENT '账号id',
  `showroom_id` int(11) NOT NULL COMMENT '展厅id,外键，关联t_showroom',
  `visit_date` date NOT NULL COMMENT '参观日期',
  `begin_time` varchar(20) NOT NULL COMMENT '开始时间',
  `end_time` varchar(20) NOT NULL COMMENT '结束时间',
  `booking_number` int(11) DEFAULT NULL COMMENT '参观人数',
  `company_type_id` int(11) DEFAULT NULL COMMENT '单位类型id，外键，关联数据字典表t_dict_info',
  `booking_persion` varchar(32) DEFAULT NULL COMMENT '预约人',
  `booking_phone` varchar(32) DEFAULT NULL COMMENT '预约人电话',
  `guide_id` int(11) DEFAULT NULL COMMENT '讲解员id，外键，关联讲解员表t_guide',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '状态(-2:取消，-1:未到访,0:预约中,1:到访)',
  `passageway` int(2) DEFAULT NULL COMMENT '预约通道（0:小程序，1:web端）',
  `cancel_channel` int(2) DEFAULT NULL COMMENT '取消通道（0:小程序，1:web端）',
  `cancel_reason_id` int(11) DEFAULT NULL COMMENT '取消原因id，外键，关联数据字典表t_dict_info',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
  `actual_num` int(11) DEFAULT NULL COMMENT '实到人数',
  `appraise` int(2) DEFAULT NULL COMMENT '评价',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_stage_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_stage_dict`;
CREATE TABLE `t_stage_dict` (
  `id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_type` int(2) NOT NULL COMMENT '项目类型（0：工程项目，1：规划调整，2：土地前期出让，3：项目前期报建）',
  `stage_type` int(2) NOT NULL DEFAULT '0' COMMENT '阶段类型（0不可自定义节点，1可自定义节点如施工阶段）',
  `stage_name` varchar(255) NOT NULL COMMENT '阶段名称',
  `sort_value` int(11) NOT NULL COMMENT '排序值',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_stage_node_info
-- ----------------------------
DROP TABLE IF EXISTS `t_stage_node_info`;
CREATE TABLE `t_stage_node_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_id` int(11) NOT NULL COMMENT '项目id，外键，关联项目表',
  `stage_id` int(11) NOT NULL COMMENT '阶段id，外键，关联t_stage_dict',
  `stage_node_id` int(11) DEFAULT NULL COMMENT '非自定义节点id，外键，关联t_node_dict',
  `node_name` varchar(255) DEFAULT NULL COMMENT '自定义节点名称',
  `type` int(2) NOT NULL COMMENT '分类（0阶段，1非自定义节点，2自定义及节点）',
  `person` varchar(32) DEFAULT NULL COMMENT '负责人',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=253 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_visit_company
-- ----------------------------
DROP TABLE IF EXISTS `t_visit_company`;
CREATE TABLE `t_visit_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT '企业名（中文/英文）',
  `type` int(2) NOT NULL COMMENT '预约类型，0会议室预约，1展厅预约',
  `booking_id` int(11) NOT NULL COMMENT '预约id',
  `del_flag` int(2) DEFAULT '1' COMMENT '删除标记（1：正常，0：删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT 'system' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(32) DEFAULT 'system' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;
