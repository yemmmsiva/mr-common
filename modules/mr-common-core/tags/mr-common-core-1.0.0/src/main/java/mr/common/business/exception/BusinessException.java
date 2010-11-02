package mr.common.business.exception;


/**
 * Excepción de negocio.
 * @author Mariano Ruiz
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -3924709530885254739L;


	/**
	 * @see java.lang.RuntimeException#RuntimeException()
	 */
	public BusinessException() {
		super();
	}

	/**
	 * @see java.lang.RuntimeException#RuntimeException(String)
	 */
	public BusinessException(String message) {
		super(message);
	}

	/**
	 * @see java.lang.RuntimeException#RuntimeException(Throwable)
	 */
	public BusinessException(Throwable cause) {
		super(cause);
	}

	/**
	 * @see java.lang.RuntimeException#RuntimeException(String, Throwable)
	 */
	public BusinessException(Throwable cause, String message) {
		super(message, cause);
	}
}
