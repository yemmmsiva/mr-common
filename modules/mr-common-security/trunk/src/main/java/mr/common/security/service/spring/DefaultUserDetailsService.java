package mr.common.security.service.spring;

import mr.common.security.exception.UserNotExistException;
import mr.common.security.model.User;
import mr.common.security.service.UserService;
import mr.common.security.spring.model.UserDetailsWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * Servicio que implementa {@link org.springframework.security.core.userdetails.UserDetailsService
 * UserDetailsService} y obtiene los usuarios de
 * {@link mr.common.security.service.UserService UserService}.
 *
 * @author Mariano Ruiz
 */
public class DefaultUserDetailsService implements UserDetailsService {

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

	private String authenticateMethod = AUTHENTICATE_BY_USERNAME;


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
	 */
	public String getAuthenticateMethod() {
		return authenticateMethod;
	}

	public void setAuthenticateMethod(String authenticateMethod) {
		this.authenticateMethod = authenticateMethod;
	}
}
