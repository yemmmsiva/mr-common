package mr.common.security.model;

import java.io.Serializable;
import java.util.List;

/**
 * Usuario del sistema.
 * @author Mariano Ruiz
 */
public interface User extends Serializable {

	/**
	 * @return Serializable - identificador del usuario
	 */
	Serializable getId();

	/**
	 * @return lista de roles activos del usuario
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	List<Role> getRoles();

	/**
	 * Verifica si el usuario tiene el rol. Este método
	 * puede no estar soportado por la implementación.<br/>
	 * En su lugar se recomienda usar los métodos del
	 * servicio: <i><code>UserService.hasRole(..)</code></i>.
	 * @param role {@link Role}
	 * @return <code>true</code> si el usuario tiene el rol.
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean hasRole(Role role);

	/**
	 * Verifica si el usuario tiene el rol. Este método
	 * puede no estar soportado por la implementación.<br/>
	 * En su lugar se recomienda usar los métodos del
	 * servicio: <i><code>UserService.hasRole(..)</code></i>.
	 * @param roleName String
	 * @return <code>true</code> si el usuario tiene el rol.
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean hasRole(String roleName);

	/**
	 * @return String - nombre identificativo del usuario en el sistema
	 */
	String getUsername();

	/**
	 * @return String - password del usuario (puede estar encriptada)
	 */
	String getPassword();

	/**
	 * @return <code>true</code> si el usuario está activado (puede ingresar al sistema)
	 */
	boolean isEnabled();

	/**
	 * @return String - correo electrónico del usuario, que también puede ser usado
	 * para su identifiación
	 */
	String getEmailAddress();
}
