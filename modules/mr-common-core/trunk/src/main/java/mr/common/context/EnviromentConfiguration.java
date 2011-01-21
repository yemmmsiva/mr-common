package mr.common.context;


/**
 * Devuelve la configuración del entorno de ejecución. Puede
 * ser alguno de los cuatro casos más comunes,
 * <ul>
 *   <li>{@link #ENVIROMENT_DEVELOPEMENT}</li>
 *   <li>{@link #ENVIROMENT_TEST}</li>
 *   <li>{@link #ENVIROMENT_PRE_PRODUCTION}</li>
 *   <li>{@link #ENVIROMENT_PRODUCTION}</li>
 * </ul>
 * u otros tipos definidos por la aplicación.
 *
 * @author Mariano Ruiz
 */
public interface EnviromentConfiguration {

	/**
	 * <i>`development`</i>: Entorno de desarrollo.
	 */
	static final String ENVIROMENT_DEVELOPEMENT = "development";
	/**
	 * <i>`test`</i>: Entorno de testing.
	 */
	static final String ENVIROMENT_TEST   = "test";
	/**
	 * <i>`pre-production`</i>: Entorno de pre-producción.
	 */
	static final String ENVIROMENT_PRE_PRODUCTION   = "pre-production";
	/**
	 * <i>`production`</i>: Entorno de producción.
	 */
	static final String ENVIROMENT_PRODUCTION   = "production";

	/**
	 * Entorno de configuración de la instancia de la aplicación, puede ser alguno de los siguientes,
	 * u otros:
	 * <ul>
	 *   <li>{@link #ENVIROMENT_DEVELOPEMENT}</li>
	 *   <li>{@link #ENVIROMENT_TEST}</li>
	 *   <li>{@link #ENVIROMENT_PRE_PRODUCTION}</li>
	 *   <li>{@link #ENVIROMENT_PRODUCTION}</li>
	 * </ul>
	 * @return String - cadena de texto con el nombre
	 * del entorno
	 */
	String getEnviroment();

	/**
	 * @return <code>true</code> si el entorno de ejecución actual es
	 * de desarrollo ({@link #ENVIROMENT_DEVELOPEMENT}).
	 * @see #getEnviroment()
	 */
	boolean isDevelopmentEnviroment();

	/**
	 * @return <code>true</code> si el entorno de ejecución actual es
	 * de test ({@link #ENVIROMENT_TEST}).
	 * @see #getEnviroment()
	 */
	boolean isTestEnviroment();

	/**
	 * @return <code>true</code> si el entorno de ejecución actual es
	 * de pre-producción ({@link #ENVIROMENT_PRE_PRODUCTION}).
	 * @see #getEnviroment()
	 */
	boolean isPreProductionEnviroment();

	/**
	 * @return <code>true</code> si el entorno de ejecución actual es
	 * de producción ({@link #ENVIROMENT_PRODUCTION}).
	 * @see #getEnviroment()
	 */
	boolean isProductionEnviroment();
}
