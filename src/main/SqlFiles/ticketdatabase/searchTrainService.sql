create
    definer = root@localhost procedure ticketdatabase.searchTrainService(IN theTrainService varchar(20), IN theStartStation int, IN theEndStation int, IN theDepartureTime datetime)
begin
    SELECT *
    FROM VIEW_ValidTrainService
    WHERE (车次号 = theTrainService OR theTrainService IS NULL)
      AND (起点站 = theStartStation OR theStartStation IS NULL)
      AND (终点站 = theEndStation OR theEndStation IS NULL)
      AND (发车时间 > theDepartureTime OR theDepartureTime IS NULL);
end;

