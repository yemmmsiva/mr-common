package mr.common.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang.IllegalClassException;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Entidad abstracta para tablas con id numérico.
 * @author Mariano Ruiz
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Long id;

    private int version;


	/**
	 * @return {@link Long} id
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	/**
	 * @param {@link Long} id
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "(id: " + id + ")"; 
	}

	/**
	 * Método equals pero que compara contra otro
	 * objeto {@link BaseEntity} si tiene el mismo id.
	 * @param {@link BaseEntity}
	 * @return <code>true</code> si ambos
	 * objetos son la misma entidad.
	 * @throws org.apache.commons.lang.IllegalClassException
	 * si <code>this</code> y <code>entity</code> no son
	 * del mismo tipo de clase
	 */
	public boolean equals(BaseEntity entity) {
		if(entity==null) {
			throw new NullPointerException();
		} else if(entity.getClass().getName() != this.getClass().getName()) {
			throw new IllegalClassException(this.getClass(), entity.getClass());
		} else if (entity.getId()==null || id==null) {
			return false;
		}
		return id.longValue() == entity.getId().longValue();
	}

	/**
	 * @see #equals(BaseEntity)
	 */
	@Override
	public boolean equals(Object o) {
		return equals((BaseEntity)o);
	}

	/**
	 * Retorna un hashcode basado en el nombre de la clase y el
	 * <code>id</code>. Si <code>id</code> es <code>null</code>, usa el
	 * <code>hashCode()</code> de <code>Object</code>.
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if(id==null) {
			return super.hashCode();
		}
		return new HashCodeBuilder(83, 7).append(this.getClass().getName()).append(id)
				.toHashCode();
	}

	/**
	 * Clona el objeto llamando a {@link Object#clone()},
	 * pero no copia el id de la entidad.
	 * @see Object#clone()
	 */
	@Override
	public BaseEntity clone() throws CloneNotSupportedException {
		BaseEntity e = (BaseEntity) super.clone();
		e.setId(null);
		return e;
	}

	/**
	 * @return version
	 */
    @Version
	public int getVersion() {
		return version;
	}

	/**
	 * @param version
	 */
	public void setVersion(int version) {
		this.version = version;
	}
}
