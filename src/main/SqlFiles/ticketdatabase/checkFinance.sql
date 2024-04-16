create
    definer = root@localhost procedure ticketdatabase.checkFinance(OUT profit float)
begin
    declare accountIndex,mark int default 0;
    declare ticketPrize float default 0;
    declare theAge int;
    declare childDiscount,studentDiscount float;
    declare theProfit float default 0;

    declare cursor1 cursor for select 账户序号, 售价 from VIEW_Ticket where 是否未过期 = '未过期' and timestampdiff(day, 下单时间, current_date()) < 30;
    declare continue handler for sqlstate '02000' set mark = 1;
    open cursor1;

    select value from discount where type = '儿童' into childDiscount;
    select value from discount where type = '学生' into studentDiscount;

    repeat
        fetch cursor1 into accountIndex,ticketPrize;

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
    until mark end repeat;

    set profit = theProfit;
end;

