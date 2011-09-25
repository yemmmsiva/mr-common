/*
 *  Script de creación de la BBDD de test para MySQL.
 */



CREATE DATABASE /*! IF NOT EXISTS */ test_mr_common /*! DEFAULT CHARACTER SET utf8 */;

USE test_mr_common;


CREATE TABLE auditableentityexample (
  id						INTEGER(16)		NOT NULL /*! AUTO_INCREMENT */,
  comentario				VARCHAR(50),

  version					INTEGER			DEFAULT 0 NOT NULL,
  owner						VARCHAR(50)		NOT NULL DEFAULT 'APP',
  created					TIMESTAMP		NOT NULL,
  lastupdater				VARCHAR(50),
  lastupdate				TIMESTAMP		NULL DEFAULT NULL,
  deleteduser				VARCHAR(50),
  deleteddate				TIMESTAMP		NULL DEFAULT NULL,
  deleted					INTEGER(1)		DEFAULT 0 NOT NULL,
  PRIMARY KEY (id)
) /*! ENGINE = InnoDB */;
