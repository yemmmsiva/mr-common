package mr.common.web.spring.resolver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mr.common.context.EnvironmentConfiguration;
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
 * se envía un mensaje internacionalizado con la key devuelta por
 * {@link #getErrorWithIdKey()} con un número de error.
 * En caso de que el método devuelva <code>false</code>,
 * se enviará en la respuesta la traza de la excepción con el id y el
 * mensaje de error.<br>
 * En ambos casos también se logueará con <i>Apache Logging</i> la traza, el id de error y
 * el mensaje como <code>ERROR</code> si es una excepción no esperada, o como
 * <code>DEBUG</code> si es una excepción esperada.
 *
 * @author Mariano Ruiz
 */
public class ExceptionResolver implements HandlerExceptionResolver {

    private static final Log logger = LogFactory.getLog(ExceptionResolver.class);

	@Autowired(required=false)
	protected EnvironmentConfiguration environmentConfiguration;

	private String errorPage = JSPView.DEFAULT_VIEW;

	private String errorWithIdKey = "errorWithId";


    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
    		                             Object obj, Exception e) {
        if (e != null) {
            Map model = new HashMap();
            if(e instanceof FrameworkException) {
            	logger.debug(ExceptionUtils.getStackTraceAsString(e));
            	model.put(JSPView.ERRORS, e);
            } else {
            	String errorId = getErrorId(request, e);
            	String stack = getStackTrace(request, e, errorId);
                logger.error(stack);
            	if(showErrorMessageId()) {
            		model.put(JSPView.ERRORS, new FrameworkException(e, getErrorWithIdKey(), errorId));
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
     * Código de error que se le mostrará al usuario si ocurre un error inesperado
     * y que se logueará con <i>Apache Logging</i>.<br/>
     * Esta implementación genera un código numérico entero aleatorio, pero
     * puede ser sobreescrito para obtener el dato desde un servicio, algún
     * parámetro de request, etc.
     *
     * @param request objeto request de la petición que generó el error.
     * @param e el error.
     */
    protected String getErrorId(HttpServletRequest request, Exception e) {
    	return new Integer(RandomUtils.nextInt()).toString();
    }

    /**
     * Stack trace de la excepción que se imprimirá en el <i>Apache Logging</i>, y
     * que puede ser mostrado al usuario dependiendo de la configuración
     * de entorno.<br/>
     * Esta implementación retorna el mensaje '<i>An error occurred (id <code>errorId</code>)</i>',
     * junto con los datos de sesión obtenidos de {@link
     * #getSessionInfo(HttpServletRequest)}, y el stack trace propio
     * de la excepción.
     *
     * @param request objeto request de la petición que generó el error
     * @param e el error.
     * @param errorId el id de error obtenido de {@link
     * #getErrorId(HttpServletRequest, Exception)}.
     */
    protected String getStackTrace(HttpServletRequest request, Exception e, String errorId) {
    	return "\nAn error occurred (id " + errorId + ")\n"
    	      + getSessionInfo(request) + "\nStack trace:\n"
    	      + ExceptionUtils.getStackTraceAsString(e) + "\n";
    }

    /**
     * Información que se le agrega al log de la exception no esperada.<br/>
     * Esta implementación genera una cadena con la información de la URL,
     * los parámetros, y el username del usuario si es que hubiera uno
     * autenticado en el hilo de ejecución.<br/>
     * Puede ser sobreescrito para agregar o cambiar la información.
     * @param request objeto request de la petición que generó el error.
     */
    @SuppressWarnings("rawtypes")
	protected String getSessionInfo(HttpServletRequest request) {
    	String sessionInfo = "Request URL: " + request.getRequestURL();
    	Map params = request.getParameterMap();
    	if(params!=null & !params.isEmpty()) {
    		sessionInfo += "\nRequest parameters: " + getParametersAsString(params);
    	}
    	if(request.getUserPrincipal()!=null
    			&& request.getUserPrincipal().getName() != null) {
    		sessionInfo += "\nUsername: " + request.getUserPrincipal().getName();
    	}
		return sessionInfo;
	}

	@SuppressWarnings("rawtypes")
	private String getParametersAsString(Map params) {
		String parametersAsString = "";
		Iterator keysIterator = params.keySet().iterator();
		while(keysIterator.hasNext()) {
			String key = (String) keysIterator.next();
			Object value = params.get(key);
			parametersAsString += key + "=";
			if(value instanceof Object[]) {
				Object[] valueArray = (Object[]) value;
				if(valueArray.length == 1) {
					parametersAsString += valueArray[0].toString();
				} else {
					parametersAsString += getParametersAsString(valueArray);
				}
			}
			if(keysIterator.hasNext()) {
				parametersAsString += ";";
			}
		}
		return parametersAsString;
	}

	private String getParametersAsString(Object[] params) {
		String parametersAsString = "[";
		for(int i=0; i<params.length; i++) {
			parametersAsString += params[i];
			if(i!=params.length-1) {
				parametersAsString += ";";
			}
		}
		return parametersAsString;
	}

	/**
     * Retorna <code>true</code> si se debe enviar un
     * mensaje de error con id en vez de la traza de error, en
     * caso de que la exception no sea una
     * {@link mr.common.exception.spring.FrameworkException FrameworkException}.<br/>
     * Esta implementación devuelve <code>true</code> si el entorno de
     * ejecución es de producción o pre-producción, para ello invoca a
     * {@link mr.common.context.EnvironmentConfiguration EnvironmentConfiguration},
     * pero el método puede ser sobreescrito por clases hijas para
     * decidir si se debe o no mostrar el mensaje de error con id.<br/>
     * En caso de no estar definido en el contexto el bean
     * {@link mr.common.context.EnvironmentConfiguration EnvironmentConfiguration},
     * esta implementación devuelve siempre <code>true</code>.
     */
	protected boolean showErrorMessageId() {
		return environmentConfiguration==null
		|| environmentConfiguration.isProductionEnvironment()
		|| environmentConfiguration.isPreProductionEnvironment();
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
	 */
	public void setDefaultErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	/**
	 * key i18n con el mensaje de error con id. Por default
	 * devuelve la key <code><i>errorWithId</i></code>, pero
	 * puede ser cambiado por
	 * {@link #setErrorWithIdKey(String)}.<br/>
	 * El mensaje debe ser algo así:
	 * <i>An error occurred (id <code>{0}</code>)</i>.
	 */
	public String getErrorWithIdKey() {
		return errorWithIdKey ; 
	}

	/**
	 * Setea la key i18n con el mensaje de error con id.
	 * Por default <code><i>errorWithId</i></code>.<br/>
	 * El mensaje debe ser algo así:
	 * <i>An error occurred (id <code>{0}</code>)</i>.
	 */
	public void setErrorWithIdKey(String key) {
		this.errorWithIdKey = key;
	}
}
