create
    definer = root@localhost procedure ticketdatabase.modifyManagerAccountInfo(IN theAccountSequence int, IN thePassword varchar(20), IN theAccountName varchar(20))
begin
    update myaccount
    set password=if(thePassword is not null, thePassword, password),
        accountName=if(theAccountName is not null, theAccountName, accountName)
    where accountSequence = theAccountSequence;
end;

