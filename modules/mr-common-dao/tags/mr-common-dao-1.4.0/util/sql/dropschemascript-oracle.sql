/*
 *  Script de borrado del tablaspace y del usuario/esquema usado en los test
 *  con la BBDD Oracle.
 */


DROP USER admincommon CASCADE;
DROP TABLESPACE TEST_MR_COMMON INCLUDING CONTENTS CASCADE CONSTRAINTS;
