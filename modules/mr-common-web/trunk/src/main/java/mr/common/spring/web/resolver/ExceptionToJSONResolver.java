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
 * Captura las excepciones lanzadas por los controllers y flows, y envía
 * la excepción a la página por default de errores
 * {@link #getDefaultErrorPage()}.<br/>
 * Si el entorno es `production` (ver {@link #isProductionEnviroment()})
 * y la excepción no es esperada (no extiende de
 * {@link mr.common.exception.spring.FrameworkException FrameworkException})
 * se envía un mensaje internacionalizado con la key
 * <code>fwk.constant.generic.errorWithId</code> y un número de error. El
 * mensaje debe ser algo así: <i>An error occurred (id <code>{0}</code>)</i>.<br/>
 * En caso de que el entorno sea distinto de producción (ej. `development`),
 * se enviará en la respuesta la traza de la excepción con el id y el
 * mensaje de error.<br>
 * En ambos casos también se logueará con <i>log4j</i> la traza, el id de error y
 * el mensaje como <code>ERROR</code> si es una excepción no esperada, o como
 * <code>DEBUG</code> si es una excepción esperada.
 *
 * @author Mariano Ruiz
 */
public class ExceptionToJSONResolver implements HandlerExceptionResolver {

    private static final Log logger = LogFactory.getLog(ExceptionToJSONResolver.class);

	@Autowired(required=false)
	private Properties appProperties;

	private String errorPage = JSPView.DEFAULT_VIEW;


    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
    		                             Object obj, Exception e) {
        if (e != null) {
            Map model = new HashMap();
            if(e instanceof FrameworkException) {
            	logger.debug(ExceptionUtils.getStackTraceAsString(e));
            	model.put(JSPView.ERRORS, e);
            } else {
            	String errorId = new Integer(RandomUtils.nextInt()).toString();
                logger.error("An error occurred (id " + errorId + ")\n" + ExceptionUtils.getStackTraceAsString(e));
            	if(isProductionEnviroment()) {
            		model.put(JSPView.ERRORS, new FrameworkException(e, "fwk.constant.generic.errorWithId", errorId));
            	} else {
            		model.put(JSPView.ERRORS, e);
            	}
            }
            return new ModelAndView(getDefaultErrorPage(), model);
        } else {
            return null;
        }
    }

    /**
     * @return <code>true</code> si el entorno es de producción
     */
	public boolean isProductionEnviroment() {
		return appProperties==null
		  || appProperties.getProperty("enviroment", "production").equals("production");
	}

	/**
	 * Devuelve el nombre de la página donde se renderiza el error.<br/>
	 * Esta implementación devuelve {@link mr.common.spring.view.JSPView
	 * #DEFAULT_VIEW JSPView.DEFAULT_VIEW}, o la página seteada con
	 * {@link #setDefaultErrorPage(String)}, pero puede sobreescribirse
	 * para direccionarse el error a otro JSP.
	 * @return String
	 */
	public String getDefaultErrorPage() {
		return errorPage;
	}

	/**
	 * Setea la página de renderización de los errores.
	 * @param errorPage String
	 */
	public void setDefaultErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}
}
