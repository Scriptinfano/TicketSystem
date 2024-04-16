create
    definer = root@localhost procedure ticketdatabase.ticketReturn(IN theTicketSequence int)
begin
    update ticket
    set accountSequence_FK=null
    where ticketSequence = theTicketSequence;
end;

