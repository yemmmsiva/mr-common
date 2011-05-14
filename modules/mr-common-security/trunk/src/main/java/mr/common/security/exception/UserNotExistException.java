package mr.common.security.exception;


/**
 * Se lanza si se trata de obtener un usuario
 * que no existe.
 * @author Mariano Ruiz
 */
public class UserNotExistException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public UserNotExistException() {
		super("User not exist.");
	}

	public UserNotExistException(String s) {
		super(s);
	}
}
