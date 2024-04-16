create
    definer = root@localhost procedure ticketdatabase.login(IN theAccountId varchar(20), IN thePassword varchar(20), OUT result tinyint(1))
begin
    if count((select 1 from myaccount where accountId = theAccountId)) = 0 then
        set result = false;
        signal sqlstate '45000' set message_text = '查无此账号';
    elseif count((select 1 from myaccount where accountId = theAccountId and password = thePassword)) = 0 then
        set result = false;
        signal sqlstate '45000' set message_text = '密码不正确';
    end if;
    set result = true;
end;

