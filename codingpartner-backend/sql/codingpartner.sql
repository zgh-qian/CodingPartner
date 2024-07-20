-- 创建数据库 codingparnter_db
create database if not exists codingparnter_db;
-- 切换数据库 codingparnter_db
use codingparnter_db;
-- 角色表
drop table if exists user_role;
create table if not exists user_role
(
    `id`        bigint auto_increment comment 'id' primary key comment 'id',
    `name`      varchar(256) unique not null comment '名称',
    `avatar`    varchar(512)        null comment '头像',
    `condition` varchar(512)        null comment '条件',
    `desc`      varchar(512)        null comment '描述',
    `value`     int                 not null comment '权限：越小越高，比如：0-超级管理员，1-管理员，2-用户'
) comment '角色表' collate = utf8mb4_unicode_ci;
-- 等级表
drop table if exists user_level;
create table if not exists user_level
(
    `id`        bigint auto_increment comment 'id' primary key comment 'id',
    `name`      varchar(256) unique not null comment '名称',
    `avatar`    varchar(512)        null comment '头像',
    `condition` varchar(512)        null comment '条件',
    `desc`      varchar(512)        null comment '描述',
    `value`     int                 not null comment '等级值，越小越高，比如：0-影，1-特殊上忍，2-上忍，3-下忍'
) comment '等级表' collate = utf8mb4_unicode_ci;
-- 用户表
drop table if exists user;
create table if not exists user
(
    `id`               bigint auto_increment comment 'id' primary key comment 'id',
    `username`         varchar(256) unique                    not null comment '账号',
    `password`         varchar(256)                           not null comment '密码',
    `email`            varchar(256) unique                    not null comment '邮箱',
    `nickname`         varchar(256)                           not null comment '昵称',
    `avatar`           varchar(512)                           null comment '头像',
    `gender`           tinyint      default 0                 null comment '性别：0-未知，1-男，2-女',
    `birthday`         date                                   null comment '生日',
    `phone`            varchar(128)                           null comment '手机号',
    `address`          varchar(512)                           null comment '地址',
    `education`        varchar(128)                           null comment '学历',
    `company`          varchar(128)                           null comment '公司',
    `position`         varchar(128)                           null comment '职位',
    `tags`             varchar(512)                           null comment '个人标签(json数组)',
    `website`          varchar(512)                           null comment '个人网站(json数组)',
    `introduction`     text                                   null comment '个人简介',
    `role`             varchar(256) default 'user'            not null comment '角色',
    `level`            varchar(256) default ''                not null comment '等级',
    `submission_count` int          default 0                 not null comment '提交次数',
    `accepted_count`   int          default 0                 not null comment '通过次数',
    `is_banned`        tinyint      default 0                 not null comment '是否封禁：0-未封禁，1-封禁',
    `is_private`       tinyint      default 0                 not null comment '是否私密：0-公开，1-私密',
    `is_delete`        tinyint      default 0                 not null comment '是否注销：0-未注销，1-注销',
    `create_time`      datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time`      datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_id (id)
) comment '用户表' collate = utf8mb4_unicode_ci;
-- 关注表
drop table if exists user_follow;
create table if not exists user_follow
(
    `id`           bigint auto_increment comment 'id' primary key,
    `follower_id`  bigint                             not null comment '关注者id',
    `following_id` bigint                             not null comment '被关注者id',
    `create_time`  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time`  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`    tinyint  default 0                 not null comment '是否删除',
    index idx_follower_id (follower_id),
    index idx_following_id (following_id)
) comment '关注表' collate = utf8mb4_unicode_ci;
-- 难度表
drop table if exists question_difficulty;
create table if not exists question_difficulty
(
    `id`   bigint auto_increment comment 'id' primary key,
    `name` varchar(128) not null comment '名称',
    `desc` varchar(512) null comment '描述'
) comment '难度表' collate = utf8mb4_unicode_ci;
-- 编程语言表
drop table if exists code_language;
create table if not exists code_language
(
    `id`    bigint auto_increment comment 'id' primary key,
    `name`  varchar(128) not null comment '前端展示名称',
    `value` varchar(128) not null comment '后台处理名称',
    `desc`  varchar(512) null comment '描述'
) comment '编程语言表' collate = utf8mb4_unicode_ci;
-- 题目表
drop table if exists question;
create table if not exists question
(
    `id`               bigint auto_increment comment 'id' primary key,
    `title`            varchar(512)                       not null comment '标题',
    `difficulty`       varchar(128)                       not null comment '难度',
    `tags`             varchar(1024)                      not null comment '标签(json数组)',
    `language`         varchar(256)                       not null comment '支持的编程语言(json数组)',
    `content`          text                               not null comment '内容',
    `judge_config`     text                               not null comment '判题配置(json对象)',
    `judge_case`       text                               not null comment '判题用例(json数组)',
    `answer`           text                               not null comment '答案',
    `submission_count` int      default 0                 not null comment '提交次数',
    `accepted_count`   int      default 0                 not null comment '通过次数',
    `thumb_count`      int      default 0                 not null comment '点赞数',
    `favour_count`     int      default 0                 not null comment '收藏数',
    `comment_count`    int      default 0                 not null comment '评论数',
    `user_id`          bigint                             not null comment '用户id',
    `create_time`      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time`      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`        tinyint  default 0                 not null comment '是否删除',
    index idx_id (id)
) comment '题目表' collate = utf8mb4_unicode_ci;
-- 题目提交状态表
drop table if exists question_status;
create table if not exists question_status
(
    `id`   bigint auto_increment comment 'id' primary key,
    `name` varchar(128) not null comment '状态名称',
    `desc` varchar(512) null comment '状态描述'
) comment '题目状态表' collate = utf8mb4_unicode_ci;
-- 题目执行状态表
drop table if exists question_result;
create table if not exists question_result
(
    `id`        bigint auto_increment comment 'id' primary key,
    `name_abbr` varchar(128) not null comment '执行状态简称',
    `name`      varchar(128) not null comment '执行状态名称',
    `desc`      varchar(128) not null comment '执行状态描述'
) comment '题目执行结果表' collate = utf8mb4_unicode_ci;
-- 题目提交表
drop table if exists question_submit;
create table if not exists question_submit
(
    `id`           bigint auto_increment comment 'id' primary key,
    `question_id`  bigint                             not null comment '题目id',
    `user_id`      bigint                             not null comment '用户id',
    `language`     varchar(128)                       not null comment '编程语言',
    `code`         text                               not null comment '代码',
    `judge_info`   text                               null comment '判题信息(json对象)',
    `judge_status` tinyint  default 0                 not null comment '判题状态：0-待判题，1-判题中，2-判题成功，3-判题失败',
    `is_pass`      tinyint  default 0                 not null comment '是否通过：0-未通过，1-通过',
    `create_time`  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time`  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`    tinyint  default 0                 not null comment '是否删除',
    index idx_question_id (question_id),
    index idx_user_id (user_id)
) comment '题目提交表' collate = utf8mb4_unicode_ci;
-- 题目评论表
drop table if exists question_comment;
create table if not exists question_comment
(
    `id`          bigint auto_increment comment 'id' primary key,
    `question_id` bigint                             not null comment '题目id',
    `user_id`     bigint                             not null comment '用户id',
    `comment_id`  bigint                             null comment '父评论id',
    `content`     text                               not null comment '内容',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`   tinyint  default 0                 not null comment '是否删除',
    index idx_question_id (question_id),
    index idx_user_id (user_id)
) comment '题目评论表' collate = utf8mb4_unicode_ci;
-- 题目点赞表
drop table if exists question_thumb;
create table if not exists question_thumb
(
    `id`          bigint auto_increment comment 'id' primary key,
    `question_id` bigint                             not null comment '题目id',
    `user_id`     bigint                             not null comment '用户id',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`   tinyint  default 0                 not null comment '是否删除',
    index idx_questionId (question_id),
    index idx_userId (user_id)
) comment '题目点赞表' collate = utf8mb4_unicode_ci;
-- 题目收藏表
drop table if exists question_favour;
create table if not exists question_favour
(
    `id`          bigint auto_increment comment 'id' primary key,
    `question_id` bigint                             not null comment '题目id',
    `user_id`     bigint                             not null comment '用户id',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`   tinyint  default 0                 not null comment '是否删除',
    index idx_questionId (question_id),
    index idx_userId (user_id)
) comment '题目收藏表' collate = utf8mb4_unicode_ci;
-- 题单表
drop table if exists question_list;
create table if not exists question_list
(
    `id`               bigint auto_increment comment 'id' primary key,
    `name`             varchar(512)                       not null comment '题单名称',
    `desc`             text                               not null comment '题单描述',
    `user_id`          bigint                             not null comment '用户id',
    `submission_count` int      default 0                 not null comment '提交次数',
    `accepted_count`   int      default 0                 not null comment '通过次数',
    `thumb_count`      int      default 0                 not null comment '点赞数',
    `favour_count`     int      default 0                 not null comment '收藏数',
    `comment_count`    int      default 0                 not null comment '评论数',
    `open_time`        datetime default CURRENT_TIMESTAMP not null comment '开放时间',
    `is_open`          tinyint  default 0                 not null comment '是否公开：0-公开，1-私密',
    `create_time`      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time`      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`        tinyint  default 0                 not null comment '是否删除',
    index idx_userId (user_id)
) comment '题单表' collate = utf8mb4_unicode_ci;
-- 题单题目表
drop table if exists question_list_question;
create table if not exists question_list_question
(
    `id`          bigint auto_increment comment 'id' primary key,
    `list_id`     bigint                             not null comment '题单id',
    `question_id` bigint                             not null comment '题目id',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`   tinyint  default 0                 not null comment '是否删除',
    index idx_list_id (list_id),
    index idx_question_id (question_id)
) comment '题单题目表' collate = utf8mb4_unicode_ci;
-- 题单评论表
drop table if exists question_list_comment;
create table if not exists question_list_comment
(
    `id`          bigint auto_increment comment 'id' primary key,
    `list_id`     bigint                             not null comment '题单id',
    `user_id`     bigint                             not null comment '用户id',
    `comment_id`  bigint                             null comment '父评论id',
    `content`     text                               not null comment '内容',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`   tinyint  default 0                 not null comment '是否删除',
    index idx_list_id (list_id),
    index idx_user_id (user_id)
) comment '题单评论表' collate = utf8mb4_unicode_ci;
-- 题单点赞表
drop table if exists question_list_thumb;
create table if not exists question_list_thumb
(
    `id`          bigint auto_increment comment 'id' primary key,
    `list_id`     bigint                             not null comment '题单id',
    `user_id`     bigint                             not null comment '用户id',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`   tinyint  default 0                 not null comment '是否删除',
    index idx_listId (list_id),
    index idx_userId (user_id)
) comment '题单点赞表' collate = utf8mb4_unicode_ci;
-- 题单收藏表
drop table if exists question_list_favour;
create table if not exists question_list_favour
(
    `id`          bigint auto_increment comment 'id' primary key,
    `list_id`     bigint                             not null comment '题单id',
    `user_id`     bigint                             not null comment '用户id',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`   tinyint  default 0                 not null comment '是否删除',
    index idx_listId (list_id),
    index idx_userId (user_id)
) comment '题单收藏表' collate = utf8mb4_unicode_ci;



-- 扩展


-- 通知类型表
create table if not exists notice_type
(
    `id`          bigint auto_increment comment 'id' primary key,
    `name`        varchar(128)                       not null comment '通知类型名称',
    `desc`        varchar(512)                       null comment '通知类型描述',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`   tinyint  default 0                 not null comment '是否删除',
    index idx_name (name)
) comment '通知类型表' collate = utf8mb4_unicode_ci;
-- 通知表
create table if not exists notice
(
    `id`          bigint auto_increment comment 'id' primary key,
    `user_id`     bigint                             not null comment '用户id',
    `type`        varchar(128)                       not null comment '通知类型',
    `content`     text                               not null comment '通知内容',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (user_id)
) comment '通知表' collate = utf8mb4_unicode_ci;
-- 系统消息表
create table if not exists system_message
(
    `id`          bigint auto_increment comment 'id' primary key,
    `type`        varchar(128)                       not null comment '消息类型',
    `content`     text                               not null comment '消息内容',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`   tinyint  default 0                 not null comment '是否删除',
    index idx_type (type)
) comment '系统消息表' collate = utf8mb4_unicode_ci;
-- 系统配置表
create table if not exists system_config
(
    `id`          bigint auto_increment comment 'id' primary key,
    `key`         varchar(128)                       not null comment '配置键',
    `value`       text                               not null comment '配置值',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`   tinyint  default 0                 not null comment '是否删除',
    index idx_key (`key`)
) comment '系统配置表' collate = utf8mb4_unicode_ci;
-- 日志表
create table if not exists log
(
    `id`          bigint auto_increment comment 'id' primary key,
    `user_id`     bigint                             null comment '用户id',
    `type`        varchar(128)                       not null comment '日志类型',
    `content`     text                               not null comment '日志内容',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`   tinyint  default 0                 not null comment '是否删除',
    index idx_type (type)
) comment '日志表' collate = utf8mb4_unicode_ci;
-- 统计表
create table if not exists statistics
(
    `id`          bigint auto_increment comment 'id' primary key,
    `type`        varchar(128)                       not null comment '统计类型',
    `value`       int      default 0                 not null comment '统计值',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`   tinyint  default 0                 not null comment '是否删除',
    index idx_type (type)
) comment '统计表' collate = utf8mb4_unicode_ci;
-- 反馈表
create table if not exists feedback
(
    `id`          bigint auto_increment comment 'id' primary key,
    `user_id`     bigint                             not null comment '用户id',
    `content`     text                               not null comment '反馈内容',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_delete`   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (user_id)
) comment '反馈表' collate = utf8mb4_unicode_ci;