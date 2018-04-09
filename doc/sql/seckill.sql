/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-04-09 10:57:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for method_lock
-- ----------------------------
DROP TABLE IF EXISTS `method_lock`;
CREATE TABLE `method_lock` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `method_name` varchar(64) NOT NULL COMMENT '锁定的方法名',
  `desc` varchar(255) NOT NULL COMMENT '备注信息',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uidx_method_name` (`method_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='锁定中的方法';

-- ----------------------------
-- Records of method_lock
-- ----------------------------

-- ----------------------------
-- Table structure for seckill
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存ID',
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '秒杀开始时间',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '秒杀结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- ----------------------------
-- Records of seckill
-- ----------------------------
INSERT INTO `seckill` VALUES ('1000', '1000元秒杀iphone6', '100', '2018-04-06 13:14:57', '2030-12-31 00:00:00', '2018-03-17 19:28:46');
INSERT INTO `seckill` VALUES ('1001', '800元秒杀ipad', '200', '2016-01-01 00:00:00', '2016-01-02 00:00:00', '2018-03-17 19:28:46');
INSERT INTO `seckill` VALUES ('1002', '6600元秒杀mac book pro', '300', '2016-01-01 00:00:00', '2016-01-02 00:00:00', '2018-03-17 19:28:46');
INSERT INTO `seckill` VALUES ('1003', '7000元秒杀iMac', '400', '2019-01-01 00:00:00', '2019-06-02 00:00:00', '2018-03-17 19:28:46');

-- ----------------------------
-- Table structure for success_killed
-- ----------------------------
DROP TABLE IF EXISTS `success_killed`;
CREATE TABLE `success_killed` (
  `seckill_id` bigint(20) NOT NULL COMMENT '秒杀商品ID',
  `user_phone` bigint(20) NOT NULL COMMENT '用户手机号',
  `state` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '状态标识:-1:无效 0:成功 1:已付款 2:已发货',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`,`user_phone`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

-- ----------------------------
-- Records of success_killed
-- ----------------------------
INSERT INTO `success_killed` VALUES ('1000', '18212345678', '0', '2018-03-18 01:06:48');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `fid` bigint(20) NOT NULL,
  `fusername` varchar(255) DEFAULT NULL,
  `fage` int(11) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
