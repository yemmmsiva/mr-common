package mr.common.security.organization.model;

import java.io.Serializable;

/**
 * Organización de la aplicación.
 * @author Mariano Ruiz
 */
public interface Organization extends Serializable {


	/**
	 * Organización <code>root</code> administradora
	 * de la aplicación.<br/>
	 * Los usuarios de esta organización son superusuarios.
	 */
	public static final String ROOT = "root";

	/**
	 * @return Serializable - identificador del usuario
	 */
	Serializable getId();

	/**
	 * @return Serializable - identificador del usuario
	 */
	void setId(Serializable id);

	/**
	 * Nombre de la organización.
	 */
	String getName();

	/**
	 * Nombre de la organización.
	 * @param name nuevo nombre
	 */
	void setName(String name);

	/**
	 * Descripción de la organización.
	 */
	String getDescription();

	/**
	 * Descripción de la organización.
	 * @param description nueva descripción
	 */
	void setDescription(String description);

	/**
	 * @return <code>true</code> si
	 * la organización está activa en la
	 * aplicación
	 */
	boolean isEnabled();

	/**
	 * Si <code>enabled = true</code>
	 * la organización estará activa, sino
	 * la organización estará inactiva.
	 */
	void setEnabled(boolean enabled);
}
