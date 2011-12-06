package mr.common.dao.hibernate3;

import java.util.List;

import javax.annotation.Resource;

import mr.common.dao.GenericAuditableDictionaryDao;
import mr.common.dao.exception.DuplicateCodeDictionaryException;
import mr.common.model.AuditableDictionary;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;


/**
 * Implementación en Hibernate 3 de la interfaz {@link mr.common.dao.GenericAuditableDictionaryDao}
 * con el soporte de Spring.
 * @author Mariano Ruiz
 */
@Repository
public class GenericHibernateAuditableDictionaryDao extends HibernateDaoSupport
     implements GenericAuditableDictionaryDao {

    /**
     * Para poder inyectar automáticamente en los DAOs con anotaciones el session
     * factory se usa este método al no poder sobreescribir
     * {@link HibernateDaoSupport#setSessionFactory(SessionFactory)} que es un método
     * final.
     */
    @Resource
    public void setEntitySessionFactory(SessionFactory entitySessionFactory) {
        super.setSessionFactory(entitySessionFactory);
    }

	@SuppressWarnings("unchecked")
	public List<AuditableDictionary> findByLikeDescription(
			String classNameDicEntity, String description) {
    	return getHibernateTemplate().find(
    			"from " + classNameDicEntity + " where lower(description) like '%"
    			               + description.toLowerCase() + "%' and audit.deleted = false");
	}

	@SuppressWarnings("unchecked")
	public List<AuditableDictionary> findByLikeLargeDescription(
			String classNameDicEntity, String largeDescription) {
    	return getHibernateTemplate().find(
    			"from " + classNameDicEntity + " where lower(largeDescription) like '%"
    			               + largeDescription.toLowerCase() + "%' and audit.deleted = false");
	}

	@SuppressWarnings("unchecked")
	public List<AuditableDictionary> find(String classNameDicEntity, String description) {
    	return getHibernateTemplate().find(
    			"from " + classNameDicEntity
    			+ " where lower(code) || lower(description) || lower(largeDescription) like '%"
    			                + description.toLowerCase() + "%' and audit.deleted = false");
	}

	@SuppressWarnings("unchecked")
	public AuditableDictionary get(String classNameDicEntity, Long id) {
    	List<AuditableDictionary> list = getHibernateTemplate().find(
    			"from " + classNameDicEntity + " where id = ? and audit.deleted = false", id);
    	if(list.size()==0) {
    		return null;
    	}
    	return list.get(0);
	}

	@SuppressWarnings("unchecked")
	public AuditableDictionary getByCode(String classNameDicEntity, String code) {
    	List<AuditableDictionary> list = getHibernateTemplate().find(
    			"from " + classNameDicEntity + " where code = ? and audit.deleted = false", code);
    	if(list.size()==0) {
    		return null;
    	} if(list.size()>1) {
    		throw new DuplicateCodeDictionaryException(
        			"More than one " + classNameDicEntity + " class persisted elements have "
          		  + "the save dictionarity code: " + code + ".");
    	}
    	return list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<AuditableDictionary> getList(String classNameDicEntity) {
        return getSession().createQuery("from " + classNameDicEntity
                + " where audit.deleted = false").list();
	}

	@SuppressWarnings("unchecked")
	public long count(String classNameDicEntity) {
        List<Number> list = getHibernateTemplate().find(
        		"select count(*) from " + classNameDicEntity + " x where x.audit.deleted = false");
        return list.get(0).longValue();
	}
}
