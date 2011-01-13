package mr.common.spring.web.resolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mr.common.exception.ExceptionUtils;
import mr.common.exception.spring.FrameworkException;
import mr.common.spring.view.JSPView;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


/**
 * Captura las excepciones lanzadas por los controllers y flows, y parsea
 * la excepción en un objeto JSON para que pueda ser leído por Ajax.
 *
 * @author Mariano Ruiz
 *
 */
public class ExceptionToJSONResolver implements HandlerExceptionResolver {

    private final Log logger = LogFactory.getLog(getClass());

	@Autowired(required=false)
	private Properties appProperties;


    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
    		                             Object obj, Exception e) {
        if (e != null) {
            Map model = new HashMap();
            if(e instanceof FrameworkException) {
            	logger.debug(ExceptionUtils.getStackTraceAsString(e));
            	model.put(JSPView.ERRORS, e);
            } else {
            	Integer errordId = RandomUtils.nextInt();
                logger.error("An error occurred (id " + errordId + ")\n" + ExceptionUtils.getStackTraceAsString(e));
            	if(appProperties==null
            		  || appProperties.getProperty("enviroment", "production").equals("production")) {
            		model.put(JSPView.ERRORS, new FrameworkException(e, "fwk.constant.generic.errorWithId", errordId.toString()));
            	} else {
            		model.put(JSPView.ERRORS, e);
            	}
            }
            return new ModelAndView(JSPView.DEFAULT_VIEW, model);
        } else {
            return null;
        }
    }
}
