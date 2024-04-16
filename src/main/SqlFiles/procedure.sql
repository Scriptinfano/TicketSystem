use ticketdatabase;

-- 显示所有车次信息
create view VIEW_AllTrainService as
select trainServiceSequence as '车次序号', trainServiceId as '车次号', t_a.stationName as '起点站', l.stationName as '终点站', departureTime as '发车时间', personNum as'剩余人数'
from (select * from station) as t_a
         right join trainservice r on t_a.stationSequence = r.startStation
         left join station l on l.stationSequence = r.endStation;

-- 显示所有有效的车次信息，即发车时间在当前时间之后的车次信息
create view VIEW_ValidTrainService as
select trainServiceSequence as '车次序号', trainServiceId as '车次号', t_a.stationName as '起点站', l.stationName as '终点站', departureTime as '发车时间', personNum as'剩余人数'
from ((select * from station) as t_a
         right join trainservice r on t_a.stationSequence = r.startStation
         left join station l on l.stationSequence = r.endStation)
where departureTime >=now();

-- 车票的视图，显示车票的所有信息
create view VIEW_Ticket as
select accountSequence                                                                               as '账户序号',
       ticketSequence                                                                                as '车票序号',
       ticketId                                                                                      as '车票号',
       车次号,
       起点站,
       终点站,
       发车时间,
       wagonNum                                                                                      as '车厢号',
       seatNum                                                                                       as '座位号',
       orderTime                                                                                     as '下单时间',
       prize                                                                                         as '售价',
       case when 发车时间 < now() then '无效' when 发车时间 >= now() then '有效' end as '是否有效'
from ticket
         inner join myaccount on accountSequence_FK = myaccount.accountSequence
         inner join VIEW_AllTrainService on trainServiceSequence_FK = VIEW_AllTrainService.车次序号;

-- 超级管理员查看所有账户列表的时候查阅此视图
create view VIEW_AccountList as
select accountSequence as '账户序号',
       accountId                                                                            as '账户ID',
       case when accountType = 1 then '管理员账号' when accountType = 2 then '会员账号' end as '账户类型',
       case when accountType = 1 then null when accountType = 2 then paymentMethod end      as '支付方式',
       registerTime                                                                         as '注册时间',
       accountName                                                                          as '用户名',
       identificationId                                                                     as '身份证号',
       contactInfo                                                                          as '联系方式',
       name                                                                                 as '姓名',
       gender                                                                               as '性别',
       age                                                                                  as '年龄'
from myaccount
         inner join person on personSequence_FK = person.personSequence;

-- 超级管理员查看所有站点信息
create view VIEW_Station as
select stationSequence as '序号', stationName as '站点名'
from station;

-- 存储过程

-- 会员查看自己购买的已有车票，返回有效的车票，无效的车票(没有账户序号为空的车票)将不予显示
create procedure checkTicket(in theAccountSequence int, in outOfDate boolean)
begin
    if outOfDate then
        select 车票序号,
               车票号,
               车次号,
               起点站,
               终点站,
               发车时间,
               车厢号,
               座位号,
               下单时间,
               售价
        from VIEW_Ticket
        where 账户序号 = theAccountSequence and 是否有效='无效';
    else
        select 车票序号,
               车票号,
               车次号,
               起点站,
               终点站,
               发车时间,
               车厢号,
               座位号,
               下单时间,
               售价
        from VIEW_Ticket
        where 账户序号 = theAccountSequence and 是否有效='有效';
    end if;
end;

-- 管理员或会员自己查看自己的账户详细信息，超级管理员也可以调用此信息实现查看账户详细信息
create procedure checkAccountInfo(in theAccountType bool, in theAccountSequence int)
begin
    if theAccountType = 1 then
        -- 账户是管理员的情况
        select 账户ID, 用户名, 姓名, 性别, 年龄, 身份证号, 联系方式, 注册时间 from VIEW_AccountList where 账户序号 = theAccountSequence;
    elseif theAccountType = 2 then
        -- 账户是会员的情况
        select 账户ID, 用户名, 姓名, 性别, 年龄, 身份证号, 联系方式, 注册时间, 支付方式 from VIEW_AccountList where 账户序号 = theAccountSequence;
    end if;
end;


-- 超级管理员和会员和管理员都可以执行车次查询，查询的是有效的车次
create procedure searchTrainService(in theTrainService varchar(20), in theStartStation varchar(20), in theEndStation varchar(20), in theDepartureTime datetime)
begin
    select *
    FROM VIEW_ValidTrainService
    WHERE (车次号 = theTrainService OR theTrainService IS NULL)
      AND (起点站 = theStartStation OR theStartStation IS NULL)
      AND (终点站 = theEndStation OR theEndStation IS NULL)
      AND (发车时间 >= theDepartureTime OR theDepartureTime IS NULL);
end;

-- 超级管理员搜索账号
create procedure searchAccount(in theAccountID varchar(20), in theAccountType varchar(20), in thePaymentMethod varchar(20), in theAccountName varchar(20), in identityID varchar(18), in theName varchar(20), in theGender tinyint, in theAge tinyint)
begin
    select *
    FROM VIEW_AccountList
    WHERE (账户ID = theAccountID OR theAccountID IS NULL)
      AND (账户类型 = theAccountType OR theAccountType IS NULL)
      AND (支付方式 = thePaymentMethod OR thePaymentMethod IS NULL)
      AND (用户名 = theAccountName OR theAccountName IS NULL)
      and (身份证号 = identityID or identityID is null)
      and (姓名 = theName or theName is null)
      and (性别 = theGender or theGender is null)
      and (年龄 = theAge or theAge is null);
end;

-- 超级管理员添加新的车次
create procedure addTrainService(in theTrainServiceId varchar(20), in theStartStation int, in theEndStation int, in theDepartureTime datetime)
begin
    insert into trainservice(trainServiceId, startStation, endStation, departureTime, personNum) values (theTrainServiceId, theStartStation, theEndStation, theDepartureTime, 200);
end;

-- 超级管理员添加站点信息
create procedure addStation(in theStationName varchar(20))
begin
    insert into station(stationName) values (theStationName);
end;

-- 超级管理员删除站点
create procedure deleteStation(in theStationSequence int)
begin
    delete from station where stationSequence = theStationSequence;
end;

-- 超级管理员根据给定的站点序号修改站点的名字
create procedure modifyStation(in theStationSequence int, in theStationName varchar(20))
begin
    update station
    set stationName=theStationName
    where stationSequence = theStationSequence;
end;

-- 超级管理员按照站点的名字搜索
create procedure searchStation(in theStationName varchar(20))
begin
    select stationSequence from station where stationName = theStationName;

end;

-- 超级管理员修改管理员账户基本信息
create procedure modifyManagerAccountInfo(in theAccountSequence int, in thePassword varchar(20), in theAccountName varchar(20))
begin
    update myaccount
    set password=if(thePassword is not null, thePassword, password),
        accountName=if(theAccountName is not null, theAccountName, accountName)
    where accountSequence = theAccountSequence;
end;

-- 管理员或会员修改自己的个人私密信息
create procedure modifyPersonalInfo(in theAccountType tinyint, in theAccountSequence int, in theName varchar(20), in theGender tinyint, in theAge tinyint, in theIdentificationId varchar(18), in theContactInfo varchar(18), in thePaymentMethod varchar(20))
begin
    declare thePersonSequence int;

    select personSequence
    from person
             inner join ticketdatabase.myaccount m on person.personSequence = m.personSequence_FK
    where accountSequence = theAccountSequence
    into thePersonSequence;
    if theAccountType = 1 then
        -- 管理员
        update person
        set name=if(theName is not null, theName, name),
            gender=if(theGender is not null, theGender, gender),
            age=if(theAge is not null, theAge, age),
            identificationId=if(theIdentificationId is not null, theIdentificationId, identificationId),
            contactInfo=if(theContactInfo is not null, theContactInfo, contactInfo)
        where personSequence = thePersonSequence;
    elseif theAccountType = 2 then
        -- 会员
        update person
        set name=if(theName is not null, theName, name),
            gender=if(theGender is not null, theGender, gender),
            age=if(theAge is not null, theAge, age),
            identificationId=if(theIdentificationId is not null, theIdentificationId, identificationId),
            contactInfo=if(theContactInfo is not null, theContactInfo, contactInfo)
        where personSequence = thePersonSequence;
        update myaccount
        set paymentMethod=thePaymentMethod
        where accountSequence = theAccountSequence;
    end if;

end;

-- 超级管理员注销其他账户或会员注销自己的账号，管理员不能注销自己的账号
create procedure deleteAccount(in theAccountSequence int)
begin
    delete
    from myaccount
    where accountSequence = theAccountSequence;
end;

-- 超级管理员添加新的管理员账户或会员自己注册新的账号，根据theType来判断是超级管理员在添加新的管理员账户还是会员自己在注册会员账户，如果thePersonSequence是0，则将创建一个新的Person对象，否则为指定的Person对象创建新账户
create
    definer = root@localhost procedure addAccount(IN theType tinyint, IN thePassword varchar(20), IN theAccountID varchar(20), IN theIdentificationId varchar(18))
begin
    -- 添加新管理员：addAccount(1,'password',null,'身份证号')
    -- 添加老管理员，老管理员创建新账号：addAccount(1,'password','accountID',null)
    -- 添加新会员：addAccount(2,'password',null,'身份证号')
    -- 老会员添加新账号：addAccount(2,'password','accountID',null)
    declare thePersonSequence int default 0;
    declare newAccountID varchar(20);
    -- 区分是老用户添加新账号还是直接添加新账号
    if (theIdentificationId is not null and theAccountID is null) then
        -- 新用户使用身份证号来注册账号
        insert into person(identificationId)
        values (theIdentificationId);
        set thePersonSequence = (select personSequence from person where identificationId = theIdentificationId);
    elseif theIdentificationId is null and theAccountID is not null then
        select personSequence_FK from myaccount where accountId=theAccountID into thePersonSequence;
    end if;

    set newAccountID=substring(md5(rand()), 1, 20);
    insert into myaccount (accountType,accountId,password,registerTime,personSequence_FK)
    values (theType,newAccountID , thePassword, now(), thePersonSequence);

end;

-- 退票，将车票表中的归属账号字段设null
create procedure ticketReturn(in theTicketSequence int)
begin
    update ticket
    set accountSequence_FK=null
    where ticketSequence = theTicketSequence;
end;

-- 计算收入，返回财务报表，计算一个月内的总收入
create procedure checkFinance(out profit float)
begin
    declare accountIndex int default 0;
    DECLARE done BOOLEAN DEFAULT FALSE;
    declare ticketPrize float default 0;
    declare theAge int;
    declare childDiscount,studentDiscount float;
    declare theProfit float default 0;

    declare cursor1 cursor for select 账户序号, 售价 from VIEW_Ticket where 是否有效 = '有效' and timestampdiff(day, 下单时间, now()) < 30;
    declare continue handler for not found set done = true;
    open cursor1;

    select value from discount where type = '儿童' into childDiscount;
    select value from discount where type = '学生' into studentDiscount;




    my_loop:
    while not done
        do
        fetch cursor1 into accountIndex,ticketPrize;
        if not done then
            select age
            from person
                     inner join myaccount on person.personSequence = myaccount.personSequence_FK
            where accountSequence = accountIndex
            into theAge;
            if theAge < 10 then
                -- 按儿童票打折
                set ticketPrize = ticketPrize * childDiscount;
            elseif theAge >= 10 and theAge <= 20 then
                -- 按学生票打折
                set ticketPrize = ticketPrize * studentDiscount;
            end if;
            set theProfit = theProfit + ticketPrize;
        end if;
        end while
    my_loop;
    set profit = theProfit;
end;

-- 超级管理员编辑折扣信息,type为1代表设置儿童折扣,type为2代表设置学生折扣
create procedure modifyDiscount(in theType int, in theValue float)
begin
    update discount
    set value=theValue
    where discountSequence = theType;
end;

-- 检验是否成功登录
create procedure login(in theAccountId varchar(20), in thePassword varchar(20), out result boolean,out theType tinyint)
begin
    declare accountNum,realAccountNum tinyint;
    select count(*) from (select * from myaccount where accountId = theAccountId) as temp into accountNum;
    select count(*) from (select * from myaccount where accountId = theAccountId and password = thePassword) as temp into realAccountNum;
    if accountNum = 0 then
        set result = false;
        signal sqlstate '45000' set message_text = '查无此账号';
    elseif realAccountNum = 0 then
        set result = false;
        signal sqlstate '45000' set message_text = '密码不正确';
    end if;
    select accountType from myaccount where accountId=theAccountId into theType;
    set result = true;
end;

