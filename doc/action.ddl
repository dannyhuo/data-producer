-- 行为

DROP TABLE IF EXISTS `test_varchar_date`;
CREATE TABLE `test_varchar_date` (
  `id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `create_time` varchar(20) COMMENT '创建时间，字符串',
  `update_time` varchar(20) COMMENT '更新时间，字符串',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;