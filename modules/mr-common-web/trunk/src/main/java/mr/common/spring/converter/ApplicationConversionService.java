package mr.common.spring.converter;

import mr.common.format.FormatUtils;

import org.springframework.binding.convert.converters.StringToDate;
import org.springframework.binding.convert.service.DefaultConversionService;
import org.springframework.stereotype.Component;


@Component("conversionService")
public class ApplicationConversionService extends DefaultConversionService {

    @Override
    protected void addDefaultConverters() {
	super.addDefaultConverters();
	StringToDate dateConverter = new StringToDate();
	dateConverter.setPattern(FormatUtils.TIME_FORMAT_DDMMYYYY);
	addConverter("shortDate", dateConverter);
    }
}
