drop database if exists vulnesolve;
create database vulnesolve;

use vulnesolve;

drop table if exists producto;
create table producto(
	id     int            primary key auto_increment,
    nombre varchar(255)   not null,
    marca  varchar(255)   not null,
    precio decimal(10, 2) not null
);


