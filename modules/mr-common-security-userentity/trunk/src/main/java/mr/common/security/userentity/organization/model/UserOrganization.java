package mr.common.security.userentity.organization.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import mr.common.model.AuditableEntity;
import mr.common.security.userentity.model.UserEntity;


/**
 * Usuarios - Organizaciones.
 * @author Mariano Ruiz
 */
@Entity(name="usersorgs")
public class UserOrganization extends AuditableEntity {

	private static final long serialVersionUID = 1L;

	private UserEntity user;
	private OrganizationEntity organization;


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
	@JoinColumn(name = "organizationId")
	@Cascade({CascadeType.ALL})
	public OrganizationEntity getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationEntity organization) {
		this.organization = organization;
	}
}
