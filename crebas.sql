/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     04/05/2017 11:32:58                          */
/*==============================================================*/


alter table CASELLA
   drop constraint FK_CASELLA_PERTANYER_SUDOKU;

alter table SUDOKU
   drop constraint FK_SUDOKU_TENIR_JUGADOR;

drop index PERTANYER_FK;

drop table CASELLA cascade constraints;

drop table JUGADOR cascade constraints;

drop index TENIR_FK;

drop table SUDOKU cascade constraints;

/*==============================================================*/
/* Table: CASELLA                                               */
/*==============================================================*/
create table CASELLA  (
   IDSUDOKU             NUMBER(3)                       not null,
   COORX                NUMBER(1)                       not null,
   COORY                NUMBER(1)                       not null,
   VALOR                NUMBER(1),
   EDITABLE             NUMBER(1),
   CASELLA              NUMBER(2),
   constraint PK_CASELLA primary key (IDSUDOKU, COORX, COORY)
);

/*==============================================================*/
/* Index: PERTANYER_FK                                          */
/*==============================================================*/
create index PERTANYER_FK on CASELLA (
   IDSUDOKU ASC
);

/*==============================================================*/
/* Table: JUGADOR                                               */
/*==============================================================*/
create table JUGADOR  (
   NOMJUAGDOR           VARCHAR2(25)                    not null,
   ESTAJUGANT           NUMBER(1),
   constraint PK_JUGADOR primary key (NOMJUAGDOR)
);

/*==============================================================*/
/* Table: SUDOKU                                                */
/*==============================================================*/
create table SUDOKU  (
   NOMJUAGDOR           VARCHAR2(25)                    not null,
   IDSUDOKU             NUMBER(3)                       not null,
   DATACREACIO          TIMESTAMP,
   constraint PK_SUDOKU primary key (IDSUDOKU)
);

/*==============================================================*/
/* Index: TENIR_FK                                              */
/*==============================================================*/
create index TENIR_FK on SUDOKU (
   NOMJUAGDOR ASC
);

alter table CASELLA
   add constraint FK_CASELLA_PERTANYER_SUDOKU foreign key (IDSUDOKU)
      references SUDOKU (IDSUDOKU);

alter table SUDOKU
   add constraint FK_SUDOKU_TENIR_JUGADOR foreign key (NOMJUAGDOR)
      references JUGADOR (NOMJUAGDOR);

