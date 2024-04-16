create
    definer = root@localhost procedure ticketdatabase.searchStation(IN theStationName varchar(20))
begin
    select stationSequence from station where stationName = theStationName;
end;

