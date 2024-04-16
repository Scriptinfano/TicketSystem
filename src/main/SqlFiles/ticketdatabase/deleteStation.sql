create
    definer = root@localhost procedure ticketdatabase.deleteStation(IN theStationSequence int)
begin
    delete from station where stationSequence = theStationSequence;
end;

