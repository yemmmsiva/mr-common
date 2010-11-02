package mr.common.security.model;

import java.io.Serializable;

/**
 * Roles que pueden tener los usuarios.
 * @author Mariano Ruiz
 */
public interface Role extends Serializable {

	/**
	 * Rol de usuario anónimo.
	 */
	static final String ROLE_GUEST = "ROLE_GUEST";
	/**
	 * Rol del superusuario.
	 */
	static final String ROLE_ADMIN = "ROLE_ADMIN";
	/**
	 * Rol de usuario corriente, necesita este rol para al menos poder
	 * ingresar al sistema.
	 */
	static final String ROLE_USER = "ROLE_USER";

	/**
	 * @return String - código único identificativo del rol
	 */
	String getAuthority();
}
