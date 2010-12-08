package mr.common.model;

import java.io.Serializable;


/**
 * Modelo de datos paginable.
 * @author Mariano Ruiz
 *
 */
public interface Pageable extends Serializable {

	/**
	 * Primer elemento de la página.
	 * @return Integer
	 */
	Integer getStart();
	/**
	 * Limite de elementos por página.
	 * @return Integer
	 */
	Integer getLimit();
	/**
	 * @return <code>true</code> si el objeto
	 * actual es paginable
	 */
	boolean isPageable();
	/**
	 * Campo (o campos) por los que se deber ordernar.
	 * @return array de string, cada uno con los siguiente
	 * formatos:
	 * <ul>
	 *   <li>id (<i>por default order ascendente</i>)</li>
	 *   <li>id ASC (<i>ascendente</i>)</li>
	 *   <li>name DESC (<i>descendente</i>)</li>
	 * </ul>
	 */
	String[] getSort();

	/**
	 * @return <code>true</code> si los datos
	 * deben ser odernados
	 */
	boolean isSorteable();
}
