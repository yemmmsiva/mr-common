package mr.common.format;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.springframework.util.StringUtils;


/**
 * Clase con métodos encargados de realizar formateo de texto y números.
 * @author Mariano Ruiz
 */
public class FormatUtils {


    /**
     * Símbolo del EURO.
     */
    public static final String EURO = "\u20AC";

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
     * Retorna el porcentaje de num sobre el total, pero redondeado el resultado hasta 2 decimales.
     * @param num double: número.
     * @param total total sobre el que se calcula el porcentaje.
     * @return resultado con decimales redondeados hasta el segundo dígito.
     */
    public static double percentageRounded(double num, double total) {
        if (num == 0.0 && total == 0.0) {
            return round(0.0, 2);
        }
        return round(num / total * 100.0, 2);
    }

    /**
     * Retorna un número hasta una determinada cantidad de decimales redondeandoló
     * con el método {@link java.math.BigDecimal#ROUND_HALF_DOWN ROUND_HALF_DOWN}.
     */
    public static double round(double num, int scale) {
        return (new BigDecimal(num).setScale(scale, BigDecimal.ROUND_HALF_DOWN)).doubleValue();
    }

    /**
     * Retorna el porcentaje como string en el formato <code>##.### %</code>.
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
     *   <li>'lastName1 [lastName2][, firstName]' -> Si tiene al menos un apellido</li>
     *   <li>'fisrtName' -> Si no tiene apellidos</li>
     *   <li>'' -> Si no tiene ni nombre ni apellidos</li>
     * </ul>
     */
    public static String getFullName(String lastName1, String lastName2, String firstName) {
        String fullName = "";
        if (StringUtils.hasText(lastName1)) {
            fullName += lastName1.trim();
        }
        if (StringUtils.hasText(lastName2)) {
        	if(fullName.length() > 0) {
        		fullName += " ";
        	}
            fullName += lastName2.trim();
        }
        if (fullName.trim().length() > 0) {
            fullName += ", ";
        }
        if (StringUtils.hasText(firstName)) {
            fullName += firstName.trim();
        }
        return fullName;
    }

	/**
	 * Convierte un número binario de cualquier longitud de bytes
	 * a una cadena hexadecimal.
	 * @param data array de bytes.
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
	 * <p>Caracteres que no tengan representación en ASCII serán eliminados, ej. el EURO: €</p>
	 * <p><b>IMPORTANTE</b>: Requiere Java 1.6+.</p>
	 * @param string cadena a limpiar.
	 * @return cadena "limpia".
	 */
	public static String normalize(String string) {
		string = java.text.Normalizer.normalize(string, java.text.Normalizer.Form.NFKD);
		return string.replaceAll("[^\\p{ASCII}]", "");
	}
}
