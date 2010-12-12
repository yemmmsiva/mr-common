package mr.common.model;

import mr.common.collection.CollectionUtils;


/**
 * Form base con parámetros de paginación, que
 * implementa {@link mr.common.model.Pageable}.
 * @author Mariano Ruiz
 */
public class Page implements Pageable {

	private static final long serialVersionUID = 1L;

	private Integer start;
	private Integer limit;
	private String sort;

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

	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * Método no soprtado
	 * @throws UnsupportedOperationException
	 */
	public String[] getSort() {
		return CollectionUtils.stringToObjectList(sort).toArray(new String[]{});
	}

	/**
	 * Método no soprtado
	 * @throws UnsupportedOperationException
	 */
	public boolean isSorteable() {
		throw new UnsupportedOperationException();
	}
}
