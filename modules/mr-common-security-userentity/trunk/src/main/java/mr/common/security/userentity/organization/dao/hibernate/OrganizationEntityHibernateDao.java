package mr.common.security.userentity.organization.dao.hibernate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mr.common.dao.HibernateUtils;
import mr.common.dao.QueryUtils;
import mr.common.dao.hibernate3.AbstractHibernateAuditableDao;
import mr.common.model.ConfigurableData;
import mr.common.security.model.Role;
import mr.common.security.organization.exception.DuplicatedOrganizationException;
import mr.common.security.organization.exception.IllegalArgumentOrganizationFindException;
import mr.common.security.organization.model.Organization;
import mr.common.security.userentity.organization.dao.OrganizationEntityDao;
import mr.common.security.userentity.organization.model.OrganizationEntity;
import mr.common.security.userentity.organization.model.UserOrganization;
import mr.common.security.userentity.organization.model.UserOrganizationRole;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;


/**
 * Implementación Hibernate de {@link
 * mr.common.security.userentity.organization.dao.OrganizationEntityDao
 * OrganizationEntityDao}.
 * @author Mariano Ruiz
 */
@Repository
public class OrganizationEntityHibernateDao extends
		AbstractHibernateAuditableDao<OrganizationEntity> implements OrganizationEntityDao {

	@SuppressWarnings("unchecked")
	public List<OrganizationEntity> find(String nameOrDescription, Serializable userId, Role role,
			Boolean activeFilter, ConfigurableData page) {
		
		return prepareQuery(nameOrDescription, userId, role, activeFilter, page, false).list();
	}

	public int findCount(String nameOrDescription, Serializable userId, Role role,
			Boolean activeFilter) {
		return ((Number)prepareQuery(
				nameOrDescription, userId, role, activeFilter, null, true).uniqueResult()).intValue();
	}

	private Query prepareQuery(String nameOrDescription, Serializable userId, Role role, Boolean activeFilter,
			ConfigurableData page, boolean countQuery) {

		if(nameOrDescription==null && activeFilter==null) {
			throw new NullPointerException("nameOrDescription = null and activeFilter = null.");
		}
		String hql = "select o from " + OrganizationEntity.class.getName() + " o";
		if(userId!=null) {
			hql += " , " + UserOrganization.class.getName() + " usersorgs";
			if(role!=null) {
				hql += " , " + UserOrganizationRole.class.getName() + " usersorgroles";
			}
		}
		Map<String, Object> params = new HashMap<String, Object>();
		hql += " where o.audit.deleted = false";
		if(userId!=null) {
			hql += " and usersorgs.audit.deleted = false and o = usersorgs.organization"
				 + " and usersorgs.user.id = :userId";
			params.put("userId", userId);
			if(role!=null) {
				hql += " and usersorgroles.audit.deleted = false and usersorgroles.userOrganization = usersorgs"
						 + " and usersorgroles.role.code = :role";
				params.put("role", role.getAuthority());
			}
		}
		if(nameOrDescription!=null) {
			hql += " and (o.name like :nameOrDescription or o.description like :nameOrDescription)";
			params.put("nameOrDescription", QueryUtils.likeParam(nameOrDescription));
		}
		if(activeFilter!=null) {
			hql += " and o.enabled = :enabled";
			params.put("enabled", activeFilter);
		}
		Query query;
		if(!countQuery) {
	        // Ordenación
	        if(page.isSortable()) {
	        	hql += " order by ";
	        	for(String sort : page.getSorts()) {
	        		hql += getOrderByParam(sort);
	        	}
	        } else {
	        	hql += " order by o.name ";
	        }
			query = getSession().createQuery(hql);
			HibernateUtils.setParameters(query, params);
			// Paginación
	        if(page!=null && page.isPageable()) {
	        	query.setFirstResult(page.getStart());
	        	query.setMaxResults(page.getLimit());
	        }
		} else {
			query = HibernateUtils.countQuery(getSession(), hql, params);
		}
		return query;
	}

	private String getOrderByParam(String sort) {
		if(sort.contains("name")) {
			return "o." + sort;
		}
		if(sort.contains("description")) {
			return "o." + sort;
		}
		if(sort.contains("enabled")) {
			return "o." + sort;
		}
		throw new IllegalArgumentOrganizationFindException(
				"Invalid sort arguments in organization find.");
	}

	@SuppressWarnings("unchecked")
	public Organization getByName(String name) {
    	List<Organization> list = getHibernateTemplate().find(
    			"from " + OrganizationEntity.class.getName()
    			+ " where name = ? and audit.deleted = false", name);
    	if(list.size()==0) {
    		return null;
    	} else if(list.size()!=1) {
    		throw new DuplicatedOrganizationException(
    			"Duplicate organization with name=" + name + " on BBDD.");
    	}
    	return list.get(0);
	}

	public String getNameById(Serializable orgId) {
		Query query = getSession().createQuery(
				"select name from " + OrganizationEntity.class.getName()
			  + " where id = :orgId and audit.deleted = false").setParameter("orgId", orgId);
		return (String) query.uniqueResult();
	}

	public Long getIdByName(String name) {
		Query query = getSession().createQuery(
				"select id from " + OrganizationEntity.class.getName()
			  + " where name = :name and audit.deleted = false").setParameter("name", name);
		return (Long) query.uniqueResult();
	}
}
