package mr.common.security.userentity.model;

import javax.persistence.Entity;
import javax.persistence.Transient;

import mr.common.model.AuditableDictionary;
import mr.common.security.model.Role;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * Implementación de la interfaz {@link mr.common.security.model.Role Role},
 * que representa un tipo de rol de usuario.
 * @author Mariano Ruiz
 */
@Entity(name="role")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class RoleEntity extends AuditableDictionary implements Role {

	private static final long serialVersionUID = 1L;

	@Transient
	public String getAuthority() {
		return getCode();
	}
}
