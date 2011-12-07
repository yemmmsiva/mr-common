package mr.common.security.userentity.validator;

import mr.common.format.validator.impl.EmailValidator;
import mr.common.security.exception.InvalidEmailAddressException;


/**
 * Validor del campo <code>emailAddress</code> de un usuario.
 *
 * @author Mariano Ruiz
 */
public class EmailAddressValidator extends EmailValidator {

	private static final long serialVersionUID = 1L;

	/**
	 * Lanza una {@link mr.common.security.exception.InvalidEmailAddressException
	 * InvalidEmailAddressException}
	 * con un mensaje de error con el email erroneo pasado.
	 */
	protected void throwError(Object email) throws RuntimeException {
		throw new InvalidEmailAddressException(
				"Invalid user email address '" + email + "'.");
	}
}
