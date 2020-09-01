create table users_table
(
   id         VARCHAR(36) CONSTRAINT users_pk PRIMARY KEY,
   last_name  VARCHAR(30),
   first_name VARCHAR(30)
);

insert into users_table(id, last_name, first_name)
values ('666', 'Wurst', 'Conchita')