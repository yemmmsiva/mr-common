package mr.common.security.userentity.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<Authority> getAuthorityList(String username) {
		return getHibernateTemplate().find(
    			"select a from " + UserEntity.class.getName() + " u join u.authorities a" 
    			+ " where u.username = ? and u.audit.deleted = false", username);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<Authority> getAuthorityList(Long userId) {
		return getHibernateTemplate().find(
    			"select a from " + UserEntity.class.getName() + " u join u.authorities a" 
    			+ " where u.id = ? and u.audit.deleted = false", userId);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<UserEntity> find(User user, Boolean activeFilter, ConfigurableData page) {
        return prepareQuery(user, activeFilter, page, false).list();
	}

	/**
	 * {@inheritDoc}
	 */
	public int findCount(User user, Boolean activeFilter) {
        return ((Number) prepareQuery(user, activeFilter, null, true).uniqueResult()).intValue();
	}

	private Query prepareQuery(User user, Boolean activeFilter, ConfigurableData page, boolean countQuery) {
		UserEntity userEntity = (UserEntity) user;
		String hql = "select u from " + UserEntity.class.getName() + " u where u.audit.deleted = false";
		Map<String, Object> params = new HashMap<String, Object>();

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
}
