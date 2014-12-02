CREATE TABLE  "MARCA" 
   (	"CODICE" NUMBER, 
	"DESCRIZIONE" VARCHAR2(255), 
	"SEDE" VARCHAR2(50), 
	 CONSTRAINT "MARCA_PK" PRIMARY KEY ("CODICE") ENABLE
   )
/
CREATE TABLE  "MODELLO" 
   (	"CODICE" NUMBER, 
	"MARCACODICE" NUMBER, 
	"DESCRIZIONE" VARCHAR2(255), 
	"DAPERIODO" VARCHAR2(30), 
	"APERIODO" VARCHAR2(30), 
	 CONSTRAINT "MODELLO_PK" PRIMARY KEY ("CODICE") ENABLE, 
	 CONSTRAINT "MODELLO_CON" FOREIGN KEY ("MARCACODICE")
	  REFERENCES  "MARCA" ("CODICE") ENABLE
   )
/
CREATE TABLE  "ALLESTIMENTO" 
   (	"CODICE" NUMBER, 
	"MODELLOCODICE" NUMBER, 
	"DESCRIZIONE" VARCHAR2(255), 
	"DAPERIODO" NUMBER, 
	"APERIODO" NUMBER, 
	 CONSTRAINT "ALLESTIMENTO_PK" PRIMARY KEY ("CODICE") ENABLE, 
	 CONSTRAINT "ALLESTIMENTO_CON" FOREIGN KEY ("MODELLOCODICE")
	  REFERENCES  "MODELLO" ("CODICE") ENABLE
   )
/

CREATE TABLE  "CARATTERISTICA" 
   (	"CODICE" NUMBER, 
	"DESCRIZIONE" VARCHAR2(30), 
	 CONSTRAINT "CARATTERISTICA_PK" PRIMARY KEY ("CODICE") ENABLE
   )
/
CREATE TABLE  "CARATTERISTICAALLESTIMENTO" 
   (	"ALLESTIMENTOCODICE" NUMBER, 
	"CARATTERISTICACODICE" NUMBER, 
	"VALORE" VARCHAR2(255), 
	 CONSTRAINT "CARATTERISTICAALLESTIMENTO_CON" PRIMARY KEY ("ALLESTIMENTOCODICE", "CARATTERISTICACODICE") ENABLE, 
	 CONSTRAINT "CARATTERISTICAALLEST_CON2" FOREIGN KEY ("ALLESTIMENTOCODICE")
	  REFERENCES  "ALLESTIMENTO" ("CODICE") ENABLE, 
	 CONSTRAINT "CARATTERISTICAALLEST3" FOREIGN KEY ("CARATTERISTICACODICE")
	  REFERENCES  "CARATTERISTICA" ("CODICE") ENABLE
   )
/

/*CREATE UNIQUE INDEX  "ALLESTIMENTO_PK" ON  "ALLESTIMENTO" ("CODICE")
/
CREATE UNIQUE INDEX  "CARATTERISTICAALLESTIMENTO_CON" ON  "CARATTERISTICAALLESTIMENTO" ("ALLESTIMENTOCODICE", "CARATTERISTICACODICE")
/
CREATE UNIQUE INDEX  "CARATTERISTICA_PK" ON  "CARATTERISTICA" ("CODICE")
/
CREATE UNIQUE INDEX  "MARCA_PK" ON  "MARCA" ("CODICE")
/
CREATE UNIQUE INDEX  "MODELLO_PK" ON  "MODELLO" ("CODICE")
*/


CREATE OR REPLACE FORCE VIEW  "VCARATTERISTICAALLESTIMENTO" ("CARATTERISTICACODICE", "CARATTERISTICA", "VALORE", "ALLESTIMENTOCODICE") AS 
  select caratteristicacodice,caratteristica.descrizione as caratteristica, valore, allestimentocodice from caratteristicaallestimento inner join caratteristica on caratteristicacodice=caratteristica.codice
/
CREATE OR REPLACE FORCE VIEW  "VDETTAGLIOALLESTIMENTO" ("CODMAR", "MARCADESCRIZIONE", "MODELLODESCRIZIONE", "CODALL", "ALLESTIMENTODESCRIZIONE", "DAPERIODO", "APERIODO", "CARATTERISTICADESCRIZIONE", "VALORE") AS 
  select marca.codice,marca.descrizione,
       modello.descrizione,
       allestimento.codice,
       allestimento.descrizione,
       allestimento.daperiodo, allestimento.aperiodo,
       caratteristica.descrizione,
       ca.valore
from marca 
     inner join modello 
         on marca.codice=modello.codice
     inner join allestimento 
         on modello.codice=allestimento.modellocodice
     left outer join caratteristicaallestimento ca 
         on allestimento.codice=ca.allestimentocodice
     inner join caratteristica 
         on ca.caratteristicacodice=caratteristica.codice
/
CREATE OR REPLACE FORCE VIEW  "VMODELLO" ("CODICE", "MARCA", "DESCRIZIONE", "DAPERIODO", "APERIODO", "MARCACODICE") AS 
  select modello.codice,marca.descrizione as marca,modello.descrizione,DaPeriodo,APeriodo,marcacodice from modello inner join marca on marca.codice=marcacodice
/
CREATE OR REPLACE FORCE VIEW  "VALLESTIMENTO" ("CODICE", "MARCA", "MODELLO", "DESCRIZIONE", "DAPERIODO", "APERIODO", "MARCACODICE", "MODELLOCODICE") AS 
  select allestimento.codice,marca,vmodello.descrizione as modello,allestimento.descrizione,allestimento.daperiodo,allestimento.aperiodo,marcacodice,modellocodice
from allestimento inner join vmodello on allestimento.modellocodice=vmodello.codice
/
