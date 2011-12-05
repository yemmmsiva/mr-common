package mr.common.format.validator;

import java.io.Serializable;


/**
 * Clase útil para implementar validadores que son compartidos
 * por otros objetos o servicios.<br/>
 * El método {@link #isValid(Object)} recibe
 * el objeto y retorna <code>true</code> en caso
 * de pasar la validación correctamente.<br/>
 * También se puede usar {@link #valide(Object)}, que
 * lanzará una excepción si el objeto no supera la
 * validación.<br/>
 * La excepción es lanzada por el método {@link #throwError(Object)},
 * que debe ser implementado. Esto permite reutilizar la lógica
 * de validación, lanzando distintos tipos de error según
 * la implementación final.
 *
 * @author Mariano Ruiz
 */
public abstract class Validator implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Valida el objeto.
	 * @param obj objeto a validar.
	 * @return <code>true</code> si el
	 * objeto supera la validación.
	 */
	abstract public boolean isValid(Object obj);

	/**
	 * Valida el objeto, y en caso de
	 * no superar la prueba, llama a {@link #throwError(Object)}
	 * que lanzará una excepción con un mensaje descriptivo
	 * del error.
	 * @param obj objeto a validar.
	 * @throws RuntimeException si la validación.
	 * no es positiva. Ver {@link mr.common.exception.spring.FrameworkException
	 * FrameworkException} para lanzar.
	 * excepciones con mensajes internacionalizados.
	 */
	public void valide(Object obj) throws RuntimeException {
		if(!isValid(obj)) {
			throwError(obj);
		}
	}

	/**
	 * Método protegido que debe ser implementado
	 * lanzando la excepción en caso de que el objecto
	 * sea inválido al ser llamado {@link #valide(Object)}.
	 * @param obj objeto validado.
	 */
	protected abstract void throwError(Object obj) throws RuntimeException;
}
