package mr.common.security.model.form;

import java.io.Serializable;


/**
 * Form con datos básicos que se pueden actualizar de un usuario.
 * @author Mariano Ruiz
 */
public class BasicUserForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String commonName;
	private String mail;


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
