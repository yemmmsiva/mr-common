package mr.common.security.exception;


/**
 * Se lanza cuando se produce un error al codificar
 * una password.
 * @author Mariano Ruiz
 */
public class EncodePasswordException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public EncodePasswordException() {
		super("An error occurred when encoding the password.");
	}

	public EncodePasswordException(String message) {
		super(message);
	}

	public EncodePasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public EncodePasswordException(Throwable cause) {
		super("An error occurred when encoding the password.", cause);
	}
}
