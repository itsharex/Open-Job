/*
 Navicat Premium Data Transfer

 Source Server         : fresh
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : open_job

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 27/02/2022 10:16:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for open_job
-- ----------------------------
DROP TABLE IF EXISTS `open_job`;
CREATE TABLE `open_job`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `job_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `handler_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '绑定的 handler 的名字',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron 表达式',
  `status` tinyint(4) NOT NULL COMMENT '任务执行状态（1 启动，0 停止）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '任务创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '任务更新时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '任务创建人',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '任务更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '爬虫任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of open_job
-- ----------------------------
INSERT INTO `open_job` VALUES (1, '测试任务', 'job-one', '0/5 * * * * ? *', 0, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);

-- ----------------------------
-- Table structure for open_job_log
-- ----------------------------
DROP TABLE IF EXISTS `open_job_log`;
CREATE TABLE `open_job_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `job_id` bigint(20) NULL DEFAULT NULL COMMENT '任务 id',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '任务状态',
  `cause` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '失败原因',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for open_job_user
-- ----------------------------
DROP TABLE IF EXISTS `open_job_user`;
CREATE TABLE `open_job_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '用户状态（0：锁定，1正常）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '爬虫系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of open_job_user
-- ----------------------------
INSERT INTO `open_job_user` VALUES (1, 'lijunping', '$2a$10$3oNlO/vvXV3FPsmimv0x3ePTcwpe/E1xl86TDC0iLKwukWkJoRIyK', '18242076871', 1, '2021-06-24 15:53:16', '2021-06-24 15:53:19');
INSERT INTO `open_job_user` VALUES (2, 'pengguifang', '$2a$10$3oNlO/vvXV3FPsmimv0x3ePTcwpe/E1xl86TDC0iLKwukWkJoRIyK', '18322520810', 1, '2021-11-30 21:53:37', '2021-11-30 21:53:40');

SET FOREIGN_KEY_CHECKS = 1;
