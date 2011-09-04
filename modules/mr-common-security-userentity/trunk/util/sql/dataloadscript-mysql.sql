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
          (select id from role where code = 'ROLE_ADMIN' and deleted = 0));
insert into authority(userId, roleId)
  values((select id from systemuser where username = 'user11'),
          (select id from role where code = 'ROLE_USER' and deleted = 0));

insert into organization(name, description)
  values('root', 'Users Administrator of the Application');

insert into usersorgs(organizationId, userId)
  values((select id from organization where name = 'root' and deleted = 0),
          (select id from systemuser where username = 'admin' and deleted = 0));

insert into usersorgsrole(usersorgsId, roleId)
  values((select id from usersorgs
             where organizationId = (select id from organization where name = 'root' and deleted = 0)
             and userId = (select id from systemuser where username = 'admin' and deleted = 0)),
          (select id from role where code = 'ROLE_ADMIN' and deleted = 0));


# Finalizamos transacción
commit;
#rollback;
