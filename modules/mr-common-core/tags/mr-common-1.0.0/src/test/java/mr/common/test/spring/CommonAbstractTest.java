package mr.common.test.spring;


import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;



/**
 * Clase que agrupa la configuración y métodos comunes.
 *
 */
@ContextConfiguration(locations = { "CommonAbstractTest-context.xml" })
public abstract class CommonAbstractTest extends AbstractJUnit4SpringContextTests {

    @Resource
    private Properties properties;


	/**
	 * Retorna el application context del test.
	 * @return {@link org.springframework.context.ApplicationContext}
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties appProperties) {
		this.properties = appProperties;
	}
}
