/*
 Navicat Premium Data Transfer

 Source Server         : localhost_mysql57
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : shopserver

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 17/06/2020 23:46:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attribute
-- ----------------------------
DROP TABLE IF EXISTS `attribute`;
CREATE TABLE `attribute`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `attribute` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `p_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `attribute_ibfk_1`(`p_id`) USING BTREE,
  CONSTRAINT `attribute_ibfk_1` FOREIGN KEY (`p_id`) REFERENCES `second_categories` (`ha_cat_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of attribute
-- ----------------------------
INSERT INTO `attribute` VALUES (2, '生产日期：2020年6月12号', 1);
INSERT INTO `attribute` VALUES (3, '生产厂商：兴盛优选有限公司', 1);
INSERT INTO `attribute` VALUES (4, '生产日期：2020年5月23号', 2);
INSERT INTO `attribute` VALUES (5, '生产厂商：兴盛优选有限公司', 2);
INSERT INTO `attribute` VALUES (7, '产地：广东', 3);
INSERT INTO `attribute` VALUES (8, '产地：广西', 5);

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `rights` int(255) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `path`(`path`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  CONSTRAINT `authority_ibfk_3` FOREIGN KEY (`path`) REFERENCES `second_menu` (`s_path`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `authority_ibfk_4` FOREIGN KEY (`name`) REFERENCES `second_menu` (`s_auth_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES (1, 'users', 1, '用户列表');
INSERT INTO `authority` VALUES (2, 'roles', 1, '角色列表');
INSERT INTO `authority` VALUES (3, 'authoritys', 1, '权限列表');
INSERT INTO `authority` VALUES (4, 'commodity', 2, '商品列表');
INSERT INTO `authority` VALUES (5, 'parameter', 2, '分类参数');
INSERT INTO `authority` VALUES (6, 'categories', 2, '商品分类');
INSERT INTO `authority` VALUES (7, 'orders', 2, '订单列表');

-- ----------------------------
-- Table structure for commodity
-- ----------------------------
DROP TABLE IF EXISTS `commodity`;
CREATE TABLE `commodity`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `price` int(10) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `p_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `p_id`(`p_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `price`(`price`) USING BTREE,
  CONSTRAINT `commodity_ibfk_1` FOREIGN KEY (`p_id`) REFERENCES `second_categories` (`ha_cat_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity
-- ----------------------------
INSERT INTO `commodity` VALUES (1, '水晶大苹果', 15, '2020-06-17 21:35:40', 1);
INSERT INTO `commodity` VALUES (2, '泰国芭蕉', 13, '2020-06-17 22:21:40', 2);
INSERT INTO `commodity` VALUES (5, '妃子笑荔枝', 22, '2020-06-17 22:21:58', 3);
INSERT INTO `commodity` VALUES (6, '广西红心火龙果', 21, '2020-06-17 22:22:21', 5);

-- ----------------------------
-- Table structure for first_categories
-- ----------------------------
DROP TABLE IF EXISTS `first_categories`;
CREATE TABLE `first_categories`  (
  `cat_id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `cat_pid` int(11) NULL DEFAULT NULL,
  `cat_level` int(11) NULL DEFAULT NULL,
  `cat_deleted` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0,
  PRIMARY KEY (`cat_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of first_categories
-- ----------------------------
INSERT INTO `first_categories` VALUES (1, '水果', 0, 0, 0);
INSERT INTO `first_categories` VALUES (4, '蔬菜', 0, 0, 0);
INSERT INTO `first_categories` VALUES (13, '饮料', 0, 0, 0);
INSERT INTO `first_categories` VALUES (14, '休闲食品', 0, 0, 0);
INSERT INTO `first_categories` VALUES (15, '生活必需品', 0, 0, 0);

-- ----------------------------
-- Table structure for first_menu
-- ----------------------------
DROP TABLE IF EXISTS `first_menu`;
CREATE TABLE `first_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auth_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of first_menu
-- ----------------------------
INSERT INTO `first_menu` VALUES (1, '用户管理', NULL);
INSERT INTO `first_menu` VALUES (2, '权限管理', NULL);
INSERT INTO `first_menu` VALUES (3, '商品管理', NULL);
INSERT INTO `first_menu` VALUES (4, '订单管理', NULL);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numbering` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `commodity` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `payment` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '否',
  `ship` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '否',
  `price` int(10) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `p_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `commodity`(`commodity`) USING BTREE,
  INDEX `price`(`price`) USING BTREE,
  INDEX `p_id`(`p_id`) USING BTREE,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`commodity`) REFERENCES `commodity` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`price`) REFERENCES `commodity` (`price`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`p_id`) REFERENCES `commodity` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (7, 'hOOYGgsbQ7w', '水晶大苹果', '否', '否', 15, '2020-06-17 18:59:04', 1);
INSERT INTO `orders` VALUES (8, 'raPy7mg01XNsUrp3NCSt/w==', '泰国芭蕉', '否', '否', 13, '2020-06-17 19:00:36', 2);
INSERT INTO `orders` VALUES (9, 'S51Upp8CeqvJ5paTR78frA==', '妃子笑荔枝', '是', '否', 22, '2020-06-17 22:23:17', 5);

-- ----------------------------
-- Table structure for parameter
-- ----------------------------
DROP TABLE IF EXISTS `parameter`;
CREATE TABLE `parameter`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parameter` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `p_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `parameter_ibfk_2`(`p_id`) USING BTREE,
  CONSTRAINT `parameter_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `second_categories` (`ha_cat_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of parameter
-- ----------------------------
INSERT INTO `parameter` VALUES (1, '品牌：红富士 国光 红玉', 1);
INSERT INTO `parameter` VALUES (2, '大小： 2kg 3kg 5kg', 1);
INSERT INTO `parameter` VALUES (3, '产地：山东烟台 新疆', 1);
INSERT INTO `parameter` VALUES (4, '品牌：都乐 佳农', 2);
INSERT INTO `parameter` VALUES (6, '重量：2kg 3kg 5kg', 2);
INSERT INTO `parameter` VALUES (7, '产地：泰国 越南', 2);
INSERT INTO `parameter` VALUES (19, '品牌：妃子笑', 3);
INSERT INTO `parameter` VALUES (20, '品牌： 红心火龙果', 5);

-- ----------------------------
-- Table structure for second_categories
-- ----------------------------
DROP TABLE IF EXISTS `second_categories`;
CREATE TABLE `second_categories`  (
  `ha_cat_id` int(11) NOT NULL AUTO_INCREMENT,
  `ha_cat_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ha_cat_pid` int(11) NULL DEFAULT NULL,
  `ha_cat_level` int(11) NULL DEFAULT NULL,
  `ha_cat_deleted` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`ha_cat_id`) USING BTREE,
  INDEX `ha_cat_pid`(`ha_cat_pid`) USING BTREE,
  CONSTRAINT `second_categories_ibfk_1` FOREIGN KEY (`ha_cat_pid`) REFERENCES `first_categories` (`cat_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_categories
-- ----------------------------
INSERT INTO `second_categories` VALUES (1, '苹果', 1, 1, 0);
INSERT INTO `second_categories` VALUES (2, '香蕉', 1, 1, 0);
INSERT INTO `second_categories` VALUES (3, '荔枝', 1, 1, 0);
INSERT INTO `second_categories` VALUES (4, '萝卜', 4, 1, 0);
INSERT INTO `second_categories` VALUES (5, '火龙果', 1, 1, 0);
INSERT INTO `second_categories` VALUES (12, '矿泉水', 13, 1, 0);
INSERT INTO `second_categories` VALUES (13, '饼干', 14, 1, 0);
INSERT INTO `second_categories` VALUES (14, '茄子', 4, 1, 0);
INSERT INTO `second_categories` VALUES (15, '空心菜', 4, 1, 0);
INSERT INTO `second_categories` VALUES (16, '芹菜', 4, 1, 0);
INSERT INTO `second_categories` VALUES (17, '酸奶', 13, 1, 0);
INSERT INTO `second_categories` VALUES (18, '辣条', 14, 1, 0);
INSERT INTO `second_categories` VALUES (19, '花生', 14, 1, 0);
INSERT INTO `second_categories` VALUES (20, '瓜子', 14, 1, 0);
INSERT INTO `second_categories` VALUES (21, '牙膏', 15, 1, 0);
INSERT INTO `second_categories` VALUES (22, '毛巾', 15, 1, 0);

-- ----------------------------
-- Table structure for second_menu
-- ----------------------------
DROP TABLE IF EXISTS `second_menu`;
CREATE TABLE `second_menu`  (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `s_auth_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `s_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `s_one_menu` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`s_id`, `s_auth_name`, `s_path`) USING BTREE,
  INDEX `s_one_menu`(`s_one_menu`) USING BTREE,
  INDEX `s_path`(`s_path`) USING BTREE,
  INDEX `s_authName`(`s_auth_name`) USING BTREE,
  CONSTRAINT `second_menu_ibfk_1` FOREIGN KEY (`s_one_menu`) REFERENCES `first_menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_menu
-- ----------------------------
INSERT INTO `second_menu` VALUES (1, '用户列表', 'users', 1);
INSERT INTO `second_menu` VALUES (2, '角色列表', 'roles', 2);
INSERT INTO `second_menu` VALUES (3, '权限列表', 'authoritys', 2);
INSERT INTO `second_menu` VALUES (4, '商品列表', 'commodity', 3);
INSERT INTO `second_menu` VALUES (5, '分类参数', 'parameter', 3);
INSERT INTO `second_menu` VALUES (6, '商品分类', 'categories', 3);
INSERT INTO `second_menu` VALUES (7, '订单列表', 'orders', 4);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '商家管理员',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', 'admin', '18773639417', 'zeqin@163.com', '超级管理员', '2020-06-10 12:21:05', '6jQ4zVypmZG0s7wIYqzDfkvwofqgYlW3BrxFs9LRTlTGZkrmDSg0VyBo9aC22Dg4');
INSERT INTO `users` VALUES (4, '张三', '123456', '15886660876', 'Ruoany@163.com', '商家管理员', '2020-06-10 10:04:40', 'QsRtNEMEoJxJaApFIXNBIQ==');

SET FOREIGN_KEY_CHECKS = 1;
