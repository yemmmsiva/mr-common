package mr.common.comparator;

import org.apache.commons.beanutils.PropertyUtils;


/**
 * Este comparador utiliza el property expression pasado en el constructor para obtener el valor
 * que será utilizado en la comparación.
 * El valor de retorno del mismo debe implementar {@link java.lang.Comparable},
 * ya que se usa el <code>compareTo(Object)</code> para obtener el resultado.<br/>
 * Si son strings los objetos obtenidos de la expresión, se usa
 * {@link String#compareToIgnoreCase(String)}.
 * Extiende de {@link mr.common.comparator.ConfigurableComparator ConfigurableComparator}, por
 * lo que también se puede configurar el orden de comparación.
 *
 * @see java.util.Comparator
 * @author Mariano Ruiz
 */
@SuppressWarnings("rawtypes")
public class PropertyComparator extends ConfigurableComparator {

	private String propertyExpression;


	/**
	 * @see mr.common.comparator.PropertyComparator
	 * @param propertyExpression String: expresión java beans del valor a comparar.<br/>
	 * Por lo tanto el valor obtenido de la expresión debe implementar {@link java.lang.Comparable}
	 * @throws RuntimeException si la property expression es erronea
	 *
	 * @see PropertyComparator
	 */
	public PropertyComparator(String propertyExpression) {
		this.propertyExpression = propertyExpression;
	}


	/**
	 * @see mr.common.comparator.PropertyComparator
	 * @param propertyExpression String: expresión java beans del valor a comparar.<br/>
	 * Por lo tanto el valor obtenido de la expresión debe implementar {@link java.lang.Comparable}
	 * @throws RuntimeException si la property expression es erronea
	 * @param order int:
	 * <ul>
	 *   <li><i>{@link ConfigurableComparator#ORDER_AZ}  (1)</i>: orden ascendente</li>
	 *   <li><i>{@link ConfigurableComparator#ORDER_ZA} (-1)</i>: orden descendente</li>
	 * </ul>
	 *
	 * @see PropertyComparator
	 */
	public PropertyComparator(String propertyExpression, int order) {
		super(order);
		this.propertyExpression = propertyExpression;
	}

	@SuppressWarnings("unchecked")
	public int compare(Object o1, Object o2) {
		Comparable c1=null, c2=null;
    	try {
	    	c1 = (Comparable) PropertyUtils.getProperty(o1, propertyExpression);
	    	c2 = (Comparable) PropertyUtils.getProperty(o2, propertyExpression);
    	} catch(Exception e) {
    		throw new RuntimeException(e);
    	}
    	if(c1 instanceof String && c2 instanceof String) {
    		return getOrder() * ((String)c1).compareToIgnoreCase((String)c2);
    	}
    	return getOrder() * c1.compareTo(c2);
	}
}
