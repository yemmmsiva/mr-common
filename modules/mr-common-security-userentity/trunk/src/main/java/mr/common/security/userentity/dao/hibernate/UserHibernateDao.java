package mr.common.security.userentity.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mr.common.dao.HibernateUtils;
import mr.common.dao.QueryUtils;
import mr.common.dao.hibernate3.AbstractHibernateAuditableDao;
import mr.common.security.exception.DuplicateUserException;
import mr.common.security.model.form.FindUserForm;
import mr.common.security.userentity.dao.UserDao;
import mr.common.security.userentity.model.Authority;
import mr.common.security.userentity.model.UserEntity;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


/**
 * @see mr.common.security.userentity.dao.UserDao
 * @author Mariano Ruiz
 */
@Repository
public class UserHibernateDao extends AbstractHibernateAuditableDao<UserEntity>
		implements UserDao {

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
    		throw new DuplicateUserException("Duplicate username");
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
    		throw new DuplicateUserException("Duplicate email user");
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
	public List<UserEntity> find(FindUserForm form) {
		String hql = "select u from " + UserEntity.class.getName() + " u where u.audit.deleted = false";
		Map<String, Object> params = new HashMap<String, Object>();

		if (StringUtils.hasText(form.getUsername())) {
			hql += " and lower(u.username) like :username";
			 params.put("username", QueryUtils.likeParam(form.getUsername()));
		}

		if (StringUtils.hasText(form.getCommonName())) {
			hql += " and (lower(u.person.commonName) like :commonName"
			     + "      or lower(u.person.givenName) like :commonName"
			     + "      or lower(u.person.surname) like :commonName)";
			 params.put("commonName", QueryUtils.likeParam(form.getCommonName()));
		}

		if (StringUtils.hasText(form.getMail())) {
			hql += " and lower(u.emailAddress) like :mail";
			 params.put("mail", QueryUtils.likeParam(form.getMail()));
		}

		if(form.getEnabled()!=null) {
			hql += " and u.enabled = :enabled";
			 params.put("enabled", form.getEnabled());
		}

        Query query = getSession().createQuery(hql.toString());
        HibernateUtils.setParameters(query, params);
        query.setCacheable(true);

        return query.list();
	}
}
