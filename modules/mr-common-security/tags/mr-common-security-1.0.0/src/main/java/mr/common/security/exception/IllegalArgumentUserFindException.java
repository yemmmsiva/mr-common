package mr.common.security.exception;


/**
 * Se lanza si se pasan argumentos inválidos en
 * la búsqueda de usuarios.
 * @author Mariano Ruiz
 */
public class IllegalArgumentUserFindException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public IllegalArgumentUserFindException() {
		super("User find arguments invalid.");
	}

	public IllegalArgumentUserFindException(String s) {
		super(s);
	}
}
