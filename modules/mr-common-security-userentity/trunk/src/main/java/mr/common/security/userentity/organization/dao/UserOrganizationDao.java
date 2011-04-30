package mr.common.security.userentity.organization.dao;

import java.util.List;

import mr.common.dao.AbstractAuditableDao;
import mr.common.security.organization.model.Organization;
import mr.common.security.userentity.organization.model.UserOrganization;


/**
 * DAO de {@link mr.common.security.userentity.organization.model.
 * UserOrganization UserOrganization}.
 *
 * @author Mariano Ruiz
 */
public interface UserOrganizationDao extends AbstractAuditableDao<UserOrganization> {

	/**
	 * Obtiene el id del {@link mr.common.security.userentity.organization.model.
	 * UserOrganization UserOrganization} si existe uno con el <code>orgId</code>
	 * y el <code>userId</code>.<br/>
	 * Tener en cuenta que si no existe la organización o el usuario con los
	 * ids pasados el resultado será <code>null</code>.
	 * @param orgId id de la organización
	 * @param userId id del usuario
	 * @return el id del objeto, <code>null</code> si no existe la relación entre
	 * el usuario y la organización, o no existen usuario u organización con los
	 * ids pasados
	 */
	Long getUserOrganizationId(Long orgId, Long userId);

	/**
	 * Obtiene todas las organizaciones a las que pertenezca el usuario.<br/>
	 * Tener en cuenta que si no existe el usuario con el id pasado el resultado
	 * será <code>null</code>.
	 * @param userId id del usuario
	 * @return lista con las organizaciones, <code>null</code> si no existe
	 * el usuario con el id pasado
	 */
	List<Organization> getUserOrganizations(Long userId);
}
