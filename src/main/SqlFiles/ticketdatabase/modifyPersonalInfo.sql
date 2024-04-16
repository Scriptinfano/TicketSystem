create
    definer = root@localhost procedure ticketdatabase.modifyPersonalInfo(IN theAccountType tinyint, IN theAccountSequence int, IN theName varchar(20), IN theGender tinyint, IN theAge tinyint, IN theIdentificationId varchar(18), IN theContactInfo varchar(18), IN thePaymentMethod varchar(20))
begin
    declare thePersonSequence int;

    select personSequence
    from person
             inner join ticketdatabase.myaccount m on person.personSequence = m.personSequence_FK
    where accountSequence = theAccountSequence
    into thePersonSequence;
    if theAccountType = 1 then
        -- 管理员
        update person
        set name=if(theName is not null, theName, name),
            gender=if(theGender is not null, theGender, gender),
            age=if(theAge is not null, theAge, age),
            identificationId=if(theIdentificationId is not null, theIdentificationId, identificationId),
            contactInfo=if(theContactInfo is not null, theContactInfo, contactInfo)
        where personSequence = thePersonSequence;
    elseif theAccountType = 2 then
        -- 会员
        update person
        set name=if(theName is not null, theName, name),
            gender=if(theGender is not null, theGender, gender),
            age=if(theAge is not null, theAge, age),
            identificationId=if(theIdentificationId is not null, theIdentificationId, identificationId),
            contactInfo=if(theContactInfo is not null, theContactInfo, contactInfo)
        where personSequence = thePersonSequence;
        update myaccount
        set paymentMethod=thePaymentMethod
        where accountSequence = theAccountSequence;
    end if;

end;

