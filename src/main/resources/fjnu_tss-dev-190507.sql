/*
 Navicat Premium Data Transfer

 Source Server         : hx
 Source Server Type    : MySQL
 Source Server Version : 50642
 Source Host           : 47.106.139.233:3306
 Source Schema         : fjnu_tss

 Target Server Type    : MySQL
 Target Server Version : 50642
 File Encoding         : 65001

 Date: 07/05/2019 15:29:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl`;
CREATE TABLE `sys_acl` (
  `id` bigint(32) unsigned NOT NULL COMMENT '权限id',
  `code` varchar(32) NOT NULL COMMENT '权限码',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '权限名称',
  `acl_module_id` bigint(32) NOT NULL DEFAULT '0' COMMENT '权限所在的权限模块id',
  `url` varchar(100) NOT NULL DEFAULT '' COMMENT '请求的url, 可以填正则表达式 ',
  `function_no` varchar(100) NOT NULL DEFAULT '' COMMENT '功能号',
  `type` int(11) NOT NULL DEFAULT '3' COMMENT '类型，0:系统 1:项目',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，1：正常，0：冻结',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '权限在当前模块下的顺序，由小到大',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='权限表';

-- ----------------------------
-- Records of sys_acl
-- ----------------------------
BEGIN;
INSERT INTO `sys_acl` VALUES (17, 'ADD_CLASS_ROOM', '新增', 8, '/classInfoManagement', 'AddClassroom', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (25, 'ADD_DEPTMENT', '新增部门', 2, '/departmentManagement', 'AddDepartment', 0, 1, 2, '');
INSERT INTO `sys_acl` VALUES (43, 'ADD_DICTIONARRY', '添加', 10, '/dataKeep/dataKeepDictionaryKeep', 'addDictionary', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (50, 'ADD_EXAM', '添加', 11, '/exam/examManagement', 'SetExamInfo', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (9, 'ADD_PERSON', '新增人员', 7, '/dataKeep/dataKeepCollegeDepartment', 'addPerson', 0, 1, 6, '');
INSERT INTO `sys_acl` VALUES (42, 'ADD_POST', '添加', 9, '/dataKeep/dataKeepPostManagement', 'addPost', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (1, 'ADD_ROLE', '新增', 1, '/system/role', 'AddRole', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (29, 'ADD_USER', '新增用户', 2, '/from/fromBuild', 'AddUser', 0, 1, 6, '');
INSERT INTO `sys_acl` VALUES (62, 'COLLEGE_ADD_DEPTMENT', '添加部门', 13, '/departmentManagement', 'AddDepartment', 1, 1, 2, '');
INSERT INTO `sys_acl` VALUES (65, 'COLLEGE_ADD_PERSON', '添加人员', 13, '/college/collegePersonManagement', 'addPerson', 1, 1, 6, '');
INSERT INTO `sys_acl` VALUES (63, 'COLLEGE_DELETE_DEPARTMENT', '删除部门', 13, '/departmentManagement', 'DeleteDepartment', 1, 1, 4, '');
INSERT INTO `sys_acl` VALUES (67, 'COLLEGE_DELETE_PERSON', '删除人员', 13, '/college/collegePersonManagement', 'deletePerson', 1, 1, 8, '');
INSERT INTO `sys_acl` VALUES (60, 'COLLEGE_SEARCH_DEPTMENT', '查看部门', 13, '/departmentManagement', 'DepartmentListFromCollege', 1, 1, 1, '');
INSERT INTO `sys_acl` VALUES (68, 'COLLEGE_SEARCH_EXAM', '查看', 14, '/exam/examManagement', 'GetExam', 1, 1, 1, '');
INSERT INTO `sys_acl` VALUES (64, 'COLLEGE_SEARCH_PERSON', '查看人员', 13, '/college/collegePersonManagement', 'searchPerson', 1, 1, 5, '');
INSERT INTO `sys_acl` VALUES (61, 'COLLEGE_UPDATE_DEPARTMENT', '修改部门', 13, '/departmentManagement', 'UpdateDepartment', 1, 1, 3, '');
INSERT INTO `sys_acl` VALUES (66, 'COLLEGE_UPDATE_PERSON', '修改人员', 13, '/college/collegePersonManagement', 'modifyPerson', 1, 1, 7, '');
INSERT INTO `sys_acl` VALUES (56, 'DATA_KEEP_ADD_DEPARTMENT', '添加部门', 7, '/departmentManagement', 'AddDepartment', 0, 1, 2, '');
INSERT INTO `sys_acl` VALUES (59, 'DATA_KEEP_DELETE_DEPARTMENT', '删除部门', 7, '/departmentManagement', 'DeleteDepartment', 0, 1, 4, '');
INSERT INTO `sys_acl` VALUES (58, 'DATA_KEEP_SEARCH_DEPARTMETN', '查看部门', 7, '/departmentManagement', 'DepartmentListFromDict', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (57, 'DATA_KEEP_UPDATE_DEPARTMETN', '修改部门', 7, '/departmentManagement', 'UpdateDepartment', 0, 1, 3, '');
INSERT INTO `sys_acl` VALUES (19, 'DELETE_CLASS_ROOM', '删除', 8, '/classInfoManagement', 'DeleteClassroom', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (27, 'DELETE_DEPARTMENT', '删除部门', 2, '/departmentManagement', 'DeleteDepartment', 0, 1, 4, '');
INSERT INTO `sys_acl` VALUES (47, 'DELETE_DICTIONARY', '删除', 10, '/dataKeep/dataKeepDictionaryKeep', 'deleteDictionary', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (52, 'DELETE_EXAM', '删除', 11, '/exam/examManagement', 'DelExam', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (7, 'DELETE_GRADE', '删除', 4, '/gradeManagement', 'deleteGrade', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (11, 'DELETE_PERSION', '删除人员', 7, '/dataKeep/dataKeepCollegeDepartment', 'deletePerson', 0, 1, 8, '');
INSERT INTO `sys_acl` VALUES (41, 'DELETE_POST', '删除', 9, '/dataKeep/dataKeepPostManagement', 'deletePost', 0, 1, 3, '');
INSERT INTO `sys_acl` VALUES (3, 'DELETE_ROLE', '删除', 1, '/system/role', 'DeleteRole', 0, 1, 3, '');
INSERT INTO `sys_acl` VALUES (31, 'DELETE_USER', '删除用户', 2, '/from/fromBuild', 'DeletePersonnel', 0, 1, 8, '');
INSERT INTO `sys_acl` VALUES (10, 'MODIFY_PERSION', '修改人员', 7, '/dataKeep/dataKeepCollegeDepartment', 'modifyPerson', 0, 1, 7, '');
INSERT INTO `sys_acl` VALUES (2, 'MODIFY_ROLE', '修改', 1, '/system/role', 'ModifyRole', 0, 1, 2, '');
INSERT INTO `sys_acl` VALUES (34, 'RESET_PASSWORD', '重置密码', 2, '/from/fromBuild', 'ResetPassword', 0, 1, 9, '');
INSERT INTO `sys_acl` VALUES (55, 'SEARCH_BEFORE_EXAM', '查看', 12, '/exam/examManagement', 'GetExam', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (20, 'SEARCH_CLASS_ROOM_INFO', '查看', 8, '/classInfoManagement', 'SeachByCondition', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (28, 'SEARCH_DEPARTMENT', '查看部门', 2, '/departmentManagement', 'DepartmentListFromOrganization', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (48, 'SEARCH_DICTIONARY', '查看', 10, '/dataKeep/dataKeepDictionaryKeep', 'searchDictionary', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (53, 'SEARCH_EXAM', '查看', 11, '/exam/examManagement', 'GetExam', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (8, 'SEARCH_GRADE', '查看', 4, '/gradeManagement', 'searchGrade', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (35, 'SEARCH_LOG', '查看', 3, '/common/operationLog', 'searchSysLog', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (12, 'SEARCH_PERSON', '查看人员', 7, '/dataKeep/dataKeepCollegeDepartment', 'searchPerson', 0, 1, 5, '');
INSERT INTO `sys_acl` VALUES (39, 'SEARCH_POST', '查看', 9, '/dataKeep/dataKeepPostManagement', 'searchPost', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (4, 'SEARCH_ROLE', '查看', 1, '/system/role', 'SearchRole', 0, 1, 4, '');
INSERT INTO `sys_acl` VALUES (32, 'SEARCH_USER', '查看用户', 2, '/from/fromBuild', 'SearchPersonnelByCondition', 0, 1, 5, '');
INSERT INTO `sys_acl` VALUES (18, 'UPDATE_CLASS_ROOM', '修改', 8, '/classInfoManagement', 'UpdateClassroom', 0, 1, 1, '');
INSERT INTO `sys_acl` VALUES (26, 'UPDATE_DEPARTMENT', '修改部门', 2, '/departmentManagement', 'UpdateDepartment', 0, 1, 3, '');
INSERT INTO `sys_acl` VALUES (45, 'UPDATE_DICTIONARY', '修改', 10, '/dataKeep/dataKeepDictionaryKeep', 'updateDictionary', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (51, 'UPDATE_EXAM', '修改', 11, '/exam/examManagement', 'SetExamInfo', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (54, 'UPDATE_EXAM_STATUS', '归档', 11, '/exam/examManagement', 'FileExam', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (40, 'UPDATE_POST', '修改', 9, '/dataKeep/dataKeepPostManagement', 'updatePost', 0, 1, 2, '');
INSERT INTO `sys_acl` VALUES (30, 'UPDATE_USER', '修改用户', 2, '/from/fromBuild', 'ModifyPersonnel', 0, 1, 7, '');
INSERT INTO `sys_acl` VALUES (33, 'UPDATE_USER_USE_STATUS', '用户状态', 2, '/from/fromBuild', 'ModifyUserStatus', 0, 1, 0, '');
INSERT INTO `sys_acl` VALUES (22, 'UPDATE_USE_STATUS', '启停', 8, '/classInfoManagement', 'UpdateStatus', 0, 1, 1, '');
COMMIT;

-- ----------------------------
-- Table structure for sys_acl_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl_module`;
CREATE TABLE `sys_acl_module` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '权限模块id',
  `code` varchar(32) NOT NULL COMMENT '权限模块code',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '权限模块名称',
  `parent_id` bigint(32) NOT NULL DEFAULT '0' COMMENT '上级权限模块id',
  `level` varchar(200) NOT NULL DEFAULT '' COMMENT '权限模块层级',
  `type` int(4) unsigned NOT NULL DEFAULT '0' COMMENT '0后台1学院端',
  `seq` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '权限模块在当前层级下的顺序，由小到大',
  `status` int(4) unsigned NOT NULL DEFAULT '1' COMMENT '状态，1：正常，0：冻结',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `route_name` varchar(100) DEFAULT '' COMMENT '前端路由name',
  `icon` varchar(50) DEFAULT '' COMMENT '前端icon',
  `component_path` varchar(255) DEFAULT '' COMMENT '前端模块路径',
  `cache` tinyint(255) DEFAULT NULL COMMENT '前端菜单项中cache',
  `path` varchar(255) DEFAULT '' COMMENT '前端权限所需路径',
  `component` varchar(255) DEFAULT '' COMMENT '前端需要',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='权限模块表';

-- ----------------------------
-- Records of sys_acl_module
-- ----------------------------
BEGIN;
INSERT INTO `sys_acl_module` VALUES (1, '', '角色管理', 0, '0', 0, 3, 1, '', 'RoleManagement', 'eye', 'pages/role-management/index', 1, '/role-management', 'role-management');
INSERT INTO `sys_acl_module` VALUES (2, '', '组织架构', 0, '0', 0, 4, 1, '', 'Organization', 'sitemap', 'pages/organization/index', 1, '/organization', 'organization');
INSERT INTO `sys_acl_module` VALUES (3, '', '日志查看', 0, '0', 0, 5, 1, '', 'Log', 'calendar', 'pages/log/index', 1, '/log', 'log');
INSERT INTO `sys_acl_module` VALUES (4, '', '成绩管理', 0, '0', 0, 2, 1, '', 'Score', 'area-chart', 'pages/score/index', 1, '/score', 'score');
INSERT INTO `sys_acl_module` VALUES (5, '', '数据维护', 0, '0', 0, 6, 1, '', 'Data', 'clone', 'layout/header-aside/layout', 1, '/data', 'layoutHeaderAside');
INSERT INTO `sys_acl_module` VALUES (6, '', '考试管理', 0, '0', 0, 1, 1, '', 'Exam', 'gears', 'layout/header-aside/layout', 1, '/exam', 'layoutHeaderAside');
INSERT INTO `sys_acl_module` VALUES (7, '', '学院部门', 5, '0.5', 0, 1, 1, '', 'CollegeDepartment', 'bank', 'pages/data/college-department/index', 1, '/data/college-department', 'college-department');
INSERT INTO `sys_acl_module` VALUES (8, '', '教室管理', 5, '0.5', 0, 2, 1, '', 'ClassManagement', 'graduation-cap', 'pages/data/class-management/index', 1, '/data/class-management', 'class-management');
INSERT INTO `sys_acl_module` VALUES (9, '', '岗位管理', 5, '0.5', 0, 3, 1, '', 'JobManagement', 'address-card-o', 'ages/data/job-management/index', 1, '/data/job-management', 'job-management');
INSERT INTO `sys_acl_module` VALUES (10, '', '字典维护', 5, '0.5', 0, 4, 1, '', 'DictionaryMaintenance', 'book', 'pages/data/maintenance-dictionary/index', 1, '/data/maintenance-dictionary', 'maintenance-dictionary');
INSERT INTO `sys_acl_module` VALUES (11, '', '进行中考试', 6, '0.6', 0, 1, 1, '', 'OngoingExam', 'map-o', 'pages/exam/ongoing-exam/index', 1, '/exam/ongoing-exam', 'ongoing-exam');
INSERT INTO `sys_acl_module` VALUES (12, '', '已归档考试', 6, '0.6', 0, 2, 1, '', 'ArchivedExam', 'archive', 'pages/exam/archived-exam/index', 1, '/exam/archived-exam', 'archived-exam');
INSERT INTO `sys_acl_module` VALUES (13, ' ', '人员管理', 0, '0', 1, 1, 1, '', 'UserManagement', 'users', 'pages/user-management/index', 1, '/user-management', 'user-management');
INSERT INTO `sys_acl_module` VALUES (14, ' ', '考试管理', 0, '0', 1, 2, 1, '', 'ExamManagement', 'mortar-board', 'pages/exam-management/index', 1, '/exam-management', 'exam-management');
COMMIT;

-- ----------------------------
-- Table structure for sys_admin_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_detail`;
CREATE TABLE `sys_admin_detail` (
  `user_id` varchar(100) NOT NULL COMMENT '报名号',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户名',
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `sex` tinyint(4) DEFAULT NULL COMMENT '0女  1男',
  `post` varchar(100) DEFAULT '' COMMENT '职务',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `login_type` tinyint(4) DEFAULT NULL COMMENT '0 ',
  `dept_id` bigint(32) unsigned NOT NULL DEFAULT '0' COMMENT '管理员为1，用户为2',
  `id_card` varchar(100) DEFAULT NULL COMMENT '身份证',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `unique` (`email`,`phone`,`id_card`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='管理员详细信息表';

-- ----------------------------
-- Records of sys_admin_detail
-- ----------------------------
BEGIN;
INSERT INTO `sys_admin_detail` VALUES ('5cc40e7eef481bb426d6a02c', 'lzh', 'lzh', NULL, '18359898989', NULL, 'hhh', '2019-04-27 16:10:39', '2019-04-27 16:10:38', NULL, 92, NULL);
INSERT INTO `sys_admin_detail` VALUES ('5cc40e9eef481bb426d6a02d', 'lwn', 'lwn', NULL, '18350808080', NULL, 'hihihi', '2019-04-27 16:11:10', '2019-04-27 16:11:10', NULL, 91, NULL);
INSERT INTO `sys_admin_detail` VALUES ('5cc40ec6ef481bb426d6a02e', 'czx', 'czx', NULL, '18350909090', NULL, 'hohoho', '2019-04-27 16:11:50', '2019-04-27 16:11:50', NULL, 91, NULL);
INSERT INTO `sys_admin_detail` VALUES ('5cc40eeeef481bb426d6a02f', 'wjy', 'wjy', NULL, '18358787878', NULL, 'zizizi', '2019-04-27 16:12:31', '2019-04-27 16:12:31', NULL, 93, NULL);
INSERT INTO `sys_admin_detail` VALUES ('5cc40f36ef481bb426d6a030', 'zmj', 'zmj', NULL, '18356767674', NULL, 'jijiji', '2019-04-27 16:13:42', '2019-04-27 16:13:42', NULL, 90, NULL);
INSERT INTO `sys_admin_detail` VALUES ('5cc40f54ef481bb426d6a031', 'lw', 'lw', NULL, '18356868686', NULL, 'wowowo', '2019-04-27 16:14:13', '2019-04-27 16:14:13', NULL, 90, NULL);
INSERT INTO `sys_admin_detail` VALUES ('5cc40f7fef481bb426d6a032', 'kjr', 'krj', NULL, '18351212121', NULL, 'wawaaw', '2019-04-27 16:14:56', '2019-04-27 16:14:55', NULL, 90, NULL);
INSERT INTO `sys_admin_detail` VALUES ('admin', '超级管理员', NULL, NULL, NULL, NULL, '', '2019-03-08 21:34:44', '2019-05-06 12:24:01', NULL, 42, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` bigint(32) NOT NULL DEFAULT '0' COMMENT '上级部门id',
  `level` varchar(200) NOT NULL DEFAULT '' COMMENT '部门层级',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '部门在当前层级下的顺序，由小到大',
  `type` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '部门来源0系统1外来',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部门表';

-- ----------------------------
-- Records of sys_department
-- ----------------------------
BEGIN;
INSERT INTO `sys_department` VALUES (42, 'admin', 0, '0', 1, 0, '', '2019-04-25 09:34:13', '2019-04-27 16:07:59');
INSERT INTO `sys_department` VALUES (85, '数信', 42, '0.42', 1, 0, '', '2019-04-27 16:08:12', '2019-05-07 13:53:37');
INSERT INTO `sys_department` VALUES (90, '计本', 85, '0.42.85', 1, 0, '', '2019-04-27 16:09:22', '2019-04-27 16:09:21');
INSERT INTO `sys_department` VALUES (91, '软工', 85, '0.42.85', 2, 0, '', '2019-04-27 16:09:34', '2019-04-27 16:09:33');
INSERT INTO `sys_department` VALUES (92, '数媒', 85, '0.42.85', 2, 0, '', '2019-04-27 16:09:44', '2019-04-27 16:09:44');
INSERT INTO `sys_department` VALUES (93, '数本', 90, '0.42.85.90', 1, 0, '', '2019-04-27 16:09:54', '2019-05-07 15:04:11');
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '自增的id',
  `user_id` varchar(100) NOT NULL DEFAULT '' COMMENT '用户名',
  `ip` varchar(128) DEFAULT '' COMMENT '登录ip',
  `operation_content` varchar(500) NOT NULL DEFAULT '' COMMENT '操作内容',
  `operation_date_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `operation_result` varchar(8) NOT NULL DEFAULT '' COMMENT '操作结果',
  `reason` varchar(500) DEFAULT '' COMMENT '原因',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=287 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统日志表';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_log` VALUES (1, 'admin', '127.0.0.1', '添加用户 lth', '2019-03-10 23:03:09', '成功', 'q');
INSERT INTO `sys_log` VALUES (2, 'i', '', '', '2019-03-05 04:26:56', '', '');
INSERT INTO `sys_log` VALUES (3, 'ii', '', '', '2019-03-05 04:26:59', '', '');
INSERT INTO `sys_log` VALUES (4, 'ilhj', '', '', '2019-03-05 04:27:04', '', '');
INSERT INTO `sys_log` VALUES (5, 'hh', '', '', '2019-03-05 04:27:07', '', '');
INSERT INTO `sys_log` VALUES (6, 'anonymousUser', '', '', '2019-04-28 13:17:38', '', '');
INSERT INTO `sys_log` VALUES (7, 'admin', '', '', '2019-04-28 13:22:00', '', '');
INSERT INTO `sys_log` VALUES (8, 'anonymousUser', '', '', '2019-04-29 10:55:20', '', '');
INSERT INTO `sys_log` VALUES (9, 'anonymousUser', '', '', '2019-04-30 09:01:23', '', '');
INSERT INTO `sys_log` VALUES (10, 'anonymousUser', '', '', '2019-04-30 09:01:26', '', '');
INSERT INTO `sys_log` VALUES (11, 'anonymousUser', '', '', '2019-04-30 09:01:28', '', '');
INSERT INTO `sys_log` VALUES (12, 'anonymousUser', '', '', '2019-04-30 09:01:29', '', '');
INSERT INTO `sys_log` VALUES (13, 'anonymousUser', '', '', '2019-04-30 09:01:30', '', '');
INSERT INTO `sys_log` VALUES (14, 'admin', '', '', '2019-04-30 09:01:36', '', '');
INSERT INTO `sys_log` VALUES (15, 'admin', '', '', '2019-04-30 09:01:43', '', '');
INSERT INTO `sys_log` VALUES (16, 'admin', '', '', '2019-04-30 09:01:45', '', '');
INSERT INTO `sys_log` VALUES (17, 'admin', '', '', '2019-04-30 09:01:55', '', '');
INSERT INTO `sys_log` VALUES (18, 'admin', '', '', '2019-04-30 09:01:55', '', '');
INSERT INTO `sys_log` VALUES (19, 'admin', '', '', '2019-04-30 09:01:58', '', '');
INSERT INTO `sys_log` VALUES (20, 'admin', '', '', '2019-04-30 09:02:03', '', '');
INSERT INTO `sys_log` VALUES (21, 'admin', '', '', '2019-04-30 09:02:03', '', '');
INSERT INTO `sys_log` VALUES (22, 'admin', '', '', '2019-04-30 09:02:06', '', '');
INSERT INTO `sys_log` VALUES (23, 'admin', '', '', '2019-04-30 09:02:11', '', '');
INSERT INTO `sys_log` VALUES (24, 'admin', '', '', '2019-04-30 09:02:12', '', '');
INSERT INTO `sys_log` VALUES (25, 'admin', '', '', '2019-04-30 09:02:13', '', '');
INSERT INTO `sys_log` VALUES (26, 'admin', '', '', '2019-04-30 09:02:14', '', '');
INSERT INTO `sys_log` VALUES (27, 'admin', '', '', '2019-04-30 09:02:19', '', '');
INSERT INTO `sys_log` VALUES (28, 'admin', '', '', '2019-04-30 09:02:24', '', '');
INSERT INTO `sys_log` VALUES (29, 'admin', '', '', '2019-04-30 09:02:27', '', '');
INSERT INTO `sys_log` VALUES (30, 'admin', '', '', '2019-04-30 09:02:27', '', '');
INSERT INTO `sys_log` VALUES (31, 'admin', '', '', '2019-04-30 09:02:27', '', '');
INSERT INTO `sys_log` VALUES (32, 'admin', '', '', '2019-04-30 09:02:28', '', '');
INSERT INTO `sys_log` VALUES (33, 'admin', '', '', '2019-04-30 09:02:44', '', '');
INSERT INTO `sys_log` VALUES (34, 'admin', '', '', '2019-04-30 09:03:21', '', '');
INSERT INTO `sys_log` VALUES (35, 'admin', '', '', '2019-04-30 09:03:23', '', '');
INSERT INTO `sys_log` VALUES (36, 'admin', '', '', '2019-04-30 09:03:48', '', '');
INSERT INTO `sys_log` VALUES (37, 'admin', '', '', '2019-04-30 09:05:06', '', '');
INSERT INTO `sys_log` VALUES (38, 'admin', '', '', '2019-04-30 09:06:07', '', '');
INSERT INTO `sys_log` VALUES (39, 'anonymousUser', '', '', '2019-04-30 09:06:39', '', '');
INSERT INTO `sys_log` VALUES (40, 'anonymousUser', '', '', '2019-04-30 09:10:31', '', '');
INSERT INTO `sys_log` VALUES (41, 'admin', '', '', '2019-04-30 09:10:44', '', '');
INSERT INTO `sys_log` VALUES (42, 'anonymousUser', '', '', '2019-04-30 09:11:02', '', '');
INSERT INTO `sys_log` VALUES (43, 'admin', '', '', '2019-04-30 09:11:24', '', '');
INSERT INTO `sys_log` VALUES (44, 'anonymousUser', '', '', '2019-04-30 09:11:24', '', '');
INSERT INTO `sys_log` VALUES (45, 'anonymousUser', '', '', '2019-04-30 09:14:20', '', '');
INSERT INTO `sys_log` VALUES (46, 'anonymousUser', '', '', '2019-04-30 09:16:27', '', '');
INSERT INTO `sys_log` VALUES (47, 'anonymousUser', '', '', '2019-04-30 09:35:02', '', '');
INSERT INTO `sys_log` VALUES (48, 'admin', '', '获取下载成绩导入模板', '2019-04-30 09:44:14', '成功', '');
INSERT INTO `sys_log` VALUES (49, 'admin', '', '获取下载成绩导入模板', '2019-04-30 09:44:25', '成功', '');
INSERT INTO `sys_log` VALUES (50, 'anonymousUser', '', '', '2019-04-30 10:14:56', '', '');
INSERT INTO `sys_log` VALUES (51, 'admin', '', '', '2019-04-30 10:16:09', '', '');
INSERT INTO `sys_log` VALUES (52, 'admin', '', '', '2019-04-30 10:17:50', '', '');
INSERT INTO `sys_log` VALUES (53, 'admin', '', '', '2019-04-30 10:19:08', '', '');
INSERT INTO `sys_log` VALUES (54, 'admin', '', '', '2019-04-30 10:21:10', '', '');
INSERT INTO `sys_log` VALUES (55, 'admin', '', '', '2019-04-30 11:09:23', '', '');
INSERT INTO `sys_log` VALUES (56, 'admin', '', '', '2019-04-30 11:11:52', '', '');
INSERT INTO `sys_log` VALUES (57, 'admin', '', '上传学生数据', '2019-04-30 11:27:24', '成功', '');
INSERT INTO `sys_log` VALUES (58, 'admin', '', '获取下载成绩导入模板', '2019-04-30 11:27:27', '成功', '');
INSERT INTO `sys_log` VALUES (59, 'admin', '', ' 删除 [] 岗位', '2019-04-30 11:28:18', '成功', '');
INSERT INTO `sys_log` VALUES (60, 'admin', '', '添加 小沙沙 岗位', '2019-04-30 11:39:08', '成功', '');
INSERT INTO `sys_log` VALUES (61, 'admin', '', '添加 计本林伟1 人员', '2019-04-30 11:39:27', '成功', '');
INSERT INTO `sys_log` VALUES (62, 'admin', '', ' 删除 [] 岗位', '2019-04-30 11:39:27', '成功', '');
INSERT INTO `sys_log` VALUES (63, 'admin', '', '添加 计本林伟2 人员', '2019-04-30 11:40:02', '成功', '');
INSERT INTO `sys_log` VALUES (64, 'admin', '', '添加 计本林伟3 人员', '2019-04-30 11:40:27', '成功', '');
INSERT INTO `sys_log` VALUES (65, 'admin', '', '添加 计本林伟4 人员', '2019-04-30 11:40:48', '成功', '');
INSERT INTO `sys_log` VALUES (66, 'admin', '', '添加 计本林伟5 人员', '2019-04-30 11:41:38', '成功', '');
INSERT INTO `sys_log` VALUES (67, 'admin', '', '添加 计本林伟6 人员', '2019-04-30 11:42:03', '成功', '');
INSERT INTO `sys_log` VALUES (68, 'admin', '', '添加 计本林伟7 人员', '2019-04-30 11:42:29', '成功', '');
INSERT INTO `sys_log` VALUES (69, 'admin', '', '添加 测试1 岗位', '2019-04-30 11:42:51', '成功', '');
INSERT INTO `sys_log` VALUES (70, 'admin', '', '添加 计本林伟8 人员', '2019-04-30 11:42:51', '成功', '');
INSERT INTO `sys_log` VALUES (71, 'admin', '', ' 删除 [] 岗位', '2019-04-30 11:42:54', '成功', '');
INSERT INTO `sys_log` VALUES (72, 'admin', '', '添加 计本林伟9 人员', '2019-04-30 11:43:14', '成功', '');
INSERT INTO `sys_log` VALUES (73, 'admin', '', '添加 计本林伟10 人员', '2019-04-30 11:43:41', '成功', '');
INSERT INTO `sys_log` VALUES (74, 'admin', '', '添加 数本林伟1 人员', '2019-04-30 11:44:11', '成功', '');
INSERT INTO `sys_log` VALUES (75, 'admin', '', '添加 数本林伟2 人员', '2019-04-30 11:44:33', '成功', '');
INSERT INTO `sys_log` VALUES (76, 'admin', '', '添加 数本林伟3 人员', '2019-04-30 11:44:50', '成功', '');
INSERT INTO `sys_log` VALUES (77, 'admin', '', '添加 数本林伟4 人员', '2019-04-30 11:45:19', '成功', '');
INSERT INTO `sys_log` VALUES (78, 'admin', '', '添加 数本林伟5 人员', '2019-04-30 11:45:44', '成功', '');
INSERT INTO `sys_log` VALUES (79, 'admin', '', '添加 [计本林伟1, 计本林伟2, 计本林伟3, 计本林伟4, 计本林伟5, 计本林伟6, 计本林伟7, 计本林伟8, 计本林伟9, 计本林伟10, 数本林伟1, 数本林伟2, 数本林伟3, 数本林伟4, 数本林伟5] 人员', '2019-04-30 11:46:00', '成功', '');
INSERT INTO `sys_log` VALUES (80, 'admin', '', '添加 传播 职务', '2019-04-30 13:54:26', '成功', '');
INSERT INTO `sys_log` VALUES (81, 'admin', '', '添加 发送 职务', '2019-04-30 13:54:35', '成功', '');
INSERT INTO `sys_log` VALUES (82, 'admin', '', '添加 美术 职务', '2019-04-30 13:54:50', '成功', '');
INSERT INTO `sys_log` VALUES (83, 'admin', '', '添加 巡考 岗位', '2019-04-30 14:07:41', '成功', '');
INSERT INTO `sys_log` VALUES (84, 'admin', '', '更新 巡考 岗位', '2019-04-30 14:07:46', '成功', '');
INSERT INTO `sys_log` VALUES (85, 'admin', '', '更新 监考 岗位', '2019-04-30 14:07:51', '成功', '');
INSERT INTO `sys_log` VALUES (86, 'admin', '', '更新 监考 岗位', '2019-04-30 14:07:56', '成功', '');
INSERT INTO `sys_log` VALUES (87, 'admin', '', '更新 保洁 岗位', '2019-04-30 14:08:08', '成功', '');
INSERT INTO `sys_log` VALUES (88, 'admin', '', '更新 出卷 岗位', '2019-04-30 14:09:09', '成功', '');
INSERT INTO `sys_log` VALUES (89, 'admin', '', '更新 出卷 岗位', '2019-04-30 14:09:14', '成功', '');
INSERT INTO `sys_log` VALUES (90, 'admin', '', '更新 保洁1 岗位', '2019-04-30 14:09:20', '成功', '');
INSERT INTO `sys_log` VALUES (91, 'admin', '', ' 删除 [] 岗位', '2019-04-30 14:11:50', '成功', '');
INSERT INTO `sys_log` VALUES (92, 'admin', '', ' 删除 [] 岗位', '2019-04-30 14:11:57', '成功', '');
INSERT INTO `sys_log` VALUES (93, 'admin', '', ' 删除 [] 岗位', '2019-04-30 14:12:02', '成功', '');
INSERT INTO `sys_log` VALUES (94, 'admin', '', '添加 监考 岗位', '2019-04-30 14:14:14', '成功', '');
INSERT INTO `sys_log` VALUES (95, 'admin', '', '添加 巡考 岗位', '2019-04-30 14:14:22', '成功', '');
INSERT INTO `sys_log` VALUES (96, 'admin', '', '添加 出题 岗位', '2019-04-30 14:14:29', '成功', '');
INSERT INTO `sys_log` VALUES (97, 'admin', '', '添加 审题 岗位', '2019-04-30 14:14:36', '成功', '');
INSERT INTO `sys_log` VALUES (98, 'admin', '', '添加 保洁 岗位', '2019-04-30 14:14:43', '成功', '');
INSERT INTO `sys_log` VALUES (99, 'admin', '', '更新 监考 岗位', '2019-04-30 14:14:47', '成功', '');
INSERT INTO `sys_log` VALUES (100, 'admin', '', '更新 巡考 岗位', '2019-04-30 14:14:53', '成功', '');
INSERT INTO `sys_log` VALUES (101, 'admin', '', '更新 出题 岗位', '2019-04-30 14:15:00', '成功', '');
INSERT INTO `sys_log` VALUES (102, 'admin', '', '更新 审题 岗位', '2019-04-30 14:15:07', '成功', '');
INSERT INTO `sys_log` VALUES (103, 'admin', '', '更新 保洁 岗位', '2019-04-30 14:15:14', '成功', '');
INSERT INTO `sys_log` VALUES (104, 'admin', '', '上传学生数据', '2019-04-30 14:15:36', '成功', '');
INSERT INTO `sys_log` VALUES (105, 'admin', '', '上传学生数据', '2019-04-30 14:15:55', '成功', '');
INSERT INTO `sys_log` VALUES (106, 'admin', '', '删除 人文1--104 考室', '2019-04-30 14:16:09', '成功', '');
INSERT INTO `sys_log` VALUES (107, 'admin', '', '删除 人文1-102 考室', '2019-04-30 14:16:11', '成功', '');
INSERT INTO `sys_log` VALUES (108, 'admin', '', '删除 人文1-103 考室', '2019-04-30 14:16:13', '成功', '');
INSERT INTO `sys_log` VALUES (109, 'admin', '', '删除 志广1-101 考室', '2019-04-30 14:16:16', '成功', '');
INSERT INTO `sys_log` VALUES (110, 'admin', '', '删除 志广1-102 考室', '2019-04-30 14:16:18', '成功', '');
INSERT INTO `sys_log` VALUES (111, 'admin', '', '删除 志广1-103 考室', '2019-04-30 14:16:21', '成功', '');
INSERT INTO `sys_log` VALUES (112, 'admin', '', '删除 成功楼1-101 考室', '2019-04-30 14:16:23', '成功', '');
INSERT INTO `sys_log` VALUES (113, 'admin', '', '删除 成功楼1-102 考室', '2019-04-30 14:16:26', '成功', '');
INSERT INTO `sys_log` VALUES (114, 'admin', '', '删除 成功楼1-103 考室', '2019-04-30 14:16:30', '成功', '');
INSERT INTO `sys_log` VALUES (115, 'admin', '', '删除 理工楼1-101 考室', '2019-04-30 14:16:32', '成功', '');
INSERT INTO `sys_log` VALUES (116, 'admin', '', '删除 理工楼1-102 考室', '2019-04-30 14:16:37', '成功', '');
INSERT INTO `sys_log` VALUES (117, 'admin', '', '删除 理工楼1-103 考室', '2019-04-30 14:16:39', '成功', '');
INSERT INTO `sys_log` VALUES (118, 'admin', '', '删除 知名1-101 考室', '2019-04-30 14:16:42', '成功', '');
INSERT INTO `sys_log` VALUES (119, 'admin', '', '删除 知名1-101 考室', '2019-04-30 14:16:44', '成功', '');
INSERT INTO `sys_log` VALUES (120, 'admin', '', '删除 笃行1-101 考室', '2019-04-30 14:16:46', '成功', '');
INSERT INTO `sys_log` VALUES (121, 'admin', '', '删除 笃行1-102 考室', '2019-04-30 14:16:49', '成功', '');
INSERT INTO `sys_log` VALUES (122, 'admin', '', '删除 笃行1-103 考室', '2019-04-30 14:16:51', '成功', '');
INSERT INTO `sys_log` VALUES (123, 'admin', '', '添加 仓山机考1 考室', '2019-04-30 14:17:48', '成功', '');
INSERT INTO `sys_log` VALUES (124, 'admin', '', '添加 仓山机考2 考室', '2019-04-30 14:18:08', '成功', '');
INSERT INTO `sys_log` VALUES (125, 'admin', '', '添加 协和机考1 考室', '2019-04-30 14:18:18', '成功', '');
INSERT INTO `sys_log` VALUES (126, 'admin', '', '添加 协和机考2 考室', '2019-04-30 14:18:28', '成功', '');
INSERT INTO `sys_log` VALUES (127, 'admin', '', '添加 旗山机考1 考室', '2019-04-30 14:18:53', '成功', '');
INSERT INTO `sys_log` VALUES (128, 'admin', '', '添加 旗山机考2 考室', '2019-04-30 14:19:19', '成功', '');
INSERT INTO `sys_log` VALUES (129, 'admin', '', '添加 协和笔试1 考室', '2019-04-30 14:19:33', '成功', '');
INSERT INTO `sys_log` VALUES (130, 'admin', '', '添加 协和笔试2 考室', '2019-04-30 14:19:41', '成功', '');
INSERT INTO `sys_log` VALUES (131, 'admin', '', '添加 旗山笔试1 考室', '2019-04-30 14:19:50', '成功', '');
INSERT INTO `sys_log` VALUES (132, 'admin', '', '添加 旗山笔试2 考室', '2019-04-30 14:20:02', '成功', '');
INSERT INTO `sys_log` VALUES (133, 'admin', '', '添加 仓山笔试1 考室', '2019-04-30 14:20:13', '成功', '');
INSERT INTO `sys_log` VALUES (134, 'admin', '', '添加 仓山笔试2 考室', '2019-04-30 14:20:22', '成功', '');
INSERT INTO `sys_log` VALUES (135, 'admin', '', '修改 仓山笔试2 考室信息', '2019-04-30 14:20:33', '成功', '');
INSERT INTO `sys_log` VALUES (136, 'admin', '', '上传学生数据', '2019-04-30 14:28:20', '成功', '');
INSERT INTO `sys_log` VALUES (137, 'admin', '', '下载了 4.30测试 岗位报名表', '2019-04-30 14:29:52', '成功', '');
INSERT INTO `sys_log` VALUES (138, 'admin', '', '添加 [计本林伟1, 计本林伟2, 计本林伟3, 计本林伟4, 计本林伟5, 计本林伟6, 计本林伟7, 计本林伟8, 计本林伟9, 计本林伟10, 数本林伟1, 数本林伟2, 数本林伟3, 数本林伟4, 数本林伟5] 人员', '2019-04-30 14:30:15', '成功', '');
INSERT INTO `sys_log` VALUES (139, 'admin', '', '添加 [计本林伟1, 计本林伟2, 计本林伟3, 计本林伟4, 计本林伟5, 计本林伟6, 计本林伟7, 计本林伟8, 计本林伟9, 计本林伟10, 数本林伟1, 数本林伟2, 数本林伟3, 数本林伟4, 数本林伟5] 人员', '2019-04-30 14:30:37', '成功', '');
INSERT INTO `sys_log` VALUES (140, 'admin', '', '上传学生数据', '2019-04-30 14:30:52', '成功', '');
INSERT INTO `sys_log` VALUES (141, 'admin', '', '打印了 4.30测试 考试的准考证', '2019-04-30 14:53:54', '成功', '');
INSERT INTO `sys_log` VALUES (142, 'admin', '', '打印了 4.30测试 考试的准考证', '2019-04-30 14:54:03', '成功', '');
INSERT INTO `sys_log` VALUES (143, 'admin', '', '打印了 4.30测试 考试的准考证', '2019-04-30 14:55:19', '成功', '');
INSERT INTO `sys_log` VALUES (144, 'admin', '', '打印了 4.30测试 考试的准考证', '2019-04-30 14:55:38', '成功', '');
INSERT INTO `sys_log` VALUES (145, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 15:04:57', '成功', '');
INSERT INTO `sys_log` VALUES (146, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 15:11:56', '成功', '');
INSERT INTO `sys_log` VALUES (147, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 15:13:08', '成功', '');
INSERT INTO `sys_log` VALUES (148, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 15:14:43', '成功', '');
INSERT INTO `sys_log` VALUES (149, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 15:15:58', '成功', '');
INSERT INTO `sys_log` VALUES (150, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 15:18:45', '成功', '');
INSERT INTO `sys_log` VALUES (151, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 15:19:53', '成功', '');
INSERT INTO `sys_log` VALUES (152, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 15:23:41', '成功', '');
INSERT INTO `sys_log` VALUES (153, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 15:47:47', '成功', '');
INSERT INTO `sys_log` VALUES (154, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 15:49:11', '成功', '');
INSERT INTO `sys_log` VALUES (155, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 15:57:41', '成功', '');
INSERT INTO `sys_log` VALUES (156, 'admin', '', '上传学生数据', '2019-04-30 16:37:08', '成功', '');
INSERT INTO `sys_log` VALUES (157, 'admin', '', '添加 [计本林伟1, 计本林伟2, 计本林伟3, 计本林伟4, 计本林伟5, 计本林伟6, 计本林伟7, 计本林伟8, 计本林伟9, 计本林伟10] 人员', '2019-04-30 16:38:20', '成功', '');
INSERT INTO `sys_log` VALUES (158, 'admin', '', '修改 数信 职务', '2019-04-30 17:16:10', '成功', '');
INSERT INTO `sys_log` VALUES (159, 'admin', '', '添加 kjr 人员', '2019-04-30 17:18:32', '成功', '');
INSERT INTO `sys_log` VALUES (160, 'admin', '', '修改 boyqj 职务', '2019-04-30 17:19:31', '成功', '');
INSERT INTO `sys_log` VALUES (161, 'admin', '', '修改 传播 职务', '2019-04-30 17:19:47', '成功', '');
INSERT INTO `sys_log` VALUES (162, 'admin', '', '修改 辅导员 职务', '2019-04-30 17:20:12', '成功', '');
INSERT INTO `sys_log` VALUES (163, 'admin', '', '修改 班主任 职务', '2019-04-30 17:20:23', '成功', '');
INSERT INTO `sys_log` VALUES (164, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 18:54:57', '成功', '');
INSERT INTO `sys_log` VALUES (165, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 18:57:46', '成功', '');
INSERT INTO `sys_log` VALUES (166, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 19:09:32', '成功', '');
INSERT INTO `sys_log` VALUES (167, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 19:29:42', '成功', '');
INSERT INTO `sys_log` VALUES (168, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 19:29:49', '成功', '');
INSERT INTO `sys_log` VALUES (169, 'anonymousUser', '', '通过考场号 0012019430002 登录', '2019-04-30 19:30:38', '成功', '');
INSERT INTO `sys_log` VALUES (170, 'anonymousUser', '', '准考证为 351022182217416 的签到', '2019-05-05 14:32:09', '成功', '');
INSERT INTO `sys_log` VALUES (171, 'anonymousUser', '', '准考证为 351022182217416 的签到', '2019-05-05 15:32:26', '成功', '');
INSERT INTO `sys_log` VALUES (172, 'admin', '', '修改 仓山机考吼吼吼 考室信息', '2019-05-06 10:17:08', '成功', '');
INSERT INTO `sys_log` VALUES (173, 'admin', '', '打印了 4.30测试 考试的准考证', '2019-05-06 10:39:40', '成功', '');
INSERT INTO `sys_log` VALUES (174, 'admin', '', '添加   呼呼 职务', '2019-05-06 10:42:10', '成功', '');
INSERT INTO `sys_log` VALUES (175, 'admin', '', '添加 123123 职务', '2019-05-06 10:52:52', '成功', '');
INSERT INTO `sys_log` VALUES (176, 'admin', '', '修改 123123 职务', '2019-05-06 10:53:16', '成功', '');
INSERT INTO `sys_log` VALUES (177, 'admin', '', '打印了 4.30测试 考试的准考证', '2019-05-06 12:22:37', '成功', '');
INSERT INTO `sys_log` VALUES (178, 'admin', '127.0.0.1', '添加 hhh 人员', '2019-05-07 09:04:20', '成功', '');
INSERT INTO `sys_log` VALUES (179, 'admin', '127.0.0.1', '更新字典 农业银行1 数据', '2019-05-07 09:04:44', '成功', '');
INSERT INTO `sys_log` VALUES (180, 'admin', '127.0.0.1', '更新字典 农业银行 数据', '2019-05-07 09:09:21', '成功', '');
INSERT INTO `sys_log` VALUES (181, 'admin', '127.0.0.1', '添加 qwe 职务', '2019-05-07 09:14:25', '成功', '');
INSERT INTO `sys_log` VALUES (182, 'admin', '127.0.0.1', '添加 hhh 职务', '2019-05-07 09:15:39', '成功', '');
INSERT INTO `sys_log` VALUES (183, 'admin', '127.0.0.1', '修改 boyqj 职务', '2019-05-07 09:17:57', '成功', '');
INSERT INTO `sys_log` VALUES (184, 'admin', '127.0.0.1', '修改 物能 职务', '2019-05-07 09:18:07', '成功', '');
INSERT INTO `sys_log` VALUES (185, 'admin', '127.0.0.1', '修改 化材 职务', '2019-05-07 09:18:14', '成功', '');
INSERT INTO `sys_log` VALUES (186, 'admin', '127.0.0.1', '上传学生数据', '2019-05-07 09:20:03', '成功', '');
INSERT INTO `sys_log` VALUES (187, 'admin', '127.0.0.1', '获取下载成绩导入模板', '2019-05-07 09:20:11', '成功', '');
INSERT INTO `sys_log` VALUES (188, 'admin', '127.0.0.1', '获取下载成绩导入模板', '2019-05-07 09:20:20', '成功', '');
INSERT INTO `sys_log` VALUES (189, 'admin', '127.0.0.1', '获取下载成绩导入模板', '2019-05-07 09:20:37', '成功', '');
INSERT INTO `sys_log` VALUES (190, 'admin', '127.0.0.1', '获取下载成绩导入模板', '2019-05-07 09:20:57', '成功', '');
INSERT INTO `sys_log` VALUES (191, 'admin', '127.0.0.1', '下载了 4.30测试 岗位报名表', '2019-05-07 09:21:09', '成功', '');
INSERT INTO `sys_log` VALUES (192, 'admin', '127.0.0.1', '删除  [] 职务', '2019-05-07 09:58:06', '成功', '');
INSERT INTO `sys_log` VALUES (193, 'admin', '127.0.0.1', '修改 大神 职务', '2019-05-07 09:58:18', '成功', '');
INSERT INTO `sys_log` VALUES (194, 'admin', '127.0.0.1', '修改 大神1 职务', '2019-05-07 09:58:30', '成功', '');
INSERT INTO `sys_log` VALUES (195, 'admin', '127.0.0.1', '删除  大神1 职务', '2019-05-07 09:58:44', '失败', '组内有成员不允许删除职务');
INSERT INTO `sys_log` VALUES (196, 'admin', '127.0.0.1', '修改 lzh 用户的使用状态', '2019-05-07 09:58:51', '成功', '');
INSERT INTO `sys_log` VALUES (197, 'admin', '127.0.0.1', '修改 lwn 用户的使用状态', '2019-05-07 09:58:59', '成功', '');
INSERT INTO `sys_log` VALUES (198, 'admin', '127.0.0.1', '修改 物能 职务', '2019-05-07 09:59:17', '成功', '');
INSERT INTO `sys_log` VALUES (199, 'admin', '127.0.0.1', '修改 计本林伟1 人员', '2019-05-07 10:00:12', '成功', '');
INSERT INTO `sys_log` VALUES (200, 'admin', '127.0.0.1', '修改 计本林伟1 人员', '2019-05-07 10:02:10', '成功', '');
INSERT INTO `sys_log` VALUES (201, 'admin', '127.0.0.1', '删除字典 [] 数据', '2019-05-07 10:02:20', '成功', '');
INSERT INTO `sys_log` VALUES (202, 'admin', '127.0.0.1', '修改 物能 职务', '2019-05-07 10:03:43', '成功', '');
INSERT INTO `sys_log` VALUES (203, 'admin', '127.0.0.1', '添加 ybtc 职务', '2019-05-07 10:04:33', '成功', '');
INSERT INTO `sys_log` VALUES (204, 'admin', '127.0.0.1', '添加 wawawa 职务', '2019-05-07 10:04:51', '成功', '');
INSERT INTO `sys_log` VALUES (205, 'admin', '127.0.0.1', '添加 abc 人员', '2019-05-07 10:05:43', '成功', '');
INSERT INTO `sys_log` VALUES (206, 'admin', '127.0.0.1', '添加 wowowo 人员', '2019-05-07 10:07:09', '成功', '');
INSERT INTO `sys_log` VALUES (207, 'admin', '127.0.0.1', '修改 物能 职务', '2019-05-07 10:08:36', '成功', '');
INSERT INTO `sys_log` VALUES (208, 'admin', '127.0.0.1', '下载了 4.30测试 岗位报名表', '2019-05-07 10:08:45', '成功', '');
INSERT INTO `sys_log` VALUES (209, 'admin', '127.0.0.1', '添加 黑暗之神 职务', '2019-05-07 10:16:59', '成功', '');
INSERT INTO `sys_log` VALUES (210, 'admin', '127.0.0.1', '修改 物能 职务', '2019-05-07 10:17:24', '成功', '');
INSERT INTO `sys_log` VALUES (211, 'admin', '127.0.0.1', '添加 [计本林伟1, 计本林伟2, 计本林伟3, 计本林伟4, 计本林伟5, 计本林伟6, 计本林伟7, 计本林伟8, 计本林伟9, 计本林伟10] 人员', '2019-05-07 10:19:00', '成功', '');
INSERT INTO `sys_log` VALUES (212, 'admin', '192.168.1.158', '修改 超级管理员 的密码', '2019-05-07 11:06:38', '成功', '');
INSERT INTO `sys_log` VALUES (213, 'admin', '192.168.1.158', '上传学生数据', '2019-05-07 11:08:00', '成功', '');
INSERT INTO `sys_log` VALUES (214, 'admin', '192.168.1.158', '删除 仓山笔试1 考室', '2019-05-07 11:08:56', '成功', '');
INSERT INTO `sys_log` VALUES (215, 'admin', '192.168.1.158', '删除 仓山机考2 考室', '2019-05-07 11:16:26', '成功', '');
INSERT INTO `sys_log` VALUES (216, 'admin', '192.168.1.158', '添加 呜啦啦 职务', '2019-05-07 11:17:54', '成功', '');
INSERT INTO `sys_log` VALUES (217, 'admin', '192.168.1.158', '修改 呜啦啦1 职务', '2019-05-07 11:18:10', '成功', '');
INSERT INTO `sys_log` VALUES (218, 'admin', '192.168.1.158', '添加 吼吼吼 职务', '2019-05-07 11:20:41', '成功', '');
INSERT INTO `sys_log` VALUES (219, 'admin', '192.168.1.158', '删除  null 职务', '2019-05-07 11:24:10', '成功', '');
INSERT INTO `sys_log` VALUES (220, 'admin', '192.168.1.158', '添加 我我我 职务', '2019-05-07 11:24:43', '成功', '');
INSERT INTO `sys_log` VALUES (221, 'admin', '127.0.0.1', '删除 仓山机考吼吼吼 考室', '2019-05-07 13:31:16', '成功', '');
INSERT INTO `sys_log` VALUES (222, 'admin', '127.0.0.1', '删除 仓山笔试2 考室', '2019-05-07 13:31:19', '成功', '');
INSERT INTO `sys_log` VALUES (223, 'admin', '127.0.0.1', '删除 协和机考1 考室', '2019-05-07 13:31:23', '成功', '');
INSERT INTO `sys_log` VALUES (224, 'admin', '127.0.0.1', '删除 协和机考2 考室', '2019-05-07 13:31:26', '成功', '');
INSERT INTO `sys_log` VALUES (225, 'admin', '127.0.0.1', '删除 协和笔试1 考室', '2019-05-07 13:31:28', '成功', '');
INSERT INTO `sys_log` VALUES (226, 'admin', '127.0.0.1', '删除 协和笔试2 考室', '2019-05-07 13:31:30', '成功', '');
INSERT INTO `sys_log` VALUES (227, 'admin', '127.0.0.1', '删除 旗山机考1 考室', '2019-05-07 13:31:33', '成功', '');
INSERT INTO `sys_log` VALUES (228, 'admin', '127.0.0.1', '删除 旗山机考2 考室', '2019-05-07 13:31:35', '成功', '');
INSERT INTO `sys_log` VALUES (229, 'admin', '127.0.0.1', '删除 旗山笔试1 考室', '2019-05-07 13:31:39', '成功', '');
INSERT INTO `sys_log` VALUES (230, 'admin', '127.0.0.1', '删除 旗山笔试2 考室', '2019-05-07 13:31:42', '成功', '');
INSERT INTO `sys_log` VALUES (231, 'admin', '192.168.1.158', '添加 协和笔试1 考室', '2019-05-07 13:36:02', '成功', '');
INSERT INTO `sys_log` VALUES (232, 'admin', '192.168.1.158', '修改 化材 职务', '2019-05-07 13:37:33', '成功', '');
INSERT INTO `sys_log` VALUES (233, 'admin', '127.0.0.1', '删除 [] 人员', '2019-05-07 13:38:32', '成功', '');
INSERT INTO `sys_log` VALUES (234, 'admin', '192.168.1.158', '删除  null 职务', '2019-05-07 13:45:05', '成功', '');
INSERT INTO `sys_log` VALUES (235, 'admin', '192.168.1.158', '删除  null 职务', '2019-05-07 13:45:10', '成功', '');
INSERT INTO `sys_log` VALUES (236, 'admin', '127.0.0.1', '删除 [] 人员', '2019-05-07 13:45:57', '成功', '');
INSERT INTO `sys_log` VALUES (237, 'admin', '192.168.1.158', '上传学生数据', '2019-05-07 13:50:34', '成功', '');
INSERT INTO `sys_log` VALUES (238, 'admin', '192.168.1.158', '添加 协和笔试2 考室', '2019-05-07 13:50:55', '成功', '');
INSERT INTO `sys_log` VALUES (239, 'admin', '192.168.1.158', '添加 协和机考1 考室', '2019-05-07 13:51:04', '成功', '');
INSERT INTO `sys_log` VALUES (240, 'admin', '192.168.1.158', '添加 协和机考2 考室', '2019-05-07 13:51:13', '成功', '');
INSERT INTO `sys_log` VALUES (241, 'admin', '192.168.1.158', '添加 协和笔试3 考室', '2019-05-07 13:51:23', '成功', '');
INSERT INTO `sys_log` VALUES (242, 'admin', '192.168.1.158', '添加 协和机考3 考室', '2019-05-07 13:51:31', '成功', '');
INSERT INTO `sys_log` VALUES (243, 'admin', '192.168.1.158', '添加 哇哇哇 职务', '2019-05-07 13:52:28', '成功', '');
INSERT INTO `sys_log` VALUES (244, 'admin', '192.168.1.158', '删除  哇哇哇 职务', '2019-05-07 13:52:45', '成功', '');
INSERT INTO `sys_log` VALUES (245, 'admin', '192.168.1.158', '删除  ybtc 职务', '2019-05-07 13:52:58', '成功', '');
INSERT INTO `sys_log` VALUES (246, 'admin', '192.168.1.158', '删除  经院 职务', '2019-05-07 13:53:09', '成功', '');
INSERT INTO `sys_log` VALUES (247, 'admin', '192.168.1.158', '删除  我我我 职务', '2019-05-07 13:53:15', '成功', '');
INSERT INTO `sys_log` VALUES (248, 'admin', '192.168.1.158', '删除  boyqj 职务', '2019-05-07 13:53:19', '成功', '');
INSERT INTO `sys_log` VALUES (249, 'admin', '192.168.1.158', '删除  123123 职务', '2019-05-07 13:53:23', '成功', '');
INSERT INTO `sys_log` VALUES (250, 'admin', '192.168.1.158', '删除  协和 职务', '2019-05-07 13:53:26', '成功', '');
INSERT INTO `sys_log` VALUES (251, 'admin', '192.168.1.158', '修改 数信 职务', '2019-05-07 13:53:37', '成功', '');
INSERT INTO `sys_log` VALUES (252, 'admin', '192.168.1.158', '删除  物能 职务', '2019-05-07 13:53:42', '成功', '');
INSERT INTO `sys_log` VALUES (253, 'admin', '192.168.1.158', '删除 [] 人员', '2019-05-07 13:54:15', '成功', '');
INSERT INTO `sys_log` VALUES (254, 'admin', '192.168.1.158', '删除  美术 职务', '2019-05-07 13:54:21', '成功', '');
INSERT INTO `sys_log` VALUES (255, 'admin', '192.168.1.158', '添加 仓山笔试1 考室', '2019-05-07 13:55:04', '成功', '');
INSERT INTO `sys_log` VALUES (256, 'admin', '192.168.1.158', '添加 仓山笔试2 考室', '2019-05-07 13:55:19', '成功', '');
INSERT INTO `sys_log` VALUES (257, 'admin', '192.168.1.158', '添加 仓山机考1 考室', '2019-05-07 13:55:31', '成功', '');
INSERT INTO `sys_log` VALUES (258, 'admin', '192.168.1.158', '添加 仓山机考2 考室', '2019-05-07 13:55:41', '成功', '');
INSERT INTO `sys_log` VALUES (259, 'admin', '192.168.1.158', '添加 旗山机考1 考室', '2019-05-07 13:55:52', '成功', '');
INSERT INTO `sys_log` VALUES (260, 'admin', '192.168.1.158', '添加 旗山机考2 考室', '2019-05-07 13:56:04', '成功', '');
INSERT INTO `sys_log` VALUES (261, 'admin', '192.168.1.158', '添加 旗山笔试1 考室', '2019-05-07 13:56:13', '成功', '');
INSERT INTO `sys_log` VALUES (262, 'admin', '192.168.1.158', '添加 旗山笔试2 考室', '2019-05-07 13:56:22', '成功', '');
INSERT INTO `sys_log` VALUES (263, 'admin', '192.168.1.158', '添加 仓山机考3 考室', '2019-05-07 13:56:57', '成功', '');
INSERT INTO `sys_log` VALUES (264, 'admin', '192.168.1.158', '添加 仓山笔试3 考室', '2019-05-07 13:57:09', '成功', '');
INSERT INTO `sys_log` VALUES (265, 'admin', '192.168.1.158', '添加 旗山机考3 考室', '2019-05-07 13:57:19', '成功', '');
INSERT INTO `sys_log` VALUES (266, 'admin', '192.168.1.158', '添加 旗山笔试3 考室', '2019-05-07 13:57:28', '成功', '');
INSERT INTO `sys_log` VALUES (267, 'admin', '192.168.1.158', '添加 监考前 岗位', '2019-05-07 13:58:30', '成功', '');
INSERT INTO `sys_log` VALUES (268, 'admin', '192.168.1.158', '添加 监考后 岗位', '2019-05-07 13:58:36', '成功', '');
INSERT INTO `sys_log` VALUES (269, 'admin', '192.168.1.158', '添加 出卷 岗位', '2019-05-07 13:58:47', '成功', '');
INSERT INTO `sys_log` VALUES (270, 'admin', '192.168.1.158', '添加 改卷 岗位', '2019-05-07 13:59:02', '成功', '');
INSERT INTO `sys_log` VALUES (271, 'admin', '192.168.1.158', '添加 巡考 岗位', '2019-05-07 13:59:12', '成功', '');
INSERT INTO `sys_log` VALUES (272, 'admin', '192.168.1.158', '添加 保洁 岗位', '2019-05-07 13:59:19', '成功', '');
INSERT INTO `sys_log` VALUES (273, 'admin', '192.168.1.158', '添加 测试 岗位', '2019-05-07 13:59:40', '成功', '');
INSERT INTO `sys_log` VALUES (274, 'admin', '192.168.1.158', '更新 测试123 岗位', '2019-05-07 13:59:45', '成功', '');
INSERT INTO `sys_log` VALUES (275, 'admin', '192.168.1.158', ' 删除 [] 岗位', '2019-05-07 13:59:49', '成功', '');
INSERT INTO `sys_log` VALUES (276, 'admin', '192.168.1.158', '修改 协和机考3 考室信息', '2019-05-07 14:00:55', '成功', '');
INSERT INTO `sys_log` VALUES (277, 'admin', '192.168.1.158', '添加 保安 岗位', '2019-05-07 14:05:03', '成功', '');
INSERT INTO `sys_log` VALUES (278, 'admin', '192.168.1.158', '添加 监控 岗位', '2019-05-07 14:05:17', '成功', '');
INSERT INTO `sys_log` VALUES (279, 'admin', '192.168.1.158', '添加 候补 岗位', '2019-05-07 14:05:30', '成功', '');
INSERT INTO `sys_log` VALUES (280, 'admin', '127.0.0.1', '下载了 5.7机考测试 岗位报名表', '2019-05-07 14:09:54', '成功', '');
INSERT INTO `sys_log` VALUES (281, 'admin', '127.0.0.1', '添加 [计本林伟1, 计本林伟2, 计本林伟3, 计本林伟4, 计本林伟5, 计本林伟6, 计本林伟7, 计本林伟8, 计本林伟9, 计本林伟10, 数本林伟1, 数本林伟2, 数本林伟3, 数本林伟4, 数本林伟5] 人员', '2019-05-07 14:10:45', '成功', '');
INSERT INTO `sys_log` VALUES (282, 'admin', '192.168.1.158', '修改 数本 职务', '2019-05-07 15:04:11', '成功', '');
INSERT INTO `sys_log` VALUES (283, 'admin', '192.168.1.158', '添加 zmj 人员', '2019-05-07 15:12:11', '成功', '');
INSERT INTO `sys_log` VALUES (284, 'admin', '192.168.1.158', '修改 zmj12 人员', '2019-05-07 15:12:24', '成功', '');
INSERT INTO `sys_log` VALUES (285, 'admin', '192.168.1.158', '修改 zmj12 人员', '2019-05-07 15:12:31', '成功', '');
INSERT INTO `sys_log` VALUES (286, 'admin', '192.168.1.158', '删除 [] 人员', '2019-05-07 15:12:48', '成功', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_code` varchar(100) NOT NULL DEFAULT '',
  `role_name` varchar(100) DEFAULT '',
  `remark` varchar(100) DEFAULT '',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `operator` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`role_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('5cc10a972c5a2660cc645bc3', '辅导员', '', '2019-04-25 09:17:11', '2019-04-30 17:20:12', 'admin');
INSERT INTO `sys_role` VALUES ('5cc10c2b2c5a2660cc645bc4', '班主任', '', '2019-04-25 09:23:56', '2019-04-30 17:20:23', 'admin');
INSERT INTO `sys_role` VALUES ('5cc10c3d2c5a2660cc645bc5', '任课老师', '', '2019-04-25 09:24:13', '2019-04-25 09:24:13', 'admin');
INSERT INTO `sys_role` VALUES ('5cc10c4b2c5a2660cc645bc6', '班长', '', '2019-04-25 09:24:27', '2019-04-25 09:24:27', 'admin');
INSERT INTO `sys_role` VALUES ('5cc10c9a2c5a2660cc645bc7', '人员', '', '2019-04-25 09:25:46', '2019-04-25 09:42:39', 'admin');
INSERT INTO `sys_role` VALUES ('5cc2e6322c5a264d00046a45', '学委', '', '2019-04-26 19:06:27', '2019-04-26 19:20:57', 'admin');
INSERT INTO `sys_role` VALUES ('5cc4049f2c5a260b7e2a256d', '大神1', '1', '2019-04-27 15:28:32', '2019-05-07 09:58:30', 'admin');
INSERT INTO `sys_role` VALUES ('5cd0ea9b2c5a2676764f1b65', '黑暗之神', '呜呼拉黑', '2019-05-07 10:17:00', '2019-05-07 10:17:00', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_acl`;
CREATE TABLE `sys_role_acl` (
  `role_code` varchar(100) NOT NULL,
  `acl_code` varchar(9600) NOT NULL DEFAULT '' COMMENT '权限code字符串,使用英文逗号隔开',
  PRIMARY KEY (`role_code`) USING BTREE,
  KEY `fk_group_authorities_group` (`role_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色权限表';

-- ----------------------------
-- Records of sys_role_acl
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_acl` VALUES ('5cc10a972c5a2660cc645bc3', 'ADD_EXAM,DELETE_EXAM,SEARCH_EXAM,UPDATE_EXAM,UPDATE_EXAM_STATUS,SEARCH_BEFORE_EXAM,DELETE_GRADE,SEARCH_GRADE,ADD_ROLE,MODIFY_ROLE,DELETE_ROLE,SEARCH_ROLE,UPDATE_USER_USE_STATUS,SEARCH_DEPARTMENT,ADD_DEPTMENT,UPDATE_DEPARTMENT,DELETE_DEPARTMENT,SEARCH_USER,ADD_USER,UPDATE_USER,DELETE_USER,RESET_PASSWORD,SEARCH_LOG,SEARCH_PERSON,ADD_PERSON,MODIFY_PERSION,DELETE_PERSION,DELETE_CLASS_ROOM,SEARCH_CLASS_ROOM_INFO,ADD_CLASS_ROOM,UPDATE_CLASS_ROOM,UPDATE_USE_STATUS,ADD_POST,SEARCH_POST,UPDATE_POST,DELETE_POST,ADD_DICTIONARRY,DELETE_DICTIONARY,SEARCH_DICTIONARY,UPDATE_DICTIONARY,COLLEGE_SEARCH_DEPTMENT,COLLEGE_ADD_DEPTMENT,COLLEGE_UPDATE_DEPARTMENT,COLLEGE_DELETE_DEPARTMENT,COLLEGE_SEARCH_PERSON,COLLEGE_ADD_PERSON,COLLEGE_UPDATE_PERSON,COLLEGE_DELETE_PERSON,COLLEGE_SEARCH_EXAM');
INSERT INTO `sys_role_acl` VALUES ('5cc10c2b2c5a2660cc645bc4', 'ADD_EXAM,DELETE_EXAM,SEARCH_EXAM,UPDATE_EXAM,UPDATE_EXAM_STATUS,SEARCH_BEFORE_EXAM,DELETE_GRADE,SEARCH_GRADE,SEARCH_LOG,COLLEGE_SEARCH_EXAM');
INSERT INTO `sys_role_acl` VALUES ('5cc10c3d2c5a2660cc645bc5', 'ADD_EXAM,DELETE_EXAM,SEARCH_EXAM,UPDATE_EXAM,UPDATE_EXAM_STATUS,SEARCH_BEFORE_EXAM,DELETE_GRADE,SEARCH_GRADE,SEARCH_LOG');
INSERT INTO `sys_role_acl` VALUES ('5cc10c4b2c5a2660cc645bc6', 'ADD_ROLE,DELETE_ROLE,MODIFY_ROLE,SEARCH_ROLE,ADD_DEPTMENT,ADD_USER,DELETE_DEPARTMENT,DELETE_USER,RESET_PASSWORD,SEARCH_DEPARTMENT,SEARCH_USER,UPDATE_DEPARTMENT,UPDATE_USER,UPDATE_USER_USE_STATUS,SEARCH_LOG,ADD_PERSON,DELETE_PERSION,MODIFY_PERSION,SEARCH_PERSON,ADD_CLASS_ROOM,DELETE_CLASS_ROOM,SEARCH_CLASS_ROOM_INFO,UPDATE_CLASS_ROOM,UPDATE_USE_STATUS,ADD_POST,DELETE_POST,SEARCH_POST,UPDATE_POST,ADD_DICTIONARRY,DELETE_DICTIONARY,SEARCH_DICTIONARY,UPDATE_DICTIONARY');
INSERT INTO `sys_role_acl` VALUES ('5cc10c9a2c5a2660cc645bc7', 'DELETE_ROLE,MODIFY_ROLE,SEARCH_ROLE,SEARCH_LOG');
INSERT INTO `sys_role_acl` VALUES ('5cc2e6322c5a264d00046a45', 'UPDATE_USER_USE_STATUS,SEARCH_DEPARTMENT,ADD_DEPTMENT,UPDATE_DEPARTMENT,DELETE_DEPARTMENT,SEARCH_USER,ADD_USER,UPDATE_USER,DELETE_USER,RESET_PASSWORD,DATA_KEEP_SEARCH_DEPARTMETN,DATA_KEEP_ADD_DEPARTMENT,DATA_KEEP_UPDATE_DEPARTMETN,DATA_KEEP_DELETE_DEPARTMENT,SEARCH_PERSON,ADD_PERSON,MODIFY_PERSION,DELETE_PERSION,COLLEGE_SEARCH_DEPTMENT,COLLEGE_ADD_DEPTMENT,COLLEGE_UPDATE_DEPARTMENT,COLLEGE_DELETE_DEPARTMENT,COLLEGE_SEARCH_PERSON,COLLEGE_ADD_PERSON,COLLEGE_UPDATE_PERSON,COLLEGE_DELETE_PERSON,COLLEGE_SEARCH_EXAM');
INSERT INTO `sys_role_acl` VALUES ('5cc4049f2c5a260b7e2a256d', 'ADD_EXAM,DELETE_EXAM,SEARCH_EXAM,UPDATE_EXAM,UPDATE_EXAM_STATUS,SEARCH_BEFORE_EXAM,DELETE_GRADE,SEARCH_GRADE,ADD_ROLE,MODIFY_ROLE,DELETE_ROLE,SEARCH_ROLE,UPDATE_USER_USE_STATUS,SEARCH_DEPARTMENT,ADD_DEPTMENT,UPDATE_DEPARTMENT,DELETE_DEPARTMENT,SEARCH_USER,ADD_USER,UPDATE_USER,DELETE_USER,RESET_PASSWORD,SEARCH_LOG,DATA_KEEP_SEARCH_DEPARTMETN,DATA_KEEP_ADD_DEPARTMENT,DATA_KEEP_UPDATE_DEPARTMETN,DATA_KEEP_DELETE_DEPARTMENT,SEARCH_PERSON,ADD_PERSON,MODIFY_PERSION,DELETE_PERSION,DELETE_CLASS_ROOM,SEARCH_CLASS_ROOM_INFO,ADD_CLASS_ROOM,UPDATE_CLASS_ROOM,UPDATE_USE_STATUS,ADD_POST,SEARCH_POST,UPDATE_POST,DELETE_POST,ADD_DICTIONARRY,DELETE_DICTIONARY,SEARCH_DICTIONARY,UPDATE_DICTIONARY,COLLEGE_SEARCH_DEPTMENT,COLLEGE_ADD_DEPTMENT,COLLEGE_UPDATE_DEPARTMENT,COLLEGE_DELETE_DEPARTMENT,COLLEGE_SEARCH_PERSON,COLLEGE_ADD_PERSON,COLLEGE_UPDATE_PERSON,COLLEGE_DELETE_PERSON,COLLEGE_SEARCH_EXAM');
INSERT INTO `sys_role_acl` VALUES ('5cd0ea9b2c5a2676764f1b65', 'DELETE_GRADE,SEARCH_GRADE,ADD_ROLE,MODIFY_ROLE,DELETE_ROLE,SEARCH_ROLE');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) NOT NULL DEFAULT '',
  `role_code` varchar(100) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_group_members_group` (`role_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色用户表';

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_user` VALUES (150, '5cc40e7eef481bb426d6a02c', '5cc10a972c5a2660cc645bc3');
INSERT INTO `sys_role_user` VALUES (151, '5cc40e7eef481bb426d6a02c', '5cc10c3d2c5a2660cc645bc5');
INSERT INTO `sys_role_user` VALUES (152, '5cc40e9eef481bb426d6a02d', '5cc2e6322c5a264d00046a45');
INSERT INTO `sys_role_user` VALUES (153, '5cc40e9eef481bb426d6a02d', '5cc10c9a2c5a2660cc645bc7');
INSERT INTO `sys_role_user` VALUES (154, '5cc40ec6ef481bb426d6a02e', '5cc10c3d2c5a2660cc645bc5');
INSERT INTO `sys_role_user` VALUES (155, '5cc40ec6ef481bb426d6a02e', '5cc10c4b2c5a2660cc645bc6');
INSERT INTO `sys_role_user` VALUES (156, '5cc40eeeef481bb426d6a02f', '5cc10c4b2c5a2660cc645bc6');
INSERT INTO `sys_role_user` VALUES (157, '5cc40eeeef481bb426d6a02f', '5cc2e6322c5a264d00046a45');
INSERT INTO `sys_role_user` VALUES (160, '5cc40f54ef481bb426d6a031', '5cc10c3d2c5a2660cc645bc5');
INSERT INTO `sys_role_user` VALUES (161, '5cc40f54ef481bb426d6a031', '5cc10c4b2c5a2660cc645bc6');
INSERT INTO `sys_role_user` VALUES (162, '5cc40f7fef481bb426d6a032', '5cc10c9a2c5a2660cc645bc7');
INSERT INTO `sys_role_user` VALUES (163, '5cc40f36ef481bb426d6a030', '5cc4049f2c5a260b7e2a256d');
COMMIT;

-- ----------------------------
-- Table structure for sys_super_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_super_user`;
CREATE TABLE `sys_super_user` (
  `user_id` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='超级用户表';

-- ----------------------------
-- Records of sys_super_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_super_user` VALUES ('admin');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` varchar(100) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
  `is_locked` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '有效性',
  `verification_code` varchar(20) DEFAULT '' COMMENT '验证码，可用在注册、忘记密码、验证码登录等场合',
  `verification_code_generate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '验证码生成时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('5cc40e7eef481bb426d6a02c', '$2a$10$KVI7FLwY5taWVDKmVRvjPe7s6FJp1EVGK37YWxiw3j9sjC1EPkGhS', 0, '', '2019-05-07 09:58:51');
INSERT INTO `sys_user` VALUES ('5cc40e9eef481bb426d6a02d', '$2a$10$hNWOABRICEjZ3OVyUxNWoevJH6wA5CEKaMfIhXbPKGp6/LeQ65k8a', 0, '', '2019-05-07 09:58:59');
INSERT INTO `sys_user` VALUES ('5cc40ec6ef481bb426d6a02e', '$2a$10$y9bEiblu56LQrLcNG4WeR.IvToby2YcMXKujPTHmZtL3yu7d9z60e', 1, '', '2019-04-27 16:11:50');
INSERT INTO `sys_user` VALUES ('5cc40eeeef481bb426d6a02f', '$2a$10$pNUf9vmIgzfBtH9i9Ci6cO.lCH7AXRv3Zm3OZK/UY/muBZ0pnTeiG', 1, '', '2019-04-27 16:12:31');
INSERT INTO `sys_user` VALUES ('5cc40f36ef481bb426d6a030', '$2a$10$gYGV/Mwa2y.2sK81.BhvZuuiA5ClKS5YJ00RtLXgjBpOdb064nAoW', 1, '', '2019-04-27 16:13:42');
INSERT INTO `sys_user` VALUES ('5cc40f54ef481bb426d6a031', '$2a$10$LxnhQoRJklSbcO6XLwz7seXXX1.yTZPClS8SYnaWhMjKpfqm1bW4a', 1, '', '2019-04-27 16:14:12');
INSERT INTO `sys_user` VALUES ('5cc40f7fef481bb426d6a032', '$2a$10$4DjUvBt6BFutVLO7.dUAr.Zt1WeSAUSSdRzsjbF/t5ORAVUavuO1q', 1, '', '2019-04-27 16:14:55');
INSERT INTO `sys_user` VALUES ('admin', '$2a$10$6IHZx3MScmskdZkKK6dpWeZhsQKdrLLNFbA0ne8JSveQOBshbdD3i', 0, '', '2019-05-07 11:06:38');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_acl`;
CREATE TABLE `sys_user_acl` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) NOT NULL DEFAULT '',
  `acl_code` varchar(3000) NOT NULL DEFAULT '' COMMENT '权限code字符串使用英文逗号分隔',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1122051641617440770 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户权限表';

-- ----------------------------
-- Records of sys_user_acl
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_acl` VALUES (1122050408991186946, '5cc40e7eef481bb426d6a02c', 'SEARCH_LOG,DATA_KEEP_SEARCH_DEPARTMETN,DATA_KEEP_ADD_DEPARTMENT,DATA_KEEP_UPDATE_DEPARTMETN,DATA_KEEP_DELETE_DEPARTMENT,SEARCH_PERSON,ADD_PERSON,MODIFY_PERSION,DELETE_PERSION,DELETE_CLASS_ROOM,SEARCH_CLASS_ROOM_INFO,ADD_CLASS_ROOM,UPDATE_CLASS_ROOM,UPDATE_USE_STATUS,ADD_POST,SEARCH_POST,UPDATE_POST,DELETE_POST,ADD_DICTIONARRY,DELETE_DICTIONARY,SEARCH_DICTIONARY,UPDATE_DICTIONARY,COLLEGE_SEARCH_DEPTMENT,COLLEGE_ADD_DEPTMENT,COLLEGE_UPDATE_DEPARTMENT,COLLEGE_DELETE_DEPARTMENT,COLLEGE_SEARCH_PERSON,COLLEGE_ADD_PERSON,COLLEGE_UPDATE_PERSON,COLLEGE_DELETE_PERSON,COLLEGE_SEARCH_EXAM');
INSERT INTO `sys_user_acl` VALUES (1122050540914630658, '5cc40e9eef481bb426d6a02d', 'UPDATE_USER_USE_STATUS,SEARCH_DEPARTMENT,ADD_DEPTMENT,UPDATE_DEPARTMENT,DELETE_DEPARTMENT,SEARCH_USER,ADD_USER,UPDATE_USER,DELETE_USER,RESET_PASSWORD,COLLEGE_SEARCH_DEPTMENT,COLLEGE_ADD_DEPTMENT,COLLEGE_UPDATE_DEPARTMENT,COLLEGE_DELETE_DEPARTMENT,COLLEGE_SEARCH_PERSON,COLLEGE_ADD_PERSON,COLLEGE_UPDATE_PERSON,COLLEGE_DELETE_PERSON');
INSERT INTO `sys_user_acl` VALUES (1122050709693423618, '5cc40ec6ef481bb426d6a02e', 'ADD_ROLE,MODIFY_ROLE,DELETE_ROLE,SEARCH_ROLE,DATA_KEEP_SEARCH_DEPARTMETN,DATA_KEEP_ADD_DEPARTMENT,DATA_KEEP_UPDATE_DEPARTMETN,DATA_KEEP_DELETE_DEPARTMENT,SEARCH_PERSON,ADD_PERSON,MODIFY_PERSION,DELETE_PERSION,DELETE_CLASS_ROOM,SEARCH_CLASS_ROOM_INFO,ADD_CLASS_ROOM,UPDATE_CLASS_ROOM,UPDATE_USE_STATUS,ADD_POST,SEARCH_POST,UPDATE_POST,DELETE_POST,ADD_DICTIONARRY,DELETE_DICTIONARY,SEARCH_DICTIONARY,UPDATE_DICTIONARY,COLLEGE_SEARCH_DEPTMENT,COLLEGE_ADD_DEPTMENT,COLLEGE_UPDATE_DEPARTMENT,COLLEGE_DELETE_DEPARTMENT,COLLEGE_SEARCH_PERSON,COLLEGE_ADD_PERSON,COLLEGE_UPDATE_PERSON,COLLEGE_DELETE_PERSON,COLLEGE_SEARCH_EXAM');
INSERT INTO `sys_user_acl` VALUES (1122050880082829313, '5cc40eeeef481bb426d6a02f', 'UPDATE_USER_USE_STATUS,SEARCH_DEPARTMENT,ADD_DEPTMENT,UPDATE_DEPARTMENT,DELETE_DEPARTMENT,SEARCH_USER,ADD_USER,UPDATE_USER,DELETE_USER,RESET_PASSWORD,DATA_KEEP_SEARCH_DEPARTMETN,DATA_KEEP_ADD_DEPARTMENT,DATA_KEEP_UPDATE_DEPARTMETN,DATA_KEEP_DELETE_DEPARTMENT,SEARCH_PERSON,ADD_PERSON,MODIFY_PERSION,DELETE_PERSION,DELETE_CLASS_ROOM,SEARCH_CLASS_ROOM_INFO,ADD_CLASS_ROOM,UPDATE_CLASS_ROOM,UPDATE_USE_STATUS,ADD_POST,SEARCH_POST,UPDATE_POST,DELETE_POST,ADD_DICTIONARRY,DELETE_DICTIONARY,SEARCH_DICTIONARY,UPDATE_DICTIONARY,COLLEGE_SEARCH_DEPTMENT,COLLEGE_ADD_DEPTMENT,COLLEGE_UPDATE_DEPARTMENT,COLLEGE_DELETE_DEPARTMENT,COLLEGE_SEARCH_PERSON,COLLEGE_ADD_PERSON,COLLEGE_UPDATE_PERSON,COLLEGE_DELETE_PERSON');
INSERT INTO `sys_user_acl` VALUES (1122051307419492354, '5cc40f54ef481bb426d6a031', 'SEARCH_LOG');
INSERT INTO `sys_user_acl` VALUES (1122051486130397185, '5cc40f7fef481bb426d6a032', 'UPDATE_USER_USE_STATUS,SEARCH_DEPARTMENT,SEARCH_USER,RESET_PASSWORD');
INSERT INTO `sys_user_acl` VALUES (1122051641617440769, '5cc40f36ef481bb426d6a030', 'MODIFY_ROLE,DELETE_ROLE,SEARCH_ROLE');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_detail`;
CREATE TABLE `sys_user_detail` (
  `user_id` varchar(100) NOT NULL COMMENT '报名号',
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `sex` tinyint(4) DEFAULT NULL COMMENT '0女  1男',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `login_type` tinyint(4) DEFAULT NULL COMMENT '0 ',
  `dept_id` bigint(32) unsigned NOT NULL DEFAULT '0' COMMENT '管理员为1，用户为2',
  `id_card` varchar(100) DEFAULT NULL COMMENT '身份证',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `unique` (`email`,`phone`,`id_card`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户详细信息表';

-- ----------------------------
-- Table structure for tss_class_info
-- ----------------------------
DROP TABLE IF EXISTS `tss_class_info`;
CREATE TABLE `tss_class_info` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '教室名称',
  `place` int(4) NOT NULL COMMENT '所属考点',
  `capacity` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '教室容量',
  `type` int(4) NOT NULL COMMENT '教室类型（机考、笔试、其他）',
  `status` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '状态（0启用、1停用）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='教室字典表';

-- ----------------------------
-- Records of tss_class_info
-- ----------------------------
BEGIN;
INSERT INTO `tss_class_info` VALUES ('5cd119422572fc0893030663', '协和笔试1', -1, 30, 1, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11cbf2572fc090424bf4f', '协和笔试2', -1, 30, 1, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11cc82572fc090424bf50', '协和机考1', -1, 30, 0, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11cd12572fc090424bf51', '协和机考2', -1, 30, 0, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11cdb2572fc090424bf52', '协和笔试3', -1, 1, 1, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11ce32572fc090424bf53', '协和机考3', -1, 1, 0, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11db82572fc090424bf54', '仓山笔试1', -2, 30, 1, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11dc72572fc090424bf55', '仓山笔试2', -2, 30, 1, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11dd32572fc090424bf56', '仓山机考1', -2, 30, 0, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11ddd2572fc090424bf57', '仓山机考2', -2, 30, 0, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11de82572fc090424bf58', '旗山机考1', -3, 30, 0, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11df42572fc090424bf59', '旗山机考2', -3, 30, 0, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11dfd2572fc090424bf5a', '旗山笔试1', -3, 30, 1, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11e062572fc090424bf5b', '旗山笔试2', -3, 30, 1, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11e292572fc090424bf5c', '仓山机考3', -2, 1, 0, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11e352572fc090424bf5d', '仓山笔试3', -2, 1, 1, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11e3f2572fc090424bf5e', '旗山机考3', -3, 1, 0, 0, NULL);
INSERT INTO `tss_class_info` VALUES ('5cd11e472572fc090424bf5f', '旗山笔试3', -3, 1, 1, 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for tss_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `tss_dictionary`;
CREATE TABLE `tss_dictionary` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(32) NOT NULL DEFAULT '',
  `code` int(11) NOT NULL DEFAULT '0' COMMENT '编号',
  `description` varchar(64) NOT NULL DEFAULT '',
  `remark` varchar(500) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1116179691917623298 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典总表';

-- ----------------------------
-- Records of tss_dictionary
-- ----------------------------
BEGIN;
INSERT INTO `tss_dictionary` VALUES (1, 'EXAM_POINT', -1, '协和', '');
INSERT INTO `tss_dictionary` VALUES (2, 'EXAM_POINT', -2, '仓山', '');
INSERT INTO `tss_dictionary` VALUES (3, 'EXAM_POINT', -3, '旗山', '');
INSERT INTO `tss_dictionary` VALUES (4, 'EXAM_TYPE', 1, '笔试', '');
INSERT INTO `tss_dictionary` VALUES (5, 'EXAM_TYPE', 0, '机考', '');
INSERT INTO `tss_dictionary` VALUES (7, 'BANK', 2, '农业银行', '');
INSERT INTO `tss_dictionary` VALUES (8, 'PERSON_TYPE', 0, '人员类型1', '');
INSERT INTO `tss_dictionary` VALUES (9, 'PERSON_TYPE', 0, '人员类型2', '');
INSERT INTO `tss_dictionary` VALUES (10, 'PERSON_KIND', 0, '人员类别1', '');
INSERT INTO `tss_dictionary` VALUES (12, 'MONEY', 0, '汇款类型1', '');
INSERT INTO `tss_dictionary` VALUES (13, 'MONEY', 0, '汇款类型2', '');
INSERT INTO `tss_dictionary` VALUES (1116179691917623297, 'BANK', 0, '工商银行', '');
COMMIT;

-- ----------------------------
-- Table structure for tss_exam
-- ----------------------------
DROP TABLE IF EXISTS `tss_exam`;
CREATE TABLE `tss_exam` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '考试 名称',
  `type` int(4) unsigned NOT NULL COMMENT '考试类型（机考0笔试1）',
  `start_time` datetime NOT NULL COMMENT '考试开始时间',
  `end_time` datetime NOT NULL COMMENT '考试结束时间 主考试可以为空',
  `report_start` int(11) NOT NULL COMMENT '签到开始时间',
  `report_end` int(11) NOT NULL COMMENT '签到结束时间',
  `parent_id` varchar(64) NOT NULL DEFAULT '' COMMENT '父考试id',
  `level` varchar(255) NOT NULL DEFAULT '' COMMENT '层级',
  `status` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '归档状态默认0未归档',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `place_code_str` varchar(255) NOT NULL COMMENT '考点code字符串',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='考试信息表';

-- ----------------------------
-- Records of tss_exam
-- ----------------------------
BEGIN;
INSERT INTO `tss_exam` VALUES ('5cd115e52c5a2638cdceeef0', '123', 1, '2019-05-01 00:00:00', '2019-05-08 00:00:00', 0, 0, '0', '0', 901, '123', '');
INSERT INTO `tss_exam` VALUES ('5cd115e52c5a2638cdceeef1', '123', 1, '2019-05-01 00:00:00', '2019-05-08 00:00:00', 0, 0, '5cd115e52c5a2638cdceeef0', '0.5cd115e52c5a2638cdceeef0', 0, '', '-2');
INSERT INTO `tss_exam` VALUES ('5cd116072c5a2638cdceeef3', '234', 1, '2019-05-01 00:00:00', '2019-05-08 00:00:00', 0, 0, '5cd115e52c5a2638cdceeef0', '0.5cd115e52c5a2638cdceeef0', 0, '', '-2');
INSERT INTO `tss_exam` VALUES ('5cd11ca32572fc090424bf39', '5.7机考测试', 0, '2019-05-02 00:00:00', '2019-05-09 00:00:00', 0, 0, '0', '0', 0, '', '');
INSERT INTO `tss_exam` VALUES ('5cd11ca32572fc090424bf3a', '测试1', 0, '2019-05-02 00:00:00', '2019-05-09 00:00:00', 0, 0, '5cd11ca32572fc090424bf39', '0.5cd11ca32572fc090424bf39', 0, '', '-1,-2,-3');
INSERT INTO `tss_exam` VALUES ('5cd11ca32572fc090424bf3e', '测试2', 0, '2019-05-02 00:00:00', '2019-05-09 00:00:00', 0, 0, '5cd11ca32572fc090424bf39', '0.5cd11ca32572fc090424bf39', 0, '', '-1');
INSERT INTO `tss_exam` VALUES ('5cd11ca32572fc090424bf40', '测试3', 0, '2019-05-02 00:00:00', '2019-05-09 00:00:00', 0, 0, '5cd11ca32572fc090424bf39', '0.5cd11ca32572fc090424bf39', 0, '', '-2');
INSERT INTO `tss_exam` VALUES ('5cd11ca32572fc090424bf42', '测试4', 0, '2019-05-02 00:00:00', '2019-05-09 00:00:00', 0, 0, '5cd11ca32572fc090424bf39', '0.5cd11ca32572fc090424bf39', 0, '', '-3');
COMMIT;

-- ----------------------------
-- Table structure for tss_exam_place
-- ----------------------------
DROP TABLE IF EXISTS `tss_exam_place`;
CREATE TABLE `tss_exam_place` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `name_or_seq` int(20) NOT NULL COMMENT '考点和考场名称或编号',
  `te_id` varchar(64) NOT NULL DEFAULT '' COMMENT '场次id',
  `tci_id` varchar(64) NOT NULL DEFAULT '' COMMENT '教室字典表id',
  `parent_id` varchar(64) NOT NULL DEFAULT '' COMMENT '父考点或考场id',
  `level` varchar(255) NOT NULL DEFAULT '' COMMENT '层级',
  `student_count` int(11) NOT NULL DEFAULT '0' COMMENT '考场学生数（-1为考点）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='考场考点信息表';

-- ----------------------------
-- Records of tss_exam_place
-- ----------------------------
BEGIN;
INSERT INTO `tss_exam_place` VALUES ('001201952001', 1, '5cd11ca32572fc090424bf3a', '5cd11de82572fc090424bf58', '5cd11ca32572fc090424bf3d', '0.5cd11ca32572fc090424bf39.5cd11ca32572fc090424bf3a.5cd11ca32572fc090424bf3d', 30, '2019-05-07 13:58:09');
INSERT INTO `tss_exam_place` VALUES ('002201952001', 2, '5cd11ca32572fc090424bf3a', '5cd11dd32572fc090424bf56', '5cd11ca32572fc090424bf3c', '0.5cd11ca32572fc090424bf39.5cd11ca32572fc090424bf3a.5cd11ca32572fc090424bf3c', 30, '2019-05-07 14:00:31');
INSERT INTO `tss_exam_place` VALUES ('003201952001', 3, '5cd11ca32572fc090424bf3a', '5cd11cc82572fc090424bf50', '5cd11ca32572fc090424bf3b', '0.5cd11ca32572fc090424bf39.5cd11ca32572fc090424bf3a.5cd11ca32572fc090424bf3b', 30, '2019-05-07 14:01:14');
INSERT INTO `tss_exam_place` VALUES ('004201952001', 1, '5cd11ca32572fc090424bf3e', '5cd11ce32572fc090424bf53', '5cd11ca32572fc090424bf3f', '0.5cd11ca32572fc090424bf39.5cd11ca32572fc090424bf3e.5cd11ca32572fc090424bf3f', 1, '2019-05-07 14:01:41');
INSERT INTO `tss_exam_place` VALUES ('006201952001', 1, '5cd11ca32572fc090424bf42', '5cd11e3f2572fc090424bf5e', '5cd11ca32572fc090424bf43', '0.5cd11ca32572fc090424bf39.5cd11ca32572fc090424bf42.5cd11ca32572fc090424bf43', 1, '2019-05-07 14:03:36');
INSERT INTO `tss_exam_place` VALUES ('007201952001', 1, '5cd11ca32572fc090424bf40', '5cd11e292572fc090424bf5c', '5cd11ca32572fc090424bf41', '0.5cd11ca32572fc090424bf39.5cd11ca32572fc090424bf40.5cd11ca32572fc090424bf41', 1, '2019-05-07 14:04:16');
INSERT INTO `tss_exam_place` VALUES ('008201952001', 4, '5cd11ca32572fc090424bf3a', '5cd11df42572fc090424bf59', '5cd11ca32572fc090424bf3d', '0.5cd11ca32572fc090424bf39.5cd11ca32572fc090424bf3a.5cd11ca32572fc090424bf3d', 30, '2019-05-07 14:05:50');
INSERT INTO `tss_exam_place` VALUES ('009201952001', 5, '5cd11ca32572fc090424bf3a', '5cd11ddd2572fc090424bf57', '5cd11ca32572fc090424bf3c', '0.5cd11ca32572fc090424bf39.5cd11ca32572fc090424bf3a.5cd11ca32572fc090424bf3c', 30, '2019-05-07 14:06:02');
INSERT INTO `tss_exam_place` VALUES ('010201952001', 6, '5cd11ca32572fc090424bf3a', '5cd11cd12572fc090424bf51', '5cd11ca32572fc090424bf3b', '0.5cd11ca32572fc090424bf39.5cd11ca32572fc090424bf3a.5cd11ca32572fc090424bf3b', 30, '2019-05-07 14:09:01');
INSERT INTO `tss_exam_place` VALUES ('5cd115e52c5a2638cdceeef2', -2, '5cd115e52c5a2638cdceeef1', '', '5cd115e52c5a2638cdceeef1', '0.5cd115e52c5a2638cdceeef0.5cd115e52c5a2638cdceeef1', -1, '2019-05-07 13:21:42');
INSERT INTO `tss_exam_place` VALUES ('5cd116072c5a2638cdceeef4', -2, '5cd116072c5a2638cdceeef3', '', '5cd116072c5a2638cdceeef3', '0.5cd115e52c5a2638cdceeef0.5cd116072c5a2638cdceeef3', -1, '2019-05-07 13:22:15');
INSERT INTO `tss_exam_place` VALUES ('5cd11ca32572fc090424bf3b', -1, '5cd11ca32572fc090424bf3a', '', '5cd11ca32572fc090424bf3a', '0.5cd11ca32572fc090424bf39.5cd11ca32572fc090424bf3a', -1, '2019-05-07 13:50:27');
INSERT INTO `tss_exam_place` VALUES ('5cd11ca32572fc090424bf3c', -2, '5cd11ca32572fc090424bf3a', '', '5cd11ca32572fc090424bf3a', '0.5cd11ca32572fc090424bf39.5cd11ca32572fc090424bf3a', -1, '2019-05-07 13:50:27');
INSERT INTO `tss_exam_place` VALUES ('5cd11ca32572fc090424bf3d', -3, '5cd11ca32572fc090424bf3a', '', '5cd11ca32572fc090424bf3a', '0.5cd11ca32572fc090424bf39.5cd11ca32572fc090424bf3a', -1, '2019-05-07 13:50:27');
INSERT INTO `tss_exam_place` VALUES ('5cd11ca32572fc090424bf3f', -1, '5cd11ca32572fc090424bf3e', '', '5cd11ca32572fc090424bf3e', '0.5cd11ca32572fc090424bf39.5cd11ca32572fc090424bf3e', -1, '2019-05-07 13:50:27');
INSERT INTO `tss_exam_place` VALUES ('5cd11ca32572fc090424bf41', -2, '5cd11ca32572fc090424bf40', '', '5cd11ca32572fc090424bf40', '0.5cd11ca32572fc090424bf39.5cd11ca32572fc090424bf40', -1, '2019-05-07 13:50:27');
INSERT INTO `tss_exam_place` VALUES ('5cd11ca32572fc090424bf43', -3, '5cd11ca32572fc090424bf42', '', '5cd11ca32572fc090424bf42', '0.5cd11ca32572fc090424bf39.5cd11ca32572fc090424bf42', -1, '2019-05-07 13:50:27');
COMMIT;

-- ----------------------------
-- Table structure for tss_grade_subjects
-- ----------------------------
DROP TABLE IF EXISTS `tss_grade_subjects`;
CREATE TABLE `tss_grade_subjects` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `kmmc` varchar(128) NOT NULL DEFAULT '' COMMENT '科目名称',
  `kssjhcs` varchar(128) NOT NULL DEFAULT '' COMMENT '考试时间或次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='记录考试科目和考试时间或次数';

-- ----------------------------
-- Table structure for tss_id_pool
-- ----------------------------
DROP TABLE IF EXISTS `tss_id_pool`;
CREATE TABLE `tss_id_pool` (
  `te_id` varchar(64) NOT NULL DEFAULT '' COMMENT '考试id',
  `te_start_time` varchar(16) NOT NULL COMMENT '考试时间',
  `exam_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '考试场次（非一场考试下拥有的考试场数）',
  `count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '编号',
  PRIMARY KEY (`te_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='考场id分配表';

-- ----------------------------
-- Records of tss_id_pool
-- ----------------------------
BEGIN;
INSERT INTO `tss_id_pool` VALUES ('5cd115e52c5a2638cdceeef0', '201951', 1, 1);
INSERT INTO `tss_id_pool` VALUES ('5cd11ca32572fc090424bf39', '201952', 1, 11);
COMMIT;

-- ----------------------------
-- Table structure for tss_import_grade
-- ----------------------------
DROP TABLE IF EXISTS `tss_import_grade`;
CREATE TABLE `tss_import_grade` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `xm` varchar(32) NOT NULL DEFAULT '' COMMENT '姓名',
  `zjhm` char(32) NOT NULL DEFAULT '' COMMENT '证件号码(身份证)',
  `xh` char(32) NOT NULL DEFAULT '' COMMENT '学号',
  `ssxx` varchar(64) NOT NULL DEFAULT '' COMMENT '所属学校',
  `xl` varchar(32) NOT NULL DEFAULT '' COMMENT '学历',
  `xz` char(3) NOT NULL DEFAULT '0' COMMENT '学制',
  `nz` char(16) NOT NULL DEFAULT '' COMMENT '年级',
  `yx` varchar(128) NOT NULL DEFAULT '' COMMENT '院系',
  `zy` varchar(128) NOT NULL DEFAULT '' COMMENT '专业',
  `bj` varchar(128) NOT NULL DEFAULT '' COMMENT '班级',
  `kssjhcs` varchar(128) NOT NULL DEFAULT '' COMMENT '考试时间或次数',
  `bszkzh` char(64) NOT NULL DEFAULT '' COMMENT '笔试准考证号',
  `exam_id` varchar(64) NOT NULL DEFAULT '' COMMENT '一场考试的唯一id',
  `bskmmc` varchar(128) NOT NULL DEFAULT '' COMMENT '笔试科目名称',
  `cjdh` char(64) NOT NULL DEFAULT '' COMMENT '成绩单号',
  `zf` char(64) NOT NULL DEFAULT '' COMMENT '总分',
  `cj1` char(16) NOT NULL DEFAULT '0' COMMENT '成绩1',
  `cj2` char(16) NOT NULL DEFAULT '0' COMMENT '成绩2',
  `cj3` char(16) NOT NULL DEFAULT '0' COMMENT '成绩3',
  `cj4` char(16) NOT NULL DEFAULT '0' COMMENT '成绩4',
  `bz` varchar(512) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='成绩导入表';

-- ----------------------------
-- Table structure for tss_job_sign_in
-- ----------------------------
DROP TABLE IF EXISTS `tss_job_sign_in`;
CREATE TABLE `tss_job_sign_in` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `te_id` varchar(64) DEFAULT NULL COMMENT '场次id',
  `user_id` varchar(64) DEFAULT NULL COMMENT '已签到人员id',
  `sign_time` datetime DEFAULT NULL COMMENT '签到时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1122683906286911490 DEFAULT CHARSET=utf8 COMMENT='岗位签到表';

-- ----------------------------
-- Table structure for tss_person_library
-- ----------------------------
DROP TABLE IF EXISTS `tss_person_library`;
CREATE TABLE `tss_person_library` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `college_id` bigint(20) unsigned NOT NULL COMMENT '所属学院或部门ID',
  `work_code` varchar(30) NOT NULL DEFAULT '' COMMENT '工号、学号等',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '姓名',
  `phone` varchar(15) DEFAULT '' COMMENT '联系电话',
  `sex` varchar(2) DEFAULT '' COMMENT '性别',
  `pid` varchar(100) DEFAULT '' COMMENT '身份证号',
  `bank` bigint(20) DEFAULT NULL COMMENT '银行CODE',
  `bank_open_code` varchar(20) DEFAULT '' COMMENT '开户行代码',
  `bank_open` varchar(50) DEFAULT '' COMMENT '开户行',
  `bank_code` varchar(30) DEFAULT '' COMMENT '银行卡号',
  `category` bigint(20) DEFAULT NULL COMMENT '人员类别',
  `type` bigint(20) DEFAULT NULL COMMENT '人员类型',
  `money_type` bigint(20) DEFAULT NULL COMMENT '汇款类型',
  `finance` varchar(40) DEFAULT '' COMMENT '是否入财务系统',
  `yes_no` varchar(10) DEFAULT '' COMMENT '是否参加监考',
  `address` varchar(10) DEFAULT '' COMMENT '选择校区',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='人员库表';

-- ----------------------------
-- Records of tss_person_library
-- ----------------------------
BEGIN;
INSERT INTO `tss_person_library` VALUES ('5cc7c36f2c5a2654d73ac8ce', 90, '52522', '计本林伟1', '13455331222', '男', 'rINAxWTCitxZiaEy7unMThWIqEHbL8TFk92Ak/kjGQE=', 7, '', '计本林伟1', '12121251', 10, 8, NULL, '', '', '');
INSERT INTO `tss_person_library` VALUES ('5cc7c3922c5a2654d73ac8cf', 90, '45132354', '计本林伟2', '15632563215', '女', 'WeZxGm2MWa3TCFSYPoFIDp0oqEV9G7F2Gn2Q0SR+2t0=', 7, '', '计本林伟2', '2131312', 10, 8, NULL, '', '', '');
INSERT INTO `tss_person_library` VALUES ('5cc7c3aa2c5a2654d73ac8d0', 90, '781231564', '计本林伟3', '15365584562', '男', 'wWENoDeVFCuybsKLAdCPBNFF71QVwa2dmCYxdpg5AEc=', 1116179691917623297, '', '计本林伟3', '21321', 10, 9, NULL, '', '', '');
INSERT INTO `tss_person_library` VALUES ('5cc7c3c02c5a2654d73ac8d1', 90, '556222', '计本林伟4', '15874562548', '女', 'ah/PRRnEoax4GlGjETbUcRRxQPyX1qCARME2GhN0j3E=', 7, '', '计本林伟4', '465131', 10, 8, NULL, '', '', '');
INSERT INTO `tss_person_library` VALUES ('5cc7c3f12c5a2654d73ac8d2', 90, '524674213', '计本林伟5', '13057425461', '男', 'O2sRq5cBGl9Npbvejx4wHGZ7u2thPY88d+GSRdk13pw=', 1116179691917623297, '', '计本林伟5', '446512313', 10, 8, NULL, '', '', '');
INSERT INTO `tss_person_library` VALUES ('5cc7c40b2c5a2654d73ac8d3', 90, '454321213', '计本林伟6', '15236584563', '男', 'k6/pMYBLpeoSdYsf+rJ3l+1tf2N/TbG6g//xKOhcSko=', 1116179691917623297, '', '计本林伟6', '7845132465', 10, 9, NULL, '', '', '');
INSERT INTO `tss_person_library` VALUES ('5cc7c4242c5a2654d73ac8d4', 90, '45432132', '计本林伟7', '18652352462', '男', 'z2PX3OMrMn2KIUT17yyFRxzflAKFUhryfiu6EG45w7E=', 7, '', '计本林伟7', '78413123123', 10, 8, NULL, '', '', '');
INSERT INTO `tss_person_library` VALUES ('5cc7c43a2c5a2654d73ac8d6', 90, '78432132132', '计本林伟8', '15568323255', '男', 'hSlPEgGMlLDo9x4r3kv082Z7u2thPY88d+GSRdk13pw=', 1116179691917623297, '', '计本林伟8', '768133121', 10, 8, NULL, '', '', '');
INSERT INTO `tss_person_library` VALUES ('5cc7c4522c5a2654d73ac8d7', 90, '243252352', '计本林伟9', '15865425625', '女', 'HMHq9ALYNjLJIOaqKtucc4OHVm06841Y1NB6/Hiv3ag=', 7, '', '计本林伟9', '513132132', 10, 8, NULL, '', '', '');
INSERT INTO `tss_person_library` VALUES ('5cc7c46d2c5a2654d73ac8d8', 90, '51312313', '计本林伟10', '18965355551', '男', 'y0jQgQSY4ZTFHJbuTHDTWasVDNdG8OD4Jo3suBgjH0s=', 7, '', '计本林伟10', '456456456', 10, 8, NULL, '', '', '');
INSERT INTO `tss_person_library` VALUES ('5cc7c48b2c5a2654d73ac8d9', 93, '513218946', '数本林伟1', '15685452621', '男', 'jzHzCyS0fvyoaE7PgCKCAYMnYreOKBORZLgtyhve7cg=', 7, '', '数本林伟1', '154645', 10, 8, NULL, '', '', '');
INSERT INTO `tss_person_library` VALUES ('5cc7c4a02c5a2654d73ac8da', 93, '241511', '数本林伟2', '18525625425', '男', 'Jf44cEBFQahTGDFKxvE83P6PredLemhdMHmEbFO7Qc8=', 7, '', '数本林伟2', '35463463', 10, 8, NULL, '', '', '');
INSERT INTO `tss_person_library` VALUES ('5cc7c4b12c5a2654d73ac8db', 93, '2431', '数本林伟3', '13565645242', '男', 'XtS2B0GVZ5IYK7bzj8883w4ZwcnG0RVac4pBWbPngv4=', 1116179691917623297, '', '数本林伟3', '4141241', 10, 8, NULL, '', '', '');
INSERT INTO `tss_person_library` VALUES ('5cc7c4ce2c5a2654d73ac8dc', 93, '45132132', '数本林伟4', '13457676747', '男', '3D5IbbYa8SwUTHKgT07vhiq1DBPbEhK2xULYNnGA+S0=', 7, '', '数本林伟4', '215163161', 10, 8, NULL, '', '', '');
INSERT INTO `tss_person_library` VALUES ('5cc7c4e72c5a2654d73ac8dd', 93, '1221516361', '数本林伟5', '15668657542', '男', 'xG9PfiqHVE+f8XkTYBKRY0K5c+ePH3c+4aFGWWVPZXc=', 1116179691917623297, '', '数本林伟5', '12412412', 10, 8, NULL, '', '', '');
COMMIT;

-- ----------------------------
-- Table structure for tss_post_info
-- ----------------------------
DROP TABLE IF EXISTS `tss_post_info`;
CREATE TABLE `tss_post_info` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `name` varchar(100) NOT NULL COMMENT '岗位名称',
  `free` decimal(11,3) NOT NULL COMMENT '岗位费用',
  `remark` varchar(500) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='岗位信息表';

-- ----------------------------
-- Records of tss_post_info
-- ----------------------------
BEGIN;
INSERT INTO `tss_post_info` VALUES ('5cd11e852572fc090424bf60', '监考前', 200.000, '');
INSERT INTO `tss_post_info` VALUES ('5cd11e8c2572fc090424bf61', '监考后', 200.000, '');
INSERT INTO `tss_post_info` VALUES ('5cd11e972572fc090424bf62', '出卷', 500.000, '');
INSERT INTO `tss_post_info` VALUES ('5cd11ea62572fc090424bf63', '改卷', 600.000, '');
INSERT INTO `tss_post_info` VALUES ('5cd11eb02572fc090424bf64', '巡考', 300.000, '');
INSERT INTO `tss_post_info` VALUES ('5cd11eb72572fc090424bf65', '保洁', 100.000, '');
INSERT INTO `tss_post_info` VALUES ('5cd1200f2572fc090424bf6e', '保安', 400.000, '');
INSERT INTO `tss_post_info` VALUES ('5cd1201d2572fc090424bf6f', '监控', 400.000, '');
INSERT INTO `tss_post_info` VALUES ('5cd1202a2572fc090424bf70', '候补', 400.000, '');
COMMIT;

-- ----------------------------
-- Table structure for tss_post_person
-- ----------------------------
DROP TABLE IF EXISTS `tss_post_person`;
CREATE TABLE `tss_post_person` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `tep_id` varchar(64) NOT NULL DEFAULT '' COMMENT '考场考点id',
  `post_id` varchar(64) NOT NULL DEFAULT '' COMMENT '岗位Id',
  `post_name` varchar(100) NOT NULL COMMENT '岗位名称',
  `post_free` decimal(11,3) NOT NULL COMMENT '岗位费用',
  `person_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '岗位人数',
  `tpi_id_str` varchar(4800) NOT NULL COMMENT '已安排的岗位人员id字符串',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='岗位人员总表';

-- ----------------------------
-- Table structure for tss_seat_order
-- ----------------------------
DROP TABLE IF EXISTS `tss_seat_order`;
CREATE TABLE `tss_seat_order` (
  `tep_id` varchar(64) NOT NULL DEFAULT '' COMMENT '考场考点id',
  `tci_id` varchar(64) NOT NULL DEFAULT '' COMMENT '安排的教室id',
  `seat_order` varchar(9600) NOT NULL DEFAULT '' COMMENT '考场座位顺序字符串，逗号连接，未安排用”0“填充',
  PRIMARY KEY (`tep_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='考场座位顺序表';

-- ----------------------------
-- Records of tss_seat_order
-- ----------------------------
BEGIN;
INSERT INTO `tss_seat_order` VALUES ('001201952001', '5cd11de82572fc090424bf58', '5cd11ca92572fc090424bf44,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0');
INSERT INTO `tss_seat_order` VALUES ('002201952001', '5cd11dd32572fc090424bf56', '5cd11ca92572fc090424bf46,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0');
INSERT INTO `tss_seat_order` VALUES ('003201952001', '5cd11cc82572fc090424bf50', '5cd11ca92572fc090424bf45,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0');
INSERT INTO `tss_seat_order` VALUES ('004201952001', '5cd11ce32572fc090424bf53', '5cd11ca92572fc090424bf47');
INSERT INTO `tss_seat_order` VALUES ('006201952001', '5cd11e3f2572fc090424bf5e', '5cd11ca92572fc090424bf49');
INSERT INTO `tss_seat_order` VALUES ('007201952001', '5cd11e292572fc090424bf5c', '5cd11ca92572fc090424bf4e');
INSERT INTO `tss_seat_order` VALUES ('008201952001', '5cd11df42572fc090424bf59', '5cd11ca92572fc090424bf4d,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0');
INSERT INTO `tss_seat_order` VALUES ('009201952001', '5cd11ddd2572fc090424bf57', '5cd11ca92572fc090424bf4a,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0');
INSERT INTO `tss_seat_order` VALUES ('010201952001', '5cd11cd12572fc090424bf51', '5cd11ca92572fc090424bf48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0');
COMMIT;

-- ----------------------------
-- Table structure for tss_seat_schedule
-- ----------------------------
DROP TABLE IF EXISTS `tss_seat_schedule`;
CREATE TABLE `tss_seat_schedule` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `xymc` varchar(64) NOT NULL DEFAULT '' COMMENT '学院名称',
  `xm` varchar(128) NOT NULL DEFAULT '' COMMENT '姓名',
  `bkjb` varchar(255) NOT NULL DEFAULT '' COMMENT '报考级别',
  `te_id` varchar(64) NOT NULL DEFAULT '' COMMENT '考试ID',
  `tep_id` varchar(64) NOT NULL DEFAULT '' COMMENT '考场考点id',
  `seat_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '座位号（未安排）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='学生座位安排总表';

-- ----------------------------
-- Table structure for tss_solicitation_time
-- ----------------------------
DROP TABLE IF EXISTS `tss_solicitation_time`;
CREATE TABLE `tss_solicitation_time` (
  `te_id` varchar(64) NOT NULL DEFAULT '' COMMENT '考试表的id',
  `post_time_start` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '岗位开始征集时间',
  `post_time_end` datetime NOT NULL COMMENT '岗位结束征集时间',
  `student_time_start` datetime NOT NULL COMMENT '学生导入开始时间',
  `student_time_end` datetime NOT NULL COMMENT '学生导入结束时间',
  `admission_ticket_time_start` datetime NOT NULL COMMENT '准考证下载开始时间',
  `admission_ticket_time_end` datetime NOT NULL COMMENT '准考证下载结束时间',
  `dept_id` bigint(20) unsigned NOT NULL COMMENT '所指派给的部门或学院',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`te_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='考试岗位和学生征集时间表';

-- ----------------------------
-- Records of tss_solicitation_time
-- ----------------------------
BEGIN;
INSERT INTO `tss_solicitation_time` VALUES ('5cd115e52c5a2638cdceeef0', '2099-12-30 23:59:59', '2099-12-30 23:59:59', '2099-12-30 23:59:59', '2099-12-30 23:59:59', '2099-12-30 23:59:59', '2099-12-30 23:59:59', 0, '');
INSERT INTO `tss_solicitation_time` VALUES ('5cd11ca32572fc090424bf39', '2019-05-01 00:00:00', '2019-05-02 00:00:00', '2099-12-30 23:59:59', '2099-12-30 23:59:59', '2099-12-30 23:59:59', '2099-12-30 23:59:59', 0, '');
COMMIT;

-- ----------------------------
-- Table structure for tss_student_info
-- ----------------------------
DROP TABLE IF EXISTS `tss_student_info`;
CREATE TABLE `tss_student_info` (
  `user_id` varchar(64) NOT NULL DEFAULT '',
  `te_id` varchar(64) DEFAULT NULL COMMENT '考试id',
  `xm` varchar(32) NOT NULL DEFAULT '' COMMENT '姓名',
  `zjhm` varchar(100) NOT NULL DEFAULT '' COMMENT '证件号码(身份证)',
  `xh` char(32) NOT NULL DEFAULT '' COMMENT '学号',
  `ssxx` varchar(64) NOT NULL DEFAULT '' COMMENT '所属学校',
  `xl` varchar(32) NOT NULL DEFAULT '' COMMENT '学历',
  `xz` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '学制',
  `nz` char(16) NOT NULL DEFAULT '' COMMENT '年级',
  `yx` varchar(128) NOT NULL DEFAULT '' COMMENT '院系',
  `zy` varchar(128) NOT NULL DEFAULT '' COMMENT '专业',
  `bj` varchar(128) NOT NULL DEFAULT '' COMMENT '班级',
  `kssjhcs` varchar(128) NOT NULL DEFAULT '' COMMENT '考试时间或次数',
  `bszkzh` char(64) NOT NULL DEFAULT '' COMMENT '笔试准考证号',
  `bskmmc` varchar(256) DEFAULT '' COMMENT '笔试科目名称',
  `time` datetime DEFAULT NULL COMMENT '签到时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='学生数据总表';

-- ----------------------------
-- Records of tss_student_info
-- ----------------------------
BEGIN;
INSERT INTO `tss_student_info` VALUES ('5cc7c09bef481bc321a787b0', '5cc7bf462c5a2653c1d8a15f', '刘泽琳', 'zZiLQJ5EcYhAhLqjj+sr+nxU+vGJ4vPl0kLGfJWPEhw=', 'QSZ20170750', '福建师范大学', '研究生', 0, '17', '外国语学院', '英语笔译', '英语笔译', '第42次', '351021182201627', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc7c09bef481bc321a787b1', '5cc7bf462c5a2653c1d8a15f', '田杨', 'umkHfkGkpjLDShlvd739loMnYreOKBORZLgtyhve7cg=', '20160506', '福建师范大学', '研究生', 0, '16', '社会历史学院', '世界史', '世界史', '第42次', '351022182217416', '（2）英语六级笔试', '2019-05-01 14:32:09');
INSERT INTO `tss_student_info` VALUES ('5cc7c09bef481bc321a787b2', '5cc7bf462c5a2653c1d8a15f', '刘滢婧', 'hG8YtECMr70XEtNdTZKgp0K5c+ePH3c+4aFGWWVPZXc=', '105062015052', '福建师范大学', '本科', 0, '15', '数学与信息学院', '数学与应用数学', '1516金融数学班', '第42次', '351022182207101', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc7c09bef481bc321a787b3', '5cc7bf462c5a2653c1d8a15f', '孙锦文', 'cMUx0nUMF1UO5kXBw8hvDBRxQPyX1qCARME2GhN0j3E=', '102112017276', '福建师范大学', '本科', 0, '17', '文学院', '中国语言文学类', '1702中国语言文学类6班', '第42次', '351021182201413', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc7c09bef481bc321a787b4', '5cc7bf462c5a2653c1d8a15f', '王婧怡', 'pJ+c4dhjtPjuUdgph91q8/e8WxedO4KM4AsdU8fJz4k=', '128012017044', '福建师范大学', '本科', 0, '17', '法学院', '法学', '1728法学2班', '第42次', '351022182223422', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc7c09bef481bc321a787b5', '5cc7bf462c5a2653c1d8a15f', '李易璇', 'lCiolYvpkPwCj9HMGcLaHQpys2u0y4gVGc5+L70FKQE=', '101082017029', '福建师范大学', '本科', 0, '17', '经济学院', '工商管理', '1701工商管理', '第42次', '351022182220528', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc7c09bef481bc321a787b6', '5cc7bf462c5a2653c1d8a15f', '田杨', 'umkHfkGkpjLDShlvd739loMnYreOKBORZLgtyhve7cg=', '20160506', '福建师范大学', '研究生', 0, '16', '社会历史学院', '世界史', '世界史', '第42次', 'S18435102210241', '（S）英语六级口试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc7c09bef481bc321a787b7', '5cc7bf462c5a2653c1d8a15f', '胡良江', '', '113012013071', '', '本科', 0, '2013', '美术学院', '美术学(师范)', '', '43,435', '2200101', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc7c09bef481bc321a787b8', '5cc7bf462c5a2653c1d8a15f', '张景兴', '', '111042013042', '', '本科', 0, '2013', '体育科学学院', '运动训练', '', '43,435', '2200102', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc7c09bef481bc321a787b9', '5cc7bf462c5a2653c1d8a15f', '李若愚', '', '129032014073', '', '本科', 0, '2014', '传播学院', '播音与主持艺术', '', '43,435', '2200103', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc7c09bef481bc321a787ba', '5cc7bf462c5a2653c1d8a15f', '杨青青', '', '111042014003', '', '本科', 0, '2014', '体育科学学院', '运动训练', '', '43,435', '2200104', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc809332c5a26677c45adae', '5cc809292c5a26677c45ada1', '刘泽琳', 'zZiLQJ5EcYhAhLqjj+sr+nxU+vGJ4vPl0kLGfJWPEhw=', 'QSZ20170750', '福建师范大学', '研究生', 0, '17', '外国语学院', '英语笔译', '英语笔译', '第42次', '351021182201627', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc809332c5a26677c45adaf', '5cc809292c5a26677c45ada1', '田杨', 'umkHfkGkpjLDShlvd739loMnYreOKBORZLgtyhve7cg=', '20160506', '福建师范大学', '研究生', 0, '16', '社会历史学院', '世界史', '世界史', '第42次', '351022182217416', '（2）英语六级笔试', '2019-05-01 14:32:09');
INSERT INTO `tss_student_info` VALUES ('5cc809332c5a26677c45adb0', '5cc809292c5a26677c45ada1', '刘滢婧', 'hG8YtECMr70XEtNdTZKgp0K5c+ePH3c+4aFGWWVPZXc=', '105062015052', '福建师范大学', '本科', 0, '15', '数学与信息学院', '数学与应用数学', '1516金融数学班', '第42次', '351022182207101', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc809332c5a26677c45adb1', '5cc809292c5a26677c45ada1', '孙锦文', 'cMUx0nUMF1UO5kXBw8hvDBRxQPyX1qCARME2GhN0j3E=', '102112017276', '福建师范大学', '本科', 0, '17', '文学院', '中国语言文学类', '1702中国语言文学类6班', '第42次', '351021182201413', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc809332c5a26677c45adb2', '5cc809292c5a26677c45ada1', '王婧怡', 'pJ+c4dhjtPjuUdgph91q8/e8WxedO4KM4AsdU8fJz4k=', '128012017044', '福建师范大学', '本科', 0, '17', '法学院', '法学', '1728法学2班', '第42次', '351022182223422', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc809332c5a26677c45adb3', '5cc809292c5a26677c45ada1', '李易璇', 'lCiolYvpkPwCj9HMGcLaHQpys2u0y4gVGc5+L70FKQE=', '101082017029', '福建师范大学', '本科', 0, '17', '经济学院', '工商管理', '1701工商管理', '第42次', '351022182220528', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc809332c5a26677c45adb4', '5cc809292c5a26677c45ada1', '田杨', 'umkHfkGkpjLDShlvd739loMnYreOKBORZLgtyhve7cg=', '20160506', '福建师范大学', '研究生', 0, '16', '社会历史学院', '世界史', '世界史', '第42次', 'S18435102210241', '（S）英语六级口试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc809332c5a26677c45adb5', '5cc809292c5a26677c45ada1', '胡良江', '', '113012013071', '', '本科', 0, '2013', '美术学院', '美术学(师范)', '', '43,435', '2200101', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc809332c5a26677c45adb6', '5cc809292c5a26677c45ada1', '张景兴', '', '111042013042', '', '本科', 0, '2013', '体育科学学院', '运动训练', '', '43,435', '2200102', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc809332c5a26677c45adb7', '5cc809292c5a26677c45ada1', '李若愚', '', '129032014073', '', '本科', 0, '2014', '传播学院', '播音与主持艺术', '', '43,435', '2200103', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cc809332c5a26677c45adb8', '5cc809292c5a26677c45ada1', '杨青青', '', '111042014003', '', '本科', 0, '2014', '体育科学学院', '运动训练', '', '43,435', '2200104', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0dd412c5a2676764f1b58', '5cc7e7a02c5a265c986deca6', '刘泽琳', 'zZiLQJ5EcYhAhLqjj+sr+nxU+vGJ4vPl0kLGfJWPEhw=', 'QSZ20170750', '福建师范大学', '研究生', 0, '17', '外国语学院', '英语笔译', '英语笔译', '第42次', '351021182201627', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0dd412c5a2676764f1b59', '5cc7e7a02c5a265c986deca6', '田杨', 'umkHfkGkpjLDShlvd739loMnYreOKBORZLgtyhve7cg=', '20160506', '福建师范大学', '研究生', 0, '16', '社会历史学院', '世界史', '世界史', '第42次', '351022182217416', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0dd412c5a2676764f1b5a', '5cc7e7a02c5a265c986deca6', '刘滢婧', 'hG8YtECMr70XEtNdTZKgp0K5c+ePH3c+4aFGWWVPZXc=', '105062015052', '福建师范大学', '本科', 0, '15', '数学与信息学院', '数学与应用数学', '1516金融数学班', '第42次', '351022182207101', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0dd412c5a2676764f1b5b', '5cc7e7a02c5a265c986deca6', '孙锦文', 'cMUx0nUMF1UO5kXBw8hvDBRxQPyX1qCARME2GhN0j3E=', '102112017276', '福建师范大学', '本科', 0, '17', '文学院', '中国语言文学类', '1702中国语言文学类6班', '第42次', '351021182201413', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0dd412c5a2676764f1b5c', '5cc7e7a02c5a265c986deca6', '王婧怡', 'pJ+c4dhjtPjuUdgph91q8/e8WxedO4KM4AsdU8fJz4k=', '128012017044', '福建师范大学', '本科', 0, '17', '法学院', '法学', '1728法学2班', '第42次', '351022182223422', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0dd412c5a2676764f1b5d', '5cc7e7a02c5a265c986deca6', '李易璇', 'lCiolYvpkPwCj9HMGcLaHQpys2u0y4gVGc5+L70FKQE=', '101082017029', '福建师范大学', '本科', 0, '17', '经济学院', '工商管理', '1701工商管理', '第42次', '351022182220528', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0dd412c5a2676764f1b5e', '5cc7e7a02c5a265c986deca6', '田杨', 'umkHfkGkpjLDShlvd739loMnYreOKBORZLgtyhve7cg=', '20160506', '福建师范大学', '研究生', 0, '16', '社会历史学院', '世界史', '世界史', '第42次', 'S18435102210241', '（S）英语六级口试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0dd412c5a2676764f1b5f', '5cc7e7a02c5a265c986deca6', '胡良江', '', '113012013071', '', '本科', 0, '2013', '美术学院', '美术学(师范)', '', '43,435', '2200101', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0dd412c5a2676764f1b60', '5cc7e7a02c5a265c986deca6', '张景兴', '', '111042013042', '', '本科', 0, '2013', '体育科学学院', '运动训练', '', '43,435', '2200102', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0dd412c5a2676764f1b61', '5cc7e7a02c5a265c986deca6', '李若愚', '', '129032014073', '', '本科', 0, '2014', '传播学院', '播音与主持艺术', '', '43,435', '2200103', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0dd412c5a2676764f1b62', '5cc7e7a02c5a265c986deca6', '杨青青', '', '111042014003', '', '本科', 0, '2014', '体育科学学院', '运动训练', '', '43,435', '2200104', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0f68f2572fc0717c847d6', '5cd0f0312572fc06925c74fd', '刘泽琳', 'zZiLQJ5EcYhAhLqjj+sr+nxU+vGJ4vPl0kLGfJWPEhw=', 'QSZ20170750', '福建师范大学', '研究生', 0, '17', '外国语学院', '英语笔译', '英语笔译', '第42次', '351021182201627', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0f68f2572fc0717c847d7', '5cd0f0312572fc06925c74fd', '田杨', 'umkHfkGkpjLDShlvd739loMnYreOKBORZLgtyhve7cg=', '20160506', '福建师范大学', '研究生', 0, '16', '社会历史学院', '世界史', '世界史', '第42次', '351022182217416', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0f68f2572fc0717c847d8', '5cd0f0312572fc06925c74fd', '刘滢婧', 'hG8YtECMr70XEtNdTZKgp0K5c+ePH3c+4aFGWWVPZXc=', '105062015052', '福建师范大学', '本科', 0, '15', '数学与信息学院', '数学与应用数学', '1516金融数学班', '第42次', '351022182207101', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0f68f2572fc0717c847d9', '5cd0f0312572fc06925c74fd', '孙锦文', 'cMUx0nUMF1UO5kXBw8hvDBRxQPyX1qCARME2GhN0j3E=', '102112017276', '福建师范大学', '本科', 0, '17', '文学院', '中国语言文学类', '1702中国语言文学类6班', '第42次', '351021182201413', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0f68f2572fc0717c847da', '5cd0f0312572fc06925c74fd', '王婧怡', 'pJ+c4dhjtPjuUdgph91q8/e8WxedO4KM4AsdU8fJz4k=', '128012017044', '福建师范大学', '本科', 0, '17', '法学院', '法学', '1728法学2班', '第42次', '351022182223422', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0f68f2572fc0717c847db', '5cd0f0312572fc06925c74fd', '李易璇', 'lCiolYvpkPwCj9HMGcLaHQpys2u0y4gVGc5+L70FKQE=', '101082017029', '福建师范大学', '本科', 0, '17', '经济学院', '工商管理', '1701工商管理', '第42次', '351022182220528', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0f68f2572fc0717c847dc', '5cd0f0312572fc06925c74fd', '田杨', 'umkHfkGkpjLDShlvd739loMnYreOKBORZLgtyhve7cg=', '20160506', '福建师范大学', '研究生', 0, '16', '社会历史学院', '世界史', '世界史', '第42次', 'S18435102210241', '（S）英语六级口试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0f68f2572fc0717c847dd', '5cd0f0312572fc06925c74fd', '胡良江', '', '113012013071', '', '本科', 0, '2013', '美术学院', '美术学(师范)', '', '43,435', '2200101', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0f68f2572fc0717c847de', '5cd0f0312572fc06925c74fd', '张景兴', '', '111042013042', '', '本科', 0, '2013', '体育科学学院', '运动训练', '', '43,435', '2200102', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0f68f2572fc0717c847df', '5cd0f0312572fc06925c74fd', '李若愚', '', '129032014073', '', '本科', 0, '2014', '传播学院', '播音与主持艺术', '', '43,435', '2200103', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd0f68f2572fc0717c847e0', '5cd0f0312572fc06925c74fd', '杨青青', '', '111042014003', '', '本科', 0, '2014', '体育科学学院', '运动训练', '', '43,435', '2200104', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd11ca92572fc090424bf44', '5cd11ca32572fc090424bf39', '刘泽琳', 'zZiLQJ5EcYhAhLqjj+sr+nxU+vGJ4vPl0kLGfJWPEhw=', 'QSZ20170750', '福建师范大学', '研究生', 0, '17', '外国语学院', '英语笔译', '英语笔译', '第42次', '351021182201627', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd11ca92572fc090424bf45', '5cd11ca32572fc090424bf39', '田杨', 'umkHfkGkpjLDShlvd739loMnYreOKBORZLgtyhve7cg=', '20160506', '福建师范大学', '研究生', 0, '16', '社会历史学院', '世界史', '世界史', '第42次', '351022182217416', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd11ca92572fc090424bf46', '5cd11ca32572fc090424bf39', '刘滢婧', 'hG8YtECMr70XEtNdTZKgp0K5c+ePH3c+4aFGWWVPZXc=', '105062015052', '福建师范大学', '本科', 0, '15', '数学与信息学院', '数学与应用数学', '1516金融数学班', '第42次', '351022182207101', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd11ca92572fc090424bf47', '5cd11ca32572fc090424bf39', '孙锦文', 'cMUx0nUMF1UO5kXBw8hvDBRxQPyX1qCARME2GhN0j3E=', '102112017276', '福建师范大学', '本科', 0, '17', '文学院', '中国语言文学类', '1702中国语言文学类6班', '第42次', '351021182201413', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd11ca92572fc090424bf48', '5cd11ca32572fc090424bf39', '王婧怡', 'pJ+c4dhjtPjuUdgph91q8/e8WxedO4KM4AsdU8fJz4k=', '128012017044', '福建师范大学', '本科', 0, '17', '法学院', '法学', '1728法学2班', '第42次', '351022182223422', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd11ca92572fc090424bf49', '5cd11ca32572fc090424bf39', '李易璇', 'lCiolYvpkPwCj9HMGcLaHQpys2u0y4gVGc5+L70FKQE=', '101082017029', '福建师范大学', '本科', 0, '17', '经济学院', '工商管理', '1701工商管理', '第42次', '351022182220528', '（2）英语六级笔试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd11ca92572fc090424bf4a', '5cd11ca32572fc090424bf39', '田杨', 'umkHfkGkpjLDShlvd739loMnYreOKBORZLgtyhve7cg=', '20160506', '福建师范大学', '研究生', 0, '16', '社会历史学院', '世界史', '世界史', '第42次', 'S18435102210241', '（S）英语六级口试', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd11ca92572fc090424bf4b', '5cd11ca32572fc090424bf39', '胡良江', '', '113012013071', '', '本科', 0, '2013', '美术学院', '美术学(师范)', '', '43,435', '2200101', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd11ca92572fc090424bf4c', '5cd11ca32572fc090424bf39', '张景兴', '', '111042013042', '', '本科', 0, '2013', '体育科学学院', '运动训练', '', '43,435', '2200102', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd11ca92572fc090424bf4d', '5cd11ca32572fc090424bf39', '李若愚', '', '129032014073', '', '本科', 0, '2014', '传播学院', '播音与主持艺术', '', '43,435', '2200103', '一级应用技术', NULL);
INSERT INTO `tss_student_info` VALUES ('5cd11ca92572fc090424bf4e', '5cd11ca32572fc090424bf39', '杨青青', '', '111042014003', '', '本科', 0, '2014', '体育科学学院', '运动训练', '', '43,435', '2200104', '一级应用技术', NULL);
COMMIT;

-- ----------------------------
-- Table structure for tss_submit_person
-- ----------------------------
DROP TABLE IF EXISTS `tss_submit_person`;
CREATE TABLE `tss_submit_person` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `college_id` bigint(20) unsigned NOT NULL COMMENT '所属学院或部门ID',
  `work_code` varchar(30) NOT NULL DEFAULT '' COMMENT '工号、学号等',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '姓名',
  `phone` varchar(15) DEFAULT '' COMMENT '联系电话',
  `sex` varchar(2) DEFAULT '' COMMENT '性别',
  `pid` varchar(100) DEFAULT '' COMMENT '身份证号',
  `bank` varchar(50) DEFAULT '' COMMENT '银行',
  `bank_open_code` varchar(20) DEFAULT '' COMMENT '开户行代码',
  `bank_open` varchar(50) DEFAULT '' COMMENT '开户行',
  `bank_code` varchar(30) DEFAULT '' COMMENT '银行卡号',
  `category` varchar(30) DEFAULT '' COMMENT '人员类别',
  `type` varchar(40) DEFAULT '' COMMENT '人员类型',
  `money_type` varchar(50) DEFAULT '' COMMENT '汇款类型',
  `finance` varchar(40) DEFAULT '' COMMENT '是否入财务系统',
  `yes_no` varchar(10) DEFAULT '' COMMENT '是否参加监考',
  `address` varchar(64) NOT NULL DEFAULT '' COMMENT '选择校区可调剂和旗山',
  `te_id` varchar(64) NOT NULL DEFAULT '' COMMENT '父考试id',
  PRIMARY KEY (`id`,`te_id`) USING BTREE,
  UNIQUE KEY `id_exid` (`id`,`te_id`) USING BTREE COMMENT '人员id与exid联合'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='报名岗位人员总表';

-- ----------------------------
-- Table structure for tss_temp_post_person
-- ----------------------------
DROP TABLE IF EXISTS `tss_temp_post_person`;
CREATE TABLE `tss_temp_post_person` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '岗位唯一id',
  `post_id` varchar(64) NOT NULL DEFAULT '' COMMENT '岗位表id',
  `tep_id` varchar(64) NOT NULL DEFAULT '' COMMENT '考场考点id',
  `post_name` varchar(100) NOT NULL COMMENT '岗位名称',
  `post_free` decimal(11,3) NOT NULL COMMENT '岗位费用',
  `person_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '岗位人数',
  `tpi_id_str` varchar(4800) NOT NULL COMMENT '已安排的岗位人员id字符串',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='岗位人员临时表';

-- ----------------------------
-- Records of tss_temp_post_person
-- ----------------------------
BEGIN;
INSERT INTO `tss_temp_post_person` VALUES ('5cd11ee62572fc090424bf67', '5cd11e852572fc090424bf60', '001201952001', '监考前', 200.000, 1, '5cc7c36f2c5a2654d73ac8ce');
INSERT INTO `tss_temp_post_person` VALUES ('5cd11eff2572fc090424bf68', '5cd11e8c2572fc090424bf61', '002201952001', '监考后', 200.000, 1, '5cc7c3aa2c5a2654d73ac8d0');
INSERT INTO `tss_temp_post_person` VALUES ('5cd11f2a2572fc090424bf69', '5cd11e972572fc090424bf62', '003201952001', '出卷', 500.000, 1, '5cc7c3f12c5a2654d73ac8d2');
INSERT INTO `tss_temp_post_person` VALUES ('5cd11f452572fc090424bf6a', '5cd11ea62572fc090424bf63', '004201952001', '改卷', 600.000, 1, '5cc7c3aa2c5a2654d73ac8d0');
INSERT INTO `tss_temp_post_person` VALUES ('5cd11fb82572fc090424bf6c', '5cd11eb72572fc090424bf65', '006201952001', '保洁', 100.000, 1, '');
INSERT INTO `tss_temp_post_person` VALUES ('5cd11fe02572fc090424bf6d', '5cd11eb02572fc090424bf64', '007201952001', '巡考', 300.000, 1, '');
INSERT INTO `tss_temp_post_person` VALUES ('5cd1203e2572fc090424bf71', '5cd1200f2572fc090424bf6e', '008201952001', '保安', 400.000, 1, '5cc7c3922c5a2654d73ac8cf');
INSERT INTO `tss_temp_post_person` VALUES ('5cd1204b2572fc090424bf72', '5cd1201d2572fc090424bf6f', '009201952001', '监控', 400.000, 1, '5cc7c3c02c5a2654d73ac8d1');
INSERT INTO `tss_temp_post_person` VALUES ('5cd120fd2c5a2638cdceeef5', '5cd1202a2572fc090424bf70', '010201952001', '候补', 400.000, 1, '5cc7c40b2c5a2654d73ac8d3');
COMMIT;

-- ----------------------------
-- Table structure for tss_temp_seat_schedule
-- ----------------------------
DROP TABLE IF EXISTS `tss_temp_seat_schedule`;
CREATE TABLE `tss_temp_seat_schedule` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT 'userId',
  `xymc` varchar(64) NOT NULL DEFAULT '' COMMENT '学院名称',
  `xm` varchar(128) NOT NULL DEFAULT '' COMMENT '姓名',
  `bkjb` varchar(255) NOT NULL DEFAULT '' COMMENT '报考级别',
  `te_id` varchar(64) NOT NULL DEFAULT '' COMMENT '考试ID',
  `tep_id` varchar(64) NOT NULL DEFAULT '' COMMENT '考场考点id',
  `seat_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '座位号（0未安排）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='学生座位安排临时表';

-- ----------------------------
-- Records of tss_temp_seat_schedule
-- ----------------------------
BEGIN;
INSERT INTO `tss_temp_seat_schedule` VALUES ('5cd11ca92572fc090424bf44', '外国语学院', '刘泽琳', '（2）英语六级笔试', '5cd11ca32572fc090424bf39', '001201952001', 1);
INSERT INTO `tss_temp_seat_schedule` VALUES ('5cd11ca92572fc090424bf45', '社会历史学院', '田杨', '（2）英语六级笔试', '5cd11ca32572fc090424bf39', '003201952001', 1);
INSERT INTO `tss_temp_seat_schedule` VALUES ('5cd11ca92572fc090424bf46', '数学与信息学院', '刘滢婧', '（2）英语六级笔试', '5cd11ca32572fc090424bf39', '002201952001', 1);
INSERT INTO `tss_temp_seat_schedule` VALUES ('5cd11ca92572fc090424bf47', '文学院', '孙锦文', '（2）英语六级笔试', '5cd11ca32572fc090424bf39', '004201952001', 1);
INSERT INTO `tss_temp_seat_schedule` VALUES ('5cd11ca92572fc090424bf48', '法学院', '王婧怡', '（2）英语六级笔试', '5cd11ca32572fc090424bf39', '010201952001', 1);
INSERT INTO `tss_temp_seat_schedule` VALUES ('5cd11ca92572fc090424bf49', '经济学院', '李易璇', '（2）英语六级笔试', '5cd11ca32572fc090424bf39', '006201952001', 1);
INSERT INTO `tss_temp_seat_schedule` VALUES ('5cd11ca92572fc090424bf4a', '社会历史学院', '田杨', '（S）英语六级口试', '5cd11ca32572fc090424bf39', '009201952001', 1);
INSERT INTO `tss_temp_seat_schedule` VALUES ('5cd11ca92572fc090424bf4b', '美术学院', '胡良江', '一级应用技术', '5cd11ca32572fc090424bf39', '', 0);
INSERT INTO `tss_temp_seat_schedule` VALUES ('5cd11ca92572fc090424bf4c', '体育科学学院', '张景兴', '一级应用技术', '5cd11ca32572fc090424bf39', '', 0);
INSERT INTO `tss_temp_seat_schedule` VALUES ('5cd11ca92572fc090424bf4d', '传播学院', '李若愚', '一级应用技术', '5cd11ca32572fc090424bf39', '008201952001', 1);
INSERT INTO `tss_temp_seat_schedule` VALUES ('5cd11ca92572fc090424bf4e', '体育科学学院', '杨青青', '一级应用技术', '5cd11ca32572fc090424bf39', '007201952001', 1);
COMMIT;

-- ----------------------------
-- Table structure for tss_temp_student_info
-- ----------------------------
DROP TABLE IF EXISTS `tss_temp_student_info`;
CREATE TABLE `tss_temp_student_info` (
  `user_id` varchar(64) NOT NULL DEFAULT '',
  `te_id` varchar(64) DEFAULT NULL COMMENT '考试id',
  `xm` varchar(32) NOT NULL DEFAULT '' COMMENT '姓名',
  `zjhm` char(100) NOT NULL DEFAULT '' COMMENT '证件号码(身份证)',
  `xh` char(32) NOT NULL DEFAULT '' COMMENT '学号',
  `ssxx` varchar(64) NOT NULL DEFAULT '' COMMENT '所属学校',
  `xl` varchar(32) NOT NULL DEFAULT '' COMMENT '学历',
  `xz` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '学制',
  `nz` char(16) NOT NULL DEFAULT '' COMMENT '年级',
  `yx` varchar(128) NOT NULL DEFAULT '' COMMENT '院系',
  `zy` varchar(128) NOT NULL DEFAULT '' COMMENT '专业',
  `bj` varchar(128) NOT NULL DEFAULT '' COMMENT '班级',
  `kssjhcs` varchar(128) NOT NULL DEFAULT '' COMMENT '考试时间或次数',
  `bszkzh` char(64) NOT NULL DEFAULT '' COMMENT '笔试准考证号',
  `bskmmc` varchar(256) DEFAULT '' COMMENT '笔试科目名称',
  `time` datetime DEFAULT NULL COMMENT '签到时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='学生数据临时表';

-- ----------------------------
-- Table structure for tss_temp_submit_person
-- ----------------------------
DROP TABLE IF EXISTS `tss_temp_submit_person`;
CREATE TABLE `tss_temp_submit_person` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `college_id` bigint(20) unsigned NOT NULL COMMENT '所属学院或部门ID',
  `work_code` varchar(30) NOT NULL DEFAULT '' COMMENT '工号、学号等',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '姓名',
  `phone` varchar(15) DEFAULT '' COMMENT '联系电话',
  `sex` varchar(2) DEFAULT '' COMMENT '性别',
  `pid` varchar(100) DEFAULT '' COMMENT '身份证号',
  `bank` varchar(50) DEFAULT '' COMMENT '银行',
  `bank_open_code` varchar(20) DEFAULT '' COMMENT '开户行代码',
  `bank_open` varchar(50) DEFAULT '' COMMENT '开户行',
  `bank_code` varchar(30) DEFAULT '' COMMENT '银行卡号',
  `category` varchar(30) DEFAULT '' COMMENT '人员类别',
  `type` varchar(40) DEFAULT '' COMMENT '人员类型',
  `money_type` varchar(50) DEFAULT '' COMMENT '汇款类型',
  `finance` varchar(40) DEFAULT '' COMMENT '是否入财务系统',
  `yes_no` varchar(10) DEFAULT '' COMMENT '是否参加监考',
  `address` varchar(64) NOT NULL DEFAULT '' COMMENT '选择校区可调剂和旗山',
  `te_id` varchar(64) NOT NULL DEFAULT '' COMMENT '父考试id（mainExamId）',
  PRIMARY KEY (`id`,`te_id`) USING BTREE,
  UNIQUE KEY `id_exid` (`id`,`te_id`) USING BTREE COMMENT '人员id与考试id联合主键'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='报名岗位人员临时表';

-- ----------------------------
-- Records of tss_temp_submit_person
-- ----------------------------
BEGIN;
INSERT INTO `tss_temp_submit_person` VALUES ('5cc7c36f2c5a2654d73ac8ce', 90, '52522', '计本林伟1', '13455331222', '男', 'rINAxWTCitxZiaEy7unMThWIqEHbL8TFk92Ak/kjGQE=', '7', '', '计本林伟1', '12121251', '10', '8', '', '', '', '旗山校区', '5cd11ca32572fc090424bf39');
INSERT INTO `tss_temp_submit_person` VALUES ('5cc7c3922c5a2654d73ac8cf', 90, '45132354', '计本林伟2', '15632563215', '女', 'WeZxGm2MWa3TCFSYPoFIDp0oqEV9G7F2Gn2Q0SR+2t0=', '7', '', '计本林伟2', '2131312', '10', '8', '', '', '', '旗山校区', '5cd11ca32572fc090424bf39');
INSERT INTO `tss_temp_submit_person` VALUES ('5cc7c3aa2c5a2654d73ac8d0', 90, '781231564', '计本林伟3', '15365584562', '男', 'wWENoDeVFCuybsKLAdCPBNFF71QVwa2dmCYxdpg5AEc=', '1116179691917623297', '', '计本林伟3', '21321', '10', '9', '', '', '', '旗山校区', '5cd11ca32572fc090424bf39');
INSERT INTO `tss_temp_submit_person` VALUES ('5cc7c3c02c5a2654d73ac8d1', 90, '556222', '计本林伟4', '15874562548', '女', 'ah/PRRnEoax4GlGjETbUcRRxQPyX1qCARME2GhN0j3E=', '7', '', '计本林伟4', '465131', '10', '8', '', '', '', '旗山校区', '5cd11ca32572fc090424bf39');
INSERT INTO `tss_temp_submit_person` VALUES ('5cc7c3f12c5a2654d73ac8d2', 90, '524674213', '计本林伟5', '13057425461', '男', 'O2sRq5cBGl9Npbvejx4wHGZ7u2thPY88d+GSRdk13pw=', '1116179691917623297', '', '计本林伟5', '446512313', '10', '8', '', '', '', '旗山校区', '5cd11ca32572fc090424bf39');
INSERT INTO `tss_temp_submit_person` VALUES ('5cc7c40b2c5a2654d73ac8d3', 90, '454321213', '计本林伟6', '15236584563', '男', 'k6/pMYBLpeoSdYsf+rJ3l+1tf2N/TbG6g//xKOhcSko=', '1116179691917623297', '', '计本林伟6', '7845132465', '10', '9', '', '', '', '旗山校区', '5cd11ca32572fc090424bf39');
INSERT INTO `tss_temp_submit_person` VALUES ('5cc7c4242c5a2654d73ac8d4', 90, '45432132', '计本林伟7', '18652352462', '男', 'z2PX3OMrMn2KIUT17yyFRxzflAKFUhryfiu6EG45w7E=', '7', '', '计本林伟7', '78413123123', '10', '8', '', '', '', '旗山校区', '5cd11ca32572fc090424bf39');
INSERT INTO `tss_temp_submit_person` VALUES ('5cc7c43a2c5a2654d73ac8d6', 90, '78432132132', '计本林伟8', '15568323255', '男', 'hSlPEgGMlLDo9x4r3kv082Z7u2thPY88d+GSRdk13pw=', '1116179691917623297', '', '计本林伟8', '768133121', '10', '8', '', '', '', '旗山校区', '5cd11ca32572fc090424bf39');
INSERT INTO `tss_temp_submit_person` VALUES ('5cc7c4522c5a2654d73ac8d7', 90, '243252352', '计本林伟9', '15865425625', '女', 'HMHq9ALYNjLJIOaqKtucc4OHVm06841Y1NB6/Hiv3ag=', '7', '', '计本林伟9', '513132132', '10', '8', '', '', '', '旗山校区', '5cd11ca32572fc090424bf39');
INSERT INTO `tss_temp_submit_person` VALUES ('5cc7c46d2c5a2654d73ac8d8', 90, '51312313', '计本林伟10', '18965355551', '男', 'y0jQgQSY4ZTFHJbuTHDTWasVDNdG8OD4Jo3suBgjH0s=', '7', '', '计本林伟10', '456456456', '10', '8', '', '', '', '旗山校区', '5cd11ca32572fc090424bf39');
INSERT INTO `tss_temp_submit_person` VALUES ('5cc7c48b2c5a2654d73ac8d9', 93, '513218946', '数本林伟1', '15685452621', '男', 'jzHzCyS0fvyoaE7PgCKCAYMnYreOKBORZLgtyhve7cg=', '7', '', '数本林伟1', '154645', '10', '8', '', '', '', '旗山校区', '5cd11ca32572fc090424bf39');
INSERT INTO `tss_temp_submit_person` VALUES ('5cc7c4a02c5a2654d73ac8da', 93, '241511', '数本林伟2', '18525625425', '男', 'Jf44cEBFQahTGDFKxvE83P6PredLemhdMHmEbFO7Qc8=', '7', '', '数本林伟2', '35463463', '10', '8', '', '', '', '旗山校区', '5cd11ca32572fc090424bf39');
INSERT INTO `tss_temp_submit_person` VALUES ('5cc7c4b12c5a2654d73ac8db', 93, '2431', '数本林伟3', '13565645242', '男', 'XtS2B0GVZ5IYK7bzj8883w4ZwcnG0RVac4pBWbPngv4=', '1116179691917623297', '', '数本林伟3', '4141241', '10', '8', '', '', '', '旗山校区', '5cd11ca32572fc090424bf39');
INSERT INTO `tss_temp_submit_person` VALUES ('5cc7c4ce2c5a2654d73ac8dc', 93, '45132132', '数本林伟4', '13457676747', '男', '3D5IbbYa8SwUTHKgT07vhiq1DBPbEhK2xULYNnGA+S0=', '7', '', '数本林伟4', '215163161', '10', '8', '', '', '', '旗山校区', '5cd11ca32572fc090424bf39');
INSERT INTO `tss_temp_submit_person` VALUES ('5cc7c4e72c5a2654d73ac8dd', 93, '1221516361', '数本林伟5', '15668657542', '男', 'xG9PfiqHVE+f8XkTYBKRY0K5c+ePH3c+4aFGWWVPZXc=', '1116179691917623297', '', '数本林伟5', '12412412', '10', '8', '', '', '', '旗山校区', '5cd11ca32572fc090424bf39');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
