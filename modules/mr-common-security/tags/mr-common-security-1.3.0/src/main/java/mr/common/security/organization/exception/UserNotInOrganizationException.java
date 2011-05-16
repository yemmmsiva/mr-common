package mr.common.security.organization.exception;

import mr.common.security.exception.SecurityException;


/**
 * Se lanza si se trata de realizar una operación
 * de un usuario sobre una organización a la que
 * no pertenece.
 *
 * @author Mariano Ruiz
 */
public class UserNotInOrganizationException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public UserNotInOrganizationException() {
		super("User is not in organization.");
	}

	public UserNotInOrganizationException(String message) {
		super(message);
	}

	public UserNotInOrganizationException(Throwable cause) {
		super(cause);
	}

	public UserNotInOrganizationException(String message, Throwable cause) {
		super(message, cause);
	}
}
