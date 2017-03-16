/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50630
Source Host           : 127.0.0.1:3306
Source Database       : chat

Target Server Type    : MYSQL
Target Server Version : 50630
File Encoding         : 65001

Date: 2017-03-16 18:09:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_relationship
-- ----------------------------
DROP TABLE IF EXISTS `t_relationship`;
CREATE TABLE `t_relationship` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户id',
  `fid` int(11) NOT NULL COMMENT '对应的朋友id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aid` int(11) NOT NULL COMMENT '账号',
  `nickname` varchar(30) DEFAULT NULL COMMENT '昵称',
  `pwd` varchar(32) NOT NULL COMMENT '密码，MD5加密',
  `region` varchar(255) DEFAULT NULL COMMENT '所在地区',
  `signature` varchar(255) DEFAULT NULL COMMENT '个性签名',
  `gender` tinyint(1) DEFAULT '0' COMMENT '用户性别，0代表男，1代表女',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `is_login` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否登录，0未登录，1登录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
