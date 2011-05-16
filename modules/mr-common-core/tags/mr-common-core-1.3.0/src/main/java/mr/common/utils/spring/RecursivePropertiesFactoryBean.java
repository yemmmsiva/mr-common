package mr.common.utils.spring;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


/**
 * Fusiona varios archivos properties en un solo bean.<br/>
 * Luego pueden usarse los placeholders <code>${ }</code> en
 * los archivos de configuración del contexto de Spring
 * para referenciar a valores de esos properties.<br/>
 * Ejemplo de como configurar esta clase:<br/>
 *
 * <pre>
 *     &lt;bean id="appProperties" class="mr.common.utils.spring.RecursivePropertiesFactoryBean"
 *          p:ignoreUnresolvablePlaceholders="true" p:order="10" lazy-init="false"
 *          p:systemPropertiesModeName="SYSTEM_PROPERTIES_MODE_OVERRIDE"&gt;
 *        &lt;property name="ignoreResourceNotFound" value="true" /&gt;
 *        &lt;property name="locations"&gt;
 *            &lt;list&gt;
 *				&lt;value&gt;classpath:config/app.properties&lt;/value&gt;
 *				&lt;value&gt;classpath:config/mail.properties&lt;/value&gt;
 *				&lt;value&gt;classpath:config/jdbc.properties&lt;/value&gt;
 *            &lt;/list&gt;
 *        &lt;/property&gt;
 *        &lt;property name="localOverride" value="true" /&gt;
 *    &lt;/bean&gt;
 * </pre>
 *
 * @author Mariano Ruiz
 */
public class RecursivePropertiesFactoryBean extends PropertyPlaceholderConfigurer implements FactoryBean {

    private Properties mergedProps;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            mergedProps = mergeProperties();
            convertProperties(mergedProps);               // Convert the merged properties, if necessary
            processProperties(beanFactory, mergedProps);  // Let the subclass process the properties
        } catch (IOException ex) {
            throw new BeanInitializationException("Could not load properties", ex);
        }
    }

	@Override
    @SuppressWarnings("rawtypes")
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        for(Enumeration e = mergedProps.keys(); e.hasMoreElements(); ) {
            String key = (String) e.nextElement();
            String value = mergedProps.getProperty(key);
            String newValue = parseStringValue(value, mergedProps, new HashSet());
            mergedProps.put(key, newValue);
        }
        super.processProperties(beanFactoryToProcess, props);
    }

    public Object getObject() throws Exception {
        return mergedProps;
    }

	@SuppressWarnings("rawtypes")
	public Class getObjectType() {
        return Properties.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
