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


	/**
	 * @return {@link String} owner
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * @param {@link String} owner
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**
	 * @return {@link Date} created
	 */
	public Date getCreated() {
		return created;
	}
	/**
	 * @param {@link Date} created
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
	/**
	 * @return {@link String} lastUpdater
	 */
	public String getLastUpdater() {
		return lastUpdater;
	}
	/**
	 * @param {@link String} lastUpdater
	 */
	public void setLastUpdater(String lastUpdater) {
		this.lastUpdater = lastUpdater;
	}
	/**
	 * @return {@link Date} lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}
	/**
	 * @param {@link Date} lastUpdate
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	/**
	 * @return {@link Date} deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}
	/**
	 * @param {@link Date} deletedDate
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}
	/**
	 * @return {@link String} deletedUser
	 */
	public String getDeletedUser() {
		return deletedUser;
	}
	/**
	 * @param {@link String} deletedUser
	 */
	public void setDeletedUser(String deletedUser) {
		this.deletedUser = deletedUser;
	}
	/**
	 * @return {@link boolean} deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}
	/**
	 * @param {@link boolean} deleted
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
