package mr.common.i18n.spring;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.MessageSourceSupport;
import org.springframework.stereotype.Component;


/**
 * Extrae los mensajes internacionalizados, y el locale
 * de la aplicación o hilo de ejecución.
 *
 * @author Mariano Ruiz
 */
@Component
public class MessageUtils {

    @Resource(name = "messageSource")
    private MessageSourceSupport source;
    private static MessageSourceSupport messageSource;

    @Resource(name = "localesSupported")
    private List<String> locales = null;
    private static String[] localesSupported = null;

    @PostConstruct
    public void initialize() {
        messageSource = source;
        localesSupported = locales.toArray(new String[0]);
    }


    public static AbstractMessageSource getMessageSource() {
        return (AbstractMessageSource) messageSource;
    }

    public void setSource(MessageSourceSupport source) {
        this.source = source;
    }

    /**
     * Retorna el locale del hilo de ejecución o de la aplicación. En
     * caso de que el contexto (ejemplo web) requiera una localización
     * no soportada por la aplicación (ver {@link
     * #isLocadeSupported(String)} y {@link #getLocalesSupported()}),
     * retornará la localización de la ejecución de la VM.
     */
    public static Locale getLocale() {
    	Locale locale = LocaleContextHolder.getLocale();
    	if(isLocadeSupported(locale.getLanguage())) {
    		return locale;
    	}
    	return Locale.getDefault();
    }

    /**
     * Retorna un objeto {@link java.util.Locale Locale} con la
     * internacionalización del <i>ISO-639</i> pasada como parámetro.
     * Si esa localización no es soportada por la aplicación (ver
     * {@link #isLocadeSupported(String)}), se devuelve el locale
     * del hilo de ejecución o de la aplicación. 
     * @param locale código <i>ISO-639</i>.
     * @return objeto locale.
     */
    public static Locale getLocale(String locale) {
    	if(locale==null || localesSupported==null || !isLocadeSupported(locale)) {
        	return LocaleContextHolder.getLocale();
    	}
    	return new Locale(locale);
    }

    /**
     * Indica si una localización es soportada por la aplicación.
     * @param locale la localización que se quiere evaluar.
     * @return <code>true</code> si la localización es
     * soportada.
     * @see #getLocalesSupported()
     */
    public static boolean isLocadeSupported(String locale) {
		if(locale==null) {
			throw new NullPointerException("locale = null.");
		}
		if(localesSupported==null) {
			throw new RuntimeException(
					"MessageUtils.localesSupported not configured.");
		}
		for(int i=0; i<localesSupported.length; i++) {
			if(localesSupported[i].equals(locale)) {
				return true;
			}
		}
		return false;
	}

    /**
     * Retorna un array de strings con todas las localizaciones
     * soportadas por la aplicación.
     * @return array de strings.
     * @see #isLocadeSupported(String)
     */
    public static String[] getLocalesSupported() {
    	return localesSupported;
    }

	/**
     * Devuelve el mensaje internacionalizado de la <code>key</code>
     * en la localización del hilo actual.
     * @return mensaje traducido.
     */
    public static String getMessage(String key) {
    	return getMessageSource().getMessage(key, null, getLocale());
    }


    /**
     * Devuelve el mensaje internacionalizado de la <code>key</code>
     * en la localización del hilo actual, y con la parametrización
     * <code>args</code>.
     * @return mensaje traducido.
     */
    public static String getMessage(String key, Object ... args) {
    	return getMessageSource().getMessage(key, args, getLocale());
    }
}
