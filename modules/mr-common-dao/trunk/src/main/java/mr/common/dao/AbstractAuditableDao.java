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
	 * @param id identificador del objeto.
	 */
	DomainObject get(Long id);

	/**
	 * Obtiene el objeto por su identificador, ignorando si
	 * está borrado lógicamente.
	 * @param id identificador del objeto.
	 * @see #get(Long)
	 */
	DomainObject getIgnoreIsDeleted(Long id);

	/**
	 * Guarda un objeto que no estaba guardado.
	 * @param entity objeto a guardar.
	 * @return id identificador del nuevo objeto.
	 */
    Long save(DomainObject entity);

	/**
	 * Marca un objeto como persistente en la sesión actual.
	 * @param entity objeto
	 */
    void persist(DomainObject entity);

	/**
	 * Marca un objeto como persistente en la sesión actual,
	 * y si hubiera otro con el mismo ID, lo remplaza por este.
	 * @param entity objeto.
	 */
    DomainObject merge(DomainObject entity);

	/**
	 * Quita de la sesión al objeto.
	 * @param entity objeto persistente.
	 */
    void detach(DomainObject entity);

	/**
	 * Refrezca el objeto desde la base de datos.
	 * @param entity objeto persistente.
	 */
    void refresh(DomainObject entity);

	/**
	 * Quita de la sesión al objeto, ejecuta todas
	 * las operaciones pendientes de la sesión,
	 * y retorna una versión refrezcada de la entidad.
	 * @param entity objeto persistente.
	 */
    DomainObject refreshEntity(DomainObject entity);

    /**
     * Ejecuta todos los cambios pendientes de la sesión
     * en la base de datos.
     */
    void flush();

    /**
     * Actualiza un objeto que ya estaba guardado.
     */
    void update(DomainObject entity);

    /**
     * Guarda un objeto nuevo o actualiza si ya existe.
     */
    void saveOrUpdate(DomainObject entity);

    /**
     * Recupera todos los objetos.
     * @param cacheable <code>true</code> para
     * que la lista quede cacheada en memoria.
     * @see #getList()
     */
    List<DomainObject> getList(boolean cacheable);

    /**
     * Recupera todos los objetos.
     * @see #getList(boolean)
     */
    List<DomainObject> getList();

    /**
     * Recupera todos los objetos, incluso los borrados
     * lógicamente.
     * @see #getList()
     */
    List<DomainObject> getListAll();

    /**
     * Recupera todos los objetos borrados lógicamente.
     * @see #getList()
     * @see #getListAll()
     */
    List<DomainObject> getDeletedList();

    /**
     * Cuenta todos los objetos.
     * @see #countAll()
     * @see #countDeleted()
     */
    long count();

    /**
     * Cuenta todos los objetos incluso los borrados
     * lógicamente.
     * @see #count()
     * @see #countDeleted()
     */
    long countAll();

    /**
     * Cuenta todos los objetos borrados lógicamente. 
     * @see #count()
     * @see #countAll()
     */
    long countDeleted();

    /**
     * Borra lógicamente el objeto pasado.
     * @see #remove(AuditableEntity)
     */
    void delete(DomainObject entity);

    /**
     * Borra físicamente el objeto pasado.
     * @see #delete(AuditableEntity)
     */
    void remove(DomainObject entity);

    /**
     * Borra lógicamente el objeto con el id pasado.
     * @see #removeById(Long)
     */
    void deleteById(Long entity);

    /**
     * Borra lógicamente todos los objetos de la lista.
     * @see #removeList(List)
     */
    void deleteList(List<DomainObject> list);

    /**
     * Borra físicamente el objeto con el id pasado.
     * @see #deleteById(Long)
     */
    void removeById(Long entity);

    /**
     * Borra físicamente todos los objetos de la lista.
     * @see #deleteList(List)
     */
    void removeList(List<DomainObject> list);

    /**
     * Borra lógicamente todos los objetos de la tabla.
     * @return cantidad de elementos borrados.
     * @see #removeAll()
     */
    int deleteAll();

    /**
     * Borra físicamente todos los objetos de la tabla.
     * @return cantidad de elementos borrados.
     * @see #deleteAll()
     */
    int removeAll();
}
