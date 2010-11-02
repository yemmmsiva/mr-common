package mr.common.test.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mr.common.dao.hibernate3.AbstractHibernateAuditableDao;
import mr.common.test.dao.AuditableEntityExampleDao;
import mr.common.test.model.AuditableEntityExample;


/**
 * @author Mariano Ruiz
 */
@Repository
public class AuditableEntityExampleHibernateDao extends AbstractHibernateAuditableDao<AuditableEntityExample>
		implements AuditableEntityExampleDao {

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<AuditableEntityExample> findByComentario(String comentario) {
		return getSession().createQuery("from " + AuditableEntityExample.class.getName()
				              + " where comentario = '" + comentario + "'"
				              + "       and audit.deleted = false").list();
	}

}
