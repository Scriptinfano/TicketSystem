create
    definer = root@localhost procedure ticketdatabase.deleteAccount(IN theAccountSequence int)
begin
    delete
    from myaccount
    where accountSequence = theAccountSequence;
end;

