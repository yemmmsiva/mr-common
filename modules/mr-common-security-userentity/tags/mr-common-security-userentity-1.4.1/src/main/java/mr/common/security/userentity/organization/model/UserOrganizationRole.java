package mr.common.security.userentity.organization.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import mr.common.model.AuditableEntity;
import mr.common.security.userentity.model.RoleEntity;


/**
 * Usuarios - Organizaciones - Roles.
 * @author Mariano Ruiz
 */
@Entity(name="usersorgsrole")
public class UserOrganizationRole extends AuditableEntity {

	private static final long serialVersionUID = 1L;

	private UserOrganization userOrganization;
	private RoleEntity role;


	@ManyToOne
	@JoinColumn(name = "usersorgsId")
	@Cascade({CascadeType.ALL})
	public UserOrganization getUserOrganization() {
		return userOrganization;
	}
	public void setUserOrganization(UserOrganization userOrganization) {
		this.userOrganization = userOrganization;
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
}
