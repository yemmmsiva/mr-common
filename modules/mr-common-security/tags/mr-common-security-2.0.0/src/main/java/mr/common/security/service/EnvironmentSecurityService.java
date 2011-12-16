package mr.common.security.service;

import mr.common.context.EnvironmentConfiguration;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * Implementación de {@link mr.common.security.service.UserSecurityService
 * UserSecurityService} que devuelve como username el valor retornado
 * por {@link mr.common.context.EnvironmentConfiguration#getApplicationName()}.<br/>
 * Usar esta implementación en sistemas que no soportan sesiones con
 * usuarios, pero por razones de auditoría u otra necesitan de
 * un usuario "responsable" o que identifique a la aplicación, en
 * especial si en una misma base de datos escriben más de
 * una aplicación.
 * 
 * @see mr.common.security.service.UserSecurityService
 * @author Mariano Ruiz
 */
public class EnvironmentSecurityService implements UserSecurityService {

	@Autowired
	private EnvironmentConfiguration environmentConfiguration;


	public String getCurrentUsername() {
		return environmentConfiguration.getApplicationName();
	}
}
