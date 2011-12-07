package mr.common.dao.hibernate3;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import mr.common.dao.AbstractDao;
import mr.common.model.BaseEntity;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * Implementación en Hibernate 3 de la interfaz {@link mr.common.dao.AbstractDao}
 * con el soporte de Spring.
 * @author Mariano Ruiz
 */
public abstract class AbstractHibernateDao<DomainObject extends BaseEntity> extends HibernateDaoSupport implements AbstractDao<DomainObject> {

    protected Class<DomainObject> domainClass = getDomainClass();

    @SuppressWarnings("unchecked")
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
     */
    @Resource
    public void setEntitySessionFactory(SessionFactory entitySessionFactory) {
        super.setSessionFactory(entitySessionFactory);
    }


    /*
     * Hibernate default implementations
     */

    public DomainObject get(Long id) {
        return (DomainObject) getHibernateTemplate().get(domainClass, id);
    }

    public Long save(DomainObject t) {
        return (Long) getHibernateTemplate().save(t);
    }

    public void saveOrUpdate(DomainObject t) {
        getHibernateTemplate().saveOrUpdate(t);
    }

    public void update(DomainObject t) {
        getHibernateTemplate().update(t);
    }

    public void persist(DomainObject t) {
        getHibernateTemplate().persist(t);
    }

	public DomainObject merge(DomainObject t) {
        return (DomainObject) getHibernateTemplate().merge(t);
    }

    public void detach(DomainObject t) {
        getHibernateTemplate().evict(t);
    }

    public void refresh(DomainObject t) {
        getHibernateTemplate().refresh(t);
    }

	public DomainObject refreshEntity(DomainObject entity) {
		flush();
		detach(entity);
		return get(entity.getId());
    }

    public void flush() {
    	getHibernateTemplate().flush();
    }

    public void delete(DomainObject t) {
        getHibernateTemplate().delete(t);
    }

	@SuppressWarnings("unchecked")
	public List<DomainObject> getList(boolean cacheable) {
        return getSession().createQuery("from " + domainClass.getName()).setCacheable(cacheable).list();
    }

    public List<DomainObject> getList() {
        return getList(true);
    }

    public void deleteById(Long id) {
    	DomainObject obj = get(id);
        getHibernateTemplate().delete(obj);
    }

    public void deleteList(List<DomainObject> list) {
		for(DomainObject obj : list) {
	        getHibernateTemplate().delete(obj);
		}
    }

    public int deleteAll() {
		String hqlDelete = "delete " + domainClass.getName();
		return getSession().createQuery(hqlDelete).executeUpdate();
    }

    public long count() {
        Query query = getSession().createQuery("select count(*) from " + domainClass.getName() + " x");
        return ((Number)query.uniqueResult()).longValue();
    }
}
