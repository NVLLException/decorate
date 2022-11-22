drop database decorate;
create database decorate;
use decorate;
drop table if exists user;
drop table if exists customer;
drop table if exists material;
drop table if exists material_category;
drop table if exists material_group;
drop table if exists material_url;
create table user(
    `id` int(11) not null auto_increment,
    `name` varchar(100) not null,
    `password` varchar(200) not null,
    primary key(`id`),
    key(`id`),
    key(`password`)
)engine = InnoDB, character set ='UTF8';
insert into user(`name`, `password`) values('admin',md5('admin'));
create table customer(
    `id` int(11) not null auto_increment,
    `name` varchar(100),
    `openId` varchar(200) not null,
    `phone` varchar(100),
    `wxIconUrl` varchar(100),
    `ext1` varchar(200),
    `ext2` varchar(200),
    `ext3` varchar(200),
    `status` tinyint(1) default 0,
    `creatorId` int(11),
    `createTime` datetime,
    `updaterId` int(11),
    `updateTime` datetime,
    primary key(`id`),
    key(`openId`)
)engine = InnoDB, character set ='UTF8';
create table material(
    `id` int(11) not null auto_increment,
    `categoryId` int(11) not null,
    `name` varchar(100),
    `description` varchar(200),
    `length` varchar(10),
    `width` varchar(10),
    `high` varchar(10),
    `price` decimal default 0,
    `ext1` varchar(200),
    `ext2` varchar(200),
    `ext3` varchar(200),
    `status` tinyint(1) default 0,
    `creatorId` int(11),
    `createTime` datetime,
    `updaterId` int(11),
    `updateTime` datetime,
    primary key(`id`),
    key(`categoryId`)
)engine = InnoDB, character set ='UTF8';
create table material_category(
    `id` int(11) not null auto_increment,
    `groupId` int(11) not null,
    `name` varchar(200),
    `ext1` varchar(200),
    `ext2` varchar(200),
    `ext3` varchar(200),
    `status` tinyint(1) default 0,
    `creatorId` int(11),
    `createTime` datetime,
    `updaterId` int(11),
    `updateTime` datetime,
    primary key(`id`),
    key(`groupId`)
)engine = InnoDB, character set ='UTF8';
create table material_group(
    `id` int(11) not null auto_increment,
    `name` varchar(200),
    `ext1` varchar(200),
    `ext2` varchar(200),
    `ext3` varchar(200),
    `status` tinyint(1) default 0,
    `creatorId` int(11),
    `createTime` datetime,
    `updaterId` int(11),
    `updateTime` datetime,
    primary key(`id`)
)engine = InnoDB, character set ='UTF8';
create table material_url(
                             `id` int(11) not null auto_increment,
                             `fileName` varchar(100) not null,
                             `referId` int(11),
                             `type` tinyint(2) comment '1:material, 2:category, 3:group',
                             `remoteUrl` varchar(300),
                             `localUrl` varchar(300),
                             `status` tinyint(1) default 0,
                             `creatorId` int(11),
                             `createTime` datetime,
                             `updaterId` int(11),
                             `updateTime` datetime,
                             primary key(`id`),
                             key(`referId`),
                             key(`type`)
)engine = InnoDB, character set ='UTF8';
create table shopping_cart(
    `id` int(11) not null auto_increment,
    `wxUserId` int(11) not null,
    `materialId` int(11) not null,
    `count` int(11) not null,
    `status` tinyint(1) not null default 0,
     primary key(`id`),
     key(`wxUserId`),
     key(`materialId`)
)engine = InnoDB, character set ='UTF8';