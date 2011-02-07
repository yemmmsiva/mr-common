package mr.common.dao.hibernate3;

import java.util.List;

import mr.common.dao.AbstractAuditableDictionaryDao;
import mr.common.dao.exception.DuplicateCodeDictionaryException;
import mr.common.model.AuditableDictionary;


/**
 * Implementación en Hibernate 3 de la interfaz {@link mr.common.dao.AbstractAuditableDictionaryDao}
 * con el soporte de Spring.
 * @author Mariano Ruiz
 */
public abstract class AbstractHibernateAuditableDictionaryDao<Dict extends AuditableDictionary>
    extends AbstractHibernateAuditableDao<Dict>
       implements AbstractAuditableDictionaryDao<Dict> {


	/**
	 * @see mr.common.dao.AbstractAuditableDictionaryDao#getByCode(String)
	 */
	@SuppressWarnings("unchecked")
	public Dict getByCode(String code) {
    	List<Dict> list = getHibernateTemplate().find(
    			"from " + domainClass.getName() + " where code = ? and audit.deleted = false", code);
    	if(list.size()==0) {
    		return null;
    	} if(list.size()>1) {
    		throw new DuplicateCodeDictionaryException();
    	}
    	return list.get(0);
	}

	/**
	 * @see mr.common.dao.AbstractAuditableDictionaryDao#find(String)
	 */
	@SuppressWarnings("unchecked")
	public List<Dict> find(String description) {
    	return getHibernateTemplate().find(
    			"from " + getDomainClass().getName()
    			+ " where lower(code) || lower(description) || lower(largeDescription) like '%"
    			                + description.toLowerCase() + "%' and audit.deleted = false");
	}

	/**
	 * @see mr.common.dao.AbstractAuditableDictionaryDao#findByLikeDescription(String)
	 */
	@SuppressWarnings("unchecked")
	public List<Dict> findByLikeDescription(String description) {
    	return getHibernateTemplate().find(
    			"from " + getDomainClass().getName() + " where lower(description) like '%"
    			                + description.toLowerCase() + "%' and audit.deleted = false");
	}

	/**
	 * @see mr.common.dao.AbstractAuditableDictionaryDao#findByLikeLargeDescription(String)
	 */
	@SuppressWarnings("unchecked")
	public List<Dict> findByLikeLargeDescription(String largeDescription) {
    	return getHibernateTemplate().find(
    			"from " + domainClass.getName() + " where lower(largeDescription) like '%"
    			                + largeDescription.toLowerCase() + "%' and audit.deleted = false");
	}

	public Long save(Dict t) {
		if(t.getCode()!=null && getByCode(t.getCode())!=null) {
			throw new DuplicateCodeDictionaryException();
		}
		return super.save(t);
	}

	public void persist(Dict t) {
		if(t.getCode()!=null && getByCode(t.getCode())!=null) {
			throw new DuplicateCodeDictionaryException();
		}
		super.persist(t);
	}
}
