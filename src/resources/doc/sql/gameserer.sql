/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50612
Source Host           : localhost:3306
Source Database       : gameserver

Target Server Type    : MYSQL
Target Server Version : 50612
File Encoding         : 65001

Date: 2014-02-19 13:51:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cached_sql`
-- ----------------------------
DROP TABLE IF EXISTS `cached_sql`;
CREATE TABLE `cached_sql` (
  `id` int(11) NOT NULL DEFAULT '0',
  `class_name` varchar(255) DEFAULT NULL,
  `flag` varchar(255) DEFAULT NULL,
  `sql` varchar(255) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `is_valied` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cached_sql
-- ----------------------------

-- ----------------------------
-- Table structure for `count`
-- ----------------------------
DROP TABLE IF EXISTS `count`;
CREATE TABLE `count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `cur` int(11) DEFAULT NULL,
  `max` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of count
-- ----------------------------

-- ----------------------------
-- Table structure for `item_log`
-- ----------------------------
DROP TABLE IF EXISTS `item_log`;
CREATE TABLE `item_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  `item_tpl_id` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `channel` int(11) DEFAULT NULL,
  `extra_data` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item_log
-- ----------------------------

-- ----------------------------
-- Table structure for `item_template`
-- ----------------------------
DROP TABLE IF EXISTS `item_template`;
CREATE TABLE `item_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `is_equip` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item_template
-- ----------------------------