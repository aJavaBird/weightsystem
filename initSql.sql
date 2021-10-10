CREATE DATABASE weight_sys DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

use weight_sys;

create table user(
id int primary key AUTO_INCREMENT comment '自增主键',
user_name varchar(50) unique comment '用户登录名',
password  varchar(80) comment '用户密码',
createTime datetime default current_timestamp comment '创建时间',
updateTime datetime default current_timestamp ON update current_timestamp  comment '更新时间' ,
remark varchar(100) comment '备注'
) comment '用户表';

create table weight(
id int primary key AUTO_INCREMENT comment '自增主键',
user_name varchar(50) comment '用户登录名',
weight  decimal(8,2) comment '体重',
waistline decimal(8,2) comment '腰围',
statistics_date date comment '统计日期',
is_delete char(1) default '0' comment '是否删除，1表示删除，0表示未删除，默认0',
createTime datetime default current_timestamp comment '创建时间，默认当前时间',
updateTime datetime default current_timestamp ON update current_timestamp  comment '更新时间' ,
remark varchar(100) comment '备注',
KEY i_weight_user_name (user_name)
) comment '体重记录表';
