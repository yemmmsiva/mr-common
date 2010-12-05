package mr.common.security.service;

import java.io.Serializable;
import java.util.List;

import mr.common.security.exception.DuplicatedUserException;
import mr.common.security.exception.InvalidPasswordException;
import mr.common.security.exception.InvalidUsernameException;
import mr.common.security.model.Role;
import mr.common.security.model.User;
import mr.common.security.model.form.BasicUserForm;
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
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	List<User> getList();

	/**
	 * @param id Serializable - algun identificador único del usuario
	 * @return user
	 * @throws UserNotExistException si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	public User getById(Serializable userId);

	/**
	 * Retorna el usuario por su username.
	 * @param username String
	 * @return user
	 * @throws UserNotExistException si el usuario no existe
	 */
	User getByUsername(String username);

	/**
	 * Retorna el usuario por su correo electrónico.
	 * @param emailAddress String
	 * @return user
	 * @throws UserNotExistException si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	User getByEmailAddress(String emailAddress);

	/**
	 * Usuario logueado en la sesión.
	 * @return {@link mr.common.security.model.User}
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	User getCurrentUser();

    /**
     * Nombre del usuario logueado en la sesión.
     * @return String
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
     */
    String getCurrentUsername();

	/**
	 * Busca usuarios por determinados parámetros.
	 * @param form - datos a machear
	 * @return listado de usuarios
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	List<User> find(FindUserForm form);

	/**
	 * Borrar el usario por su useraname.
	 * @throws UserNotExistException si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void deleteByUsername(String username);

	/**
	 * Borrar el usario por su id.
	 * @throws UserNotExistException si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void deleteById(Serializable id);

	/**
	 * Crea o actualiza un usuario.
	 * @param form {@link mr.common.security.model.UserForm UserForm}
	 * @throws UserNotExistException si el usuario no existe
	 * @throws InvalidPasswordException Si la password es
	 * inválida
	 * @throws InvalidUsernameException si el nombre
	 * del usuario no es válido
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void saveOrUpdate(UserForm form);

	/**
	 * Actualiza la información del usuario.
	 * @param form {@link mr.common.security.model.form.BasicUserForm BasicUserForm}
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void update(BasicUserForm form);

	/**
	 * Codifica la password pasada al encoding usado para almacenar las password.
	 * @param plainPassword String - la password sin codificar
	 * @return String - la password codificada
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 * @throws EncodePasswordException Si ocurre un error al
	 * codificar la password
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
	 * Valida si el email de usuario pasado es válido.
	 * @param emailAddress String
	 * @return <code>true</code> si el nombre es válido
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean isValidEmailAddress(String emailAddress);

	/**
	 * Valida si la password de usuario pasada es válida.
	 * @param password String
	 * @return <code>true</code> si la password es válida
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean isValidPassword(String password);

	/**
	 * Chequea si el usuario tiene la password pasada
	 * @param username String
	 * @param password String
	 * @return <code>true</code> si la password es correcta
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws EncodePasswordException Si ocurre un error al
	 * codificar la password pasada
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean checkPassword(String username, String password);

	/**
	 * Chequea si el usuario tiene la password pasada
	 * @param id Serializable
	 * @param password String
	 * @return <code>true</code> si la password es correcta
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws EncodePasswordException Si ocurre un error al
	 * codificar la password pasada
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean checkPassword(Serializable id, String password);

	/**
	 * Cambia la password del usuario.
	 * @param username String: username del usuario
	 * @param newPassword String: nueva password
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws InvalidPasswordException Si la password es
	 * inválida
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updatePassword(String username, String newPassword);

	/**
	 * Cambia la password del usuario.
	 * @param userId Serializable: id del usuario
	 * @param newPassword String: nueva password
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws InvalidPasswordException Si la password es
	 * inválida
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updatePassword(Serializable userId, String newPassword);

	/**
	 * Cambia el email address del usuario.
	 * @param username String: username del usuario
	 * @param newEmailAddress String: nuevo email address
	 * @throws DuplicatedUserException si ya existe un usuario
	 * con el mismo emailAddress
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updateEmailAddress(String username, String newEmailAddress);

	/**
	 * Cambia la email address del usuario.
	 * @param userId Serializable: id del usuario
	 * @param newEmailAddress String: nueva email address
	 * @throws DuplicatedUserException si ya existe un usuario
	 * con el mismo emailAddress
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updateEmailAddress(Serializable userId, String newEmailAddress);

	/**
	 * Cambia el username del usuario.
	 * @param username String: username del usuario
	 * @param newUserName String: nuevo username
	 * @throws DuplicatedUserException si ya existe un usuario
	 * con el mismo nombre
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws InvalidUsernameException si el nombre
	 * del usuario no es válido
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updateUsername(String username, String newUsername);

	/**
	 * Cambia la commonName del usuario.
	 * @param userId Serializable: id del usuario
	 * @param newUserName String: nueva username
	 * @throws DuplicatedUserException si ya existe un usuario
	 * con el mismo nombre
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws InvalidUsernameException si el nombre
	 * del usuario no es válido
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updateUsername(Serializable userId, String newUsername);

	/**
	 * Cambia el commonName del usuario.
	 * @param username String: username del usuario
	 * @param newCommonName String: nuevo commonName
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updateCommonName(String username, String newCommonName);

	/**
	 * Cambia la commonName del usuario.
	 * @param userId Serializable: id del usuario
	 * @param newCommonName String: nueva commonName
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updateCommonName(Serializable userId, String newCommonName);

	/**
	 * Listado de roles de la aplicación.
	 * @return List Roles
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	List<Role> getRolesList();

	/**
	 * @param user {@link mr.common.security.model.User User}
	 * @param role {@link mr.common.security.model.Role Role}
	 * @return <code>true</code> si el usuario tiene el rol.
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean hasRole(User user, Role role);

	/**
	 * @param userId Serializable
	 * @param roleName String
	 * @return <code>true</code> si el usuario tiene el rol.
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean hasRole(Serializable userId, String roleName);

	/**
	 * @param username String
	 * @param roleName String
	 * @return <code>true</code> si el usuario tiene el rol.
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean hasRole(String username, String roleName);
}
