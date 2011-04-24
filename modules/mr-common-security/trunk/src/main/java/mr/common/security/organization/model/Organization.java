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
	 * @return Serializable - identificador de la organización
	 */
	Serializable getId();

	/**
	 * @return Serializable - identificador de la organización
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
	 * @return time zone id de la organización
	 * @see java.util.TimeZone
	 */
	String getTimeZoneId();
	/**
	 * @param timeZone id del time zone de la organización
	 */
	void setTimeZoneId(String timeZone);

	/**
	 * Nombre de la ciudad o región de la organización.
	 */
	String getCityOrRegionName();
	/**
	 * @param cityOrRegionName nombre de la ciudad
	 * o región de la organización
	 */
	void setCityOrRegionName(String cityOrRegionName);

	/**
	 * Obtiene el código ISO 3166 A3 del país de la
	 * organización.
	 * @see http://www.iso.org/iso/country_codes.htm
	 */
	String getCountryId();
	/**
	 * @param countryId código ISO 3166 A3 del país de la
	 * organización
	 * @see http://www.iso.org/iso/country_codes.htm
	 */
	void setCountryId(String countryId);

	/**
	 * Identificador del logo
	 * persistido de la organización.
	 */
	Serializable getLogoId();
	/**
	 * @param logoId identificador del logo
	 * persistido de la organización
	 */
	void setLogoId(Serializable logoId);
}
