CREATE DATABASE apicloud DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use apicloud;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户 id',
    `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
    `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
    `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户头像',
    `account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
    `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户密码 (加密后)',
    `status` int(11) NOT NULL DEFAULT 0 COMMENT '使用状态 0.正常 1拉黑',
    `role` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户角色: user / admin',
    `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uniq_phone`(`phone`) USING BTREE,
    UNIQUE INDEX `uniq_nick_name`(`nick_name`) USING BTREE,
    UNIQUE INDEX `uniq_account`(`account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;
-- 插入一个管理员
INSERT INTO `user` VALUES (1, '18888888888', 'admin', 'https://img.yzcdn.cn/vant/cat.jpeg', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 0, 'admin', '2021-01-01 00:00:00.000', '2021-01-01 00:00:00.000');
-- 插入一个普通用户
INSERT INTO `user` VALUES (2, '18888888889', 'user', 'https://img.yzcdn.cn/vant/cat.jpeg', 'user', 'e10adc3949ba59abbe56e057f20f883e', 0, 'user', '2021-01-01 00:00:00.000', '2021-01-01 00:00:00.000');

-- ----------------------------
-- Table structure for api_interface
-- ----------------------------
DROP TABLE IF EXISTS `api_interface`;
CREATE TABLE `api_interface`  (
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '接口 id',
    `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接口名称',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '接口描述',
    `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接口地址',
    `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求方式',
    `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求参数',
    `request_header` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '请求头',
    `response_header` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '响应头',
    `status` int(11) NOT NULL DEFAULT 0 COMMENT '使用状态 0关闭 1开启',
    `total_req_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '总请求次数',
    `creator_id` bigint(20) UNSIGNED NOT NULL COMMENT '创建人 id',
    `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uniq_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;





