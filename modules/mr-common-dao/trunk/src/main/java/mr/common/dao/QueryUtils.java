package mr.common.dao;


/**
 * Funciones útiles para querys SQL/HQL.
 * @author Mariano Ruiz
 */
public class QueryUtils {

    /**
     * Retorna el parámetro con prefijo y sufijo <code>%</code> para
     * una query con el operador <code>LIKE</code>, convertido a
     * minúsculas y filtrando caracteres inválidos.<br />
     * TODO Completar el filtro de caracteres inválidos.
     */
    public static String likeParam(String param) {
    	return "%" + param.toLowerCase().replaceAll("'", "''") + "%";
    }
}
