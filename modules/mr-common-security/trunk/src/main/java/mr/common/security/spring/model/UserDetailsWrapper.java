package mr.common.security.spring.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mr.common.security.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



/**
 * Wrapper que implementa {@link org.springframework.security.core.userdetails.UserDetails
 * UserDetails} que contiene a {@link mr.common.security.model.User User}.
 *
 * @author Mariano Ruiz
 */
public class UserDetailsWrapper implements UserDetails {

	private static final long serialVersionUID = 1L;

	private User user;

	public UserDetailsWrapper(User user) {
		this.user = user;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auths =
				new ArrayList<GrantedAuthority>(user.getRoles().size());
		for(int i=0; i<user.getRoles().size(); i++) {
			auths.add(new GrantedAuthorityWrapper(user.getRoles().get(i)));
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
