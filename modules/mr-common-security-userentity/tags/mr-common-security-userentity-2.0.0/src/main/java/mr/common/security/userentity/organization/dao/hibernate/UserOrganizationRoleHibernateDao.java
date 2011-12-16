package mr.common.security.userentity.organization.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import mr.common.collection.CollectionUtils;
import mr.common.dao.hibernate3.AbstractHibernateAuditableDao;
import mr.common.security.model.Role;
import mr.common.security.organization.model.Organization;
import mr.common.security.userentity.organization.dao.UserOrganizationRoleDao;
import mr.common.security.userentity.organization.model.UserOrganizationRole;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;


/**
 * Implementación de {@link mr.common.security.userentity.organization.dao.UserOrganizationRoleDao
 * UserOrganizationRoleDao}.
 *
 * @author Mariano Ruiz
 */
@Repository
public class UserOrganizationRoleHibernateDao extends
		AbstractHibernateAuditableDao<UserOrganizationRole> implements
		UserOrganizationRoleDao {

	@SuppressWarnings("unchecked")
	public List<Organization> getUserOrganizationsWithRoles(
			Serializable userId, Role ... roles) {
		String hql = "select uor.userOrganization.organization from "
				+ UserOrganizationRole.class.getName()
				+ " uor where uor.userOrganization.user.id = :userId"
				+ "     and uor.role.code in (:rolesName) and uor.audit.deleted = false";
		Query query = getSession().createQuery(hql);
		query.setParameter("userId", userId);
		query.setParameterList("rolesName", CollectionUtils.objectArrayToList(roles, "authority"));
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Serializable> getUserOrganizationsWithRolesId(
			Serializable userId, Role ... roles) {
		String hql = "select uor.userOrganization.organization.id from "
				+ UserOrganizationRole.class.getName()
				+ " uor where uor.userOrganization.user.id = :userId"
				+ "     and uor.role.code in (:rolesName) and uor.audit.deleted = false";
		Query query = getSession().createQuery(hql);
		query.setParameter("userId", userId);
		query.setParameterList("rolesName", CollectionUtils.objectArrayToList(roles, "authority"));
		return query.list();
	}

	public int getUserOrganizationsWithRolesCount(Serializable userId, Role ... roles) {
		String hql = "select count(uor.userOrganization.organization) from "
				+ UserOrganizationRole.class.getName()
				+ " uor where uor.userOrganization.user.id = :userId"
				+ "     and uor.role.code in (:rolesName) and uor.audit.deleted = false";
		Query query = getSession().createQuery(hql);
		query.setParameter("userId", userId);
		query.setParameterList("rolesName", CollectionUtils.objectArrayToList(roles, "authority"));
		return ((Number)query.uniqueResult()).intValue();
	}
}
