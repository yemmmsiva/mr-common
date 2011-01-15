package mr.common.format.validator;

import java.util.regex.Pattern;

import org.apache.oro.text.perl.Perl5Util;


/**
 * Métodos útiles de validaciones.
 * @author Mariano Ruiz
 */
public abstract class ValidatorUtils {

	private static final Pattern alphaNumericPattern = Pattern.compile(ValidatorConstants.PATTERN_ALPHA_NUMERIC);


	/**
	 * Valida si la es alfanumérica (A-Z, 0-9), no sensible a mayúsculas.
	 * @param text String
	 * @return boolean
	 */
	public static boolean isValidAlphaNumeric(String text) {
		return alphaNumericPattern.matcher(text).matches();
	}

	/**
	 * Valida que el usuario se un nombre válido para un nombre de usuario
	 * de sistema (ej. nombre de usuario de mail).
	 * @param username String
	 * @return boolean
	 */
	public static boolean isValidUsername(String username) {
		if(!username.equals(username.trim())) {
			return false;
		}
		Perl5Util userMatcher = new Perl5Util();
		return userMatcher.match(ValidatorConstants.PERL5PATTERN_MAIL_USER, username);
	}
}
