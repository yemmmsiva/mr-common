package mr.common.security.exception;


/**
 * Se lanza si la password de usuario es inválida.
 * @author Mariano Ruiz
 */
public class InvalidPasswordException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public InvalidPasswordException() {
		super("Invalid user password.");
	}

	public InvalidPasswordException(String message) {
		super(message);
	}
}
