package mr.common.security.organization.exception;

import mr.common.security.exception.SecurityException;


/**
 * Se lanza si se trata de editar o borrar
 * una organización que está lockeada.
 *
 * @see mr.common.security.organization.service.OrganizationService
 * #updateLock(Serializable, boolean)
 *
 * @author Mariano Ruiz
 */
public class OrganizationLockedException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public OrganizationLockedException() {
		super("Organization is locked.");
	}

	public OrganizationLockedException(String message) {
		super(message);
	}
}
