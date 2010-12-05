package mr.common.security.service.spring;

import java.util.Properties;

import mr.common.security.exception.UserNotExistException;
import mr.common.security.model.User;
import mr.common.security.service.UserService;
import mr.common.security.spring.model.UserDetailsWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;


/**
 * Servicio que implementa {@link org.springframework.security.userdetails.UserDetailsService UserDetailsService}
 * y obtiene los usuarios de {@link mr.common.security.service.UserService UserService}.
 * 
 * @author Mariano Ruiz
 */
public class DefaultUserDetailsService implements UserDetailsService {

	private static final Log logger = LogFactory.getLog(DefaultUserDetailsService.class);

	/**
	 * <code>`email`</code>: La autenticación se lleva a cabo por el email del usuario.
	 */
	public static final String AUTHENTICATE_BY_EMAIL = "email";
	/**
	 * <code>`username`</code>: La autenticación se lleva a cabo por el nombre del usuario.
	 */
	public static final String AUTHENTICATE_BY_USERNAME = "username";

	@Autowired
	private UserService userService;

	@Autowired(required=false)
	private Properties appProperties;


	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		User user;
		try {
			if(getAuthenticateMethod().equals(AUTHENTICATE_BY_EMAIL)) {
				user = userService.getByEmailAddress(username);
			} else {
				user = userService.getByUsername(username);
			}
		} catch(UserNotExistException e) {
			throw new BadCredentialsException("User not exist");
		}
		return new UserDetailsWrapper(user);
	}

	/**
	 * Método de autenticación (con qué campo se va a identificar
	 * el usuario).
	 * <ul>
	 *   <li>{@link #AUTHENTICATE_BY_USERNAME}</li>
	 *   <li>{@link #AUTHENTICATE_BY_EMAIL}</li>
	 * </ul>
	 * @return String
	 */
	public String getAuthenticateMethod() {
		String method;
		if(appProperties == null) {
			method = AUTHENTICATE_BY_USERNAME;
			logger.warn("`appProperties` is null.");
			logger.warn("Use `username` default method authentication.");
		} else {
			method = (String) appProperties.get("authenticateBy");
			if(method == null) {
				method = AUTHENTICATE_BY_USERNAME;
			}
		}
		return method;
	}
}
