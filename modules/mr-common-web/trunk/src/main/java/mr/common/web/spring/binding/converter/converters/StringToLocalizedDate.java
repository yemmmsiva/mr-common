package mr.common.web.spring.binding.converter.converters;

import java.text.DateFormat;

import mr.common.time.TimeUtils;

import org.springframework.binding.convert.converters.StringToDate;


/**
 * Extiende de {@link org.springframework.binding.convert.converters.StringToDate StringToDate},
 * y convierte la fecha desde el string usando como pattern el del locale del
 * thread de la operación.
 *
 * @author Mariano Ruiz
 */
public class StringToLocalizedDate extends StringToDate {

	public StringToLocalizedDate() {
		super();
	}

	@Override
	protected DateFormat getDateFormat() {
		return TimeUtils.getDateFormatLocalized();
	}
}
