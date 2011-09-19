package mr.common.security.userentity.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import mr.common.security.userentity.model.Person;


/**
 * @author Mariano Ruiz
 */
@Entity(name="userdata")
public class UserData extends Person {

	private static final long serialVersionUID = 1L;

	private UserEntity user;
	private boolean locked = false;

	@OneToOne(mappedBy = "userData")
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}

	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}
