package mr.common.context;


/**
 * Devuelve la configuración del entorno de ejecución. Puede
 * ser alguno de los cuatro casos más comunes,
 * <ul>
 *   <li>{@link #ENVIRONMENT_DEVELOPMENT}</li>
 *   <li>{@link #ENVIRONMENT_TEST}</li>
 *   <li>{@link #ENVIRONMENT_PRE_PRODUCTION}</li>
 *   <li>{@link #ENVIRONMENT_PRODUCTION}</li>
 * </ul>
 * u otros tipos definidos por la aplicación.
 *
 * @author Mariano Ruiz
 */
public interface EnvironmentConfiguration {

	/**
	 * <i>`development`</i>: Entorno de desarrollo.
	 */
	static final String ENVIRONMENT_DEVELOPMENT = "development";
	/**
	 * <i>`test`</i>: Entorno de testing.
	 */
	static final String ENVIRONMENT_TEST   = "test";
	/**
	 * <i>`pre-production`</i>: Entorno de pre-producción.
	 */
	static final String ENVIRONMENT_PRE_PRODUCTION   = "pre-production";
	/**
	 * <i>`production`</i>: Entorno de producción.
	 */
	static final String ENVIRONMENT_PRODUCTION   = "production";

	/**
	 * <i>`APP`</i>: Nombre por default de la aplicación.
	 */
	static final String APP_NAME = "APP";


	/**
	 * Nombre de la aplicación.
	 */
	String getApplicationName();


	/**
	 * Entorno de configuración de la instancia de la aplicación, puede ser alguno de los siguientes,
	 * u otros:
	 * <ul>
	 *   <li>{@link #ENVIRONMENT_DEVELOPMENT}</li>
	 *   <li>{@link #ENVIRONMENT_TEST}</li>
	 *   <li>{@link #ENVIRONMENT_PRE_PRODUCTION}</li>
	 *   <li>{@link #ENVIRONMENT_PRODUCTION}</li>
	 * </ul>
	 * @return cadena de texto con el nombre
	 * del entorno.
	 */
	String getEnvironment();

	/**
	 * @return <code>true</code> si el entorno de ejecución actual es
	 * de desarrollo ({@link #ENVIRONMENT_DEVELOPMENT}).
	 * @see #getEnvironment()
	 * @throws java.lang.IllegalStateException si este tipo de entorno
	 * no es soportado por la aplicación.
	 */
	boolean isDevelopmentEnvironment();

	/**
	 * @return <code>true</code> si el entorno de ejecución actual es
	 * de test ({@link #ENVIRONMENT_TEST}).
	 * @see #getEnvironment()
	 * @throws java.lang.IllegalStateException si este tipo de entorno
	 * no es soportado por la aplicación.
	 */
	boolean isTestEnvironment();

	/**
	 * @return <code>true</code> si el entorno de ejecución actual es
	 * de pre-producción ({@link #ENVIRONMENT_PRE_PRODUCTION}).
	 * @see #getEnvironment()
	 * @throws java.lang.IllegalStateException si este tipo de entorno
	 * no es soportado por la aplicación.
	 */
	boolean isPreProductionEnvironment();

	/**
	 * @return <code>true</code> si el entorno de ejecución actual es
	 * de producción ({@link #ENVIRONMENT_PRODUCTION}).
	 * @see #getEnvironment()
	 * @throws java.lang.IllegalStateException si este tipo de entorno
	 * no es soportado por la aplicación.
	 */
	boolean isProductionEnvironment();
}
