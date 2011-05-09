package mr.common.security.userentity.organization.dao;

import java.io.Serializable;
import java.util.List;

import mr.common.dao.AbstractAuditableDao;
import mr.common.model.ConfigurableData;
import mr.common.security.organization.model.Organization;
import mr.common.security.userentity.organization.model.OrganizationEntity;


/**
 * DAO de {@link mr.common.security.userentity.organization.model.OrganizationEntity
 * OrganizationEntity}.
 * @author Mariano Ruiz
 */
public interface OrganizationEntityDao extends AbstractAuditableDao<OrganizationEntity> {

	/**
	 * Busca organizaciones según los parámetros pasados
	 * y en forma pagina.
	 * @param nameOrDescription - nombre o descripción
	 * de la organización
	 * @param userId - id del usuario que debe estar en las organizaciones
	 * @param activeFilter - si es distinto de <code>null</code>,
	 * su valor indica si se debe filtrar organizaciones
	 * activados/desactivados
	 * @param page - página de datos, <code>null</code>
	 * si se deben traer todos los datos y sin ordenar
	 * @return listado de organizaciones
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	List<OrganizationEntity> find(String nameOrDescription, Serializable userId,
	                              Boolean activeFilter, ConfigurableData page);

	/**
	 * Obtiene la cantidad de organizaciones por determinados parámetros.
	 * @param nameOrDescription - nombre o descripción
	 * de la organización
	 * @param userId - id del usuario que debe estar en las organizaciones
	 * @param activeFilter - si es distinto de <code>null</code>,
	 * su valor indica si se debe filtrar usuarios
	 * activados/desactivados
	 * @return listado de organizaciones
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	int findCount(String nameOrDescription, Serializable userId,
	              Boolean activeFilter);

	/**
	 * Obtiene una organización por su nombre.
	 * @param name nombre de la organización
	 * @return la organización, o <code>null</code>
	 * si no existe una con el nombre pasada
	 */
	Organization getByName(String name);

	/**
	 * Obtiene el nombre de la organización por
	 * su id.
	 * @param orgId el id de la organización
	 * @return el nombre de la organización, <code>null</code>
	 * si no existe la organización
	 */
	String getNameById(Serializable orgId);

	/**
	 * Obtiene el id de la organización por
	 * su nombre.
	 * @param name nombre de la organización
	 * @return el id de la organización
	 */
	Long getIdByName(String name);
}
