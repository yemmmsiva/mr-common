package mr.common.security.userentity.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import mr.common.format.validator.ValidatorUtils;
import mr.common.model.ConfigurableData;
import mr.common.security.EncodeUtils;
import mr.common.security.exception.DuplicatedEmailAddressException;
import mr.common.security.exception.DuplicatedUserException;
import mr.common.security.exception.EncodePasswordException;
import mr.common.security.exception.InvalidEmailAddressException;
import mr.common.security.exception.InvalidPasswordException;
import mr.common.security.exception.InvalidRoleException;
import mr.common.security.exception.InvalidUsernameException;
import mr.common.security.exception.UserNotExistException;
import mr.common.security.model.Role;
import mr.common.security.model.User;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.EmailValidator;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


/**
 * Implementación de {@link mr.common.security.service.UserService UserService},
 * que maneja usuarios {@link mr.common.security.userentity.model.UserEntity UserEntity}.
 * @author Mariano Ruiz
 */
@Service
@Repository
public class UserEntityService implements UserService {

	//private final Log logger = LogFactory.getLog(getClass());
	private static final Log logger = LogFactory.getLog(UserEntityService.class);

	private EmailValidator emailValidator = EmailValidator.getInstance();

	@Resource
	private UserEntityDao userDao;
	@Resource
	private UserDataDao userDataDao;
	@Resource
	private AuthorityDao authorithyDao;
	@Resource
	private RoleDao roleDao;

	@Resource
	private UserSecurityService userSecurityService;


    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public List getList() {
    	return userDao.getList();
	}

    @Transactional
	public User getByUsername(String username) {
    	if(username==null) {
    		throw new NullPointerException("username = null");
    	}
		User user = userDao.getByUsername(username);
		if(user==null) {
			throw new UserNotExistException(
					"User with username='" + username + "' not exist");
		}
		return user;
	}

    @Transactional
	public User getByEmailAddress(String emailAddress) {
    	if(emailAddress==null) {
    		throw new NullPointerException("emailAddress = null");
    	}
		User user = userDao.getByEmailAddress(emailAddress);
		if(user==null) {
			throw new UserNotExistException(
					"User with emailAddress='" + emailAddress + "' not exist");
		}
		return user;
	}

    @Transactional
	public User getCurrentUser() {
		String user = userSecurityService.getCurrentUsername();
		if(user != null) {
			return getByUsername(user);
		}
		return null;
	}

    @Transactional
	public String getCurrentUsername() {
		return userSecurityService.getCurrentUsername();
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public List find(User user, Boolean activeFilter, ConfigurableData page) {
		return userDao.find(user, activeFilter, page);
	}

    @Transactional
	public int findCount(User user, Boolean activeFilter) {
		return userDao.findCount(user, activeFilter);
	}

    @Transactional(readOnly = false)
	public void deleteByUsername(String username) {
    	UserEntity u = (UserEntity) getByUsername(username);
		userDataDao.delete(u.getUserData());
		authorithyDao.deleteList(u.getAuthorities());
		userDao.delete(u);
	}

	private UserEntity saveOrUpdate(UserEntity user) {
		UserEntity userEntity;
		UserData userData;

		// Recuperamos el usuario o creamos el nuevo
		boolean userExist = user.getId()!=null;
		if(userExist) {
			userEntity = (UserEntity) getById(user.getId());
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
		userEntity.setUsername(user.getUsername());
		userEntity.setEmailAddress(user.getEmailAddress());
		userEntity.setEnabled(user.isEnabled());
		if(user.getPassword()!=null) {
			try {
				userEntity.setPassword(encodePassword(user.getPassword()));
			} catch (EncodePasswordException e) {
				logger.error("An error occurred when encoding the password of the user=" + userEntity.getUsername(), e);
				throw e;
			}
		}

		// Guardamos o actualizamos el usuario
		userEntity.setUserData(userData);
		userData.setUser(userEntity);
		userDataDao.saveOrUpdate(userData);
		userDao.saveOrUpdate(userEntity);

		// Guardamos los roles
		if(userEntity.getAuthorities()!=null) {
			for(Authority au : userEntity.getAuthorities()) {
				authorithyDao.delete(au);
			}
		}
		for(Role role : user.getRoles()) {
			Authority au = new Authority();
			au.setRole((RoleEntity)role);
			au.setUser(userEntity);
			authorithyDao.save(au);
		}

		return userEntity;
	}

	@Transactional(readOnly = false)
	public User newUser(User user) {
		if(user.getId()!=null) {
			throw new IllegalArgumentException(
					"New user should not have set the id.");
		}
		return saveOrUpdate((UserEntity)user);
	}

	@Transactional(readOnly = false)
	public User updateUser(Serializable id, User user) {
		if(user.getId()!=null) {
			throw new IllegalArgumentException(
					"Argument `user` should not have set the id.");
		}
		UserEntity userEntity = (UserEntity) user;
		userEntity.setId((Long)id);
		return saveOrUpdate((UserEntity)user);
	}

	@Transactional(readOnly = false)
	public User updateUser(String username, User user) {
		if(user.getId()!=null) {
			throw new IllegalArgumentException(
					"Argument `user` should not have set the id.");
		}
		UserEntity userEntity = (UserEntity) user;
		userEntity.setId((Long)getByUsername(username).getId());
		return saveOrUpdate((UserEntity)user);
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public List getRolesList() {
		return roleDao.getList();
	}

    @Transactional
    public Role getRole(String roleName) {
    	Role role = roleDao.getByCode(roleName);
    	if(role==null) {
    		throw new InvalidRoleException("role='" + roleName + "' not exist");
    	}
    	return role;
    }

	private void updatePassword(UserEntity user, String newPassword) {
		try {
			user.setPassword(encodePassword(newPassword));
		} catch (EncodePasswordException e) {
			logger.error("An error occurred when encoding the password of the user= '" + user.getUsername() + "'.", e);
			throw e;
		}
		userDao.update(user);
	}

    @Transactional(readOnly = false)
	public void updatePassword(String username, String newPassword) {
    	if(!isValidPassword(newPassword)) {
    		throw new InvalidPasswordException();
    	}
		UserEntity user = (UserEntity) getByUsername(username);
		updatePassword(user, newPassword);
	}

    @Transactional(readOnly = false)
	public void updatePassword(Serializable id, String newPassword) {
    	if(!isValidPassword(newPassword)) {
    		throw new InvalidPasswordException();
    	}
		UserEntity user = (UserEntity) getById(id);
		updatePassword(user, newPassword);
	}

    @Transactional
	public User getById(Serializable id) {
    	if(id==null) {
    		throw new NullPointerException("id = null");
    	}
		User user = userDao.get((Long)id);
		if(user==null) {
			throw new UserNotExistException(
					"User with id=" + id + " not exist");
		}
		return user;
	}

    @Transactional(readOnly = false)
	public void deleteById(Serializable id) {
    	UserEntity u = (UserEntity) getById(id);
		userDataDao.delete(u.getUserData());
		authorithyDao.deleteList(u.getAuthorities());
		userDao.delete(u);
	}

	public String encodePassword(String plainPassword) {
		try {
			return EncodeUtils.md5(plainPassword);
		} catch(Exception e) {
			logger.error("An error occurred when encoding the password", e);
			throw new EncodePasswordException(e);
		}
	}

	public boolean isValidUsername(String username) {
		return ValidatorUtils.isValidUsername(username);
	}

	public boolean isValidPassword(String password) {
		if(!StringUtils.hasText(password)) {
			return false;
		}
		// Se valida la password de la misma forma que el nombre de usuario
		return ValidatorUtils.isValidUsername(password);
	}

	public boolean isValidEmailAddress(String emailAddress) {
		if(emailAddress==null) {
			throw new NullPointerException("Email address can't be null.");
		}
		return emailValidator.isValid(emailAddress);
	}

	public boolean hasRole(User user, Role role) {
		return ((UserEntity)user).hasRole(role);
	}

	public boolean hasRole(Serializable userId, String roleName) {
		Role role = getRole(roleName);
		List <Authority> authorities = userDao.getAuthorityList((Long)getById(userId).getId());
		for(Authority authority : authorities) {
			if(authority.getRole().equals(role)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasRole(String username, String roleName) {
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
		UserEntity user = (UserEntity) getByUsername(username);
		user.getUserData().setCommonName(newCommonName);
		userDataDao.update(user.getUserData());
	}

	@Transactional(readOnly = false)
	public void updateCommonName(Serializable id, String newCommonName) {
		UserEntity user = (UserEntity) getById(id);
		user.getUserData().setCommonName(newCommonName);
		userDataDao.update(user.getUserData());
	}

	@Transactional(readOnly = false)
	public void updateEmailAddress(String username, String newEmailAddress) {
		UserEntity user = (UserEntity) getByUsername(username);
		UserEntity user2 = userDao.getByEmailAddress(newEmailAddress);
		if(user2!=null && !user2.getId().equals(user.getId())) {
			throw new DuplicatedUserException(
					"A user with email address='" + newEmailAddress + "' exist.");
		}
		user.setEmailAddress(newEmailAddress);
		userDao.update(user);
	}

	@Transactional(readOnly = false)
	public void updateEmailAddress(Serializable id, String newEmailAddress) {
		UserEntity user = (UserEntity) getById(id);
		UserEntity user2 = userDao.getByEmailAddress(newEmailAddress);
		if(user2!=null && !user2.getId().equals(user.getId())) {
			throw new DuplicatedUserException(
					"A user with email address='" + newEmailAddress + "' exist.");
		}
		user.setEmailAddress(newEmailAddress);
		userDao.update(user);
	}

	@Transactional(readOnly = false)
	public void updateUsername(String username, String newUsername) {
		if(!isValidUsername(newUsername)) {
			throw new InvalidUsernameException();
		}
		UserEntity user = (UserEntity) getByUsername(username);
		UserEntity user2 = userDao.getByUsername(newUsername);
		if(user2!=null && !user2.getId().equals(user.getId())) {
			throw new DuplicatedUserException(
					"A user with username='" + newUsername + "' exist.");
		}
		user.setUsername(newUsername);
		userDao.update(user);
	}

	@Transactional(readOnly = false)
	public void updateUsername(Serializable id, String newUsername) {
		if(!isValidUsername(newUsername)) {
			throw new InvalidUsernameException();
		}
		UserEntity user = (UserEntity) getById(id);
		UserEntity user2 = userDao.getByUsername(newUsername);
		if(user2!=null && !user2.getId().equals(user.getId())) {
			throw new DuplicatedUserException(
					"A user with username='" + newUsername + "' exist.");
		}
		user.setUsername(newUsername);
		userDao.update(user);
	}

	@Transactional
	public boolean checkPassword(String username, String password) {
		UserEntity user = (UserEntity) getByUsername(username);
		return user.getPassword().equals(encodePassword(password));
	}

	@Transactional
	public boolean checkPassword(Serializable id, String password) {
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
		UserEntity user = new UserEntity();
		UserData userData = new UserData();
		user.setUserData(userData);
		return user;
	}
}
