package mr.common.test.model;

import javax.persistence.Entity;
import mr.common.model.AuditableEntity;

/**
 * Tabla de ejemplo de una entidad {@link AuditableEntity}.
 * @author Mariano Ruiz
 */
@Entity(name="auditableentityexample")
public class AuditableEntityExample extends AuditableEntity {

	private static final long serialVersionUID = 2175119826913479516L;

	private String comentario;

	/**
	 * @return {@link String} comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param {@link String} comentario
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}
