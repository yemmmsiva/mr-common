package mr.common.displaytag.decorator.column;

import java.text.DecimalFormat;

import javax.servlet.jsp.PageContext;

import mr.common.format.FormatUtils;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;



/**
 * Decorator para que displaytag formatee decimales como moneda, ej. 12.787.78,45.
 * @author Mariano Ruiz
 */
public class MoneyColumnDecorator implements DisplaytagColumnDecorator
{
    private DecimalFormat decimalFormat = new DecimalFormat(FormatUtils.FORMAT_MONEY);

    /**
     * {@inheritDoc}
     */
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException
    {
    	if(columnValue==null) return "";
    	return decimalFormat.format(columnValue);
    }
}
