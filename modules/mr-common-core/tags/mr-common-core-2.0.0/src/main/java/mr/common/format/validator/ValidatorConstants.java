package mr.common.format.validator;


/**
 * Patters de validaciones compatibles con las expresiones regulares
 * de Java o las de Perl5.
 * 
 * @author Mariano Ruiz
 */
public interface ValidatorConstants {

	/**
	 * Patrón de cadena de alfanuméricos (A-Z, 0-9), no sensible a mayúsculas.
	 */
	public static final String PATTERN_ALPHA_NUMERIC =
		"[a-zA-Z0-9]*";

	/**
	 * Patrón de cadena válida como nombre de usuario de mail.<br/>
	 * NOTA: No valida si los primeros caracteres son espacios en blanco, aplicar <code>trim()</code>
	 * a la cadena si se quiere evitar.
	 */
	public static final String PERL5PATTERN_MAIL_USER =
	"/^\\s*(([^\\s\\000-\\037\\(\\)<>@,;:'\\\\\\\"\\.\\[\\]\\177]|')+|(\"[^\"]*\"))(\\.(([^\\s\\000-\\037\\(\\)<>@,;:'\\\\\\\"\\.\\[\\]\\177]|')+|(\"[^\"]*\")))*$/";
}
