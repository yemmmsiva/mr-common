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

	private UserData userData;
	private List<Authority> authorities;


	@Transient
	public List<Role> getRoles() {
		List<Role> roles = new ArrayList<Role>();
		for(Authority a : authorities) {
			roles.add(a.getRole());
		}
		return roles;
	}

	/**
	 * @deprecated Operación no soportada, usar {@link #setAuthorities(List)}
	 * para configurar los roles.
	 * @throws UnsupportedOperationException si es invocado el método
	 */
	@Deprecated
	public void setRoles(List<Role> roles) {
		throw new UnsupportedOperationException(
				"Use `mr.common.security.userentity.model.UserEntity.setAuthorities(List<Authority>)`"
				+ " to set roles.");
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

	/**
	 * Listado de credenciales del usuario.
	 * @return {@link mr.common.security.userentity.model.Authority}
	 */
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	@Where(clause = Audit.UNDELETED_RESTRICTION)
	@Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public List<Authority> getAuthorities() {
		return authorities;
	}
	/**
	 * 
	 * @param authorities - lista de credenciales
	 * {@link mr.common.security.userentity.model.Authority Authority}
	 * a setear al usuario
	 */
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@OneToOne
	@JoinColumn(name="userDataId")
	@Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public UserData getUserData() {
		return userData;
	}
	public void setUserData(UserData person) {
		this.userData = person;
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

	public String getCommonName() {
		return userData.getCommonName();
	}

	public void setCommonName(String commonName) {
		userData.setCommonName(commonName);
	}
}
