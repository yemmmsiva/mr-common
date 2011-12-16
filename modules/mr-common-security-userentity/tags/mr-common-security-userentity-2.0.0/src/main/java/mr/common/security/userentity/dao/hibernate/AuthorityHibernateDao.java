package mr.common.security.userentity.dao.hibernate;

import mr.common.dao.hibernate3.AbstractHibernateAuditableDao;
import mr.common.security.userentity.dao.AuthorityDao;
import mr.common.security.userentity.model.Authority;

import org.springframework.stereotype.Repository;


/**
 * Implementación de {@link mr.common.security.userentity.dao.AuthorityDao AuthorityDao}.
 * @author Mariano Ruiz
 */
@Repository
public class AuthorityHibernateDao extends
		AbstractHibernateAuditableDao<Authority> implements AuthorityDao {

}
