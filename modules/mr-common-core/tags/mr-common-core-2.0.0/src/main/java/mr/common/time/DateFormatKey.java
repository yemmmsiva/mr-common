package mr.common.time;

import java.util.TimeZone;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Clase que representa el
 * identificador de un {@link java.text.DateFormat
 * DateFormat}: su mascara (pattern) y su
 * {@link java.util.TimeZone TimeZone}.
 *
 * @author Mariano Ruiz
 */
public class DateFormatKey {

	private TimeZone timeZone;
	private String mask;

	public DateFormatKey(String mask) {
		this.timeZone = TimeZone.getDefault();
		this.mask = mask;
	}

	public DateFormatKey(String mask, TimeZone timeZone) {
		this.timeZone = timeZone;
		this.mask = mask;
	}

	@Override
	public boolean equals(Object o) {
		DateFormatKey key = (DateFormatKey) o;
		return this.mask.equals(key.mask)
		          && this.timeZone.getID().equals(key.timeZone.getID());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(37, 7)
			.append(mask)
			.append(timeZone.getID())
			.toHashCode();
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}
}
