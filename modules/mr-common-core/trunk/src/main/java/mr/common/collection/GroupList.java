package mr.common.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista que extiende de {@link java.util.ArrayList}, y contiene objetos que recibe como parámetro de
 * otra lista, y la transforma en una lista con elementos {@link GroupElement}.<br/>
 * La lista origen tiene un {@code <Type>} repetido y ordenado por un {@code <Parent>} que contiene.
 * A su vez cada {@code <Type>} contiene en cada row elemento/s {@code <Child>} para ese {@code <Parent>},
 * teniendo la lista origen una representación como la siguiente:<br/>
 * <ul>
 *   <li>Parent <b>1</b>, Child 1</li>
 *   <li>Parent <b>1</b>, Child 32</li>
 *   <li>Parent <b>1</b>, Child 2</li>
 *   <li>Parent <b>1</b>, Child 5</li>
 *   <li>---</li>
 *   <li>Parent <b>4</b>, Child 1</li>
 *   <li>Parent <b>4</b>, Child 1</li>
 *   <li>Parent <b>4</b>, Child 3</li>
 *   <li>---</li>
 *   <li>Parent <b>5</b>, Child 78</li>
 *   <li>---</li>
 *   <li>Parent <b>6</b>, Child 3</li>
 *   <li>Parent <b>6</b>, Child 1</li>
 *   <li>...</li>
 * </ul>
 * Al crear una lista <code>GroupList</code> con elementos <code>GroupElement</code>, la nueva
 * lista quedaría como la siguiente:<br/>
 * <ul>
 *   <li>Parent <b>1</b></li>
 *     <ul>
 *       <li>Child 1</li>
 *       <li>Child 32</li>
 *       <li>Child 2</li>
 *       <li>Child 5</li>
 *     </ul>
 *   <li>Parent <b>4</b></li>
 *     <ul>
 *       <li>Child 1</li>
 *       <li>Child 1</li>
 *       <li>Child 3</li>
 *     </ul>
 *   <li>Parent <b>5</b></li>
 *     <ul>
 *       <li>Child 78</li>
 *     </ul>
 *   <li>Parent <b>6</b></li>
 *     <ul>
 *       <li>Child 3</li>
 *       <li>Child 1</li>
 *     </ul>
 *   <li>...</li>
 * </ul>
 * 
 * Se deben implementar los métodos {@link #getParent(Type)} y {@link #getChild(Type)}
 * para que la lista conozca cómo extraer los elementos {@code <Parent>} y los elementos {@code <Child>}
 * de cada objeto {@code <Type>} de la lista origen.
 *
 * @param <Type> Tipo de datos que contiene la lista que se va a transformar.
 * @param <Parent> Tipo de datos 'padre' (que se repite varias veces agrupadamente).
 * que contendrá los elementos 'hijos'.
 * @param <Child> Tipo de datos 'hijo'.
 *
 * @author Mariano Ruiz
 */
public abstract class GroupList<Type, Parent, Child> extends ArrayList<GroupElement<Parent, Child>> {

	private static final long serialVersionUID = 1L;


	/**
	 * @param list lista origen.
	 * @param initialCapacity capacidad inicial del array.
	 */
	public GroupList(List<Type> list, int initialCapacity) {
		super(initialCapacity);
		init(list);
	}

	/**
	 * @param list lista origen.
	 */
	public GroupList(List<Type> list) {
		super();
		init(list);
	}

	/**
	 * Se debe implementar este método para que la lista conozca de los datos de origen
	 * de dónde extraer los objetos {@code <Parent>}. Ej.:<br/>
	 * <code>..Parent getParent(Account obj) { return obj.getClient(); }</code>
	 */
	protected abstract Parent getParent(Type obj);

	/**
	 * Se debe implementar este método para que la lista conozca de los datos de origen
	 * de dónde extraer los objetos {@code <Child>}. Ej.:<br/>
	 * <code>..Parent getChild(Account obj) { return obj.getAddress(); }</code>
	 */
	protected abstract Child getChild(Type obj);

	/**
	 * Es invocado antes de agregar el elemento <b><code>toAttach</code></b> al <b><code>groupElement</code></b>
	 * al ser construida la lista, por lo que puede ser sobreescrito para funciones de agregación,
	 * como totalizadores, contadores, etc.
	 */
	protected void beforeAdded(GroupElement<Parent, Child> groupElement, Child toAttach) {}

	/**
	 * Es invocado luego de agregar el elemento <b><code>attached</code></b> al <b><code>groupElement</code></b>
	 * al ser construida la lista, por lo que puede ser sobreescrito para funciones de agregación,
	 * como totalizadores, contadores, etc.
	 */
	protected void afterAdding(GroupElement<Parent, Child> groupElement, Child attached) {}

	private void init(List<Type> list) {
		Type prevElement = null;
		GroupElement<Parent, Child> prevGroupElement = null;
		for(int i=0; i<list.size(); i++) {
			Type element = list.get(i);
			GroupElement<Parent, Child> groupElement;
			if(prevElement==null || !equalParent(element, prevElement)) {
				groupElement = new GroupElement<Parent, Child>(getParent(element));
				add(groupElement);
				prevGroupElement = groupElement;
			} else {
				groupElement = prevGroupElement;
			}
			Child child = getChild(element);
			beforeAdded(groupElement, child);
			groupElement.addChild(child);
			afterAdding(groupElement, child);
			prevElement = element;
		}
	}

	private boolean equalParent(Type obj1, Type obj2) {
		return getParent(obj1).equals(getParent(obj2));
	}
}
