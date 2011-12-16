package mr.common.dao.exception;


/**
 * Se lanza cuando en un diccionario hay más de un elemento con
 * el mismo código.
 *
 * @author Mariano Ruiz
 */
public class DuplicateCodeDictionaryException extends DaoException {

	private static final long serialVersionUID = 1L;

	public DuplicateCodeDictionaryException() {
		super("More than one persisted elements have the save dictionarity code.");
	}

	public DuplicateCodeDictionaryException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateCodeDictionaryException(String message) {
		super(message);
	}

	public DuplicateCodeDictionaryException(Throwable cause) {
		super(cause);
	}
}
