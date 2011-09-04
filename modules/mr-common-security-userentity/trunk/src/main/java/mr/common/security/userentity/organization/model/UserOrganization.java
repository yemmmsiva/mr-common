package mr.common.security.userentity.organization.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import mr.common.model.Audit;
import mr.common.model.AuditableEntity;
import mr.common.security.model.Role;
import mr.common.security.userentity.model.RoleEntity;
import mr.common.security.userentity.model.UserEntity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Where;


/**
 * Usuarios - Organizaciones.
 * @author Mariano Ruiz
 */
@Entity(name="usersorgs")
public class UserOrganization extends AuditableEntity {

	private static final long serialVersionUID = 1L;

	private UserEntity user;
	private OrganizationEntity organization;
	private List<UserOrganizationRole> authorities;


	@Transient
	public List<Role> getRoles() {
		if(authorities!=null) {
			List<Role> roles = new ArrayList<Role>();
			for(UserOrganizationRole a : authorities) {
				roles.add(a.getRole());
			}
			return roles;
		}
		return null;
	}

	/**
	 * Setea los roles del usuario en la organización.<br/>
	 * Tener en cuenta que la persistencia
	 * de los roles no es en cascada con la del usuario. Para
	 * hacerlo se deben persistir los objetos
	 * {@link mr.common.security.userentity.organization.model.
	 * UserOrganizationRole UserOrganizationRole}
	 * que se obtienen con {@link #getAuthorities()}
	 */
	public void setRoles(List<Role> roles) {
		List<UserOrganizationRole> authorities =
				new ArrayList<UserOrganizationRole>(roles.size());
		for(Role role : roles) {
			UserOrganizationRole au = new UserOrganizationRole();
			au.setRole((RoleEntity) role);
			au.setUserOrganization(this);
			authorities.add(au);
		}
		setAuthorities(authorities);
	}

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

	@OneToMany(mappedBy = "userOrganization", fetch = FetchType.LAZY)
	@JoinColumn(name = "usersorgsId")
	@Where(clause = Audit.UNDELETED_RESTRICTION)
	@Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public List<UserOrganizationRole> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<UserOrganizationRole> authorities) {
		this.authorities = authorities;
	}
}
