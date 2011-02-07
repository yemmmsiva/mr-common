package mr.common.security.exception;


/**
 * Se lanza si el role es inválido o no existe.
 * @author Mariano Ruiz
 */
public class InvalidRoleException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public InvalidRoleException() {
		super("Invalid role or role not exist.");
	}

	public InvalidRoleException(String message) {
		super(message);
	}
}
