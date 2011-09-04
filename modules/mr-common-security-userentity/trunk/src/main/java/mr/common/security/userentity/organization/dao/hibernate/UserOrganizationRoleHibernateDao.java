package mr.common.security.userentity.organization.dao.hibernate;

import org.springframework.stereotype.Repository;

import mr.common.dao.hibernate3.AbstractHibernateAuditableDao;
import mr.common.security.userentity.organization.dao.UserOrganizationRoleDao;
import mr.common.security.userentity.organization.model.UserOrganizationRole;


/**
 * Implementación de {@link mr.common.security.userentity.organization.dao.
 * UserOrganizationRoleDao UserOrganizationRoleDao}.
 *
 * @author Mariano Ruiz
 */
@Repository
public class UserOrganizationRoleHibernateDao extends
		AbstractHibernateAuditableDao<UserOrganizationRole> implements
		UserOrganizationRoleDao {

}
