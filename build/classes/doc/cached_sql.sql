/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50612
Source Host           : localhost:3306
Source Database       : gameserver

Target Server Type    : MYSQL
Target Server Version : 50612
File Encoding         : 65001

Date: 2013-11-18 18:17:07
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
