Crear usuarios sql:

	
create user 'libros'@'localhost' identified by 'libros'; grant all privileges on librosdb.* to 'libros'@'localhost'; flush privileges;
exit;

create user 'realm'@'localhost' identified by 'realm'; grant all privileges on realmdb.* to 'realm'@'localhost'; flush privileges; 
exit;


Cargar bases de datos:

mysql -u libros -p libros source (path relativo)/librosdb-schema.sql;
source (path relativo)/librosdb-data.sql;
exit;

mysql -u realm -p realm source (path relativo)/realmdb-schema.sql;
source (path relativo)/realmdb-data.sql; exit;
exit;


Hacer maven build package libros-api 

Cargar en tomcat el .war de libros-api

    
Hacer peticiones con POSTMAN con la uri base http://localhost:8080/libros-api
