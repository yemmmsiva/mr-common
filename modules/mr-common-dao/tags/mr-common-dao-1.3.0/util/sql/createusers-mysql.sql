/*
 *  Script de creación del usuario necesario para la conexión con la BBDD MySQL.
 */


CREATE USER admincommon IDENTIFIED BY 'admin';
CREATE USER admincommon@localhost IDENTIFIED BY 'admin';
GRANT ALL
ON test_mr_common.*
TO admincommon;
