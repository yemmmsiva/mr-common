package mr.common.security.spring.model;

import mr.common.security.model.User;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;


/**
 * Wrapper que implementa {@link org.springframework.security.userdetails.UserDetails UserDetails} que
 * contiene a {@link mr.common.security.model.User User}.
 * 
 * @author Mariano Ruiz
 *
 */
public class UserDetailsWrapper implements UserDetails {

	private static final long serialVersionUID = 1L;

	private User user;

	public UserDetailsWrapper(User user) {
		this.user = user;
	}

	public GrantedAuthority[] getAuthorities() {
		GrantedAuthority [] auths = new GrantedAuthority[user.getRoles().size()];
		for(int i=0; i<auths.length; i++) {
			auths[i] = new GrantedAuthorityWrapper(user.getRoles().get(i));
		}
		return auths;
	}

	public String getPassword() {
		return user.getPassword();
	}

	public String getUsername() {
		return user.getUsername();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return user.isEnabled();
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
