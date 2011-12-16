package mr.common.web.spring.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mr.common.exception.ExceptionUtils;
import mr.common.i18n.spring.MessageUtils;

import org.springframework.context.NoSuchMessageException;
import org.springframework.web.servlet.view.JstlView;


/**
 * Vista que extiende de {@link org.springframework.web.servlet.view.JstlView
 * JstlView}. Intercepta la respuesta y añade el objecto
 * del contexto de Spring definido como <code>appProperties</code>.<br/>
 * Si este objecto es una instancia de {@link java.util.Properties},
 * podrán ser accedidas sus propiedades con la notación
 * para acceso a beans de JSP. Ej: <code>${nombrePropiedad.id}</code><br/>
 * Si se lanzara una excepción desde la JSP, devolverá como respuesta
 * <code>application/error</code> como contentType al cliente, con la
 * cadena <code>{success:false}</code> por si es capturado por algún
 * objeto en el navegador que halla echo la petición por Ajax.
 */
public class JSPView extends JstlView {

	/**
	 * <i>`default`</i>: Página por default para redirigir respuestas.
	 */
	public static final String DEFAULT_VIEW = "default";
	public static final String ERRORS = "errors";
	public static final String JSON_ERROR = "{success:false}";

    private static final String APP_PROPERTIES = "appProperties";


	@Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest req,
                                                      HttpServletResponse resp) throws Exception {

        try {
            addVariables(model);
            super.renderMergedOutputModel(model, req, resp);
        } catch (Exception e) {
            logger.error(e);
            error(resp);
        }
    }

    /**
     * Introduce todas las variables necesarias en el modelo.<br/>
     * Si hubiera una variable <i>'appProperties'</i> accesible en
     * {@link #getAttributesMap()}, la coloca en el modelo
     * para que sea accesible desde el JSP.<br/>
     * Bajo el nombre <i>'response'</i> carga los siguientes valores
     * como un mapa:
     * <ul>
     *   <li><code>success</code>: con valor <code>true</code>
     *   si no errores u excepciones en el modelo.</li>
     *   <li><code>exceptions</code>: lista de mensajes
     *   de error de validaciones u excepciones que hubiera
     *   en el modelo.</li>
     * </ul>
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addVariables(Map model) {
        // introducimos el valor 'success' true/false, según se halla lanzado
    	// una excepción o no.

    	Object appProperties = getAttributesMap().get(APP_PROPERTIES);
        if (appProperties != null) {
            model.put(APP_PROPERTIES, appProperties);
        }

        Map<String, Object> params = new HashMap<String, Object>();
        model.put("response", params);

        if(model.containsKey(ERRORS)) {
        	List<String> errors = new ArrayList<String>();
        	Object error = model.get(ERRORS);
        	if(error instanceof Object[]) {
        		Object[] errorsArray = (Object[]) error;
        		for(Object e : errorsArray) {
                	errors.add(getErrorMessage(e));
        		}
        	} else if(error instanceof Collection) {
        		Collection errorsList = (Collection) error;
        		for(Object e : errorsList) {
        			errors.add(getErrorMessage(e));
        		}
        	} else if(error instanceof Throwable) {
        		errors.add(getErrorMessage(error));
        	} else {
        		// El error es una key i18n, o en caso de que no lo sea,
        		// el resolver al no encontrar la key devolverá el string
        		// tal cual
        		String message;
        		if(error instanceof String) {
            		try {
            			message = MessageUtils.getMessage((String)error);
            		} catch(NoSuchMessageException e) {
            			message = (String)error;
            		}
        		} else {
        			message = error.toString();
        		}
        		errors.add(message);
        	}
        	params.put("exceptions", errors);
        	params.put("success", Boolean.FALSE);
        } else {
        	params.put("success", Boolean.TRUE);
        }
    }

    private String getErrorMessage(Object e) {
    	String message;
    	if(e instanceof Throwable) {
			Throwable ex = (Throwable) e;
			message = ex.getMessage();
			if(message==null) {
				message = ExceptionUtils.getStackTraceAsString(ex);
			}
    	} else {
    		message = e.toString();
    	}
		return message;
    }

    protected void error(HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType("application/error");
        response.getOutputStream().println(JSON_ERROR);
        response.flushBuffer();
    }
}
