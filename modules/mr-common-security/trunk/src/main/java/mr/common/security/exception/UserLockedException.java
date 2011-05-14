package mr.common.security.exception;


/**
 * Se lanza si se trata de modificar o borrar
 * un usuario bloqueado.
 * @author Mariano Ruiz
 */
public class UserLockedException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public UserLockedException() {
		super("User is locked.");
	}

	public UserLockedException(String s) {
		super(s);
	}
}
