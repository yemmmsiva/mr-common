package mr.common.security.service;


/**
 * Implementación de {@link mr.common.security.service.UserSecurityService
 * UserSecurityService} que devuelve como username el valor
 * retornado por {@link #getCurrentUsername()}, que puede ser
 * configurado por {@link #setCurrentUsername(String)}.<br/>
 * Usar esta implementación en sistemas que no soportan sesiones con
 * usuarios, pero por razones de auditoría u otra necesitan de
 * un usuario "responsable" o que identifique a la aplicación.
 * 
 * @see mr.common.security.service.UserSecurityService
 * @author Mariano Ruiz
 */
public class UserSecurityServiceImpl implements UserSecurityService {

	private String currentUsername = APP_USER;

	public UserSecurityServiceImpl() { }

	public UserSecurityServiceImpl(String currentUsername) {
		this.currentUsername = currentUsername;
	}

	/**
	 * Devuelve el nombre de usuario, por default
	 * si no es configurado retorna {@link
	 * mr.common.security.service.UserSecurityService#APP_USER}
	 */
	public String getCurrentUsername() {
		return this.currentUsername;
	}

	public void setCurrentUsername(String currentUsername) {
		this.currentUsername = currentUsername;
	}
}
