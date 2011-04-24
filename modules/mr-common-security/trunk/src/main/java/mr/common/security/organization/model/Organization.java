package mr.common.security.organization.model;

import java.io.Serializable;


/**
 * Organización de la aplicación.<br/>
 * Pueden agraparse usuarios dentro de las mismas, y
 * estos tener roles dentro de la organización.
 *
 * @author Mariano Ruiz
 */
public interface Organization extends Serializable {


	/**
	 * @return Serializable - identificador del usuario
	 */
	Serializable getId();

	/**
	 * @return Serializable - identificador del usuario
	 */
	void setId(Serializable id);

	/**
	 * Nombre de la organización.
	 */
	String getName();

	/**
	 * Nombre de la organización.
	 * @param name nuevo nombre
	 */
	void setName(String name);

	/**
	 * Descripción de la organización.
	 */
	String getDescription();

	/**
	 * Descripción de la organización.
	 * @param description nueva descripción
	 */
	void setDescription(String description);

	/**
	 * @return <code>true</code> si
	 * la organización está activa en la
	 * aplicación
	 */
	boolean isEnabled();

	/**
	 * Si <code>enabled = true</code>
	 * la organización estará activa, sino
	 * la organización estará inactiva.
	 */
	void setEnabled(boolean enabled);

	/**
	 * Número de teléfono.
	 */
	String getTelephoneNumber();
	/**
	 * Número de teléfono.
	 */
	void setTelephoneNumber(String telephoneNumber);

	String getPostalAddress();

	void setPostalAddress(String postalAddress);

	String getPostalCode();

	void setPostalCode(String postalCode);

	String getStateOrProvinceName();

	void setStateOrProvinceName(String stateOrProvinceName);

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
