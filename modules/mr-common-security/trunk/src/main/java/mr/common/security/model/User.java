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
	 * @param roles - lista de roles del usuario
	 */
	void setRoles(List<Role> roles);

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
	 * @param username - nombre identificativo del usuario en el sistema
	 */
	void setUsername(String username);

	/**
	 * @return String - password del usuario (puede estar encriptada dependiendo
	 * de la implementación)
	 */
	String getPassword();
	/**
	 * @param password - contraseña del usuario (puede estar encriptada dependiendo
	 * de la implementación)
	 */
	void setPassword(String password);

	/**
	 * @return <code>true</code> si el usuario está activado (puede ingresar al sistema)
	 */
	boolean isEnabled();
	/**
	 * @param enabled - <code>true</code> si el usuario debe
	 * estar activado (puede ingresar al sistema)
	 */
	void setEnabled(boolean enabled);

	/**
	 * @return String - correo electrónico del usuario, que también puede ser usado
	 * para su identificación
	 */
	String getEmailAddress();
	/**
	 * @param emailAddress - correo electrónico del usuario, que también puede ser usado
	 * para su identificación
	 */
	void setEmailAddress(String emailAddress);

	/**
	 * @return String - nombre real o identificativo del usuario, no puede ser usado
	 * para su identificación
	 */
	String getCommonName();
	/**
	 * @param commonName - nombre real o identificativo del usuario, no puede ser usado
	 * para su identificación
	 */
	void setCommonName(String commonName);
}
