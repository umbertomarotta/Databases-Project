-- L’ID di utente non deve essere modificato
CREATE OR replace TRIGGER usr_idup 
  BEFORE UPDATE ON Utente 
  FOR EACH ROW 
  WHEN (NEW.id != OLD.id) 
BEGIN 
    Raise_application_error(-20001, 'Main Table ID cannot be changed'); 
END;

-- L’ID di album non deve essere modificato
CREATE OR replace TRIGGER alb_idup 
  BEFORE UPDATE ON Album 
  FOR EACH ROW 
  WHEN (NEW.id != OLD.id) 
BEGIN 
    Raise_application_error(-20001, 'Main Table ID cannot be changed'); 
END;

-- L’ID di file non deve essere modificato
CREATE OR replace TRIGGER fil_idup 
  BEFORE UPDATE ON Files 
  FOR EACH ROW 
  WHEN (NEW.id != OLD.id) 
BEGIN 
    Raise_application_error(-20001, 'Main Table ID cannot be changed'); 
END;

-- L’ID di raccolta non deve essere modificato
CREATE OR replace TRIGGER Rac_idup 
  BEFORE UPDATE ON Raccolta 
  FOR EACH ROW 
  WHEN (NEW.id != OLD.id) 
BEGIN 
    Raise_application_error(-20001, 'Main Table ID cannot be changed'); 
END;

-- L’ID di gruppo non deve essere modificato
CREATE OR replace TRIGGER gru_idup 
  BEFORE UPDATE ON Gruppo 
  FOR EACH ROW 
  WHEN (NEW.id != OLD.id) 
BEGIN 
    Raise_application_error(-20001, 'Main Table ID cannot be changed'); 
END;

-- L’ID di commento non deve essere modificato
CREATE OR replace TRIGGER com_idup 
  BEFORE UPDATE ON Commento 
  FOR EACH ROW 
  WHEN (NEW.id != OLD.id) 
BEGIN 
    Raise_application_error(-20001, 'Main Table ID cannot be changed'); 
END;

-- L’ID di tag non deve essere modificato
CREATE OR replace TRIGGER tag_idup 
  BEFORE UPDATE ON Tag 
  FOR EACH ROW 
  WHEN (NEW.id != OLD.id) 
BEGIN 
    Raise_application_error(-20001, 'Main Table ID cannot be changed'); 
END;

-- L’ID di Discussione non deve essere modificato
CREATE OR replace TRIGGER dis_idup 
  BEFORE UPDATE ON Discussione 
  FOR EACH ROW 
  WHEN (NEW.id != OLD.id) 
BEGIN 
    Raise_application_error(-20001, 'Main Table ID cannot be changed'); 
END;

-- L’ID di Visualizza non deve essere modificato
CREATE OR replace TRIGGER vis_idup 
  BEFORE UPDATE ON Visualizza 
  FOR EACH ROW 
  WHEN (NEW.id != OLD.id) 
BEGIN 
    Raise_application_error(-20001, 'Main Table ID cannot be changed'); 
END;

-- FotoID deve essere una foto di UtenteID
CREATE OR replace TRIGGER usr_fotid 
  BEFORE INSERT OR UPDATE ON utente 
  FOR EACH ROW 
  WHEN (NEW.fotoid IS NOT NULL) 
DECLARE 
    x INTEGER; 
BEGIN 
    SELECT Count(*) 
    INTO   x 
    FROM   foto 
           join files 
             ON files.id = foto.fileid 
           join album 
             ON files.albumid = album.id 
    WHERE  :new.id = album.proprietarioid 
           AND :new.fotoid = files.id; 

    IF ( x != 1 ) THEN 
      Raise_application_error(-20002, 'FotoID Inconsistence'); 
    END IF; 
END;

-- La foto non deve essere anche un video
CREATE OR replace TRIGGER fot_tr1 
  BEFORE INSERT OR UPDATE ON foto 
  FOR EACH ROW 
DECLARE 
    x INTEGER; 
BEGIN 
    SELECT Count(*) 
    INTO   x 
    FROM   video 
    WHERE  :new.fileid = video.fileid; 

    IF ( x != 0 ) THEN 
      Raise_application_error(-20003, 'FileID is already assigned'); 
    END IF; 
END;

-- Il video non deve essere anche una foto
CREATE OR replace TRIGGER vid_tr1 
  BEFORE INSERT OR UPDATE ON video 
  FOR EACH ROW 
DECLARE 
    x INTEGER; 
BEGIN 
    SELECT Count(*) 
    INTO   x 
    FROM   foto 
    WHERE  :new.fileid = foto.fileid; 

    IF ( x != 0 ) THEN 
      Raise_application_error(-20003, 'FileID is already assigned'); 
    END IF; 
END;

-- Auto incremento
CREATE SEQUENCE pervisual 
  INCREMENT BY 1 
  START WITH 1; 

CREATE TRIGGER incvisual 
  BEFORE INSERT ON visualizza 
  FOR EACH ROW 
BEGIN 
    NEW.id := pervisual.NEXTVAL; 
END;

-- L’utente non puo’ visualizzare tutto
CREATE OR replace TRIGGER vis_tr1 
  BEFORE INSERT OR UPDATE ON visualizza 
  FOR EACH ROW 
DECLARE 
    x     INTEGER; 
BEGIN 
    x := 1; 

    IF( :new.fileid IS NULL ) THEN --è una discussione
      SELECT Count(*) 
      INTO   x 
      FROM   discussione 
             join gruppo 
               ON discussione.gruppoid = gruppo.id 
             join membership 
               ON gruppo.id = membership.gruppoid 
             join utente 
               ON :new.utenteid = utente.id 
      WHERE  membership.utenteid = :new.utenteid --se faccio parte del gruppo 
             AND discussione.id = :new.discussioneid --se la discussione esiste 
             AND :new.data >= discussione.datacreazione 
             --se il post esisteva prima 
             AND :new.data >= utente.dataregistrazione; --se io esistevo prima 
    ELSE --e’ una file
      SELECT Count(*) 
      INTO   x 
      FROM   files 
             join album 
               ON files.albumid = album.id 
             join utente 
               ON :new.utenteid = utente.id 
      WHERE  ( files.privacy != 'Me' 
                OR album.proprietarioid = :new.utenteid ) 
             --se posso visualizzare il file 
             AND files.id = :new.fileid --se il file esiste 
             AND :new.data >= files.dataupload --se il post esisteva prima 
             AND :new.data >= utente.dataregistrazione; --se io esistevo prima 
    END IF; 

    IF ( x != 1 ) THEN 
      Raise_application_error(-20004, 'User cannot view the file'); 
    END IF; 
END;

-- L’utente non puo’ commentare tutto e senza averlo visualizzato
CREATE OR replace TRIGGER com_tr1 
  BEFORE INSERT OR UPDATE ON commento 
  FOR EACH ROW 
DECLARE 
    x INTEGER; 
BEGIN 
    x := 1; 

    IF( :new.fileid IS NULL ) THEN --e' una discussione  
      SELECT Count(*) 
      INTO   x 
      FROM   discussione 
             join gruppo 
               ON discussione.gruppoid = gruppo.id 
             join membership 
               ON gruppo.id = membership.gruppoid 
             join utente 
               ON :new.utenteid = utente.id 
      WHERE  membership.utenteid = :new.utenteid --se faccio parte del gruppo   
             AND discussione.id = :new.discussioneid 
             --se la discussione esiste   
             AND :new.data >= discussione.datacreazione 
             --se il post esisteva prima   
             AND :new.data >= utente.dataregistrazione; --se io esistevo prima   
    ELSE --e? una file  
      SELECT Count(*) 
      INTO   x 
      FROM   files 
             join album 
               ON files.albumid = album.id 
             join utente 
               ON :new.utenteid = utente.id 
      WHERE  ( files.privacy != 'Me' 
                OR album.proprietarioid = :new.utenteid ) 
             --se posso visualizzare il file   
             AND files.id = :new.fileid --se il file esiste   
             AND :new.data >= files.dataupload --se il post esisteva prima   
             AND :new.data >= utente.dataregistrazione; --se io esistevo prima   
    END IF; 

    IF ( x != 1 ) THEN 
      Raise_application_error(-20004, 'User cannot view the file'); 
    ELSE 
      SELECT Count(*) 
      INTO   x 
      FROM   visualizza 
      WHERE  :new.discussioneid = visualizza.discussioneid 
             AND :new.fileid = visualizza.fileid 
             AND :new.utenteid = visualizza.utenteid; 

      IF ( x = 0 ) THEN --non ho visualizzato 
        INSERT INTO visualizza --Doppio controllo
        VALUES      (0, 
                     :new.utenteid, 
                     :new.discussioneid, 
                     :new.fileid, 
                     :new.data); 
      END IF; 
    END IF; 
END; 

-- L’utente non puo’ aggiungere tutto ai preferiti e senza visualizzarlo
CREATE OR replace TRIGGER pre_tr1 
  BEFORE INSERT OR UPDATE ON preferiti 
  FOR EACH ROW 
DECLARE 
    x INTEGER; 
BEGIN 
    x := 1; 

    IF( :new.fileid IS NULL ) THEN --è una discussione  
      SELECT Count(*) 
      INTO   x 
      FROM   discussione 
             join gruppo 
               ON discussione.gruppoid = gruppo.id 
             join membership 
               ON gruppo.id = membership.gruppoid 
             join utente 
               ON :new.utenteid = utente.id 
      WHERE  membership.utenteid = :new.utenteid --se faccio parte del gruppo   
             AND discussione.id = :new.discussioneid 
             --se la discussione esiste   
             AND :new.datacreazione >= discussione.datacreazione 
             --se il post esisteva prima   
             AND :new.datacreazione >= utente.dataregistrazione; --se io esistevo prima   
    ELSE --e un file  
      SELECT Count(*) 
      INTO   x 
      FROM   files 
             join album 
               ON files.albumid = album.id 
             join utente 
               ON :new.utenteid = utente.id 
      WHERE  ( files.privacy != 'Me' 
                OR album.proprietarioid = :new.utenteid ) 
             --se posso visualizzare il file   
             AND files.id = :new.fileid --se il file esiste   
             AND :new.datacreazione >= files.dataupload --se il post esisteva prima   
             AND :new.datacreazione >= utente.dataregistrazione; --se io esistevo prima   
    END IF; 

    IF ( x != 1 ) THEN 
      Raise_application_error(-20004, 'User cannot view the file'); 
    ELSE 
      SELECT Count(*) 
      INTO   x 
      FROM   visualizza 
      WHERE  :new.discussioneid = visualizza.discussioneid 
             AND :new.fileid = visualizza.fileid 
             AND :new.utenteid = visualizza.utenteid; 

      IF ( x = 0 ) THEN --non ho visualizzato 
        INSERT INTO visualizza --Doppio controllo
        VALUES      (0, 
                     :new.utenteid, 
                     :new.discussioneid, 
                     :new.fileid, 
                     :new.datacreazione); 
      END IF; 
    END IF; 
END;

-- Date di Following
CREATE OR replace TRIGGER fol_date 
  BEFORE INSERT OR UPDATE ON following 
  FOR EACH ROW 
DECLARE 
    x INTEGER; 
BEGIN 
    SELECT Count(*) 
    INTO   x 
    FROM   utente U1, 
           utente U2 
    WHERE  :new.utenteid = U1.id 
           AND :new.followerid = U2.id 
           AND :new.datacreazione >= U1.dataregistrazione 
           AND :new.datacreazione >= U2.dataregistrazione; 

    IF ( x != 1 ) THEN 
      Raise_application_error(-20005, 'Date Inconsistence'); 
    END IF; 
END; 

-- Date di Album
CREATE OR replace TRIGGER alb_date 
  BEFORE INSERT OR UPDATE ON album 
  FOR EACH ROW 
DECLARE 
    x INTEGER; 
BEGIN 
    SELECT Count(*) 
    INTO   x 
    FROM   utente 
    WHERE  utente.id = :new.proprietarioid 
           AND utente.dataregistrazione <= :new.datacreazione; 

    IF ( x != 1 ) THEN 
      Raise_application_error(-20005, 'Date Inconsistence'); 
    END IF; 
END;

-- Date di Raccolta
CREATE OR replace TRIGGER rac_date 
  BEFORE INSERT OR UPDATE ON raccolta 
  FOR EACH ROW 
DECLARE 
    x INTEGER; 
BEGIN 
    SELECT Count(*) 
    INTO   x 
    FROM   utente 
    WHERE  utente.id = :new.creatoreid 
           AND utente.dataregistrazione <= :new.datacreazione; 

    IF ( x != 1 ) THEN 
      Raise_application_error(-20005, 'Date Inconsistence'); 
    END IF; 
END;


-- Date di Gruppo
CREATE OR replace TRIGGER gru_date 
  BEFORE INSERT OR UPDATE ON gruppo 
  FOR EACH ROW 
DECLARE 
    x INTEGER; 
BEGIN 
    SELECT Count(*) 
    INTO   x 
    FROM   utente 
    WHERE  utente.id = :new.creatoreid 
           AND utente.dataregistrazione <= :new.datacreazione; 

    IF ( x != 1 ) THEN 
      Raise_application_error(-20005, 'Date Inconsistence'); 
    END IF; 
END;

-- Date di Files
CREATE OR replace TRIGGER fil_date 
  BEFORE INSERT OR UPDATE ON files 
  FOR EACH ROW 
DECLARE 
    x INTEGER; 
BEGIN 
    SELECT Count(*) 
    INTO   x 
    FROM   album 
           join utente 
             ON album.proprietarioid = utente.id 
    WHERE  album.id = :new.albumid 
           AND album.datacreazione <= :new.dataupload 
           AND utente.dataregistrazione <= :new.dataupload; 

    IF ( x != 1 ) THEN 
      Raise_application_error(-20005, 'Date Inconsistence'); 
    END IF; 
END;

-- Date di Discussione
CREATE OR replace TRIGGER disc_date 
  BEFORE INSERT OR UPDATE ON discussione 
  FOR EACH ROW 
DECLARE 
    x INTEGER; 
BEGIN 
    SELECT Count(*) 
    INTO   x 
    FROM   utente, 
           gruppo 
    WHERE  :new.starterid = utente.id 
           AND :new.gruppoid = gruppo.id 
           AND :new.datacreazione >= utente.dataregistrazione 
           AND :new.datacreazione >= gruppo.datacreazione; 

    IF ( x != 1 ) THEN 
      Raise_application_error(-20005, 'Date Inconsistence'); 
    END IF; 
END;

-- Date di Membership
CREATE OR replace TRIGGER memb_date 
  BEFORE INSERT OR UPDATE ON membership 
  FOR EACH ROW 
DECLARE 
    x INTEGER; 
BEGIN 
    SELECT Count(*) 
    INTO   x 
    FROM   utente, 
           gruppo 
    WHERE  :new.utenteid = utente.id 
           AND :new.gruppoid = gruppo.id 
           AND :new.datacreazione >= utente.dataregistrazione 
           AND :new.datacreazione >= gruppo.datacreazione; 

    IF ( x != 1 ) THEN 
      Raise_application_error(-20005, 'Date Inconsistence'); 
    END IF; 
END;
