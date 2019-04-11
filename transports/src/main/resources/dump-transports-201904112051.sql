-- MySQL dump 10.13  Distrib 5.7.21, for Linux (x86_64)
--
-- Host: localhost    Database: transports
-- ------------------------------------------------------
-- Server version	5.7.21-1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persistent_logins` (
  `series` varchar(255) NOT NULL,
  `last_used` datetime DEFAULT NULL COMMENT '最后一次时间',
  `token` varchar(255) NOT NULL COMMENT '令牌',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  PRIMARY KEY (`series`),
  UNIQUE KEY `UK_bqa9l0go97v49wwyx4c0u3kpd` (`token`),
  UNIQUE KEY `UK_f8t9fsfwc17s6qcbx0ath6l3h` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录验证临时存储';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;
/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_organization`
--

DROP TABLE IF EXISTS `sys_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) DEFAULT NULL COMMENT '启用标识',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`id`),
  KEY `FKmeds2u6ae5usj0ko0bqj3k0eo` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织结构表未用到';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_organization`
--

LOCK TABLES `sys_organization` WRITE;
/*!40000 ALTER TABLE `sys_organization` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_resource`
--

DROP TABLE IF EXISTS `sys_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_resource`
--

LOCK TABLES `sys_resource` WRITE;
/*!40000 ALTER TABLE `sys_resource` DISABLE KEYS */;
INSERT INTO `sys_resource` VALUES (1,'\0','顶级栏目',100,NULL,0,NULL,0),(2,'\0','权限配置',8,'',0,'',1),(3,'\0','角色管理',102,'/role',0,'/role',2),(4,'\0','权限管理',103,'/resource',0,'/resource',2),(5,'\0','用户管理',101,'/user',0,'/user',2),(6,'\0','编辑',100,'/role/editor-role',1,'/role/editor-role',3),(7,'\0','添加权限节点',100,'/resource/add-permission',1,'/resource/add-permission',4),(8,'\0','添加管理员',100,'/user/add-user',1,'/user/add-user',5),(9,'\0','添加角色',100,'/role/add-role',1,'/role/add-role',3),(10,'\0','删除角色',100,'/role/delete',1,'/role/delete',3),(11,'\0','删除用户',100,'/user/delete-user',1,'/user/delete-user',5),(12,'\0','删除权限',100,'/resource/delete',1,'/resource/delete',4),(13,'\0','启用',100,'/user/available-user',1,'/user/available-user',3),(14,'\0','修改管理员密码',100,'/user/modify-password',1,'/user/modify-password',5),(16,NULL,'权限编辑',100,'/resource/editor-permission',1,'/resource/editor-permission',4),(150,'\0','编辑管理员信息',100,'/user/edit-user',1,'/user/edit-user',5),(153,NULL,'运输管理',1,'',0,'',1),(154,NULL,'起点城市管理',10,'/admin/cityStart',0,'/admin/cityStart',153),(155,NULL,'终点城市管理',11,'/admin/cityEnd',0,'/admin/cityEnd',153),(156,NULL,'车辆管理',12,'/admin/car',0,'/admin/car',153),(157,NULL,'货物管理',13,'/admin/goods',0,'/admin/goods',153),(158,NULL,'任务管理',14,'/admin/task',0,'/admin/task',153),(159,NULL,'调度管理',15,'/admin/task',0,'/admin/task',153);
/*!40000 ALTER TABLE `sys_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) DEFAULT NULL COMMENT '启用标识',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,NULL,'管理员','管理员');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_resources`
--

DROP TABLE IF EXISTS `sys_role_resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_resources` (
  `sys_role_id` bigint(20) NOT NULL COMMENT '角色id',
  `resources_id` bigint(20) NOT NULL COMMENT '资源id',
  KEY `FKog6jj4v6yh9e1ilxk2mwuk75a` (`resources_id`),
  KEY `FKsqkqfd2hpr5cc2kbrtgoced2w` (`sys_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和权限关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_resources`
--

LOCK TABLES `sys_role_resources` WRITE;
/*!40000 ALTER TABLE `sys_role_resources` DISABLE KEYS */;
INSERT INTO `sys_role_resources` VALUES (1,2),(1,3),(1,6),(1,9),(1,10),(1,13),(1,4),(1,7),(1,12),(1,16),(1,5),(1,8),(1,11),(1,14),(1,150),(1,153),(1,154),(1,155),(1,156),(1,157),(1,158),(1,159);
/*!40000 ALTER TABLE `sys_role_resources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'2017-07-11 17:42:18','$2a$10$SIU57gfkh/TsIVYALXBNAeDnQzkm652FT9cg4h8wtEfC306uliyYa','2019-01-11 07:34:38','admin','','1191134106@qq.com','15030103078',1),(23,'2019-01-10 13:18:55','$2a$10$8x5Tmu4DQf6jqU4N9JCF0.IHbMfa9oEpneMkANYU.6x49HotEpX1W','2019-03-07 16:16:21','admin1','','','',1),(26,'2019-01-15 02:55:42','$2a$10$KpDQ0oXFKsSmgxOu7dmTlOIPmUf7W9pNYqisQUDZvTkFavG9H6SP2','2019-03-12 07:19:25','saler','','','1111111',1);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_roles`
--

DROP TABLE IF EXISTS `sys_user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_roles` (
  `sys_user_id` bigint(20) NOT NULL COMMENT '用户id',
  `roles_id` bigint(20) NOT NULL COMMENT '角色id',
  KEY `FKdpvc6d7xqpqr43dfuk1s27cqh` (`roles_id`),
  KEY `FKd0ut7sloes191bygyf7a3pk52` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_roles`
--

LOCK TABLES `sys_user_roles` WRITE;
/*!40000 ALTER TABLE `sys_user_roles` DISABLE KEYS */;
INSERT INTO `sys_user_roles` VALUES (1,1);
/*!40000 ALTER TABLE `sys_user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_car`
--

DROP TABLE IF EXISTS `t_car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `status` int(11) DEFAULT NULL COMMENT '使用状态',
  `load_use` double DEFAULT '0' COMMENT '已载重',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_car`
--

LOCK TABLES `t_car` WRITE;
/*!40000 ALTER TABLE `t_car` DISABLE KEYS */;
INSERT INTO `t_car` VALUES (1,'123','123',123,'123','2019-04-11','123','15011108691','123',1,0);
/*!40000 ALTER TABLE `t_car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_city_end`
--

DROP TABLE IF EXISTS `t_city_end`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_city_end` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '城市名',
  `code` varchar(255) DEFAULT NULL COMMENT '唯一编号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_city_end`
--

LOCK TABLES `t_city_end` WRITE;
/*!40000 ALTER TABLE `t_city_end` DISABLE KEYS */;
INSERT INTO `t_city_end` VALUES (1,'天津','002',''),(2,'重庆','009','');
/*!40000 ALTER TABLE `t_city_end` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_city_start`
--

DROP TABLE IF EXISTS `t_city_start`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_city_start` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '城市名',
  `code` varchar(255) DEFAULT NULL COMMENT '唯一编号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_city_start`
--

LOCK TABLES `t_city_start` WRITE;
/*!40000 ALTER TABLE `t_city_start` DISABLE KEYS */;
INSERT INTO `t_city_start` VALUES (1,'北京','001',''),(2,'南京','003','');
/*!40000 ALTER TABLE `t_city_start` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_goods`
--

DROP TABLE IF EXISTS `t_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_goods`
--

LOCK TABLES `t_goods` WRITE;
/*!40000 ALTER TABLE `t_goods` DISABLE KEYS */;
INSERT INTO `t_goods` VALUES (3,'test','test',18,0,'20190411193447fmt','1','1'),(4,'66','test',199,0,'20190411195238aqf','',''),(5,'test','11',11,0,'2019041119524456j','','');
/*!40000 ALTER TABLE `t_goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_task`
--

DROP TABLE IF EXISTS `t_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_task`
--

LOCK TABLES `t_task` WRITE;
/*!40000 ALTER TABLE `t_task` DISABLE KEYS */;
INSERT INTO `t_task` VALUES (1,2,2,'2019-04-11',2,'3434',1,'123');
/*!40000 ALTER TABLE `t_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'transports'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-11 20:51:55
