-- 优惠券
DROP TABLE IF EXISTS `a_coupon`;
CREATE TABLE `a_coupon` (
  `coupon_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `coupon_name` varchar(100) COMMENT '优惠券名称',
  `coupon_type` tinyint COMMENT '优惠券类型，0：满减，如满100减5块， 1：折扣券',
  `discount_sill` decimal(8, 2) COMMENT '满减基数',
  `discount` decimal(4, 2) COMMENT '打折， 92折扣为0.92',
  `discount_amount` decimal(8, 2) COMMENT '满减或立减金额',
  `audit_status` tinyint default 0 COMMENT '审核状态，0待审核，1审核通过，2审核未通过',
  `start_time` timestamp null COMMENT '有效期，起始时间',
  `end_time` timestamp default now() COMMENT '有效期，截止时间',
  `create_by` varchar(30) COMMENT '创建人',
  `create_time` timestamp default now() COMMENT '创建时间',
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

-- 优惠券明细，对应到每个人
DROP TABLE IF EXISTS `a_coupon_detail`;
CREATE TABLE `a_coupon_detail` (
  `coupon_detail_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `coupon_id` BIGINT COMMENT '优惠券ID',
  `coupon_code` varchar(20) COMMENT '优惠券码，唯一',
  `user_id` BIGINT COMMENT '用户ID',
  `status` tinyint default 0 COMMENT '支付状态，0未使用，1已使用',
  `send_time` timestamp default now() COMMENT '发券时间',
  `used_time` timestamp null COMMENT '发券时间',
  PRIMARY KEY (`coupon_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create unique index `a_coupon_detail_coupon_code` on `a_coupon_detail`(`coupon_code`);
create index `a_coupon_detail_user_id` on `a_coupon_detail`(`user_id`);
create index `a_coupon_detail_send_time` on `a_coupon_detail`(`send_time`);

-- 优惠券使用明细
DROP TABLE IF EXISTS `a_coupon_usage`;
CREATE TABLE `a_coupon_usage` (
  `coupon_usage_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `coupon_code` varchar(20) COMMENT '优惠券码，唯一',
  `user_id` BIGINT COMMENT '用户ID',
  `order_id` BIGINT COMMENT '订单ID',
  `discount_amount` decimal(8, 2) COMMENT '抵扣金额',
  `use_time` timestamp default now() COMMENT '用券时间',
  PRIMARY KEY (`coupon_usage_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create index `a_coupon_usage_coupon_code` on `a_coupon_usage`(`coupon_code`);
create index `a_coupon_usage_user_id` on `a_coupon_usage`(`user_id`);
create index `a_coupon_usage_order_id` on `a_coupon_usage`(`order_id`);

-- 模板消息
DROP TABLE IF EXISTS `a_msg_template`;
CREATE TABLE `a_msg_template` (
  `template_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `template_name` varchar(20) COMMENT '模板名称',
  `content` varchar(500) COMMENT '模板消息内容',
  `template_type` tinyint COMMENT '模板消息类型 0 短信， 1、微信， 2 app push, 3、邮件',
  `placeholders` varchar(100) default 0 COMMENT '模板消息中占位符，占位符会在发送的时候用对应的参数替换',
  `create_time` timestamp default now() COMMENT '用券时间',
  `update_time` timestamp null COMMENT '更新时间',
  PRIMARY KEY (`template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;


-- 消息发送记录
DROP TABLE IF EXISTS `a_msg_send_log`;
CREATE TABLE `a_msg_send_log` (
  `send_log_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `send_user_id` BIGINT COMMENT '发送用户ID',
  `receipt_code` varchar(32) COMMENT '发送回执编号，发送方传入',
  `receiver` varchar(50) COMMENT '接收方，短信为手机号，邮件为邮箱等',
  `tmplate_id` varchar(500) COMMENT '模板消息内容',
  `placeholders` varchar(100) default 0 COMMENT '模板消息中占位符，占位符会在发送的时候用对应的参数替换【冗余】',
  `args` varchar(200) COMMENT 'json格式， 模板消息中的参数值',
  `content` varchar(300) COMMENT '发送内容',
  `msg_type` tinyint COMMENT '消息类型 0 短信， 1、微信， 2 app push, 3、邮件',
  `send_time` timestamp default now() COMMENT '发送时间',
  `send_status` tinyint COMMENT '发送状态 0 失败， 1、成功',
  PRIMARY KEY (`send_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create index `idx_a_msg_send_log_send_user_id` on `a_msg_send_log`(`send_user_id`);
create index `idx_a_msg_send_log_receipt_code` on `a_msg_send_log`(`receipt_code`);
create index `idx_a_msg_send_log_send_time` on `a_msg_send_log`(`send_time`);


-- 活动
DROP TABLE IF EXISTS `a_activity`;
CREATE TABLE `a_activity` (
  `activity_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `activity_name` varchar(100) COMMENT '活动名称',
  `audience_id` BIGINT COMMENT '人群ID',
  `create_user_id` BIGINT COMMENT '创建人ID',
  `create_user_name` varchar(30) COMMENT '创建人',
  `approve_user_id` BIGINT COMMENT '审批人ID',
  `approve_user_name` varchar(30) COMMENT '审批人',
  `msg_template_id` int COMMENT '模板消息ID',
  `coupon_id` BIGINT COMMENT '优惠券ID',
  `coupon_name` BIGINT COMMENT '优惠券名称',
  `template_name` varchar(30) COMMENT '模板消息名称',
  `template_type` tinyint COMMENT '模板消息类型 0 短信， 1、微信， 2 app push, 3、邮件',
  `approve_status` tinyint default 0 COMMENT '审批状态 0 未审核， 1 通过， 2 不通过',
  `approve_comment` varchar(100) COMMENT '审批备注',
  `start_time` timestamp null COMMENT '活动有效期，起始时间',
  `end_time` timestamp null COMMENT '活动有效期，截止时间',
  `create_time` timestamp default now() COMMENT '创建时间',
  `approve_time` timestamp null COMMENT '审核时间',
  `is_deleted` tinyint default 0 COMMENT '是否删除， 0=未删除，1=已删除',
  PRIMARY KEY (`activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;