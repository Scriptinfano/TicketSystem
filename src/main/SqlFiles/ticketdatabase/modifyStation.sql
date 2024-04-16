create
    definer = root@localhost procedure ticketdatabase.modifyStation(IN theStationSequence int, IN theStationName varchar(20))
begin
    update station
    set stationName=theStationName
    where stationSequence = theStationSequence;
end;

