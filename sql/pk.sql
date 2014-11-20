
CREATE SEQUENCE FAHRSCHEIN_SEQ INCREMENT BY 1 START WITH 1 MAXVALUE 99999999999 MINVALUE 1;
CREATE SEQUENCE KUNDE_SEQ INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999 MINVALUE 1;
CREATE SEQUENCE RESERVIERUNG_SEQ INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999 MINVALUE 1;
CREATE SEQUENCE STRECKE_SEQ INCREMENT BY 1 START WITH 1 MAXVALUE 999999999999 MINVALUE 1;
CREATE SEQUENCE ZUG_SEQ INCREMENT BY 1 START WITH 1 MAXVALUE 99999999999 MINVALUE 1;

create or replace trigger FAHRSCHEIN_TRIGGER     before insert on "FAHRSCHEIN"    for each row begin     if inserting then       if :NEW."FID" is null then          select FAHRSCHEIN_SEQ.nextval into :NEW."FID" from dual;       end if;    end if; end;

create or replace trigger KUNDE_TRIGGER     before insert on "KUNDE"    for each row begin     if inserting then       if :NEW."KID" is null then          select KUNDE_SEQ.nextval into :NEW."KID" from dual;       end if;    end if; end;

create or replace trigger RESERVIERUNG_TRIGGER     before insert on "RESERVIERUNG"    for each row begin     if inserting then       if :NEW."RID" is null then          select RESERVIERUNG_SEQ.nextval into :NEW."RID" from dual;       end if;    end if; end;

create or replace trigger STRECKE_TRIGGER     before insert on "STRECKE"    for each row begin     if inserting then       if :NEW."SID" is null then          select STRECKE_SEQ.nextval into :NEW."SID" from dual;       end if;    end if; end;

create or replace trigger ZUG_TRIGGER     before insert on "ZUG"    for each row begin     if inserting then       if :NEW."ZID" is null then          select ZUG_SEQ.nextval into :NEW."ZID" from dual;       end if;    end if; end;






 
