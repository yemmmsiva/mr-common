package mr.common.security.organization.service;

import java.io.Serializable;
import java.util.List;

import mr.common.model.ConfigurableData;
import mr.common.security.exception.UserLockedException;
import mr.common.security.exception.UserNotExistException;
import mr.common.security.model.Role;
import mr.common.security.model.User;
import mr.common.security.organization.exception.DuplicatedOrganizationException;
import mr.common.security.organization.exception.InvalidOrganizationNameException;
import mr.common.security.organization.exception.OrganizationLockedException;
import mr.common.security.organization.exception.OrganizationNotExistException;
import mr.common.security.organization.exception.UserIsInOrganizationException;
import mr.common.security.organization.exception.UserNotInOrganizationException;
import mr.common.security.organization.model.Organization;
import mr.common.security.service.UserService;


/**
 * Servicio para el manejo de organizaciones de
 * la aplicación.
 *
 * @see mr.common.security.organization.model.Organization
 *
 * @author Mariano Ruiz
 */
public interface OrganizationService {


	/**
	 * @return listado de todas las organizaciones
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 * @see #find(String, Boolean, ConfigurableData)
	 * @see #find(String, Serializable, Boolean, ConfigurableData)
	 */
	List<Organization> getList();

	/**
	 * Cantidad de organizaciones de la aplicación.
	 * @return la cantidad
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 * @see #findCount(String, Boolean)
	 * @see #findCount(String, Serializable, Boolean)
	 */
	int count();

	/**
	 * Busca organizaciones según los parámetros pasados
	 * y en forma paginada.
	 * @param nameOrDescription - nombre o descripción
	 * de la organización
	 * @param activeFilter - si es distinto de <code>null</code>,
	 * su valor indica si se debe filtrar organizaciones
	 * activados/desactivados
	 * @param page - página de datos, <code>null</code>
	 * si se deben traer todos los datos y sin ordenar
	 * @return listado de organizaciones
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	List<Organization> find(String nameOrDescription,
	                        Boolean activeFilter, ConfigurableData page);

	/**
	 * Busca organizaciones según los parámetros pasados
	 * y en forma paginada.
	 * @param nameOrDescription - nombre o descripción
	 * de la organización
	 * @param userId - id del usuario que debe estar en las organizaciones,
	 * <code>null</code> para no filtrar por usuario
	 * @param role - role que debe tener el usuario con <code>userId</code>,
	 * sino se va a filtrar por usuario o no se desea filtrar por
	 * role -> <code>null</code>
	 * @param activeFilter - si es distinto de <code>null</code>,
	 * su valor indica si se debe filtrar organizaciones
	 * activados/desactivados
	 * @param page - página de datos, <code>null</code>
	 * si se deben traer todos los datos y sin ordenar
	 * @return listado de organizaciones
	 * @throws UserNotExistException si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	List<Organization> find(String nameOrDescription, Serializable userId, Role role,
	                        Boolean activeFilter, ConfigurableData page);

	/**
	 * Obtiene la cantidad de organizaciones por determinados parámetros.
	 * @param nameOrDescription - nombre o descripción
	 * de la organización
	 * @param activeFilter - si es distinto de <code>null</code>,
	 * su valor indica si se debe filtrar usuarios
	 * activados/desactivados
	 * @return listado de organizaciones
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	int findCount(String nameOrDescription, Boolean activeFilter);

	/**
	 * Obtiene la cantidad de organizaciones por determinados parámetros.
	 * @param nameOrDescription - nombre o descripción
	 * de la organización
	 * @param userId - id del usuario que debe estar en las organizaciones,
	 * <code>null</code> para no filtrar por usuario
	 * @param role - role que debe tener el usuario con <code>userId</code>,
	 * sino se va a filtrar por usuario o no se desea filtrar por
	 * role -> <code>null</code>
	 * @param activeFilter - si es distinto de <code>null</code>,
	 * su valor indica si se debe filtrar usuarios
	 * activados/desactivados
	 * @return listado de organizaciones
	 * @throws UserNotExistException si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	int findCount(String nameOrDescription, Serializable userId, Role role,
	              Boolean activeFilter);

	/**
	 * @param id Serializable - identificador único de la organización
	 * @return la organización
	 * @throws OrganizationNotExistException Si la organización no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	Organization getById(Serializable id);

	/**
	 * Retorna la organización por su nombre.
	 * @param name String
	 * @return la organización
	 * @throws OrganizationNotExistException Si la organización no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	Organization getByName(String name);

	/**
	 * Obtiene el nombre de la organización por
	 * su id.
	 * @param orgId el id de la organización
	 * @return el nombre de la organización
	 * @throws OrganizationNotExistException Si la organización no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	String getNameById(Serializable orgId);

	/**
	 * Obtiene el id de la organización por
	 * su nombre.
	 * @param name nombre de la organización
	 * @return el id de la organización
	 * @throws OrganizationNotExistException Si la organización no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	Serializable getIdByName(String name);

	/**
	 * Crea la nueva organización con la información pasada.
	 * @param org: datos de la nueva organización
	 * @return la organización actualizada
	 * @throws InvalidOrganizationNameException Si el nombre
	 * de organización no es válido
	 * @throws DuplicatedOrganizationException Si una organización
	 * ya existe con el mismo nombre
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	Organization newOrganization(Organization org);

	/**
	 * Actualiza la información de la organización.
	 * @param id: identificador de la organización
	 * @param organization: datos nuevos
	 * @return la organización actualizada
	 * @throws OrganizationNotExistException Si la organización
	 * no existe
	 * @throws InvalidOrganizationNameException Si el nombre
	 * de organización no es válido
	 * @throws DuplicatedOrganizationException Si una organización
	 * ya existe con el mismo nombre
	 * @throws OrganizationLockedException Si la organización
	 * está bloqueada para escritura
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	Organization updateOrganization(Serializable id, Organization organization);

	/**
	 * Actualiza la información de la organización.
	 * @param name: nombre de la organización
	 * @param organization: datos nuevos
	 * @return la organización actualizada
	 * @throws OrganizationNotExistException Si la organización
	 * no existe
	 * @throws InvalidOrganizationNameException Si el nombre
	 * de organización no es válido
	 * @throws DuplicatedOrganizationException Si una organización
	 * ya existe con el mismo nombre
	 * @throws OrganizationLockedException Si la organización
	 * está bloqueada para escritura
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	Organization updateOrganization(String name, Organization organization);

	/**
	 * Retorna un nuevo objeto organización de la implementación
	 * usada. Esta instancia no representa una organización
	 * en el sistema, solo retorna un objeto 'en blanco',
	 * que puede ser usado para crear una nueva,
	 * o cargarle parámetros de búsqueda, etc.
	 * @return instancia de la implementación usada
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	Organization getOrganizationInstance();

	/**
	 * Valida si el nombre de organización pasado es válido.
	 * @param name String
	 * @return <code>true</code> si el nombre es válido
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean isValidOrganizationName(String name);


	/**
	 * Borra la organización por su nombre.
	 * @throws OrganizationNotExistException Si la organización
	 * no existe
	 * @throws OrganizationLockedException Si la organización
	 * está bloqueada para escritura y borrado
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void deleteByName(String name);

	/**
	 * Borra la organización por su id.
	 * @throws OrganizationNotExistException Si la organización
	 * no existe
	 * @throws OrganizationLockedException Si la organización
	 * está bloqueada para escritura y borrado
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void deleteById(Serializable id);

	/**
	 * Cambia el id del logo de la organización.
	 * @param orgId id de la organización
	 * @param newLogoId nuevo id
	 * @throws OrganizationNotExistException Si la organización
	 * no existe
	 * @throws OrganizationLockedException Si la organización
	 * está bloqueada para escritura
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 * @see mr.common.security.organization.model.Organization#setLogoId(Serializable)
	 */
	void updateLogoId(Serializable orgId, Serializable newLogoId);

	/**
	 * Cambia el estado de bloqueo de la organización.
	 * @param orgId id de la organización
	 * @param lock <code>true</code> para bloquear la
	 * organización contra escritura y borrados
	 * @throws OrganizationNotExistException Si la organización
	 * no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 * @see mr.common.security.organization.model.Organization#setLocked(boolean)
	 */
	void updateLock(Serializable orgId, boolean lock);

	/**
	 * Agrega al usuario a la organización.
	 * @param orgId id de la organización
	 * @param userId id del usuario
	 * @throws OrganizationNotExistException Si la organización
	 * no existe
	 * @throws UserNotExistException Si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 * @throws UserIsInOrganizationException Si el usuario
	 * ya pertenece a la organización
	 * @throws UserLockedException Si el usuario está bloqueado
	 * para escritura
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void addUser(Serializable orgId, Serializable userId);

	/**
	 * Agrega al usuario a la organización, con los roles pasados.
	 * @param orgId id de la organización
	 * @param userId id del usuario
	 * @param roles lista con los roles
	 * @throws OrganizationNotExistException Si la organización
	 * no existe
	 * @throws UserNotExistException Si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 * @throws UserIsInOrganizationException Si el usuario
	 * ya pertenece a la organización
	 * @throws UserLockedException Si el usuario está bloqueado
	 * para escritura
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void addUser(Serializable orgId, Serializable userId, List<Role> roles);

	/**
	 * Quita el usuario de la organización.
	 * @param orgId id de la organización
	 * @param userId id del usuario
	 * @throws OrganizationNotExistException Si la organización
	 * no existe
	 * @throws UserNotExistException Si el usuario no existe
	 * @throws UserNotInOrganizationException si el usuario
	 * no pertenece a la organización
	 * @throws UserLockedException Si el usuario está bloqueado
	 * para escritura
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void removeUser(Serializable orgId, Serializable userId);

	/**
	 * Quita al usuario de todas las organizaciones.
	 * @param userId id del usuario
	 * @return cantidad de organizaciones en las que el
	 * usuario fue removido
	 * @throws UserNotExistException Si el usuario no existe
	 * @throws UserLockedException Si el usuario está bloqueado
	 * para escritura
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	int removeUserFromAll(Serializable userId);

	/**
	 * Quita todos los usuarios de la organización.
	 * @param id identificador de la organización
	 * @return cantidad de usuarios quitados
	 * @throws OrganizationNotExistException Si la organización
	 * no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	int removeAllUsersFromOrganization(Long id);

	/**
	 * Verifica si el usuario pertenece a la organización.
	 * @param orgId id de la organización
	 * @param userId id del usuario
	 * @return <code>true</code> si el usuario pertene
	 * a la organización
	 * @throws OrganizationNotExistException Si la organización
	 * no existe
	 * @throws UserNotExistException Si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean isUserInOrganization(Serializable orgId, Serializable userId);

	/**
	 * Obtiene los roles del usuario en la organización.
	 * @param orgId id de la organización
	 * @param userId id del usuario
	 * @return lista con los roles, o lista vacía si el usuario
	 * pertenece a la organización pero no posee roles
	 * @throws OrganizationNotExistException Si la organización
	 * no existe
	 * @throws UserNotExistException Si el usuario no existe
	 * @throws UserNotInOrganizationException si el usuario
	 * no pertenece a la organización
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	List<Role> getUserOrganizationRoles(Serializable orgId, Serializable userId);

	/**
	 * Verifica si el usuario posee el role en la organización.
	 * @param orgId id de la organización
	 * @param userId id del usuario
	 * @param role role a verificar
	 * @return <code>true</code> si el usuario posee el role
	 * en la organización
	 * @throws OrganizationNotExistException Si la organización
	 * no existe
	 * @throws UserNotExistException Si el usuario no existe
	 * @throws UserNotInOrganizationException si el usuario
	 * no pertenece a la organización
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean hasRoleInOrganization(Serializable orgId, Serializable userId, Role role);

	/**
	 * Verifica si el usuario posee el role en la organización.
	 * @param orgId id de la organización
	 * @param userId id del usuario
	 * @param roleName nombre del role a verificar
	 * @return <code>true</code> si el usuario posee el role
	 * en la organización
	 * @throws OrganizationNotExistException Si la organización
	 * no existe
	 * @throws UserNotExistException Si el usuario no existe
	 * @throws UserNotInOrganizationException si el usuario
	 * no pertenece a la organización
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean hasRoleInOrganization(Serializable orgId, Serializable userId, String roleName);

	/**
	 * Actualiza los roles del usuario en la organización.
	 * @param orgId id de la organización
	 * @param userId id del usuario
	 * @param newRoles lista con los roles
	 * @throws OrganizationNotExistException Si la organización
	 * no existe
	 * @throws UserNotExistException Si el usuario no existe
	 * @throws UserNotInOrganizationException si el usuario
	 * no pertenece a la organización
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	void updateUserOrganizationRoles(Serializable orgId, Serializable userId, List<Role> newRoles);

	/**
	 * Obtiene todas las organizaciones a las que
	 * pertenece el usuario.
	 * @param userId id del usuario
	 * @return lista con las organizaciones
	 * @throws UserNotExistException Si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	List<Organization> getUserOrganizations(Serializable userId);

	/**
	 * Obtiene todos los ids de las organizaciones a las que
	 * pertenece el usuario.
	 * @param userId id del usuario
	 * @return lista con los ids de organización
	 * @throws UserNotExistException Si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	List<Serializable> getUserOrganizationsId(Serializable userId);

	/**
	 * Obtiene la cantidad de organizaciones a las que
	 * pertenece el usuario.
	 * @param userId id del usuario
	 * @return cantidad de organizaciones
	 * @throws UserNotExistException Si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	int getUserOrganizationsCount(Serializable userId);


	/**
	 * Obtiene todas las organizaciones a las que
	 * pertenece el usuario con al menos uno de los roles pasados.
	 * @param userId id del usuario
	 * @param roles las credenciales requeridas
	 * @return lista con las organizaciones
	 * @throws UserNotExistException Si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	List<Organization> getUserOrganizationsWithRoles(Serializable userId, Role ... roles);

	/**
	 * Obtiene todos los ids de las organizaciones a las que
	 * pertenece el usuario con al menos uno de los roles pasados.
	 * @param userId id del usuario
	 * @param roles las credenciales requeridas
	 * @return lista con los ids de organización
	 * @throws UserNotExistException Si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	List<Serializable> getUserOrganizationsWithRolesId(Serializable userId, Role ... roles);

	/**
	 * Obtiene la cantidad de organizaciones a las que
	 * pertenece el usuario con al menos uno de los roles pasados.
	 * @param userId id del usuario
	 * @param roles las credenciales requeridas
	 * @return cantidad de organizaciones
	 * @throws UserNotExistException Si el usuario no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	int getUserOrganizationsWithRolesCount(Serializable userId, Role ... roles);

	/**
	 * Obtiene todos los usuarios de la organización.
	 * @param id id de la organización
	 * @return lista de usuarios
	 * @throws OrganizationNotExistException Si la organización no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 * @see UserService#find(User, Serializable, Boolean, ConfigurableData)
	 */
	List<User> getUsers(Serializable id);

	/**
	 * Obtiene todos los ids de los usuarios de la organización.
	 * @param id id de la organización
	 * @return lista de usuarios
	 * @throws OrganizationNotExistException Si la organización no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 * @see UserService#find(User, Serializable, Boolean, ConfigurableData)
	 */
	List<Serializable> getUsersId(Serializable id);

	/**
	 * Obtiene la cantidad de todos los usuarios de la organización.
	 * @param id id de la organización
	 * @return cantidad de usuarios
	 * @throws OrganizationNotExistException Si la organización no existe
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 * @see UserService#findCount(User, Serializable, Boolean)
	 */
	int getUsersCount(Serializable id);
}
