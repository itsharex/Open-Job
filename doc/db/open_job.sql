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

 Date: 19/01/2023 19:33:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for open_job
-- ----------------------------
DROP TABLE IF EXISTS `open_job`;
CREATE TABLE `open_job`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT ''主键'',
  `app_id` bigint(20) NOT NULL COMMENT ''应用 id'',
  `job_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''任务名称'',
  `handler_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''绑定的 handler 的名字'',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''cron 表达式'',
  `params` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''参数'',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT ''任务执行状态（1 启动，0 停止）'',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT ''任务创建时间'',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT ''任务更新时间'',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT ''任务创建人'',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT ''任务更新人'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''爬虫任务表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for open_job_app
-- ----------------------------
DROP TABLE IF EXISTS `open_job_app`;
CREATE TABLE `open_job_app`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT ''主键'',
  `app_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''应用名称（英文） '',
  `app_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''应用描述'',
  `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT ''创建时间'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for open_job_log
-- ----------------------------
DROP TABLE IF EXISTS `open_job_log`;
CREATE TABLE `open_job_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT ''主键'',
  `app_id` bigint(20) NOT NULL COMMENT ''应用 id'',
  `job_id` bigint(20) NOT NULL COMMENT ''任务 id'',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT ''任务状态'',
  `cause` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT ''失败原因'',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT ''创建时间'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1703 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for open_job_report
-- ----------------------------
DROP TABLE IF EXISTS `open_job_report`;
CREATE TABLE `open_job_report`  (
  `id` bigint(20) NOT NULL,
  `app_id` bigint(20) NOT NULL COMMENT ''应用 id'',
  `task_exec_total_count` bigint(20) NULL DEFAULT NULL COMMENT ''执行总次数'',
  `task_exec_success_count` bigint(20) NULL DEFAULT NULL COMMENT ''执行成功总次数'',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT ''创建时间'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for open_job_user
-- ----------------------------
DROP TABLE IF EXISTS `open_job_user`;
CREATE TABLE `open_job_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT ''主键'',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''用户名'',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''密码'',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''手机号'',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT ''用户状态（0：正常，1锁定）'',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT ''更新时间'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''爬虫系统用户表'' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
