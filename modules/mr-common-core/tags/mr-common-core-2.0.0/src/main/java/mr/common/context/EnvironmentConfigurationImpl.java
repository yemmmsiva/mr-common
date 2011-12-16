package mr.common.context;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Implementación de {@link mr.common.context.EnvironmentConfiguration
 * EnvironmentConfiguration}.
 *
 * @author Mariano Ruiz
 */
public class EnvironmentConfigurationImpl implements EnvironmentConfiguration {

	private static final Log logger = LogFactory.getLog(EnvironmentConfigurationImpl.class);

	private String environment;

	private String applicationName;


	/**
	 * Constructor por default, el entorno se configura como
	 * {@link mr.common.context.EnvironmentConfiguration#ENVIRONMENT_PRODUCTION
	 * ENVIRONMENT_PRODUCTION}, y con el nombre {@link
	 * mr.common.context.EnvironmentConfiguration#APP_NAME APP_NAME}.
	 */
	public EnvironmentConfigurationImpl() {
		environment = ENVIRONMENT_PRODUCTION;
		applicationName = APP_NAME;
	}

	/**
	 * Se crea la configuración con el entorno pasado
	 * como parámetro.
	 * @param applicationName nombre de la aplicación.
	 * @param environment entorno de ejecución.
	 */
	public EnvironmentConfigurationImpl(String applicationName, String environment) {
		this.applicationName = applicationName;
		this.environment = environment;
	}

	@PostConstruct
	protected void logEnviroment() {
		String logMsg = "\n\n*****************************************************************************"
		              + "\n    STARTING APPLICATION '" + getApplicationName()
		              + "' IN '" + getEnvironment() + "' ENVIRONMENT.";
		if(isProductionEnvironment()) {
			logMsg += "\n*****************************************************************************\n";
			logger.info(logMsg);
		} else {
			logMsg += "\n      Do NOT deploy to your live server(s) without changing this."
				    + "\n      See EnvironmentConfiguration.getEnvironment() for more information.";
			logMsg += "\n*****************************************************************************\n";
			logger.warn(logMsg);
		}
	}

	public String getApplicationName() {
		return applicationName;
	}

	public String getEnvironment() {
		return environment;
	}

	public boolean isDevelopmentEnvironment() {
		return getEnvironment().equals(ENVIRONMENT_DEVELOPMENT);
	}

	public boolean isTestEnvironment() {
		return getEnvironment().equals(ENVIRONMENT_TEST);
	}

	public boolean isPreProductionEnvironment() {
		return getEnvironment().equals(ENVIRONMENT_PRE_PRODUCTION);
	}

	public boolean isProductionEnvironment() {
		return getEnvironment().equals(ENVIRONMENT_PRODUCTION);
	}
}
