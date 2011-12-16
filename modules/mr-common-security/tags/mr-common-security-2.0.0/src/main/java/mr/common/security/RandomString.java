package mr.common.security;


/**
 * Interfaz útil para crear clases que generen strings aleatorios,
 * ejemplo para generar passwords, nombres de archivos temporales, etc,
 * y son reutilizados por otros objetos o servicios.
 *
 * @author Mariano Ruiz
 */
public interface RandomString {

	/**
	 * String aleatorio.
	 * @return cadena de texto.
	 */
	String nextString();
}
