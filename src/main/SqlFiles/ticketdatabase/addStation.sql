create
    definer = root@localhost procedure ticketdatabase.addStation(IN theStationName varchar(20))
begin
    insert into station(stationName) values (theStationName);
end;

