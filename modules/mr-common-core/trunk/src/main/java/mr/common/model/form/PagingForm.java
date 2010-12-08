package mr.common.model.form;

import mr.common.model.Pageable;


/**
 * Form base con parámetros de paginación, que
 * implementa {@link mr.common.model.Pageable}.
 * @author Mariano Ruiz
 */
public abstract class PagingForm implements Pageable {

	private static final long serialVersionUID = 1L;

	private int start = 0;
	private int limit = 0;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public boolean isPageable() {
		return getLimit()!=0;
	}
}
