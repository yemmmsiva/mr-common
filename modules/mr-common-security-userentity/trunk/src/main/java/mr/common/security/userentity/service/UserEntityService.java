package mr.common.security.userentity.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mr.common.format.validator.Validator;
import mr.common.model.ConfigurableData;
import mr.common.security.Encoder;
import mr.common.security.Md5Encoder;
import mr.common.security.RandomString;
import mr.common.security.UUIDRandomString;
import mr.common.security.exception.DuplicatedEmailAddressException;
import mr.common.security.exception.DuplicatedUserException;
import mr.common.security.exception.EncodePasswordException;
import mr.common.security.exception.InvalidEmailAddressException;
import mr.common.security.exception.InvalidPasswordException;
import mr.common.security.exception.InvalidRoleException;
import mr.common.security.exception.InvalidUsernameException;
import mr.common.security.exception.UserLockedException;
import mr.common.security.exception.UserNotExistException;
import mr.common.security.model.Role;
import mr.common.security.model.User;
import mr.common.security.organization.service.OrganizationService;
import mr.common.security.service.UserSecurityService;
import mr.common.security.service.UserService;
import mr.common.security.userentity.dao.AuthorityDao;
import mr.common.security.userentity.dao.RoleDao;
import mr.common.security.userentity.dao.UserDataDao;
import mr.common.security.userentity.dao.UserEntityDao;
import mr.common.security.userentity.model.Authority;
import mr.common.security.userentity.model.RoleEntity;
import mr.common.security.userentity.model.UserData;
import mr.common.security.userentity.model.UserEntity;
import mr.common.security.userentity.validator.EmailAddressValidator;
import mr.common.security.userentity.validator.PasswordValidator;
import mr.common.security.userentity.validator.UsernameValidator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementación de {@link mr.common.security.service.UserService UserService},
 * que maneja usuarios {@link mr.common.security.userentity.model.UserEntity UserEntity}.
 * @author Mariano Ruiz
 */
public class UserEntityService implements UserService {

	//private final Log logger = LogFactory.getLog(getClass());
	private static final Log logger = LogFactory.getLog(UserEntityService.class);

	// Los DAOs como protected por si son necesarios
	// en una implementación que sobreescriba esta
	// y los necesite..
	@Resource
	protected UserEntityDao userDao;
	@Resource
	protected UserDataDao userDataDao;
	@Resource
	protected AuthorityDao authorithyDao;
	@Resource
	protected RoleDao roleDao;

	@Resource
	private UserSecurityService userSecurityService;

	@Autowired(required = false)
	private OrganizationService organizationService;

	// Validadores de passwords, username e emails,
	// estos objetos deben ser inmutables, por lo que
	// es correcto setearlos por el inyector
	// de objetos o con su constructor.
	// En caso de no configurarse por default se usarán
	// las implementaciones del framework.
	private Validator passwordValidator = new PasswordValidator(4, 20);
	private Validator usernameValidator = new UsernameValidator();
	private Validator emailAddressValidator = new EmailAddressValidator();

	// Encoder de passwords, este objeto deber ser inmutable,
	// por lo que solo debe ser seteado por el inyector
	// de objetos o con su constructor.
	// En caso de no configurarse por default se usará
	// un encoder MD5.
	private Encoder passwordEncoder = new Md5Encoder();

	// Generador de passwords aleatorias.
	// En caso de no configurarse por default se usará
	// un generador de claves hexadecimal con 8 dígitos.
	private RandomString passwordGenerator = new UUIDRandomString(8);


	public UserEntityService() { }

	public UserEntityService(Validator usernameValidator,
	                          Validator passwordValidator,
	                          Validator emailAddressValidator) {

		this.usernameValidator = usernameValidator;
		this.passwordValidator = passwordValidator;
		this.emailAddressValidator = emailAddressValidator;
	}

	public UserEntityService(Encoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public UserEntityService(RandomString passwordGenerator) {
		this.passwordGenerator = passwordGenerator;
	}

	public UserEntityService(Validator usernameValidator,
	                          Validator passwordValidator,
	                          Validator emailAddressValidator,
	                          Encoder passwordEncoder,
	                          RandomString passwordGenerator) {

		this.usernameValidator = usernameValidator;
		this.passwordValidator = passwordValidator;
		this.emailAddressValidator = emailAddressValidator;
		this.passwordEncoder = passwordEncoder;
		this.passwordGenerator = passwordGenerator;
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true)
	public List getList() {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Loading all users.");
    	}
    	return userDao.getList();
	}

    @Transactional(readOnly = true)
	public int count() {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Counting all users.");
    	}
    	return (int) userDao.count();
    }

    @Transactional(readOnly = true)
	public User getByUsername(String username) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting user with username=" + username);
    	}
    	return getByUsername(username, false);
	}

    @Transactional(readOnly = true)
	protected User getByUsername(String username, boolean toEdit) {
    	if(username==null) {
    		throw new NullPointerException("username = null.");
    	}
		User user = userDao.getByUsername(username);
		if(user==null) {
			throw new UserNotExistException(
					"User with username=" + username + " not exist.");
		}
		if(toEdit && user.isLocked()) {
			throw new UserLockedException(user);
		}
		return user;
	}

    @Transactional(readOnly = true)
	public User getByEmailAddress(String emailAddress) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting user with emailAddress=" + emailAddress);
    	}
    	if(emailAddress==null) {
    		throw new NullPointerException("emailAddress = null.");
    	}
		User user = userDao.getByEmailAddress(emailAddress);
		if(user==null) {
			throw new UserNotExistException(
					"User with emailAddress=" + emailAddress + " not exist.");
		}
		return user;
	}

    @Transactional(readOnly = true)
	public User getCurrentUser() {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting current user...");
    	}
		String username = userSecurityService.getCurrentUsername();
    	if(logger.isDebugEnabled()) {
    		logger.debug("... current username=" + username);
    	}
		if(username != null) {
			return getByUsername(username);
		}
		return null;
	}

    @Transactional(readOnly = true)
	public Serializable getCurrentUserId() {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting current user id...");
    	}
		String username = userSecurityService.getCurrentUsername();
		if(username != null) {
			Serializable userId = getIdByUsername(username);
	    	if(logger.isDebugEnabled()) {
	    		logger.debug("... current userId=" + userId);
	    	}
			return userId;
		}
		return null;
	}

    @Transactional(readOnly = true)
	public String getCurrentUsername() {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting current username...");
    	}
		String username = userSecurityService.getCurrentUsername();
    	if(logger.isDebugEnabled()) {
    		logger.debug("... current username=" + username);
    	}
    	return username;
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true)
	public List find(User user, Boolean activeFilter, ConfigurableData page) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Finding users.");
    	}
		return userDao.find(user, null, activeFilter, page);
	}

    @Transactional(readOnly = true)
	public int findCount(User user, Boolean activeFilter) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Count finding users.");
    	}
		return userDao.findCount(user, null, activeFilter);
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true)
	public List find(User user, Serializable orgId, Boolean activeFilter, ConfigurableData page) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Finding users in organization.");
    	}
    	organizationService.getNameById(orgId); // Lanza una excepción si no existe la organización
		return userDao.find(user, orgId, activeFilter, page);
	}

    @Transactional(readOnly = true)
	public int findCount(User user, Serializable orgId, Boolean activeFilter) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Count finding users in organization.");
    	}
    	organizationService.getNameById(orgId); // Lanza una excepción si no existe la organización
		return userDao.findCount(user, orgId, activeFilter);
	}

    @Transactional(readOnly = false)
	public void deleteByUsername(String username) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Deleting user with username=" + username);
    	}
    	UserEntity u = (UserEntity) getByUsername(username, true);
    	if(organizationService!=null) {
    		organizationService.removeUserFromAll(u.getId());
    	}
		userDataDao.delete(u.getUserData());
		authorithyDao.deleteList(u.getAuthorities());
		userDao.delete(u);
	}

	private UserEntity saveOrUpdate(UserEntity user, Serializable orgId) {
		UserEntity userEntity;
		UserData userData;

		// Recuperamos el usuario o creamos el nuevo
		boolean userExist = user.getId()!=null;
		if(userExist) {
			userEntity = (UserEntity) getById(user.getId(), true);
			userData = userEntity.getUserData();
		} else {
			userEntity = new UserEntity();
			userData = new UserData();
		}

		// Validamos los campos
		if(!isValidUsername(user.getUsername())) {
			throw new InvalidUsernameException(user.getUsername());
		}
		if((user.getPassword()!=null && !isValidPassword(user.getPassword()))
				|| (user.getId()==null && user.getPassword()==null)) {
			throw new InvalidPasswordException();
		}
		if(!isValidEmailAddress(user.getEmailAddress())) {
			throw new InvalidEmailAddressException();
		}
		
		UserEntity duplicate = userDao.getByUsername(user.getUsername());
		if(duplicate!=null && (userEntity.getId()==null
				|| (userEntity.getId()!=null && !userEntity.getId().equals(duplicate.getId())))) {
			throw new DuplicatedUserException();
		}
		duplicate = userDao.getByEmailAddress(user.getEmailAddress());
		if(duplicate!=null && (userEntity.getId()==null
				|| (userEntity.getId()!=null && !userEntity.getId().equals(duplicate.getId())))) {
			throw new DuplicatedEmailAddressException();
		}
		userData.setCommonName(user.getUserData().getCommonName());
		userData.setLastName(user.getUserData().getLastName());
		userData.setTimeZoneId(user.getUserData().getTimeZoneId());
		userData.setCityOrRegionName(user.getUserData().getCityOrRegionName());
		userData.setStateOrProvinceName(user.getUserData().getStateOrProvinceName());
		userData.setPostalAddress(user.getUserData().getPostalAddress());
		userData.setPostalCode(user.getUserData().getPostalCode());
		userData.setCountryId(user.getUserData().getCountryId());
		userData.setBirthdayDate(user.getUserData().getBirthdayDate());
		userData.setTelephoneNumber(user.getUserData().getTelephoneNumber());
		userData.setDescription(user.getUserData().getDescription());
		userData.setPortraitId(user.getUserData().getPortraitId());
		userData.setLocked(user.getUserData().isLocked());
		userEntity.setUsername(user.getUsername());
		userEntity.setEmailAddress(user.getEmailAddress());
		userEntity.setEnabled(user.isEnabled());
		if(user.getPassword()!=null) {
			try {
				userEntity.setPassword(encodePassword(user.getPassword()));
			} catch (EncodePasswordException e) {
				logger.error(
					"An error occurred when encoding the password of the user=" + userEntity.getUsername() + ".", e);
				throw e;
			}
		}

		// Guardamos o actualizamos el usuario
		userEntity.setUserData(userData);
		userData.setUser(userEntity);
		userDataDao.saveOrUpdate(userData);
		userEntity = userDao.merge(userEntity);


		if(user.getRoles()!=null) {
			updateRoles(userEntity, user.getRoles());
		}

		if(!userExist && orgId!=null) {
			organizationService.addUser(orgId, userEntity.getId());
		}

		return userEntity;
	}

	@Transactional(readOnly = false)
	public User newUser(User user) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Creating new user...");
    	}
		if(user==null) {
			throw new NullPointerException("user = null.");
		}
		if(user.getId()!=null) {
			throw new IllegalArgumentException(
					"New user should not have set the id.");
		}
		UserEntity userEntity = saveOrUpdate((UserEntity)user, null);
    	if(logger.isDebugEnabled()) {
    		logger.debug("... new user created with id=" + userEntity.getId());
    	}
		return userEntity;
	}

	@Transactional(readOnly = false)
	public User newUser(User user, Serializable orgId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Creating new user in organization with id=" + orgId);
    	}
		if(user==null) {
			throw new NullPointerException("user = null.");
		}
		if(orgId==null) {
			throw new NullPointerException("orgId = null.");
		}
		if(user.getId()!=null) {
			throw new IllegalArgumentException(
					"New user should not have set the id.");
		}
		return saveOrUpdate((UserEntity)user, orgId);
	}

	@Transactional(readOnly = false)
	public User updateUser(Serializable id, User user) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating user with id=" + id);
    	}
		if(id==null) {
			throw new NullPointerException(
					"id = null.");
		}
		if(user.getId()!=null) {
			throw new IllegalArgumentException(
					"Argument `user` should not have set the id.");
		}
		UserEntity userEntity = (UserEntity) user;
		userEntity.setId((Long)id);
		return saveOrUpdate((UserEntity)user, null);
	}

	@Transactional(readOnly = false)
	public User updateUser(String username, User user) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating user with username=" + username);
    	}
		if(username==null) {
			throw new NullPointerException(
					"username = null.");
		}
		if(user.getId()!=null) {
			throw new IllegalArgumentException(
					"Argument `user` should not have set the id.");
		}
		UserEntity userEntity = (UserEntity) user;
		userEntity.setId(getByUsername(username).getId());
		return saveOrUpdate((UserEntity)user, null);
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true)
	public List getRolesList() {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Loading all roles.");
    	}
		return roleDao.getList();
	}

    @Transactional(readOnly = true)
    public Role getRole(String roleName) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting role with name=" + roleName);
    	}
    	if(roleName==null) {
			throw new NullPointerException(
					"roleName = null.");
    	}
    	Role role = roleDao.getByCode(roleName);
    	if(role==null) {
    		throw new InvalidRoleException("role=" + roleName + " not exist");
    	}
    	return role;
    }

	private void updatePassword(UserEntity user, String newPassword) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating password of user with id=" + user.getId());
    	}
		try {
			user.setPassword(encodePassword(newPassword));
		} catch (EncodePasswordException e) {
			logger.error("An error occurred when encoding the password of the user= " + user.getUsername() + ".", e);
			throw e;
		}
		userDao.update(user);
	}

	@Transactional(readOnly = false)
	public void updateRoles(String username, List<Role> newRoles) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating roles of user with username=" + username);
    	}
		UserEntity user = (UserEntity) getByUsername(username, true);
		updateRoles(user, newRoles);
	}

	@Transactional(readOnly = false)
	public void updateRoles(Serializable id, List<Role> newRoles) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating roles of user with id=" + id);
    	}
		UserEntity user = (UserEntity) getById(id, true);
		updateRoles(user, newRoles);
	}

	private void updateRoles(UserEntity user, List<Role> newRoles) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating roles of user with id=" + user.getId());
    	}
		// Borramos los roles anteriores contenidos en
		// la nueva lista
		List<Role> currentSavedRoles = new ArrayList<Role>(newRoles.size());
		if(user.getAuthorities()!=null) {
			for(Authority au : user.getAuthorities()) {
				if(!newRoles.contains(au.getRole())) {
					authorithyDao.delete(au);
				} else {
					currentSavedRoles.add(au.getRole());
				}
			}
		}

		// Cargamos los nuevos
		for(Role role : newRoles) {
			if(!currentSavedRoles.contains(role)) {
				Authority au = new Authority();
				au.setRole((RoleEntity)role);
				au.setUser(user);
				authorithyDao.save(au);
			}
		}
	}

    @Transactional(readOnly = false)
	public void updatePassword(String username, String newPassword) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating password of user with username=" + username);
    	}
    	if(!isValidPassword(newPassword)) {
    		throw new InvalidPasswordException();
    	}
		UserEntity user = (UserEntity) getByUsername(username, true);
		updatePassword(user, newPassword);
	}

    @Transactional(readOnly = false)
	public void updatePassword(Serializable id, String newPassword) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating password of user with id=" + id);
    	}
    	if(!isValidPassword(newPassword)) {
    		throw new InvalidPasswordException();
    	}
		UserEntity user = (UserEntity) getById(id, true);
		updatePassword(user, newPassword);
	}

    @Transactional(readOnly = true)
	public User getById(Serializable id) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting user with id=" + id);
    	}
		return getById(id, false);
	}

    @Transactional(readOnly = true)
	protected User getById(Serializable id, boolean toEdit) {
    	if(id==null) {
    		throw new NullPointerException("id = null.");
    	}
		User user = userDao.get((Long)id);
		if(user==null) {
			throw new UserNotExistException(
					"User with id=" + id + " not exist.");
		}
		if(toEdit && user.isLocked()) {
			throw new UserLockedException(user);
		}
		return user;
	}

    @Transactional(readOnly = false)
	public void deleteById(Serializable id) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Deleting user with id=" + id);
    	}
    	UserEntity u = (UserEntity) getById(id, true);
    	if(organizationService!=null) {
    		organizationService.removeUserFromAll(u.getId());
    	}
		userDataDao.delete(u.getUserData());
		authorithyDao.deleteList(u.getAuthorities());
		userDao.delete(u);
	}

	public String encodePassword(String plainPassword) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Encoding password.");
    	}
		try {
			return passwordEncoder.encode(plainPassword);
		} catch(Exception e) {
			logger.error("An error occurred when encoding the password.", e);
			throw new EncodePasswordException(e);
		}
	}

	public boolean isValidUsername(String username) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Validating username=" + username);
    	}
		if(username==null) {
			throw new NullPointerException("username = null.");
		}
		return usernameValidator.isValid(username);
	}

	public boolean isValidPassword(String password) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Validating password.");
    	}
		return passwordValidator.isValid(password);
	}

	public String generateRandomPassword() {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Generating random password.");
    	}
		return passwordGenerator.nextString();
	}

	public boolean isValidEmailAddress(String emailAddress) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Validating emailAddress=" + emailAddress);
    	}
		if(emailAddress==null) {
			throw new NullPointerException("emailAddress = null.");
		}
		return emailAddressValidator.isValid(emailAddress);
	}

	public boolean hasRole(User user, Role role) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Validating roles of user with id=" + user.getId());
    	}
		return ((UserEntity)user).hasRole(role);
	}

	@Transactional(readOnly = true)
	public boolean hasRole(Serializable userId, String roleName) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Validating roles of user with id=" + userId);
    	}
		Role role = getRole(roleName);
		List <Authority> authorities = userDao.getAuthorityList((Long)getById(userId).getId());
		for(Authority authority : authorities) {
			if(authority.getRole().equals(role)) {
				return true;
			}
		}
		return false;
	}

	@Transactional(readOnly = true)
	public boolean hasRole(String username, String roleName) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Validating roles of user with username=" + username);
    	}
		List <Authority> authorities = userDao.getAuthorityList((Long)getByUsername(username).getId());
		for(Authority authority : authorities) {
			if(authority.getRole().getAuthority().equals(roleName)) {
				return true;
			}
		}
		return false;
	}

	@Transactional(readOnly = false)
	public void updateCommonName(String username, String newCommonName) {
		UserEntity user = (UserEntity) getByUsername(username, true);
		user.getUserData().setCommonName(newCommonName);
		userDataDao.update(user.getUserData());
	}

	@Transactional(readOnly = false)
	public void updateCommonName(Serializable userId, String newCommonName) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating commonName of user with id=" + userId);
    	}
		UserEntity user = (UserEntity) getById(userId, true);
		user.getUserData().setCommonName(newCommonName);
		userDataDao.update(user.getUserData());
	}

	@Transactional(readOnly = false)
	public void updateLock(Serializable userId, boolean lock) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating lock information of user with id=" + userId);
    	}
		UserEntity user = (UserEntity) getById(userId);
		user.getUserData().setLocked(lock);
		userDataDao.update(user.getUserData());
	}

	@Transactional(readOnly = false)
	public void updateLock(String username, boolean lock) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating lock information of user with username=" + username);
    	}
		UserEntity user = (UserEntity) getByUsername(username);
		user.getUserData().setLocked(lock);
		userDataDao.update(user.getUserData());
	}

	@Transactional(readOnly = false)
	public void updateTimeZoneId(String username, String newTimeZoneId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating timeZone of user with username=" + username);
    	}
		UserEntity user = (UserEntity) getByUsername(username, true);
		user.getUserData().setTimeZoneId(newTimeZoneId);
		userDataDao.update(user.getUserData());
	}

	@Transactional(readOnly = false)
	public void updatePortraitId(String username, Serializable newPortraitId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating portraitId of user with username=" + username);
    	}
		UserEntity user = (UserEntity) getByUsername(username, true);
		user.getUserData().setPortraitId((Long)newPortraitId);
		userDataDao.update(user.getUserData());
	}

	@Transactional(readOnly = false)
	public void updatePortraitId(Serializable userId, Serializable newPortraitId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating portraitId of user with id=" + userId);
    	}
		UserEntity user = (UserEntity) getById(userId, true);
		user.getUserData().setPortraitId((Long)newPortraitId);
		userDataDao.update(user.getUserData());
	}

	@Transactional(readOnly = false)
	public void updateTimeZoneId(Serializable userId, String newTimeZoneId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating timeZone of user with id=" + userId);
    	}
		UserEntity user = (UserEntity) getById(userId);
		user.getUserData().setTimeZoneId(newTimeZoneId);
		userDataDao.update(user.getUserData());
	}

	@Transactional(readOnly = false)
	public void updateCountryId(String username, String newCountryId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating countryId of user with username=" + username);
    	}
		UserEntity user = (UserEntity) getByUsername(username, true);
		user.getUserData().setCountryId(newCountryId);
		userDataDao.update(user.getUserData());
	}

	@Transactional(readOnly = false)
	public void updateCountryId(Serializable userId, String newCountryId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating countryId of user with id=" + userId);
    	}
		UserEntity user = (UserEntity) getById(userId, true);
		user.getUserData().setCountryId(newCountryId);
		userDataDao.update(user.getUserData());
	}

	@Transactional(readOnly = false)
	public void updateCityOrRegionName(String username, String newCityOrRegionName) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating cityOrRegionName of user with username=" + username);
    	}
		UserEntity user = (UserEntity) getByUsername(username, true);
		user.getUserData().setCityOrRegionName(newCityOrRegionName);
		userDataDao.update(user.getUserData());
	}

	@Transactional(readOnly = false)
	public void updateCityOrRegionName(Serializable userId, String newCityOrRegionName) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating cityOrRegionName of user with id=" + userId);
    	}
		UserEntity user = (UserEntity) getById(userId, true);
		user.getUserData().setCityOrRegionName(newCityOrRegionName);
		userDataDao.update(user.getUserData());
	}

	@Transactional(readOnly = false)
	public void updateBirthdayDate(String username, Date newBirthdayDate) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating birthdayDate of user with username=" + username);
    	}
		UserEntity user = (UserEntity) getByUsername(username, true);
		user.getUserData().setBirthdayDate(newBirthdayDate);
		userDataDao.update(user.getUserData());
	}

	@Transactional(readOnly = false)
	public void updateBirthdayDate(Serializable userId, Date newBirthdayDate) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating birthdayDate of user with id=" + userId);
    	}
		UserEntity user = (UserEntity) getById(userId, true);
		user.getUserData().setBirthdayDate(newBirthdayDate);
		userDataDao.update(user.getUserData());
	}

	@Transactional(readOnly = false)
	public void updateEmailAddress(String username, String newEmailAddress) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating emailAddress of user with username=" + username);
    	}
		UserEntity user = (UserEntity) getByUsername(username, true);
		UserEntity user2 = userDao.getByEmailAddress(newEmailAddress);
		if(user2!=null && !user2.getId().equals(user.getId())) {
			throw new DuplicatedUserException(
					"A user with email address=" + newEmailAddress + " exist.");
		}
		user.setEmailAddress(newEmailAddress);
		userDao.update(user);
	}

	@Transactional(readOnly = false)
	public void updateEmailAddress(Serializable id, String newEmailAddress) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating emailAddress of user with id=" + id);
    	}
		UserEntity user = (UserEntity) getById(id, true);
		UserEntity user2 = userDao.getByEmailAddress(newEmailAddress);
		if(user2!=null && !user2.getId().equals(user.getId())) {
			throw new DuplicatedUserException(
					"A user with email address=" + newEmailAddress + " exist.");
		}
		user.setEmailAddress(newEmailAddress);
		userDao.update(user);
	}

	@Transactional(readOnly = false)
	public void updateUsername(String username, String newUsername) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating username of user with username=" + username);
    	}
		if(!isValidUsername(newUsername)) {
			throw new InvalidUsernameException();
		}
		UserEntity user = (UserEntity) getByUsername(username, true);
		UserEntity user2 = userDao.getByUsername(newUsername);
		if(user2!=null && !user2.getId().equals(user.getId())) {
			throw new DuplicatedUserException(
					"A user with username=" + newUsername + " exist.");
		}
		user.setUsername(newUsername);
		userDao.update(user);
	}

	@Transactional(readOnly = false)
	public void updateUsername(Serializable id, String newUsername) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Updating username of user with id=" + id);
    	}
		if(!isValidUsername(newUsername)) {
			throw new InvalidUsernameException();
		}
		UserEntity user = (UserEntity) getById(id, true);
		UserEntity user2 = userDao.getByUsername(newUsername);
		if(user2!=null && !user2.getId().equals(user.getId())) {
			throw new DuplicatedUserException(
					"A user with username=" + newUsername + " exist.");
		}
		user.setUsername(newUsername);
		userDao.update(user);
	}

	@Transactional(readOnly = true)
	public boolean checkPassword(String username, String password) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Verifying password of user with username=" + username);
    	}
		UserEntity user = (UserEntity) getByUsername(username);
		return user.getPassword().equals(encodePassword(password));
	}

	@Transactional(readOnly = true)
	public boolean checkPassword(Serializable id, String password) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Verifying password of user with id=" + id);
    	}
		UserEntity user = (UserEntity) getById(id);
		return user.getPassword().equals(encodePassword(password));
	}

	/**
	 * Crea una instancia de
	 * {@link mr.common.security.userentity.model.UserEntity UserEntity}
	 * que implementa {@link mr.common.security.model.User User}. También
	 * se crea y linkea una instancia de
	 * {@link mr.common.security.userentity.model.UserData UserData}
	 * (<code>user.userData</code>).<br/>
	 * Tener en cuenta que el objeto no representa un usuario
	 * válido del sistema, como especifica
	 * {@link mr.common.security.service.UserService#getUserInstance()}
	 */
	public User getUserInstance() {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Creating a new user object instance, class="
    				+ UserEntity.class.getName());
    	}
		UserEntity user = new UserEntity();
		UserData userData = new UserData();
		user.setUserData(userData);
		return user;
	}

	@Transactional(readOnly = true)
	public String getUsernameById(Serializable userId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting username of user with id=" + userId);
    	}
    	if(userId==null) {
    		throw new NullPointerException("userId = null.");
    	}
		String username = userDao.getUsernameById((Long)userId);
		if(username==null) {
			throw new UserNotExistException(
				"User with id=" + userId.toString() + " not exist.");
		}
		return username;
	}

	@Transactional(readOnly = true)
	public Serializable getIdByUsername(String username) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting id of user with username=" + username);
    	}
    	if(username==null) {
    		throw new NullPointerException("username = null.");
    	}
    	Serializable userId = userDao.getIdByUsername(username);
		if(userId==null) {
			throw new UserNotExistException(
					"User with username='" + username + "' not exist.");
		}
		return userId;
	}

	@Transactional(readOnly = true)
	public Serializable getIdByEmailAddress(String emailAddress) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting id of user with emailAddress=" + emailAddress);
    	}
    	if(emailAddress==null) {
    		throw new NullPointerException("emailAddress = null.");
    	}
    	Serializable userId = userDao.getIdByEmailAddress(emailAddress);
		if(userId==null) {
			throw new UserNotExistException(
					"User with emailAddress='" + emailAddress + "' not exist.");
		}
		return userId;
	}

	@Transactional(readOnly = true)
	public List<Role> getUserRoles(Serializable userId) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting roles of user with id=" + userId);
    	}
		return getById(userId, false).getRoles();
	}

	@Transactional(readOnly = true)
	public List<Role> getUserRoles(String username) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Getting roles of user with username=" + username);
    	}
		return getByUsername(username, false).getRoles();
	}


	/*!** Getters & setters  **!*/

	public Validator getUsernameValidator() {
		return usernameValidator;
	}
	public void setUsernameValidator(Validator usernameValidator) {
		this.usernameValidator = usernameValidator;
	}
	public Validator getPasswordValidator() {
		return passwordValidator;
	}
	public void setPasswordValidator(Validator passwordValidator) {
		this.passwordValidator = passwordValidator;
	}
	public Validator getEmailAddressValidator() {
		return emailAddressValidator;
	}
	public void setEmailAddressValidator(Validator emailAddressValidator) {
		this.emailAddressValidator = emailAddressValidator;
	}
	public Encoder getPasswordEncoder() {
		return passwordEncoder;
	}
	public void setPasswordEncoder(Encoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	public UserEntityDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserEntityDao userDao) {
		this.userDao = userDao;
	}
	public UserDataDao getUserDataDao() {
		return userDataDao;
	}
	public void setUserDataDao(UserDataDao userDataDao) {
		this.userDataDao = userDataDao;
	}
	public AuthorityDao getAuthorithyDao() {
		return authorithyDao;
	}
	public void setAuthorithyDao(AuthorityDao authorithyDao) {
		this.authorithyDao = authorithyDao;
	}
	public RoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	public UserSecurityService getUserSecurityService() {
		return userSecurityService;
	}
	public void setUserSecurityService(UserSecurityService userSecurityService) {
		this.userSecurityService = userSecurityService;
	}
	public RandomString getPasswordGenerator() {
		return passwordGenerator;
	}
	public void setPasswordGenerator(RandomString passwordGenerator) {
		this.passwordGenerator = passwordGenerator;
	}
}
