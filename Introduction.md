# Introduction to mr-common #



mr-common is a **Java SE/EE framework**. Was develop with [Apache common's](http://commons.apache.org/), [Spring](http://www.springsource.org/) (Core, DAO, Security, AOP, MVC), [Hibernate](http://www.hibernate.org/), and [Birt Report](http://www.eclipse.org/birt/) frameworks.

Can be linked to develop Java SE, EE, or web applications.


## Details ##

The framework is divided into modules (jars):
  * [mr-common-core](CommonCore.md)
  * [mr-common-dao](CommonDao.md)
  * [mr-common-security](CommonSecurity.md)
  * [mr-common-security-userentity](CommonSecurityUserEntity.md)
  * [mr-common-web](CommonWeb.md)
  * [mr-common-birt](CommonBirt.md)

Each module is compiled with [Apache Maven 2](http://maven.apache.org/).

## Example template ##
An **application example** is [here](http://apps.mrdev.com.ar/mr-common/). _User_: `admin`, _password_: `admin`. Read more about the template [here](MrCommonTemplate.md).

A configuration example of each modules is in folder `utils` on the root.

## Dependencies modules ##

See the diagram of dependencies.

![http://mr-common.googlecode.com/files/mr-common-modules.png](http://mr-common.googlecode.com/files/mr-common-modules.png)


## Requirements ##

Java SE 1.5 (recommended 1.6), Maven 2.0.6. For web applications: Servlet 2.4, JSP 2.0 (for Tomcat Servers, required 5.5, recommended 6.0).


## Maven configuration ##

Example of modules dependency of mr-common jars in **pom.xml**:

```
    <dependencies>
        <dependency>
            <groupId>mr.common</groupId>
            <artifactId>mr-common-core</artifactId>
            <version>2.0.0</version>
        </dependency>
        <dependency>
            <groupId>mr.common</groupId>
            <artifactId>mr-common-dao</artifactId>
            <version>2.0.0</version>
        </dependency>
        <dependency>
            <groupId>mr.common</groupId>
            <artifactId>mr-common-security</artifactId>
            <version>2.0.0</version>
        </dependency>
        <dependency>
            <groupId>mr.common</groupId>
            <artifactId>mr-common-security-userentity</artifactId>
            <version>2.0.0</version>
        </dependency>
        <dependency>
            <groupId>mr.common</groupId>
            <artifactId>mr-common-web</artifactId>
            <version>2.0.0</version>
        </dependency>
        <dependency>
            <groupId>mr.common</groupId>
            <artifactId>mr-common-birt</artifactId>
            <version>2.0.0</version>
        </dependency>
    </dependencies>
```


## Maven repository ##

A Maven 2 repository with the jar modules are available [here](http://repo.mrdev.com.ar/artifactory).

Configure example of repo in the pom.xml:

```
	<repositories>
		<repository>
			<id>mrdev-releases</id>
			<url>http://repo.mrdev.com.ar/maven2</url>
		</repository>
	</repositories>
```

See the [pom.xml](http://code.google.com/p/mr-common/source/browse/modules/mr-common-security-userentity/trunk/pom.xml) file of the _mr-common-security-userentity_ as example.

A local m2 repository folder zipped are archive to download [here](http://mr-common.googlecode.com/files/repository-2.0.zip), with all dependencies.


## About me ##

My name is **Mariano Ruiz**, I work as _Java & Web Developer_. I'm from Argentina.

**My Home Page**: http://www.mrdev.com.ar

**My Email**: [marianoruiz@mrdev.com.ar](mailto:marianoruiz@mrdev.com.ar)