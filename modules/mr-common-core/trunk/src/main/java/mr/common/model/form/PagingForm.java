package mr.common.model.form;

import mr.common.model.Pageable;


/**
 * Form base con parámetros de paginación, que
 * implementa {@link mr.common.model.Pageable}.
 * @author Mariano Ruiz
 */
public abstract class PagingForm implements Pageable {

	private static final long serialVersionUID = 1L;

	private Integer start;
	private Integer limit;

	public Integer getStart() {
		if(start==null && getLimit()!=null) {
			return 0;
		}
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public boolean isPageable() {
		return getLimit()!=null && getStart()!=null && getLimit()!=0;
	}

	/**
	 * Método no soprtado
	 * @throws UnsupportedOperationException
	 */
	public String[] getSort() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Método no soprtado
	 * @throws UnsupportedOperationException
	 */
	public boolean isSorteable() {
		throw new UnsupportedOperationException();
	}
}
