package mr.common.security.organization.exception;

import mr.common.security.exception.SecurityException;


/**
 * Se lanza si se trata de insertar un usuario
 * en una organización a la que no pertenece.
 *
 * @author Mariano Ruiz
 */
public class UserIsInOrganizationException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public UserIsInOrganizationException() {
		super("User is in organization.");
	}

	public UserIsInOrganizationException(String message) {
		super(message);
	}

	public UserIsInOrganizationException(Throwable cause) {
		super(cause);
	}

	public UserIsInOrganizationException(String message, Throwable cause) {
		super(message, cause);
	}
}
