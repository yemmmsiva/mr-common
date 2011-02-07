package mr.common.security.spring.model;

import mr.common.security.model.Role;

import org.springframework.security.GrantedAuthority;


/**
 * Wrapper que implementa a {@link org.springframework.security.GrantedAuthority GrantedAuthority}
 * y que contiene a {@link mr.common.security.model.Role Role}.
 * 
 * @author Mariano Ruiz
 */
public class GrantedAuthorityWrapper implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	private Role role;

	public GrantedAuthorityWrapper(Role role) {
		this.role = role;
	}

	public int compareTo(Object o) {
		return role.getAuthority().compareTo(((GrantedAuthority)o).getAuthority());
	}

	public String getAuthority() {
		return role.getAuthority();
	}

	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
