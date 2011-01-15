package mr.common.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Elemento usado como contenedor de elementos en las listas {@link GroupList},
 * donde cada <code>GroupElement</code> contiene un objeto {@code <Parent>},
 * y asociados a este elementos {@code <Child>}.
 * 
 * @see com.livra.madmax.util.GroupList
 * @author mruiz
 */
public class GroupElement<Parent, Child> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Parent parent;
	private List<Child> childs;


	/**
	 * @see com.livra.madmax.util.GroupElement
	 * @param parent {@code <Parent>}
	 */
	public GroupElement(Parent parent) {
		this.parent = parent;
		childs = new ArrayList<Child>();
	}

	/**
	 * Agregar elementos a lista <child>, igual
	 * a: <code>getChilds().add(Child)</code>
	 * @param child
	 */
	public void addChild(Child child) {
		childs.add(child);
	}

	/**
	 * Retorna objeto 'padre'.
	 * @return {@code <Parent>}
	 */
	public Parent getParent() {
		return parent;
	}
	/**
	 * @param parent {@code <Parent>}
	 */
	public void setParent(Parent parent) {
		this.parent = parent;
	}
	/**
	 * Retorna los elementos 'hijos' del 'padre'.
	 * @return {@code List<Child>}
	 */
	public List<Child> getChilds() {
		return childs;
	}
	/**
	 * @param childs {@code List<Child>}
	 */
	public void setChilds(List<Child> childs) {
		this.childs = childs;
	} 
}
