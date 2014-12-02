--Utente(ID, FotoID, Username, Password, Email, Nome, Cognome, DataNascita, DataRegistrazione, UltimoLogin)
CREATE TABLE Utente(
    ID integer NOT NULL,
    FotoID integer UNIQUE,
    Username varchar2(30) NOT NULL UNIQUE,
    Password varchar2(30) NOT NULL,
    Email varchar2(30) NOT NULL UNIQUE CHECK
(REGEXP_LIKE(Risoluzione,'^.+@.+\..+$')),
    Nome varchar2(30),
    Cognome varchar2(30),
    DataNascita date,
    DataRegistrazione date NOT NULL,
    UltimoLogin date NOT NULL,
    CONSTRAINT usr_pk PRIMARY KEY (ID),
);

ALTER TABLE Utente 
    ADD CONSTRAINT usr_fk1 FOREIGN KEY (FotoID)
        REFERENCES Files (ID) ON DELETE SET NULL;
    ADD CONSTRAINT usr_ck1 CHECK ( UltimoLogin >= DataRegistrazione);
    ADD CONSTRAINT usr_ck2 CHECK ( DataRegistrazione >= DataNascita);

--Album(ID, PropietarioID, CopertinaID, Titolo, Descrizione, DataCreazione, Data)
CREATE TABLE Album(
    ID integer NOT NULL,
    ProprietarioID integer NOT NULL,
    CopertinaID integer UNIQUE,
    Titolo varchar2(30) NOT NULL,
    Descrizione varchar2(255),
    DataCreazione date NOT NULL,
    Data date,
    CONSTRAINT alb_pk PRIMARY KEY (ID),
    CONSTRAINT alb_fk1 FOREIGN KEY (ProprietarioID)
        REFERENCES Utente (ID) ON DELETE CASCADE,
);

ALTER TABLE Album
    ADD CONSTRAINT alb_fk2 FOREIGN KEY (CopertinaID)
        REFERENCES Files (ID) ON DELETE SET NULL;

--Files(ID, AlbumID, GruppoID, Titolo, Descrizione, Privacy, DataUpload, Data, Luogo, Dimensione, Percorso)
CREATE TABLE Files(
    ID integer NOT NULL,
    AlbumID integer NOT NULL,
    GruppoID integer,
    Titolo varchar2(30),
    Descrizione varchar2(255),
    Privacy varchar2(30),
    DataUpload date NOT NULL,
    Data date,
    Luogo varchar2(30),
    Dimensione integer NOT NULL,
    Percorso varchar2(255) NOT NULL,
    CONSTRAINT fil_pk PRIMARY KEY (ID),
    CONSTRAINT fil_fk1 FOREIGN KEY (AlbumID)
        REFERENCES Album (ID) ON DELETE CASCADE,
    CONSTRAINT fil_fk2 FOREIGN KEY (GruppoID)
        REFERENCES Gruppo (ID) ON DELETE SET NULL,
    CONSTRAINT fil_prv CHECK
        (Privacy IN ('Tutti', 'Me'))
);

--Foto(FileID, Risoluzione, Compression, CameraMan, CameraModel)
CREATE TABLE Foto(
    FileID integer NOT NULL,
    Risoluzione varchar2(30) NOT NULL CHECK
(REGEXP_LIKE(Risoluzione,'[1-9]{1,4}x[1-9]{1,4}')),
    Compression varchar2(30) NOT NULL,
    CameraMan varchar2(30),
    CameraModel varchar2(30),
    CONSTRAINT fot_com CHECK
        (Compression IN ('JPEG', 'GIF', 'PNG', 'RAW')),
    CONSTRAINT fot_pk PRIMARY KEY (FileID),
    CONSTRAINT fot_fk1 FOREIGN KEY (FileID)
        REFERENCES Files (ID) ON DELETE CASCADE
);

--Video(FileID, Risoluzione, Bitrate, Ratio, Compression)
CREATE TABLE Video(
    FileID integer NOT NULL,
    Risoluzione varchar2(30) NOT NULL,
    Bitrate integer NOT NULL,
    Ratio varchar2(30) NOT NULL,
    Compression varchar2(30) NOT NULL,
    CONSTRAINT vid_res CHECK
        (Risoluzione IN ('360p', '720p', '1080p', '4K')),
    CONSTRAINT vid_com CHECK
        (Compression IN ('MPEG-2', 'MPEG-4', 'H.264', 'MKV')),
    CONSTRAINT vid_pk PRIMARY KEY (FileID),
    CONSTRAINT vid_fk1 FOREIGN KEY (FileID)
        REFERENCES Files (ID) ON DELETE CASCADE
);

--Raccolta(ID, CreatoreID, CopertinaID, Nome, Descrizione, DataCreazione)
CREATE TABLE Raccolta(
    ID integer NOT NULL,
    CreatoreID integer NOT NULL,
    CopertinaID integer,
    Nome varchar2(30) NOT NULL,
    Descrizione varchar2(255),
    DataCreazione date NOT NULL,
    CONSTRAINT rac_pk PRIMARY KEY (ID),
    CONSTRAINT rac_fk1 FOREIGN KEY (CreatoreID)
        REFERENCES Utente (ID) ON DELETE CASCADE,
    CONSTRAINT rac_fk2 FOREIGN KEY (CopertinaID)
        REFERENCES Files (ID) ON DELETE SET NULL
);

--Gruppo(ID, CreatoreID, Nome, Descrizione, DataCreazione)
CREATE TABLE Gruppo(
    ID integer NOT NULL,
    CreatoreID integer NOT NULL,
    Nome varchar2(30) NOT NULL UNIQUE,
    Descrizione varchar2(255),
    DataCreazione date NOT NULL,
    CONSTRAINT gru_pk PRIMARY KEY (ID),
    CONSTRAINT gru_fk1 FOREIGN KEY (CreatoreID)
        REFERENCES Utente (ID) ON DELETE CASCADE
);

--Discussione(ID, StarterID, GruppoID, Titolo, Testo, DataCreazione)
CREATE TABLE Discussione(
    ID integer NOT NULL,
    StarterID integer NOT NULL,
    GruppoID integer NOT NULL,
    Titolo varchar2(30) NOT NULL,
    Testo varchar2(255),
    DataCreazione date NOT NULL,
    CONSTRAINT dis_pk PRIMARY KEY (ID),
    CONSTRAINT dis_fk1 FOREIGN KEY (StarterID)
        REFERENCES Utente (ID) ON DELETE CASCADE,
    CONSTRAINT dis_fk2 FOREIGN KEY (GruppoID)
        REFERENCES Gruppo (ID) ON DELETE CASCADE
);

--Commento(ID, UtenteID, DiscussioneID, FileID, Testo, Data)
CREATE TABLE Commento(
    ID integer NOT NULL,
    UtenteID integer NOT NULL,
    DiscussioneID integer,
    FileID integer,
    Testo varchar2(255) NOT NULL,
    Data date NOT NULL,
    CONSTRAINT com_pk PRIMARY KEY (ID),
    CONSTRAINT com_fk1 FOREIGN KEY (UtenteID)
        REFERENCES Utente (ID) ON DELETE CASCADE,
    CONSTRAINT com_fk2 FOREIGN KEY (DiscussioneID)
        REFERENCES Discussione (ID) ON DELETE CASCADE,
    CONSTRAINT com_fk3 FOREIGN KEY (FileID)
        REFERENCES Files (ID) ON DELETE CASCADE,
    CONSTRAINT com_ck1 CHECK ((DiscussioneID is null AND FileID is not null) OR
                              (DiscussioneID is not null AND FileID is null)),
);

--RaccolteAlbum(RaccoltaID, AlbumID)
CREATE TABLE RaccolteAlbum(
    RaccoltaID integer NOT NULL,
    AlbumID integer NOT NULL,
    CONSTRAINT ral_pk PRIMARY KEY (RaccoltaID, AlbumID),
    CONSTRAINT ral_fk1 FOREIGN KEY (RaccoltaID)
        REFERENCES Raccolta (ID) ON DELETE CASCADE,
    CONSTRAINT ral_fk2 FOREIGN KEY (AlbumID)
        REFERENCES Album (ID) ON DELETE CASCADE
);

--Following(UtenteID, FollowerID, DataCreazione)
CREATE TABLE Following(
    UtenteID integer NOT NULL,
    FollowerID integer NOT NULL,
    DataCreazione date NOT NULL,
    CONSTRAINT fol_pk PRIMARY KEY (UtenteID, FollowerID),
    CONSTRAINT fol_fk1 FOREIGN KEY (UtenteID)
        REFERENCES Utente (ID) ON DELETE CASCADE,
    CONSTRAINT fol_fk2 FOREIGN KEY (FollowerID)
        REFERENCES Utente (ID) ON DELETE CASCADE,
    CONSTRAINT fol_ck1 CHECK ( UtenteID != FollowerID)
);

--Preferiti(UtenteID, FileID, DataCreazione)
CREATE TABLE Preferiti(
    UtenteID integer NOT NULL,
    FileID integer NOT NULL,
    DataCreazione date NOT NULL,
    CONSTRAINT pre_pk PRIMARY KEY (UtenteID, FileID),
    CONSTRAINT pre_fk1 FOREIGN KEY (UtenteID)
        REFERENCES Utente (ID) ON DELETE CASCADE,
    CONSTRAINT pre_fk2 FOREIGN KEY (FileID)
        REFERENCES Files (ID) ON DELETE CASCADE
);

--Membership(UtenteID, GruppoID, DataCreazione)
CREATE TABLE Membership(
    UtenteID integer NOT NULL,
    GruppoID integer NOT NULL,
    DataCreazione date NOT NULL,
    CONSTRAINT mem_pk PRIMARY KEY (UtenteID, GruppoID),
    CONSTRAINT mem_fk1 FOREIGN KEY (UtenteID)
        REFERENCES Utente (ID) ON DELETE CASCADE,
    CONSTRAINT mem_fk2 FOREIGN KEY (GruppoID)
        REFERENCES Gruppo (ID) ON DELETE CASCADE
);

--Tag(ID, Testo)
CREATE TABLE Tag(
    ID integer NOT NULL,
    Testo varchar2(30) NOT NULL,
    CONSTRAINT tag_pk PRIMARY KEY (ID),
);

--TagsFile(TagID, FileID)
CREATE TABLE TagsFile(
    TagID integer NOT NULL,
    FileID integer NOT NULL,
    CONSTRAINT tgs_pk PRIMARY KEY (TagID, FileID),
    CONSTRAINT tgs_fk1 FOREIGN KEY (TagID)
        REFERENCES Tag (ID) ON DELETE CASCADE,
    CONSTRAINT tgs_fk2 FOREIGN KEY (FileID)
        REFERENCES Files (ID) ON DELETE CASCADE
);

--TagsPepole(TagID, FileID)
CREATE TABLE TagsPepole(
    UtenteID integer NOT NULL,
    FileID integer NOT NULL,
    CONSTRAINT tgp_pk PRIMARY KEY (UtenteID, FileID),
    CONSTRAINT tgp_fk1 FOREIGN KEY (UtenteID)
        REFERENCES Utente (ID) ON DELETE CASCADE,
    CONSTRAINT tgp_fk2 FOREIGN KEY (FileID)
        REFERENCES Files (ID) ON DELETE CASCADE
);

--Visualizza(ID, UtenteID, DiscussioneID, FileID, Data)
CREATE TABLE Visualizza(
    ID integer NOT NULL,
    UtenteID integer NOT NULL,
    DiscussioneID integer,
    FileID integer,
    Data date NOT NULL,
    CONSTRAINT vis_pk PRIMARY KEY (ID),
    CONSTRAINT vis_fk1 FOREIGN KEY (UtenteID)
        REFERENCES Utente (ID) ON DELETE CASCADE,
    CONSTRAINT vis_fk2 FOREIGN KEY (DiscussioneID)
        REFERENCES Discussione (ID) ON DELETE CASCADE,
    CONSTRAINT vis_fk3 FOREIGN KEY (FileID)
        REFERENCES Files (ID) ON DELETE CASCADE,
    CONSTRAINT vis_ck1 CHECK ((DiscussioneID is null AND FileID is not null) OR
                              (DiscussioneID is not null AND FileID is null))
);