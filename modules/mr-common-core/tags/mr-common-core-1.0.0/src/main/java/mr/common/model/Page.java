package mr.common.model;

import org.springframework.util.StringUtils;


/**
 * Form base con parámetros de paginación, que
 * implementa {@link mr.common.model.Pageable Pageable},
 * y de ordenamiento
 * ({@link mr.common.model.Sortable Sortable}).<br/>
 * Los parámetros de ordenamiento se setean con
 * {@link #setSort(String)} como un único string con
 * los campos de ordenación separados por coma (,).
 * Pero {@link #getSorts()} los devuelve como un array
 * string como lo determina {@link mr.common.model.Sortable Sortable}.
 * @author Mariano Ruiz
 */
public class Page implements ConfigurableData {

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

	public String getSort() {
		return sort;
	}

	public String[] getSorts() {
		if(!StringUtils.hasText(sort)) {
			return new String[]{};
		}
		return sort.split(",");
	}

	public boolean isSortable() {
		return StringUtils.hasText(getSort());
	}
}
