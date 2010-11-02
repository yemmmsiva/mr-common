package mr.common.security.model.form;

import java.util.List;

import mr.common.collection.CollectionUtils;


/**
 * @author Mariano Ruiz
 */
public class UserForm extends FindUserForm {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String surname;
	private String password;
	private String roles;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public List<String> getRolesAsList() {
		return CollectionUtils.stringToObjectList(roles);
	}
}
