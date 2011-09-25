package mr.common.security.userentity.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import mr.common.model.AuditableEntity;

/**
 * Relación <i>usuario - role</i>.<br/>
 * Cada usuario tiene una lista de estas credenciales
 * {@link mr.common.security.userentity.model.UserEntity#getAuthorities()}. 
 * @author Mariano Ruiz
 */
@Entity(name="authority")
public class Authority extends AuditableEntity {

	private static final long serialVersionUID = -8338747885229182460L;

	private UserEntity user;
	private RoleEntity role;


	@ManyToOne
	@JoinColumn(name = "userId")
	@Cascade({CascadeType.ALL})
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "roleId")
	@Cascade({CascadeType.ALL})
	public RoleEntity getRole() {
		return role;
	}
	public void setRole(RoleEntity role) {
		this.role = role;
	}

	@Transient
	public String getAuthority() {
		return role.getAuthority();
	}

	public String toString() {
		return "user " + user.getUsername() + " -> " + role.getAuthority();
	}
}
