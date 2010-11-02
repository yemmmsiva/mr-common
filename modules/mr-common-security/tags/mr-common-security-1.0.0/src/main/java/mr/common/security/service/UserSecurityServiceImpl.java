package mr.common.security.service;


/**
 * Implementación de {@link mr.common.security.service.UserSecurityService
 * UserSecurityService} que devuelve siempre la constante
 * {@link mr.common.security.service.UserSecurityService#APP_USER APP_USER}
 * en el método {@link #getCurrentUsername()}.<br/>
 * Usar esta implementación en sistemas que no soportan sesiones con
 * usuarios, pero por razones de auditoría u otra necesitan de
 * un usuario "responsable".
 * 
 * @see mr.common.security.service.UserSecurityService
 * @author Mariano Ruiz
 */
public class UserSecurityServiceImpl implements UserSecurityService {

	public String getCurrentUsername() {
		return APP_USER;
	}
}
