create
    definer = root@localhost procedure ticketdatabase.addAccount(IN theType tinyint, IN thePassword varchar(20), IN theAccountName varchar(20), IN thePaymentMethod varchar(20), IN theIdentificationId varchar(18))
begin
    -- 添加新管理员：addAccount(1,'password','account name',null,'身份证号')
    -- 添加老管理员，老管理员创建新账号：addAccount(1,'password','account name',null,null)
    -- 添加新会员：addAccount(2,'password','account name','微信支付','身份证号')
    -- 老会员添加新账号：addAccount(2,'password','account name','微信支付',null)
        set @sequence2=0;

    -- 区分是老用户添加新账号还是直接添加新账号
    if (theIdentificationId is not null) then
        insert into person(identificationId)
        values (theIdentificationId);
    end if;
   set @sequence2= (select personSequence from person where identificationId = theIdentificationId);

    if theType = 1 then
        insert into myaccount (accountId, accountType, password, accountName, registerTime, personSequence_FK, paymentMethod)
        values (substring(md5(rand()), 1, 20), theType, thePassword, theAccountName, current_date(), @sequence2, null);
    elseif theType = 2 then
        insert into myaccount (accountId, accountType, password, accountName, registerTime, personSequence_FK, paymentMethod)
        values (substring(md5(rand()), 1, 20), theType, thePassword, theAccountName, current_date(), @sequence2, thePaymentMethod);
    end if;
end;

