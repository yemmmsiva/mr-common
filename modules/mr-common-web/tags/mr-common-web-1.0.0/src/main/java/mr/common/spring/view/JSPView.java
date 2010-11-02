package mr.common.spring.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mr.common.exception.ExceptionUtils;

import org.springframework.web.servlet.view.JstlView;


/**
 * Vista que intercepta la respuesta y añade el objecto
 * del contexto de Spring definido como <code>appProperties</code>.<br/>
 * Si este objecto es una instancia de {@link java.util.Properties},
 * podrán ser accedidas sus propiedades con la notación
 * para acceso a beans de JSP. Ej: <code>${nombrePropiedad.id}</code><br/>
 * Si se lanzara una excepción desde la JSP, devolverá como respuesta
 * <code>application/error</code> como contentType al cliente, con la
 * cadena <code>{success:false}</code> por si es capturado por algún
 * objeto en el navegador que halla echo la petición por Ajax.
 */
@SuppressWarnings("unchecked")
public class JSPView extends JstlView {

	public static final String DEFAULT_VIEW = "default";
	public static final String ERRORS = "errors";
	public static final String JSON_ERROR = "{success:false}";

    private static final String APP_PROPERTIES = "appProperties";


    @SuppressWarnings("rawtypes")
	@Override
    protected void renderMergedOutputModel(Map model, HttpServletRequest req,
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
     * Introduce todas las variables necesarias en el modelo. Todas irán
     * bajo el nombre "fwk".
     * 
     * @param model Map
     */
    @SuppressWarnings("rawtypes")
	protected void addVariables(Map model) {
        // introducimos el valor de success true/false (necesario para ExtJS)

    	Object appProperties = getAttributesMap().get(APP_PROPERTIES);
        if (appProperties != null) {
            model.put(APP_PROPERTIES, appProperties);
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uuid", UUID.randomUUID().toString());
        model.put("fwk", params);

        Throwable ex = null;
        if(model.containsKey(ERRORS)) {
        	ex = (Throwable) model.get(ERRORS);
        	logger.debug(ExceptionUtils.getStackTraceAsString(ex));
        	List<String> errors = new ArrayList<String>(0);
        	errors.add(ex.getMessage());
        	params.put("fwkExceptions", errors);
        	params.put("success", Boolean.FALSE);
            model.put("__success", Boolean.FALSE);
        } else {
            model.put("__success", Boolean.TRUE);
        	params.put("success", Boolean.TRUE);
        }
    }

    protected void error(HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType("application/error");
        response.getOutputStream().println(JSON_ERROR);
        response.flushBuffer();
    }
}
