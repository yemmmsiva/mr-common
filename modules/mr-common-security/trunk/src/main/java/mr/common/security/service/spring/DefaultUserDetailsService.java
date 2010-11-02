package mr.common.security.service.spring;

import java.util.Properties;

import mr.common.security.model.User;
import mr.common.security.service.UserService;
import mr.common.security.spring.model.UserDetailsWrapper;

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

	@Autowired
	private UserService userService;

	@Autowired(required=false)
	private Properties appProperties;


	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		User user;
		if(appProperties!=null && appProperties.get("login.authenticateBy").equals("email")) {
			user = userService.getByEmailAddress(username);
		} else {
			user = userService.getByUsername(username);
		}
		if(user==null) {
			throw new BadCredentialsException("User not exist");
		}
		return new UserDetailsWrapper(user);
	}
}
