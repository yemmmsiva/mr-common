package mr.common.web.spring.binding.converter.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import mr.common.time.TimeUtils;

import org.springframework.util.StringUtils;


/**
 * Implementa {@link java.beans.PropertyEditor}, y formatea o devuelve
 * un {@link java.util.Date} localizado en el lenguaje del thread en ejecución. 
 * @author Mariano Ruiz
 */
public class LocalizedDateEditor extends PropertyEditorSupport {

	private boolean allowEmpty;

	public LocalizedDateEditor() {
		this.allowEmpty = true;
	}

	public LocalizedDateEditor(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if (allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
		} else {
			try {
				setValue(getDateFormat().parse(text));
			} catch (ParseException ex) {
				IllegalArgumentException iae = new IllegalArgumentException(
						"Could not parse date: " + ex.getMessage());
				iae.initCause(ex);
				throw iae;
			}
		}
	}

	public String getAsText() {
		Date value = (Date) getValue();
		return value == null ? "" : getDateFormat().format(value);
	}

	/**
	 * Devuelve dinámicamente el formateador de fechas.<br/>
	 * Puede ser sobreescrito para modificar el comportamiento
	 * para obtener el formateador.
	 * @return {@link java.text.DateFormat DateFormat}
	 * @see mr.common.time.TimeUtils#getDateFormatLocalized()
	 */
	public DateFormat getDateFormat() {
		return TimeUtils.getDateFormatLocalized();
	}
}
