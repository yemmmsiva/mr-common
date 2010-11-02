package mr.common.collection;

import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * Este comparador utiliza el nombre del método pasado en el constructor para obtener el valor
 * que será utilizado en la comparación.
 * El valor de retorno del mismo debe implementar {@link java.lang.Comparable#compareTo(Object)},
 * ya que se usa el <code>compareTo(Object)</code> para obtener el resultado.
 * @see java.util.Comparator  
 * @author mruiz
 */
@SuppressWarnings("rawtypes")
public class ValueMethodComparator implements Comparator {

	private boolean ascending;
	private Class params[] = {};
	Object paramsObj[] = {};
	Method method = null;


	/**
	 * @see mr.common.collection.ValueMethodComparator
	 * @param objectClass Class: clase del objecto del cual se ejecutará
	 * <code>methodName</code>
	 * @param methodName String: nombre del método que se va a invocar para
	 * obtener el objeto que se va a utilizar en la comparación.<br/>
	 * El retorno del mismo por lo tanto debe implementar {@link java.lang.Comparable}
	 * @param ascending boolean: <code>true</code> si es comparación ascendente
	 * @see ValueMethodComparator
	 * @throws RuntimeException: si el nombre del método es erroneo o no accesible
	 */
	@SuppressWarnings("unchecked")
	public ValueMethodComparator(Class objectClass, String methodName, boolean ascending) {
		this.ascending = ascending;
    	try {
			method = objectClass.getMethod(methodName, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "unchecked" })
	public int compare(Object o1, Object o2) {
		Comparable c1=null, c2=null;
    	try {
	    	c1 = (Comparable) method.invoke(o1, paramsObj);
	    	c2 = (Comparable) method.invoke(o2, paramsObj);
    	} catch(Exception e) {
    		throw new RuntimeException(e);
    	}
    	if(ascending) {
    		return c1.compareTo(c2);
    	}
    	return c2.compareTo(c1);
	}
}
