package mr.common.security.userentity.dao.hibernate;

import mr.common.dao.hibernate3.AbstractHibernateAuditableDao;
import mr.common.security.userentity.dao.UserDataDao;
import mr.common.security.userentity.model.UserData;

import org.springframework.stereotype.Repository;


/**
 * Implementación de {@link mr.common.security.userentity.dao.UserDataDao}.
 * @author Mariano Ruiz
 */
@Repository
public class UserDataHibernateDao extends
		AbstractHibernateAuditableDao<UserData> implements UserDataDao {

}
