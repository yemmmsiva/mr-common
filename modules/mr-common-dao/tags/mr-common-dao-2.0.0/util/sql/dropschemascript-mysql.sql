/*
 *  Script de borrado del tablaspace y del usuario/esquema usado en los test
 *  con la BBDD MySQL.
 */

DROP USER admincommon;
DROP DATABASE /*! IF EXISTS */ test_mr_common;
