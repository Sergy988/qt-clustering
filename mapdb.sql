
drop database if exists MapDB;

create database MapDB;

create user 'MapUser'@'localhost' identified by 'map';

grant create, select, insert, delete on MapDB.* to MapUser@localhost identified by 'map';

use MapDB;

create table playtennis(
    outlook varchar(10),
    temperature float(5,2),
    umidity varchar(10),
    wind varchar(10),
    play varchar(10)
);

insert into playtennis values('sunny',30.3,'high','weak','no');
insert into playtennis values('sunny',30.3,'high','strong','no');
insert into playtennis values('overcast',30.0,'high','weak','yes');
insert into playtennis values('rain',13.0,'high','weak','yes');
insert into playtennis values('rain',0.0,'normal','weak','yes');
insert into playtennis values('rain',0.0,'normal','strong','no');
insert into playtennis values('overcast',0.1,'normal','strong','yes');
insert into playtennis values('sunny',13.0,'high','weak','no');
insert into playtennis values('sunny',0.1,'normal','weak','yes');
insert into playtennis values('rain',12.0,'normal','weak','yes');
insert into playtennis values('sunny',12.5,'normal','strong','yes');
insert into playtennis values('overcast',12.5,'high','strong','yes');
insert into playtennis values('overcast',29.21,'normal','weak','yes');
insert into playtennis values('rain',12.5,'high','strong','no');
insert into playtennis values('rain',5.0,'normal','strong','no');
insert into playtennis values('sunny',28.0,'high','weak','yes');

create table test(
    discrete varchar(10),
    continuous float(5, 2)
);

insert into test values('A', 1.0);
insert into test values('B', 1.5);
insert into test values('C', 2.0);

