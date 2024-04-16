create
    definer = root@localhost procedure ticketdatabase.checkAccountInfo(IN theAccountType tinyint(1), IN theAccountSequence int)
begin
    if theAccountType = 1 then
        -- 账户是管理员的情况
        select 账户ID, 用户名, 姓名, 性别, 年龄, 身份证号, 联系方式, 注册时间 from VIEW_AccountList where 序号 = theAccountSequence;
    elseif theAccountType = 2 then
        -- 账户是会员的情况
        select 账户ID, 用户名, 姓名, 性别, 年龄, 身份证号, 联系方式, 注册时间, 支付方式 from VIEW_AccountList where 序号 = theAccountSequence;
    end if;
end;

