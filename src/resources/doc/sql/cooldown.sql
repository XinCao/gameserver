-- ----------------------------
-- Table structure for `cooldown`
-- ----------------------------
DROP TABLE IF EXISTS `cooldown`;
CREATE TABLE `cooldown` (
  `id` int(11) NOT NULL DEFAULT '0',
  `player_id` int(11) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `cur` int(11) DEFAULT NULL,
  `interval` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cooldown
-- ----------------------------
