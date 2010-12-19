package mr.common.model;

import java.io.Serializable;


/**
 * Modelo de datos ordenable.<br/>
 * {@link #getSorts()} devuelve un array de strings
 * con los campos de ordenación y el orden
 * (ascendente o descendente).<br/>
 * Verificar si la información es ordenable
 * con {@link #isSortable()}.
 * 
 * @author Mariano Ruiz
 */
public interface Sortable extends Serializable {

	/**
	 * Campo (o campos) por los que se deber ordernar.
	 * @return array de string, cada uno con formatos
	 * como los siguientes:
	 * <ul>
	 *   <li>'id' (<i>por default order ascendente</i>)</li>
	 *   <li>'id ASC' (<i>ascendente</i>)</li>
	 *   <li>'name DESC' (<i>descendente</i>)</li>
	 * </ul>
	 */
	String[] getSorts();

	/**
	 * @return <code>true</code> si los datos
	 * deben ser odernados
	 */
	boolean isSortable();
}
