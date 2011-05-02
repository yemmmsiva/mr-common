package mr.common.security.userentity.organization.dao.hibernate;

import java.util.Date;
import java.util.List;

import mr.common.dao.hibernate3.AbstractHibernateAuditableDao;
import mr.common.security.organization.model.Organization;
import mr.common.security.userentity.organization.dao.UserOrganizationDao;
import mr.common.security.userentity.organization.model.UserOrganization;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;


/**
 * Implementación Hibernate de {@link mr.common.security.userentity.organization.dao.
 * UserOrganizationDao UserOrganizationDao}.
 *
 * @author Mariano Ruiz
 */
@Repository
public class UserOrganizationHibernateDao extends AbstractHibernateAuditableDao<UserOrganization>
    implements UserOrganizationDao {

	public Long getUserOrganizationId(Long orgId, Long userId) {
		String hql = "select id from " + UserOrganization.class.getName()
		          + " where organization.id = :orgId and user.id = :userId and audit.deleted = false";
		Query query = getSession().createQuery(hql);
		query.setParameter("orgId", orgId);
		query.setParameter("userId", userId);
		return (Long) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Organization> getUserOrganizations(Long userId) {
		String hql = "select organization from "
				+ UserOrganization.class.getName()
				+ " where user.id = :userId and audit.deleted = false";
		Query query = getSession().createQuery(hql);
		query.setParameter("userId", userId);
		return query.list();
	}

	public int removeUserFromAll(Long userId) {
    	String hql = "update " + UserOrganization.class.getName()
        + " set audit.deleted = true, audit.deletedDate = :currentDate, audit.deletedUser = :currentUser"
        + " where user.id = :userId";
        Query query = getSession().createQuery(hql);
        query.setTimestamp("currentDate", new Date());
        query.setString("currentUser", getCurrentUsername());
        query.setParameter("userId", userId);
        return query.executeUpdate();
	}
}
