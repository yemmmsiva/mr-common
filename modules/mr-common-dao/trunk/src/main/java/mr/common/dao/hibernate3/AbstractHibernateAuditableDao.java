package mr.common.dao.hibernate3;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mr.common.dao.AbstractAuditableDao;
import mr.common.dao.exception.DaoException;
import mr.common.model.Audit;
import mr.common.model.AuditableEntity;
import mr.common.model.BaseEntity;
import mr.common.security.service.UserSecurityService;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * Implementación en Hibernate 3 de la interfaz {@link mr.common.dao.AbstractAuditableDao}
 * con el soporte de Spring.
 * @author Mariano Ruiz
 */
public abstract class AbstractHibernateAuditableDao<DomainObject extends AuditableEntity>
                  extends HibernateDaoSupport implements AbstractAuditableDao<DomainObject> {


	@Resource
	private UserSecurityService securityService;

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
	 * @see mr.common.dao.AbstractAuditableDao#get(java.lang.Long)
	 */
    @SuppressWarnings("unchecked")
	public DomainObject get(Long id) {
    	List<DomainObject> list = getHibernateTemplate().find(
    			"from " + domainClass.getName() + " where id = ? and audit.deleted = false", id);
    	if(list.size()==0) {
    		return null;
    	}
    	return list.get(0);
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#getIgnoreIsDeleted(java.lang.Long)
	 */
    @SuppressWarnings("unchecked")
	public DomainObject getIgnoreIsDeleted(Long id) {
        return (DomainObject) getHibernateTemplate().get(domainClass, id);
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#save(DomainObject)
	 */
    public Long save(DomainObject t) {
		saveAudit(t);
        return (Long) getHibernateTemplate().save(t);
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#persist(DomainObject)
	 */
    public void persist(DomainObject t) {
		saveAudit(t);
        getHibernateTemplate().persist(t);
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#merge(DomainObject)
	 */
    @SuppressWarnings("unchecked")
	public DomainObject merge(DomainObject t) {
		saveAudit(t);
        return (DomainObject) getHibernateTemplate().merge(t);
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#detach(DomainObject)
	 */
    public void detach(DomainObject t) {
        getHibernateTemplate().evict(t);
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#refresh(DomainObject)
	 */
    public void refresh(DomainObject t) {
        getHibernateTemplate().refresh(t);
    }

	/**
	 * @see mr.common.dao.AbstractAuditableDao#refreshEntity(BaseEntity)
	 */
    public DomainObject refreshEntity(DomainObject entity) {
		flush();
		detach(entity);
		return get(entity.getId());
    }

    /**
     * @see mr.common.dao.AbstractAuditableDao#flush()
     */
    public void flush() {
    	getHibernateTemplate().flush();
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#saveOrUpdate(DomainObject)
	 */
    public void saveOrUpdate(DomainObject t) {
		saveAudit(t);
        getHibernateTemplate().saveOrUpdate(t);
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#update(DomainObject)
	 */
    public void update(DomainObject t) {
		saveAudit(t);
        getHibernateTemplate().update(t);
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#delete(DomainObject)
	 */
    public void delete(DomainObject t) {
		deleteAudit(t);
        getHibernateTemplate().update(t);
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#remove(DomainObject)
	 */
    public void remove(DomainObject t) {
        getHibernateTemplate().delete(t);
    }

	/**
	 * @see mr.common.dao.AbstractAuditableDao#getList(boolean)
	 */
    @SuppressWarnings("unchecked")
	public List<DomainObject> getList(boolean cacheable) {
        return getSession().createQuery("from " + domainClass.getName()
        		                     + " where audit.deleted = false").setCacheable(cacheable).list();
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#getList()
	 */
    public List<DomainObject> getList() {
        return getList(true);
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#getListAll()
	 */
    @SuppressWarnings("unchecked")
	public List<DomainObject> getListAll() {
    	return getSession().createQuery("from " + domainClass.getName()).list();
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#getDeletedList()
	 */
    @SuppressWarnings("unchecked")
	public List<DomainObject> getDeletedList() {
        return getSession().createQuery("from " + domainClass.getName()
                                     + " where audit.deleted = true").list();
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#deleteById(java.lang.Long)
	 */
    public void deleteById(Long id) {
    	DomainObject obj = get(id);
    	if(obj==null) {
    		throw  new DaoException(
    				"Entity with id=" + id + " not exist.");
    	}
    	deleteAudit(obj);
        getHibernateTemplate().update(obj);
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#deleteList(List)
	 */
    public void deleteList(List<DomainObject> list) {
		for(DomainObject obj : list) {
			deleteAudit(obj);
	        getHibernateTemplate().update(obj);
		}
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#removeById(java.lang.Long)
	 */
    public void removeById(Long id) {
    	DomainObject obj = getIgnoreIsDeleted(id);
    	if(obj==null) {
    		throw  new DaoException(
    				"Entity with id=" + id + " not exist.");
    	}
        getHibernateTemplate().delete(obj);
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#removeList(List)
	 */
    public void removeList(List<DomainObject> list) {
		for(DomainObject obj : list) {
	        getHibernateTemplate().delete(obj);
		}
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#deleteAll()
	 */
    public int deleteAll() {
		String hql = "update "
				+ domainClass.getName()
				+ " set audit.deleted = true, audit.deletedDate = :currentDate, audit.deletedUser = :currentUser";
		Query query = getSession().createQuery(hql);
		query.setTimestamp("currentDate", new Date());
		query.setString("currentUser", getCurrentUsername());
		return query.executeUpdate();
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#removeAll()
	 */
    public int removeAll() {
		String hqlDelete = "delete " + domainClass.getName();
		return getSession().createQuery(hqlDelete).executeUpdate();
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#count()
	 */
    @SuppressWarnings("unchecked")
	public long count() {
        List<Number> list = getHibernateTemplate().find(
        		"select count(*) from " + domainClass.getName() + " x where x.audit.deleted = false");
        return list.get(0).longValue();
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#countAll()
	 */
    @SuppressWarnings("unchecked")
	public long countAll() {
        List<Number> list = getHibernateTemplate().find("select count(*) from " + domainClass.getName() + " x");
        return list.get(0).longValue();
    }

    /**
	 * @see mr.common.dao.AbstractAuditableDao#countAll()
	 */
	@SuppressWarnings("unchecked")
	public long countDeleted() {
        List<Number> list = getHibernateTemplate().find(
        		"select count(*) from " + domainClass.getName() + " x where x.audit.deleted = true");
        return list.get(0).longValue();
    }



    /**
     * Crea un objeto auditoría con tiempo actual y el usuario actual, o <code>APP_USER</code>.
     * @return {@link Audit} audit
     */
    public Audit getNewAuditInstance() {
    	Audit audit = new Audit();
        audit.setOwner(getCurrentUsername());
        audit.setCreated(new Date());
        return audit;
    }

    /*
     *  Métodos privados de soporte.
     */

    /**
     * Retorna el usuario actual de la sesión, o <code>APP_USER</code>
     * si la operación no se está haciendo a través de un usuario.
     * @return String
     */
    protected String getCurrentUsername() {
    	String u = securityService.getCurrentUsername();
        if (u != null) {
            return u;
        }
        // Si no hay usuario logado el registro fue creado por la aplicación
        return Audit.DEFAULT_USER;
    }

    /**
     * Se llama cuando se ejecuta el borrado de un registro.
     * @param auditable objecto auditable
     */
    protected void deleteAudit(AuditableEntity auditable) {
        Audit audit = auditable.getAudit();
        if(audit == null) {
        	throw new DaoException("Auditable element have audit = null.");
        }
        audit.setDeletedDate(new Date());
        audit.setDeletedUser(getCurrentUsername());
        audit.setDeleted(true);
    }

    /**
     * Crea la información de auditoría en los objetos nuevos,
     * o la actualiza en los ya existentes.
     * 
     * @param auditable
     */
    protected void saveAudit(AuditableEntity auditable) {
        Audit audit = auditable.getAudit();
        if (audit == null) {
            audit = getNewAuditInstance();
            auditable.setAudit(audit);
        } else {
            audit.setLastUpdater(getCurrentUsername());
            audit.setLastUpdate(new Date());
        }
    }
}
