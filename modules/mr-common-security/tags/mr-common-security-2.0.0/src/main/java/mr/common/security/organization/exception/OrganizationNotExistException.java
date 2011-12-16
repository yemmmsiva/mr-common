package mr.common.security.organization.exception;

import mr.common.security.exception.SecurityException;


/**
 * Se lanza si se trata de obtener una organización
 * que no existe.
 * @author Mariano Ruiz
 */
public class OrganizationNotExistException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public OrganizationNotExistException() {
		super("Organization not exist.");
	}

	public OrganizationNotExistException(String message) {
		super(message);
	}
}
