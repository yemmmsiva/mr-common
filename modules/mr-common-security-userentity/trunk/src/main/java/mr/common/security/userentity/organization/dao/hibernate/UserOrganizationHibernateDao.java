package mr.common.security.userentity.organization.dao.hibernate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mr.common.dao.hibernate3.AbstractHibernateAuditableDao;
import mr.common.security.model.User;
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

	public UserOrganization getUserOrganization(Long orgId, Long userId) {
		String hql = "select userOrgs from " + UserOrganization.class.getName()
		          + " userOrgs where userOrgs.organization.id = :orgId and userOrgs.user.id = :userId "
		          + " and userOrgs.audit.deleted = false";
		Query query = getSession().createQuery(hql);
		query.setParameter("orgId", orgId);
		query.setParameter("userId", userId);
		return (UserOrganization) query.uniqueResult();
	}

	public Long getUserOrganizationId(Long orgId, Long userId) {
		String hql = "select userOrgs.id from " + UserOrganization.class.getName()
		          + " userOrgs where userOrgs.organization.id = :orgId and userOrgs.user.id = :userId "
		          + " and userOrgs.audit.deleted = false";
		Query query = getSession().createQuery(hql);
		query.setParameter("orgId", orgId);
		query.setParameter("userId", userId);
		return (Long) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Organization> getUserOrganizations(Long userId) {
		String hql = "select userOrgs.organization from "
				+ UserOrganization.class.getName()
				+ " userOrgs where userOrgs.user.id = :userId and userOrgs.audit.deleted = false";
		Query query = getSession().createQuery(hql);
		query.setParameter("userId", userId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Long> getUserOrganizationsId(Long userId) {
		String hql = "select userOrgs.organization.id from "
				+ UserOrganization.class.getName()
				+ " userOrgs where userOrgs.user.id = :userId and userOrgs.audit.deleted = false";
		Query query = getSession().createQuery(hql);
		query.setParameter("userId", userId);
		return query.list();
	}

	public int getUserOrganizationsCount(Long userId) {
		String hql = "select count(*) from "
				+ UserOrganization.class.getName()
				+ " userOrgs where userOrgs.user.id = :userId and userOrgs.audit.deleted = false";
		Query query = getSession().createQuery(hql);
		query.setParameter("userId", userId);
		return ((Number)query.uniqueResult()).intValue();
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

	@SuppressWarnings("unchecked")
	public List<User> getUsers(Serializable id) {
		String hql = "select userOrgs.user from "
			+ UserOrganization.class.getName()
			+ " userOrgs where userOrgs.organization.id = :orgId and userOrgs.audit.deleted = false";
		Query query = getSession().createQuery(hql);
		query.setParameter("orgId", id);
		return query.list();
	}

	@SuppressWarnings("rawtypes")
	public List getUsersId(Serializable id) {
		String hql = "select userOrgs.user.id from "
			+ UserOrganization.class.getName()
			+ " userOrgs where userOrgs.organization.id = :orgId and userOrgs.audit.deleted = false";
		Query query = getSession().createQuery(hql);
		query.setParameter("orgId", id);
		return query.list();
	}

	public int getUsersCount(Serializable id) {
		String hql = "select count(*) from "
			+ UserOrganization.class.getName()
			+ " where organization.id = :orgId and audit.deleted = false";
		Query query = getSession().createQuery(hql);
		query.setParameter("orgId", id);
		return ((Number)query.uniqueResult()).intValue();
	}

	public int removeAllUsersFromOrganization(Long id) {
    	String hql = "update " + UserOrganization.class.getName()
        + " set audit.deleted = true, audit.deletedDate = :currentDate, audit.deletedUser = :currentUser"
        + " where organization.id = :orgId";
        Query query = getSession().createQuery(hql);
        query.setTimestamp("currentDate", new Date());
        query.setString("currentUser", getCurrentUsername());
        query.setParameter("orgId", id);
        return query.executeUpdate();
	}
}
