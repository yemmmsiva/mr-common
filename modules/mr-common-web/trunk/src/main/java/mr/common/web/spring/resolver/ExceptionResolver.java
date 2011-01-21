package mr.common.web.spring.resolver;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mr.common.context.EnviromentConfiguration;
import mr.common.exception.ExceptionUtils;
import mr.common.exception.spring.FrameworkException;
import mr.common.web.spring.view.JSPView;

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
 * Si el método {@link #showErrorMessageId()} retorna <code>true</code>
 * y la excepción no es esperada (no extiende de
 * {@link mr.common.exception.spring.FrameworkException FrameworkException})
 * se envía un mensaje internacionalizado con la key
 * <code>fwk.constant.generic.errorWithId</code> y un número de error. El
 * mensaje debe ser algo así: <i>An error occurred (id <code>{0}</code>)</i>.<br/>
 * En caso de que el método devuelva <code>false</code>,
 * se enviará en la respuesta la traza de la excepción con el id y el
 * mensaje de error.<br>
 * En ambos casos también se logueará con <i>log4j</i> la traza, el id de error y
 * el mensaje como <code>ERROR</code> si es una excepción no esperada, o como
 * <code>DEBUG</code> si es una excepción esperada.
 *
 * @author Mariano Ruiz
 */
public class ExceptionResolver implements HandlerExceptionResolver {

    private static final Log logger = LogFactory.getLog(ExceptionResolver.class);

	@Autowired(required=false)
	protected EnviromentConfiguration enviromentConfiguration;

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
            	String stack = "An error occurred (id " + errorId + ")\n" + ExceptionUtils.getStackTraceAsString(e);
                logger.error(stack);
            	if(showErrorMessageId()) {
            		model.put(JSPView.ERRORS, new FrameworkException(e, "fwk.constant.generic.errorWithId", errorId));
            	} else {
            		model.put(JSPView.ERRORS, stack);
            	}
            }
            return new ModelAndView(getDefaultErrorPage(), model);
        } else {
            return null;
        }
    }

    /**
     * Retorna <code>true</code> si se debe enviar un
     * mensaje de error con id en vez de la traza de error, en
     * caso de que la exception no sea una
     * {@link mr.common.exception.spring.FrameworkException FrameworkException}.<br/>
     * Esta implementación devuelve <code>true</code> si el entorno de
     * ejecución es de producción o pre-producción, para ello invoca a
     * {@link mr.common.context.EnviromentConfiguration},
     * pero el método puede ser sobreescrito por clases hijas para
     * decidir si se debe o no mostrar el mensaje de error con id.<br/>
     * En caso de no estar definido en el contexto el bean {@link mr.common.context.
     * EnviromentConfiguration EnviromentConfiguration}, esta implementación devuelve
     * siempre <code>true</code>.
     */
	protected boolean showErrorMessageId() {
		return enviromentConfiguration==null
		|| enviromentConfiguration.isProductionEnviroment()
		|| enviromentConfiguration.isPreProductionEnviroment();
	}

	/**
	 * Devuelve el nombre de la página donde se renderiza el error.<br/>
	 * Esta implementación devuelve la página configurada con
	 * {@link #setDefaultErrorPage(String)}, y si no fue configurada
	 * por defecto retorna a {@link mr.common.web.spring.view.JSPView
	 * #DEFAULT_VIEW JSPView.DEFAULT_VIEW}, pero puede sobreescribirse
	 * para direccionarse el error a otros JSPs.
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
