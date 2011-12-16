package mr.common.dao;

import java.util.List;

import mr.common.model.AuditableDictionary;


/**
 * Interfaz abstracta de un DAO genérico con los métodos comunes
 * para leer de  cualquier entidade que extienda de {@link AuditableDictionary}.
 * @author Mariano Ruiz
 */
public interface GenericAuditableDictionaryDao {

	/**
	 * Obtiene el objeto por el nombre de su clase e identificador.
	 * @param classNameDicEntity nombre de la clase.
	 * @param id identificador del objeto.
	 */
	AuditableDictionary get(String classNameDicEntity, Long id);

    /**
     * Recupera todos los objetos guardados de la clase pasada.
     * @param classNameDicEntity nombre de la clase.
     */
	List<AuditableDictionary> getList(String classNameDicEntity);

    /**
     * Cuenta todos los objetos guardados de la clase pasada.
     * @param classNameDicEntity nombre de la clase.
     */
	long count(String classNameDicEntity);

	/**
	 * Obtiene el elemento por su código y nombre de clase.
	 * @param classNameDicEntity nombre de la clase.
	 */
	AuditableDictionary getByCode(String classNameDicEntity, String code);

	/**
	 * Obtiene todos los elementos que en su descripción contengan
	 * a <code>description</code> de la clase pasada.
	 * @param classNameDicEntity nombre de la clase.
	 */
	List<AuditableDictionary> findByLikeDescription(String classNameDicEntity, String description);

	/**
	 * Obtiene todos los elementos que en su descripción larga contengan
	 * a <code>largeDescription</code> de la clase pasada.
	 * @param classNameDicEntity nombre de la clase.
	 */
	List<AuditableDictionary> findByLikeLargeDescription(String classNameDicEntity, String largeDescription);

	/**
	 * Obtiene todos los elementos que en su descripción, descripción larga
	 * o código contengan <code>description</code> de la clase pasada.
	 * @param classNameDicEntity nombre de la clase.
	 */
	List<AuditableDictionary> find(String classNameDicEntity, String description);
}
