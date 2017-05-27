/*==============================================================*/
/* DBMS name: ORACLE Version 10g */
/* Created on: 16/05/2017 10:50:25 */
/*==============================================================*/


alter table CASELLA
drop constraint FK_CASELLA_TE_SUDOKU;

alter table SUDOKU
drop constraint FK_SUDOKU_GUARDA_JUGADOR;

drop index TE_FK;

drop table CASELLA cascade constraints;

drop table JUGADOR cascade constraints;

drop index GUARDA_FK;

drop table SUDOKU cascade constraints;

/*==============================================================*/
/* Table: CASELLA */
/*==============================================================*/
create table CASELLA (
NOMJUGADOR VARCHAR2(25) not null,
IDSUDOKU NUMBER(3) not null,
COORX NUMBER(1) not null,
COORY NUMBER(1) not null,
VALOR NUMBER(1),
EDITABLE NUMBER(1),
constraint PK_CASELLA primary key (NOMJUGADOR, IDSUDOKU, COORX, COORY),
constraint CHK_COOR CHECK (COORX BETWEEN 0 AND 8 AND COORY BETWEEN 0 AND 8),
constraint CHK_VALOR CHECK (VALOR BETWEEN 1 AND 9),
constraint CHK_EDITABLE CHECK (EDITABLE BETWEEN 0 AND 1)
);

/*==============================================================*/
/* Index: TE_FK */
/*==============================================================*/
create index TE_FK on CASELLA (
NOMJUGADOR ASC,
IDSUDOKU ASC
);

/*==============================================================*/
/* Table: JUGADOR */
/*==============================================================*/
create table JUGADOR (
NOMJUGADOR VARCHAR2(25) not null,
ESTAJUAGNT INTEGER,
constraint PK_JUGADOR primary key (NOMJUGADOR),
constraint CHK_ESTAJUAGNT CHECK (ESTAJUAGNT BETWEEN 0 AND 1)
);

/*==============================================================*/
/* Table: SUDOKU */
/*==============================================================*/
create table SUDOKU (
NOMJUGADOR VARCHAR2(25) not null,
IDSUDOKU NUMBER(3) not null,
DATACREACIO TIMESTAMP,
constraint PK_SUDOKU primary key (NOMJUGADOR, IDSUDOKU),
constraint IDSUDOKU CHECK (IDSUDOKU<1000)
);

/*==============================================================*/
/* Index: GUARDA_FK */
/*==============================================================*/
create index GUARDA_FK on SUDOKU (
NOMJUGADOR ASC
);

alter table CASELLA
add constraint FK_CASELLA_TE_SUDOKU foreign key (NOMJUGADOR, IDSUDOKU)
references SUDOKU (NOMJUGADOR, IDSUDOKU);

alter table SUDOKU
add constraint FK_SUDOKU_GUARDA_JUGADOR foreign key (NOMJUGADOR)
references JUGADOR (NOMJUGADOR);
