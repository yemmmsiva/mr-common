# mr-common-dao #

This modules support the **persistent layer**, built on top of the [Hibernate](http://www.hibernate.org/) framework and [Spring](http://www.springsource.org/) DAO support.

The plus of this modules is the **audit support**. All record of the database are audited.


## Details ##
The audit information is:
  * Logical delete.
  * The user who created the record and when he did.
  * The last user who edited the registry and when it did.
  * The last user who deleted the registry and when it did.
  * Versioning for optimistic locking (see [@Version](http://www.intertech.com/Blog/post/Versioning-Optimistic-Locking-in-Hibernate.aspx)).


## Classes ##
This is an example of how to **model entities** are: In the example we have a class `Person`, who was born in a `Country`. All model classes are audited.

![http://mr-common.googlecode.com/files/mr-common-dao-model.png](http://mr-common.googlecode.com/files/mr-common-dao-model.png)

The next diagram is the **DAO** classes for Person entity.

![http://mr-common.googlecode.com/files/mr-common-dao-dao.png](http://mr-common.googlecode.com/files/mr-common-dao-dao.png)

A good code example of how model entities, are the entities in the module [mr-common-security-userentity](CommonSecurityUserEntity.md), see the class `mr.common.security.userentity.model.UserEntity` and the related DAO classes.

_This module is a part of **[mr-common](Introduction.md)** framework_.