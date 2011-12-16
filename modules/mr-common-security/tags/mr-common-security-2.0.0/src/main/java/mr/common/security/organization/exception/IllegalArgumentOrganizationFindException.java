package mr.common.security.organization.exception;

import mr.common.security.exception.SecurityException;


/**
 * Se lanza si se pasan argumentos inválidos en
 * la búsqueda de organizaciones.
 * @author Mariano Ruiz
 */
public class IllegalArgumentOrganizationFindException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public IllegalArgumentOrganizationFindException() {
		super("Organization find arguments invalid.");
	}

	public IllegalArgumentOrganizationFindException(String message) {
		super(message);
	}
}
