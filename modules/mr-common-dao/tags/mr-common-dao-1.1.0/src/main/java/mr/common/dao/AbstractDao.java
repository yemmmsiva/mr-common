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
	 * 
	 * @param id Long: identificador del objeto
	 * @return DomainObject
	 */
	DomainObject get(Long id);

	/**
	 * Guarda un objeto que no estaba guardado.
	 * 
	 * @param entity DomainObject: objeto a guardar
	 * @return id Long: identificador del nuevo objeto
	 */
	Long save(DomainObject entity);

	/**
	 * Actualiza un objeto que ya estaba guardado.
	 * 
	 * @param entity DomainObject
	 */
	void update(DomainObject entity);

	/**
	 * Guarda un objeto nuevo o actualiza si ya existe.
	 * 
	 * @param entity DomainObject
	 */
	void saveOrUpdate(DomainObject entity);

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
	 * Recupera todos los objetos.
	 * 
	 * @return List DomainObject
	 */
	List<DomainObject> getList();

	/**
	 * Cuenta todos los objetos guardados.
	 * 
	 * @return long
	 */
	long count();

	/**
	 * Borra el objeto pasado.
	 * 
	 * @param entity DomainObject
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
	 * 
	 * @param list List
	 */
	void deleteList(List<DomainObject> list);

	/**
	 * Borra todos los objetos de la tabla.
	 */
	void deleteAll();
}
