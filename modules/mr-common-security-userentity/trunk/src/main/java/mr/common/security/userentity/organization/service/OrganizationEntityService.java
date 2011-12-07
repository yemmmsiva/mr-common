package mr.common.security.userentity.organization.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mr.common.model.ConfigurableData;
import mr.common.security.exception.UserLockedException;
import mr.common.security.model.Role;
import mr.common.security.model.User;
import mr.common.security.organization.exception.DuplicatedOrganizationException;
import mr.common.security.organization.exception.InvalidOrganizationNameException;
import mr.common.security.organization.exception.OrganizationLockedException;
import mr.common.security.organization.exception.OrganizationNotExistException;
import mr.common.security.organization.exception.UserIsInOrganizationException;
import mr.common.security.organization.exception.UserNotInOrganizationException;
import mr.common.security.organization.model.Organization;
import mr.common.security.organization.service.OrganizationService;
import mr.common.security.service.UserService;
import mr.common.security.userentity.model.RoleEntity;
import mr.common.security.userentity.model.UserEntity;
import mr.common.security.userentity.organization.dao.OrganizationEntityDao;
import mr.common.security.userentity.organization.dao.UserOrganizationDao;
import mr.common.security.userentity.organization.dao.UserOrganizationRoleDao;
import mr.common.security.userentity.organization.model.OrganizationEntity;
import mr.common.security.userentity.organization.model.UserOrganization;
import mr.common.security.userentity.organization.model.UserOrganizationRole;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementación de {@link
 * mr.common.security.organization.service.OrganizationService
 * OrganizationService}.
 *
 * @see mr.common.security.userentity.organization.model.OrganizationEntity
 * @author Mariano Ruiz
 */
public class OrganizationEntityService implements OrganizationService {

	private static final Log logger = LogFactory.getLog(OrganizationEntityService.class);

	@Resource
	private OrganizationEntityDao orgDao;

	@Resource
	private UserOrganizationDao userOrganizationDao;

	@Resource
	private UserOrganizationRoleDao userOrganizationRoleDao;

	@Autowired
	private UserService userService;


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true)
	public List getList() {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Loading all organizations.");
    	}
		return orgDao.getList();
	}

	@Transactional(readOnly = true)
	public int count() {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Counting all organizations.");
    	}
		return (int) orgDao.count();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true)
	public List find(String nameOrDescription, Boolean activeFilter,
			ConfigurableData page) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Finding organizations.");
    	}
		return orgDao.find(nameOrDescription, null, null, activeFilter, page);
	}

	@Transactional(readOnly = true)
	public int findCount(String nameOrDescription, Boolean activeFilter) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Count finding organizations.");
    	}
		return orgDao.findCount(nameOrDescription, null, null, activeFilter);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true)
	public List find(String nameOrDescription, Serializable userId, Role role,
			Boolean activeFilter, ConfigurableData page) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Finding organizations of user.");
    	}
		userService.getUsernameById(userId); // Lanza una excepción si no existe el usuario
		return orgDao.find(nameOrDescription, userId, role, activeFilter, page);
	}

	@Transactional(readOnly = true)
	public int findCount(String nameOrDescription, Serializable userId, Role role,
			Boolean activeFilter) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Count finding organizations of user.");
    	}
		return orgDao.findCount(nameOrDescription, userId, role, activeFilter);
	}

	@Transactional(readOnly = true)
	public Organization getById(Serializable id) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting organization with id=" + id);
    	}
    	return getById(id, true);
	}

	@Transactional(readOnly = true)
	protected Organization getById(Serializable id, boolean readOnly) {
    	if(id==null) {
    		throw new NullPointerException("id = null.");
    	}
    	Organization org = orgDao.get((Long)id);
		if(org==null) {
			throw new OrganizationNotExistException(
					"Organization with id=" + id.toString() + " not exist.");
		}
		if(!readOnly && org.isLocked()) {
			throw new OrganizationLockedException();
		}
		return org;
	}

	@Transactional(readOnly = true)
	public Organization getByName(String name) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting organization with name=" + name);
    	}
    	if(name==null) {
    		throw new NullPointerException("name = null.");
    	}
    	Organization org = orgDao.getByName(name);
		if(org==null) {
			throw new OrganizationNotExistException(
					"Organization with name=" + name + " not exist.");
		}
		return org;
	}

	@Transactional(readOnly = false)
	public Organization newOrganization(Organization org) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Creating new organization.");
    	}
    	if(org==null) {
    		throw new NullPointerException("org = null.");
    	}
		if(org.getId()!=null) {
			throw new IllegalArgumentException(
					"New organization should not have set the id.");
		}
		return saveOrUpdate((OrganizationEntity)org);
	}

	@Transactional(readOnly = false)
	private Organization saveOrUpdate(OrganizationEntity org) {
		OrganizationEntity organization;
		if(org.getId()!=null) {
			organization = (OrganizationEntity) getById(org.getId(), false);
		} else {
			organization = new OrganizationEntity();
		}
		if(isValidOrganizationName(org.getName())) {
			organization.setName(org.getName());
		} else {
			throw new InvalidOrganizationNameException(org.getName());
		}
		OrganizationEntity duplicate = (OrganizationEntity) orgDao.getByName(org.getName());
		if(duplicate!=null && (org.getId()==null
				|| (org.getId()!=null && !org.getId().equals(duplicate.getId())))) {
			throw new DuplicatedOrganizationException();
		}
		organization.setEnabled(org.isEnabled());
		organization.setDescription(org.getDescription());
		organization.setTimeZoneId(org.getTimeZoneId());
		organization.setCountryId(org.getCountryId());
		organization.setCityOrRegionName(org.getCityOrRegionName());
		organization.setStateOrProvinceName(org.getStateOrProvinceName());
		organization.setPostalAddress(org.getPostalAddress());
		organization.setPostalCode(org.getPostalCode());
		organization.setTelephoneNumber(org.getTelephoneNumber());
		organization.setLogoId(org.getLogoId());
		return orgDao.merge(organization);
	}

	@Transactional(readOnly = false)
	public Organization updateOrganization(Serializable id, Organization organization) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating organization with id=" + id);
    	}
		if(id==null) {
			throw new NullPointerException(
					"id = null.");
		}
		if(organization.getId()!=null) {
			throw new IllegalArgumentException(
					"Argument `organization` should not have set the id.");
		}
		OrganizationEntity organizationEntity = (OrganizationEntity) organization;
		organizationEntity.setId((Long)id);
		return saveOrUpdate((OrganizationEntity)organization);
	}

	@Transactional(readOnly = false)
	public Organization updateOrganization(String name, Organization organization) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating organization with name=" + name);
    	}
		if(name==null) {
			throw new NullPointerException(
					"name = null.");
		}
		if(organization.getId()!=null) {
			throw new IllegalArgumentException(
					"Argument `organization` should not have set the id.");
		}
		OrganizationEntity organizationEntity = (OrganizationEntity) organization;
		organizationEntity.setId(getByName(name).getId());
		return saveOrUpdate((OrganizationEntity)organization);
	}

	public Organization getOrganizationInstance() {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Creating a new organization object instance, class="
    				+ OrganizationEntity.class.getName());
    	}
		return new OrganizationEntity();
	}

	public boolean isValidOrganizationName(String name) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Validating organization name=" + name);
    	}
		if(!name.equals(name.trim())) {
			return false;
		}
		return true;
	}

	@Transactional(readOnly = false)
	public void deleteByName(String name) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Deleting organization with name=" + name);
    	}
		Long orgId = (Long) getIdByName(name);
		removeAllUsersFromOrganization(orgId);
		orgDao.delete((OrganizationEntity) getById(orgId, false));
	}

	@Transactional(readOnly = false)
	public void deleteById(Serializable id) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Deleting organization with id=" + id);
    	}
		removeAllUsersFromOrganization((Long) id);
		orgDao.delete((OrganizationEntity) getById(id, false));
	}

	@Transactional(readOnly = false)
	public void updateLogoId(Serializable orgId, Serializable newLogoId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating logoId of organization with id=" + orgId);
    	}
		OrganizationEntity org = (OrganizationEntity) getById(orgId, false);
		org.setLogoId((Long)newLogoId);
		orgDao.update(org);
	}

	@Transactional(readOnly = false)
	public void updateLock(Serializable orgId, boolean lock) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating lock of organization with id=" + orgId);
    	}
		OrganizationEntity org = (OrganizationEntity) getById(orgId);
		org.setLocked(lock);
		orgDao.update(org);
	}

	@Transactional(readOnly = false)
	public void addUser(Serializable orgId, Serializable userId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Adding user to organization with id=" + orgId);
    	}
		UserEntity user = (UserEntity) userService.getById(userId);
		if(user.isLocked()) {
			throw new UserLockedException(user);
		}
		OrganizationEntity org = (OrganizationEntity) getById(orgId);
		if(isUserInOrganization(orgId, userId)) {
			throw new UserIsInOrganizationException(orgId, userId);
		}
		UserOrganization userOrganization = new UserOrganization();
		userOrganization.setUser(user);
		userOrganization.setOrganization(org);
		userOrganizationDao.save(userOrganization);
	}

	@Transactional(readOnly = false)
	public void removeUser(Serializable orgId, Serializable userId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Removing user from organization with id=" + orgId);
    	}
		UserEntity user = (UserEntity) userService.getById(userId);
		if(user.isLocked()) {
			throw new UserLockedException(user);
		}
		getNameById(orgId); // Si la organización no existe lanza excepción
		UserOrganization uo = userOrganizationDao.getUserOrganization((Long)orgId, (Long)userId);
		if(uo==null) {
			throw new UserNotInOrganizationException(orgId, userId);
		}
		if(uo.getAuthorities()!=null) {
			for(UserOrganizationRole r : uo.getAuthorities()) {
				userOrganizationRoleDao.delete(r);
			}
		}
		userOrganizationDao.delete(uo);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public int removeUserFromAll(Serializable userId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Removing user from all organizations.");
    	}
		UserEntity user = (UserEntity) userService.getById(userId);
		if(user.isLocked()) {
			throw new UserLockedException(user);
		}
		List<Long> orgIds = userOrganizationDao.getUserOrganizationsId((Long)userId);
		for(Long orgId : orgIds) {
			removeUser(orgId, userId);
		}
		return orgIds.size();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public int removeAllUsersFromOrganization(Long id) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Removing all user from organization with id=" + id);
    	}
		getNameById(id); // Si la organización no existe lanza excepción
		List<Long> userIds = userOrganizationDao.getUsersId(id);
		for(Long userId : userIds) {
			removeUser(id, userId);
		}
		return userIds.size();
	}

	@Transactional(readOnly = true)
	public boolean isUserInOrganization(Serializable orgId, Serializable userId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Validating if user is in organization with id=" + orgId);
    	}
		userService.getUsernameById(userId); // Si el user no existe lanza excepción
		getNameById(orgId); // Si la organización no existe lanza excepción
		return userOrganizationDao.getUserOrganizationId((Long)orgId, (Long)userId) != null;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Serializable> getUserOrganizationsId(Serializable userId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting organization ids of user with id=" + userId);
    	}
		userService.getUsernameById(userId); // Si el user no existe lanza excepción
		return userOrganizationDao.getUserOrganizationsId((Long)userId);
	}

	@Transactional(readOnly = true)
	public List<Organization> getUserOrganizations(Serializable userId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting organization of user with id=" + userId);
    	}
		userService.getUsernameById(userId); // Si el user no existe lanza excepción
		return userOrganizationDao.getUserOrganizations((Long)userId);
	}

	@Transactional(readOnly = true)
	public int getUserOrganizationsCount(Serializable userId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Counting organization of user with id=" + userId);
    	}
		userService.getUsernameById(userId); // Si el user no existe lanza excepción
		return userOrganizationDao.getUserOrganizationsCount((Long)userId);
	}

	@Transactional(readOnly = true)
	public List<Serializable> getUserOrganizationsWithRolesId(Serializable userId, Role ... roles) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting organization ids of user with roles.");
    	}
		userService.getUsernameById(userId); // Si el user no existe lanza excepción
		return userOrganizationRoleDao.getUserOrganizationsWithRolesId((Long)userId, roles);
	}

	@Transactional(readOnly = true)
	public List<Organization> getUserOrganizationsWithRoles(Serializable userId, Role ... roles) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting organization of user with roles.");
    	}
		userService.getUsernameById(userId); // Si el user no existe lanza excepción
		return userOrganizationRoleDao.getUserOrganizationsWithRoles((Long)userId, roles);
	}

	@Transactional(readOnly = true)
	public int getUserOrganizationsWithRolesCount(Serializable userId, Role ... roles) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Counting organization ids of user with roles.");
    	}
		userService.getUsernameById(userId); // Si el user no existe lanza excepción
		return userOrganizationRoleDao.getUserOrganizationsWithRolesCount((Long)userId, roles);
	}

	@Transactional(readOnly = true)
	public String getNameById(Serializable orgId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting name of organization with id=" + orgId);
    	}
		if(orgId==null) {
			throw new NullPointerException(
					"orgId = null.");
		}
		String name = orgDao.getNameById(orgId);
		if(name==null) {
			throw new OrganizationNotExistException(
					"Organization with id=" + orgId.toString() + " not exist.");
		}
		return name;
	}

	@Transactional(readOnly = true)
	public Serializable getIdByName(String name) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting id of organization with name=" + name);
    	}
		if(name==null) {
			throw new NullPointerException(
					"name = null.");
		}
		Long id = orgDao.getIdByName(name);
		if(id==null) {
			throw new OrganizationNotExistException(
					"Organization with name=" + name + " not exist.");
		}
		return id;
	}

	@Transactional(readOnly = true)
	public List<User> getUsers(Serializable id) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Loading users from organization with id=" + id);
    	}
		getNameById(id); // Si la organización no existe lanza excepción
		return userOrganizationDao.getUsers(id);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Serializable> getUsersId(Serializable id) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Loading users ids from organization with id=" + id);
    	}
		getNameById(id); // Si la organización no existe lanza excepción
		return userOrganizationDao.getUsersId(id);
	}

	@Transactional(readOnly = true)
	public int getUsersCount(Serializable id) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Counting users from organization with id=" + id);
    	}
		getNameById(id); // Si la organización no existe lanza excepción
		return userOrganizationDao.getUsersCount(id);
	}

	@Transactional(readOnly = true)
	public List<Role> getUserOrganizationRoles(Serializable orgId,
			Serializable userId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting roles of user in organization.");
    	}
		userService.getUsernameById(userId); // Lanza una excepción si no existe el usuario
		getNameById(orgId);                  // Lanza una excepción si no existe la organización
		UserOrganization userOrganization = userOrganizationDao.getUserOrganization((Long)orgId, (Long)userId);
		if(userOrganization==null) {
			throw new UserNotInOrganizationException(orgId, userId);
		}
		return userOrganization.getRoles();
	}

	@Transactional(readOnly = true)
	public boolean hasRoleInOrganization(Serializable orgId,
			Serializable userId, String roleName) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Validating roles of user in organization.");
    	}
		userService.getUsernameById(userId); // Lanza una excepción si no existe el usuario
		getNameById(orgId);                  // Lanza una excepción si no existe la organización
		UserOrganization userOrganization = userOrganizationDao.getUserOrganization((Long)orgId, (Long)userId);
		if(userOrganization==null) {
			throw new UserNotInOrganizationException(orgId, userId);
		}
		List<Role> roles = userOrganization.getRoles();
		for(Role r : roles) {
			if(r.getAuthority().equals(roleName)) {
				return true;
			}
		}
		return false;
	}

	@Transactional(readOnly = true)
	public boolean hasRoleInOrganization(Serializable orgId,
			Serializable userId, Role role) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Validating roles of user in organization.");
    	}
		return hasRoleInOrganization(orgId, userId, role.getAuthority());
	}

	@Transactional(readOnly = false)
	public void updateUserOrganizationRoles(Serializable orgId,
			Serializable userId, List<Role> newRoles) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating roles of user in organization.");
    	}
		UserEntity user = (UserEntity) userService.getById(userId);
		if(user.isLocked()) {
			throw new UserLockedException(user);
		}
		getNameById(orgId);   // Lanza una excepción si no existe la organización
		UserOrganization userOrganization = userOrganizationDao.getUserOrganization((Long)orgId, (Long)userId);
		if(userOrganization==null) {
			throw new UserNotInOrganizationException(orgId, userId);
		}

		// Borramos los roles anteriores contenidos en
		// la nueva lista
		List<Role> currentSavedRoles = new ArrayList<Role>(newRoles.size());
		if(userOrganization.getAuthorities()!=null) {
			for(UserOrganizationRole au : userOrganization.getAuthorities()) {
				if(!newRoles.contains(au.getRole())) {
					userOrganizationRoleDao.delete(au);
				} else {
					currentSavedRoles.add(au.getRole());
				}
			}
		}

		// Cargamos los nuevos
		for(Role role : newRoles) {
			if(!currentSavedRoles.contains(role)) {
				UserOrganizationRole au = new UserOrganizationRole();
				au.setRole((RoleEntity)role);
				au.setUserOrganization(userOrganization);
				userOrganizationRoleDao.save(au);
			}
		}
	}

	@Transactional(readOnly = false)
	public void addUser(Serializable orgId, Serializable userId,
			List<Role> roles) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Adding user in organization with roles.");
    	}
		addUser(orgId, userId);
		updateUserOrganizationRoles(orgId, userId, roles);
	}
}
