drop database if exists TicketDatabase;
create database TicketDatabase;
use TicketDatabase;
SET FOREIGN_KEY_CHECKS = 0;

/* Drop Tables */
-- 每个语句后面的CASCADE删除与该表相关联的所有约束、触发器、索引等。使用CASCADE确保删除操作的彻底性，不留下任何与该表相关的对象
DROP TABLE IF EXISTS `Account` CASCADE;
DROP TABLE IF EXISTS `Discount` CASCADE;
DROP TABLE IF EXISTS `Person` CASCADE;
DROP TABLE IF EXISTS `Station` CASCADE;
DROP TABLE IF EXISTS `Ticket` CASCADE;
DROP TABLE IF EXISTS `TrainService` CASCADE;

/* Create Tables */
-- 账户
CREATE TABLE `MyAccount`
(
    `accountSequence`   INT auto_increment NOT NULL comment '账户序号，自增主键',
    `accountId`         VARCHAR(20)        NOT NULL comment '账户ID',
    `accountName`       VARCHAR(20)        NULL comment '用户名',
    `password`          VARCHAR(20)        NOT NULL comment '登录密码',
    `accountType`       TINYINT            NOT NULL comment '账户类型，标识账户属于会员还是管理员，1代表管理员，2代表会员账户',
    `paymentMethod`     VARCHAR(20)        NULL comment '支付方式，只能取支付宝或者微信支付，当账户类型为管理员时，支付方式设为null',
    `registerTime`      DATETIME(6)        NOT NULL comment '注册时间',
    `personSequence_FK` INT                NULL comment '外键，标识该账户属于哪一个人',
    constraint CHK_AccountType check ( accountType in (1, 2) ),
    constraint CHK_PaymentMethod check ( paymentMethod in ('支付宝', '微信支付', null) ),
    constraint FK_person foreign key (personSequence_FK) references Person (personSequence) on DELETE CASCADE on UPDATE RESTRICT,
    constraint PK_Account primary key (accountSequence),
    constraint UNIQ_AccountId unique key (accountId),
    constraint UNIQ_AccountName unique key (accountName)
);

-- 折扣
CREATE TABLE `Discount`
(
    `discountSequence` INT auto_increment NOT NULL comment '折扣序号，自增主键，1代表儿童，2代表学生',
    `value`            FLOAT              NOT NULL comment '折扣值，值介于0到1之间',
    `type`             VARCHAR(20)        NOT NULL comment '折扣类型，取学生或儿童',
    CONSTRAINT `PK_Discount` PRIMARY KEY (discountSequence),
    constraint CHK_Type check ( type in ('学生', '儿童')),
    constraint CHK_Value check ( value > 0 and value <= 1 )
);

-- 乘客
CREATE TABLE `Person`
(
    `personSequence`   INT auto_increment comment '乘客序号，自增主键',
    `name`             VARCHAR(20) NULL comment '姓名',
    `gender`           TINYINT     NULL comment '性别，取1表示男，取2表示女',
    `age`              TINYINT     NULL comment '年龄',
    `identificationId` VARCHAR(18) NOT NULL comment '身份证号',
    `contactInfo`      VARCHAR(20) NULL comment '联系方式',
    constraint PK_personSequence primary key (personSequence),
    constraint UNIQ_IdentificationId unique key (identificationId),
    constraint CHK_Gender check ( gender in (1, 2) ),
    constraint CHK_Age check ( age > 0 and age < 100 )
);

-- 站点
CREATE TABLE `Station`
(
    `stationSequence` INT auto_increment comment '站点序号，自增主键',
    `stationName`     VARCHAR(20) NOT NULL comment '站点名',
    CONSTRAINT PK_Station PRIMARY KEY (stationSequence),
    constraint UNIQ_StationName unique key (stationName)
);

-- 车票，当accountSequence_FK为null时标识车票已退或者账号被删除之后该票没有归属者
CREATE TABLE `Ticket`
(
    `ticketSequence`          INT auto_increment NOT NULL comment '车票的序号，自增主键',
    `ticketId`                VARCHAR(20)        NOT NULL comment '票的ID号',
    `wagonNum`                TINYINT            NOT NULL comment '车厢号，总共十个车厢',
    `seatNum`                 TINYINT            NOT NULL comment '座位号，每个车厢20个座位',
    `orderTime`               DATETIME(6)        NOT NULL comment '买票时间',
    `prize`                   FLOAT              NOT NULL comment '该票的基础售价',
    `accountSequence_FK`      INT                NULL comment '外键，连接到账户的序号，标识该车票被哪个账号买走',
    `trainServiceSequence_FK` INT                NOT NULL comment '外键，连接到车次的序号，标识该车票代表哪一趟车次',
    CONSTRAINT `PK_Ticket` PRIMARY KEY (ticketSequence),
    constraint CHK_Prize check ( prize > 0 ),
    constraint FK_AccountSequence foreign key (accountSequence_FK) references MyAccount (accountSequence) on DELETE SET NULL on UPDATE RESTRICT,
    constraint FK_TrainServiceSequence foreign key (trainServiceSequence_FK) references TrainService (trainServiceSequence) on delete restrict on update restrict,
    constraint CHK_WagonNum check ( wagonNum > 0 and wagonNum <= 10 ),
    constraint CHK_SeatNum check ( seatNum > 0 and seatNum <= 20 ),
    constraint UNIQ_TicketId unique key (ticketId)
);

-- 车次
CREATE TABLE `TrainService`
(
    `trainServiceSequence` INT auto_increment NOT NULL comment '车次序号，自增主键',
    `trainServiceId`       VARCHAR(20)        NOT NULL comment '车次号，标识列车的号码',
    `startStation`         INT                NULL comment '起始站，外键，指向站点的序号',
    `endStation`           INT                NULL comment '终点站，外键，指向站点的序号',
    `departureTime`        DATETIME           NOT NULL comment '发车时间',
    `personNum`            INT                NOT NULL comment '该趟列车所剩余的人数',
    CONSTRAINT `PK_TrainServiceSequence` PRIMARY KEY (trainServiceSequence),
    constraint FK_StartStation foreign key (startStation) references Station (stationSequence) on delete set null on update restrict,
    constraint FK_EndStation foreign key (endStation) references Station (stationSequence) on delete set null on update restrict,
    constraint CHK_PersonNum check ( personNum > 0 )
);
SET FOREIGN_KEY_CHECKS = 1;
-- 填充Discount表
insert into discount(value, type)
values (1, '儿童');
insert into discount(value, type)
values (1, '学生');