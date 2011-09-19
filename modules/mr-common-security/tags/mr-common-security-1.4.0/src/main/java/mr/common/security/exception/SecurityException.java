package mr.common.security.exception;


/**
 * Clase base para todas las excepciones del módulo
 * de <i>mr-common-security</i>.
 * @author Mariano Ruiz
 */
public class SecurityException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	public SecurityException() {
	}

	public SecurityException(String message) {
		super(message);
	}

	public SecurityException(Throwable cause) {
		super(cause);
	}

	public SecurityException(String message, Throwable cause) {
		super(message, cause);
	}
}
