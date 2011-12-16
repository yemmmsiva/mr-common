package mr.common.security.userentity.validator;

import mr.common.format.validator.Validator;
import mr.common.format.validator.ValidatorUtils;
import mr.common.security.exception.InvalidUsernameException;


/**
 * Validor del campo <code>username</code> de un usuario.
 *
 * @author Mariano Ruiz
 */
public class UsernameValidator extends Validator {

	private static final long serialVersionUID = 1L;

	public boolean isValid(Object username) {
		return ValidatorUtils.isValidUsername((String)username);
	}

	/**
	 * Lanza una {@link mr.common.security.exception.InvalidUsernameException
	 * InvalidUsernameException}
	 * con un mensaje de error con el username erroneo pasado.
	 */
	protected void throwError(Object username) throws RuntimeException {
		throw new InvalidUsernameException(
				"Invalid username '" + username + "'.");
	}
}
