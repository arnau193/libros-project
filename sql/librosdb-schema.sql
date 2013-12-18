drop database if exists librosdb;
create database librosdb;

use librosdb;

create table users (
        username		varchar(20) not null primary key,
        name			varchar(20) not null,
		email		 	varchar(255) not null
);

create table libros (
	idlibro				int not null auto_increment primary key,
	titulo 				varchar (40) not null,
	autor 				varchar(80) not null,
	lengua				varchar(100) not null,
	edicion				integer not null,
	fecha_edicion		date,
	fecha_impresion		date,
	editorial			varchar(40) not null
);


create table resenas (	
	idresena			int not null auto_increment primary key,
	idlibro				int not null,	
	username 			varchar (20) not null,
	texto				varchar(500) not null,
	fecha_creacion		timestamp,
	foreign key(username) references users(username),
	foreign key(idlibro) references libros(idlibro)
);