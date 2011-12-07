package mr.common.security.userentity.dao.hibernate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mr.common.collection.CollectionUtils;
import mr.common.dao.HibernateUtils;
import mr.common.dao.QueryUtils;
import mr.common.dao.hibernate3.AbstractHibernateAuditableDao;
import mr.common.model.ConfigurableData;
import mr.common.security.exception.DuplicatedUserException;
import mr.common.security.exception.IllegalArgumentUserFindException;
import mr.common.security.model.User;
import mr.common.security.userentity.dao.UserEntityDao;
import mr.common.security.userentity.model.Authority;
import mr.common.security.userentity.model.UserEntity;
import mr.common.security.userentity.organization.model.UserOrganization;
import mr.common.security.userentity.organization.model.UserOrganizationRole;

import org.hibernate.Query;
import org.hibernate.QueryException;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


/**
 * @see mr.common.security.userentity.dao.UserEntityDao
 * @author Mariano Ruiz
 */
@Repository
public class UserEntityHibernateDao extends AbstractHibernateAuditableDao<UserEntity>
		implements UserEntityDao {

	@SuppressWarnings("unchecked")
	public UserEntity getByUsername(String username) {
    	List<UserEntity> list = getHibernateTemplate().find(
    			"from " + UserEntity.class.getName() + " where username = ? and audit.deleted = false", username);
    	if(list.size()==0) {
    		return null;
    	} else if(list.size()!=1) {
    		throw new DuplicatedUserException("Duplicate username on BBDD.");
    	}
    	return list.get(0);
	}

	@SuppressWarnings("unchecked")
	public UserEntity getByEmailAddress(String emailAddress) {
    	List<UserEntity> list = getHibernateTemplate().find(
    			"from " + UserEntity.class.getName() + " where emailAddress = ? and audit.deleted = false", emailAddress);
    	if(list.size()==0) {
    		return null;
    	} else if(list.size()!=1) {
    		throw new DuplicatedUserException("Duplicate email user");
    	}
    	return list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Authority> getAuthorityList(String username) {
		return getHibernateTemplate().find(
    			"select a from " + UserEntity.class.getName() + " u join u.authorities a" 
    			+ " where u.username = ? and u.audit.deleted = false", username);
	}

	@SuppressWarnings("unchecked")
	public List<Authority> getAuthorityList(Long userId) {
		return getHibernateTemplate().find(
    			"select a from " + UserEntity.class.getName() + " u join u.authorities a" 
    			+ " where u.id = ? and u.audit.deleted = false", userId);
	}

	@SuppressWarnings("unchecked")
	public List<UserEntity> find(User user, Serializable orgId, Boolean activeFilter, ConfigurableData page) {
        return prepareQuery(user, (Long)orgId, activeFilter, page, false).list();
	}

	public int findCount(User user, Serializable orgId, Boolean activeFilter) {
        return ((Number) prepareQuery(user, (Long)orgId, activeFilter, null, true).uniqueResult()).intValue();
	}

	private Query prepareQuery(User user, Long orgId, Boolean activeFilter, ConfigurableData page, boolean countQuery) {
		UserEntity userEntity = (UserEntity) user;
		String hql = "select u from " + UserEntity.class.getName() + " u ";
		if(orgId!=null) {
			hql += ", " + UserOrganization.class.getName() + " usersOrgs ";
		}
		if(user.getRoles()!=null) {
			if(orgId!=null) {
				hql += ", " + UserOrganizationRole.class.getName() + " au ";
			} else {
				hql += ", " + Authority.class.getName() + " au ";
			}
		}
		hql += "where u.audit.deleted = false";
		Map<String, Object> params = new HashMap<String, Object>();

		if(orgId!=null) {
			hql += " and usersOrgs.organization.id = :orgId and usersOrgs.user = u"
				 + " and usersOrgs.audit.deleted = false";
			params.put("orgId", orgId);
		}
		if(user.getRoles()!=null) {
			hql += " and au.audit.deleted = false and au.role.code in (:rolesName)";
			params.put("rolesName", CollectionUtils.objectListToList(user.getRoles(), "authority"));
			if(orgId!=null) {
				hql += " and au.userOrganization.user = u and au.userOrganization.organization = u";
			} else {
				hql += " and au.user = u";
			}
		}
		if (StringUtils.hasText(userEntity.getUsername())) {
			hql += " and lower(u.username) like :username";
			 params.put("username", QueryUtils.likeParam(userEntity.getUsername()));
		}

		if (StringUtils.hasText(userEntity.getUserData().getCommonName())) {
			hql += " and (lower(u.userData.commonName) like :commonName"
			     + "      or lower(u.userData.firstName) like :commonName"
			     + "      or lower(u.userData.lastName) like :commonName)";
			 params.put("commonName", QueryUtils.likeParam(userEntity.getUserData().getCommonName()));
		}

		if (StringUtils.hasText(userEntity.getEmailAddress())) {
			hql += " and lower(u.emailAddress) like :emailAddress";
			 params.put("emailAddress", QueryUtils.likeParam(userEntity.getEmailAddress()));
		}

		if(activeFilter!=null) {
			hql += " and u.enabled = :enabled";
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
	        	hql += " order by u.username ";
	        }

	        try {
	        	query = getSession().createQuery(hql);
	        } catch(QueryException e) {
	        	throw new IllegalArgumentUserFindException();
	        }
	        HibernateUtils.setParameters(query, params);

			// Paginación
	        if(page!=null && page.isPageable()) {
	        	query.setFirstResult(page.getStart());
	        	query.setMaxResults(page.getLimit());
	        }
		} else {
			query = HibernateUtils.countQuery(getSession(), hql, params);
		}
        query.setCacheable(true);

        return query;
	}

	private String getOrderByParam(String sort) {
		if(sort.contains("username")) {
			return "u." + sort;
		}
		if(sort.contains("emailAddress")) {
			return "u." + sort;
		}
		if(sort.contains("enabled")) {
			return "u." + sort;
		}
		if(sort.contains("commonName")) {
			return "u.userData." + sort;
		}
		throw new IllegalArgumentUserFindException(
				"Invalid sort arguments in user find.");
	}

	public String getUsernameById(Long userId) {
		String hql = "select username from " + UserEntity.class.getName()
				+ " where id = :userId and audit.deleted = false";
		Query query = getSession().createQuery(hql);
		query.setParameter("userId", userId);
		return (String) query.uniqueResult();
	}

	public Long getIdByUsername(String username) {
		String hql = "select id from " + UserEntity.class.getName()
				+ " where username = :username and audit.deleted = false";
		Query query = getSession().createQuery(hql);
		query.setParameter("username", username);
		return (Long) query.uniqueResult();
	}

	public Long getIdByEmailAddress(String emailAddress) {
		String hql = "select id from " + UserEntity.class.getName()
				+ " where emailAddress = :emailAddress and audit.deleted = false";
		Query query = getSession().createQuery(hql);
		query.setParameter("emailAddress", emailAddress);
		return (Long) query.uniqueResult();
	}
}
