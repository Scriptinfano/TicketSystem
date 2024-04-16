create
    definer = root@localhost procedure ticketdatabase.addTicket(IN theAccountSequence int, IN theTrainServiceSequence int, IN thePrize float)
begin
    declare theRestOfPerson,theWagonNum,theSeatNum tinyint;

    select 剩余人数 from VIEW_ValidTrainService where 序号 = theTrainServiceSequence into theRestOfPerson;

    create temporary table indexes
    (
        seatIndex tinyint primary key
    );

    if theRestOfPerson > 0 then

        call getValidSeatIndex(theTrainServiceSequence, theWagonNum, theSeatNum);
        insert into ticket (ticketId, wagonNum, seatNum, orderTime, prize, accountSequence_FK, trainServiceSequence_FK)
        values (substring(md5(rand()), 1, 20), theWagonNum, theSeatNum, curdate(), thePrize, theAccountSequence, theTrainServiceSequence);
        -- 更新此趟列车的人数
        update trainservice
        set personNum=personNum - 1
        where trainServiceSequence = theTrainServiceSequence;

    else
        signal sqlstate '45000' set message_text = '票已卖光';
    end if;

end;

