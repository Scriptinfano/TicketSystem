create
    definer = root@localhost procedure ticketdatabase.modifyDiscount(IN theType int, IN theValue float)
begin
    update discount
    set value=theValue
    where type = theType;
end;

