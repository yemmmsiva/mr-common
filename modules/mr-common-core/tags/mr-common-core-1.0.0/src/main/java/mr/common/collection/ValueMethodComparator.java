package mr.common.collection;

import java.util.Comparator;

import org.apache.commons.beanutils.PropertyUtils;


/**
 * Este comparador utiliza el el property expression pasado en el constructor para obtener el valor
 * que será utilizado en la comparación.
 * El valor de retorno del mismo debe implementar {@link java.lang.Comparable#compareTo(Object)},
 * ya que se usa el <code>compareTo(Object)</code> para obtener el resultado.
 *
 * @see java.util.Comparator
 * @author Mariano Ruiz
 */
@SuppressWarnings("rawtypes")
public class ValueMethodComparator implements Comparator {

	private boolean ascending;
	private String propertyExpression;


	/**
	 * @see mr.common.collection.ValueMethodComparator
	 * @param objectClass Class: clase del objecto del cual se ejecutará
	 * <code>methodName</code>
	 * @param propertyExpression String: expresión java beans del valor a comparar.<br/>
	 * Por lo tanto el valor obtenido de la expresión debe implementar {@link java.lang.Comparable}
	 * @param ascending boolean: <code>true</code> si es comparación ascendente
	 * @throws RuntimeException: si el nombre del método es erroneo o no accesible
	 *
	 * @see ValueMethodComparator
	 */
	public ValueMethodComparator(Class objectClass, String propertyExpression, boolean ascending) {
		this.ascending = ascending;
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
    	if(ascending) {
    		return c1.compareTo(c2);
    	}
    	return c2.compareTo(c1);
	}
}
