package mr.common.model;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;


/**
 * Tabla abstracta con id numérico y campos de auditoria.
 * Los objetos que extiendan pueden ser borrados lógicamente
 * y auditadas sus últimas modificaciones. 
 * @author Mariano Ruiz
 */
@MappedSuperclass
public abstract class AuditableEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Embedded
	private Audit audit;


	public Audit getAudit() {
		return audit;
	}
	public void setAudit(Audit audit) {
		this.audit = audit;
	}
}
