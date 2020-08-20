create table users_table(id VARCHAR(36), last_name VARCHAR(30), first_name VARCHAR(30), PRIMARY KEY (id)) ENGINE=INNODB;

insert into users_table(id, last_name, first_name) values (666,'Wurst','Conchita')