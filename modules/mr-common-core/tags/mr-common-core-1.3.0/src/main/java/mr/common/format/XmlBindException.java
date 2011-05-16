package mr.common.format;


/**
 * Es lanzada cuando se produce un error al
 * bindear un XML a un objeto o visceversa.
 *
 * @see mr.common.format.XmlUtils
 * @author Mariano Ruiz
 */
public class XmlBindException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public XmlBindException() { }

	public XmlBindException(String message) {
		super(message);
	}

	public XmlBindException(Throwable cause) {
		super(cause);
	}

	public XmlBindException(String message, Throwable cause) {
		super(message, cause);
	}
}
