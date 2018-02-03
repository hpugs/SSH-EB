/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : ssheb

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-03 20:00:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for useraccount
-- ----------------------------
DROP TABLE IF EXISTS `useraccount`;
CREATE TABLE `useraccount` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `account` varchar(20) DEFAULT NULL COMMENT '用户账号',
  `passwd` varchar(20) DEFAULT NULL COMMENT '用户密码',
  `state` tinyint(4) unsigned DEFAULT '1' COMMENT '账户状态（1、正常（默认）；2、删除、3、冻结）',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
