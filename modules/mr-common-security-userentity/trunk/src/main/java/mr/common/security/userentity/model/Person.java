package mr.common.security.userentity.model;

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

	private String givenName;
	private String surname;
	private String commonName;
	private String telephoneNumber;
	private String description;
	private String postalAddress;
	private String postalCode;
	private String mail;
	private String countryName;
	private String stateOrProvinceName;
	private String gender;
	private Boolean org;


	/**
	 * Retorna el commonName si existe, sino el apellido y nombre.
	 * @return String
	 */
	public String getCommonName() {
		if(commonName == null) {
			FormatUtils.getApellidoNombre(surname, "", givenName);
		}
		return commonName;
	}

	/**
	 * @see mr.common.model.Person.getCommonName()
	 */
	public String toString() {
		return getCommonName();
	}


	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getStateOrProvinceName() {
		return stateOrProvinceName;
	}
	public void setStateOrProvinceName(String stateOrProvinceName) {
		this.stateOrProvinceName = stateOrProvinceName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return <code>true</code> si es una organización
	 * (persona jurídica)
	 */
	public Boolean getOrg() {
		return org;
	}
	public void setOrg(Boolean org) {
		this.org = org;
	}
}
