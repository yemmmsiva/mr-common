package mr.common.security.userentity.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mr.common.format.validator.ValidatorUtils;
import mr.common.security.EncodeUtils;
import mr.common.security.exception.DuplicatedEmailAddressException;
import mr.common.security.exception.DuplicatedUserException;
import mr.common.security.exception.EncodePasswordException;
import mr.common.security.exception.InvalidEmailAddressException;
import mr.common.security.exception.InvalidPasswordException;
import mr.common.security.exception.InvalidUsernameException;
import mr.common.security.exception.UserNotExistException;
import mr.common.security.model.Role;
import mr.common.security.model.User;
import mr.common.security.model.form.BasicUserForm;
import mr.common.security.model.form.FindUserForm;
import mr.common.security.model.form.UserForm;
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


	/**
	 * {@inheritDoc}
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public List getList() {
    	return userDao.getList();
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
    @Transactional
	public User getCurrentUser() {
		String user = userSecurityService.getCurrentUsername();
		if(user != null) {
			return getByUsername(user);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
    @Transactional
	public String getCurrentUsername() {
		return userSecurityService.getCurrentUsername();
	}

	/**
	 * {@inheritDoc}
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public List find(FindUserForm form) {
		return userDao.find(form);
	}

	/**
	 * {@inheritDoc}
	 */
    @Transactional(readOnly = false)
	public void deleteByUsername(String username) {
    	UserEntity u = (UserEntity) getByUsername(username);
		userDataDao.delete(u.getUserData());
		authorithyDao.deleteList(u.getAuthorities());
		userDao.delete(u);
	}

    @Transactional(readOnly = false)
    public void update(BasicUserForm form) {
    	
    }

	/**
	 * {@inheritDoc}
	 * @throws Exception 
	 */
    @Transactional(readOnly = false)
	public void saveOrUpdate(UserForm form) {
		UserEntity user;
		UserData userData;

		// Recuperamos el usuario o creamos el nuevo
		boolean userExist = form.getId()!=null;
		if(userExist) {
			user = (UserEntity) getById(form.getId());
			userData = user.getUserData();
		} else {
			user = new UserEntity();
			userData = new UserData();
		}

		// Validamos los campos
		if(!isValidUsername(form.getUsername())) {
			throw new InvalidUsernameException(form.getUsername());
		}
		if(StringUtils.hasText(form.getPassword())) {
			if(!isValidPassword(form.getPassword())) {
				throw new InvalidPasswordException();
			}
		}
		if(!isValidEmailAddress(form.getMail())) {
			throw new InvalidEmailAddressException();
		}
		
		UserEntity duplicado = userDao.getByUsername(form.getUsername());
		if(duplicado!=null && (user.getId()==null
				|| (user.getId()!=null && user.getId().longValue()!=duplicado.getId().longValue()))) {
			throw new DuplicatedUserException();
		}
		duplicado = userDao.getByEmailAddress(form.getMail());
		if(duplicado!=null && (user.getId()==null
				|| (user.getId()!=null && user.getId().longValue()!=duplicado.getId().longValue()))) {
			throw new DuplicatedEmailAddressException();
		}
		userData.setCommonName(form.getCommonName());
		userData.setSurname(form.getSurname());
		user.setUsername(form.getUsername());
		user.setEmailAddress(form.getMail());
		user.setEnabled(form.getEnabled());
		if(StringUtils.hasText(form.getPassword())) {
			try {
				user.setPassword(encodePassword(form.getPassword()));
			} catch (EncodePasswordException e) {
				logger.error("An error occurred when encoding the password of the user=" + user.getUsername(), e);
				throw e;
			}
		}

		// Guardamos o actualizamos el usuario
		user.setUserData(userData);
		userData.setUser(user);
		userDataDao.saveOrUpdate(userData);
		userDao.saveOrUpdate(user);

		// Guardamos los roles
		if(user.getAuthorities()!=null) {
			for(Authority au : user.getAuthorities()) {
				authorithyDao.delete(au);
			}
		}
		List<RoleEntity> roles = new ArrayList<RoleEntity>();
		for(String role : form.getRolesAsList()) {
			roles.add(roleDao.getByCode(role));
		}
		for(RoleEntity role : roles) {
			Authority au = new Authority();
			au.setRole(role);
			au.setUser(user);
			authorithyDao.save(au);
		}
	}

	/**
	 * {@inheritDoc}
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public List getRolesList() {
		return roleDao.getList();
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

    /**
     * {@inheritDoc}
     */
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
		// Se valida la password de la misma forma que el nombre de usuario
		return ValidatorUtils.isValidUsername(password);
	}

	public boolean isValidEmailAddress(String emailAddress) {
		if(emailAddress==null) {
			throw new NullPointerException();
		}
		return emailValidator.isValid(emailAddress);
	}

	public boolean hasRole(User user, Role role) {
		return ((UserEntity)user).hasRole(role);
	}

	public boolean hasRole(Serializable userId, String roleName) {
		List <Authority> authorities = userDao.getAuthorityList((Long)getById(userId).getId());
		for(Authority authority : authorities) {
			if(authority.getRole().getAuthority().equals(roleName)) {
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
}
