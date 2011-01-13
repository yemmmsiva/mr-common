package mr.common.spring.converter;

import mr.common.time.TimeUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.binding.convert.converters.CollectionToCollection;
import org.springframework.binding.convert.converters.NumberToNumber;
import org.springframework.binding.convert.converters.ObjectToCollection;
import org.springframework.binding.convert.converters.StringToBigDecimal;
import org.springframework.binding.convert.converters.StringToBigInteger;
import org.springframework.binding.convert.converters.StringToBoolean;
import org.springframework.binding.convert.converters.StringToByte;
import org.springframework.binding.convert.converters.StringToCharacter;
import org.springframework.binding.convert.converters.StringToDate;
import org.springframework.binding.convert.converters.StringToDouble;
import org.springframework.binding.convert.converters.StringToEnum;
import org.springframework.binding.convert.converters.StringToFloat;
import org.springframework.binding.convert.converters.StringToInteger;
import org.springframework.binding.convert.converters.StringToLabeledEnum;
import org.springframework.binding.convert.converters.StringToLocale;
import org.springframework.binding.convert.converters.StringToLong;
import org.springframework.binding.convert.converters.StringToShort;
import org.springframework.binding.convert.service.DefaultConversionService;
import org.springframework.util.ClassUtils;


public class ApplicationConversionService extends DefaultConversionService {

	private static final Log logger = LogFactory.getLog(ApplicationConversionService.class);

    @Override
    protected void addDefaultConverters() {

    	logger.debug("Adding default converters.");

		addConverter(new StringToByte());
		addConverter(new StringToBoolean());
		addConverter(new StringToCharacter());
		addConverter(new StringToShort());
		addConverter(new StringToInteger());
		addConverter(new StringToLong());
		addConverter(new StringToFloat());
		addConverter(new StringToDouble());
		addConverter(new StringToBigInteger());
		addConverter(new StringToBigDecimal());
		addConverter(new StringToLocale());
		//addConverter(new StringToDate());
		addConverter(new StringToLabeledEnum());
		addConverter(new NumberToNumber());
		addConverter(new ObjectToCollection(this));
		addConverter(new CollectionToCollection(this));
        if(ClassUtils.isPresent("java.lang.Enum", getClass().getClassLoader()))
            addConverter(new StringToEnum());

        // Fecha con internacionalización
		StringToDate dateConverter = new StringToDate();
		dateConverter.setPattern(TimeUtils.TIME_FORMAT_DDMMYYYY);
		addConverter("shortDate", dateConverter);
    }
}
