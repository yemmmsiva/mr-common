package mr.common.dao.hibernate3;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import mr.common.dao.AbstractDao;
import mr.common.model.BaseEntity;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación en Hibernate 3 de la interfaz {@link mr.common.dao.AbstractDao}
 * con el soporte de Spring.
 * @author Mariano Ruiz
 */
@SuppressWarnings("unchecked")
public abstract class AbstractHibernateDao<DomainObject extends BaseEntity> extends HibernateDaoSupport implements AbstractDao<DomainObject> {

    protected Class<DomainObject> domainClass = getDomainClass();

    protected Class<DomainObject> getDomainClass() {
        Object type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            return (Class<DomainObject>) ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            return (Class<DomainObject>) type.getClass();
        }
    }

    /**
     * Para poder inyectar automáticamente en los DAOs con anotaciones el session
     * factory se usa este método al no poder sobreescribir
     * {@link HibernateDaoSupport#setSessionFactory(SessionFactory)} que es un método
     * final.
     * @param entitySessionFactory {@link SessionFactory}
     */
    @Resource
    public void setEntitySessionFactory(SessionFactory entitySessionFactory) {
        super.setSessionFactory(entitySessionFactory);
    }


    /*
     * Hibernate default implementations
     */

    /**
	 * @see mr.common.dao.AbstractDao#get(java.lang.Long)
	 */
	@Transactional(readOnly = true)
    public DomainObject get(Long id) {
        return (DomainObject) getHibernateTemplate().get(domainClass, id);
    }

    /**
	 * @see mr.common.dao.AbstractDao#save(DomainObject)
	 */
	@Transactional
    public Long save(DomainObject t) {
        return (Long) getHibernateTemplate().save(t);
    }

    /**
	 * @see mr.common.dao.AbstractDao#saveOrUpdate(DomainObject)
	 */
	@Transactional
    public void saveOrUpdate(DomainObject t) {
        getHibernateTemplate().saveOrUpdate(t);
    }

    /**
	 * @see mr.common.dao.AbstractDao#update(DomainObject)
	 */
	@Transactional
    public void update(DomainObject t) {
        getHibernateTemplate().update(t);
    }

    /**
	 * @see mr.common.dao.AbstractDao#persist(DomainObject)
	 */
    public void persist(DomainObject t) {
        getHibernateTemplate().persist(t);
    }

    /**
	 * @see mr.common.dao.AbstractDao#merge(DomainObject)
	 */
    public void merge(DomainObject t) {
        getHibernateTemplate().merge(t);
    }

    /**
	 * @see mr.common.dao.AbstractDao#delete(DomainObject)
	 */
	@Transactional
    public void delete(DomainObject t) {
        getHibernateTemplate().delete(t);
    }

	/**
	 * @see mr.common.dao.AbstractDao#getList()
	 */
	@Transactional(readOnly = true)
    public List<DomainObject> getList(boolean cacheable) {
        return getSession().createQuery("from " + domainClass.getName()).setCacheable(cacheable).list();
    }

    /**
	 * @see mr.common.dao.AbstractDao#getList()
	 */
	@Transactional(readOnly = true)
    public List<DomainObject> getList() {
        return getList(true);
    }

    /**
	 * @see mr.common.dao.AbstractDao#deleteById(java.lang.Long)
	 */
	@Transactional
    public void deleteById(Long id) {
    	DomainObject obj = get(id);
        getHibernateTemplate().delete(obj);
    }

    /**
	 * @see mr.common.dao.AbstractDao#deleteList(List)
	 */
	@Transactional
    public void deleteList(List<DomainObject> list) {
		for(DomainObject obj : list) {
	        getHibernateTemplate().delete(obj);
		}
    }

    /**
	 * @see mr.common.dao.AbstractDao#deleteAll()
	 */
	@Transactional
    public void deleteAll() {
        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {
                String hqlDelete = "delete " + domainClass.getName();
                session.createQuery(hqlDelete).executeUpdate();
                return null;
            }

        });
    }

    /**
	 * @see mr.common.dao.AbstractDao#count()
	 */
	@Transactional(readOnly = true)
    public long count() {
        Query query = getSession().createQuery("select count(*) from " + domainClass.getName() + " x");
        return ((Number)query.uniqueResult()).longValue();
    }
}
