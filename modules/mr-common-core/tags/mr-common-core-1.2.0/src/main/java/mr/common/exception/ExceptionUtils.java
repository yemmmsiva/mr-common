package mr.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * Métodos útiles para el manejo de excepciones.
 * @author Mariano Ruiz
 */
public class ExceptionUtils {

    /**
    * Obtiene el stack trace de la excepción como string.
    * @param exception Throwable
    * @return String
    */
    public static String getStackTraceAsString(Throwable exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.print(" [ ");
        pw.print(exception.getClass().getName());
        pw.print(" ] ");
        pw.print(exception.getMessage());
        exception.printStackTrace(pw);
        return sw.toString();
    }
}
