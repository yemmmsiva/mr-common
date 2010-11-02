package mr.common.format;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase con métodos encargados de realizar formateo de texto, fechas y números.
 * @author Mariano Ruiz
 */
public class FormatUtils {


    /**
     * Símbolo del EURO.
     */
    public static final String EURO = "\u20AC";

    /**
     * Formato de fecha latinoamericano, ej. <code>01/09/2009</code>.
     */
    public static final String TIME_FORMAT_DDMMYYYY = "dd/MM/yyyy";

    /**
     * Formato de fecha en texto latinoamericano, ej. <code>1 de septiembre de 2009</code>.
     */
    public static final String TIME_FORMAT_DD_DE_MES_DE_YYYY = "d 'de' MMMM 'de' yyyy";

    /**
     * Formato de fecha en formato TIMESTAMP de base de datos, ej. <code>2009-11-23 14:50:55</code>.
     */
    public static final String TIME_FORMAT_TIMESTAMP = "yyyy-MM-dd hh:mm:ss";

    /**
     * Máscara de formato moneda para {@link DecimalFormat}, ej. <code>1.587,80</code>.
     */
    public static final String FORMAT_MONEY = "#,##0.00";

    /**
     * Máscara de formato número entero para {@link DecimalFormat}, ej. <code>1.587.010</code>.
     */
    public static final String FORMAT_INTEGER = "#,##0";

    /**
     * Máscara de formato decimal para {@link DecimalFormat}, ej. <code>1.587,80</code>.
     */
    public static final String FORMAT_DECIMAL = "#,##0.#";

    /**
     * Máscara de formato Euro para {@link DecimalFormat}, ej. <code>1.587,80 €</code>.
     */
    public static final String FORMAT_EURO = FORMAT_MONEY + " " + EURO;


    /**
     * Método que recibe una fecha en formato String y la devuelve en formato Date.
     * @param fecha String
     * @param mascara String: Formato de la fecha a transformar, ej. <code>yyyy-MM-dd</code>
     * @return date
     * @throws ParseException e
     */
    public static Date strToDate(String fecha, String mascara) throws ParseException {

        Date date = null;
        SimpleDateFormat df = new SimpleDateFormat(mascara);
        date = df.parse(fecha);
        return date;
    }

    /**
     * Retorna la misma fecha pero sin la información de la hora (o sea con hora 00:00).
     * @param d Date
     * @return Date
     * @throws ParseException e
     */
    public static Date dateWithoutHour(Date d) throws ParseException {
        SimpleDateFormat sd = new SimpleDateFormat(TIME_FORMAT_DDMMYYYY);
        Date fecha = null;
        fecha = sd.parse(sd.format(d));
        return fecha;
    }

    /**
     * Retorna el porcentaje de num sobre el total, pero redondeado el resultado hasta 2 decimales.
     * @param num double: número
     * @param total double: total sobre el que se calcula el porcentaje
     * @return double: resultado con decimales redondeados hasta el segundo dígito
     */
    public static double percentageRounded(double num, double total) {
        if (num == 0.0 && total == 0.0) {
            return round(0.0, 2);
        }
        final double CIEN_PORCIENTO = 100.0;
        return round(num / total * CIEN_PORCIENTO, 2);
    }

    /**
     * Retorna un número hasta una determinada cantidad de decimales redondeandoló.
     * @param numero double
     * @param decimales double
     * @return double
     */
    public static double round(double numero, int decimales) {
        return (new BigDecimal(numero).setScale(decimales, BigDecimal.ROUND_HALF_DOWN)).doubleValue();
    }

    /**
     * Retorna el porcentaje como string en el formato <code>##.### %</code>.
     * @param n double
     * @param total double
     * @return String
     */
    public static String percentageRoundedAsString(Double n, Double total) {
        DecimalFormat pf = new DecimalFormat("##.### %");
        if (total == 0) {
            return pf.format(0);
        }
        return pf.format(n / total);
    }

    /**
     * Obtiene el/los apellidos y el nombre de una persona en el formato:
     * <ul>
     * <li>"Apellido1 [Apellido2][, Nombre]" -> Si tiene al menos un apellido</li>
     * <li>"Nombre" -> Si tiene al menos un apellido</li>
     * <li>"" -> Si no tiene ni nombre ni apellidos</li>
     * </ul>
     * @param apellido1 String
     * @param apellido2 String
     * @param nombre String
     * @return String
     */
    public static String getApellidoNombre(String apellido1, String apellido2, String nombre) {
        String apellidoNombre = "";
        if (apellido1 != null && apellido1.trim().length() > 0) {
            apellidoNombre += apellido1.trim();
        }
        if (apellido2 != null && apellido2.trim().length() > 0) {
        	if(apellidoNombre.length() > 0) {
        		apellidoNombre += " ";
        	}
            apellidoNombre += apellido2.trim();
        }
        if (apellidoNombre.trim().length() > 0) {
            apellidoNombre += ", ";
        }
        if (nombre != null && nombre.trim().length() > 0) {
            apellidoNombre += nombre.trim();
        }
        return apellidoNombre;
    }


	private static String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
		"Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }; 

	/**
	 * Retorna el nombre del mes (en ES, sin internacionalización).
	 * @param numMes int: número de mes (1 a 12)
	 * @return String
	 * TODO Internacionalizar
	 */
	public static String mesAsSting(int numMes) {
		return meses[numMes-1];
	}

	/**
	 * Convierte un número binario de cualquier longitud de bytes
	 * a una cadena hexadecimal.
	 * @param data: array de bytes
	 * @return String
	 */
    public static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }

	/**
	 * Convierte caracteres básicos con diacríticos o no ASCII a sus caracteres "limpios"
	 * equivalentes. Útil para cuando se quiere que sean indistintos con o sin
	 * tilde. De modo que:
	 * <p>
	 * <code>Á</code> => <code>A</code>
	 * <br>
	 * <code>ü</code> => <code>u</code>
	 * </p>
	 * Caracteres que no tengan representación en ASCII serán eliminados, ej. el EURO: €
	 * @param s String: cadena a limpiar
	 * @return String: cadena "limpia"
	 */
	public static String normalize(String string){
		string = Normalizer.normalize(string, Normalizer.Form.NFKD);
		return string.replaceAll("[^\\p{ASCII}]","");
	}
}
