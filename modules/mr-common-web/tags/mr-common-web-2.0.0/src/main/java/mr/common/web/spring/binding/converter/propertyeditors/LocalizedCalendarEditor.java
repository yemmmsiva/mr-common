package mr.common.web.spring.binding.converter.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

import mr.common.time.TimeUtils;

import org.springframework.util.StringUtils;


/**
 * Implementa {@link java.beans.PropertyEditor}, y formatea o devuelve
 * un {@link java.util.Calendar} localizado en el lenguaje del thread en ejecución. 
 * @author Mariano Ruiz
 */
public class LocalizedCalendarEditor extends PropertyEditorSupport {

	private boolean allowEmpty;

	public LocalizedCalendarEditor() {
		this.allowEmpty = true;
	}

	public LocalizedCalendarEditor(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if (allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
		} else {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(getDateFormat().parse(text));
				cal.setTimeZone(getTimeZone());
				setValue(cal);
			} catch (ParseException ex) {
				IllegalArgumentException iae = new IllegalArgumentException(
						"Could not parse date: " + ex.getMessage());
				iae.initCause(ex);
				throw iae;
			}
		}
	}

	public String getAsText() {
		Calendar value = (Calendar) getValue();
		return value == null ? "" : getDateFormat().format(value.getTime());
	}

	/**
	 * Devuelve dinámicamente el formateador de fechas.<br/>
	 * Puede ser sobreescrito para modificar el comportamiento
	 * para obtener el formateador.
	 * @see mr.common.time.TimeUtils#getDateFormatLocalized()
	 */
	public DateFormat getDateFormat() {
		return TimeUtils.getDateFormatLocalized();
	}

	/**
	 * Devuelve el timezone que se le seteará al calendar.<br/>
	 * Esta implementación llama a {@link java.util.TimeZone#getDefault()},
	 * pero puede ser sobreescrita para obtenerla a través
	 * de otra fuente.
	 */
	public TimeZone getTimeZone() {
		return TimeZone.getDefault();
	}
}
