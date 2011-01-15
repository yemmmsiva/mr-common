package mr.common.security.exception;


/**
 * Se lanza si el username es inválido.
 * @author Mariano Ruiz
 */
public class InvalidUsernameException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public InvalidUsernameException() {
		super("Invalid user name.");
	}

	public InvalidUsernameException(String username) {
		super("The username '" + username + "' is invalid.");
	}
}
