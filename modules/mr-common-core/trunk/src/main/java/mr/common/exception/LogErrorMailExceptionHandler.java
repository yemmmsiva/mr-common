package mr.common.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Extiende de {@link ThreadGroup}, y loguea como <b><code>ERROR</code></b>
 * con <i>Log4j</i> una excepción.
 * @author Mariano Ruiz
 */
public class LogErrorMailExceptionHandler extends ThreadGroup {

	private static final Log logger = LogFactory.getLog(LogErrorMailExceptionHandler.class);

	public LogErrorMailExceptionHandler() {
		super(LogErrorMailExceptionHandler.class.getSimpleName());
	}

	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {
		logger.error(thread, throwable);
	}
}
