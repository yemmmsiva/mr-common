
package mr.common.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


/**
 * Clases útiles para trabajar con fechas, horas, conversiones, etc.
 * @author Mariano Ruiz
 */
public abstract class TimeUtils {

    /**
     * Formato de fecha latinoamericano, ej. <code>20/12/2009</code>.
     */
    public static final String TIME_FORMAT_DDMMYYYY = "dd/MM/yyyy";

    /**
     * Formato de fecha americano, ej. <code>12/20/2009</code>.
     */
    public static final String TIME_FORMAT_MMDDYYYY = "MM/dd/yyyy";

    /**
     * Formato de fecha estándar, ej. <code>20091220</code>.
     */
    public static final String TIME_FORMAT_YYYYMMDD = "yyyyMMdd";

    /**
     * Formato de fecha en texto latinoamericano, ej. <code>1 de septiembre de 2009</code>.
     */
    public static final String TIME_FORMAT_DD_DE_MES_DE_YYYY = "d 'de' MMMM 'de' yyyy";

    /**
     * Formato de fecha en formato TIMESTAMP de base de datos, ej. <code>2009-11-23 14:50:55</code>.
     */
    public static final String TIME_FORMAT_TIMESTAMP = "yyyy-MM-dd hh:mm:ss";

    /**
     * Milisegundos de un año (no bisiesto).
     */
	public static final long MILLISECONDS_OF_YEAR = 1000 * 60 * 60 * 24 * 365;

    // Formateador estándar usado por algunos métodos
    private static SimpleDateFormat df = new SimpleDateFormat(TIME_FORMAT_YYYYMMDD);

    /**
     * Método que recibe una fecha en formato String y la devuelve como un objecto {@link java.util.Date}.
     * @param dateString String
     * @param mask String: Formato de la fecha a transformar, ej. <code>yyyy-MM-dd</code>
     * @return date
     * @throws ParseException e
     */
    public static Date strToDate(String dateString, String mask) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(mask);
        return df.parse(dateString);
    }

    /**
     * Método que recibe una fecha en formato String y la devuelve como un objecto {@link java.util.Calendar}.
     * @param dateString String
     * @param mask String: Formato de la fecha a transformar, ej. <code>yyyy-MM-dd</code>
     * @return calendar
     * @throws RuntimeException excepción {@link ParseException} wrappeada en una runtime
     */
    public static Calendar strToCalendar(String dateString, String mask) {
        DateFormat df = new SimpleDateFormat(mask);
        Date date;
		try {
			date = df.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(df.getTimeZone());
		calendar.setTime(date);
		return calendar;
    }

    /**
     * Formatea a string la fecha pasada con la máscara pasada,
     * y usando el time zone del calendar.
     * @param cal {@link java.util.Calendar}
     * @param mask String
     * @return String
     */
	public static String format(Calendar cal, String mask) {
		DateFormat df = new SimpleDateFormat(mask);
		df.setTimeZone(cal.getTimeZone());
		return df.format(cal.getTime());
	}

    /**
     * Formatea a string la fecha pasada con la máscara pasada.
     * @param date {@link java.util.Date}
     * @param mask String
     * @return String
     */
	public static String format(Date date, String mask) {
		DateFormat df = new SimpleDateFormat(mask);
		return df.format(date.getTime());
	}

    /**
     * Formatea a string la fecha pasada con la máscara pasada, teniendo
     * en cuenta la zona horaria.
     * @param date {@link java.util.Date}
     * @param zone {@link java.util.TimeZone}
     * @param mask String
     * @return String
     */
	public static String format(Date date, TimeZone zone, String mask) {
		DateFormat df = new SimpleDateFormat(mask);
		df.setTimeZone(zone);
		return df.format(date.getTime());
	}

    /**
     * Retorna la misma fecha pero sin la información de la hora (o sea con hora 00:00:000).
     * @param d Date
     * @return Date
     */
    public static Date dateWithoutHour(Date d) {
        Date fecha = null;
        try {
			fecha = df.parse(df.format(d));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
        return fecha;
    }

    /**
     * Retorna la misma fecha pero sin la información de la hora (o sea con hora 00:00:000).
     * @param c Calendar
     * @return Calendar
     */
    public static Calendar calendarWithoutHour(Calendar c) {
		Calendar cal = (Calendar) c.clone();
		cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
		return cal;
    }

	/**
	 * <p>Diferencia de tiempo entre las dos fechas, en la unidad de tiempo pasada.</p>
	 * <p>Ambos calendars deben pertenecer al mismo timezone para que el cálculo
	 * sea correcto.</p>
	 * Ejemplo de unidades de tiempo:
	 * <ul>
	 * <li>TimeUnit.MILLISECONDS</li>
	 * <li>TimeUnit.HOURS</li>
	 * <li>TimeUnit.DAYS</li>
	 * ...
	 * </ul>
	 * @param a {@link Calendar}
	 * @param b {@link Calendar}
	 * @param units {@link TimeUnit}
	 * @return long
	 */
	public static long getDifferenceBetween(
			Calendar a, Calendar b, TimeUnit units) {
		return units.convert(
				b.getTimeInMillis() - a.getTimeInMillis(),
				TimeUnit.MILLISECONDS);
	}

	/**
	 * Diferencia de tiempo entre las dos fechas, en la unidad de tiempo pasada.
	 * Ejemplo de unidades de tiempo:
	 * <ul>
	 * <li>TimeUnit.MILLISECONDS</li>
	 * <li>TimeUnit.HOURS</li>
	 * <li>TimeUnit.DAYS</li>
	 * ...
	 * </ul>
	 * @param a {@link Calendar}
	 * @param b {@link Calendar}
	 * @param units {@link TimeUnit}
	 * @return long
	 */
	public static long getDifferenceBetween(
			Date a, Date b, TimeUnit units) {
		return units.convert(
				b.getTime() - a.getTime(),
				TimeUnit.MILLISECONDS);
	}

	/**
	 * @param year int
	 * @return <code>true</code> si el año es bisiesto
	 */
	public static boolean isLeapYear(int year) {
		return (new GregorianCalendar()).isLeapYear(year);
	}

	/**
	 * ¿El día de la semana es sábado o domingo?.
	 * @param dayOfWeak int - día de la semana
	 * @return boolean
	 * @see #isHolidayDay(int, boolean)
	 */
	public static boolean isDayOfWeekEnd(int dayOfWeak) {
		return dayOfWeak == Calendar.SUNDAY || dayOfWeak == Calendar.SATURDAY;
	}

	/**
	 * @param dateOfBirth Date: fecha de nacimiento
	 * @return int edad en años
	 */
	public static int getAge(Date dateOfBirth) {
		long ageInMilliseconds = (new Date()).getTime() - dateOfBirth.getTime();
		return (int) (ageInMilliseconds / MILLISECONDS_OF_YEAR);
	}

	/**
	 * @param dateOfBirth Date: fecha de nacimiento
	 * @return int edad en años
	 */
	public static int getAge(Calendar dateOfBirth) {
		long ageInMilliseconds = (Calendar.getInstance()).getTimeInMillis() - dateOfBirth.getTimeInMillis();
		return (int) (ageInMilliseconds / MILLISECONDS_OF_YEAR);
	}
}
