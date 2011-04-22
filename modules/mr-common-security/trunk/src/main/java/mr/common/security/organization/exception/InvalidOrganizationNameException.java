package mr.common.security.organization.exception;

import mr.common.security.exception.SecurityException;


/**
 * Se lanza si el nombre de organización es inválido.
 * @author Mariano Ruiz
 */
public class InvalidOrganizationNameException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public InvalidOrganizationNameException() {
		super("Invalid organization name.");
	}

	public InvalidOrganizationNameException(String name) {
		super("The organization name '" + name + "' is invalid.");
	}
}
