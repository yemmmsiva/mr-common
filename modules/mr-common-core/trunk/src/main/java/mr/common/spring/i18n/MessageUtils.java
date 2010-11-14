package mr.common.spring.i18n;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.MessageSourceSupport;
import org.springframework.stereotype.Component;


/**
 * Extrae los mensajes internacionalizados.
 */
@Component
public class MessageUtils {

    public static Locale DEFAULT_LOCALE = new Locale("en");

    @Resource(name = "messageSource")
    private MessageSourceSupport source;

    static MessageSourceSupport messageSource;

    @PostConstruct
    public void initialize() {
        messageSource = source;
    }

    public static AbstractMessageSource getMessageSource() {
        return (AbstractMessageSource) messageSource;
    }

    public void setSource(MessageSourceSupport source) {
        this.source = source;
    }

    public static Locale getLocale() {
    	return LocaleContextHolder.getLocale();
    }
}
