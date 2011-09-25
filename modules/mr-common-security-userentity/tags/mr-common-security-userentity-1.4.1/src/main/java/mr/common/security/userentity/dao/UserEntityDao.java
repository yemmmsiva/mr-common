package mr.common.security.userentity.dao;

import java.io.Serializable;
import java.util.List;

import mr.common.dao.AbstractAuditableDao;
import mr.common.model.ConfigurableData;
import mr.common.security.exception.IllegalArgumentUserFindException;
import mr.common.security.model.User;
import mr.common.security.userentity.model.Authority;
import mr.common.security.userentity.model.UserEntity;


/**
 * Interfaz DAO de {@link mr.common.security.userentity.model.UserEntity UserEntity}.
 * @author marruiz
 */
public interface UserEntityDao extends AbstractAuditableDao<UserEntity> {

	/**
	 * Obtiene el usuario por el nombre.
	 * @param username String
	 * @return user
	 */
	UserEntity getByUsername(String username);

	/**
	 * Obtiene el usuario por su dirección de email.
	 * @param emailAddress String
	 * @return user
	 */
	UserEntity getByEmailAddress(String emailAddress);

	/**
	 * Roles del usuario.
	 * @param username String
	 * @return {@link mr.common.security.model.Authority} roles
	 */
	List<Authority> getAuthorityList(String username);

	/**
	 * Roles del usuario.
	 * @param userId Long
	 * @return {@link mr.common.security.model.Authority} roles
	 */
	List<Authority> getAuthorityList(Long userId);

	/**
	 * Búsqueda de usuario según criterios pasados, y paginación.
	 * @param user - datos a machear
	 * @param orgId - id de organización al que deben
	 * pertener los usuarios, <code>null</code> para
	 * cualquier organización
	 * @param activeFilter - si es distinto de <code>null</code>,
	 * su valor indica si se debe filtrar usuarios
	 * activados/desactivados
	 * @return lista de usuarios
	 * @throws IllegalArgumentUserFindException Si los argumentos
	 * de búsqueda son inválidos, o si se lanza una {@link org.hibernate.QueryException}
	 */
	List<UserEntity> find(User user, Serializable orgId, Boolean activeFilter, ConfigurableData page);

	/**
	 * Obtiene la cantidad de usuarios según criterios pasados.
	 * @param user - datos a machear
	 * @param orgId - id de organización al que deben
	 * pertener los usuarios, <code>null</code> para
	 * cualquier organización
	 * @param activeFilter - si es distinto de <code>null</code>,
	 * su valor indica si se debe filtrar usuarios
	 * activados/desactivados
	 * @return int
	 */
	int findCount(User user, Serializable orgId, Boolean activeFilter);

	/**
	 * Obtiene el username del usuario por su id.
	 * @param userId id del usuario
	 * @return el username del usuario, <code>null</code>
	 * si el usuario no existe
	 */
	String getUsernameById(Long userId);

	/**
	 * Obtiene el id del usuario por su username.
	 * @param username nombre del usuario
	 * @return el id del usuario, <code>null</code>
	 * si el usuario no existe
	 */
	Long getIdByUsername(String username);

	/**
	 * Obtiene el id del usuario por su email.
	 * @param email del usuario
	 * @return el id del usuario, <code>null</code>
	 * si el usuario no existe
	 */
	Long getIdByEmailAddress(String emailAddress);
}
