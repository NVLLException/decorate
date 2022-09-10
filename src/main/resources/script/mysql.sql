use decorate;
drop table if exists user;
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
    `description` varchar(200),
    `ext1` varchar(200),
    `ext2` varchar(200),
    `ext3` varchar(200),
    `creatorId` int(11),
    `createTime` datetime,
    `updaterId` int(11),
    `updateTime` datetime,
    primary key(`id`),
    key(`groupId`)
)engine = InnoDB, character set ='UTF8';
create table material_group(
    `id` int(11) not null auto_increment,
    `description` varchar(200),
    `ext1` varchar(200),
    `ext2` varchar(200),
    `ext3` varchar(200),
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
                             `creatorId` int(11),
                             `createTime` datetime,
                             `updaterId` int(11),
                             `updateTime` datetime,
                             primary key(`id`),
                             key(`referId`)
)engine = InnoDB, character set ='UTF8';