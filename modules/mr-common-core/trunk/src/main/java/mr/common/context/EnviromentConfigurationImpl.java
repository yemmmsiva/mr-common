package mr.common.context;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Implementación de {@link mr.common.context.EnviromentConfiguration
 * EnviromentConfiguration}.
 *
 * @author Mariano Ruiz
 */
public class EnviromentConfigurationImpl implements EnviromentConfiguration {

	private static final Log logger = LogFactory.getLog(EnviromentConfigurationImpl.class);

	private String enviroment;

	private String applicationName;


	/**
	 * Constructor por default, el entorno se configura como
	 * {@link mr.common.context.EnviromentConfiguration#ENVIROMENT_PRODUCTION
	 * ENVIROMENT_PRODUCTION}, y con el nombre {@link
	 * mr.common.context.EnviromentConfiguration#APP_NAME APP_NAME}.
	 */
	public EnviromentConfigurationImpl() {
		enviroment = ENVIROMENT_PRODUCTION;
		applicationName = APP_NAME;
	}

	/**
	 * Se crea la configuración con el entorno pasado
	 * como parámetro.
	 * @param applicationName nombre de la aplicación.
	 * @param enviroment entorno de ejecución.
	 */
	public EnviromentConfigurationImpl(String applicationName, String enviroment) {
		this.applicationName = applicationName;
		this.enviroment = enviroment;
	}

	@PostConstruct
	protected void logEnviroment() {
		String logMsg = "\n\n*****************************************************************************"
		              + "\n    STARTING APPLICATION '" + getApplicationName()
		              + "' IN '" + getEnviroment() + "' ENVIROMENT.";
		if(isProductionEnviroment()) {
			logMsg += "\n*****************************************************************************\n";
			logger.info(logMsg);
		} else {
			logMsg += "\n      Do NOT deploy to your live server(s) without changing this."
				    + "\n      See EnviromentConfiguration.getEnviroment() for more information.";
			logMsg += "\n*****************************************************************************\n";
			logger.warn(logMsg);
		}
	}

	public String getApplicationName() {
		return applicationName;
	}

	public String getEnviroment() {
		return enviroment;
	}

	public boolean isDevelopmentEnviroment() {
		return getEnviroment().equals(ENVIROMENT_DEVELOPEMENT);
	}

	public boolean isTestEnviroment() {
		return getEnviroment().equals(ENVIROMENT_TEST);
	}

	public boolean isPreProductionEnviroment() {
		return getEnviroment().equals(ENVIROMENT_PRE_PRODUCTION);
	}

	public boolean isProductionEnviroment() {
		return getEnviroment().equals(ENVIROMENT_PRODUCTION);
	}
}
