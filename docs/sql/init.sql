CREATE database if NOT EXISTS `mini-admin` default character set utf8mb4 collate utf8mb4_general_ci;
use `mini-admin`;

-- 系统用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`             BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '系统用户ID',
    `login_username` VARCHAR(32)  NOT NULL COMMENT '登录用户名',
    `real_name`      VARCHAR(32)  NOT NULL COMMENT '真实姓名',
    `tel_phone`      VARCHAR(32)  NOT NULL COMMENT '手机号',
    `sex`            TINYINT(6)   NOT NULL DEFAULT 0 COMMENT '性别 0-未知, 1-男, 2-女',
    `avatar_url`     VARCHAR(128) COMMENT '头像地址',
    `user_no`        VARCHAR(32) COMMENT '员工编号',
    `state`          TINYINT(6)   NOT NULL DEFAULT 0 COMMENT '状态 0-停用 1-启用',
    `created_at`     TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
    `updated_at`     TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`login_username`),
    UNIQUE KEY (`tel_phone`),
    UNIQUE KEY (`user_no`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100001
  CHARACTER SET = utf8mb4 COMMENT ='系统用户表'
  ROW_FORMAT = Dynamic;

-- 系统角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          varchar(32)  NOT NULL COMMENT '主键id',
    `role_name`   varchar(200) NULL DEFAULT NULL COMMENT '角色名称',
    `role_code`   varchar(100) NOT NULL COMMENT '角色编码',
    `description` varchar(255) NULL DEFAULT NULL COMMENT '描述',
    `create_by`   varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
    `create_time` datetime     NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(32)  NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime     NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uniq_sys_role_role_code` (`role_code`) USING BTREE,
    INDEX `idx_sr_role_code` (`role_code`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COMMENT = '系统角色表'
  ROW_FORMAT = Dynamic;

-- 系统用户<->角色关系表
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`      varchar(32) NOT NULL COMMENT '主键id',
    `user_id` varchar(32) NULL DEFAULT NULL COMMENT '用户id',
    `role_id` varchar(32) NULL DEFAULT NULL COMMENT '角色id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_sur_user_id` (`user_id`) USING BTREE,
    INDEX `idx_sur_role_id` (`role_id`) USING BTREE,
    INDEX `idx_sur_user_role_id` (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COMMENT = '用户角色表'
  ROW_FORMAT = Dynamic;


-- 系统权限表
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`
(
    `id`                   varchar(32)  NOT NULL COMMENT '主键id',
    `parent_id`            varchar(32)  NULL DEFAULT NULL COMMENT '父id',
    `name`                 varchar(100) NULL DEFAULT NULL COMMENT '菜单标题',
    `url`                  varchar(255) NULL DEFAULT NULL COMMENT '路径',
    `component`            varchar(255) NULL DEFAULT NULL COMMENT '组件',
    `component_name`       varchar(100) NULL DEFAULT NULL COMMENT '组件名字',
    `redirect`             varchar(255) NULL DEFAULT NULL COMMENT '一级菜单跳转地址',
    `menu_type`            int(11)      NULL DEFAULT NULL COMMENT '菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)',
    `perms`                varchar(255) NULL DEFAULT NULL COMMENT '菜单权限编码',
    `perms_type`           varchar(10)  NULL DEFAULT '0' COMMENT '权限策略1显示2禁用',
    `sort_no`              double(8, 2) NULL DEFAULT NULL COMMENT '菜单排序',
    `always_show`          tinyint(1)   NULL DEFAULT NULL COMMENT '聚合子路由: 1是0否',
    `icon`                 varchar(100) NULL DEFAULT NULL COMMENT '菜单图标',
    `is_route`             tinyint(1)   NULL DEFAULT 1 COMMENT '是否路由菜单: 0:不是  1:是（默认值1）',
    `is_leaf`              tinyint(1)   NULL DEFAULT NULL COMMENT '是否叶子节点:    1:是   0:不是',
    `keep_alive`           tinyint(1)   NULL DEFAULT NULL COMMENT '是否缓存该页面:    1:是   0:不是',
    `hidden`               tinyint(1)   NULL DEFAULT 0 COMMENT '是否隐藏路由: 0否,1是',
    `hide_tab`             tinyint(1)   NULL DEFAULT NULL COMMENT '是否隐藏tab: 0否,1是',
    `description`          varchar(255) NULL DEFAULT NULL COMMENT '描述',
    `create_by`            varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
    `create_time`          datetime     NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`            varchar(32)  NULL DEFAULT NULL COMMENT '更新人',
    `update_time`          datetime     NULL DEFAULT NULL COMMENT '更新时间',
    `del_flag`             int(1)       NULL DEFAULT 0 COMMENT '删除状态 0正常 1已删除',
    `status`               varchar(2)   NULL DEFAULT NULL COMMENT '按钮权限状态(0无效1有效)',
    `internal_or_external` tinyint(1)   NULL DEFAULT 0 COMMENT '外链菜单打开方式 0/内部打开 1/外部打开',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_sp_parent_id` (`parent_id`) USING BTREE,
    INDEX `idx_sp_is_route` (`is_route`) USING BTREE,
    INDEX `idx_sp_is_leaf` (`is_leaf`) USING BTREE,
    INDEX `idx_sp_sort_no` (`sort_no`) USING BTREE,
    INDEX `idx_sp_del_flag` (`del_flag`) USING BTREE,
    INDEX `idx_sp_menu_type` (`menu_type`) USING BTREE,
    INDEX `idx_sp_hidden` (`hidden`) USING BTREE,
    INDEX `idx_sp_status` (`status`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COMMENT = '菜单权限表'
  ROW_FORMAT = Dynamic;

-- 系统角色<->权限关系表
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    `id`            varchar(32)   NOT NULL,
    `role_id`       varchar(32)   NULL DEFAULT NULL COMMENT '角色id',
    `permission_id` varchar(32)   NULL DEFAULT NULL COMMENT '权限id',
    `data_rule_ids` varchar(1000) NULL DEFAULT NULL COMMENT '数据权限ids',
    `operate_date`  datetime      NULL DEFAULT NULL COMMENT '操作时间',
    `operate_ip`    varchar(100)  NULL DEFAULT NULL COMMENT '操作ip',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_srp_role_per_id` (`role_id`, `permission_id`) USING BTREE,
    INDEX `idx_srp_role_id` (`role_id`) USING BTREE,
    INDEX `idx_srp_permission_id` (`permission_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4 COMMENT = '角色权限关系表'
  ROW_FORMAT = Dynamic;

-- 系统用户认证表
DROP TABLE IF EXISTS `sys_user_auth`;
CREATE TABLE `sys_user_auth`
(
    `auth_id`       BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id`       BIGINT(20)   NOT NULL COMMENT '系统用户表标识',
    `identity_type` TINYINT(6)   NOT NULL DEFAULT '0' COMMENT '登录类型  1-登录账号 2-手机号 3-邮箱  4-微信  5-QQ 6-支付宝 7-微博',
    `identifier`    VARCHAR(128) NOT NULL COMMENT '认证标识 ( 用户名 | open_id )',
    `credential`    VARCHAR(128) NOT NULL COMMENT '密码凭证',
    `salt`          VARCHAR(128) NOT NULL COMMENT 'salt',
    PRIMARY KEY (`auth_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1001
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统用户认证表';


-- 系统配置表
DROP TABLE IF EXISTS sys_config;
CREATE TABLE `t_sys_config`
(
    `config_key`   VARCHAR(50)  NOT NULL COMMENT '配置KEY',
    `config_name`  VARCHAR(50)  NOT NULL COMMENT '配置名称',
    `config_desc`  VARCHAR(200) NOT NULL COMMENT '描述信息',
    `group_key`    VARCHAR(50)  NOT NULL COMMENT '分组key',
    `group_name`   VARCHAR(50)  NOT NULL COMMENT '分组名称',
    `config_val`   TEXT         NOT NULL COMMENT '配置内容项',
    `type`         VARCHAR(20)  NOT NULL DEFAULT 'text' COMMENT '类型: text-输入框, textarea-多行文本, uploadImg-上传图片, switch-开关',
    `sort_num`     BIGINT(20)   NOT NULL DEFAULT 0 COMMENT '显示顺序',
    `updated_time` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
    PRIMARY KEY (`config_key`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统配置表'
  ROW_FORMAT = Dynamic;

-- 系统操作日志表
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
(
    `log_id`        BIGINT        NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_ip`       VARCHAR(128)  NOT NULL DEFAULT '' COMMENT '用户IP',
    `sys_type`      VARCHAR(8)    NOT NULL COMMENT '所属系统： IPAY, CRM, CALC, MDM',
    `method_name`   VARCHAR(128)  NOT NULL DEFAULT '' COMMENT '方法名',
    `method_remark` VARCHAR(128)  NOT NULL DEFAULT '' COMMENT '方法描述',
    `req_url`       VARCHAR(256)  NOT NULL DEFAULT '' COMMENT '请求地址',
    `opt_req_param` VARCHAR(2048) NOT NULL DEFAULT '' COMMENT '操作请求参数',
    `opt_res_info`  VARCHAR(2048) NOT NULL DEFAULT '' COMMENT '操作响应结果',
    `created_time`  DATETIME               DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    `duration_time` DECIMAL(15)   NULL COMMENT '持续时间，单位毫秒',
    PRIMARY KEY (`log_id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4 COMMENT = '系统操作日志表'
  ROW_FORMAT = Dynamic;

-- mysql 消息表
DROP TABLE IF EXISTS `sys_msg`;
CREATE TABLE `sys_msg`
(
    msg_id       BIGINT                             NOT NULL COMMENT '消息标识'
        PRIMARY KEY,
    msg_state    INT      DEFAULT 0                 NOT NULL COMMENT '消息状态:0等待消费 1已消费 2已死亡，默认值0',
    msg_body     VARCHAR(512)                       NOT NULL COMMENT '消息内容',
    queue_name   VARCHAR(128)                       NULL COMMENT '队列名称',
    consumer     VARCHAR(64)                        NOT NULL COMMENT '消费系统',
    consume_time DATETIME                           NULL COMMENT '消费时间',
    die_count    INT      DEFAULT 10                NOT NULL COMMENT '死亡次数条件,由使用方决定,默认为发送10次还没被消费则标记死亡，人工介入',
    die_time     DATETIME                           NULL COMMENT '死亡时间',
    send_count   INT      DEFAULT 0                 NOT NULL COMMENT '重复发送消息次数，默认为0',
    sent_time    DATETIME                           NULL COMMENT '最近发送消息时间',
    producer     VARCHAR(64)                        NOT NULL COMMENT '消息生产者',
    update_time  DATETIME DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_time  DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间'
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4 COMMENT '消息表'
  ROW_FORMAT = Dynamic;

-- 系统字典表
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`
(
    `id`          varchar(32)              NOT NULL,
    `dict_name`   varchar(100)             NOT NULL COMMENT '字典名称',
    `dict_code`   varchar(100)             NOT NULL COMMENT '字典编码',
    `description` varchar(255)             NULL DEFAULT NULL COMMENT '描述',
    `del_flag`    int(1)                   NULL DEFAULT NULL COMMENT '删除状态',
    `create_by`   varchar(32)              NULL DEFAULT NULL COMMENT '创建人',
    `create_time` datetime                 NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(32)              NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime                 NULL DEFAULT NULL COMMENT '更新时间',
    `type`        int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '字典类型0为string,1为number',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_sd_dict_code` (`dict_code`) USING BTREE
) ENGINE = INNODB
  CHARACTER SET = utf8mb4 COMMENT = '系统字典表'
  ROW_FORMAT = Dynamic;

-- 系统字典分项表
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`
(
    `id`          varchar(32)  NOT NULL,
    `dict_id`     varchar(32)  NULL DEFAULT NULL COMMENT '字典id',
    `item_text`   varchar(100) NOT NULL COMMENT '字典项文本',
    `item_value`  varchar(100) NOT NULL COMMENT '字典项值',
    `description` varchar(255) NULL DEFAULT NULL COMMENT '描述',
    `sort_order`  int(10)      NULL DEFAULT NULL COMMENT '排序',
    `status`      int(11)      NULL DEFAULT NULL COMMENT '状态（1启用 0不启用）',
    `create_by`   varchar(32)  NULL DEFAULT NULL,
    `create_time` datetime     NULL DEFAULT NULL,
    `update_by`   varchar(32)  NULL DEFAULT NULL,
    `update_time` datetime     NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_sdi_role_dict_id` (`dict_id`) USING BTREE,
    INDEX `idx_sdi_role_sort_order` (`sort_order`) USING BTREE,
    INDEX `idx_sdi_status` (`status`) USING BTREE,
    INDEX `idx_sdi_dict_val` (`dict_id`, `item_value`) USING BTREE
) ENGINE = INNODB
  CHARACTER SET = utf8mb4 COMMENT = '系统字典分项表'
  ROW_FORMAT = Dynamic;


-- mysql 微信订单表
create table if not exists a_wx_order
(
    wx_order_id  bigint                               not null comment '微信订单标识'
        primary key,
    order_no     varchar(64)                          not null comment '商户订单编号',
    order_state  varchar(2) default '0'               null comment '订单状态，默认值00，可选值有：00未支付，01支付成功，02超时已关闭，03用户已取消，04退款中，05已退款，06退款异常',
    order_amt    decimal    default 0                 not null comment '订单金额，单位分，默认值0.00',
    order_title  varchar(128)                         not null comment '订单标题',
    order_body   varchar(1024)                        null comment '订单明细，json格式',
    cons_no      varchar(32)                          not null comment '用户编号',
    qrcode_url   varchar(512)                         null comment '订单二维码连接',
    expired_time datetime                             null comment '订单失效时间，没有值代表永不失效',
    create_time  datetime   default current_timestamp not null comment '创建时间',
    update_time  datetime   default current_timestamp not null on update current_timestamp comment '更新时间'
) engine = innodb
  default charset = utf8mb4 comment '微信订单表';