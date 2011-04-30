package mr.common.dao.exception;


/**
 * Excepción del framework cuando se produce un error
 * en el acceso o persistencia de los objetos.
 *
 * @author Mariano Ruiz
 */
public class DaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	public DaoException() { }

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}
}
