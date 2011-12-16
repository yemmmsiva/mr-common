package mr.common.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Extiende de {@link ThreadGroup}, y loguea como <b><code>ERROR</code></b>
 * con <i>Apache Commons Logging</i> las excepciones del thread.
 * @author Mariano Ruiz
 */
public class LogErrorThreadGroupHandler extends ThreadGroup {

	private static final Log logger = LogFactory.getLog(LogErrorThreadGroupHandler.class);

	public LogErrorThreadGroupHandler() {
		super(LogErrorThreadGroupHandler.class.getSimpleName());
	}

	public LogErrorThreadGroupHandler(String name) {
		super(name);
	}

	/**
	 * Solo loguea como <b>ERROR</b> la excepción.
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {
		//super.uncaughtException(thread, throwable);
		logger.error(thread, throwable);
	}
}
