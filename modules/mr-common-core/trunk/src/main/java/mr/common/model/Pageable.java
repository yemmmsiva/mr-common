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
	 * @return int
	 */
	public int getStart();
	/**
	 * Limite de elementos por página.
	 * @return int
	 */
	public int getLimit();
	/**
	 * @return <code>true</code> si el objeto
	 * actual es paginable
	 */
	public boolean isPageable();
}
