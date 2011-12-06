package mr.common.dao;

import java.util.List;

import mr.common.model.BaseEntity;

/**
 * Interfaz abstracta de un DAO genérico que debe ser implementado por
 * todos los DAOs de entidades que extiendan de {@link BaseEntity}.
 * @author Mariano Ruiz
 */
public interface AbstractDao<DomainObject extends BaseEntity> {

	/**
	 * Obtiene el objeto por su identificador.
	 * @param id identificador del objeto.
	 */
	DomainObject get(Long id);

	/**
	 * Guarda un objeto que no estaba guardado.
	 * @return id identificador del nuevo objeto.
	 */
	Long save(DomainObject entity);

	/**
	 * Actualiza un objeto que ya estaba guardado.
	 */
	void update(DomainObject entity);

	/**
	 * Guarda un objeto nuevo o actualiza si ya existe.
	 */
	void saveOrUpdate(DomainObject entity);

	/**
	 * Marca un objeto como persistente en la sesión actual.
	 */
    void persist(DomainObject entity);

	/**
	 * Marca un objeto como persistente en la sesión actual,
	 * y si hubiera otro con el mismo ID, lo remplaza por este.
	 */
    DomainObject merge(DomainObject entity);

	/**
	 * Quita de la sesión al objeto.
	 * @param entity DomainObject: objeto persistente
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
	 */
    DomainObject refreshEntity(DomainObject entity);

    /**
     * Ejecuta todos los cambios pendientes de la sesión
     * en la base de datos.
     */
    void flush();

	/**
	 * Recupera todos los objetos.
	 */
	List<DomainObject> getList();

	/**
	 * Cuenta todos los objetos guardados.
	 */
	long count();

	/**
	 * Borra el objeto pasado.
	 */
	void delete(DomainObject entity);

	/**
	 * Borra el objeto con el id pasado.
	 * 
	 * @param entity Long
	 */
	void deleteById(Long entity);

	/**
	 * Borra todos los objetos de la lista.
	 */
	void deleteList(List<DomainObject> list);

	/**
	 * Borra todos los objetos de la tabla.
     * @return cantidad de elementos borrados.
	 */
	int deleteAll();
}
