package mr.common.security.exception;


import mr.common.security.model.User;


/**
 * Se lanza si se trata de modificar o borrar
 * un usuario bloqueado.
 *
 * @see mr.common.security.service.UserService#updateLock(java.io.Serializable, boolean)
 *
 * @author Mariano Ruiz
 */
public class UserLockedException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public UserLockedException() {
		super("User is locked.");
	}

	public UserLockedException(User user) {
		super("User id=" + user.getId() + " is locked.");
	}

	public UserLockedException(String s) {
		super(s);
	}
}
