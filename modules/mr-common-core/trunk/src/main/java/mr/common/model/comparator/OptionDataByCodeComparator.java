package mr.common.model.comparator;

import mr.common.model.OptionData;


/**
 * Comparador que determina la comparación con el valor
 * de {@link mr.common.model.OptionData#getCode() OptionData.getCode()}
 * de ambos objetos, ignarando si son may/minus.
 *
 * @see mr.common.model.comparator.ConfigurableComparator
 * @author Mariano Ruiz
 */
public class OptionDataByCodeComparator extends ConfigurableComparator<OptionData> {

	/**
	 * @see mr.common.model.comparator.ConfigurableComparator
	 * #ConfigurableComparator()
	 */
	public OptionDataByCodeComparator() {
		super();
	}

	/**
	 * @see mr.common.model.comparator.ConfigurableComparator
	 * #ConfigurableComparator(int)
	 */
	public OptionDataByCodeComparator(int order) {
		super(order);
	}

	/**
	 * Compara por el código.
	 */
	@Override
	public int compare(OptionData o1, OptionData o2) {
		return getOrder() * o1.getCode().compareToIgnoreCase(o2.getCode());
	}
}
