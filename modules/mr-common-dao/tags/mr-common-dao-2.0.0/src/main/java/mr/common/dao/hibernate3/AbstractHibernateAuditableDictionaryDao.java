package mr.common.dao.hibernate3;

import java.util.List;

import mr.common.dao.AbstractAuditableDictionaryDao;
import mr.common.dao.exception.DaoException;
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


	@SuppressWarnings("unchecked")
	public Dict getByCode(String code) {
    	List<Dict> list = getHibernateTemplate().find(
    			"from " + domainClass.getName() + " where code = ? and audit.deleted = false", code);
    	if(list.size()==0) {
    		return null;
    	} if(list.size()>1) {
    		throw new DuplicateCodeDictionaryException(
    			"More than one " + domainClass.getSimpleName() + " class persisted elements have "
    		  + "the save dictionarity code: " + code + ".");
    	}
    	return list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Dict> find(String description) {
    	return getHibernateTemplate().find(
    			"from " + getDomainClass().getName()
    			+ " where lower(code) || lower(description) || lower(largeDescription) like '%"
    			                + description.toLowerCase() + "%' and audit.deleted = false");
	}

	@SuppressWarnings("unchecked")
	public List<Dict> findByLikeDescription(String description) {
    	return getHibernateTemplate().find(
    			"from " + getDomainClass().getName() + " where lower(description) like '%"
    			                + description.toLowerCase() + "%' and audit.deleted = false");
	}

	@SuppressWarnings("unchecked")
	public List<Dict> findByLikeLargeDescription(String largeDescription) {
    	return getHibernateTemplate().find(
    			"from " + domainClass.getName() + " where lower(largeDescription) like '%"
    			                + largeDescription.toLowerCase() + "%' and audit.deleted = false");
	}

	public Long save(Dict t) {
		if(t.getCode()==null) {
			throw new DaoException("The dictionary element do not have code.");
		}
		if(getByCode(t.getCode())!=null) {
			throw new DuplicateCodeDictionaryException(
					"A element with code = " + t.getCode() + " exist.");
		}
		return super.save(t);
	}

	public void persist(Dict t) {
		if(t.getCode()==null) {
			throw new DaoException("The dictionary element do not have code.");
		}
		if(getByCode(t.getCode())!=null) {
			throw new DuplicateCodeDictionaryException(
					"A element with code = " + t.getCode() + " exist.");
		}
		super.persist(t);
	}
}
