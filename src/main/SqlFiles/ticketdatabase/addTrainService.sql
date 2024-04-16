create
    definer = root@localhost procedure ticketdatabase.addTrainService(IN theTrainServiceId varchar(20), IN theStartStation int, IN theEndStation int, IN theDepartureTime datetime)
begin
    insert into trainservice(trainServiceId, startStation, endStation, departureTime, personNum) values (theTrainServiceId, theStartStation, theEndStation, theDepartureTime, 200);
end;

