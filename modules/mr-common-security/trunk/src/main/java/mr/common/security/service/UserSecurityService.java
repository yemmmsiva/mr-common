package mr.common.security.service;


/**
 * Servicio que obtiene el usuario que está actualmente logueado
 * en el sistema.
 * @author Mariano Ruiz
 */
public interface UserSecurityService {

	/**
	 * Nombre de "usuario" del sistema: `<code>APP</code>`.
	 */
	public static final String APP_USER = "APP";

    /**
     * Devuelve el nombre del usuario que está
     * actualmente logueado.<br/>
     * Si no hay usuario logueado, retorna <code>null</code>.
     * Si el sistema no soporta usuarios, debe retornar
     * {@link #APP_USER}.
     * @return current user name.
     */
    public String getCurrentUsername();
}
