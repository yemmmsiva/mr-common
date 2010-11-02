package mr.common.spring.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;


/**
 * Implementa {@link BeanFactoryAware}, por lo que al crear un bean de esta clase
 * en el contexto de Spring, podremos acceder a cualquier objeto del mismo con el
 * método estático {@link #get(String)} o {@link #get(Class)}.<br/>
 * Para poder usarla hay que declararla en el contexto de Spring, se debe
 * especificar el factory method. Ej.:<br/><br/>
 * <code>
 * &lt;bean id="bean" class="mr.common.spring.context.Bean" factory-method="instance" /&gt;
 * </code>
 * @author Mariano Ruiz
 */
public class Bean implements BeanFactoryAware {

    private static Bean instance;
    private BeanFactory factory;

    /**
     * Factory method usado por Spring para crear el objeto.
     * @return {@link mr.common.spring.context.Bean}
     */
    public static Bean instance() {
        if (instance == null) {
            instance = new Bean();
        }
        return instance;
    }

    /**
     * Obtiene el objeto del contexto de Spring con el nombre pasado.
     * @param name String
     * @return Object
     */
    public static Object get(String name) {
        return instance().factory.getBean(name);
    }

    /**
     * Obtiene el objeto del contexto de Spring del tipo pasado (incluido subclases).
     * @param type {@link Class}
     * @return Object
     * @throws NoSuchBeanDefinitionException - Si existe más de un objeto del tipo pasado
     * @throws ClassCastException - Si el contexto no es del tipo {@link ListableBeanFactory}
     * 
     */
    @SuppressWarnings("rawtypes")
	public static Object get(Class type) {
    	ListableBeanFactory lbf = (ListableBeanFactory) instance().factory;
    	return BeanFactoryUtils.beanOfType(lbf, type);
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.factory = beanFactory;
    }
}
