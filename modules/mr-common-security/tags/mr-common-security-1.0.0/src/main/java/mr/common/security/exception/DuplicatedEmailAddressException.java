package mr.common.security.exception;


/**
 * Se lanza cuando hay más de un usuario con
 * el mismo email address.
 * @author Mariano Ruiz
 */
public class DuplicatedEmailAddressException extends SecurityException {

	private static final long serialVersionUID = 1L;

	public DuplicatedEmailAddressException() {
		super("Duplicated user email address.");
	}

	public DuplicatedEmailAddressException(String s) {
		super(s);
	}
}
