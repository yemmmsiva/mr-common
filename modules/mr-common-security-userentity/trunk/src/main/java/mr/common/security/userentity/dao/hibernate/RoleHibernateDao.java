package mr.common.security.userentity.dao.hibernate;

import mr.common.dao.hibernate3.AbstractHibernateAuditableDictionaryDao;
import mr.common.security.userentity.dao.RoleDao;
import mr.common.security.userentity.model.RoleEntity;

import org.springframework.stereotype.Repository;


/**
 * Implementación de {@link mr.common.security.userentity.dao.RoleDao RoleDao}.
 * @author Mariano Ruiz
 */
@Repository
public class RoleHibernateDao extends AbstractHibernateAuditableDictionaryDao<RoleEntity>
		implements RoleDao {

}
