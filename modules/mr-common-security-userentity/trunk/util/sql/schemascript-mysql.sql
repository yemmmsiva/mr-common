/*
 *  Script de creación de la BBDD para MySQL.
 */


CREATE TABLE userdata (
  id						INTEGER(16)		NOT NULL /*! AUTO_INCREMENT */,
  version					INTEGER,
  givenName					VARCHAR(50),
  surname					VARCHAR(50),
  commonName				VARCHAR(50),
  telephoneNumber			VARCHAR(50),
  description				VARCHAR(200),
  postalAddress				VARCHAR(50),
  postalCode				VARCHAR(20),
  mail						VARCHAR(50),
  countryName				VARCHAR(50),
  stateOrProvinceName		VARCHAR(50),
  gender					VARCHAR(10),
  org						INTEGER(1),

  owner						VARCHAR(50)		NOT NULL DEFAULT 'APP',
  created					TIMESTAMP		NOT NULL,
  lastupdater				VARCHAR(50),
  lastupdate				TIMESTAMP		NULL DEFAULT NULL,
  deleteduser				VARCHAR(50),
  deleteddate				TIMESTAMP		NULL DEFAULT NULL,
  deleted					INTEGER(1)		DEFAULT 0 NOT NULL,
  PRIMARY KEY (id)
) /*! ENGINE = InnoDB */;


CREATE TABLE systemuser (
  id						INTEGER(16)		NOT NULL /*! AUTO_INCREMENT */,
  version					INTEGER,
  username					VARCHAR(50)		NOT NULL,
  emailAddress				VARCHAR(50)		NOT NULL,
  password					VARCHAR(50)		NOT NULL,
  enabled					INTEGER(1)		DEFAULT 1 NOT NULL,
  userDataId				INTEGER(16),

  owner						VARCHAR(50)		NOT NULL DEFAULT 'APP',
  created					TIMESTAMP		NOT NULL,
  lastupdater				VARCHAR(50),
  lastupdate				TIMESTAMP		NULL DEFAULT NULL,
  deleteduser				VARCHAR(50),
  deleteddate				TIMESTAMP		NULL DEFAULT NULL,
  deleted					INTEGER(1)		DEFAULT 0 NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_userDataId FOREIGN KEY (userDataId) REFERENCES userdata(id)
) /*! ENGINE = InnoDB */;


CREATE TABLE role (
  id						INTEGER(16)		NOT NULL /*! AUTO_INCREMENT */,
  version					INTEGER,
  code						VARCHAR(20),
  description				VARCHAR(250)	NOT NULL,
  largeDescription			VARCHAR(1000),

  owner						VARCHAR(50)		NOT NULL DEFAULT 'APP',
  created					TIMESTAMP		NOT NULL,
  lastupdater				VARCHAR(50),
  lastupdate				TIMESTAMP		NULL DEFAULT NULL,
  deleteduser				VARCHAR(50),
  deleteddate				TIMESTAMP		NULL DEFAULT NULL,
  deleted					INTEGER(1)		DEFAULT 0 NOT NULL,
  PRIMARY KEY (id)
) /*! ENGINE = InnoDB */;


CREATE TABLE authority (
  id						INTEGER(16)		NOT NULL /*! AUTO_INCREMENT */,
  version					INTEGER,
  userId					INTEGER(16)		NOT NULL,
  roleId					INTEGER(16)		NOT NULL,

  owner						VARCHAR(50)		NOT NULL DEFAULT 'APP',
  created					TIMESTAMP		NOT NULL,
  lastupdater				VARCHAR(50),
  lastupdate				TIMESTAMP		NULL DEFAULT NULL,
  deleteduser				VARCHAR(50),
  deleteddate				TIMESTAMP		NULL DEFAULT NULL,
  deleted					INTEGER(1)		DEFAULT 0 NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_userId FOREIGN KEY (userId) REFERENCES systemuser(id),
  CONSTRAINT FK_roleId FOREIGN KEY (roleId) REFERENCES role(id)
) /*! ENGINE = InnoDB */;
