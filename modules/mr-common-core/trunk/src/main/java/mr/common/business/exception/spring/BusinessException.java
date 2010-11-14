package mr.common.business.exception.spring;

import mr.common.exception.spring.FrameworkException;


/**
 * Excepción de negocio, con soporte i18n para los mensajes.
 * @author Mariano Ruiz
 */
public class BusinessException extends FrameworkException {

	private static final long serialVersionUID = 277013854162276483L;


	/**
	 * @see mr.common.spring.exception.FrameworkException#FrameworkException()
	 */
	public BusinessException() {
		super();
	}

	/**
	 * @see mr.common.spring.exception.FrameworkException#FrameworkException(String, Object...)
	 */
	public BusinessException(String messageKey, Object... messageArgs) {
		super(messageKey, messageArgs);
	}

	/**
	 * @see mr.common.spring.exception.FrameworkException#FrameworkException(Throwable))
	 */
	public BusinessException(Throwable cause) {
		super(cause);
	}

	/**
	 * @see mr.common.spring.exception.FrameworkException#FrameworkException(Throwable, String, Object...)
	 */
	public BusinessException(Throwable cause, String messageKey, Object... messageArgs) {
		super(cause, messageKey, messageArgs);
	}
}
