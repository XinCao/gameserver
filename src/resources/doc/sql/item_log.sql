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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item_log
-- ----------------------------