create
    definer = root@localhost procedure ticketdatabase.getValidSeatIndex(IN theTrainServiceSequence int, OUT theValidWagonNum tinyint, OUT theValidSeatNum tinyint)
begin
    declare mark,theWagonNum,theSeatNum,theSeatIndex,theIndex tinyint default 0;
    declare cursor1 cursor for select 车厢号, 座位号 from VIEW_Ticket where 是否未过期 = '未过期' and 车次号 = theTrainServiceSequence;
    declare continue handler for sqlstate '02000' set mark = 1;
    open cursor1;

    create temporary table indexes
    (
        seatIndex tinyint primary key
    );

    repeat
        fetch cursor1 into theWagonNum,theSeatNum;
        set theSeatIndex = (theWagonNum - 1) * 10 + theSeatNum;
        insert into indexes(seatIndex) values (theSeatIndex);
    until mark end repeat;

    while count((select seatIndex from indexes where seatIndex = theIndex)) >= 1
        do
            set theIndex = theIndex + 1;
        end while;
    set theWagonNum = theIndex / 10;
    set theSeatNum = theIndex % 10;

    set theValidSeatNum = theSeatNum;
    set theValidWagonNum = theWagonNum;
end;

