create
    definer = root@localhost procedure ticketdatabase.checkTicket(IN theAccountSequence int, IN outOfDate tinyint(1))
begin
    if outOfDate then
        select 车票号,
               车次号,
               起点站,
               终点站,
               发车时间,
               车厢号,
               座位号,
               下单时间,
               售价
        from VIEW_Ticket
        where 账户序号 = theAccountSequence
          and 是否未过期 = '无效';
    else
        select 车票号,
               车次号,
               起点站,
               终点站,
               发车时间,
               车厢号,
               座位号,
               下单时间,
               售价
        from VIEW_Ticket
        where 账户序号 = theAccountSequence
          and 是否未过期 = '未过期';
    end if;

end;

