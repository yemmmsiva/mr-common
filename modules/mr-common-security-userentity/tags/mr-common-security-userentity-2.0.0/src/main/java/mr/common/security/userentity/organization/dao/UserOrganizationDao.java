package mr.common.security.userentity.organization.dao;

import java.io.Serializable;
import java.util.List;

import mr.common.dao.AbstractAuditableDao;
import mr.common.security.model.User;
import mr.common.security.organization.model.Organization;
import mr.common.security.userentity.organization.model.UserOrganization;


/**
 * DAO de {@link mr.common.security.userentity.organization.model.UserOrganization
 * UserOrganization}.
 *
 * @author Mariano Ruiz
 */
public interface UserOrganizationDao extends AbstractAuditableDao<UserOrganization> {

	/**
	 * Obtiene el {@link mr.common.security.userentity.organization.model.UserOrganization
	 * UserOrganization} si existe uno con el <code>orgId</code>
	 * y el <code>userId</code>.<br/>
	 * Tener en cuenta que si no existe la organización o el usuario con los
	 * ids pasados el resultado será <code>null</code>.
	 * @param orgId id de la organización.
	 * @param userId id del usuario.
	 * @return el objeto entidad, <code>null</code> si no existe la relación entre
	 * el usuario y la organización, o no existen usuario u organización con los
	 * ids pasados.
	 */
	UserOrganization getUserOrganization(Long orgId, Long userId);

	/**
	 * Obtiene el id del {@link mr.common.security.userentity.organization.model.UserOrganization
	 * UserOrganization} si existe uno con el <code>orgId</code>
	 * y el <code>userId</code>.<br/>
	 * Tener en cuenta que si no existe la organización o el usuario con los
	 * ids pasados el resultado será <code>null</code>.
	 * @param orgId id de la organización.
	 * @param userId id del usuario.
	 * @return el id del objeto, <code>null</code> si no existe la relación entre
	 * el usuario y la organización, o no existen usuario u organización con los
	 * ids pasados.
	 */
	Long getUserOrganizationId(Long orgId, Long userId);

	/**
	 * Obtiene todas las organizaciones a las que pertenezca el usuario.<br/>
	 * Tener en cuenta que si no existe el usuario con el id pasado el resultado
	 * será <code>null</code>.
	 * @param userId id del usuario.
	 * @return lista con las organizaciones, <code>null</code> si no existe
	 * el usuario con el id pasado.
	 */
	List<Organization> getUserOrganizations(Long userId);

	/**
	 * Obtiene todas los ids de organizaciones a las que pertenezca el usuario.<br/>
	 * Tener en cuenta que si no existe el usuario con el id pasado el resultado
	 * será <code>null</code>.
	 * @param userId id del usuario.
	 * @return lista con los ids de las organizaciones, <code>null</code> si no existe
	 * el usuario con el id pasado.
	 */
	@SuppressWarnings("rawtypes")
	List getUserOrganizationsId(Long userId);

	/**
	 * Obtiene la cantidad de organizaciones a las que
	 * pertenece el usuario.
	 * @param userId id del usuario.
	 * @return cantidad de organizaciones.
	 */
	int getUserOrganizationsCount(Long userId);

	/**
	 * Quita al usuario de todas las organizaciones.
	 * @param userId id del usuario.
	 * @return cantidad de organizaciones en las que
	 * fue removido el usuario.
	 */
	int removeUserFromAll(Long userId);

	/**
	 * Quita todos los usuarios de la organización.
	 * @param id identificador de la organización.
	 * @return cantidad de usuarios quitados.
	 */
	int removeAllUsersFromOrganization(Long id);

	/**
	 * Obtiene todos los usuarios de la organización.
	 * @param id id de la organización.
	 * @return lista de usuarios.
	 */
	List<User> getUsers(Serializable id);

	/**
	 * Obtiene todos los ids de los usuarios de la organización.
	 * @param id id de la organización.
	 * @return lista de usuarios.
	 */
	@SuppressWarnings("rawtypes")
	List getUsersId(Serializable id);

	/**
	 * Obtiene la cantidad de todos los usuarios de la organización.
	 * @param id id de la organización.
	 * @return cantidad de usuarios.
	 */
	int getUsersCount(Serializable id);
}
