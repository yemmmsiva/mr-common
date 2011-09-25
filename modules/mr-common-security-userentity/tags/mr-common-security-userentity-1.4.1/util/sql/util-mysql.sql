/*
 *  Queries utiles para la BBDD MySQL.
 */


/*
 * Lista todos los usuarios con sus roles de aplicación.
 */
select u.username, r.code roleName
 from systemuser u join authority au on u.id = au.userId
      join role r on au.roleId = r.id
 where /* u.username = 'admin' and */
     u.deleted = 0
     and au.deleted = 0
 order by u.username, r.code;


/*
 * Lista todos los usuarios - organizaciones.
 */
select u.username, o.name orgName
 from usersorgs uo join organization o on uo.organizationId = o.id
     join systemuser u on uo.userId = u.id
 where /* u.username = 'user11' and */
      u.deleted = 0
      and uo.deleted = 0
 order by u.username, o.name;


/*
 * Lista todos los usuarios - organizaciones - roles de organización.
 */
select u.username, o.name orgName, r.code roleName
 from usersorgsrole uor join usersorgs uo on uor.usersorgsId = uo.id
     join organization o on uo.organizationId = o.id
     join systemuser u on uo.userId = u.id
     join role r on uor.roleId = r.id
 where /* u.username = 'admin' and */
      u.deleted = 0
      and uor.deleted = 0
 order by u.username, o.name, r.code;
