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

	private int minLength = 4;
	private int maxLength = 32;

	public PasswordValidator() { }

	public PasswordValidator(int minLength, int maxLength) {
		if(minLength<1) {
			throw new IllegalArgumentException("minLength < 1.");
		} else if(minLength>32) {
			throw new IllegalArgumentException("minLength > 32.");
		}
		if(maxLength<1) {
			throw new IllegalArgumentException("maxLength < 1.");
		} else if(maxLength>32) {
			throw new IllegalArgumentException("maxLength > 32.");
		}
		if(minLength>maxLength) {
			throw new IllegalArgumentException("minLength > maxLength.");
		}
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	public boolean isValid(Object password) {
		String pass = (String) password;
		if(!StringUtils.hasText(pass)) {
			return false;
		}
		if(pass.length()<minLength || pass.length()>maxLength) {
			return false;
		}
		return ValidatorUtils.isValidUsername(pass);
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
