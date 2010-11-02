package mr.common.security.exception;

import mr.common.business.exception.BusinessException;

/**
 * Se lanza cuando hay más de un usuario con
 * el mismo nombre o email.
 * @author Mariano Ruiz
 */
public class DuplicateUserException extends BusinessException {

	private static final long serialVersionUID = -281421919550082217L;

	public DuplicateUserException(String s) {
		super(s);
	}
}
