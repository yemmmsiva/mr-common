package mr.common.displaytag.decorator.column;

import java.text.DecimalFormat;

import javax.servlet.jsp.PageContext;

import mr.common.format.FormatUtils;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;


/**
 * Decorator para que displaytag formatee número decimales, ej. 98.453.432,9.
 * @author Mariano Ruiz
 */
public class DecimalColumnDecorator implements DisplaytagColumnDecorator
{
    private DecimalFormat decimalFormat = new DecimalFormat(FormatUtils.FORMAT_DECIMAL);

    /**
     * {@inheritDoc}
     */
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException
    {
    	if(columnValue==null) return "";
    	return decimalFormat.format(columnValue);
    }
}
