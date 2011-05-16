package mr.common.test.service.impl;

import java.util.List;

import javax.annotation.Resource;

import mr.common.test.dao.AuditableEntityExampleDao;
import mr.common.test.model.AuditableEntityExample;
import mr.common.test.service.ExampleService;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Mariano Ruiz
 */
@Service
@Repository
public class ExampleHibernateService implements ExampleService {

	@Resource
	private AuditableEntityExampleDao baseEntityExampleDao;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<AuditableEntityExample> findAllExample() {
		return baseEntityExampleDao.getList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public void saveExample(AuditableEntityExample example) {
		baseEntityExampleDao.save(example);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public void methodTestRollbackTransaction() {
		AuditableEntityExample ex = new AuditableEntityExample();
		ex.setComentario(ExampleService.TEXTO_COMENTARIO_TRANSACCION);
		baseEntityExampleDao.save(ex);
		// Tratamos de borrar un objeto que no existe para
		// que lanze la excepción
		baseEntityExampleDao.deleteById(999999999L);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	public List<AuditableEntityExample> findByComentario(String comentario) {
		return baseEntityExampleDao.findByComentario(comentario);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public void deleteAll() {
		baseEntityExampleDao.deleteAll();
	}
}
