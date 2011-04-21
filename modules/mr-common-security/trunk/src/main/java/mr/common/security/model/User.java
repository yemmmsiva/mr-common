package mr.common.security.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Usuario del sistema.
 * @author Mariano Ruiz
 */
public interface User extends Serializable {

	/**
	 * @return Serializable - identificador del usuario
	 */
	Serializable getId();

	/**
	 * @return Serializable - identificador del usuario
	 */
	void setId(Serializable id);

	/**
	 * @return lista de roles activos del usuario
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	List<Role> getRoles();
	/**
	 * Setea los roles del usuario en el sistema.
	 * @param roles - lista de roles del usuario
	 */
	void setRoles(List<Role> roles);

	/**
	 * Verifica si el usuario tiene el rol. Este método
	 * puede no estar soportado por la implementación.<br/>
	 * En su lugar se recomienda usar los métodos del
	 * servicio: <i><code>UserService.hasRole(..)</code></i>.
	 * @param role {@link Role}
	 * @return <code>true</code> si el usuario tiene el rol.
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean hasRole(Role role);

	/**
	 * Verifica si el usuario tiene el rol. Este método
	 * puede no estar soportado por la implementación.<br/>
	 * En su lugar se recomienda usar los métodos del
	 * servicio: <i><code>UserService.hasRole(..)</code></i>.
	 * @param roleName String
	 * @return <code>true</code> si el usuario tiene el rol.
	 * @throws UnsupportedOperationException Si la operación
	 * no es soportada por la implementación
	 */
	boolean hasRole(String roleName);

	/**
	 * @return String - nombre identificativo del usuario en el sistema
	 */
	String getUsername();
	/**
	 * @param username - nombre identificativo del usuario en el sistema
	 */
	void setUsername(String username);

	/**
	 * @return String - password del usuario (puede estar encriptada dependiendo
	 * de la implementación)
	 */
	String getPassword();
	/**
	 * @param password - contraseña del usuario (puede estar encriptada dependiendo
	 * de la implementación)
	 */
	void setPassword(String password);

	/**
	 * @return <code>true</code> si el usuario está activado (puede ingresar al sistema)
	 */
	boolean isEnabled();
	/**
	 * @param enabled - <code>true</code> si el usuario debe
	 * estar activado (puede ingresar al sistema)
	 */
	void setEnabled(boolean enabled);

	/**
	 * @return String - correo electrónico del usuario, que también puede ser usado
	 * para su identificación
	 */
	String getEmailAddress();
	/**
	 * @param emailAddress - correo electrónico del usuario, que también puede ser usado
	 * para su identificación
	 */
	void setEmailAddress(String emailAddress);

	/**
	 * @return String - nombre real o identificativo del usuario, no puede ser usado
	 * para su identificación
	 */
	String getCommonName();
	/**
	 * @param commonName - nombre real o identificativo del usuario, no puede ser usado
	 * para su identificación
	 */
	void setCommonName(String commonName);

	/**
	 * @return - primer nombre (nombre de pila)
	 */
	String getFirstName();
	/**
	 * @param firstName - primer nombre (nombre de pila)
	 */
	void setFirstName(String firstName);

	/**
	 * @return apellido de la persona
	 */
	String getLastName();
	/**
	 * @param lastName - apellido de la persona
	 */
	void setLastName(String lastName);
	
	String getTelephoneNumber();

	void setTelephoneNumber(String telephoneNumber);
	/**
	 * @return descripción o comentario útil acerca
	 * del usuario
	 */
	String getDescription();
	/**
	 * @param description - descripción o comentario útil acerca
	 * del usuario
	 */
	void setDescription(String description);

	String getPostalAddress();

	void setPostalAddress(String postalAddress);

	String getPostalCode();

	void setPostalCode(String postalCode);

	String getStateOrProvinceName();

	void setStateOrProvinceName(String stateOrProvinceName);

	/**
	 * @return <code>true</code> si es una organización
	 * (persona jurídica)
	 */
	Boolean getOrg();
	/**
	 * @param org: <code>true</code> si es una organización
	 * (persona jurídica)
	 */
	void setOrg(Boolean org);

	/**
	 * 
	 * @return <code>true</code> si es hombre
	 */
	Boolean getMale();
	/**
	 * @param male: <code>true</code> si es hombre
	 */
	void setMale(Boolean male);

	/**
	 * @return time zone id del usuario
	 * @see java.util.TimeZone
	 */
	String getTimeZoneId();
	/**
	 * @param timeZone id del time zone del usuario
	 */
	void setTimeZoneId(String timeZone);

	/**
	 * Nombre de la ciudad o región del usuario.
	 */
	String getCityOrRegionName();
	/**
	 * @param cityOrRegionName nombre de la ciudad
	 * o región del usuario
	 */
	void setCityOrRegionName(String cityOrRegionName);

	/**
	 * Obtiene el código ISO 3166 A3 del país del
	 * usuario.
	 * @see http://www.iso.org/iso/country_codes.htm
	 */
	String getCountryId();
	/**
	 * @param countryId código ISO 3166 A3 del país del
	 * usuario
	 * @see http://www.iso.org/iso/country_codes.htm
	 */
	void setCountryId(String countryId);

	/**
	 * @return fecha de nacimiento del usuario
	 */
	Date getBirthdayDate();
	/**
	 * @param date: fecha de nacimiento del usuario
	 */
	void setBirthdayDate(Date date);

	/**
	 * Identificador de la fotografía o pick
	 * persistido del usuario.
	 */
	Serializable getPortraitId();
	/**
	 * @param portraitId identificador de la fotografía
	 * o pick persistido del usuario
	 */
	void setPortraitId(Serializable portraitId);
}
