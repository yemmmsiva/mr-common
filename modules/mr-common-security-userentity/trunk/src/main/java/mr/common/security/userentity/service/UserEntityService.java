package mr.common.security.userentity.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import mr.common.business.exception.spring.BusinessException;
import mr.common.format.validator.ValidatorUtils;
import mr.common.security.EncodeUtils;
import mr.common.security.model.Role;
import mr.common.security.model.User;
import mr.common.security.model.form.FindUserForm;
import mr.common.security.model.form.UserForm;
import mr.common.security.service.UserSecurityService;
import mr.common.security.service.UserService;
import mr.common.security.userentity.dao.AuthorityDao;
import mr.common.security.userentity.dao.RoleDao;
import mr.common.security.userentity.dao.UserDao;
import mr.common.security.userentity.dao.UserDataDao;
import mr.common.security.userentity.model.Authority;
import mr.common.security.userentity.model.RoleEntity;
import mr.common.security.userentity.model.UserData;
import mr.common.security.userentity.model.UserEntity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

	@Resource
	private UserDao userDao;
	@Resource
	private UserDataDao userDataDao;
	@Resource
	private AuthorityDao authorithyDao;
	@Resource
	private RoleDao roleDao;

	@Resource
	private UserSecurityService userSecurityService;

	@Autowired(required=false)
	private Properties appProperties;

	@Autowired(required=false)
	private JavaMailSender mailSender;
	
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
		return userDao.getByUsername(username);
	}

	/**
	 * {@inheritDoc}
	 */
    @Transactional
	public User getByEmailAddress(String emailAddress) {
		return userDao.getByEmailAddress(emailAddress);
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
    	UserEntity u = (UserEntity) userDao.getByUsername(username);
		userDataDao.delete(u.getPerson());
		authorithyDao.deleteList(u.getAuthorities());
		userDao.delete(u);
	}

	/**
	 * {@inheritDoc}
	 */
    @Transactional
	public User get(Serializable id) {
		return userDao.get((Long)id);
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
			user = userDao.get(form.getId());
			userData = user.getPerson();
		} else {
			user = new UserEntity();
			userData = new UserData();
		}

		// Validamos los campos
		if(!isValidUsername(form.getUsername())) {
			throw new BusinessException("user.edit.error.invalidUserName");
		}
		if(StringUtils.hasText(form.getPassword())) {
			if(!isValidPassword(form.getPassword())) {
				throw new BusinessException("user.edit.error.invalidPassword");
			}
		}
		if(!EmailValidator.getInstance().isValid(form.getMail())) {
			throw new BusinessException("user.edit.error.invalidMail");
		}

		// Si el usuario existía verificamos que cambio
		boolean usernameChange = false;
		boolean emailAddressChange = false;
		boolean passwordChange = false;
		if(userExist) {
			if(!user.getUsername().equals(form.getUsername())) {
				usernameChange = true;
			}
			if(!user.getEmailAddress().equals(form.getMail())) {
				emailAddressChange = true;
			}
			if(StringUtils.hasText(form.getPassword())) {
				passwordChange = true;
			}
		}
		
		UserEntity duplicado = userDao.getByUsername(form.getUsername());
		if(duplicado!=null && (user.getId()==null
				|| (user.getId()!=null && user.getId().longValue()!=duplicado.getId().longValue()))) {
			throw new BusinessException("user.edit.error.duplicatedName");
		}
		duplicado = userDao.getByEmailAddress(form.getMail());
		if(duplicado!=null && (user.getId()==null
				|| (user.getId()!=null && user.getId().longValue()!=duplicado.getId().longValue()))) {
			throw new BusinessException("user.edit.error.duplicatedEmailAddress");
		}
		userData.setCommonName(form.getCommonName());
		userData.setSurname(form.getSurname());
		user.setUsername(form.getUsername());
		user.setEmailAddress(form.getMail());
		user.setEnabled(form.getEnabled());
		if(StringUtils.hasText(form.getPassword())) {
			try {
				user.setPassword(encodePassword(form.getPassword()));
			} catch (Exception e) {
				logger.error("Error al codificar la password del usuario '" + form.getUsername() + "'.", e);
				throw new BusinessException("user.edit.error.encode");
			}
		}

		// Guardamos o actualizamos el usuario
		user.setPerson(userData);
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
		// Enviamos un mail si el usuario es nuevo o si alguno
		// de los campos siguientes cambió
		if(!userExist || usernameChange || passwordChange || emailAddressChange) {
			sendMail(user, form.getPassword(), userExist);
		}
	}

    /**
     * Envía un mail al usuario nuevo o actualizado con sus credenciales.<br/>
     * Se envía solo si están configuradas ciertas propiedad y objectos, ver código.<br/>
     * En caso de producirse errores se loguea el mismo en los logs, pero no se relanza
     * la exception a quien llama.
     */
    private void sendMail(User user, String password, boolean userExist) {
		if(mailSender!=null && appProperties!=null
				&& appProperties.getProperty("user.sendMail").equals("true")) {
			try {
				SimpleMailMessage message = new SimpleMailMessage();
				message.setSentDate(new Date());
				message.setTo(user.getEmailAddress());
				String cc = (String)appProperties.getProperty("mail.cc.to", "");
				if(StringUtils.hasText(cc)) {
					message.setCc((cc).split("; "));
				}
				String from = getCurrentUser().getEmailAddress();
				if(from==null) {
					from = appProperties.getProperty("mail.cc.from");
				}
				message.setFrom(from);
				message.setSubject((String)appProperties.getProperty("newuser.email.subject"));
				String start;
				if(userExist) {
					start = (String)appProperties.getProperty("updateduser.email.message");
				} else {
					start = (String)appProperties.getProperty("newuser.email.message");
				}
				message.setText(parseBodyMessageMail(start, user.getUsername(), user.getEmailAddress(), password));
				mailSender.send(message);
			} catch(Exception e) {
				logger.error("Error al enviar mail de notificacion al nuevo usuario '" + user.getUsername() + "'.", e);
			}
		}
    }

    private String parseBodyMessageMail(String start, String username, String emailAddress, String password) {
    	String bodyMessage = start + "\n\n"
        + "Username: " +username + "\n"
        + "Email: " + emailAddress + "\n"
        + "Password: " + password + "\n\n"
        + "Ingresa desde: " + (String)appProperties.getProperty("newuser.email.url");
    	return bodyMessage;
    }

	/**
	 * {@inheritDoc}
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public List getRolesList() {
		return roleDao.getList();
	}

	private void changePassword(UserEntity user, String newPassword) {
		if(!isValidPassword(newPassword)) {
			throw new BusinessException("user.edit.error.invalidPassword");
		}
		try {
			user.setPassword(encodePassword(newPassword));
		} catch (Exception e) {
			logger.error("Error al codificar la password del usuario '" + user.getUsername() + "'.", e);
			throw new BusinessException("user.edit.error.encode");
		}
		userDao.update(user);
		sendMail(user, newPassword, true);
	}

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = false)
	public void changePassword(String username, String newPassword) {
		UserEntity user = userDao.getByUsername(username);
		changePassword(user, newPassword);
	}

    @Transactional(readOnly = false)
	public void changePassword(Serializable id, String newPassword) {
		UserEntity user = userDao.get((Long)id);
		changePassword(user, newPassword);
	}

    @Transactional
	public User getById(Serializable id) {
		return userDao.get((Long)id);
	}

    @Transactional(readOnly = false)
	public void deleteById(Serializable id) {
		userDao.deleteById((Long)id);
	}

	public String encodePassword(String plainPassword) {
		try {
			return EncodeUtils.md5(plainPassword);
		} catch(Exception e) {
			logger.error("No se pudo codificar la password", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isValidUsername(String username) {
		return ValidatorUtils.isValidUsername(username);
	}

	public boolean isValidPassword(String password) {
		// Se valida la password de la misma forma que el nombre de usuario
		return ValidatorUtils.isValidUsername(password);
	}

	public boolean hasRole(User user, Role role) {
		return ((UserEntity)user).hasRole(role);
	}

	public boolean hasRole(Serializable userId, String roleName) {
		List <Authority> authorities = userDao.getAuthorityList((Long)userId);
		for(Authority authority : authorities) {
			if(authority.getRole().getAuthority().equals(roleName)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasRole(String username, String roleName) {
		List <Authority> authorities = userDao.getAuthorityList(username);
		for(Authority authority : authorities) {
			if(authority.getRole().getAuthority().equals(roleName)) {
				return true;
			}
		}
		return false;
	}
}
