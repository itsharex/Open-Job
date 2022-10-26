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

 Date: 26/10/2022 22:48:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for open_job
-- ----------------------------
DROP TABLE IF EXISTS `open_job`;
CREATE TABLE `open_job`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_id` bigint(20) NOT NULL COMMENT '应用 id',
  `job_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `handler_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '绑定的 handler 的名字',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron 表达式',
  `params` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '任务执行状态（1 启动，0 停止）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '任务创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '任务更新时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '任务创建人',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '任务更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分布式调度系统任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of open_job
-- ----------------------------
INSERT INTO `open_job` VALUES (1, 1, '测试任务', 'job-one', '*/1 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (2, 1, '实时资讯', 'job-two', '*/2 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (3, 1, '测试任务2', 'job-three', '*/3 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (4, 1, '测试任务', 'job-one', '*/4 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (5, 1, '实时资讯', 'job-two', '*/5 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (6, 1, '测试任务2', 'job-three', '*/6 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (7, 1, '测试任务', 'job-one', '*/7 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (8, 1, '实时资讯', 'job-two', '*/8 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (9, 1, '测试任务2', 'job-three', '*/9 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (10, 1, '测试任务', 'job-one', '*/10 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (11, 1, '实时资讯', 'job-two', '*/11 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (12, 1, '测试任务2', 'job-three', '*/12 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (13, 1, '测试任务', 'job-one', '*/13 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (14, 1, '实时资讯', 'job-two', '*/14 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (15, 1, '测试任务2', 'job-three', '*/15 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (16, 1, '测试任务', 'job-one', '*/16 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (17, 1, '实时资讯', 'job-two', '*/17 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (18, 1, '测试任务2', 'job-three', '*/18 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (19, 1, '测试任务', 'job-one', '*/19 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (20, 1, '实时资讯', 'job-two', '*/20 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (21, 1, '测试任务2', 'job-three', '*/21 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (22, 1, '测试任务', 'job-one', '*/22 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (23, 1, '实时资讯', 'job-two', '*/23 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (24, 1, '测试任务2', 'job-three', '*/24 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (25, 1, '测试任务', 'job-one', '*/25 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (26, 1, '实时资讯', 'job-two', '*/26 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (27, 1, '测试任务2', 'job-three', '*/27 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (28, 1, '测试任务', 'job-one', '*/28 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (29, 1, '实时资讯', 'job-two', '*/29 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (30, 1, '测试任务2', 'job-three', '*/30 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (31, 1, '测试任务', 'job-one', '*/31 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (32, 1, '实时资讯', 'job-two', '*/32 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (33, 1, '测试任务2', 'job-three', '*/33 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (34, 1, '测试任务', 'job-one', '*/34 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (35, 1, '实时资讯', 'job-two', '*/35 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (36, 1, '测试任务2', 'job-three', '*/36 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (37, 1, '测试任务', 'job-one', '*/37 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (38, 1, '实时资讯', 'job-two', '*/38 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (39, 1, '测试任务2', 'job-three', '*/39 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (40, 1, '测试任务', 'job-one', '*/40 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (41, 1, '实时资讯', 'job-two', '*/41 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (42, 1, '测试任务2', 'job-three', '*/42 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (43, 1, '测试任务', 'job-one', '*/43 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (44, 1, '实时资讯', 'job-two', '*/44 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (45, 1, '测试任务2', 'job-three', '*/45 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (46, 1, '测试任务', 'job-one', '*/46 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (48, 1, '测试任务2', 'job-three', '*/48 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (49, 1, '测试任务2', 'job-three', '*/49 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (50, 1, '测试任务', 'job-one', '*/50 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (51, 1, '实时资讯', 'job-two', '*/51 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (52, 1, '测试任务2', 'job-three', '*/52 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (53, 1, '测试任务', 'job-one', '*/53 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (54, 1, '实时资讯', 'job-two', '*/54 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (55, 1, '测试任务2', 'job-three', '*/55 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (56, 1, '测试任务', 'job-one', '*/56 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (57, 1, '实时资讯', 'job-two', '*/57 * * * * ? *', NULL, 1, '2022-04-17 12:25:50', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (58, 1, '测试任务2', 'job-three', '*/58 * * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);
INSERT INTO `open_job` VALUES (59, 1, '测试任务', 'job-one', '*/59 * * * * ? *', NULL, 1, '2021-09-29 16:51:32', '2021-09-29 16:51:35', 1, 1);
INSERT INTO `open_job` VALUES (60, 1, '测试任务2', 'job-three', '0 */1 * * * ? *', NULL, 1, '2022-04-17 21:45:14', NULL, 1, NULL);

-- ----------------------------
-- Table structure for open_job_app
-- ----------------------------
DROP TABLE IF EXISTS `open_job_app`;
CREATE TABLE `open_job_app`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用名称（英文） ',
  `app_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用描述',
  `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of open_job_app
-- ----------------------------
INSERT INTO `open_job_app` VALUES (1, 'open-job-services', '测试应用1', '1', '2022-06-11 08:27:35');

-- ----------------------------
-- Table structure for open_job_log
-- ----------------------------
DROP TABLE IF EXISTS `open_job_log`;
CREATE TABLE `open_job_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `job_id` bigint(20) NOT NULL COMMENT '任务 id',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '任务状态',
  `cause` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '失败原因',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 985 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of open_job_log
-- ----------------------------
INSERT INTO `open_job_log` VALUES (1, 1, 1, NULL, '2022-10-26 22:47:25');
INSERT INTO `open_job_log` VALUES (2, 5, 1, NULL, '2022-10-26 22:47:25');
INSERT INTO `open_job_log` VALUES (3, 25, 1, NULL, '2022-10-26 22:47:25');
INSERT INTO `open_job_log` VALUES (4, 1, 1, NULL, '2022-10-26 22:47:26');
INSERT INTO `open_job_log` VALUES (5, 2, 1, NULL, '2022-10-26 22:47:26');
INSERT INTO `open_job_log` VALUES (6, 13, 1, NULL, '2022-10-26 22:47:26');
INSERT INTO `open_job_log` VALUES (7, 26, 1, NULL, '2022-10-26 22:47:26');
INSERT INTO `open_job_log` VALUES (8, 1, 1, NULL, '2022-10-26 22:47:27');
INSERT INTO `open_job_log` VALUES (9, 3, 1, NULL, '2022-10-26 22:47:27');
INSERT INTO `open_job_log` VALUES (10, 9, 1, NULL, '2022-10-26 22:47:27');
INSERT INTO `open_job_log` VALUES (11, 27, 1, NULL, '2022-10-26 22:47:27');
INSERT INTO `open_job_log` VALUES (12, 1, 1, NULL, '2022-10-26 22:47:28');
INSERT INTO `open_job_log` VALUES (13, 2, 1, NULL, '2022-10-26 22:47:28');
INSERT INTO `open_job_log` VALUES (14, 4, 1, NULL, '2022-10-26 22:47:28');
INSERT INTO `open_job_log` VALUES (15, 7, 1, NULL, '2022-10-26 22:47:28');
INSERT INTO `open_job_log` VALUES (16, 14, 1, NULL, '2022-10-26 22:47:28');
INSERT INTO `open_job_log` VALUES (17, 28, 1, NULL, '2022-10-26 22:47:28');
INSERT INTO `open_job_log` VALUES (18, 1, 1, NULL, '2022-10-26 22:47:29');
INSERT INTO `open_job_log` VALUES (19, 29, 1, NULL, '2022-10-26 22:47:29');
INSERT INTO `open_job_log` VALUES (20, 1, 1, NULL, '2022-10-26 22:47:30');
INSERT INTO `open_job_log` VALUES (21, 2, 1, NULL, '2022-10-26 22:47:30');
INSERT INTO `open_job_log` VALUES (22, 3, 1, NULL, '2022-10-26 22:47:30');
INSERT INTO `open_job_log` VALUES (23, 5, 1, NULL, '2022-10-26 22:47:30');
INSERT INTO `open_job_log` VALUES (24, 6, 1, NULL, '2022-10-26 22:47:30');
INSERT INTO `open_job_log` VALUES (25, 10, 1, NULL, '2022-10-26 22:47:30');
INSERT INTO `open_job_log` VALUES (26, 15, 1, NULL, '2022-10-26 22:47:30');
INSERT INTO `open_job_log` VALUES (27, 30, 1, NULL, '2022-10-26 22:47:30');
INSERT INTO `open_job_log` VALUES (28, 1, 1, NULL, '2022-10-26 22:47:31');
INSERT INTO `open_job_log` VALUES (29, 31, 1, NULL, '2022-10-26 22:47:31');
INSERT INTO `open_job_log` VALUES (30, 1, 1, NULL, '2022-10-26 22:47:32');
INSERT INTO `open_job_log` VALUES (31, 2, 1, NULL, '2022-10-26 22:47:32');
INSERT INTO `open_job_log` VALUES (32, 4, 1, NULL, '2022-10-26 22:47:32');
INSERT INTO `open_job_log` VALUES (33, 8, 1, NULL, '2022-10-26 22:47:32');
INSERT INTO `open_job_log` VALUES (34, 16, 1, NULL, '2022-10-26 22:47:32');
INSERT INTO `open_job_log` VALUES (35, 32, 1, NULL, '2022-10-26 22:47:32');
INSERT INTO `open_job_log` VALUES (36, 1, 1, NULL, '2022-10-26 22:47:33');
INSERT INTO `open_job_log` VALUES (37, 3, 1, NULL, '2022-10-26 22:47:33');
INSERT INTO `open_job_log` VALUES (38, 11, 1, NULL, '2022-10-26 22:47:33');
INSERT INTO `open_job_log` VALUES (39, 33, 1, NULL, '2022-10-26 22:47:33');
INSERT INTO `open_job_log` VALUES (40, 1, 1, NULL, '2022-10-26 22:47:34');
INSERT INTO `open_job_log` VALUES (41, 2, 1, NULL, '2022-10-26 22:47:34');
INSERT INTO `open_job_log` VALUES (42, 17, 1, NULL, '2022-10-26 22:47:34');
INSERT INTO `open_job_log` VALUES (43, 34, 1, NULL, '2022-10-26 22:47:34');
INSERT INTO `open_job_log` VALUES (44, 1, 1, NULL, '2022-10-26 22:47:35');
INSERT INTO `open_job_log` VALUES (45, 5, 1, NULL, '2022-10-26 22:47:35');
INSERT INTO `open_job_log` VALUES (46, 7, 1, NULL, '2022-10-26 22:47:35');
INSERT INTO `open_job_log` VALUES (47, 35, 1, NULL, '2022-10-26 22:47:35');
INSERT INTO `open_job_log` VALUES (48, 1, 1, NULL, '2022-10-26 22:47:36');
INSERT INTO `open_job_log` VALUES (49, 2, 1, NULL, '2022-10-26 22:47:36');
INSERT INTO `open_job_log` VALUES (50, 3, 1, NULL, '2022-10-26 22:47:36');
INSERT INTO `open_job_log` VALUES (51, 4, 1, NULL, '2022-10-26 22:47:36');
INSERT INTO `open_job_log` VALUES (52, 6, 1, NULL, '2022-10-26 22:47:36');
INSERT INTO `open_job_log` VALUES (53, 9, 1, NULL, '2022-10-26 22:47:36');
INSERT INTO `open_job_log` VALUES (54, 12, 1, NULL, '2022-10-26 22:47:36');
INSERT INTO `open_job_log` VALUES (55, 18, 1, NULL, '2022-10-26 22:47:36');
INSERT INTO `open_job_log` VALUES (56, 36, 1, NULL, '2022-10-26 22:47:36');
INSERT INTO `open_job_log` VALUES (57, 1, 1, NULL, '2022-10-26 22:47:37');
INSERT INTO `open_job_log` VALUES (58, 37, 1, NULL, '2022-10-26 22:47:37');
INSERT INTO `open_job_log` VALUES (59, 1, 1, NULL, '2022-10-26 22:47:38');
INSERT INTO `open_job_log` VALUES (60, 2, 1, NULL, '2022-10-26 22:47:38');
INSERT INTO `open_job_log` VALUES (61, 19, 1, NULL, '2022-10-26 22:47:38');
INSERT INTO `open_job_log` VALUES (62, 38, 1, NULL, '2022-10-26 22:47:38');
INSERT INTO `open_job_log` VALUES (63, 1, 1, NULL, '2022-10-26 22:47:39');
INSERT INTO `open_job_log` VALUES (64, 3, 1, NULL, '2022-10-26 22:47:39');
INSERT INTO `open_job_log` VALUES (65, 13, 1, NULL, '2022-10-26 22:47:39');
INSERT INTO `open_job_log` VALUES (66, 39, 1, NULL, '2022-10-26 22:47:39');
INSERT INTO `open_job_log` VALUES (67, 1, 1, NULL, '2022-10-26 22:47:40');
INSERT INTO `open_job_log` VALUES (68, 2, 1, NULL, '2022-10-26 22:47:40');
INSERT INTO `open_job_log` VALUES (69, 4, 1, NULL, '2022-10-26 22:47:40');
INSERT INTO `open_job_log` VALUES (70, 5, 1, NULL, '2022-10-26 22:47:40');
INSERT INTO `open_job_log` VALUES (71, 8, 1, NULL, '2022-10-26 22:47:40');
INSERT INTO `open_job_log` VALUES (72, 10, 1, NULL, '2022-10-26 22:47:40');
INSERT INTO `open_job_log` VALUES (73, 20, 1, NULL, '2022-10-26 22:47:40');
INSERT INTO `open_job_log` VALUES (74, 40, 1, NULL, '2022-10-26 22:47:40');
INSERT INTO `open_job_log` VALUES (75, 1, 1, NULL, '2022-10-26 22:47:41');
INSERT INTO `open_job_log` VALUES (76, 41, 1, NULL, '2022-10-26 22:47:41');
INSERT INTO `open_job_log` VALUES (77, 1, 1, NULL, '2022-10-26 22:47:42');
INSERT INTO `open_job_log` VALUES (78, 2, 1, NULL, '2022-10-26 22:47:42');
INSERT INTO `open_job_log` VALUES (79, 3, 1, NULL, '2022-10-26 22:47:42');
INSERT INTO `open_job_log` VALUES (80, 6, 1, NULL, '2022-10-26 22:47:42');
INSERT INTO `open_job_log` VALUES (81, 7, 1, NULL, '2022-10-26 22:47:42');
INSERT INTO `open_job_log` VALUES (82, 14, 1, NULL, '2022-10-26 22:47:42');
INSERT INTO `open_job_log` VALUES (83, 21, 1, NULL, '2022-10-26 22:47:42');
INSERT INTO `open_job_log` VALUES (84, 42, 1, NULL, '2022-10-26 22:47:42');
INSERT INTO `open_job_log` VALUES (85, 1, 1, NULL, '2022-10-26 22:47:43');
INSERT INTO `open_job_log` VALUES (86, 43, 1, NULL, '2022-10-26 22:47:43');
INSERT INTO `open_job_log` VALUES (87, 1, 1, NULL, '2022-10-26 22:47:44');
INSERT INTO `open_job_log` VALUES (88, 2, 1, NULL, '2022-10-26 22:47:44');
INSERT INTO `open_job_log` VALUES (89, 4, 1, NULL, '2022-10-26 22:47:44');
INSERT INTO `open_job_log` VALUES (90, 11, 1, NULL, '2022-10-26 22:47:44');
INSERT INTO `open_job_log` VALUES (91, 22, 1, NULL, '2022-10-26 22:47:44');
INSERT INTO `open_job_log` VALUES (92, 44, 1, NULL, '2022-10-26 22:47:44');
INSERT INTO `open_job_log` VALUES (93, 1, 1, NULL, '2022-10-26 22:47:45');
INSERT INTO `open_job_log` VALUES (94, 3, 1, NULL, '2022-10-26 22:47:45');
INSERT INTO `open_job_log` VALUES (95, 5, 1, NULL, '2022-10-26 22:47:45');
INSERT INTO `open_job_log` VALUES (96, 9, 1, NULL, '2022-10-26 22:47:45');
INSERT INTO `open_job_log` VALUES (97, 15, 1, NULL, '2022-10-26 22:47:45');
INSERT INTO `open_job_log` VALUES (98, 45, 1, NULL, '2022-10-26 22:47:45');
INSERT INTO `open_job_log` VALUES (99, 1, 1, NULL, '2022-10-26 22:47:46');
INSERT INTO `open_job_log` VALUES (100, 2, 1, NULL, '2022-10-26 22:47:46');
INSERT INTO `open_job_log` VALUES (101, 23, 1, NULL, '2022-10-26 22:47:46');
INSERT INTO `open_job_log` VALUES (102, 46, 1, NULL, '2022-10-26 22:47:46');
INSERT INTO `open_job_log` VALUES (103, 1, 1, NULL, '2022-10-26 22:47:47');
INSERT INTO `open_job_log` VALUES (104, 1, 1, NULL, '2022-10-26 22:47:48');
INSERT INTO `open_job_log` VALUES (105, 2, 1, NULL, '2022-10-26 22:47:48');
INSERT INTO `open_job_log` VALUES (106, 3, 1, NULL, '2022-10-26 22:47:48');
INSERT INTO `open_job_log` VALUES (107, 4, 1, NULL, '2022-10-26 22:47:48');
INSERT INTO `open_job_log` VALUES (108, 6, 1, NULL, '2022-10-26 22:47:48');
INSERT INTO `open_job_log` VALUES (109, 8, 1, NULL, '2022-10-26 22:47:48');
INSERT INTO `open_job_log` VALUES (110, 12, 1, NULL, '2022-10-26 22:47:48');
INSERT INTO `open_job_log` VALUES (111, 16, 1, NULL, '2022-10-26 22:47:48');
INSERT INTO `open_job_log` VALUES (112, 24, 1, NULL, '2022-10-26 22:47:48');
INSERT INTO `open_job_log` VALUES (113, 48, 1, NULL, '2022-10-26 22:47:48');
INSERT INTO `open_job_log` VALUES (114, 1, 1, NULL, '2022-10-26 22:47:49');
INSERT INTO `open_job_log` VALUES (115, 7, 1, NULL, '2022-10-26 22:47:49');
INSERT INTO `open_job_log` VALUES (116, 49, 1, NULL, '2022-10-26 22:47:49');

-- ----------------------------
-- Table structure for open_job_report
-- ----------------------------
DROP TABLE IF EXISTS `open_job_report`;
CREATE TABLE `open_job_report`  (
  `id` bigint(20) NOT NULL,
  `task_exec_total_count` bigint(20) NULL DEFAULT NULL,
  `task_exec_success_count` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of open_job_report
-- ----------------------------
INSERT INTO `open_job_report` VALUES (1, 1000, 980, '2022-04-10 12:54:15');
INSERT INTO `open_job_report` VALUES (2, 1200, 1100, '2022-04-11 12:55:14');
INSERT INTO `open_job_report` VALUES (3, 1300, 1200, '2022-04-12 12:55:55');
INSERT INTO `open_job_report` VALUES (4, 13500, 13000, '2022-04-13 14:02:39');

-- ----------------------------
-- Table structure for open_job_user
-- ----------------------------
DROP TABLE IF EXISTS `open_job_user`;
CREATE TABLE `open_job_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '用户状态（0：正常，1锁定）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分布式调度系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of open_job_user
-- ----------------------------
INSERT INTO `open_job_user` VALUES (1, 'lijunping', '$2a$10$3oNlO/vvXV3FPsmimv0x3ePTcwpe/E1xl86TDC0iLKwukWkJoRIyK', '18242076871', 0, '2021-06-24 15:53:16', '2021-06-24 15:53:19');
INSERT INTO `open_job_user` VALUES (2, 'pengguifang', '$2a$10$3oNlO/vvXV3FPsmimv0x3ePTcwpe/E1xl86TDC0iLKwukWkJoRIyK', '18322520810', 0, '2021-11-30 21:53:37', '2021-11-30 21:53:40');

SET FOREIGN_KEY_CHECKS = 1;
