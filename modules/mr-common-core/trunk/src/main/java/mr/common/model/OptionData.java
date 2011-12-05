package mr.common.model;

import java.io.Serializable;


/**
 * DTO que representa una opción, que contiene
 * un código identificativo y una descripción.
 *
 * @author Mariano Ruiz
 */
public interface OptionData extends Serializable {

	/**
	 * @return código identificativo de la opción
	 */
	String getCode();

	/**
	 * @return descripción de la opción
	 */
	String getDescription();
}
