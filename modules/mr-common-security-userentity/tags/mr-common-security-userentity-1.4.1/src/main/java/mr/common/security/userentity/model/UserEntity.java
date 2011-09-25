package mr.common.security.userentity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
	private boolean enabled = true;

	private UserData userData;
	private List<Authority> authorities;


	@Transient
	public List<Role> getRoles() {
		if(authorities!=null) {
			List<Role> roles = new ArrayList<Role>();
			for(Authority a : authorities) {
				roles.add(a.getRole());
			}
			return roles;
		}
		return null;
	}

	/**
	 * Setea los roles del usuario en el sistema.<br/>
	 * Tener en cuenta que la persistencia
	 * de los roles no es en cascada con la del usuario. Para
	 * hacerlo se deben persistir los objetos
	 * {@link mr.common.security.userentity.model.Authority Authority}
	 * que se obtienen con {@link #getAuthorities()}
	 */
	public void setRoles(List<Role> roles) {
		List<Authority> authorities = new ArrayList<Authority>(roles.size());
		for(Role role : roles) {
			Authority au = new Authority();
			au.setRole((RoleEntity) role);
			au.setUser(this);
			authorities.add(au);
		}
		setAuthorities(authorities);
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

	@Transient
	public String getCommonName() {
		return userData.getCommonName();
	}
	public void setCommonName(String commonName) {
		userData.setCommonName(commonName);
	}

	@Transient
	public String getFirstName() {
		return userData.getFirstName();
	}
	public void setFirstName(String firstName) {
		userData.setFirstName(firstName);
	}

	@Transient
	public String getLastName() {
		return userData.getLastName();
	}
	public void setLastName(String lastName) {
		userData.setLastName(lastName);
	}

	@Transient
	public String getTelephoneNumber() {
		return userData.getTelephoneNumber();
	}
	public void setTelephoneNumber(String telephoneNumber) {
		userData.setTelephoneNumber(telephoneNumber);
	}

	@Transient
	public String getDescription() {
		return userData.getDescription();
	}
	public void setDescription(String description) {
		userData.setDescription(description);
	}

	@Transient
	public String getPostalAddress() {
		return userData.getPostalAddress();
	}
	public void setPostalAddress(String postalAddress) {
		userData.setPostalAddress(postalAddress);
	}

	@Transient
	public String getPostalCode() {
		return userData.getPostalCode();
	}
	public void setPostalCode(String postalCode) {
		userData.setPostalCode(postalCode);
	}

	@Transient
	public String getStateOrProvinceName() {
		return userData.getStateOrProvinceName();
	}
	public void setStateOrProvinceName(String stateOrProvinceName) {
		userData.setStateOrProvinceName(stateOrProvinceName);
	}

	@Transient
	public String getCountryId() {
		return userData.getCountryId();
	}
	public void setCountryId(String countryId) {
		userData.setCountryId(countryId);
	}

	@Transient
	public String getCityOrRegionName() {
		return userData.getCityOrRegionName();
	}
	public void setCityOrRegionName(String cityOrRegionName) {
		userData.setCityOrRegionName(cityOrRegionName);
	}

	@Transient
	public Boolean getOrg() {
		return userData.getOrg();
	}
	public void setOrg(Boolean org) {
		userData.setOrg(org);
	}

	@Transient
	public Boolean getMale() {
		return userData.getMale();
	}
	public void setMale(Boolean male) {
		userData.setMale(male);
	}

	@Transient
	public String getTimeZoneId() {
		return userData.getTimeZoneId();
	}
	public void setTimeZoneId(String timeZone) {
		userData.setTimeZoneId(timeZone);
	}

	@Transient
	public Date getBirthdayDate() {
		return userData.getBirthdayDate();
	}
	public void setBirthdayDate(Date date) {
		userData.setBirthdayDate(date);
	}

	@Transient
	public Serializable getPortraitId() {
		return userData.getPortraitId();
	}
	public void setPortraitId(Serializable portraitId) {
		userData.setPortraitId((Long)portraitId);
	}

	@Transient
	public void setId(Serializable id) {
		setId((Long)id);
	}

	@Transient
	public boolean isLocked() {
		return userData.isLocked();
	}

	@Transient
	public void setLocked(boolean locked) {
		userData.setLocked(locked);
	}
}
