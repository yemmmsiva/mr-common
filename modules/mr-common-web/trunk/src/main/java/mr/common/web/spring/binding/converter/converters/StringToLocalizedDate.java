package mr.common.web.spring.binding.converter.converters;

import mr.common.time.TimeUtils;

import org.springframework.binding.convert.converters.StringToDate;


/**
 * Extiende de {@link org.springframework.binding.convert.converters.StringToDate StringToDate},
 * y convierte la fecha desde el string usando como pattern el del locale del
 * hilo de la operación.
 *
 * @author Mariano Ruiz
 */
public class StringToLocalizedDate extends StringToDate {

	

	public StringToLocalizedDate() {
		super();
		setPattern(TimeUtils.TIME_FORMAT_DDMMYYYY);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object toObject(String string, Class targetClass) throws Exception {
		return super.toObject(string, targetClass);
	}
}
