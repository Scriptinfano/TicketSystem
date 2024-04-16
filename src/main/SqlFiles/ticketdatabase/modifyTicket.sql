create
    definer = root@localhost procedure ticketdatabase.modifyTicket(IN theTicketSequence int, IN theNewTrainServiceSequence int)
begin
    declare mark,theValidWagonNum,theValidSeatNum tinyint default 0;
    declare cursor1 cursor for select 车厢号, 座位号 from VIEW_Ticket where 是否未过期 = '未过期' and 车次号 = theNewTrainServiceSequence;
    declare continue handler for sqlstate '02000' set mark = 1;
    open cursor1;

    create temporary table indexes
    (
        seatIndex tinyint primary key
    );

    update ticket
    set trainServiceSequence_FK=theNewTrainServiceSequence
    where ticketSequence = theTicketSequence;
    update trainservice
    set personNum=personNum - 1
    where trainServiceSequence = (select trainServiceSequence_FK from ticket where ticketSequence = theTicketSequence);
    update trainservice
    set personNum=personNum + 1
    where trainServiceSequence = theNewTrainServiceSequence;

    call getValidSeatIndex(theNewTrainServiceSequence, theValidWagonNum, theValidSeatNum);
    update ticket
    set wagonNum=theValidWagonNum,
        seatNum=theValidSeatNum
    where ticketSequence = theTicketSequence;
end;

