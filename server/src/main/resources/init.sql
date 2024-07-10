create SCHEMA IF NOT EXISTS users;
USE users;

drop table IF EXISTS user_table;
create TABLE user_table(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
login VARCHAR (100),
password VARCHAR(100),
nickname VARCHAR(100),
role VARCHAR(100));

insert into user_table(login, password, nickname, role)values('login0', 'pass0','nick0', 'ADMIN');
insert into user_table(login, password, nickname, role)values('login1', 'pass1','nick1', 'USER');
insert into user_table(login, password, nickname, role)values('login2', 'pass2','nick2', 'USER');
insert into user_table(login, password, nickname, role)values('login3', 'pass3','nick3', 'USER');
insert into user_table(login, password, nickname, role)values('login4', 'pass4','nick4', 'USER');
insert into user_table(login, password, nickname, role)values('login5', 'pass5','nick5', 'USER');
insert into user_table(login, password, nickname, role)values('login6', 'pass6','nick6', 'USER');
insert into user_table(login, password, nickname, role)values('login7', 'pass7','nick7', 'USER');
insert into user_table(login, password, nickname, role)values('login8', 'pass8','nick8', 'USER');
insert into user_table(login, password, nickname, role)values('login9', 'pass9','nick9', 'USER');