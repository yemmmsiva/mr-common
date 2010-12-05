package mr.common.security.exception;


/**
 * Se lanza si el email de usuario es inválido.
 * @author Mariano Ruiz
 */
public class InvalidEmailAddressException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public InvalidEmailAddressException() {
		super("Invalid user email address.");
	}

	public InvalidEmailAddressException(String message) {
		super(message);
	}
}
