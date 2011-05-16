package mr.common.security.organization.exception;

import mr.common.security.exception.SecurityException;


/**
 * Se lanza cuando hay más de una organización con
 * el mismo nombre.
 * @author Mariano Ruiz
 */
public class DuplicatedOrganizationException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public DuplicatedOrganizationException() {
		super("Duplicated organization.");
	}

	public DuplicatedOrganizationException(String message) {
		super(message);
	}
}
