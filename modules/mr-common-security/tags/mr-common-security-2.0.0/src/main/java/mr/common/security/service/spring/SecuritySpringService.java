package mr.common.security.service.spring;

import mr.common.security.service.UserSecurityService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * Implementación de Spring de {@link mr.common.security.service.UserSecurityService SecurityService}.
 * @author Mariano Ruiz
 */
public class SecuritySpringService implements UserSecurityService {

	public String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication==null || authentication.getPrincipal() instanceof String) {	// Usuario anónimo
			return null;
		}
		UserDetails u = (UserDetails) (authentication == null ? null : authentication.getPrincipal());
		if(u!=null) {
			return u.getUsername();
		}
		return null;
	}
}
