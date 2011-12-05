package mr.common.io.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mr.common.format.FormatUtils;
import mr.common.io.StreamUtils;


public class FileUtils {

	/**
	 * Devuelve el archivo como un String.
	 * @throws IOException
	 * @see #read(File, String)
	 */
    public static String read(File file) throws IOException {
        return read(file, null);
    }

    /**
     * Devuelve el archivo como un String. Lo interpreta bajo con el charset pasado.
     * @throws IOException
     */
    public static String read(File file, String charsetName) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        write(new FileInputStream(file), output);
        return charsetName == null ? output.toString() : output.toString(charsetName);        
    }

    /**
     * Devuelve el contenido de un {@link InputStream} como un String.
     * @throws IOException
     * @see #read(InputStream, String)
     */
    public static String read(InputStream stream) throws IOException {
        return read(stream, null);
    }

    /**
     * Devuelve el contenido de un {@link InputStream} como un String.
     * Lo interpreta bajo con el charset pasado.
     * @throws IOException
     */
    public static String read(InputStream stream, String charsetName) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        write(stream, output);
        return charsetName == null ? output.toString() : output.toString(charsetName);        
    }

    /**
     * Escribe lo que hay en el <code>inputStream</code> en el <code>outputStream</code>.
     * @throws IOException
     */
    public static void write(InputStream inputStream, OutputStream outputStream) throws IOException {
        StreamUtils.copy(inputStream, outputStream);
    }

    /**
     * @param pathOrURL path del archivo o URL del recurso.
     * @return última extensión del recurso, en minúsculas.
     */
    public static String getExtension(String pathOrURL) {
        int dotPlace = pathOrURL.lastIndexOf('.');
        return (dotPlace > 0 ? pathOrURL.substring(dotPlace) : "").toLowerCase();
    }

    /**
     * Borra el archivo, o el directorio con todo su contenido sin ningún tipo de confirmación.
     */
    public static void deleteFileOrDirectoryWithContent(File path) {
        if(path.exists()) {
        	if (path.isDirectory()) {
	            for (File file : path.listFiles()) {
	                if (file.isDirectory()) {
	                	deleteFileOrDirectoryWithContent(file);
	                } else {
	                	file.delete();
	                }
	            }
	            path.delete();
        	} else {
        		path.delete();
        	}
        }
    }

    /**
     * Normaliza el nombre de un fichero o carpeta, reemplazando los caracteres no compatibles
     * con todos los sistemas de archivo por el caracter "<code>_</code>".<br/>
     * También por cuestiones de compatibilidad, recorta el nombre a no más de 30 caracteres,
     * y quita todo lo que hay después de el primer punto inclusibe, por lo que se debe
     * usar para normalizar el nombre sin la extensión.
     * <p><b>IMPORTANTE</b>: Requiere Java 1.6+.</p>
     */
    public static String normalizeFileName(String clientFileName) {
    	String name = clientFileName.contains(".") ? clientFileName.substring(0, clientFileName.lastIndexOf('.')) : clientFileName;
    	name = FormatUtils.normalize(name);
		name = name.replaceAll("[\\@#$%&/()\\s¿?¡!-\\.,;\\:\\[\\]\\{\\}<>'^\\*]", "_");
		name = name.substring(0,Math.min(name.length(), 30));
		return name;
    }

    /**
     * Transforma el nombre de un archivo a otro con compatibilidad para cualquier
     * sistema de archivos, y le agrega una número aleatorio antes de la extensión
     * para hacerlo único en caso de subirse más de un archivo.
     * @see #normalizeFileName(String)
     */
    public static String getUploadFileName(String clientFileName) {
    	String name = normalizeFileName(clientFileName);
    	String ext = getExtension(clientFileName);
		return (name+"_"+System.currentTimeMillis() + ext).toLowerCase();
    }
    
    /**
	 * Normaliza los finales de línea de un String obtenido de un archivo,
	 * si el archivo tiene retornos de carro al estilo MS-DOS, los cambia por
	 * el end of line al estilo Unix.
	 */
    public static String normalizeEndOfLine(String file) {
    	if (file == null) {
    		return null;
    	}
    	return file.replaceAll("\r\n", "\n").replaceAll("\r", "\n");
    }
}
