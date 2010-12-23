package mr.common.spring.web.resolver;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import mr.common.exception.ExceptionUtils;
import mr.common.spring.view.JSPView;

/**
 * Captura las excepciones lanzadas por los controllers y flows, y parsea
 * la excepción en un objeto JSON para que pueda ser leído por ExtJS.
 * @author Mariano Ruiz
 *
 */
public class ExceptionToJSONResolver implements HandlerExceptionResolver {

    private final Log logger = LogFactory.getLog(getClass());

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ModelAndView resolveException(HttpServletRequest arg0, HttpServletResponse arg1,
    		                             Object arg2, Exception e) {
        if (e != null) {
            logger.debug(ExceptionUtils.getStackTraceAsString(e));
            Map model = new HashMap();
            model.put(JSPView.ERRORS, e);
            return new ModelAndView(JSPView.DEFAULT_VIEW, model);
        } else {
            return null;
        }
    }
}
