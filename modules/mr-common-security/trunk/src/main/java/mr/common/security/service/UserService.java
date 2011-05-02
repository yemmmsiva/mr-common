package mr.common.security.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mr.common.model.ConfigurableData;
import mr.common.security.exception.DuplicatedEmailAddressException;
import mr.common.security.exception.DuplicatedUserException;
import mr.common.security.exception.EncodePasswordException;
import mr.common.security.exception.IllegalArgumentUserFindException;
import mr.common.security.exception.InvalidPasswordException;
import mr.common.security.exception.InvalidRoleException;
import mr.common.security.exception.InvalidUsernameException;
import mr.common.security.exception.UserNotExistException;
import mr.common.security.model.Role;
import mr.common.security.model.User;
import mr.common.security.organization.exception.OrganizationNotExistException;


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
	 * Obtiene el username del usuario por su id.
	 * @param userId id del usuario
	 * @return el username del usuario
	 * @throws UserNotExistException si el usuario no existe
	 */
	String getUsernameById(Serializable userId);

	/**
	 * Obtiene el id del usuario por su username.
	 * @param username nombre del usuario
	 * @return el id del usuario
	 * @throws UserNotExistException si el usuario no existe
	 */
	Serializable getIdByUsername(String username);

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
	 * @param user - datos a machear
	 * @param activeFilter - si es distinto de <code>null</code>,
	 * su valor indica si se debe filtrar usuarios
	 * activados/desactivados
	 * @param page - página de datos, <code>null</code>
	 * si se deben traer todos los datos y sin ordenar
	 * @return listado de usuarios
	 * @throws IllegalArgumentUserFindException Si los argumentos
	 * de búsqueda o paginación son inválidos
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	List<User> find(User user, Boolean activeFilter, ConfigurableData page);

	/**
	 * Obtiene la cantidad de usuarios por determinados parámetros.
	 * @param user - datos a machear
	 * @param activeFilter - si es distinto de <code>null</code>,
	 * su valor indica si se debe filtrar usuarios
	 * @return int
	 * @throws IllegalArgumentUserFindException Si los argumentos
	 * de búsqueda son inválidos
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	int findCount(User user, Boolean activeFilter);

	/**
	 * Busca usuarios por determinados parámetros.
	 * @param user - datos a machear
	 * @param activeFilter - si es distinto de <code>null</code>,
	 * su valor indica si se debe filtrar usuarios
	 * activados/desactivados
	 * @param orgId - id de organización al que deben
	 * pertener los usuarios, <code>null</code> para
	 * cualquier organización
	 * @param page - página de datos, <code>null</code>
	 * si se deben traer todos los datos y sin ordenar
	 * @return listado de usuarios
	 * @throws IllegalArgumentUserFindException Si los argumentos
	 * de búsqueda o paginación son inválidos
	 * @throws OrganizationNotExistException Si la organización no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	List<User> find(User user, Serializable orgId, Boolean activeFilter, ConfigurableData page);

	/**
	 * Obtiene la cantidad de usuarios por determinados parámetros.
	 * @param user - datos a machear
	 * @param activeFilter - si es distinto de <code>null</code>,
	 * su valor indica si se debe filtrar usuarios
	 * @param orgId - id de organización al que deben
	 * pertener los usuarios, <code>null</code> para
	 * cualquier organización
	 * @return int
	 * @throws IllegalArgumentUserFindException Si los argumentos
	 * de búsqueda son inválidos
	 * @throws OrganizationNotExistException Si la organización no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	int findCount(User user, Serializable orgId, Boolean activeFilter);

	/**
	 * Borra el usuario por su username.
	 * @throws UserNotExistException si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void deleteByUsername(String username);

	/**
	 * Borra el usuario por su id.
	 * @throws UserNotExistException si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void deleteById(Serializable id);

	/**
	 * Crea el un usuario con la información pasada.
	 * @param user: datos del usuario nuevo
	 * @return el usuario actualizado
	 * @throws InvalidPasswordException Si la password es
	 * inválida
	 * @throws InvalidUsernameException Si el nombre
	 * del usuario no es válido
	 * @throws DuplicatedUserException Si un usuario
	 * ya existe con el mismo nombre
	 * @throws DuplicatedEmailAddressException Si un usuario
	 * ya existe con el mismo email
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	User newUser(User user);

	/**
	 * Crea un nuevo usuario, y en la organización
	 * con el id pasado.
	 * @param user: datos del usuario nuevo
	 * @param orgId: id de la organización a la que
	 * se debe agregar el usuario
	 * @return el usuario actualizado
	 * @throws InvalidPasswordException Si la password es
	 * inválida
	 * @throws InvalidUsernameException Si el nombre
	 * del usuario no es válido
	 * @throws DuplicatedUserException Si un usuario
	 * ya existe con el mismo nombre
	 * @throws DuplicatedEmailAddressException Si un usuario
	 * ya existe con el mismo email
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	User newUser(User user, Serializable orgId);

	/**
	 * Actualiza la información del usuario.
	 * @param id: identificador del usuario
	 * @param user: datos nuevos a actualizar
	 * @return el usuario actualizado
	 * @throws UserNotExistException si el usuario no existe
	 * @throws InvalidPasswordException Si la password es
	 * inválida
	 * @throws InvalidUsernameException si el nombre
	 * del usuario no es válido
	 * @throws DuplicatedUserException Si un usuario
	 * ya existe con el mismo nombre
	 * @throws DuplicatedEmailAddressException Si un usuario
	 * ya existe con el mismo email
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	User updateUser(Serializable id, User user);

	/**
	 * Actualiza la información del usuario.
	 * @param username: nombre del usuario
	 * @param user: datos nuevos a actualizar
	 * @return el usuario actualizado
	 * @throws UserNotExistException si el usuario no existe
	 * @throws InvalidPasswordException Si la password es
	 * inválida
	 * @throws InvalidUsernameException si el nombre
	 * del usuario no es válido
	 * @throws DuplicatedUserException Si un usuario
	 * ya existe con el mismo nombre
	 * @throws DuplicatedEmailAddressException Si un usuario
	 * ya existe con el mismo email
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	User updateUser(String username, User user);

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
	 * Cambia el id de la fotografía del usuario.
	 * @param userId id del usuario
	 * @param newPortraitId nuevo id
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updatePortraitId(Serializable userId, Serializable newPortraitId);

	/**
	 * Cambia el id de la fotografía del usuario.
	 * @param username nombre del usuario
	 * @param newPortraitId nuevo id
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updatePortraitId(String username, Serializable newPortraitId);

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
	 * Cambia el time zone del usuario.
	 * @param username String: username del usuario
	 * @param newTimeZoneId String: nuevo timeZoneId
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updateTimeZoneId(String username, String newTimeZoneId);

	/**
	 * Cambia la time zone del usuario.
	 * @param userId Serializable: id del usuario
	 * @param newTimeZoneId String: nueva timeZoneId
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updateTimeZoneId(Serializable userId, String newTimeZoneId);

	/**
	 * Cambia la fecha de cumpleaños del usuario.
	 * @param username nombre del usuario
	 * @param newBirthdayDate nueva fecha
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updateBirthdayDate(String username, Date newBirthdayDate);

	/**
	 * Cambia la fecha de cumpleaños del usuario.
	 * @param userId Serializable: id del usuario
	 * @param newBirthdayDate nueva fecha
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updateBirthdayDate(Serializable userId, Date newBirthdayDate);

	/**
	 * Cambia el nombre de la ciudad o región del usuario.
	 * @param username String: username del usuario
	 * @param newCityOrRegionName String: nueva ciudad o región
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updateCityOrRegionName(String username, String newCityOrRegionName);

	/**
	 * Cambia el nombre de la ciudad o región del usuario.
	 * @param userId Serializable: id del usuario
	 * @param newCityOrRegionName String: nueva ciudad o región
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updateCityOrRegionName(Serializable userId, String newCityOrRegionName);

	/**
	 * Cambia el ID de país del usuario.
	 * @param username String: username del usuario
	 * @param newCountryId String: nuevo id
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 * @see mr.common.security.model.User#setCountryId(String)
	 */
	void updateCountryId(String username, String newCountryId);

	/**
	 * Cambia la país del usuario.
	 * @param userId Serializable: id del usuario
	 * @param newCountryId String: nuevo id
	 * @throws UserNotExistException si el usuario
	 * no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 * @see mr.common.security.model.User#setCountryId(String)
	 */
	void updateCountryId(Serializable userId, String newCountryId);

	/**
	 * Listado de roles de la aplicación.
	 * @return List Roles
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	List<Role> getRolesList();

	/**
	 * Obtiene el role por su nombre.
	 * @param roleName String
	 * @return {@link mr.common.security.model.Role Role}
	 * @throws InvalidRoleException Si el role no existe
	 */
	Role getRole(String roleName);

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
	 * @throws InvalidRoleException Si el role no existe
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

	/**
	 * Retorna un nuevo objeto usuario de la implementación
	 * usada. Esta instancia no representa un usuario
	 * en el sistema, solo retorna un objeto 'en blanco',
	 * que puede ser usado para crear un nuevo usuario,
	 * o cargarle parámetros de búsqueda, etc.
	 * @return instancia de la implementación usada
	 * de {@link mr.common.security.model.User User}
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	User getUserInstance();
}
