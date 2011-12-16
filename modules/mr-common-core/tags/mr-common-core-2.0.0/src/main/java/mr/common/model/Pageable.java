package mr.common.model;

import java.io.Serializable;


/**
 * Modelo de datos paginable.<br/>
 * Los datos son traídos desde {@link #getStart()},
 * con un límite de datos máximo obtenido
 * de {@link #getLimit()}.<br/>
 * Como la paginación puede ser opcional,
 * {@link #isPageable()} determina si
 * hay paginación.
 * 
 * @author Mariano Ruiz
 */
public interface Pageable extends Serializable {

	/**
	 * Primer elemento de la página.
	 */
	Integer getStart();
	/**
	 * Limite de elementos por página.
	 */
	Integer getLimit();
	/**
	 * @return <code>true</code> si el objeto
	 * actual es paginable
	 */
	boolean isPageable();
}
