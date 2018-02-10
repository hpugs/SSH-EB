/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : ssheb

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-10 17:52:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for log_sms_send
-- ----------------------------
DROP TABLE IF EXISTS `log_sms_send`;
CREATE TABLE `log_sms_send` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '短信发送ID',
  `ip` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户IP地址',
  `phone` varchar(11) CHARACTER SET utf8 NOT NULL COMMENT '手机号',
  `content` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '短信内容',
  `type` tinyint(4) unsigned NOT NULL COMMENT '短信类型 (1：验证码；2：通知)',
  `autograph` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '短信签名（阿里云短信后台提供）',
  `template` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '短信模板（阿里云短信后台提供）',
  `response_code` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '短信发送状态',
  `response_msg` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '短信发送日志',
  `request_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '请求id',
  `biz_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '状态id',
  `response_sub_time` datetime DEFAULT NULL COMMENT '短信发送时间 ',
  `source` tinyint(4) unsigned DEFAULT '1' COMMENT '来源（1：PC（默认）；2：H5；3、Android；4、IOS；5、ERP）',
  `enabled` tinyint(4) DEFAULT '1' COMMENT '是否有效\n（1：是；2：否）',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for log_user_login
-- ----------------------------
DROP TABLE IF EXISTS `log_user_login`;
CREATE TABLE `log_user_login` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '登录日志id',
  `ip` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户登录Ip地址',
  `session_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '登录用户SessionId',
  `account_type` tinyint(4) unsigned DEFAULT '2' COMMENT '账号类型（1、用户名；2、手机号（默认）；3、邮箱）',
  `login_account` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '登录账号',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `login_result` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '登录返回：登录成功；登录失败；不存在该用户',
  `login_state` tinyint(4) DEFAULT NULL COMMENT '登录状态：（1：成功；2：失败 ）用于一天内多次登录失败锁定账号',
  `source` tinyint(4) unsigned DEFAULT '1' COMMENT '来源（1：PC（默认）；2：H5；3、Android；4、IOS；5、ERP）',
  `state` tinyint(4) unsigned DEFAULT '1' COMMENT '是否删除（1、是（默认）；2：否）',
  `update_user_id` int(11) DEFAULT NULL COMMENT '修改用户Id(来自ERP）',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `account` varchar(20) DEFAULT NULL COMMENT '用户账号',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `passwd` varchar(100) DEFAULT NULL COMMENT '用户密码',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `state` tinyint(4) unsigned DEFAULT '1' COMMENT '账户状态（1、正常（默认）；2、删除、3、冻结）',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for user_third_login
-- ----------------------------
DROP TABLE IF EXISTS `user_third_login`;
CREATE TABLE `user_third_login` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '第三方登录表id',
  `user_account_id` int(11) unsigned DEFAULT NULL COMMENT '用户表id',
  `type` tinyint(4) unsigned DEFAULT NULL COMMENT '类型(1/2/3) 1:QQ 2:微信 3:新浪',
  `open_id` varchar(255) DEFAULT NULL COMMENT '授权用户唯一标识',
  `msg` varchar(255) DEFAULT NULL COMMENT '返回信息',
  `avatar` varchar(255) DEFAULT NULL COMMENT '第三方头像',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '第三方昵称',
  `gender` varchar(4) DEFAULT NULL COMMENT '性别',
  `country` varchar(255) DEFAULT NULL COMMENT '国家',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '记录最后一次修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
