package mr.common.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import mr.common.model.BaseEntity;
import mr.common.spring.context.Bean;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.hql.ParameterTranslations;
import org.hibernate.hql.QueryTranslator;
import org.hibernate.hql.QueryTranslatorFactory;
import org.hibernate.hql.ast.ASTQueryTranslatorFactory;
import org.hibernate.proxy.HibernateProxy;


/**
 * Funciones útiles para usar con Hibernate.
 * @author Mariano Ruiz
 */
public class HibernateUtils {

    /**
     * Carga los parámetros del hash en la query.
     * @param query {@link org.hibernate.Query}
     * @param params {@link java.util.Map}
     */
    @SuppressWarnings("rawtypes")
	public static void setParameters(Query query, Map<String, Object> params) {
        for (String key : params.keySet()) {
            Object param = params.get(key);
            if(param instanceof Collection) {
            	query.setParameterList(key, (Collection)param);
            } else if(param instanceof Object[]) {
            	query.setParameterList(key, (Object[])param);
            } else {
            	query.setParameter(key, param);
            }
        }
    }

    /**
     * Retorna la cantidad de elementos de una colección de objetos obtenida
     * por Hibernate. La ventaja de usar este método en vez de <code>size()</code>
     * es que si la colección aún no fue inicializada (lazy loadding), la
     * cantidad será calculada a partir de una query, y no se traerán los objetos
     * de la misma.
     * @param session {@link org.hibernate.Session}
     * @param collection {@link Collection}
     * @return int
     */
    public static int countCollection(Session session, Collection<?> collection) {
        if (Hibernate.isInitialized(collection)) {
            return collection.size();
        } else {
            return ((Number)
              session.createFilter(collection, "select cast(count(*) as int)").uniqueResult()).intValue();
        }
    }

    /**
     * Retorna la cantidad de elementos de una query HQL, pero sin necesidad de traer los
     * elementos o tener que armar la query HQL con el count equivalente.
     * @param session {@link org.hibernate.Session}
     * @param hql String
     * @param parameters {@code Map<String, Object>}
     * @return int
     * @see #countQuery(Session, String, Map)
     * @see #countCollection(Session, Collection)
     */
    public static int count(Session session, String hql, Map<String, Object> parameters) {
        Query q = session.createSQLQuery("select count(*) from (" + hqlToSql(session, hql, parameters) + ") as t");
        setParameters(q, parameters);
        return ((Number) q.uniqueResult()).intValue();
    }

    /**
     * Retorna un objeto {@link org.hibernate.Query Query} a partir de la query HQL pasada
     * y los parámetros, pero convertida a una query que cuente los elementos en vez de
     * traerlos.
     * @param session {@link org.hibernate.Session}
     * @param hql String
     * @param parameters {@code Map<String, Object>}
     * @return {@link org.hibernate.Query Query}
     * @see #count(Session, String, Map)
     * @see #countCollection(Session, Collection)
     */
    public static Query countQuery(Session session, String hql, Map<String, Object> parameters) {
        Query q = session.createSQLQuery("select count(*) from (" + hqlToSql(session, hql, parameters) + ") as t");
        setParameters(q, parameters);
        return q;
    }

    /**
     * Convierte la query HQL en su query SQL equivalente (teniendo en cuenta el dialécto de la sesión).
     * También reemplaza los parámetros pasados a la query HQL.
     * @param session {@link org.hibernate.Session}
     * @param hql String
     * @param parameters {@code Map<String, Object>}
     * @return String
     */
    public static String hqlToSql(Session session, String hql, Map<String, Object> parameters) {
        final QueryTranslatorFactory translatorFactory = new ASTQueryTranslatorFactory();
        final SessionFactoryImplementor factory = (SessionFactoryImplementor) session.getSessionFactory();
        final QueryTranslator translator = translatorFactory.createQueryTranslator(hql, hql, Collections.EMPTY_MAP, factory);
        translator.compile(Collections.EMPTY_MAP, false);
        return convertSQLParameters(translator.getSQLString(), translator.getParameterTranslations(), parameters);

    }

	    /**
	     * Este método sirve para recuperar un SQL en String y sustituir los '?' por los nombres de los parametros originales para generar un PreparedStatement
	     * @param sql String
	     * @param pt {@link ParameterTranslations}
	     * @param parameters {@code Map<String, Object>}
	     * @return String
	     */
	    private static String convertSQLParameters(String sql, ParameterTranslations pt, Map<String, Object> parameters) {
	        if (parameters == null || parameters.size() == 0) {
	        	return sql;
	        }
	        int contador = 0;
	        while (StringUtils.contains(sql, '?')) {
	            sql = StringUtils.replaceOnce(sql, "?", ":" + contador);
	            contador++;
	        }
	        for (String key : parameters.keySet()) {
	            int locations[] = pt.getNamedParameterSqlLocations(key);
	            for (int location : locations) {
	                sql = StringUtils.replaceOnce(sql, ":" + location, ":" + key);
	            }
	        }
	        return sql;
	    }

    /**
     * @param entity {@link mr.common.model.BaseEntity}
     * @return El mismo objeto pero si 'proxear' por Hibernate.
     */
    @SuppressWarnings("unchecked")
	public static <DomainObject extends BaseEntity> DomainObject unproxy(DomainObject entity) {
        if (entity instanceof HibernateProxy) {
            return (DomainObject)((HibernateProxy)entity).getHibernateLazyInitializer().getImplementation();
        } else {
            return entity;
        }
    }

    /**
     * Detacha el objeto persistente de la sesión.
     * @param entity {@link mr.common.model.BaseEntity}
     */
	public static void detach(BaseEntity entity) {
		((Session)Bean.get(Session.class)).evict(entity);
	}

    /**
     * Atacha el objeto persistente a la sesión, y devuelve
     * una versión refrezcada del mismo.
     * @param entity {@link mr.common.model.BaseEntity}
     */
	@SuppressWarnings("unchecked")
	public static <DomainObject extends BaseEntity> DomainObject attach(DomainObject entity) {
		return (DomainObject) ((Session)Bean.get(Session.class)).merge(entity);
	}

	/**
	 * Flush de la cache de sesión.
	 */
    public static void flush() {
        ((Session)Bean.get(Session.class)).flush();
    }
}
