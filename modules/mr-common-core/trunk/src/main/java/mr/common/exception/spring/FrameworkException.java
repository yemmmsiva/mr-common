package mr.common.exception.spring;

import mr.common.i18n.spring.MessageUtils;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.AbstractMessageSource;


/**
 * Excepción que recibe el mensaje de error a través de su clave i18n y lo traduce
 * según la localización.
 */
public class FrameworkException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String messageKey;
	private Object[] messageArgs;


	/**
	 * @see java.lang.RuntimeException#RuntimeException()
	 */
	protected FrameworkException() {
        super();
    }

	/**
	 * Excepción con mensaje internacionalizado.
	 * @param messageKey String: clave i18n del error
	 * @param messageArgs Object... Argumentos del mensaje
	 * @see java.lang.RuntimeException#RuntimeException(String)
	 */
    public FrameworkException(String messageKey, Object... messageArgs) {
        super(resolveMessage(messageKey, messageArgs));
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
    }

    /**
     * @see java.lang.RuntimeException#RuntimeException(Throwable)
     */
    public FrameworkException(Throwable cause) {
        super(cause);
    }

    /**
     * Excepción con mensaje internacionalizado y excepción que le dio origen.
     * @param cause Throwable: Excepción origen
     * @param messageKey String Mensaje i18n del error
     * @param messageArgs Object... Argumentos del error
     * @see java.lang.RuntimeException#RuntimeException(String, Throwable)
     */
    public FrameworkException(Throwable cause, String messageKey, Object... messageArgs) {
        super(resolveMessage(messageKey, messageArgs), cause);
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
    }


	public String getMessageKey() {
		return messageKey;
	}
	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}
	public Object[] getMessageArgs() {
		return messageArgs;
	}
	public void setMessageArgs(Object[] messageArgs) {
		this.messageArgs = messageArgs;
	}


	    private static String resolveMessage(String messageKey, Object[] messageArgs) {
	        AbstractMessageSource ms = MessageUtils.getMessageSource();
	        String resolved = null;
	        if (ms != null) {
	            try {
	                resolved = ms.getMessage(messageKey, messageArgs, MessageUtils.getLocale());
	            } catch (NoSuchMessageException e) {
	                resolved = copyArgs(messageKey, messageArgs);
	            }
	        } else {
	            resolved = copyArgs(messageKey, messageArgs);
	        }
	
	        return resolved;
	    }
	
	    private static String copyArgs(String messageKey, Object[] messageArgs) {
	        String resolved = messageKey + (messageArgs.length > 0 ? ":" : "");
	        for (Object arg : messageArgs) {
	            resolved = resolved + ", " + arg;
	        }
	        return resolved;
	    }
}
