package mr.common.test.dao;

import java.util.List;

import mr.common.dao.AbstractAuditableDao;
import mr.common.test.model.AuditableEntityExample;


/**
 * @author Mariano Ruiz
 */
public interface AuditableEntityExampleDao extends AbstractAuditableDao<AuditableEntityExample> {

	List<AuditableEntityExample> findByComentario(String comentario);

}
