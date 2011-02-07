package mr.common.io.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Objecto que permite obtener el contenido de una URL como String.
 * @author Mariano Ruiz
 */
public class URLConnectionContent {

	private URL url;

	/**
	 * @see mr.common.io.net.URLConnectionContent
	 * @param fullURL String
	 * @throws MalformedURLException
	 */
	public URLConnectionContent(final String fullURL) throws MalformedURLException {
		url = new URL(fullURL);
	}

	/**
	 * @see mr.common.io.net.URLConnectionContent
	 * @param fullURL {@link java.net.URL}
	 */
	public URLConnectionContent(final URL fullURL) {
		url = fullURL;
	}

	/**
	 * @return String - El contenido de la URL
	 * @throws java.io.IOException
	 */
	public String getURLContentAsString() throws IOException {
		String value;
		Reader reader = null;
		Writer writer = null;
		try {
			URLConnection connection =  url.openConnection();
			String charset = connection.getContentType();
			if (charset != null) {
				charset = charset.replaceAll("[^;]*; *charset *= *([^ ;]*) *($|;).*$", "$1");
			}
			InputStream contentStream = (InputStream) connection.getContent();
			try {
				reader = new BufferedReader(new InputStreamReader(contentStream, charset));
			} catch(UnsupportedEncodingException e) {
				reader = new BufferedReader(new InputStreamReader(contentStream));				
			}
			char[] data = new char[contentStream.available()];
			writer = new StringWriter(data.length);
			int chars;
			while ((chars = reader.read(data)) > 0) {
				writer.write(data, 0, chars);
			}
			value = writer.toString();
		} catch (IOException e) {
			throw e;
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					throw e;
				}
			}
			if (null != writer) {
				try {
					writer.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		return value;
	}
}
