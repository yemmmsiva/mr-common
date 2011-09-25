package mr.common.test.spring;


import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
//import org.springframework.transaction.support.TransactionSynchronizationManager;



/**
 * Clase que agrupa la configuración y métodos comunes para realizar tests.
 * Levanta el contexto de Spring en forma transaccional, por lo que luego de
 * ejecutarse cada test, se realiza un rollback de las acciones.
 *
 * @author Mariano Ruiz
 */
@ContextConfiguration(locations = { "CommonAbstractTest-context.xml" })
public abstract class CommonAbstractTransactionalTest extends AbstractTransactionalJUnit4SpringContextTests {

	protected static final String SESSION_FACTORY_NAME = "entitySessionFactory";
	protected SessionFactory sessionFactory = null;


	/**
	 * Retorna el application context del test.
	 * @return {@link org.springframework.context.ApplicationContext}
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

    /**
     * Metodo encargado de modificar el flushMode de Hibernate para que se mantenga una unica sesion
     * y se pueda realizar referencia a objetos definidos como lazy.
     */
    @Before
    public void enableHibernateLazyMode() {
        sessionFactory = (SessionFactory) applicationContext.getBean(SESSION_FACTORY_NAME);
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        if (session.getFlushMode() != FlushMode.COMMIT) {
            session.setFlushMode(FlushMode.COMMIT);
            //TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
        }
    }
}
