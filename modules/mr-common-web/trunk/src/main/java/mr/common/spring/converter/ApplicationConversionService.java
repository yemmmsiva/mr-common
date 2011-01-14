package mr.common.spring.converter;

import mr.common.web.spring.binding.converter.converters.StringToLocalizedDate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.binding.convert.converters.CollectionToCollection;
import org.springframework.binding.convert.converters.Converter;
import org.springframework.binding.convert.converters.NumberToNumber;
import org.springframework.binding.convert.converters.ObjectToCollection;
import org.springframework.binding.convert.converters.StringToBigDecimal;
import org.springframework.binding.convert.converters.StringToBigInteger;
import org.springframework.binding.convert.converters.StringToBoolean;
import org.springframework.binding.convert.converters.StringToByte;
import org.springframework.binding.convert.converters.StringToCharacter;
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


/**
 * Extiende de {@link org.springframework.binding.convert.service.DefaultConversionService
 * DefaultConversionService}, he instala los conversores para el binding de parámetros
 * a forms.<br/>
 * Usa los converters estándar que provee Spring, pero en vez de usar
 * {@link org.springframework.binding.convert.converters.StringToDate StringToDate}, utiliza
 * {@link mr.common.web.spring.binding.converter.converters.StringToLocalizedDate
 * StringToLocalizedDate} para convertir de string a {@link java.util.Date}.<br/>
 * <b>NOTA</b>: los conversions service en Spring 2.x solo funcionan con Spring Webflow,
 * en controladores de Spring MVC es soportado a partir de las versiones 3.x, sino pueden
 * usarse para los controllers de la versión 2.x algún binding initializer, como
 * {@link mr.common.web.spring.binding.BindingInitializer BindingInitializer}.<br/>
 * También para que funcione este conversion service, debe ser contenido por algún
 * <code>ApplicationContext</code>.
 *
 * @author Mariano Ruiz
 */
public class ApplicationConversionService extends DefaultConversionService {

	private static final Log logger = LogFactory.getLog(ApplicationConversionService.class);

	private Converter stringToDateConverter;


	public ApplicationConversionService() {
		super();
		stringToDateConverter = new StringToLocalizedDate();
	}

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
		addConverter("date", getStringToDateConverter());
    }

	/**
	 * Retorna un {@link org.springframework.binding.convert.converters.Converter
	 * Converter} para el binding de objetos {@link java.util.Date}.<br/>
	 * Esta implementación devuelve un
	 * {@link mr.common.web.spring.binding.converter.converters.StringToLocalizedDate
	 * StringToLocalizedDate}, pero puede ser sobreescrita para
	 * devolver un converter ajustado a las necesidades. 
	 */
    public Converter getStringToDateConverter() {
    	return stringToDateConverter;
    }
}
