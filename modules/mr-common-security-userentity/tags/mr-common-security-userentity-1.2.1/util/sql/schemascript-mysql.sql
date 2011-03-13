/*
 *  Script de creación de la BBDD para MySQL.
 */

/*
 * General user data.
 */
CREATE TABLE userdata (
  id						INTEGER(16)		NOT NULL /*! AUTO_INCREMENT */,
  version					INTEGER,
  firstName					VARCHAR(50)		DEFAULT NULL,
  lastName					VARCHAR(50)		DEFAULT NULL,
  commonName				VARCHAR(50)		DEFAULT NULL,
  telephoneNumber			VARCHAR(50)		DEFAULT NULL,
  description				VARCHAR(500)	DEFAULT NULL,
  postalAddress				VARCHAR(500)	DEFAULT NULL,
  postalCode				VARCHAR(20)		DEFAULT NULL,
  cityOrRegionName			VARCHAR(200)	DEFAULT NULL,
  stateOrProvinceName		VARCHAR(50)		DEFAULT NULL,
  countryId					CHAR(3)			DEFAULT NULL,
  male						BOOLEAN			DEFAULT NULL,
  org						BOOLEAN			DEFAULT NULL,
  timeZoneId				VARCHAR(50)		DEFAULT NULL,
  birthdayDate				DATE			DEFAULT NULL,
  portraitId				INTEGER(16)		DEFAULT NULL,

  owner						VARCHAR(50)		NOT NULL DEFAULT 'APP',
  created					TIMESTAMP		NOT NULL,
  lastupdater				VARCHAR(50),
  lastupdate				TIMESTAMP		NULL DEFAULT NULL,
  deleteduser				VARCHAR(50),
  deleteddate				TIMESTAMP		NULL DEFAULT NULL,
  deleted					INTEGER(1)		DEFAULT 0 NOT NULL,
  PRIMARY KEY (id)
) /*! ENGINE = InnoDB */;


/*
 * System users.
 */
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
ALTER TABLE systemuser ADD INDEX usernameIndex(username);
ALTER TABLE systemuser ADD INDEX emailAddressIndex(emailAddress);


/*
 * System roles of the users. 
 */
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
ALTER TABLE role ADD INDEX codeIndex(code);


/*
 * Users *-* roles.
 */
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
