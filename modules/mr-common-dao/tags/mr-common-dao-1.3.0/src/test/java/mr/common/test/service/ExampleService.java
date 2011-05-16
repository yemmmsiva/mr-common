package mr.common.test.service;

import java.util.List;

import mr.common.test.dao.AuditableEntityExampleDao;
import mr.common.test.model.AuditableEntityExample;

/**
 * @author Mariano Ruiz
 *
 */
public interface ExampleService {

	static String TEXTO_COMENTARIO_TRANSACCION = "transaccion";

	/**
	 * @see AuditableEntityExampleDao#getList()
	 */
	List<AuditableEntityExample> findAllExample();

	/**
	 * @see AuditableEntityExampleDao#save(AuditableEntityExample)
	 */
	void saveExample(AuditableEntityExample example);

	/**
	 * @see AuditableEntityExampleDao#deleteAll()
	 */
	void deleteAll();

	/**
	 * Este método falla lanzando una excepción para verificar en el test
	 * que funciona la transaccionalidad. Guarda un objeto
	 * {@link AuditableEntityExample} con el campo <code>comentario</code>
	 * con el valor <code>TEXTO_COMENTARIO_TRANSACCION</code>, se debe verificar
	 * luego de ejecutar el método y capturada su excepción que
	 * el objeto no ha sido guardado en la BBDD.
	 */
	void methodTestRollbackTransaction();

	/**
	 * @see AuditableEntityExampleDao#findByComentario(String)
	 */
	List<AuditableEntityExample> findByComentario(String comentario);
}
