package mr.common.security.userentity.organization.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Transient;

import mr.common.model.AuditableEntity;
import mr.common.security.organization.model.Organization;


/**
 * Implementación de {@link mr.common.security.organization.
 * model.Organization Organization}.
 * @author Mariano Ruiz
 */
@Entity
public class OrganizationEntity extends AuditableEntity
      implements Organization {

	private static final long serialVersionUID = 1L;

	public static final String ADMINISTRATORS = "Administrators";

	private String name;
	private String description;
	private boolean enabled;


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Transient
	public void setId(Serializable id) {
		setId((Long)id);
	}
}
