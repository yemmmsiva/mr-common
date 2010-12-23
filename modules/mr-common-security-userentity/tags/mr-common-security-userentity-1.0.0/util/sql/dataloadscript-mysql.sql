/*
 *  Ejemplo de script de datos de la BBDD para MySQL.
 */

# Iniciamos transacción
start transaction;

insert into userdata(id, commonName, description)
  values(1, 'Administrador', 'Master de la aplicación');
insert into userdata(id, commonName, description)
  values(2, 'User 11', 'Usuario cliente');


insert into systemuser(username, emailAddress, password, userDataId)
  values('admin', 'marianoruiz@mrdev.com.ar', MD5('admin'), 1);
insert into systemuser(username, emailAddress, password, userDataId)
  values('user11', 'user11@mrdev.com.ar', MD5('user11'), 2);


insert into role(code, description, largeDescription)
  values('ROLE_ADMIN', 'Administrador', 'Administrador de la aplicación');
insert into role(code, description, largeDescription)
  values('ROLE_USER', 'Usuario', 'Usuario de la aplicación');

  
insert into authority(userId, roleId)
  values((select id from systemuser where username = 'admin'),
          (select id from role where code = 'ROLE_ADMIN'));
insert into authority(userId, roleId)
  values((select id from systemuser where username = 'user11'),
          (select id from role where code = 'ROLE_USER'));


# Finalizamos transacción
commit;
#rollback;
