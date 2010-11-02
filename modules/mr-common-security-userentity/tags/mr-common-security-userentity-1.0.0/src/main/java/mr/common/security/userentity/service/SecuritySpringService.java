package mr.common.security.userentity.service;

import mr.common.security.service.UserSecurityService;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;


/**
 * Implementación de Spring de <code>SecurityService</code>.
 * @author Mariano Ruiz
 */
public class SecuritySpringService implements UserSecurityService {

	/**
	 * {@inheritDoc}
	 */
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
