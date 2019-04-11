/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50720
Source Host           : 127.0.0.1:3306
Source Database       : transports

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-04-11 23:58:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `series` varchar(255) NOT NULL,
  `last_used` datetime DEFAULT NULL COMMENT '最后一次时间',
  `token` varchar(255) NOT NULL COMMENT '令牌',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  PRIMARY KEY (`series`),
  UNIQUE KEY `UK_bqa9l0go97v49wwyx4c0u3kpd` (`token`),
  UNIQUE KEY `UK_f8t9fsfwc17s6qcbx0ath6l3h` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录验证临时存储';

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) DEFAULT NULL COMMENT '启用标识',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`id`),
  KEY `FKmeds2u6ae5usj0ko0bqj3k0eo` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织结构表未用到';

-- ----------------------------
-- Records of sys_organization
-- ----------------------------

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) DEFAULT NULL COMMENT '启动标识',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `order_num` int(11) NOT NULL COMMENT '顺序',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `url` varchar(255) DEFAULT NULL COMMENT '路径',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`id`),
  KEY `FK3fekum3ead5klp7y4lckn5ohi` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8 COMMENT='权限管理';

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', '\0', '顶级栏目', '100', null, '0', null, '0');
INSERT INTO `sys_resource` VALUES ('2', '\0', '权限配置', '8', '', '0', '', '1');
INSERT INTO `sys_resource` VALUES ('3', '\0', '角色管理', '102', '/role', '0', '/role', '2');
INSERT INTO `sys_resource` VALUES ('4', '\0', '权限管理', '103', '/resource', '0', '/resource', '2');
INSERT INTO `sys_resource` VALUES ('5', '\0', '用户管理', '101', '/user', '0', '/user', '2');
INSERT INTO `sys_resource` VALUES ('6', '\0', '编辑', '100', '/role/editor-role', '1', '/role/editor-role', '3');
INSERT INTO `sys_resource` VALUES ('7', '\0', '添加权限节点', '100', '/resource/add-permission', '1', '/resource/add-permission', '4');
INSERT INTO `sys_resource` VALUES ('8', '\0', '添加管理员', '100', '/user/add-user', '1', '/user/add-user', '5');
INSERT INTO `sys_resource` VALUES ('9', '\0', '添加角色', '100', '/role/add-role', '1', '/role/add-role', '3');
INSERT INTO `sys_resource` VALUES ('10', '\0', '删除角色', '100', '/role/delete', '1', '/role/delete', '3');
INSERT INTO `sys_resource` VALUES ('11', '\0', '删除用户', '100', '/user/delete-user', '1', '/user/delete-user', '5');
INSERT INTO `sys_resource` VALUES ('12', '\0', '删除权限', '100', '/resource/delete', '1', '/resource/delete', '4');
INSERT INTO `sys_resource` VALUES ('13', '\0', '启用', '100', '/user/available-user', '1', '/user/available-user', '3');
INSERT INTO `sys_resource` VALUES ('14', '\0', '修改管理员密码', '100', '/user/modify-password', '1', '/user/modify-password', '5');
INSERT INTO `sys_resource` VALUES ('16', null, '权限编辑', '100', '/resource/editor-permission', '1', '/resource/editor-permission', '4');
INSERT INTO `sys_resource` VALUES ('150', '\0', '编辑管理员信息', '100', '/user/edit-user', '1', '/user/edit-user', '5');
INSERT INTO `sys_resource` VALUES ('153', null, '运输管理', '1', '', '0', '', '1');
INSERT INTO `sys_resource` VALUES ('154', null, '起点城市管理', '10', '/admin/cityStart', '0', '/admin/cityStart', '153');
INSERT INTO `sys_resource` VALUES ('155', null, '终点城市管理', '11', '/admin/cityEnd', '0', '/admin/cityEnd', '153');
INSERT INTO `sys_resource` VALUES ('156', null, '车辆管理', '12', '/admin/car', '0', '/admin/car', '153');
INSERT INTO `sys_resource` VALUES ('157', null, '货物管理', '13', '/admin/goods', '0', '/admin/goods', '153');
INSERT INTO `sys_resource` VALUES ('158', null, '任务管理', '14', '/admin/task?flag=1000', '0', '/admin/task?flag=1000', '153');
INSERT INTO `sys_resource` VALUES ('159', null, '调度管理', '15', '/admin/task?flag=1001', '0', '/admin/task?flag=1001', '153');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) DEFAULT NULL COMMENT '启用标识',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', null, '管理员', '管理员');

-- ----------------------------
-- Table structure for sys_role_resources
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resources`;
CREATE TABLE `sys_role_resources` (
  `sys_role_id` bigint(20) NOT NULL COMMENT '角色id',
  `resources_id` bigint(20) NOT NULL COMMENT '资源id',
  KEY `FKog6jj4v6yh9e1ilxk2mwuk75a` (`resources_id`),
  KEY `FKsqkqfd2hpr5cc2kbrtgoced2w` (`sys_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和权限关联表';

-- ----------------------------
-- Records of sys_role_resources
-- ----------------------------
INSERT INTO `sys_role_resources` VALUES ('1', '2');
INSERT INTO `sys_role_resources` VALUES ('1', '3');
INSERT INTO `sys_role_resources` VALUES ('1', '6');
INSERT INTO `sys_role_resources` VALUES ('1', '9');
INSERT INTO `sys_role_resources` VALUES ('1', '10');
INSERT INTO `sys_role_resources` VALUES ('1', '13');
INSERT INTO `sys_role_resources` VALUES ('1', '4');
INSERT INTO `sys_role_resources` VALUES ('1', '7');
INSERT INTO `sys_role_resources` VALUES ('1', '12');
INSERT INTO `sys_role_resources` VALUES ('1', '16');
INSERT INTO `sys_role_resources` VALUES ('1', '5');
INSERT INTO `sys_role_resources` VALUES ('1', '8');
INSERT INTO `sys_role_resources` VALUES ('1', '11');
INSERT INTO `sys_role_resources` VALUES ('1', '14');
INSERT INTO `sys_role_resources` VALUES ('1', '150');
INSERT INTO `sys_role_resources` VALUES ('1', '153');
INSERT INTO `sys_role_resources` VALUES ('1', '154');
INSERT INTO `sys_role_resources` VALUES ('1', '155');
INSERT INTO `sys_role_resources` VALUES ('1', '156');
INSERT INTO `sys_role_resources` VALUES ('1', '157');
INSERT INTO `sys_role_resources` VALUES ('1', '158');
INSERT INTO `sys_role_resources` VALUES ('1', '159');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `available` bit(1) DEFAULT NULL COMMENT '启动标识',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `tel` varchar(255) DEFAULT NULL COMMENT '电话',
  `sex_type` int(11) DEFAULT NULL COMMENT '性别(0.男,1.女)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '2017-07-11 17:42:18', '$2a$10$SIU57gfkh/TsIVYALXBNAeDnQzkm652FT9cg4h8wtEfC306uliyYa', '2019-01-11 07:34:38', 'admin', '', '1191134106@qq.com', '15030103078', '1');
INSERT INTO `sys_user` VALUES ('23', '2019-01-10 13:18:55', '$2a$10$8x5Tmu4DQf6jqU4N9JCF0.IHbMfa9oEpneMkANYU.6x49HotEpX1W', '2019-03-07 16:16:21', 'admin1', '', '', '', '1');
INSERT INTO `sys_user` VALUES ('26', '2019-01-15 02:55:42', '$2a$10$KpDQ0oXFKsSmgxOu7dmTlOIPmUf7W9pNYqisQUDZvTkFavG9H6SP2', '2019-03-12 07:19:25', 'saler', '', '', '1111111', '1');

-- ----------------------------
-- Table structure for sys_user_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_roles`;
CREATE TABLE `sys_user_roles` (
  `sys_user_id` bigint(20) NOT NULL COMMENT '用户id',
  `roles_id` bigint(20) NOT NULL COMMENT '角色id',
  KEY `FKdpvc6d7xqpqr43dfuk1s27cqh` (`roles_id`),
  KEY `FKd0ut7sloes191bygyf7a3pk52` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_roles
-- ----------------------------
INSERT INTO `sys_user_roles` VALUES ('1', '1');

-- ----------------------------
-- Table structure for t_car
-- ----------------------------
DROP TABLE IF EXISTS `t_car`;
CREATE TABLE `t_car` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '车辆编号',
  `type` varchar(255) DEFAULT NULL COMMENT '车辆类型',
  `load_max` double DEFAULT NULL COMMENT '载重量',
  `pai` varchar(255) DEFAULT NULL COMMENT '牌照',
  `pai_time` date DEFAULT NULL COMMENT '上牌时间',
  `name` varchar(255) DEFAULT NULL COMMENT '司机姓名',
  `tel` varchar(255) DEFAULT NULL COMMENT '联系手机',
  `id_card` varchar(255) DEFAULT NULL COMMENT '身份证号',
  `status` int(11) DEFAULT '0' COMMENT '使用状态',
  `load_use` double DEFAULT '0' COMMENT '已载重',
  `load_used` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_car
-- ----------------------------
INSERT INTO `t_car` VALUES ('1', '001', '中型货车', '1000', 'KF666', '2019-04-11', '小刘', '15011108695', '666', '0', '1000', '0');
INSERT INTO `t_car` VALUES ('2', '006', '小型货车', '500', 'KQ5555', '2019-04-26', '小王', '1501108797', '666', '0', '500', '0');
INSERT INTO `t_car` VALUES ('3', '007', '迷你货车', '100', 'HT777', '2019-04-11', '小宋', '15011109878', '777', '0', '100', '0');

-- ----------------------------
-- Table structure for t_city_end
-- ----------------------------
DROP TABLE IF EXISTS `t_city_end`;
CREATE TABLE `t_city_end` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '城市名',
  `code` varchar(255) DEFAULT NULL COMMENT '唯一编号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_city_end
-- ----------------------------
INSERT INTO `t_city_end` VALUES ('1', '天津', '002', '');
INSERT INTO `t_city_end` VALUES ('2', '重庆', '009', '');

-- ----------------------------
-- Table structure for t_city_start
-- ----------------------------
DROP TABLE IF EXISTS `t_city_start`;
CREATE TABLE `t_city_start` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '城市名',
  `code` varchar(255) DEFAULT NULL COMMENT '唯一编号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_city_start
-- ----------------------------
INSERT INTO `t_city_start` VALUES ('1', '北京', '001', '');
INSERT INTO `t_city_start` VALUES ('2', '南京', '003', '');

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batch_code` varchar(255) DEFAULT NULL COMMENT '批次号',
  `name` varchar(255) DEFAULT NULL COMMENT '货物名称',
  `load_m` double DEFAULT NULL COMMENT '重量',
  `status` int(11) DEFAULT '0' COMMENT '状态',
  `code` varchar(255) DEFAULT NULL COMMENT '编号',
  `remark` varchar(1000) DEFAULT NULL COMMENT '注意事项',
  `content` varchar(1000) DEFAULT NULL COMMENT '货物内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_goods
-- ----------------------------
INSERT INTO `t_goods` VALUES ('3', 'mq009', '测试货物', '200', '2', '20190411193447fmt', '轻放', '1');
INSERT INTO `t_goods` VALUES ('4', 'kq565', '测试货物2', '100', '2', '20190411195238aqf', '', '');
INSERT INTO `t_goods` VALUES ('5', 'mq009', '测试货物3', '500', '2', '2019041119524456j', '', '');

-- ----------------------------
-- Table structure for t_task
-- ----------------------------
DROP TABLE IF EXISTS `t_task`;
CREATE TABLE `t_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `start_city_id` bigint(20) DEFAULT NULL COMMENT '起点城市',
  `end_city_id` bigint(20) DEFAULT NULL COMMENT '终点城市',
  `task_date` date DEFAULT NULL COMMENT '任务日期',
  `status` int(11) DEFAULT '0' COMMENT '状态',
  `name` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `car_id` bigint(11) DEFAULT NULL COMMENT '使用车辆',
  `goods_batch` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_task
-- ----------------------------
INSERT INTO `t_task` VALUES ('1', '2', '2', '2019-04-11', '2', '3434', '1', 'mq009');
INSERT INTO `t_task` VALUES ('2', '1', '1', '2019-04-11', '2', '测试', '2', 'kq565');
