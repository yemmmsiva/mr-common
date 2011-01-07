package mr.common.model.comparator;

import mr.common.model.OptionData;


/**
 * Comparador que determina la comparación con el valor
 * de {@link mr.common.model.OptionData#getDescription() OptionData.getDescription()}
 * de ambos objetos, ignarando si son may/minus.
 *
 * @see mr.common.model.comparator.ConfigurableComparator
 * @author Mariano Ruiz
 */
public class OptionDataByDescriptionComparator extends ConfigurableComparator<OptionData> {

	/**
	 * @see mr.common.model.comparator.ConfigurableComparator
	 * #ConfigurableComparator()
	 */
	public OptionDataByDescriptionComparator() {
		super();
	}

	/**
	 * @see mr.common.model.comparator.ConfigurableComparator
	 * #ConfigurableComparator(int)
	 */
	public OptionDataByDescriptionComparator(int order) {
		super(order);
	}

	@Override
	public int compare(OptionData o1, OptionData o2) {
		return getOrder() * o1.getDescription().compareToIgnoreCase(o2.getDescription());
	}
}
