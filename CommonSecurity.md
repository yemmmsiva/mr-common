# mr-common-security #

This module is the **security layer** support. Provide a set of interfaces to user managment and login access, but must be implemented by other modules.

It also provides some useful classes for [Spring Security](http://static.springframework.org/spring-security/).

A implementation of this is [mr-common-security-userentity](CommonSecurityUserEntity.md).



## Classes ##

This is the diagram of the most important classes:

![http://mr-common.googlecode.com/files/mr-common-security.png](http://mr-common.googlecode.com/files/mr-common-security.png)


## Spring Security classes ##

Implementations of the **Spring Security framework** interfaces:

![http://mr-common.googlecode.com/files/mr-common-security-springsupport.png](http://mr-common.googlecode.com/files/mr-common-security-springsupport.png)


## Organizations ##

Optional, the module also support user organizations. An organization can contains many users, and users can be a part of one or more organizations.

The model class for an organization is **`mr.common.security.organization.model.Organization`**, and the service class is **`mr.common.security.organization.service.OrganizationService`** (implemented in [mr-common-security-userentity](CommonSecurityUserEntity.md)).


_This module is a part of **[mr-common](Introduction.md)** framework_.