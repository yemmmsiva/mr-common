package mr.common.dao;

import java.util.List;

import mr.common.model.AuditableDictionary;


/**
 * Interfaz abstracta de un DAO genérico que debe ser implementado por
 * todos los DAOs de entidades que extiendan de {@link AuditableDictionary}.
 * @author Mariano Ruiz
 */
public interface AbstractAuditableDictionaryDao<Dict extends AuditableDictionary>
    extends AbstractAuditableDao<Dict> {

	/**
	 * Obtiene el elemento por su código.
	 */
	Dict getByCode(String code);

	/**
	 * Obtiene todos los elementos que en su descripción contengan
	 * a <code>description</code>.
	 */
	List<Dict> findByLikeDescription(String description);

	/**
	 * Obtiene todos los elementos que en su descripción larga contengan
	 * a <code>largeDescription</code>.
	 */
	List<Dict> findByLikeLargeDescription(String largeDescription);

	/**
	 * Obtiene todos los elementos que en su descripción, descripción larga
	 * o código contengan <code>description</code>.
	 */
	List<Dict> find(String description);
}
