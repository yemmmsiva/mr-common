package mr.common.security.userentity.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import mr.common.model.Audit;
import mr.common.model.AuditableEntity;
import mr.common.security.model.Role;
import mr.common.security.model.User;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Where;

/**
 * Mapeo de la tabla <code>user</code> con los usuarios de la aplicación.<br/>
 * Implementa {@link mr.common.security.model.User User}.
 * @author Mariano Ruiz
 */
@Entity(name="systemuser")
public class UserEntity extends AuditableEntity implements User {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String emailAddress;
	private boolean enabled;

	private UserData person;
	private List<Authority> authorities;


	@Transient
	public List<Role> getRoles() {
		List<Role> roles = new ArrayList<Role>();
		for(Authority a : authorities) {
			roles.add(a.getRole());
		}
		return roles;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	@Where(clause = Audit.UNDELETED_RESTRICTION)
	@Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public List<Authority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@OneToOne
	@JoinColumn(name="userDataId")
	@Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public UserData getPerson() {
		return person;
	}
	public void setPerson(UserData person) {
		this.person = person;
	}

	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public boolean hasRole(Role role) {
		for(Authority authority : authorities) {
			if(authority.getRole().equals(role)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasRole(String roleName) {
		for(Authority authority : authorities) {
			if(authority.getRole().getAuthority().equals(roleName)) {
				return true;
			}
		}
		return false;
	}
}
