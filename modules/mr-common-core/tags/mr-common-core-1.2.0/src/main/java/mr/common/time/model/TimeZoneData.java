package mr.common.time.model;

import java.util.Locale;
import java.util.TimeZone;

import mr.common.model.OptionData;


/**
 * DTO que implementa {@link mr.common.model.OptionData OptionData}, y
 * representa un {@link java.util.TimeZone TimeZone}, que puede ser internacionalizado
 * mediante la localización pasada ({@link java.util.Locale Locale}), y contiene
 * métodos para presentarlo en una interfaz con una descripción legible.
 *
 * @author Mariano Ruiz
 */
public class TimeZoneData implements OptionData {

	private static final long serialVersionUID = 1L;

	private String code;
	private String displayName;

	/**
	 * @param timeZone - time zone
	 * @param locale - localización, si es <code>null</code> se
	 * localizará con el lenguaje por defecto del sistema
	 * @see mr.common.time.model.TimeZoneData#TimeZoneWrapperData(TimeZone)
	 * @see mr.common.time.model.TimeZoneData
	 */
	public TimeZoneData(TimeZone timeZone, Locale locale) {
		code = timeZone.getID();
		if(locale!=null) {
			displayName = timeZone.getDisplayName(locale);
		} else {
			displayName = timeZone.getDisplayName();
		}
		displayName += " (" + code.replace('_', ' ') + ")";
	}

	/**
	 * @param timeZone time zone
	 * @see mr.common.time.model.TimeZoneData#TimeZoneWrapperData(TimeZone, Locale)
	 * @see mr.common.time.model.TimeZoneData
	 */
	public TimeZoneData(TimeZone timeZone) {
		code = timeZone.getID();
		displayName = timeZone.getDisplayName() + " (" + code.replace('_', ' ') + ")";
	}

	/**
	 * ID del time zone.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Descripción internacionalizada.
	 */
	public String getDescription() {
		return displayName;
	}

	/**
	 * Descripción internacionalizada de {@link #getDescription()}.
	 */
	public String toString() {
		return displayName;
	}
}
