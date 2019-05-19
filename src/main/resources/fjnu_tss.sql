/*
 Navicat MySQL Data Transfer

 Source Server         : willim
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 127.0.0.1:3306
 Source Schema         : fjnu_tss

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 18/03/2019 13:09:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl`;
CREATE TABLE `sys_acl`  (
  `id` bigint(32) UNSIGNED NOT NULL COMMENT '权限id',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '权限名称',
  `acl_module_id` bigint(32) NOT NULL DEFAULT 0 COMMENT '权限所在的权限模块id',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '请求的url, 可以填正则表达式 ',
  `function_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '功能号',
  `type` int(11) NOT NULL DEFAULT 3 COMMENT '类型，0:系统 1:项目',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态，1：正常，0：冻结',
  `seq` int(11) NOT NULL DEFAULT 0 COMMENT '权限在当前模块下的顺序，由小到大',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_acl
-- ----------------------------
INSERT INTO `sys_acl` VALUES (1, 'A1', '新增', 4, '/project/projectType', 'AddProjectType', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (2, 'A2', '修改', 4, '/project/projectType', 'ModifyProjectType', 0, 1, 2, '');
INSERT INTO `sys_acl` VALUES (3, 'A3', '删除', 4, '/project/projectType', 'DeleteProjectType', 0, 1, 3, '');
INSERT INTO `sys_acl` VALUES (4, 'A4', '查看', 4, '/project/projectType', 'Search', 0, 1, 4, '');
INSERT INTO `sys_acl` VALUES (5, 'B1', '新增', 5, '/project/projectTemplate', 'AddProjectTemplate', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (6, 'B2', '修改', 5, '/project/projectTemplate', 'ModifyProjectTemplate', 0, 1, 2, '');
INSERT INTO `sys_acl` VALUES (7, 'B3', '删除', 5, '/project/projectTemplate', 'DeleteProjectTemplate', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (8, 'B4', '查看', 5, '/project/projectTemplate', 'SearchProjectTemplate', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (9, 'C1', '新增', 6, '/project/projectManagement', 'AddProject', 0, 1, 1, '参与人对当前项目没有新增');
INSERT INTO `sys_acl` VALUES (10, 'C2', '修改', 6, '/project/projectManagement', 'ModifyProject', 0, 1, 2, '');
INSERT INTO `sys_acl` VALUES (11, 'C3', '删除', 6, '/project/projectManagement', 'DeleteProject', 0, 1, 3, '');
INSERT INTO `sys_acl` VALUES (12, 'C4', '查看', 6, '/project/projectManagement', 'Search', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (17, 'D1', '新增', 8, '/system/roleManagement', 'AddRole', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (18, 'D2', '修改', 8, '/system/roleManagement', 'ModifyRole', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (19, 'D3', '删除', 8, '/system/roleManagement', 'DeleteRole', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (20, 'D4', '查看', 8, '/system/roleManagement', 'GetRoleList', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (22, 'E1', '修改', 9, '/system/schoolInfo', 'ModifySchoolInfo', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (24, 'E2', '查看', 9, '/system/schoolInfo', 'GetSchoolInfo', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (25, 'F1', '新增部门', 10, '/system/organization', 'AddDept', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (26, 'F2', '修改部门', 10, '/system/organization', 'ModifyDept', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (27, 'F3', '删除部门', 10, '/system/organization', 'DeleteDept', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (28, 'F4', '查看部门', 10, '/system/organization', 'GetDeptTree', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (29, 'G1', '新增用户', 10, '/user', 'AddPersonnel', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (30, 'G2', '修改用户', 10, '/user', 'ModifyPersonnel', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (31, 'G3', '删除用户', 10, '/user', 'DeletePersonnel', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (32, 'G4', '查看用户', 10, '/user', 'SearchPersonnelByCondition', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (33, 'G5', '用户状态', 10, '/user', 'ModifyUserStatus', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (34, 'G6', '重置密码', 10, '/user', 'ResetPassword', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (35, 'G7', '查看', 11, '/log', 'searchSysLog', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (36, 'H1', '文件查看', 7, '/project/fileManagement', 'SearchProjectFile', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (38, 'I1', '查看', 13, '/worktop/myMessage', 'GetMessageList', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (39, 'J1', '查看', 14, '/worktop/myProject', 'Search', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (40, 'J2', '修改', 14, '/worktop/myProject', 'ModifyProject', 0, 1, 2, '');
INSERT INTO `sys_acl` VALUES (41, 'J3', '删除', 14, '/worktop/myProject', 'DeleteProject', 0, 1, 3, '');
INSERT INTO `sys_acl` VALUES (42, 'K1', '查看', 0, '/project/projectManagement', 'GetProjectInfo', 1, 1, 0, '');
INSERT INTO `sys_acl` VALUES (43, 'K2', '评论', 0, '/project/messageManagement', 'AddComment', 1, 1, 0, '');
INSERT INTO `sys_acl` VALUES (45, 'K3', '上传', 0, '/project/fileManagement', 'NodeFileUpload', 1, 1, 0, '');
INSERT INTO `sys_acl` VALUES (47, 'K4', '导出', 0, '/project/projectManagement', 'GetNodeFileInfo', 1, 1, 0, '');
INSERT INTO `sys_acl` VALUES (48, 'L1', '添加节点', 0, '/project/nodeManagement', 'AddProjectNode', 2, 1, 0, '');
INSERT INTO `sys_acl` VALUES (50, 'L2', '删除节点', 0, '/project/nodeManagement', 'DeleteProjectNode', 2, 1, 0, '');
INSERT INTO `sys_acl` VALUES (51, 'L3', '修改节点', 0, '/project/nodeManagement', 'ModifyProjectNode', 2, 1, 0, '');
INSERT INTO `sys_acl` VALUES (52, 'L4', '修改节点树', 0, '/project/projectManagement', 'ModifyTree', 2, 1, 0, '');
INSERT INTO `sys_acl` VALUES (53, 'T1', '测试', 0, '/test', 'GetTest', 3, 1, 0, '');

-- ----------------------------
-- Table structure for sys_acl_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl_module`;
CREATE TABLE `sys_acl_module`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '权限模块id',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限模块code',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '权限模块名称',
  `parent_id` bigint(32) NOT NULL DEFAULT 0 COMMENT '上级权限模块id',
  `level` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '权限模块层级',
  `type` int(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0后台1前台',
  `seq` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '权限模块在当前层级下的顺序，由小到大',
  `status` int(4) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态，1：正常，0：冻结',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限模块表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_acl_module
-- ----------------------------
INSERT INTO `sys_acl_module` VALUES (1, '', '我的工作台', 0, '0', 0, 1, 1, 'product');
INSERT INTO `sys_acl_module` VALUES (2, '', '项目', 0, '0', 0, 2, 1, '');
INSERT INTO `sys_acl_module` VALUES (3, '', '系统', 0, '0', 0, 3, 1, '');
INSERT INTO `sys_acl_module` VALUES (4, '', '项目类型', 2, '0.2', 0, 1, 1, '');
INSERT INTO `sys_acl_module` VALUES (5, '', '项目模板', 2, '0.2', 0, 2, 1, '');
INSERT INTO `sys_acl_module` VALUES (6, '', '项目管理', 2, '0.2', 0, 3, 1, '');
INSERT INTO `sys_acl_module` VALUES (7, '', '文档管理', 2, '0.2', 0, 5, 1, '');
INSERT INTO `sys_acl_module` VALUES (8, '', '职务', 3, '0.3', 0, 1, 1, '');
INSERT INTO `sys_acl_module` VALUES (9, '', '学校', 3, '0.3', 0, 2, 1, '');
INSERT INTO `sys_acl_module` VALUES (10, '', '组织机构', 3, '0.3', 0, 3, 1, '');
INSERT INTO `sys_acl_module` VALUES (11, '', '系统日志', 3, '0.3', 0, 4, 1, '');
INSERT INTO `sys_acl_module` VALUES (13, '', '我的消息', 1, '0.1', 0, 1, 1, '');
INSERT INTO `sys_acl_module` VALUES (14, '', '我的项目', 1, '0.1', 0, 2, 1, '');

-- ----------------------------
-- Table structure for sys_admin_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_detail`;
CREATE TABLE `sys_admin_detail`  (
  `user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报名号',
  `real_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` tinyint(4) NULL DEFAULT NULL COMMENT '0女  1男',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `login_type` tinyint(4) NULL DEFAULT NULL COMMENT '0 ',
  `dept_id` bigint(32) UNSIGNED NOT NULL DEFAULT 0 COMMENT '管理员为1，用户为2',
  `id_card` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `unique`(`email`, `phone`, `id_card`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员详细信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_admin_detail
-- ----------------------------
INSERT INTO `sys_admin_detail` VALUES ('5c8201368127b02e9407f871', 'lwn', '529042209@qq.com', '15980587718', 1, '2019-03-08 13:44:22', '2019-03-08 13:44:21', NULL, 0, '350128199920000114');
INSERT INTO `sys_admin_detail` VALUES ('gys', NULL, NULL, NULL, NULL, '2019-03-08 21:34:44', '2019-03-08 21:34:47', NULL, 0, NULL);
INSERT INTO `sys_admin_detail` VALUES ('gys1', 'geyusha', '1562366526@qq.com', '18859152356', 1, '2019-03-08 20:34:56', '2019-03-08 21:37:22', NULL, 0, '140525199802079344');
INSERT INTO `sys_admin_detail` VALUES ('lth', 'lth', '1562366524@qq.com', '18859152357', 1, '2019-03-07 13:52:32', '2019-03-08 21:40:20', NULL, 0, '140525199802069344');

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` bigint(32) NOT NULL DEFAULT 0 COMMENT '上级部门id',
  `level` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '部门层级',
  `seq` int(11) NOT NULL DEFAULT 0 COMMENT '部门在当前层级下的顺序，由小到大',
  `type` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '部门来源0系统1外来',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department` VALUES (1, '一级部门', 0, '0', 1, 0, '一级部门', '2017-10-11 07:21:40', '2018-11-05 11:08:07');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '自增的id',
  `user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录ip',
  `operation_content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '操作内容',
  `operation_date_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '操作时间',
  `operation_result` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '操作结果',
  `reason` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '原因',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 'admin', '127.0.0.1', '添加用户 lth', '2019-03-10 23:03:09', '成功', 'q');
INSERT INTO `sys_log` VALUES (2, 'i', '', '', '2019-03-05 04:26:56', '', '');
INSERT INTO `sys_log` VALUES (3, 'ii', '', '', '2019-03-05 04:26:59', '', '');
INSERT INTO `sys_log` VALUES (4, 'ilhj', '', '', '2019-03-05 04:27:04', '', '');
INSERT INTO `sys_log` VALUES (5, 'hh', '', '', '2019-03-05 04:27:07', '', '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `operator` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('2', 'ceshi', '', '2019-03-06 03:55:11', '2019-03-07 11:35:54', 'lll');
INSERT INTO `sys_role` VALUES ('2019030516192556167', '再次测试', '测试', '2019-03-05 16:19:25', '2019-03-05 18:58:08', 'admin');
INSERT INTO `sys_role` VALUES ('20190305183957074_98', '测试35', '测试', '2019-03-05 18:39:57', '2019-03-05 18:39:57', 'admin');
INSERT INTO `sys_role` VALUES ('5c8214f38127b01038b3a075', '3.8 测试1', '3.8 测试1', '2019-03-08 15:08:35', '2019-03-08 16:05:06', 'admin');
INSERT INTO `sys_role` VALUES ('G1', '组员', '测试', '2019-03-03 16:24:06', '2019-03-05 16:24:23', NULL);
INSERT INTO `sys_role` VALUES ('K1', '教育技术科科长', '无', '2018-11-05 11:28:51', '2018-11-05 11:28:51', 'admin');
INSERT INTO `sys_role` VALUES ('K10', '一卡通', '无', '2018-11-05 10:32:15', '2018-11-05 10:32:15', 'admin');
INSERT INTO `sys_role` VALUES ('K2', '网络技术科科长', '无', '2018-11-05 10:15:59', '2018-11-05 10:15:59', 'admin');
INSERT INTO `sys_role` VALUES ('K3', '主任', '无', '2018-11-05 10:22:59', '2018-11-05 10:22:59', 'admin');
INSERT INTO `sys_role` VALUES ('K4', '办公室主任', '无', '2018-11-05 10:27:37', '2018-11-05 10:27:37', 'admin');
INSERT INTO `sys_role` VALUES ('K5', '信息资源科科长', '无', '2018-11-05 10:30:45', '2018-11-05 10:30:45', 'admin');
INSERT INTO `sys_role` VALUES ('K6', '一卡通负责人', '无', '2018-11-05 10:28:33', '2018-11-05 10:28:33', 'admin');
INSERT INTO `sys_role` VALUES ('K7', '网络技术科', '无', '2018-11-05 10:30:17', '2018-11-05 10:30:17', 'admin');
INSERT INTO `sys_role` VALUES ('K8', '教育技术科', '无', '2018-11-05 10:30:51', '2018-11-05 10:30:51', 'admin');
INSERT INTO `sys_role` VALUES ('K9', '办公室', '无', '2018-11-05 10:31:29', '2018-11-05 10:31:29', 'admin');

-- ----------------------------
-- Table structure for sys_role_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_acl`;
CREATE TABLE `sys_role_acl`  (
  `role_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `acl_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '权限code字符串,使用英文逗号隔开',
  PRIMARY KEY (`role_code`) USING BTREE,
  INDEX `fk_group_authorities_group`(`role_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_acl
-- ----------------------------
INSERT INTO `sys_role_acl` VALUES ('2019030516192556167', 'A1, A2, A3');
INSERT INTO `sys_role_acl` VALUES ('20190305183957074_98', 'A1, A2');
INSERT INTO `sys_role_acl` VALUES ('5c8214f38127b01038b3a075', 'A1,A2');
INSERT INTO `sys_role_acl` VALUES ('K1', 'A1');
INSERT INTO `sys_role_acl` VALUES ('k2', 'I1,J1,J1,J2,,,,');
INSERT INTO `sys_role_acl` VALUES ('k3', 'J1,G1,,G2,');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `id` bigint(32) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `role_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_group_members_group`(`role_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 97 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (1, 'admin', '2');
INSERT INTO `sys_role_user` VALUES (3, '13666969189', '3');
INSERT INTO `sys_role_user` VALUES (4, '18960559996', '2');
INSERT INTO `sys_role_user` VALUES (5, '13328583688', '1');
INSERT INTO `sys_role_user` VALUES (6, '18005985116', 'K1');
INSERT INTO `sys_role_user` VALUES (7, '18005985516', 'K1');
INSERT INTO `sys_role_user` VALUES (8, '18005985669', 'K1');
INSERT INTO `sys_role_user` VALUES (9, '18005985896', '7');
INSERT INTO `sys_role_user` VALUES (10, '18005986123', '7');
INSERT INTO `sys_role_user` VALUES (11, '18005985659', '7');
INSERT INTO `sys_role_user` VALUES (12, '18005985606', '8');
INSERT INTO `sys_role_user` VALUES (13, '18965301795', '8');
INSERT INTO `sys_role_user` VALUES (14, '13950960786', '9');
INSERT INTO `sys_role_user` VALUES (15, '18005985618', '8');
INSERT INTO `sys_role_user` VALUES (16, '18259742525', '7');
INSERT INTO `sys_role_user` VALUES (17, '18006980059', '9');
INSERT INTO `sys_role_user` VALUES (18, '18005985506', '10');
INSERT INTO `sys_role_user` VALUES (22, '15980587718', '2');
INSERT INTO `sys_role_user` VALUES (23, '15980587718', '3');
INSERT INTO `sys_role_user` VALUES (33, '5c81f5568127b035dce00e82', '1');
INSERT INTO `sys_role_user` VALUES (34, '5c81f5568127b035dce00e82', '2');
INSERT INTO `sys_role_user` VALUES (35, '5c81f5568127b035dce00e82', '3');
INSERT INTO `sys_role_user` VALUES (36, '5c81f6058127b035dce00e83', 'k1');
INSERT INTO `sys_role_user` VALUES (37, '5c81f6058127b035dce00e83', 'k2');
INSERT INTO `sys_role_user` VALUES (38, '5c81f8308127b02b288e8a3a', 'k1');
INSERT INTO `sys_role_user` VALUES (39, '5c81f8308127b02b288e8a3a', 'k2');
INSERT INTO `sys_role_user` VALUES (40, '5c81f8698127b022dc47e515', 'k1');
INSERT INTO `sys_role_user` VALUES (41, '5c81f8698127b022dc47e515', 'k2');
INSERT INTO `sys_role_user` VALUES (42, '5c81f8a18127b022dc47e516', 'k1');
INSERT INTO `sys_role_user` VALUES (43, '5c81f8a18127b022dc47e516', 'k2');
INSERT INTO `sys_role_user` VALUES (44, '5c81f9788127b022dc47e517', 'k1');
INSERT INTO `sys_role_user` VALUES (45, '5c81f9788127b022dc47e517', 'k2');
INSERT INTO `sys_role_user` VALUES (46, '5c81f9878127b022dc47e518', 'k1');
INSERT INTO `sys_role_user` VALUES (47, '5c81f9878127b022dc47e518', 'k2');
INSERT INTO `sys_role_user` VALUES (48, '5c81f9ca8127b022dc47e519', 'k1');
INSERT INTO `sys_role_user` VALUES (49, '5c81f9ca8127b022dc47e519', 'k2');
INSERT INTO `sys_role_user` VALUES (50, '5c81fa3a8127b007747e7631', 'k1');
INSERT INTO `sys_role_user` VALUES (51, '5c81fa3a8127b007747e7631', 'k2');
INSERT INTO `sys_role_user` VALUES (52, '5c81faaf8127b01058448c15', 'k1');
INSERT INTO `sys_role_user` VALUES (53, '5c81faaf8127b01058448c15', 'k2');
INSERT INTO `sys_role_user` VALUES (54, '5c81fb7b8127b021fc48817d', 'k1');
INSERT INTO `sys_role_user` VALUES (55, '5c81fb7b8127b021fc48817d', 'k2');
INSERT INTO `sys_role_user` VALUES (56, '5c81fd0b8127b030b0ca5b8a', 'k1');
INSERT INTO `sys_role_user` VALUES (57, '5c81fd0b8127b030b0ca5b8a', 'k2');
INSERT INTO `sys_role_user` VALUES (58, '5c81fd8b8127b030b0ca5b8b', 'k1');
INSERT INTO `sys_role_user` VALUES (59, '5c81fd8b8127b030b0ca5b8b', 'k2');
INSERT INTO `sys_role_user` VALUES (60, '5c81fe478127b030b0ca5b8c', 'k1');
INSERT INTO `sys_role_user` VALUES (61, '5c81fe478127b030b0ca5b8c', 'k2');
INSERT INTO `sys_role_user` VALUES (62, '5c81fe8b8127b00ff03f17f9', 'k1');
INSERT INTO `sys_role_user` VALUES (63, '5c81fe8b8127b00ff03f17f9', 'k2');
INSERT INTO `sys_role_user` VALUES (64, '5c81fff98127b0351c1b7a12', 'k1');
INSERT INTO `sys_role_user` VALUES (65, '5c81fff98127b0351c1b7a12', 'k2');
INSERT INTO `sys_role_user` VALUES (66, '5c8201368127b02e9407f871', 'k1');
INSERT INTO `sys_role_user` VALUES (67, '5c8201368127b02e9407f871', 'k2');
INSERT INTO `sys_role_user` VALUES (74, '5c8236f8ee43711a40d1bcb9', '1');
INSERT INTO `sys_role_user` VALUES (75, '5c8236f8ee43711a40d1bcb9', '2');
INSERT INTO `sys_role_user` VALUES (76, '5c8236f8ee43711a40d1bcb9', '3');
INSERT INTO `sys_role_user` VALUES (89, 'gys1', '1');
INSERT INTO `sys_role_user` VALUES (90, 'gys1', '2');
INSERT INTO `sys_role_user` VALUES (91, 'gys1', '3');
INSERT INTO `sys_role_user` VALUES (92, 'gys1', '4');
INSERT INTO `sys_role_user` VALUES (93, 'lth', '1');
INSERT INTO `sys_role_user` VALUES (94, 'lth', '2');
INSERT INTO `sys_role_user` VALUES (95, 'lth', '3');
INSERT INTO `sys_role_user` VALUES (96, 'lth', '4');

-- ----------------------------
-- Table structure for sys_super_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_super_user`;
CREATE TABLE `sys_super_user`  (
  `user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '超级用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_super_user
-- ----------------------------
INSERT INTO `sys_super_user` VALUES ('admin');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `is_locked` int(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '有效性',
  `verification_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '验证码，可用在注册、忘记密码、验证码登录等场合',
  `verification_code_generate_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '验证码生成时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('5c81f5568127b035dce00e82', '$2a$10$6.Czi3fslUfzWH0h/RIPjeKyNezoC.qeVd6DxIZXSZ4UWldWw8uX2', 1, '', '2019-03-08 12:53:41');
INSERT INTO `sys_user` VALUES ('5c81f6058127b035dce00e83', '$2a$10$bvmrlq7My7.ZwQ5Hkvcy3eWOo..EX5.IfyO46/cm/DplY64z4.yAe', 1, '', '2019-03-08 12:56:36');
INSERT INTO `sys_user` VALUES ('5c81f8308127b02b288e8a3a', '$2a$10$nS0fcbSsEwSf8cF6PBT8nOmMnfWAervQ0h6DGkWcuCqCNpMnsGsTO', 1, '', '2019-03-08 13:05:57');
INSERT INTO `sys_user` VALUES ('5c81f8698127b022dc47e515', '$2a$10$36aJHB9sA0OqZfFt1WHsfOzXVbMc65MDEGVCatXZsMthS0VR/Ip9u', 1, '', '2019-03-08 13:06:48');
INSERT INTO `sys_user` VALUES ('5c81f8a18127b022dc47e516', '$2a$10$OjvEVdQOZcD5PALNd6qvluGnEX5pnlI.WBSTL8zZUmD0vWjtm3Cy2', 1, '', '2019-03-08 13:08:06');
INSERT INTO `sys_user` VALUES ('5c81f9788127b022dc47e517', '$2a$10$Q7CY7EFJ0SfVmgxX/rUFWOIKAF5hgyBtUsEoeM8nz0wHpSHObqEUy', 1, '', '2019-03-08 13:11:19');
INSERT INTO `sys_user` VALUES ('5c81f9878127b022dc47e518', '$2a$10$xowoo9HHnK./BPpyeLVetuTD6qzRSs5ltMICB1PXVXhl0U9PsT.Hm', 1, '', '2019-03-08 13:11:34');
INSERT INTO `sys_user` VALUES ('5c81f9ca8127b022dc47e519', '$2a$10$yCP9l5EH/dz59gSZFms4OeGTV4.oPaazWOR4dSulRDp3qZc.1CLO2', 1, '', '2019-03-08 13:12:41');
INSERT INTO `sys_user` VALUES ('5c81fa3a8127b007747e7631', '$2a$10$rxleBd.HyPskZILaoo0CAOXeG0i.sm.LBRyuG75811J3cFYWNNpke', 1, '', '2019-03-08 13:14:34');
INSERT INTO `sys_user` VALUES ('5c81faaf8127b01058448c15', '$2a$10$SeJ9Mva0jj62KJMVH.zOLepvag/e7.rZc72Dyrhv26YXLScAJem.2', 1, '', '2019-03-08 13:16:30');
INSERT INTO `sys_user` VALUES ('5c81fb7b8127b021fc48817d', '$2a$10$eRTu3AjnLxFshdsylNqG/uF5OKvV/B65peEyZgph6NSPtbcV/gtHK', 1, '', '2019-03-08 13:19:54');
INSERT INTO `sys_user` VALUES ('5c81fd0b8127b030b0ca5b8a', '$2a$10$6ly8b3vj2fRzjJ8/iReH7.4tYV.m0ljL6dbd7nPnbbU0tJe0s5NSm', 1, '', '2019-03-08 13:26:34');
INSERT INTO `sys_user` VALUES ('5c81fd8b8127b030b0ca5b8b', '$2a$10$O8YeXme8HVvaaFFJbqmxHe4qUIOHMDutDvKbUzLHCcmtwKdPI.RfO', 1, '', '2019-03-08 13:28:47');
INSERT INTO `sys_user` VALUES ('5c81fe478127b030b0ca5b8c', '$2a$10$jZ32at73VK7IYyq8Jcxl7uyILLFqxte.fGJ2OiY6WMBVxNvw0TA/2', 1, '', '2019-03-08 13:31:49');
INSERT INTO `sys_user` VALUES ('5c81fe8b8127b00ff03f17f9', '$2a$10$RcVAiw5x01fHC8.u99oRWuvFeulO.XknN7WiRseZewvG6EXGk./Y2', 1, '', '2019-03-08 13:32:58');
INSERT INTO `sys_user` VALUES ('5c81fff98127b0351c1b7a12', '$2a$10$qMD746Kz2d7PsUPTrbvW5uLFFOmF4aPe7Stn/32q8DywSW0Z5nMGa', 1, '', '2019-03-08 13:39:04');
INSERT INTO `sys_user` VALUES ('5c8201368127b02e9407f871', '$2a$10$leFda5hiw6.C4oxGw7HDu.CHiEQqd9OgEcPjHd7CVuQe9CmJEb2iS', 1, '', '2019-03-08 13:44:21');
INSERT INTO `sys_user` VALUES ('5c8204c78127b02e9407f872', '$2a$10$7sKppfXMVWR9gkgGujFCteXrVzDNwIkoz33xZFRXCFpXoAiFpnKDu', 1, '', '2019-03-08 13:59:34');
INSERT INTO `sys_user` VALUES ('5c8236f8ee43711a40d1bcb9', '$2a$10$P0IHbXLgtuKms5lArugYkODiudnePirlwOUczCeTxvdYSzK1JCzEO', 1, '', '2019-03-08 17:33:45');
INSERT INTO `sys_user` VALUES ('admin', '$2a$10$jX/Wj7dWyOUp6FD9wTPs7.KimLep8DQGcGtyhEiPTSQd5kTuh03y6', 1, '', '2019-02-25 15:42:09');
INSERT INTO `sys_user` VALUES ('gys', '', 0, '', '2019-03-08 21:34:12');
INSERT INTO `sys_user` VALUES ('gys1', '', 0, '', '2019-03-07 05:19:37');
INSERT INTO `sys_user` VALUES ('lth', '$2a$10$C2Y.d2cZLYJeecxu5/LIAe/GnsOiOrBdl5C0fEROPYY6QIC6a1ygy', 1, '', '2018-12-04 09:34:12');

-- ----------------------------
-- Table structure for sys_user_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_acl`;
CREATE TABLE `sys_user_acl`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `acl_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '权限code字符串使用英文逗号分隔',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ix_auth_username`(`user_id`, `acl_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_acl
-- ----------------------------
INSERT INTO `sys_user_acl` VALUES (1, '5c81f5568127b035dce00e82', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (2, '5c81f6058127b035dce00e83', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (3, '5c81f8308127b02b288e8a3a', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (4, '5c81f8698127b022dc47e515', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (5, '5c81f8a18127b022dc47e516', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (6, '5c81f9788127b022dc47e517', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (7, '5c81f9878127b022dc47e518', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (8, '5c81f9ca8127b022dc47e519', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (9, '5c81fa3a8127b007747e7631', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (10, '5c81faaf8127b01058448c15', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (11, '5c81fb7b8127b021fc48817d', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (12, '5c81fd0b8127b030b0ca5b8a', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (13, '5c81fd8b8127b030b0ca5b8b', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (14, '5c81fe478127b030b0ca5b8c', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (15, '5c81fe8b8127b00ff03f17f9', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (16, '5c81fff98127b0351c1b7a12', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (17, '5c8201368127b02e9407f871', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (18, '5c8236f8ee43711a40d1bcb9', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (19, 'admin', 'T1,D1,D2,D3,D4');
INSERT INTO `sys_user_acl` VALUES (20, 'gys1', 'D1,D2');
INSERT INTO `sys_user_acl` VALUES (21, 'lth', 'D1,D2');

-- ----------------------------
-- Table structure for sys_user_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_detail`;
CREATE TABLE `sys_user_detail`  (
  `user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报名号',
  `real_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` tinyint(4) NULL DEFAULT NULL COMMENT '0女  1男',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `login_type` tinyint(4) NULL DEFAULT NULL COMMENT '0 ',
  `dept_id` bigint(32) UNSIGNED NOT NULL DEFAULT 0 COMMENT '管理员为1，用户为2',
  `id_card` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `unique`(`email`, `phone`, `id_card`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户详细信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_detail
-- ----------------------------
INSERT INTO `sys_user_detail` VALUES ('13328583688', '张茂华', 'test@test.com', '13328583688', NULL, '2018-11-05 11:13:41', '2018-11-05 11:13:41', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('13666969189', '张武威', 'test@test.com', '13666969189', NULL, '2018-11-05 10:44:40', '2018-11-05 10:44:40', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('13950960786', '刘友武', '13950960786@q.cn', '13950960786', NULL, '2018-11-05 11:19:53', '2018-11-05 11:19:53', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('18005985116', '汤伟红', '18005985116@test.com', '18005985116', NULL, '2018-11-05 11:14:15', '2018-11-05 11:14:15', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('18005985506', '贾荷敏', '18005985506@q.cn', '18005985506', NULL, '2018-11-05 11:22:52', '2018-11-05 11:22:52', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('18005985516', '邓翼强', '18005985516@q.com', '18005985516', NULL, '2018-11-05 11:15:10', '2018-11-05 11:15:10', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('18005985606', '陈友相', '18005985606@q.cn', '18005985606', NULL, '2018-11-05 11:18:45', '2018-11-05 11:18:45', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('18005985618', '李勇', '18005985618@q.cn', '18005985618', NULL, '2018-11-05 11:20:30', '2018-11-05 11:20:30', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('18005985659', '刘孙发', '18005985659@q.com', '18005985659', NULL, '2018-11-05 11:18:14', '2018-11-05 11:18:14', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('18005985669', '胡景', '18005985669@q.com', '18005985669', NULL, '2018-11-05 11:15:49', '2018-11-05 11:15:49', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('18005985896', '陆招兰', '18005985896@q.com', '18005985896', NULL, '2018-11-05 11:16:34', '2018-11-05 11:16:34', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('18005986123', '余建', '18005986123@q.com', '18005986123', NULL, '2018-11-05 11:17:20', '2018-11-05 11:17:20', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('18006980059', '罗淑媛', '18006980059@q.cn', '18006980059', NULL, '2018-11-05 11:22:26', '2018-11-05 11:22:26', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('18259742525', '肖香梅', '18259742525@q.cn', '18259742525', NULL, '2018-11-05 11:21:58', '2018-11-05 11:21:58', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('18960559996', '林志兴', 'test@test.com', '18960559996', NULL, '2018-11-05 11:13:02', '2019-03-05 16:09:58', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('18965301795', '李增禄', '18965301795@q.cn', '18965301795', NULL, '2018-11-05 11:19:24', '2019-03-05 16:10:02', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('5c8204c78127b02e9407f872', 'lwn', '529042209@qq.com', '15980587719', 1, '2019-03-08 13:59:35', '2019-03-08 14:21:22', 0, 0, '350128199920000114');
INSERT INTO `sys_user_detail` VALUES ('admin', 'admin', '123@qq.com', '18811111212', NULL, '2018-10-30 14:58:36', '2019-03-06 20:00:32', NULL, 1, NULL);
INSERT INTO `sys_user_detail` VALUES ('lth', '林天航', '1234567@qq.com', '11111111111', 1, '2018-12-04 09:34:12', '2019-03-07 12:02:38', 1, 1, '1');

-- ----------------------------
-- Table structure for tss_class_info
-- ----------------------------
DROP TABLE IF EXISTS `tss_class_info`;
CREATE TABLE `tss_class_info`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '教室名称',
  `place` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '所属考点',
  `capacity` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '教室容量',
  `type` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '笔试' COMMENT '教室类型（机考、笔试、其他）',
  `status` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态（0启用、1停用）',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '教室字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tss_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `tss_dictionary`;
CREATE TABLE `tss_dictionary`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '编号',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典总表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tss_exam
-- ----------------------------
DROP TABLE IF EXISTS `tss_exam`;
CREATE TABLE `tss_exam`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '考试 名称',
  `type` int(2) UNSIGNED NOT NULL COMMENT '考试类型（机考0笔试1）',
  `start_time` datetime(0) NOT NULL COMMENT '考试开始时间',
  `end_time` datetime(0) NOT NULL COMMENT '考试结束时间 主考试可以为空',
  `report_start` datetime(0) NOT NULL COMMENT '签到开始时间',
  `report_end` datetime(0) NOT NULL COMMENT '签到结束时间',
  `parent_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '父考试id',
  `level` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '层级',
  `status` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '归档状态默认0未归档',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '考试信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tss_exam_place
-- ----------------------------
DROP TABLE IF EXISTS `tss_exam_place`;
CREATE TABLE `tss_exam_place`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `name_or_seq` int(20) NOT NULL COMMENT '考点和考场名称或编号',
  `te_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '考试表id',
  `tci_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '教室字典表id',
  `parent_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '父考点或考场id',
  `level` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '层级',
  `student_count` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '考场考生数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '考场考点信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tss_id_pool
-- ----------------------------
DROP TABLE IF EXISTS `tss_id_pool`;
CREATE TABLE `tss_id_pool`  (
  `te_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '考试id',
  `te_start_time` datetime(0) NOT NULL COMMENT '考试时间',
  `exam_num` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '考试场次（非一场考试下拥有的考试场数）',
  `count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '编号',
  PRIMARY KEY (`te_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '考场id分配表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tss_import_grade
-- ----------------------------
DROP TABLE IF EXISTS `tss_import_grade`;
CREATE TABLE `tss_import_grade`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `xm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `zjhm` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '证件号码(身份证)',
  `xh` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '学号',
  `ssxx` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '所属学校',
  `xl` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '学历',
  `xz` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '学制',
  `nz` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '年级',
  `yx` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '院系',
  `zy` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '专业',
  `bj` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '班级',
  `kssjhcs` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '考试时间或次数',
  `bszkzh` char(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '笔试准考证号',
  `bskmmc` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '笔试科目名称',
  `cjdh` char(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '成绩单号',
  `zf` char(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '总分',
  `cj1` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '成绩1',
  `cj2` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '成绩2',
  `cj3` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '成绩3',
  `cj4` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '成绩4',
  `bz` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '成绩导入表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tss_person_library
-- ----------------------------
DROP TABLE IF EXISTS `tss_person_library`;
CREATE TABLE `tss_person_library`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `college_id` bigint(20) UNSIGNED NOT NULL COMMENT '所属学院或部门ID',
  `work_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '工号、学号等',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '联系电话',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '性别',
  `pid` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '身份证号',
  `bank` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '银行',
  `bank_open_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '开户行代码',
  `bank_open` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '开户行',
  `bank_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '银行卡号',
  `category` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '人员类别',
  `type` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '人员类型',
  `money_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '汇款类型',
  `order` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '是否入财务系统',
  `yes_no` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '是否参加监考',
  `address` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '选择校区',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '人员库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tss_post_info
-- ----------------------------
DROP TABLE IF EXISTS `tss_post_info`;
CREATE TABLE `tss_post_info`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位名称',
  `free` decimal(11, 3) NOT NULL COMMENT '岗位费用',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tss_post_person
-- ----------------------------
DROP TABLE IF EXISTS `tss_post_person`;
CREATE TABLE `tss_post_person`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `tep_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '考场考点id',
  `post_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位名称',
  `post_free` decimal(11, 3) NOT NULL COMMENT '岗位费用',
  `person_num` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '岗位人数',
  `tpi_id_str` varchar(4800) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '已安排的岗位人员id字符串',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位人员总表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tss_seat_order
-- ----------------------------
DROP TABLE IF EXISTS `tss_seat_order`;
CREATE TABLE `tss_seat_order`  (
  `tep_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '考场考点id',
  `seat_order` varchar(9600) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '考场座位顺序字符串',
  PRIMARY KEY (`tep_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '考场座位顺序表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tss_seat_schedule
-- ----------------------------
DROP TABLE IF EXISTS `tss_seat_schedule`;
CREATE TABLE `tss_seat_schedule`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `xymc` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '学院名称',
  `xm` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `bkjb` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '报考级别',
  `tep_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '考场考点id',
  `seat_num` int(11) NOT NULL DEFAULT -1 COMMENT '座位号（-1未安排）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生座位安排总表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tss_solicitation_time
-- ----------------------------
DROP TABLE IF EXISTS `tss_solicitation_time`;
CREATE TABLE `tss_solicitation_time`  (
  `te_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '考试表的id',
  `post_time_start` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '岗位开始征集时间',
  `post_time_end` datetime(0) NOT NULL COMMENT '岗位结束征集时间',
  `student_time_start` datetime(0) NOT NULL COMMENT '学生导入开始时间',
  `student_time_end` datetime(0) NOT NULL COMMENT '学生导入结束时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`te_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '考试岗位和学生征集时间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tss_student_info
-- ----------------------------
DROP TABLE IF EXISTS `tss_student_info`;
CREATE TABLE `tss_student_info`  (
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `te_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '考试id',
  `xm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `zjhm` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '证件号码(身份证)',
  `xh` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '学号',
  `ssxx` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '所属学校',
  `xl` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '学历',
  `xz` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '学制',
  `nz` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '年级',
  `yx` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '院系',
  `zy` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '专业',
  `bj` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '班级',
  `kssjhcs` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '考试时间或次数',
  `bszkzh` char(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '笔试准考证号',
  `bskmmc` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '笔试科目名称',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生数据总表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tss_submit_person
-- ----------------------------
DROP TABLE IF EXISTS `tss_submit_person`;
CREATE TABLE `tss_submit_person`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `college_id` bigint(20) UNSIGNED NOT NULL COMMENT '所属学院或部门ID',
  `work_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '工号、学号等',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '联系电话',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '性别',
  `pid` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '身份证号',
  `bank` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '银行',
  `bank_open_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '开户行代码',
  `bank_open` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '开户行',
  `bank_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '银行卡号',
  `category` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '人员类别',
  `type` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '人员类型',
  `money_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '汇款类型',
  `order` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '是否入财务系统',
  `yes_no` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '是否参加监考',
  `address` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '选择校区',
  `tep_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '考点考场id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '报名岗位人员总表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tss_temp_seat_schedule
-- ----------------------------
DROP TABLE IF EXISTS `tss_temp_seat_schedule`;
CREATE TABLE `tss_temp_seat_schedule`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `xymc` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '学院名称',
  `xm` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `bkjb` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '报考级别',
  `tep_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '考场考点id',
  `seat_num` int(11) NOT NULL DEFAULT -1 COMMENT '座位号（-1未安排）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生座位安排临时表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tss_temp_student_info
-- ----------------------------
DROP TABLE IF EXISTS `tss_temp_student_info`;
CREATE TABLE `tss_temp_student_info`  (
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `te_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '考试id',
  `xm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `zjhm` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '证件号码(身份证)',
  `xh` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '学号',
  `ssxx` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '所属学校',
  `xl` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '学历',
  `xz` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '学制',
  `nz` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '年级',
  `yx` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '院系',
  `zy` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '专业',
  `bj` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '班级',
  `kssjhcs` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '考试时间或次数',
  `bszkzh` char(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '笔试准考证号',
  `bskmmc` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '笔试科目名称',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生数据临时表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tss_temp_submit_person
-- ----------------------------
DROP TABLE IF EXISTS `tss_temp_submit_person`;
CREATE TABLE `tss_temp_submit_person`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `college_id` bigint(20) UNSIGNED NOT NULL COMMENT '所属学院或部门ID',
  `work_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '工号、学号等',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '联系电话',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '性别',
  `pid` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '身份证号',
  `bank` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '银行',
  `bank_open_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '开户行代码',
  `bank_open` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '开户行',
  `bank_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '银行卡号',
  `category` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '人员类别',
  `type` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '人员类型',
  `money_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '汇款类型',
  `order` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '是否入财务系统',
  `yes_no` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '是否参加监考',
  `address` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '选择校区',
  `tep_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '考点考场id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '报名岗位人员临时表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
