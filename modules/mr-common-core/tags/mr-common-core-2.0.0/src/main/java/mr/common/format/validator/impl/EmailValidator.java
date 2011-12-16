package mr.common.format.validator.impl;

import mr.common.format.validator.Validator;

/**
 * Implementa {@link mr.common.format.validator.Validator}, y
 * valida si una dirección de email es válida.
 *
 * @author Mariano Ruiz
 */
public class EmailValidator extends Validator {

	private static final long serialVersionUID = 1L;

	private org.apache.commons.validator.EmailValidator
	    emailValidator = org.apache.commons.validator.EmailValidator.getInstance();

	public boolean isValid(Object email) {
		return emailValidator.isValid((String)email);
	}

	/**
	 * Lanza una {@link RuntimeException} con un mensaje
	 * de error con el email erroneo pasado.
	 */
	protected void throwError(Object email) throws RuntimeException {
		throw new RuntimeException(
				"Invalid email address '" + email + "'.");
	}
}
