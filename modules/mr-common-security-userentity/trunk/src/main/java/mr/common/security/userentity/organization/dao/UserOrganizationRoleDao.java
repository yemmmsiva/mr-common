package mr.common.security.userentity.organization.dao;

import java.io.Serializable;
import java.util.List;

import mr.common.dao.AbstractAuditableDao;
import mr.common.security.exception.UserNotExistException;
import mr.common.security.model.Role;
import mr.common.security.organization.model.Organization;
import mr.common.security.userentity.organization.model.UserOrganizationRole;


/**
 * DAO de {@link mr.common.security.userentity.organization.model.
 * UserOrganizationRole UserOrganizationRole}.
 *
 * @author Mariano Ruiz
 */
public interface UserOrganizationRoleDao extends
		AbstractAuditableDao<UserOrganizationRole> {

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
}
