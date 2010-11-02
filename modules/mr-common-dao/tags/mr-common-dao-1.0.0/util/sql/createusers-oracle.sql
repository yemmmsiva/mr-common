/*
 *  Script de creación del tablaspace y del usuario/esquema usado en los test
 *  con la BBDD Oracle.
 */


CREATE TABLESPACE TEST_MR_COMMON
    LOGGING
    DATAFILE 'test_mr_common.dbf' SIZE 100M REUSE
    EXTENT MANAGEMENT LOCAL
    SEGMENT SPACE MANAGEMENT AUTO;

CREATE USER admincommon IDENTIFIED BY "admin"
    DEFAULT TABLESPACE "TEST_MR_COMMON"
    QUOTA UNLIMITED ON "TEST_MR_COMMON";

GRANT CREATE SEQUENCE TO admincommon;
GRANT CREATE SESSION TO admincommon;
GRANT CREATE TABLE TO admincommon;
GRANT CREATE ANY VIEW TO admincommon;
GRANT CREATE PROCEDURE TO admincommon;
