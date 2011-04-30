package mr.common.security.userentity.validator;

import org.springframework.util.StringUtils;

import mr.common.format.validator.Validator;
import mr.common.format.validator.ValidatorUtils;
import mr.common.security.exception.InvalidPasswordException;


/**
 * Validor del campo password de un usuario.
 *
 * @author Mariano Ruiz
 */
public class PasswordValidator extends Validator {

	private static final long serialVersionUID = 1L;

	public boolean isValid(Object password) {
		if(!StringUtils.hasText((String)password)) {
			return false;
		}
		return ValidatorUtils.isValidUsername((String)password);
	}

	/**
	 * Lanza una {@link mr.common.security.exception.InvalidPasswordException
	 * InvalidPasswordException}
	 * con un mensaje de error con el password erroneo pasado.
	 */
	protected void throwError(Object password) throws RuntimeException {
		throw new InvalidPasswordException();
	}
}
