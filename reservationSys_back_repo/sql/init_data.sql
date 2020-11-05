
-- ----------------------------
-- 初始账号：13738428021，密码：qq123456
-- ----------------------------
INSERT INTO `sy_account` VALUES ('4e9e0c02787244c5bfc8b5521ba06d7a', 'system', '13738428021', 'd0dcbf0d12a6b1e7fbfa2ce5848f3eff', '1', '2019-03-20 10:52:41', 'system', '2020-09-08 11:10:14', 'system');

-- ----------------------------
-- 初始角色
-- ----------------------------
INSERT INTO `sy_role` VALUES ('8ef71efdd02c44888b2957de6ab9cd11', 'PC端默认角色', 'PC端默认权限，默认数据无法删除', '1', '2020-09-22 11:09:00', 'system', '2020-09-23 16:54:27', 'system', '2');
INSERT INTO `sy_role` VALUES ('cfc14f21c3c84a55852a061a7e866484', '管理员', '管理员', '1', '2020-09-04 10:23:03', 'system', '2020-09-22 11:11:59', 'system', '1');

-- ----------------------------
-- 初始账号角色
-- ----------------------------
INSERT INTO `sy_account_role` VALUES ('221', '4e9e0c02787244c5bfc8b5521ba06d7a', 'cfc14f21c3c84a55852a061a7e866484', '1', '2020-09-05 14:07:26', 'system', '2020-09-05 14:07:26', 'system');

-- ----------------------------
-- 初始菜单
-- ----------------------------
INSERT INTO `sy_menu` VALUES ('1', '首页', '0', '/', '0', null, null, null, null, '1', '2020-08-28 10:48:14', 'system', '2020-09-23 14:42:28', 'system');
INSERT INTO `sy_menu` VALUES ('2', '基础功能', '0', '/base', '6', null, 'el-icon-menu', null, null, '1', '2020-09-04 10:20:07', 'system', '2020-09-11 13:51:37', 'system');
INSERT INTO `sy_menu` VALUES ('3', '账号列表', '2', 'account-list', '1', null, null, null, null, '1', '2020-09-04 10:20:54', 'system', '2020-09-08 10:31:49', 'system');
INSERT INTO `sy_menu` VALUES ('4', '角色列表', '2', 'role-list', '2', null, null, null, null, '1', '2020-09-04 10:21:18', 'system', '2020-09-08 10:31:51', 'system');
INSERT INTO `sy_menu` VALUES ('5', '数据字典', '2', 'dict', '3', null, null, null, null, '1', '2020-09-04 10:21:36', 'system', '2020-09-08 10:31:53', 'system');
INSERT INTO `sy_menu` VALUES ('10', '展厅参观预约', '0', '/hall', '2', null, 'el-icon-date', null, null, '1', '2020-08-28 10:48:53', 'system', '2020-09-11 13:51:28', 'system');
INSERT INTO `sy_menu` VALUES ('11', '展厅列表', '10', 'hall-list', '3', null, null, null, null, '1', '2020-08-28 10:49:10', 'system', '2020-09-08 10:28:40', 'system');
INSERT INTO `sy_menu` VALUES ('12', '讲解员列表', '10', 'guide-list', '4', null, null, null, null, '1', '2020-08-28 10:49:29', 'system', '2020-09-08 10:28:44', 'system');
INSERT INTO `sy_menu` VALUES ('13', '展厅预约', '10', 'reservate', '1', null, null, null, null, '1', '2020-08-28 10:49:48', 'system', '2020-09-08 10:28:32', 'system');
INSERT INTO `sy_menu` VALUES ('14', '展厅预约列表', '10', 'reservation-list', '2', null, null, null, null, '1', '2020-08-28 10:49:58', 'system', '2020-09-08 10:28:38', 'system');
INSERT INTO `sy_menu` VALUES ('15', '统计分析', '10', 'analysis', '5', null, null, null, null, '1', '2020-09-04 10:19:25', 'system', '2020-09-08 10:28:49', 'system');
INSERT INTO `sy_menu` VALUES ('20', '会议室预约', '0', '/meeting', '3', null, 'el-icon-date', null, null, '1', '2020-08-28 10:50:16', 'system', '2020-09-11 13:51:26', 'system');
INSERT INTO `sy_menu` VALUES ('21', '会议室列表', '20', 'roomlist', '4', null, null, null, null, '1', '2020-08-28 10:50:36', 'system', '2020-09-08 10:29:49', 'system');
INSERT INTO `sy_menu` VALUES ('22', '会议室预约', '20', 'reservate', '1', null, null, null, null, '1', '2020-08-28 10:50:52', 'system', '2020-09-08 10:29:22', 'system');
INSERT INTO `sy_menu` VALUES ('23', '路演厅预约', '20', 'reservate/perform-hall', '2', null, null, null, null, '1', '2020-08-28 10:18:37', 'system', '2020-09-08 10:29:28', 'system');
INSERT INTO `sy_menu` VALUES ('24', '预约列表', '20', 'reservation-list', '3', null, null, null, null, '1', '2020-08-28 10:19:01', 'system', '2020-09-08 10:29:31', 'system');
INSERT INTO `sy_menu` VALUES ('25', '统计分析', '20', 'analysis', '5', null, null, null, null, '1', '2020-09-04 10:13:48', 'system', '2020-09-08 10:29:43', 'system');
INSERT INTO `sy_menu` VALUES ('30', '人才公寓', '0', '/apartment', '4', null, 'el-icon-school', null, null, '1', '2020-08-28 10:19:20', 'system', '2020-09-11 13:51:51', 'system');
INSERT INTO `sy_menu` VALUES ('31', '公寓列表', '30', 'apartment-list', '10', null, null, null, null, '1', '2020-08-28 10:19:28', 'system', '2020-09-08 10:31:14', 'system');
INSERT INTO `sy_menu` VALUES ('32', '公司列表', '30', 'company-list', '11', null, null, null, null, '1', '2020-08-28 10:20:12', 'system', '2020-09-08 10:31:17', 'system');
INSERT INTO `sy_menu` VALUES ('33', '申请列表', '30', 'applylist', '1', null, null, null, null, '1', '2020-08-28 10:20:29', 'system', '2020-09-08 10:30:38', 'system');
INSERT INTO `sy_menu` VALUES ('36', '复核列表', '30', 'reviewlist/self', '4', null, null, null, null, '1', '2020-08-28 10:21:15', 'system', '2020-09-27 09:51:24', 'system');
INSERT INTO `sy_menu` VALUES ('38', '湾区分管审核列表', '30', 'reviewlist/apartment', '6', null, null, null, null, '1', '2020-08-28 10:22:14', 'system', '2020-09-27 09:51:20', 'system');
INSERT INTO `sy_menu` VALUES ('39', '湾区总经理审核列表', '30', 'reviewlist/company', '7', null, null, null, null, '1', '2020-09-25 17:49:35', 'system', '2020-09-27 09:51:10', 'system');
INSERT INTO `sy_menu` VALUES ('40', '镇分管审核列表', '30', 'reviewlist/leader', '8', null, null, null, null, '1', '2020-08-28 10:22:44', 'system', '2020-09-17 10:21:22', 'system');
INSERT INTO `sy_menu` VALUES ('50', '规划建设部', '0', '/plan', '5', null, 'el-icon-document', null, null, '1', '2020-08-28 10:23:23', 'system', '2020-09-11 13:51:50', 'system');
INSERT INTO `sy_menu` VALUES ('51', '工程项目', '50', 'project-list', '1', null, null, null, null, '1', '2020-08-28 10:25:43', 'system', '2020-09-08 10:31:35', 'system');
INSERT INTO `sy_menu` VALUES ('52', '规划调整', '50', 'planing-adjustment-list', '2', null, null, null, null, '1', '2020-08-28 10:25:51', 'system', '2020-09-08 10:31:36', 'system');
INSERT INTO `sy_menu` VALUES ('53', '土地前期出让', '50', 'land-transfer-list', '3', null, null, null, null, '1', '2020-08-28 10:26:30', 'system', '2020-09-08 10:31:37', 'system');
INSERT INTO `sy_menu` VALUES ('54', '项目前期报建', '50', 'early-construction-list', '4', null, null, null, null, '1', '2020-08-28 10:26:45', 'system', '2020-09-08 10:31:38', 'system');
INSERT INTO `sy_menu` VALUES ('55', '合同管理', '50', 'contract-manager', '5', null, null, null, null, '1', '2020-08-28 10:27:00', 'system', '2020-09-08 10:31:41', 'system');

-- ----------------------------
-- 初始角色菜单
-- ----------------------------
INSERT INTO `sy_role_menu` VALUES ('1039', 'cfc14f21c3c84a55852a061a7e866484', '1', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1040', 'cfc14f21c3c84a55852a061a7e866484', '2', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1041', 'cfc14f21c3c84a55852a061a7e866484', '4', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1042', 'cfc14f21c3c84a55852a061a7e866484', '5', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1043', 'cfc14f21c3c84a55852a061a7e866484', '3', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1044', 'cfc14f21c3c84a55852a061a7e866484', '10', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1045', 'cfc14f21c3c84a55852a061a7e866484', '11', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1046', 'cfc14f21c3c84a55852a061a7e866484', '12', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1047', 'cfc14f21c3c84a55852a061a7e866484', '13', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1048', 'cfc14f21c3c84a55852a061a7e866484', '14', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1049', 'cfc14f21c3c84a55852a061a7e866484', '15', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1050', 'cfc14f21c3c84a55852a061a7e866484', '20', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1051', 'cfc14f21c3c84a55852a061a7e866484', '23', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1052', 'cfc14f21c3c84a55852a061a7e866484', '25', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1053', 'cfc14f21c3c84a55852a061a7e866484', '24', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1054', 'cfc14f21c3c84a55852a061a7e866484', '22', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1055', 'cfc14f21c3c84a55852a061a7e866484', '21', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1056', 'cfc14f21c3c84a55852a061a7e866484', '30', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1057', 'cfc14f21c3c84a55852a061a7e866484', '33', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1059', 'cfc14f21c3c84a55852a061a7e866484', '40', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1061', 'cfc14f21c3c84a55852a061a7e866484', '38', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1063', 'cfc14f21c3c84a55852a061a7e866484', '36', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1066', 'cfc14f21c3c84a55852a061a7e866484', '32', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1067', 'cfc14f21c3c84a55852a061a7e866484', '31', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1068', 'cfc14f21c3c84a55852a061a7e866484', '50', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1069', 'cfc14f21c3c84a55852a061a7e866484', '51', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:02', 'system');
INSERT INTO `sy_role_menu` VALUES ('1070', 'cfc14f21c3c84a55852a061a7e866484', '52', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:03', 'system');
INSERT INTO `sy_role_menu` VALUES ('1071', 'cfc14f21c3c84a55852a061a7e866484', '53', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:03', 'system');
INSERT INTO `sy_role_menu` VALUES ('1072', 'cfc14f21c3c84a55852a061a7e866484', '54', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:03', 'system');
INSERT INTO `sy_role_menu` VALUES ('1073', 'cfc14f21c3c84a55852a061a7e866484', '55', '1', '2020-09-07 15:12:45', 'system', '2020-09-16 18:33:03', 'system');
INSERT INTO `sy_role_menu` VALUES ('1231', '8ef71efdd02c44888b2957de6ab9cd11', '13', '1', '2020-09-23 14:36:27', 'system', '2020-09-23 14:36:27', 'system');
INSERT INTO `sy_role_menu` VALUES ('1232', '8ef71efdd02c44888b2957de6ab9cd11', '14', '1', '2020-09-23 14:36:27', 'system', '2020-09-23 14:36:27', 'system');
INSERT INTO `sy_role_menu` VALUES ('1233', '8ef71efdd02c44888b2957de6ab9cd11', '22', '1', '2020-09-23 14:36:27', 'system', '2020-09-23 14:36:27', 'system');
INSERT INTO `sy_role_menu` VALUES ('1234', '8ef71efdd02c44888b2957de6ab9cd11', '23', '1', '2020-09-23 14:36:27', 'system', '2020-09-23 14:36:27', 'system');
INSERT INTO `sy_role_menu` VALUES ('1235', '8ef71efdd02c44888b2957de6ab9cd11', '24', '1', '2020-09-23 14:36:27', 'system', '2020-09-23 14:36:27', 'system');
INSERT INTO `sy_role_menu` VALUES ('1236', '8ef71efdd02c44888b2957de6ab9cd11', '10', '1', '2020-09-23 14:36:27', 'system', '2020-09-23 14:36:27', 'system');
INSERT INTO `sy_role_menu` VALUES ('1237', '8ef71efdd02c44888b2957de6ab9cd11', '20', '1', '2020-09-23 14:36:27', 'system', '2020-09-23 14:36:27', 'system');
INSERT INTO `sy_role_menu` VALUES ('1247', '8ef71efdd02c44888b2957de6ab9cd11', '1', '1', '2020-09-23 14:53:40', 'system', '2020-09-23 14:53:40', 'system');
INSERT INTO `sy_role_menu` VALUES ('1248', 'cfc14f21c3c84a55852a061a7e866484', '39', '1', '2020-09-25 17:50:27', 'system', '2020-09-25 17:50:43', 'system');

-- ----------------------------
-- 初始字典
-- ----------------------------
INSERT INTO `t_dict_type` VALUES ('1', 'system', '2018-12-03 10:30:38', '1', 'system', '2020-09-04 16:19:36', '', '会议室类型', 'meetingType', '1.00');
INSERT INTO `t_dict_type` VALUES ('2', 'system', '2020-08-11 12:07:14', '1', 'system', '2020-09-04 15:39:25', '', '公司类型', 'companyType', '2.00');
INSERT INTO `t_dict_type` VALUES ('3', 'system', '2020-08-11 12:12:50', '1', 'system', '2020-09-04 15:39:28', null, '取消原因', 'cancelReason', '3.00');
INSERT INTO `t_dict_type` VALUES ('4', 'system', '2020-08-17 19:41:19', '1', 'system', '2020-09-04 15:39:29', null, '公寓户型', 'apartmentType', '4.00');
INSERT INTO `t_dict_type` VALUES ('5', 'system', '2020-08-30 14:06:06', '1', 'system', '2020-09-04 15:39:32', '', '合同类型', 'contractType', '5.00');
INSERT INTO `t_dict_type` VALUES ('6', 'system', '2020-09-04 17:08:23', '1', 'system', '2020-09-04 17:08:23', null, '合同状态', 'contractStatus', '6.00');
INSERT INTO `t_dict_type` VALUES ('7', 'system', '2020-09-22 18:44:23', '1', 'system', '2020-09-03 18:44:44', null, '工程项目阶段', 'projectStage', '0.00');

-- ----------------------------
-- 初始字典数据
-- ----------------------------
INSERT INTO `t_dict_info` VALUES ('1', 'system', '2020-08-07 15:27:41', '1', 'system', '2020-09-04 15:47:19', '会议室类型-内部会议室', '1', '1.00', '1', '内部会议室', 'insideMeeting', '1');
INSERT INTO `t_dict_info` VALUES ('2', 'system', '2020-08-07 15:31:02', '1', 'system', '2020-09-04 17:18:30', '会议室类型-路演厅', '1', '4.00', '1', '路演厅', 'roadshow', '1');
INSERT INTO `t_dict_info` VALUES ('3', 'system', '2020-08-07 15:28:03', '1', 'system', '2020-09-04 17:18:28', '会议室类型-外部会议室', '1', '2.00', '1', '外部会议室', 'externalMeeting', '0');
INSERT INTO `t_dict_info` VALUES ('4', 'system', '2020-08-07 15:28:54', '1', 'system', '2020-09-04 17:18:26', '会议室类型-外部VIP室', '1', '3.00', '1', '外部VIP室', 'externalVIP', '0');
INSERT INTO `t_dict_info` VALUES ('6', 'system', '2020-08-16 16:19:24', '1', 'system', '2020-09-13 11:04:48', '单位类型-公司', '2', '1.00', '1', '公司', 'company', '0');
INSERT INTO `t_dict_info` VALUES ('7', 'system', '2020-08-16 16:21:04', '1', 'system', '2020-08-16 16:21:04', '单位类型-政府单位', '2', '2.00', '1', '政府单位', 'governmentOrgan', '0');
INSERT INTO `t_dict_info` VALUES ('8', 'system', '2020-08-16 16:21:46', '1', 'system', '2020-08-16 16:21:46', '单位类型-个人', '2', '3.00', '1', '个人', 'personal', '0');
INSERT INTO `t_dict_info` VALUES ('9', 'system', '2020-08-17 14:39:01', '1', 'system', '2020-08-17 14:40:28', '取消原因-行程冲突', '3', '1.00', '1', '行程冲突', 'itineraryConflict', '0');
INSERT INTO `t_dict_info` VALUES ('10', 'system', '2020-08-17 14:39:31', '1', 'system', '2020-08-17 14:39:38', '取消原因-其它', '3', '2.00', '1', '其它', 'other', '0');
INSERT INTO `t_dict_info` VALUES ('11', 'system', '2020-08-17 19:42:34', '1', 'system', '2020-08-17 19:42:34', '公寓户型-一居室', '4', '1.00', '1', '一居室', 'oneBedroom', '0');
INSERT INTO `t_dict_info` VALUES ('12', 'system', '2020-08-17 19:43:18', '1', 'system', '2020-08-17 19:43:18', '公寓户型-二居室', '4', '2.00', '1', '二居室', 'twoBedroom', '0');
INSERT INTO `t_dict_info` VALUES ('13', 'system', '2020-08-17 19:43:54', '1', 'system', '2020-08-17 19:43:54', '公寓户型-三居室', '4', '3.00', '1', '三居室', 'threeBedroom', '0');
INSERT INTO `t_dict_info` VALUES ('14', 'system', '2020-08-30 14:16:54', '1', 'system', '2020-09-04 15:33:31', '合同类型-合同类型一', '5', '0.00', '1', '合同类型一', 'contractTypeOne', '0');
INSERT INTO `t_dict_info` VALUES ('15', 'system', '2020-09-04 15:34:00', '1', 'system', '2020-09-05 09:36:35', '合同类型-合同类型二', '5', '1.00', '1', '合同类型二', 'contractTypeTwo', '0');
INSERT INTO `t_dict_info` VALUES ('16', 'system', '2020-09-04 17:09:02', '1', 'system', '2020-09-04 17:13:29', '合同状态-作废', '6', '1.00', '1', '作废', 'cancel', '0');
INSERT INTO `t_dict_info` VALUES ('17', 'system', '2020-09-04 17:10:12', '1', 'system', '2020-09-04 17:13:52', '合同状态-中止', '6', '2.00', '1', '中止', 'suspension', '0');
INSERT INTO `t_dict_info` VALUES ('18', 'system', '2020-09-04 17:13:20', '1', 'system', '2020-09-04 17:14:27', '合同状态-执行中', '6', '3.00', '1', '执行中', 'inExecution', '0');
INSERT INTO `t_dict_info` VALUES ('19', 'system', '2020-09-04 17:14:59', '1', 'system', '2020-09-22 16:27:27', '合同状态-完成', '6', '4.00', '1', '完成', 'over', '1');
INSERT INTO `t_dict_info` VALUES ('26', 'system', '2020-09-05 09:47:08', '1', 'system', '2020-09-05 09:47:28', '合同类型-合同类型三', '5', '0.00', '0', '合同类型三', 'contractTypeThree', '0');
INSERT INTO `t_dict_info` VALUES ('27', 'system', '2020-09-16 10:53:19', '1', 'system', '2020-09-16 10:53:19', '123', '4', '4.00', '0', '四居室', 'fourroom', '0');
INSERT INTO `t_dict_info` VALUES ('28', 'system', '2020-09-22 18:48:59', '1', 'system', '2020-09-22 18:53:11', null, '7', '1.00', '1', '设计方案', 'pro_stage_1', '0');
INSERT INTO `t_dict_info` VALUES ('29', 'system', '2020-09-22 18:49:20', '1', 'system', '2020-09-22 18:53:13', null, '7', '2.00', '1', '上会', 'pro_stage_2', '0');
INSERT INTO `t_dict_info` VALUES ('30', 'system', '2020-09-22 18:49:39', '1', 'system', '2020-09-22 18:53:16', null, '7', '3.00', '1', '立项', 'pro_stage_3', '0');
INSERT INTO `t_dict_info` VALUES ('31', 'system', '2020-09-22 18:49:58', '1', 'system', '2020-09-22 18:53:18', null, '7', '4.00', '1', '招标投', 'pro_stage_4', '0');
INSERT INTO `t_dict_info` VALUES ('32', 'system', '2020-09-22 18:50:22', '1', 'system', '2020-09-22 18:53:21', null, '7', '5.00', '1', '合同签订', 'pro_stage_5', '0');
INSERT INTO `t_dict_info` VALUES ('33', 'system', '2020-09-22 18:50:37', '1', 'system', '2020-09-22 18:53:23', null, '7', '6.00', '1', '施工', 'pro_stage_6', '0');
INSERT INTO `t_dict_info` VALUES ('34', 'system', '2020-09-22 18:50:55', '1', 'system', '2020-09-22 18:53:26', null, '7', '7.00', '1', '竣工验收', 'pro_stage_7', '0');
INSERT INTO `t_dict_info` VALUES ('35', 'system', '2020-09-22 18:51:14', '1', 'system', '2020-09-22 18:53:29', null, '7', '8.00', '1', '结算', 'pro_stage_8', '0');

-- ----------------------------
-- 初始项目阶段字典
-- ----------------------------
INSERT INTO `t_stage_dict` VALUES ('00000000001', '0', '0', '设计方案', '1', '1', '2020-08-21 10:54:48', 'system', '2020-08-21 10:54:48', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000002', '0', '0', '上会', '2', '1', '2020-08-21 10:55:31', 'system', '2020-08-21 10:55:31', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000003', '0', '0', '立项', '3', '1', '2020-08-21 10:55:43', 'system', '2020-08-21 10:55:43', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000004', '0', '0', '招投标', '4', '1', '2020-08-21 10:56:27', 'system', '2020-08-21 10:56:27', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000005', '0', '0', '合同签订', '5', '1', '2020-08-21 10:56:42', 'system', '2020-08-21 10:56:42', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000006', '0', '1', '施工', '6', '1', '2020-08-21 10:56:55', 'system', '2020-08-21 16:18:16', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000007', '0', '0', '竣工验收', '7', '1', '2020-08-21 10:57:08', 'system', '2020-08-21 10:57:18', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000008', '0', '0', '结算', '8', '1', '2020-08-21 10:57:30', 'system', '2020-09-22 15:08:37', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000021', '1', '0', '前期研究阶段', '1', '1', '2020-08-21 10:58:45', 'system', '2020-08-21 11:08:51', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000022', '1', '0', '初步方案阶段', '2', '1', '2020-08-21 10:59:07', 'system', '2020-08-21 11:08:53', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000023', '1', '0', '报审阶段', '3', '1', '2020-08-21 10:59:22', 'system', '2020-08-21 11:08:54', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000031', '2', '0', '预储备阶段', '1', '1', '2020-08-21 11:26:16', 'system', '2020-08-21 14:03:56', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000032', '2', '0', '申请土地指标', '2', '1', '2020-08-21 11:27:11', 'system', '2020-08-21 14:03:58', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000033', '2', '0', '农转用手续', '3', '1', '2020-08-21 11:27:29', 'system', '2020-08-21 14:04:00', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000034', '2', '0', '地块招拍挂阶段', '4', '1', '2020-08-21 11:28:09', 'system', '2020-08-21 14:04:05', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000035', '2', '0', '土地挂牌阶段', '5', '1', '2020-08-21 11:28:28', 'system', '2020-08-21 14:04:07', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000036', '2', '0', '摘牌', '6', '1', '2020-08-21 11:28:39', 'system', '2020-08-21 14:04:09', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000051', '3', '0', '开工前报建审批', '1', '1', '2020-08-21 12:07:09', 'system', '2020-08-21 14:04:14', 'system');
INSERT INTO `t_stage_dict` VALUES ('00000000052', '3', '0', '基目综合验收', '2', '1', '2020-08-21 13:33:35', 'system', '2020-08-21 14:04:18', 'system');

-- ----------------------------
-- 初始阶段节点字典
-- ----------------------------
INSERT INTO `t_node_dict` VALUES ('00000000024', '项目建议书确认', '21', '1', '1', '2020-08-21 11:08:14', 'system', '2020-08-21 11:09:14', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000025', '规划资料收集', '21', '2', '1', '2020-08-21 11:09:56', 'system', '2020-08-21 11:09:56', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000026', '向市局申领《规划编制要素底版》', '21', '3', '1', '2020-08-21 11:11:55', 'system', '2020-08-21 11:11:55', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000027', '现状踏勘及资料收集', '21', '4', '1', '2020-08-21 11:12:24', 'system', '2020-08-21 11:12:24', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000028', '区镇部门沟通对接', '21', '5', '1', '2020-08-21 11:13:04', 'system', '2020-08-21 11:15:37', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000029', '评估报告编制', '21', '6', '1', '2020-08-21 11:13:29', 'system', '2020-08-21 11:13:29', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000030', '区级评估报告评审会', '21', '7', '1', '2020-08-21 11:14:17', 'system', '2020-08-21 11:14:39', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000031', '区镇相关部门沟通对接', '22', '1', '1', '2020-08-21 11:15:30', 'system', '2020-08-21 11:15:59', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000032', '区市局确认规划方案', '22', '2', '1', '2020-08-21 11:16:46', 'system', '2020-08-21 11:16:46', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000033', '上报任务书', '22', '3', '1', '2020-08-21 11:17:04', 'system', '2020-08-21 11:17:04', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000034', '任务书批复', '22', '4', '1', '2020-08-21 11:17:38', 'system', '2020-08-21 11:18:57', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000035', '任务书修改确认', '22', '5', '1', '2020-08-21 11:17:49', 'system', '2020-08-21 11:17:49', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000036', '编制初步方案', '22', '6', '1', '2020-08-21 11:18:46', 'system', '2020-08-21 11:18:46', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000037', '区级部门及专家评审方案', '22', '7', '1', '2020-08-21 11:19:35', 'system', '2020-08-21 11:20:00', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000038', '规划公示公告', '22', '8', '1', '2020-08-21 11:19:54', 'system', '2020-08-21 11:19:54', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000039', '区级部门及专家评审后修改方案', '23', '1', '1', '2020-08-21 11:21:21', 'system', '2020-08-21 11:21:24', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000040', '方案上报', '23', '2', '1', '2020-08-21 11:22:41', 'system', '2020-08-21 11:22:41', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000041', '区局市局认可', '23', '3', '1', '2020-08-21 11:23:00', 'system', '2020-08-21 11:23:00', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000042', '最终上报成果及上报技审', '23', '4', '1', '2020-08-21 11:24:45', 'system', '2020-08-21 11:24:45', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000043', '报市政府批准、同步备案', '23', '5', '1', '2020-08-21 11:25:04', 'system', '2020-08-21 11:25:04', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000057', '预评估会议', '31', '1', '1', '2020-08-21 11:31:09', 'system', '2020-08-21 14:14:07', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000058', '预储备批文', '31', '2', '1', '2020-08-21 11:31:28', 'system', '2020-08-21 14:14:11', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000059', '地块测绘', '31', '3', '1', '2020-08-21 11:31:43', 'system', '2020-08-21 14:14:10', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000060', '选址', '31', '4', '1', '2020-08-21 11:32:07', 'system', '2020-08-21 14:14:12', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000061', '征地包干手续', '31', '5', '1', '2020-08-21 11:32:21', 'system', '2020-08-21 14:14:09', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000062', '缴纳规费', '32', '1', '1', '2020-08-21 11:33:09', 'system', '2020-08-21 14:14:16', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000063', '缴纳指标费', '32', '2', '1', '2020-08-21 11:33:23', 'system', '2020-08-21 14:14:17', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000064', '一书四方案', '33', '1', '1', '2020-08-21 11:34:02', 'system', '2020-08-21 14:14:22', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000065', '财政缴纳新增费', '33', '2', '1', '2020-08-21 11:34:16', 'system', '2020-08-21 14:14:20', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000066', '地块储备', '34', '1', '1', '2020-08-21 11:41:22', 'system', '2020-08-21 14:14:24', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000067', '地块征询', '34', '2', '1', '2020-08-21 11:41:45', 'system', '2020-08-21 14:14:26', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000068', '带产业申报', '34', '3', '1', '2020-08-21 11:42:04', 'system', '2020-08-21 14:14:31', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000069', '地下水土壤检测', '34', '4', '1', '2020-08-21 11:42:30', 'system', '2020-08-21 14:14:32', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000070', '方案审核', '34', '5', '1', '2020-08-21 11:42:46', 'system', '2020-08-21 14:14:35', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000071', '区局审核', '34', '6', '1', '2020-08-21 11:43:03', 'system', '2020-08-21 14:14:37', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000072', '市局审核', '34', '7', '1', '2020-08-21 11:43:19', 'system', '2020-08-21 14:14:38', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000073', '公标期20天', '35', '1', '1', '2020-08-21 12:02:06', 'system', '2020-08-21 14:14:44', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000074', '提交竞买申请', '35', '2', '1', '2020-08-21 12:02:25', 'system', '2020-08-21 14:14:46', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000075', '交给竞买保证金', '35', '3', '1', '2020-08-21 12:03:06', 'system', '2020-08-21 14:14:48', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000076', '最终确认竞买资格', '35', '4', '1', '2020-08-21 12:03:23', 'system', '2020-08-21 14:14:52', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000077', '拍卖成交', '36', '1', '1', '2020-08-21 12:04:01', 'system', '2020-08-21 14:14:55', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000078', '签订成交确认书', '36', '2', '1', '2020-08-21 12:04:30', 'system', '2020-08-21 14:15:00', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000079', '签订土地出让合同', '36', '3', '1', '2020-08-21 12:04:47', 'system', '2020-08-21 14:14:57', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000093', '建设用地规划许可证', '51', '1', '1', '2020-08-21 13:36:22', 'system', '2020-08-21 14:15:08', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000094', '项目备案报建', '51', '2', '1', '2020-08-21 13:36:51', 'system', '2020-08-21 14:15:14', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000095', '环评报告-职业危害预评价报告审批', '51', '3', '1', '2020-08-21 13:37:23', 'system', '2020-08-21 14:15:18', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000096', '工程设计方案审核', '51', '4', '1', '2020-08-21 13:39:12', 'system', '2020-08-21 14:15:21', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000097', '工程规划许可证', '51', '5', '1', '2020-08-21 13:39:29', 'system', '2020-08-21 14:15:22', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000098', '验收前置条件等准备工作', '52', '1', '1', '2020-08-21 13:39:56', 'system', '2020-08-21 14:15:26', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000099', '申请验收', '52', '2', '1', '2020-08-21 13:40:19', 'system', '2020-08-21 14:15:27', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000100', '资料审核', '52', '3', '1', '2020-08-21 13:40:33', 'system', '2020-08-21 14:15:28', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000101', '规土/环保/消防/卫生/绿化/防雷等验收', '52', '4', '1', '2020-08-21 13:42:18', 'system', '2020-08-21 14:15:29', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000103', '验收意见', '52', '5', '1', '2020-08-21 13:42:36', 'system', '2020-08-21 14:15:34', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000104', '验收资料归档/档案验收', '52', '6', '1', '2020-08-21 13:43:00', 'system', '2020-08-21 14:15:30', 'system');
INSERT INTO `t_node_dict` VALUES ('00000000105', '申领房产证', '52', '7', '1', '2020-08-21 13:43:16', 'system', '2020-08-21 14:15:36', 'system');

-- ----------------------------
-- 初始设置
-- ----------------------------
INSERT INTO `t_setting` VALUES ('ALI_SMS_ACCESS_KEY_ID', 'ALI_SMS', 'LTAI4FhUd3TJUsVbXnGepZgL', '阿里云短信配置', '1', '2020-09-02 15:39:47', 'system', '2020-09-16 18:37:59', 'system');
INSERT INTO `t_setting` VALUES ('ALI_SMS_ACCESS_KEY_SECRET', 'ALI_SMS', 'dJbjZN7nemhnJBWdjA3JKsA7U34D7l', '阿里云短信配置', '1', '2020-09-02 15:44:34', 'system', '2020-09-16 18:38:11', 'system');
INSERT INTO `t_setting` VALUES ('ALI_SMS_MESS_TEMPLATE_CODE', 'ALI_SMS', 'SMS_201680154', '阿里云短信配置，消息通知模板', '1', '2020-09-03 20:00:01', 'system', '2020-09-16 18:38:45', 'system');
INSERT INTO `t_setting` VALUES ('ALI_SMS_SIGN_NAME', 'ALI_SMS', '上海湾区科创中心', '阿里云短信配置，短信签名', '1', '2020-09-05 17:35:26', 'system', '2020-09-16 18:38:23', 'system');
INSERT INTO `t_setting` VALUES ('ALI_SMS_TEMPLATE_CODE', 'ALI_SMS', 'SMS_201810008', '阿里云短信配置，验证码模板', '1', '2020-09-02 15:45:42', 'system', '2020-09-16 18:38:49', 'system');
INSERT INTO `t_setting` VALUES ('DEFAULT_GUIDE', 'GUIDE', '1', '默认讲解员id', '1', '2020-08-26 09:51:23', 'system', '2020-09-02 15:10:06', 'system');
INSERT INTO `t_setting` VALUES ('QINIU_OSS_ACCESSKEY', 'QINIU_OSS', '-Oa6lz6oazBF2aibyAF4_qSvh7P8XziW13HGeVzQ', '七牛云配置', '1', '2020-09-02 15:00:39', 'system', '2020-09-02 15:41:52', 'system');
INSERT INTO `t_setting` VALUES ('QINIU_OSS_BUCKET_PRIVATE', 'QINIU_OSS', 'cr-private', '七牛云配置', '1', '2020-09-02 15:03:18', 'system', '2020-09-02 15:42:02', 'system');
INSERT INTO `t_setting` VALUES ('QINIU_OSS_BUCKET_PUBLIC', 'QINIU_OSS', 'cr-public', '七牛云配置', '1', '2020-09-02 15:04:02', 'system', '2020-09-02 15:41:47', 'system');
INSERT INTO `t_setting` VALUES ('QINIU_OSS_ENDPOINT', 'QINIU_OSS', 'file.zhiduoduo.com.cn', '七牛云配置，私有空间', '1', '2020-09-03 14:42:50', 'system', '2020-09-03 15:19:36', 'system');
INSERT INTO `t_setting` VALUES ('QINIU_OSS_HTTP', 'QINIU_OSS', 'http://', '七牛云配置', '1', '2020-09-03 14:41:54', 'system', '2020-09-03 14:47:58', 'system');
INSERT INTO `t_setting` VALUES ('QINIU_OSS_HTTPS', 'QINIU_OSS', 'https://', '七牛云配置', '1', '2020-09-03 15:20:22', 'system', '2020-09-03 15:20:27', 'system');
INSERT INTO `t_setting` VALUES ('QINIU_OSS_PUBLIC_ENDPOINT', 'QINIU_OSS', 'static.zhiduoduo.com.cn', '七牛云配置，公共空间', '1', '2020-09-03 15:21:01', 'system', '2020-09-03 15:22:09', 'system');
INSERT INTO `t_setting` VALUES ('QINIU_OSS_SECRETKEY', 'QINIU_OSS', 'a8KwBberev1jilm35HFLrFOaa2uJma1n7XSpCRjz', '七牛云配置', '1', '2020-09-02 15:01:04', 'system', '2020-09-03 15:12:42', 'system');
INSERT INTO `t_setting` VALUES ('QINIU_OSS_ZONE', 'QINIU_OSS', '0', '七牛云配置，华东', '1', '2020-09-02 15:28:58', 'system', '2020-09-02 15:42:08', 'system');
