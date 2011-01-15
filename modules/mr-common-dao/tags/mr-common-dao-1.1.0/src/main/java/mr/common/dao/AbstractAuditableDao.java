package mr.common.dao;

import java.util.List;

import mr.common.model.AuditableEntity;

/**
 * Interfaz abstracta de un DAO genérico que debe ser implementado por
 * todos los DAOs de entidades que extiendan de {@link AuditableEntity}.
 * @author Mariano Ruiz
 */
public interface AbstractAuditableDao<DomainObject extends AuditableEntity> {

	/**
	 * Obtiene el objeto por su identificador.
	 * @param id Long: identificador del objeto
	 * @return DomainObject
	 */
	DomainObject get(Long id);

	/**
	 * Obtiene el objeto por su identificador, ignorando si
	 * está borrado lógicamente.
	 * @param id Long: identificador del objeto
	 * @return DomainObject
	 * @see #get(Long)
	 */
	DomainObject getIgnoreIsDeleted(Long id);

	/**
	 * Guarda un objeto que no estaba guardado.
	 * @param entity DomainObject: objeto a guardar
	 * @return id Long: identificador del nuevo objeto
	 */
    Long save(DomainObject entity);

	/**
	 * Marca un objeto como persistente en la sesión actual.
	 * @param entity DomainObject: objeto
	 */
    void persist(DomainObject entity);

	/**
	 * Marca un objeto como persistente en la sesión actual,
	 * y si hubiera otro con el mismo ID, lo remplaza por este.
	 * @param entity DomainObject: objeto
	 * @return DomainObject
	 */
    DomainObject merge(DomainObject entity);

	/**
	 * Quita de la sesión al objeto.
	 * @param entity DomainObject: objeto persistente
	 */
    void detach(DomainObject entity);

	/**
	 * Refrezca el objeto desde la base de datos.
	 * @param entity DomainObject: objeto persistente
	 */
    void refresh(DomainObject entity);

    /**
     * Actualiza un objeto que ya estaba guardado.
     * @param entity DomainObject
     */
    void update(DomainObject entity);

    /**
     * Guarda un objeto nuevo o actualiza si ya existe.
     * @param entity DomainObject
     */
    void saveOrUpdate(DomainObject entity);

    /**
     * Recupera todos los objetos.
     * @param cacheable boolean: <code>true</code> para
     * que la lista quede cacheada en memoria
     * @return List DomainObject
     * @see #getList()
     */
    List<DomainObject> getList(boolean cacheable);

    /**
     * Recupera todos los objetos.
     * @return List DomainObject
     * @see #getList(boolean)
     */
    List<DomainObject> getList();

    /**
     * Recupera todos los objetos, incluso los borrados
     * lógicamente.
     * @return List DomainObject
     * @see #getList()
     */
    List<DomainObject> getListAll();

    /**
     * Recupera todos los objetos borrados lógicamente.
     * @return List DomainObject
     * @see #getList()
     * @see #getListAll()
     */
    List<DomainObject> getDeletedList();

    /**
     * Cuenta todos los objetos.
     * @return long
     * @see #countAll()
     * @see #countDeleted()
     */
    long count();

    /**
     * Cuenta todos los objetos incluso los borrados
     * lógicamente.
     * @return long
     * @see #count()
     * @see #countDeleted()
     */
    long countAll();

    /**
     * Cuenta todos los objetos borrados lógicamente. 
     * @return long
     * @see #count()
     * @see #countAll()
     */
    long countDeleted();

    /**
     * Borra lógicamente el objeto pasado.
     * @param entity DomainObject
     * @see #remove(AuditableEntity)
     */
    void delete(DomainObject entity);

    /**
     * Borra físicamente el objeto pasado.
     * @param entity DomainObject
     * @see #delete(AuditableEntity)
     */
    void remove(DomainObject entity);

    /**
     * Borra lógicamente el objeto con el id pasado.
     * @param entity Long
     * @see #removeById(Long)
     */
    void deleteById(Long entity);

    /**
     * Borra lógicamente todos los objetos de la lista.
     * @param list List
     * @see #removeList(List)
     */
    void deleteList(List<DomainObject> list);

    /**
     * Borra físicamente el objeto con el id pasado.
     * @param entity Long
     * @see #deleteById(Long)
     */
    void removeById(Long entity);

    /**
     * Borra físicamente todos los objetos de la lista.
     * @param list List
     * @see #deleteList(List)
     */
    void removeList(List<DomainObject> list);

    /**
     * Borra lógicamente todos los objetos de la tabla.
     * @see #removeAll()
     */
    void deleteAll();

    /**
     * Borra físicamente todos los objetos de la tabla.
     * @see #deleteAll()
     */
    void removeAll();
}
