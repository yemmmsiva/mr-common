package mr.common.security.userentity.organization.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Transient;

import mr.common.model.AuditableEntity;
import mr.common.security.organization.model.Organization;


/**
 * Implementación de {@link
 * mr.common.security.organization.model.Organization Organization}.
 * @author Mariano Ruiz
 */
@Entity(name="organization")
public class OrganizationEntity extends AuditableEntity
      implements Organization {

	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	private String telephoneNumber;
	private String postalAddress;
	private String postalCode;
	private String cityOrRegionName;
	private String stateOrProvinceName;
	private String countryId;
	private String timeZoneId;
	private Long logoId;
	private boolean enabled = true;
	private boolean locked = false;


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Transient
	public void setId(Serializable id) {
		setId((Long)id);
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public String getPostalAddress() {
		return postalAddress;
	}
	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCityOrRegionName() {
		return cityOrRegionName;
	}
	public void setCityOrRegionName(String cityOrRegionName) {
		this.cityOrRegionName = cityOrRegionName;
	}
	public String getStateOrProvinceName() {
		return stateOrProvinceName;
	}
	public void setStateOrProvinceName(String stateOrProvinceName) {
		this.stateOrProvinceName = stateOrProvinceName;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getTimeZoneId() {
		return timeZoneId;
	}
	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}
	public Long getLogoId() {
		return logoId;
	}
	public void setLogoId(Serializable logoId) {
		this.logoId = (Long) logoId;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}
