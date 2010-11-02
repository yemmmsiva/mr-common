package mr.common.security.service;

import java.io.Serializable;
import java.util.List;

import mr.common.security.model.Role;
import mr.common.security.model.User;
import mr.common.security.model.form.FindUserForm;
import mr.common.security.model.form.UserForm;


/**
 * Servicio para el manejo de usuarios: logueo, actualizaciones, verificación
 * de credenciales, etc.
 * 
 * @author Mariano Ruiz
 */
public interface UserService {

	/**
	 * @return listado de todos los usuarios
	 */
	List<User> getList();

	/**
	 * @param id Serializable - algun identificador único del usuario
	 * @return user
	 */
	public User getById(Serializable userId);

	/**
	 * Retorna el usuario por su username.
	 * @param username String
	 * @return user
	 */
	User getByUsername(String username);

	/**
	 * Retorna el usuario por su correo electrónico.
	 * @param emailAddress String
	 * @return user
	 */
	User getByEmailAddress(String emailAddress);

	/**
	 * Usuario logueado en la sesión.
	 * @return {@link mr.common.security.model.User}
	 */
	User getCurrentUser();

    /**
     * Nombre del usuario logueado en la sesión.
     * @return String
     */
    String getCurrentUsername();

	/**
	 * Busca usuarios por determinados parámetros.
	 * @param form - datos a machear
	 * @return listado de usuarios
	 */
	List<User> find(FindUserForm form);

	/**
	 * Borrar el usario por su useraname.
	 */
	void deleteByUsername(String username);

	/**
	 * Borrar el usario por su id.
	 */
	void deleteById(Serializable id);

	/**
	 * Crea o actualiza un usuario.
	 * @param form {@link mr.common.security.model.UserForm}
	 */
	void saveOrUpdate(UserForm form);

	/**
	 * Codifica la password pasada al encoding usado para almacenar las password.
	 * @param plainPassword String - la password sin codificar
	 * @return String - la password codificada
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	String encodePassword(String plainPassword);

	/**
	 * Valida si el nombre de usuario pasado es válido.
	 * @param username String
	 * @return <code>true</code> si el nombre es válido
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean isValidUsername(String username);

	/**
	 * Valida si la password de usuario pasada es válida.
	 * @param password String
	 * @return <code>true</code> si la password es válida
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean isValidPassword(String password);

	/**
	 * Cambia la password del usuario.
	 * @param username String: nombre del usuario
	 * @param newPassword String: nueva password
	 */
	void changePassword(String username, String newPassword);

	/**
	 * Cambia la password del usuario.
	 * @param userId Serializable: id del usuario
	 * @param newPassword String: nueva password
	 */
	void changePassword(Serializable userId, String newPassword);

	/**
	 * Listado de roles de la aplicación.
	 * @return List Roles
	 */
	List<Role> getRolesList();

	/**
	 * @param user {@link mr.common.security.model.User User}
	 * @param role {@link mr.common.security.model.Role Role}
	 * @return <code>true</code> si el usuario tiene el rol.
	 */
	boolean hasRole(User user, Role role);

	/**
	 * @param userId Serializable
	 * @param roleName String
	 * @return <code>true</code> si el usuario tiene el rol.
	 */
	boolean hasRole(Serializable userId, String roleName);

	/**
	 * @param username String
	 * @param roleName String
	 * @return <code>true</code> si el usuario tiene el rol.
	 */
	boolean hasRole(String username, String roleName);
}
