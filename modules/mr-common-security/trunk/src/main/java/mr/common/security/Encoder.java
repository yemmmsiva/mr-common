package mr.common.security;


/**
 * Clase base útil para crear objetos que codifican - decodifican
 * texto, y son reutilizados por otros objetos o servicios.
 *
 * @author Mariano Ruiz
 */
public abstract class Encoder {

	/**
	 * Codifica el texto.
	 * @param text texto a codificar
	 * @return texto codificado
	 */
	public abstract String encode(String text);

	/**
	 * Decodifica el texto.
	 * @param encodedText texto a decodificar
	 * @return texto decodficado
	 * @throws UnsupportedOperationException si
	 * la operación no es soportada por la implementación,
	 * por ejemplo si {@link #isDecodable()} retorna
	 * <code>false</code>, o por ejemplo si
	 * se usa un algoritmo de hashing para codificar
	 */
	public String decode(String encodedText) {
		throw new UnsupportedOperationException(
			"The `decode` operation is not supported.");
	}

	/**
	 * Retorna <code>true</code> si <code>text</code>
	 * al codificarse retorna <code>encodedText</code>.
	 * @param encodedText texto codificado
	 * @param text texto sin codificar
	 * @return <code>true</code> si ambos string
	 * matchean
	 */
	public boolean match(String encodedText, String text) {
		return encode(text).equals(encodedText);
	}

	public abstract boolean isDecodable();
}
