package mr.common.security.userentity.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import mr.common.format.FormatUtils;
import mr.common.model.AuditableEntity;


/**
 * Clase que representa a un persona, con algunos
 * campos mapeados según la clase person de LDAP.
 * @author Mariano Ruiz
 */
@MappedSuperclass
public abstract class Person extends AuditableEntity {

	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private String commonName;
	private Boolean male;
	private String telephoneNumber;
	private String description;
	private String postalAddress;
	private String postalCode;
	private String cityOrRegionName;
	private String stateOrProvinceName;
	private String countryId;
	private Boolean org;
	private String timeZoneId;
	private Date birthdayDate;


	/**
	 * Retorna el commonName si existe, sino el apellido y nombre.
	 * @return String
	 */
	public String getCommonName() {
		if(commonName == null) {
			FormatUtils.getFullName(lastName, "", firstName);
		}
		return commonName;
	}

	public String toString() {
		return getCommonName();
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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

	public Boolean getOrg() {
		return org;
	}
	public void setOrg(Boolean org) {
		this.org = org;
	}

	public Boolean getMale() {
		return male;
	}
	public void setMale(Boolean male) {
		this.male = male;
	}

	public String getTimeZoneId() {
		return timeZoneId;
	}
	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	public Date getBirthdayDate() {
		return birthdayDate;
	}
	public void setBirthdayDate(Date birthdayDate) {
		this.birthdayDate = birthdayDate;
	}
}
