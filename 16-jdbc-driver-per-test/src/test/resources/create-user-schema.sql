create table USERS
(
   ID         VARCHAR(36) CONSTRAINT users_pk PRIMARY KEY,
   LAST_NAME  VARCHAR(30),
   FIRST_NAME VARCHAR(30)
);

insert into USERS(ID, LAST_NAME, FIRST_NAME)
values ('666', 'Wurst', 'Conchita')