package mr.common.web.spring.binding;

import java.beans.PropertyEditor;
import java.util.Calendar;
import java.util.Date;

import mr.common.web.spring.binding.converter.propertyeditors.LocalizedCalendarEditor;
import mr.common.web.spring.binding.converter.propertyeditors.LocalizedDateEditor;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;


/**
 * Binder initializer que implementa {@link org.springframework.web.bind.support.WebBindingInitializer
 * WebBindingInitializer}, y agrega el binding para fechas internacionalizado.
 *
 * @author Mariano Ruiz
 */
public class BindingInitializer implements WebBindingInitializer {

	private PropertyEditor dateEditor;
	private PropertyEditor calendarEditor;

	public BindingInitializer() {
		dateEditor = new LocalizedDateEditor();
		calendarEditor = new LocalizedCalendarEditor();
	}

	public void initBinder(WebDataBinder webdatabinder, WebRequest webrequest) {
		webdatabinder.registerCustomEditor(Date.class, getDateEditor());
		webdatabinder.registerCustomEditor(Calendar.class, getCalendarEditor());
	}

	/**
	 * Retorna un {@link java.beans.PropertyEditor} para
	 * el binding de objetos {@link java.util.Date}.<br/>
	 * Esta implementación devuelve un
	 * {@link mr.common.web.spring.binding.converter.propertyeditors.LocalizedDateEditor
	 * LocalizedDateEditor}, pero puede ser sobreescrita para
	 * devolver un editor ajustado a las necesidades. 
	 */
	public PropertyEditor getDateEditor() {
		return dateEditor;
	}

	/**
	 * Retorna un {@link java.beans.PropertyEditor} para
	 * el binding de objetos {@link java.util.Calendar}.<br/>
	 * Esta implementación devuelve un
	 * {@link mr.common.web.spring.binding.converter.propertyeditors.LocalizedCalendarEditor
	 * LocalizedCalendarEditor}, pero puede ser sobreescrita para
	 * devolver un editor ajustado a las necesidades. 
	 */
	public PropertyEditor getCalendarEditor() {
		return calendarEditor;
	}
}
