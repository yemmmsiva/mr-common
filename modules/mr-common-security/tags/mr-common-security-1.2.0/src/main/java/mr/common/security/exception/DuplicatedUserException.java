package mr.common.security.exception;


/**
 * Se lanza cuando hay más de un usuario con
 * el mismo nombre.
 * @author Mariano Ruiz
 */
public class DuplicatedUserException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public DuplicatedUserException() {
		super("Duplicated user.");
	}

	public DuplicatedUserException(String s) {
		super(s);
	}
}
