package mr.common.displaytag.decorator.column;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.PageContext;

import mr.common.format.FormatUtils;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;


/**
 * Decorator para que displaytag formatee fechas dd/MM/yyyy.
 * @author Mariano Ruiz
 */
public class DateColumnDecorator implements DisplaytagColumnDecorator
{
    private SimpleDateFormat dateFormat = new SimpleDateFormat(FormatUtils.TIME_FORMAT_DDMMYYYY);

    /**
     * {@inheritDoc}
     */
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException
    {
    	if(columnValue==null) return "";
        return dateFormat.format((Date) columnValue);
    }
}
