package mr.common.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import org.xml.sax.InputSource;


/**
 * Métodos útiles con streams de datos.
 * @author Mariano Ruiz
 */
public abstract class StreamUtils {

	/**
	 * Obtiene los datos de <code>input</code> como un array de bytes.<br/>
	 * No olvidar cerrar la conexión de <code>input</code>.
	 * @param input {@link java.io.InputStream}
	 * @return array de bytes
	 * @throws IOException
	 */
	public static byte[] getBytes(InputStream input) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		copy(input, result);
		input.close();
		return result.toByteArray();
	}

	/**
	 * Devuelve un {@link org.xml.sax.InputSource} a partir
	 * del string pasado.
	 */
	public static InputSource getInputSource(String text) {
		return new InputSource(new StringReader(text));
	}

	/**
	 * Copia todo el contenido desde <code>input</code> hasta <code>output</code>.
	 * @param input {@link java.io.InputStream}
	 * @param output {@link java.io.OutputStream}
	 * @throws IOException
	 */
	public static void copy(InputStream input, OutputStream output) throws IOException {
		byte[] buf = new byte[4096];
		int bytesRead = input.read(buf);
		while (bytesRead != -1) {
			output.write(buf, 0, bytesRead);
			bytesRead = input.read(buf);
		}
		output.flush();
	}
}
