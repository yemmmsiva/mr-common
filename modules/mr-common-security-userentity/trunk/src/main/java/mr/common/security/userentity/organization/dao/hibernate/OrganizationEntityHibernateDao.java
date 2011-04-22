package mr.common.security.userentity.organization.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mr.common.dao.HibernateUtils;
import mr.common.dao.hibernate3.AbstractHibernateAuditableDao;
import mr.common.model.ConfigurableData;
import mr.common.security.organization.exception.DuplicatedOrganizationException;
import mr.common.security.organization.exception.IllegalArgumentOrganizationFindException;
import mr.common.security.organization.model.Organization;
import mr.common.security.userentity.organization.dao.OrganizationEntityDao;
import mr.common.security.userentity.organization.model.OrganizationEntity;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;


/**
 * Implementación Hibernate de {@link mr.common.security.userentity.organization.
 * dao.OrganizationEntityDao OrganizationEntityDao}.
 * @author Mariano Ruiz
 */
@Repository
public class OrganizationEntityHibernateDao extends
		AbstractHibernateAuditableDao<OrganizationEntity> implements OrganizationEntityDao {

	@SuppressWarnings("unchecked")
	public List<Organization> find(String nameOrDescription,
			Boolean activeFilter, ConfigurableData page) {
		
		return prepareQuery(nameOrDescription, activeFilter, page, false).list();
	}

	public int findCount(String nameOrDescription, Boolean activeFilter) {
		return ((Number)prepareQuery(
				nameOrDescription, activeFilter, null, true).uniqueResult()).intValue();
	}

	private Query prepareQuery(String nameOrDescription, Boolean activeFilter,
			ConfigurableData page, boolean countQuery) {

		if(nameOrDescription==null && activeFilter==null) {
			throw new NullPointerException("nameOrDescription = null & activeFilter = null");
		}
		String hql = "select o from " + Organization.class.getName() + " o where"
		           + " o.audit.deleted = false";
		Map<String, Object> params = new HashMap<String, Object>();
		if(nameOrDescription!=null) {
			hql += " and (o.name like :nameOrDescription or o.description like :nameOrDescription)";
			params.put("nameOrDescription", nameOrDescription);
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
    			"from " + OrganizationEntity.class.getName() + " where name = ? and audit.deleted = false", name);
    	if(list.size()==0) {
    		return null;
    	} else if(list.size()!=1) {
    		throw new DuplicatedOrganizationException("Duplicate organization on BBDD.");
    	}
    	return list.get(0);
	}
}
