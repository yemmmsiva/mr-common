/*
 *  Script de creación de la BBDD de test para Oracle.
 *  Ejecutar luego de crear el tablaspace y el esquema con el
 *  script de creación de usuario de Oracle.
 */


/*
 *  Secuencia unica Hibernate para las tablas.
 */
CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 NOCYCLE CACHE 100;


CREATE TABLE baseentityexample (
  id						NUMBER(16)			NOT NULL,
  version					INTEGER,
  comentario				VARCHAR2(50 CHAR),
  CONSTRAINT pk_baseentityexample PRIMARY KEY (id)
);
