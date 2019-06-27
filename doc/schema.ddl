--用户表
DROP TABLE IF EXISTS `a_user`;
CREATE TABLE `a_user` (
  `user_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `user_name` VARCHAR(100) NOT NULL  COMMENT '用户名',
  `password` VARCHAR(255) COMMENT '密码',
  `real_name` VARCHAR(100) COMMENT '真实姓名',
  `gender` char(1) COMMENT 'M/F',
  `birthday` date COMMENT '生日',
  `phone` VARCHAR(20) COMMENT '电话',
  `email` VARCHAR(100) COMMENT '邮箱',
  `open_id` VARCHAR(50) COMMENT '微信openid',
  `union_id` VARCHAR(50) COMMENT 'union id',
  `card_id` VARCHAR(50) COMMENT 'card id, 身份证号',
  `member_level` tinyint default 0 COMMENT '会员等级，0:普通会员，1:白银会员，2:黄金会员，3:白金会员，4:钻石会员',
  `growth_value` int default 0 COMMENT '会员成长值',
  `profession` VARCHAR(100) COMMENT '职业',
  `hobby` VARCHAR(100) COMMENT '爱好',
  `county` VARCHAR(20) COMMENT '区县',
  `city` VARCHAR(20) COMMENT '城市',
  `provice` VARCHAR(20) COMMENT '省份',
  `create_time` timestamp default now() COMMENT '创建时间',
  `update_time` timestamp COMMENT '修改时间',
  `is_deleted` tinyint default 0 COMMENT '是否删除， 0=未删除，1=已删除',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create unique index `idx_a_user_user_name` on `a_user`(`user_name`);
create unique index `idx_a_user_phone` on `a_user`(`phone`);
create unique index `idx_a_user_email` on `a_user`(`email`);
create unique index `idx_a_user_open_id` on `a_user`(`open_id`);
create unique index `idx_a_user_union_id` on `a_user`(`union_id`);


--登录明细
DROP TABLE IF EXISTS `a_user_login_detail`;
CREATE TABLE `a_user_login_detail` (
  `user_login_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `user_id` BIGINT COMMENT '用户ID',
  `login_status` int default 0 COMMENT '0：登录成功，1：密码错误，2：验证码错误',
  `login_platform` int default 0 COMMENT '0：app登录，1：pc登录，2：h5登录，3：小程序登录，4：微信公众号登录',
  `ip_address` varchar(50) COMMENT '登录设备IP地址',
  `divice_id` varchar(50) COMMENT '登录ID',
  `browser` varchar(50) COMMENT '浏览器名称',
  `login_time` timestamp default now() COMMENT '用券时间',
  PRIMARY KEY (`user_login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create index `a_user_login_detail_user_id` on `a_user_login_detail`(`user_id`);

--产品
DROP TABLE IF EXISTS `a_product`;
CREATE TABLE `a_product` (
  `product_id` int AUTO_INCREMENT COMMENT 'key',
  `product_name` VARCHAR(100) NOT NULL  COMMENT '产品名称',
  `store_id` int COMMENT '门店ID，存储入驻商家店铺，入驻商家订单需拆单',
  `sell_price` decimal(8, 2) COMMENT '销售价',
  `marketing_price` decimal(8, 2) COMMENT '市场价',
  `category_code` varchar(20) COMMENT '产品品类',
  `status` tinyint default 0 COMMENT '状态，0：待审核， 1：审核未通过， 2：审核通过， 3：已上架， 4：已下架',
  `comment` VARCHAR(100) COMMENT '备注',
  `create_time` timestamp default now() COMMENT '创建时间',
  `update_time` timestamp COMMENT '修改时间',
  `is_deleted` tinyint default 0 COMMENT '是否删除， 0=未删除，1=已删除',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;


--产品审核
DROP TABLE IF EXISTS `a_product_audit`;
CREATE TABLE `a_product_audit` (
  `product_audit_id` int AUTO_INCREMENT COMMENT 'key',
  `product_id` int NOT NULL  COMMENT '产品ID',
  `audit_result` tinyint COMMENT '审核结果',
  `audit_persion` VARCHAR(100)  COMMENT '审核人',
  `position` VARCHAR(100)  COMMENT '审核人职位',
  `coment` VARCHAR(100)  COMMENT '备注',
  `audit_time` timestamp default now() COMMENT '审核时间',
  PRIMARY KEY (`product_audit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create index `idx_product_audit_product_id` on `a_product_audit`(`product_id`);


--产品下架记录
DROP TABLE IF EXISTS `a_product_soldout`;
CREATE TABLE `a_product_soldout` (
  `product_soldout_id` int AUTO_INCREMENT COMMENT 'key',
  `product_id` int NOT NULL  COMMENT '产品ID',
  `soldout_persion` VARCHAR(100)  COMMENT '下架人',
  `position` VARCHAR(100)  COMMENT '下架人人职位',
  `coment` VARCHAR(100)  COMMENT '备注',
  `soldout_time` timestamp default now() COMMENT '下架时间',
  PRIMARY KEY (`product_soldout_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create index `idx_product_soldout_id_product_id` on `a_product_soldout`(`product_soldout_id`);



--产品品类表
DROP TABLE IF EXISTS `a_product_category`;
CREATE TABLE `a_product_category` (
  `category_id` int AUTO_INCREMENT COMMENT 'key',
  `category_name` VARCHAR(100) NOT NULL  COMMENT '产品品类名称',
  `category_code` varchar(10) COMMENT '产品品类编码',
  `parent_category_code` varchar(10) COMMENT '产品品类父编码',
  `category_level` tinyint COMMENT '产品品类级别，1级，2级，3级',
  `create_time` timestamp default now() COMMENT '创建时间',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

--购物车
DROP TABLE IF EXISTS `a_ecart`;
CREATE TABLE `a_ecart` (
  `cart_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `product_id` int COMMENT '产品ID',
  `user_id` BIGINT COMMENT '所属用户ID',
  `store_id` int COMMENT '门店ID',
  `discount_amount` decimal(8, 2) COMMENT '折扣金额',
  `due_amount` decimal(8, 2) COMMENT '应付金额，销售-折扣',
  `actual_pay_amount` decimal(8, 2) COMMENT '实付金额',
  `contact_name` VARCHAR(100) COMMENT '联系人姓名',
  `contact_mobile` VARCHAR(100) COMMENT '联系人手机号',
  `order_platform` int default 0 COMMENT '下单平台， 0：线下门店，1：pc，2：app，3：微信，4：小程序，5：公众号，6：h5，7：wap，8：分销， 9：其它',
  `is_payed` tinyint default 0 COMMENT '是否支付， 0=未支付，1=已支付',
  `status` tinyint default 0 COMMENT '订单状态， 未支付，已支付，已退款',
  `comment` VARCHAR(100) COMMENT '备注',
  `create_time` timestamp default now() COMMENT '创建时间',
  `update_time` timestamp COMMENT '修改时间',
  `is_deleted` tinyint default 0 COMMENT '是否删除， 0=未删除，1=已删除',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create index `a_order_create_time` on `a_order`(`create_time`);
create index `a_order_update_time` on `a_order`(`update_time`);


--订单表
DROP TABLE IF EXISTS `a_order`;
CREATE TABLE `a_order` (
  `order_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `store_id` int COMMENT '门店ID，空表示线上订单',
  `user_id` BIGINT COMMENT '所属用户ID',
  `total_amount` decimal(8, 2) COMMENT '销售金额',
  `discount_amount` decimal(8, 2) COMMENT '折扣金额',
  `due_amount` decimal(8, 2) COMMENT '应付金额，销售-折扣',
  `actual_pay_amount` decimal(8, 2) COMMENT '实付金额',
  `contact_name` VARCHAR(100) COMMENT '联系人姓名',
  `contact_mobile` VARCHAR(100) COMMENT '联系人手机号',
  `order_platform` int default 0 COMMENT '下单平台， 0：线下门店，1：pc，2：app，3：微信，4：小程序，5：公众号，6：h5，7：wap，8：分销， 9：其它',
  `is_payed` tinyint default 0 COMMENT '是否支付， 0=未支付，1=已支付',
  `status` tinyint default 0 COMMENT '订单状态， 未支付，已支付，已退款',
  `comment` VARCHAR(100) COMMENT '备注',
  `create_time` timestamp default now() COMMENT '创建时间',
  `update_time` timestamp COMMENT '修改时间',
  `is_deleted` tinyint default 0 COMMENT '是否删除， 0=未删除，1=已删除',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create index `a_order_create_time` on `a_order`(`create_time`);
create index `a_order_update_time` on `a_order`(`update_time`);

--订单明细表
DROP TABLE IF EXISTS `a_order_detail`;
CREATE TABLE `a_order_detail` (
  `order_detail_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `order_id` BIGINT COMMENT '订单ID',
  `product_id` int COMMENT '产品ID',
  `quantity` int COMMENT '数量',
  `price` decimal(8, 2) COMMENT '单价',
  `total_amount` decimal(8, 2) COMMENT '总金额，单价*数量',
  `discount_amount` decimal(8, 2) COMMENT '折扣金额',
  `comment` VARCHAR(100) COMMENT '备注',
  `create_time` timestamp default now() COMMENT '创建时间',
  `update_time` timestamp COMMENT '修改时间',
  `is_deleted` tinyint default 0 COMMENT '是否删除， 0=未删除，1=已删除',
  PRIMARY KEY (`order_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create index `a_order_detail_order_id` on `a_order_detail`(`order_id`);
create index `a_order_detail_product_id` on `a_order_detail`(`product_id`);
create index `a_order_detail_create_time` on `a_order_detail`(`create_time`);
create index `a_order_detail_update_time` on `a_order_detail`(`update_time`);

--支付流水
DROP TABLE IF EXISTS `a_order_payment`;
CREATE TABLE `a_order_payment` (
  `order_payment_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `order_id` BIGINT COMMENT '订单ID',
  `pay_amount` decimal(8,2) COMMENT '实付金额',
  `pay_style` varchar(50) COMMENT '支付方式，微信，支付宝，银行卡',
  `pay_card_no` varchar(50) COMMENT '支付卡号',
  `pay_no` varchar(20) COMMENT '支付回执单号',
  `status` tinyint COMMENT '支付状态，0成功，1失败',
  `payment_time` timestamp default now() COMMENT '支付时间',
  PRIMARY KEY (`order_payment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create index `a_order_payment_order_id` on `a_order_payment`(`order_id`);

--退款单
DROP TABLE IF EXISTS `a_order_refund`;
CREATE TABLE `a_order_refund` (
  `order_refund_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `order_payment_id` BIGINT COMMENT '支付单号',
  `refund_amount` int COMMENT '退款金额',
  `refund_card_no` varchar(20) COMMENT '退款卡号',
  `pay_no` varchar(20) COMMENT '退款回执单号编号',
  `status` tinyint COMMENT '退款状态，0成功，1失败',
  `refund_time` timestamp default now() COMMENT '退款时间',
  PRIMARY KEY (`order_refund_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create index `a_order_refund_order_payment_id` on `a_order_refund`(`order_payment_id`);

--优惠券
DROP TABLE IF EXISTS `a_coupon`;
CREATE TABLE `a_coupon` (
  `coupon_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `coupon_name` varchar(100) COMMENT '优惠券名称',
  `coupon_type` tinyint COMMENT '优惠券类型，0：满减，如满100减5块， 1：折扣券',
  `discount_sill` decimal(8, 2) COMMENT '满减基数',
  `discount` decimal(4, 2) COMMENT '打折， 92折扣为0.92',
  `discount_amount` decimal(8, 2) COMMENT '满减或立减金额',
  `audit_status` tinyint default 0 COMMENT '审核状态，0待审核，1审核通过，2审核未通过',
  `start_time` timestamp default now() COMMENT '有效期，起始时间',
  `end_time` timestamp default now() COMMENT '有效期，截止时间',
  `create_by` varchar(30) COMMENT '创建人',
  `create_time` timestamp default now() COMMENT '创建时间',
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;


--优惠券明细，对应到每个人
DROP TABLE IF EXISTS `a_coupon_detail`;
CREATE TABLE `a_coupon_detail` (
  `coupon_detail_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `coupon_id` BIGINT COMMENT '优惠券ID',
  `coupon_code` varchar(20) COMMENT '优惠券码，唯一',
  `user_id` BIGINT COMMENT '用户ID',
  `status` tinyint default 0 COMMENT '支付状态，0未使用，1已使用',
  `send_time` timestamp default now() COMMENT '发券时间',
  `used_time` timestamp COMMENT '发券时间',
  PRIMARY KEY (`coupon_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create unique index `a_coupon_detail_coupon_code` on `a_coupon_detail`(`coupon_code`);
create index `a_coupon_detail_user_id` on `a_coupon_detail`(`user_id`);
create index `a_coupon_detail_send_time` on `a_coupon_detail`(`send_time`);

--优惠券使用明细
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

--收藏
DROP TABLE IF EXISTS `a_product_user_favorite`;
CREATE TABLE `a_product_user_favorite` (
  `product_user_favorite_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `user_id` BIGINT COMMENT '用户ID',
  `product_id` BIGINT COMMENT '产品ID',
  `product_name` varchar(100) COMMENT '产品ID',
  `action` tinyint COMMENT '0:收藏，1：取消收藏',
  `favorite_time` timestamp default now() COMMENT '收藏时间',
  `un_favorite_time` timestamp default now() COMMENT '取消收藏时间',
  PRIMARY KEY (`product_user_favorite_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create index `a_product_user_favorite_user_id` on `a_product_user_favorite`(`user_id`);
create index `a_product_user_favorite_product_id` on `a_product_user_favorite`(`product_id`);
create index `a_product_user_favorite_favorite_time` on `a_product_user_favorite`(`favorite_time`);


--行政区
DROP TABLE IF EXISTS `a_area`;
CREATE TABLE `a_area` (
  `area_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `province` varchar(50) COMMENT '省',
  `city` varchar(50) COMMENT '市',
  `contry` varchar(50) COMMENT '区/县',
  `bm` int COMMENT '行政区代码',
  `comment` varchar(50) COMMENT '备注',
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create index `a_area_bm` on `a_area`(`bm`);


--门店
DROP TABLE IF EXISTS `a_store`;
CREATE TABLE `a_store` (
  `store_id` int AUTO_INCREMENT COMMENT 'key',
  `store_name` varchar(50) COMMENT '店铺名称',
  `province` varchar(50) COMMENT '省',
  `city` varchar(50) COMMENT '市',
  `contry` varchar(50) COMMENT '区/县',
  `status` tinyint default 0 COMMENT '店铺状态。0、待营业，1、营业中，2、已关店',
  `contacts` varchar(50) COMMENT '联系人',
  `contact_number` varchar(20) COMMENT '联系电话',
  `store_type` tinyint COMMENT '店铺类型.0、官网旗舰店，1、线上实体店，2、官网入驻商铺',
  `address` varchar(50) COMMENT '地址',
  `comment` varchar(50) COMMENT '备注',
  `create_time` timestamp COMMENT '开店时间',
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create unique index `idx_a_store_store_name` on `a_store`(`store_name`);

--店铺审核
DROP TABLE IF EXISTS `a_store_audit`;
CREATE TABLE `a_store_audit` (
  `store_audit_id` int AUTO_INCREMENT COMMENT 'key',
  `store_id` int COMMENT '店铺名称',
  `audit_result` tinyint COMMENT '审核结果',
  `audit_persion` VARCHAR(100)  COMMENT '审核人',
  `position` VARCHAR(100)  COMMENT '审核人职位',
  `comment` varchar(50) COMMENT '备注',
  `audit_time` timestamp COMMENT '审核时间',
  PRIMARY KEY (`store_audit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
create unique index `idx_a_store_audit_store_id` on `a_store_audit`(`store_id`);

--商品品类
DROP TABLE IF EXISTS `a_product_category`;
CREATE TABLE `a_product_category` (
  `category_id` BIGINT AUTO_INCREMENT COMMENT 'key',
  `category_code` varchar(50) COMMENT '品类编码',
  `parent_category_code` varchar(50) COMMENT '父品类编码',
  `category_name` varchar(50) COMMENT '品类名称',
  `category_level` varchar(50) COMMENT '品类级别',
  `comment` varchar(50) COMMENT '备注',
  `create_time` timestamp COMMENT '创建时间',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;