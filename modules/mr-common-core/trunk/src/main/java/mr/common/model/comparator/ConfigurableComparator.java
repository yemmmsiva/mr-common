package mr.common.model.comparator;

import java.util.Comparator;


/**
 * Clase que implementa {@link java.util.Comparator Comparator}, y tiene
 * configurado el order de comparación:
 * <ul>
 *   <li><i>{@link #ORDER_AZ}  (1)</i>: orden ascendente</li>
 *   <li><i>{@link #ORDER_ZA} (-1)</i>: orden descendente</li>
 * </ul>
 * También implementa el método
 * {@link java.util.Comparator#compare(Object, Object) Comparator.compare(Object, Object)}
 * que usa {@link String#compareToIgnoreCase(String)} de <code>o1</code>
 * para compararlo con <code>o2</code> si ambos son strings,
 * sino invoca al método <code>compareTo(Object)</code>
 * para efectuar la comparación. Por lo general este método será sobreescrito, pero
 * se debe respetar el orden configurado.
 *
 * @param <T> clase de los objetos a comparar
 *
 * @author Mariano Ruiz
 */
public class ConfigurableComparator<T> implements Comparator<T> {

	/**
	 * Orden ascendente.
	 */
	public static int ORDER_AZ =  1;
	/**
	 * Orden descendente.
	 */
	public static int ORDER_ZA = -1;

	private int order = 1;

	/**
	 * Constructor por defecto, el orden de comparación
	 * será ascendente ({@link #ORDER_AZ}).
	 */
	public ConfigurableComparator() { }

	/**
	 * Constructor con el orden de comparación como parámetro.
	 * @param order int:
	 * <ul>
	 *   <li><i>{@link #ORDER_AZ}  (1)</i>: orden ascendente</li>
	 *   <li><i>{@link #ORDER_ZA} (-1)</i>: orden descendente</li>
	 * </ul>
	 */
	public ConfigurableComparator(int order) {
		this.order = order;
	}

	/**
	 * @return int - orden de comparación:
	 * <ul>
	 *   <li><i>{@link #ORDER_AZ}  (1)</i>: orden ascendente</li>
	 *   <li><i>{@link #ORDER_ZA} (-1)</i>: orden descendente</li>
	 * </ul>
	 */
	public int getOrder() {
		return order;
	}
	/**
	 * Orden de comparación:
	 * @param order int:
	 * <ul>
	 *   <li><i>{@link #ORDER_AZ}  (1)</i>: orden ascendente</li>
	 *   <li><i>{@link #ORDER_ZA} (-1)</i>: orden descendente</li>
	 * </ul>
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * Implementación de comparación que llama a los métodos
	 * {@link String#compareToIgnoreCase(String)} de <code>o1</code>
	 * para compararlo con <code>o2</code> si ambos son strings,
	 * sino invoca al método <code>compareTo(Object)</code>
	 * para efectuar la comparación.<br/>
	 * El orden configurado determinará si la comparación
	 * es ascendente o descendente.<br/>
	 * Por lo general este método será sobreescrito, pero se
	 * debe tener en cuenta el order, que se puede obtener
	 * con {@link #getOrder()}.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int compare(T o1, T o2) {
		if(o1 instanceof String && o2 instanceof String) {
			return order * ((String)o1).compareToIgnoreCase((String)o2);
		}
		return order * ((Comparable)o1).compareTo(o2);
	}
}
