-- 系统配置表
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config` (
    `config_key` VARCHAR(50) NOT NULL COMMENT '配置KEY',
    `config_name` VARCHAR(50) NOT NULL COMMENT '配置名称',
    `config_desc` VARCHAR(200) NOT NULL COMMENT '描述信息',
    `group_key` VARCHAR(50) NOT NULL COMMENT '分组key',
    `group_name` VARCHAR(50) NOT NULL COMMENT '分组名称',
    `config_val` TEXT NOT NULL COMMENT '配置内容项',
    `type` VARCHAR(20) NOT NULL DEFAULT 'text' COMMENT '类型: text-输入框, textarea-多行文本, uploadImg-上传图片, switch-开关',
    `sort_num` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '显示顺序',
    `updated_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
    PRIMARY KEY (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';
-- 系统操作日志表
DROP TABLE IF EXISTS `t_sys_log`;
CREATE TABLE `t_sys_log` (
     `sys_log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
     `user_id` bigint(20) DEFAULT NULL COMMENT '系统用户ID',
     `user_name` varchar(32) DEFAULT NULL COMMENT '用户姓名',
     `user_ip` varchar(128) NOT NULL DEFAULT '' COMMENT '用户IP',
     `sys_type` varchar(8) NOT NULL COMMENT '所属系统： Mini管理系统, ',
     `method_name` varchar(128) NOT NULL DEFAULT '' COMMENT '方法名',
     `method_remark` varchar(128) NOT NULL DEFAULT '' COMMENT '方法描述',
     `req_url` varchar(256) NOT NULL DEFAULT '' COMMENT '请求地址',
     `opt_req_param` varchar(2048) NOT NULL DEFAULT '' COMMENT '操作请求参数',
     `opt_res_info` varchar(2048) NOT NULL DEFAULT '' COMMENT '操作响应结果',
     `created_at` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
     PRIMARY KEY (`sys_log_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT = '系统操作日志表';