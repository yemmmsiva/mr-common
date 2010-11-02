package mr.common.format;

import java.text.Format;


/**
 * Wrapper de la clase {@link java.lang.Format Format}, que permite además configurar un String
 * como valor de retorno cuando el objeto pasado es nulo. Si no se setea el valor por defecto,
 * el formateador develoverá "".
 * @see java.text.Format
 * @author mruiz
 */
public class FormatWrapper {

	private Format format;
	private String nullSymbol = "";


	/**
	 * @see com.livra.madmax.util.FormatWrapper
	 * @param format Format
	 */
	public FormatWrapper(Format format) {
		this.format = format;
	}

	/**
	 * @see com.livra.madmax.util.FormatWrapper
	 * @param format Format
	 * @param nullSymbol String: valor por default para objetos nulos.
	 */
	public FormatWrapper(Format format, String nullSymbol) {
		this.format = format;
		this.nullSymbol = nullSymbol;
	}

	/**
	 * Invoca a {@link java.lang.Format#format(Object)} si el objecto
	 * no es nulo, sino devuelve el valor de {@link #getNullSymbol}.
	 * @param obj
	 * @return
	 */
	public String format(Object obj) {
		if (obj == null) {
			return getNullSymbol();
		}
		return getFormat().format(obj);
	}

	/**
	 * Objecto {@link java.lang.Format} usado para formatear.
	 */
	public Format getFormat() {
		return format;
	}
	public void setFormat(Format format) {
		this.format = format;
	}

	/**
	 * Valor por defecto si el objecto a formatear es nulo.
	 * @return String
	 */
	public String getNullSymbol() {
		return nullSymbol;
	}
	public void setNullSymbol(String nullSymbol) {
		this.nullSymbol = nullSymbol;
	}
}
