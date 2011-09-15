package mr.common.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mr.common.comparator.ConfigurableComparator;
import mr.common.comparator.PropertyComparator;
import mr.common.i18n.spring.MessageUtils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.springframework.util.StringUtils;


/**
 * Clase útiles para trabajar con colecciones.
 * @author Mariano Ruiz
 */
public class CollectionUtils {


    /**
     * Recorre toda la lista y obtiene por cada objeto el elemento obtenido de <code>propertyExpression</code>, y concatena
     * en un <code>String</code> el resultado de cada uno separados por <code>separator</code>,
     * y encerrado cada valor por <code>enclosedSymbol</code>.
     * @param list List: lista de objetos
     * @param propertyExpression String: expresión java bean de la propiedad con el texto o clave i18n
     * @param separator String: literal que separará cada elemento en la cadena,
     * ej. una coma y espacio: <code>', '</code>
     * @param enclosedSymbol String: literal que contendrá cada elemento en la cadena,
     * ej. comillas simples: <code>''</code>
     * @return String
	 * @throws RuntimeException si la property expression es erronea
     */
    @SuppressWarnings("rawtypes")
	public static String objectListToString(List list, String propertyExpression, String separator, String enclosedSymbol) throws RuntimeException {
    	return objectListToString(list, propertyExpression, separator, enclosedSymbol, null);
    }


    /**
     * Recorre toda la lista y obtiene por cada objeto el elemento obtenido de <code>propertyExpression</code>, y concatena
     * en un <code>String</code> el resultado (internacionalizado o no) de cada uno separados por <code>separator</code>,
     * y encerrado cada valor por <code>enclosedSymbol</code>.<br/>
     * Si <code>prefix</code> es distinto de nulo se usa como prefijo del string de <code>propertyExpression</code> para
     * armar la clave i18n y obtener el texto internacionalizado.
     * @param list List: lista de objetos
     * @param propertyExpression String: expresión java bean de la propiedad con el texto o clave i18n
     * @param separator String: literal que separará cada elemento en la cadena,
     * ej. una coma y espacio: <code>', '</code>
     * @param enclosedSymbol String: literal que contendrá cada elemento en la cadena,
     * ej. comillas simples: <code>''</code>
     * @param prefix String: prefijo de clave i18n
     * @return String
	 * @throws RuntimeException si la property expression es erronea
     */
    @SuppressWarnings("rawtypes")
	public static String objectListToString(List list, String propertyExpression, String separator, String enclosedSymbol, String prefix) throws RuntimeException {
    	if(list==null || list.size()==0) {
    		return "";
    	}
    	String text = "";
    	try {
	    	for(Object obj : list) {
	    		if(!text.equals("")) {
	    			text += enclosedSymbol + separator + enclosedSymbol;
	    		} else {
	    			text += enclosedSymbol;
	    		}
	    		if(prefix==null) {
	    			text += PropertyUtils.getProperty(obj, propertyExpression).toString();
	    		} else {
	    			text += MessageUtils.getMessageSource().getMessage(prefix + PropertyUtils.getProperty(obj, propertyExpression).toString(),
	    					                                           null, MessageUtils.getLocale());
	    		}
	    	}
			if(!text.equals("")) {
				text += enclosedSymbol;
			}
    	} catch(Exception e) {
    		throw new RuntimeException(e);
    	}
    	return text;
    }

    /**
     * Recorre toda la lista y obtiene por cada objeto el elemento obtenido de <code>propertyExpression</code>, y
     * los devuelve en una lista nueva.<br/>
     * @param list lista de objetos
     * @param propertyExpression expresión java bean de la propiedad
     * @return lista nueva con los objetos de la expresión. Si <code>list=null</code> retorna
     * <code>null</code>
	 * @throws RuntimeException si la property expression es erronea
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List objectListToList(List list, String propertyExpression) throws RuntimeException {
    	if(list==null || list.size()==0) {
    		return list;
    	}
    	List result = new ArrayList(list.size());
    	try {
	    	for(Object obj : list) {
	    		result.add(PropertyUtils.getProperty(obj, propertyExpression));
	    	}
    	} catch(Exception e) {
    		throw new RuntimeException(e);
    	}
    	return result;
    }

    /**
     * Recorre toda la lista y obtiene por cada objeto el elemento obtenido de <code>propertyExpression</code>, y concatena
     * en un <code>String</code> el resultado de cada uno separados por coma.
     * @param list List: lista de objetos
     * @param propertyExpression String: expresión java bean de la propiedad con el texto o clave i18n
     * @return String
	 * @throws RuntimeException si la property expression es erronea
     */
    @SuppressWarnings({ "rawtypes" })
	public static String objectListToString(List list, String propertyExpression) throws RuntimeException {
    	return objectListToString(list, propertyExpression, ", ", "");
    }

    /**
     * Recorre toda la lista y obtiene por cada objeto el elemento obtenido de <code>propertyExpression</code>,
     * le concatena el <code>prefix</code> y se obtiene el valor internacionalizado y se concatena
     * en un <code>String</code> el resultado de cada uno separados por coma.<br/>
     * Si <code>prefix</code> es distinto de nulo se usa como prefijo del string de <code>propertyExpression</code> para
     * armar la clave i18n y obtener el texto internacionalizado.
     * @param list List: lista de objetos
     * @param propertyExpression String: expresión java bean de la propiedad con el texto o clave i18n
     * @param prefix String: prefijo de clave i18n
     * @return String
	 * @throws RuntimeException si la property expression es erronea
     */
    @SuppressWarnings({ "rawtypes" })
	public static String objectListToString(List list, String propertyExpression, String prefix) throws RuntimeException {
    	return objectListToString(list, propertyExpression, ", ", "", prefix);
    }

    /**
     * Transforma una cadena de texto con palabras separadas por <a
     * href="../util/regex/Pattern.html#sum">regular expression</a>
     * a una lista de <code>String</code>.
     * @param stringList String: cadena que contiene las palabras a separar
     * @param regex String: Expresión regular (o literal)
     * @return lista de String, vacía si <code>stringList</code> es no
     * contiene texto
     */
    public static List<String> stringToObjectList(String stringList, String regex) {
		if(StringUtils.hasText(stringList)) {
			return Arrays.asList(stringList.split(regex));
		}
		return new ArrayList<String>(0);
    }

    /**
     * Transforma una cadena de texto con palabras separadas por comas <code>,</code>
     * a una lista de <code>String</code>.
     * @param stringList String: cadena que contiene las palabras a separar
     * @return lista de String, vacía si <code>stringList</code> es no
     * contiene texto
     */
    public static List<String> stringToObjectList(String stringList) {
    	return stringToObjectList(stringList, ",");
    }

    /**
     * Ordena la lista utilizando las property expression de java beans pasadas para
     * obtener los valores que serán utilizados en la comparación.<br/>
     * El objeto retornado por la expresión debe implementar {@link java.lang.Comparable#compareTo(Object)},
     * ya que se usa el <code>compareTo(Object)</code> para obtener el resultado.<br/>
     * Si son strings los objetos obtenidos de la expresión, se usa
     * {@link String#compareToIgnoreCase(String)}.
     * @param list List: lista a ser ordenada
     * @param methodName String []: nombre del método que se utiliza para la ordenación.
	 * @param orders [] int: para cada expression el orden de comparación,
	 * <ul>
	 *   <li><i>{@link ConfigurableComparator#ORDER_AZ}  (1)</i>: orden ascendente</li>
	 *   <li><i>{@link ConfigurableComparator#ORDER_ZA} (-1)</i>: orden descendente</li>
	 * </ul>
	 * Si <code>orders</code> es <code>null</code>, se orderna con todas las expresiones en forma ascendente
     * @return List: lista ordenada
	 * @throws RuntimeException si la property expression es erronea
	 *
 	 * @see java.util.Comparator
 	 * @see mr.common.comparator.PropertyComparator
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List sortListByProperties(List list, String [] propertyExpressions, int [] orders) {
		if(list.isEmpty()) {
			return list;
		}
		if(propertyExpressions.length == 0) {
			throw new IllegalArgumentException("propertyExpressions.length = 0");
		}
		if(orders!=null && propertyExpressions.length != orders.length) {
			throw new IllegalArgumentException("propertyExpressions.length != orders.length");
		}
		ComparatorChain comparators = new ComparatorChain();
		Comparator comparator;
		for(int i=0; i<propertyExpressions.length; i++) {
			int order;
			if(orders!=null) {
				order = orders[i];
			} else {
				order = ConfigurableComparator.ORDER_AZ;
			}
			comparator = new PropertyComparator(propertyExpressions[i], order);
			comparators.addComparator(comparator);
		}
		Collections.sort(list, comparators);
		return list;
	}

	/**
	 * Similar a {@link java.util.Arrays#asList(Object...)}, pero retorna
	 * un {@link java.util.Set Set} en vez de una lista (tener en cuenta que
	 * los objetos repetidos solo serán incluidos una vez).
	 * @param array
	 * @return {@link java.util.Set}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Set asSet(Object... array) {
		Arrays.asList(array);
		Set set = new HashSet(array.length);
		for(int i=0; i<array.length; i++) {
			set.add(array[i]);
		}
		return set;
	}
}
