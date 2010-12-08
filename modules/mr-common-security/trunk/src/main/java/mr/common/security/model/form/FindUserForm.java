package mr.common.security.model.form;

import mr.common.model.form.PagingForm;



/**
 * Form para buscar usuarios.
 * @author Mariano Ruiz
 */
public class FindUserForm extends PagingForm {

	private static final long serialVersionUID = 1L;

	private String username;
	private String commonName;
	private String mail;
	private Boolean enabled;
	private String output;


	public String getEnabledAsYesNo() {
		if(enabled==null) {
			return "";
		} else if(enabled) {
			return "YES";
		} else {
			return "NO";
		}
	}

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
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
}
