package mr.common.web.servlet;

import javax.servlet.http.HttpServletRequest;


/**
 * Conversiones útiles provenientes de información
 * del request de un submit.
 * @author Mariano Ruiz
 */
public abstract class RequestUtils {

	/**
	 * Retorna el parametro en el request en forma de parámetro
	 * en una URL.<br/>
	 * <p>Ejemplo: <code>param=valorparam</code></p>
	 * <p>Si el parámetro en el request fuera una array, lo parsea
	 * adecuadamente de la forma:</p>
	 * <code>param1=valorparam1&amp;param2=valorparam2&amp;param3=valorparam3</code>
	 * @param request el request de la petición
	 * @param parameterName nombre del parámetro
	 * @return cadena con los parámetros parseados para una URL, o cadena
	 * vacía en caso de que el parámetro no exista en el request
	 */
	public static String parameter(HttpServletRequest request, String parameterName) {
		String[] values = request.getParameterValues(parameterName);
		String param = "";
		if(values!=null) {
			for(int i=0; i<values.length; i++) {
				if(i!=0) {
					param += "&";
				}
				param += parameterName + "=" + values[i];
			}
		}
		return param;
	}

	/**
	 * Retorna el valor del parámetro, o <code>defaultValue</code>
	 * en caso de no existir en el <code>request</code>.<br/>
	 * En caso de ser multivalor, los mismos serán concatenados,
	 * y separados por <code>separator</code>.
	 * @param request la petición con los parámetros
	 * @param parameterName el parámetro con el valor
	 * @param defaultValue el valor por default en caso de no
	 * existir el parámetro en el <code>request</code>
	 * @param separator el separador en caso de que el parámetro
	 * tenga múltiples valores (String[])
	 * @return el valor del parámetro/s, o el valor por default
	 */
	public static String parameterValue(HttpServletRequest request,
			String parameterName, String defaultValue, String separator) {
		String value = "";
		String [] values = request.getParameterValues(parameterName);
		if(values==null) {
			return defaultValue;
		}
		for(int i=0; i<values.length; i++) {
			if(i!=0) {
				value += separator;
			}
			value += values[i];
		}
		return value;
	}

	/**
	 * Retorna el valor del parámetro, o <code>defaultValue</code>
	 * en caso de no existir en el <code>request</code>.<br/>
	 * En caso de ser multivalor, los mismos serán concatenados,
	 * y separados por coma y espacio (, ).
	 * @param request la petición con los parámetros
	 * @param parameterName el parámetro con el valor
	 * @param defaultValue el valor por default en caso de no
	 * existir el parámetro en el <code>request</code>
	 * @return el valor del parámetro/s, o el valor por default
	 */
	public static String parameterValue(HttpServletRequest request,
			String parameterName, String defaultValue) {
		return parameterValue(request, parameterName, defaultValue, ", ");
	}

	/**
	 * Retorna <code>true</code> si el parámetro del request tiene el valor esperado. Si
	 * el parámetro no existe retorna <code>false</code>, y si el parámetro tiene múltiples
	 * valores (array), retorna <code>true</code> si al menos uno de sus valores es
	 * igual a <code>value</code>.
	 * @param request el request de la petición
	 * @param parameterName nombre del parámetro
	 * @param value el valor esperado
	 * @return <code>true</code> si el parámetro tiene el valor valor esperado
	 */
	public static boolean hasValue(HttpServletRequest request, String parameterName, String value) {
		String[] values = request.getParameterValues(parameterName);
		if(values==null) {
			return false;
		}
		for(int i=0; i<values.length; i++) {
			if(values[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
}
