package mr.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;

import mr.common.security.service.UserSecurityService;

/**
 * Campos de auditoria para una tabla.
 * @author Mariano Ruiz
 *
 */
@Embeddable
public class Audit implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Restricción de borrado para los mapeos, usar con
	 * la anotación <code>@Where</code> para que las listas
	 * no se inicializen con objetos borrados por auditoría.
	 */
	public static final String UNDELETED_RESTRICTION = "deleted = 0";

	/**
	 * Usuario diccionario usado por defecto cuando el registro es creado
	 * por la aplicación.
	 */
	public static final String DEFAULT_USER = UserSecurityService.APP_USER;

	private String 	owner;
	private Date 	created;
	private String 	lastUpdater;
	private Date 	lastUpdate;
	private String 	deletedUser;
	private Date 	deletedDate;
	private boolean deleted;


	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getLastUpdater() {
		return lastUpdater;
	}
	public void setLastUpdater(String lastUpdater) {
		this.lastUpdater = lastUpdater;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public Date getDeletedDate() {
		return deletedDate;
	}
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}
	public String getDeletedUser() {
		return deletedUser;
	}
	public void setDeletedUser(String deletedUser) {
		this.deletedUser = deletedUser;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
