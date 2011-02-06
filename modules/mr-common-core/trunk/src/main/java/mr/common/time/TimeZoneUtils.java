package mr.common.time;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Clases útiles para trabajar con {@link java.util.TimeZone}. Los métodos
 * {@link #getAvailableIDs} y {@link #getAvailableTimeZones()} son similares
 * a {@link java.util.TimeZone#getAvailableIDs() TimeZone#getAvailableIDs()},
 * pero quita time zones que no sean válidos según la <i>tz database</i>,
 * o que sean redundates, como por ejemplo
 * <strike><code>JST</code></strike> -> <code>Japan</code>.<br/>
 * De todos modos no se debe tomar a estos listados como más válidos
 * que el retornado por la clase útil de Java, solo puede ser más
 * conveniente si queremos reducir y normalizar la cantidad de opciones,
 * por ejemplo para listarlas en una aplicación.
 *
 * @author Mariano Ruiz
 */
public abstract class TimeZoneUtils {

	private static final Log logger = LogFactory.getLog(TimeZoneUtils.class);

	public static String GMT = "GMT";

	/**
	 * Este Map tiene como key - value -> timezoneId - valid-timezoneId.
	 * Como timezone válido se entiende el timezone más estándar o compatible
	 * con la tz database. Si al map se le pasa una key válida, se le pasara
	 * como value la misma key.
	 */
	private static Map<String, String> validTimeZones;

	/**
	 * Cachea el cálculo de los time zones ids válidos.
	 */
	private static List<String> timeZoneIDs = null;
	/**
	 * Cachea el cálculo de los time zones válidos.
	 */
	private static List<TimeZone> timeZones = null;

	static {
		validTimeZones = new HashMap<String, String>(23) {
			private static final long serialVersionUID = 1L;
			@Override
			synchronized public String get(Object key) {
				String value = super.get(key);
				if(value==null) {
					if(key.equals(GMT)) {
						return (String) key;
					} else {
						TimeZone zone = TimeZone.getTimeZone((String)key);
						if(!zone.getID().equals(GMT)) {
							return zone.getID();
						} else {
							return null;
						}
					}
				}
				return value;
			}
		};

		synchronized(validTimeZones) {
			validTimeZones.put("America/Buenos_Aires",              "America/Argentina/Buenos_Aires");
			validTimeZones.put("America/Catamarca",                 "America/Argentina/Catamarca");
			validTimeZones.put("America/Cordoba",                   "America/Argentina/Cordoba");
			validTimeZones.put("America/Jujuy",                     "America/Argentina/Jujuy");
			validTimeZones.put("America/La_Rioja",                  "America/Argentina/La_Rioja");
			validTimeZones.put("America/Rosario",                   "America/Argentina/Rosario");
			validTimeZones.put("America/Mendoza",                   "America/Argentina/Mendoza");
			validTimeZones.put("America/Rio_Gallegos",              "America/Argentina/Rio_Gallegos");
			validTimeZones.put("America/Salta",                     "America/Argentina/Salta");
			validTimeZones.put("America/San_Juan",                  "America/Argentina/San_Juan");
			validTimeZones.put("America/San_Luis",                  "America/Argentina/San_Luis");
			validTimeZones.put("America/Tucuman",                   "America/Argentina/Tucuman");
			validTimeZones.put("America/Ushuaia",                   "America/Argentina/Ushuaia");
			validTimeZones.put("America/ComodRivadavia",            "America/Argentina/Buenos_Aires");
			validTimeZones.put("America/Argentina/ComodRivadavia",  "America/Argentina/Buenos_Aires");
			validTimeZones.put("AGT",                               "America/Argentina/Buenos_Aires");
	
			validTimeZones.put("GMT0",           GMT);
			validTimeZones.put("Etc/Universal",  GMT);
			validTimeZones.put("Universal",      GMT);
			validTimeZones.put("UCT",            GMT);
			validTimeZones.put("UTC",            GMT);
			validTimeZones.put("Etc/UCT",        GMT);
			validTimeZones.put("Etc/UTC",        GMT);
			validTimeZones.put("Etc/GMT",        GMT);
			validTimeZones.put("Etc/Greenwich",  GMT);
			validTimeZones.put("Etc/GMT0",       GMT);
			validTimeZones.put("Etc/GMT-0",      GMT);
			validTimeZones.put("Etc/GMT-1",      "GMT-01:00");
			validTimeZones.put("Etc/GMT-2",      "GMT-02:00");
			validTimeZones.put("Etc/GMT-3",      "GMT-03:00");
			validTimeZones.put("Etc/GMT-4",      "GMT-04:00");
			validTimeZones.put("Etc/GMT-5",      "GMT-05:00");
			validTimeZones.put("Etc/GMT-6",      "GMT-06:00");
			validTimeZones.put("Etc/GMT-7",      "GMT-07:00");
			validTimeZones.put("Etc/GMT-8",      "GMT-08:00");
			validTimeZones.put("Etc/GMT-9",      "GMT-09:00");
			validTimeZones.put("Etc/GMT-10",     "GMT-10:00");
			validTimeZones.put("Etc/GMT-11",     "GMT-11:00");
			validTimeZones.put("Etc/GMT-12",     "GMT-12:00");
			validTimeZones.put("Etc/GMT-13",     "GMT-13:00");
			validTimeZones.put("Etc/GMT-14",     "GMT-14:00");
			validTimeZones.put("Etc/GMT+0",      "GMT+00:00");
			validTimeZones.put("Etc/GMT+1",      "GMT+01:00");
			validTimeZones.put("Etc/GMT+2",      "GMT+02:00");
			validTimeZones.put("Etc/GMT+3",      "GMT+03:00");
			validTimeZones.put("Etc/GMT+4",      "GMT+04:00");
			validTimeZones.put("Etc/GMT+5",      "GMT+05:00");
			validTimeZones.put("Etc/GMT+6",      "GMT+06:00");
			validTimeZones.put("Etc/GMT+7",      "GMT+07:00");
			validTimeZones.put("Etc/GMT+8",      "GMT+08:00");
			validTimeZones.put("Etc/GMT+9",      "GMT+09:00");
			validTimeZones.put("Etc/GMT+10",     "GMT+10:00");
			validTimeZones.put("Etc/GMT+11",     "GMT+11:00");
			validTimeZones.put("Etc/GMT+12",     "GMT+12:00");
			validTimeZones.put("Etc/GMT+13",     "GMT+13:00");
			validTimeZones.put("Etc/GMT+14",     "GMT+14:00");
	
			validTimeZones.put("NZ-CHAT",          "Pacific/Chatham");
			validTimeZones.put("Etc/Zulu",          "Zulu");
			validTimeZones.put("GB-Eire",           "Eire");
			validTimeZones.put("Cuba",              "America/Havana");
			validTimeZones.put("EST5EDT",           "EST");
			validTimeZones.put("SystemV/EST5",      "EST");
			validTimeZones.put("SystemV/EST5EDT",   "EST");
			validTimeZones.put("SystemV/AST4",      "PRT");
			validTimeZones.put("SystemV/AST4ADT",   "PRT");
			validTimeZones.put("Antarctica/Palmer", "Chile/Continental");
			validTimeZones.put("BET",               "Brazil/East");
			validTimeZones.put("ECT",               "CET");
			validTimeZones.put("Asia/Riyadh87",     "Asia/Riyadh");
			validTimeZones.put("Asia/Riyadh88",     "Asia/Riyadh");
			validTimeZones.put("Asia/Riyadh89",     "Asia/Riyadh");
			validTimeZones.put("Mideast/Riyadh87",  "Asia/Riyadh");
			validTimeZones.put("Mideast/Riyadh88",  "Asia/Riyadh");
			validTimeZones.put("Mideast/Riyadh89",  "Asia/Riyadh");
			validTimeZones.put("JST",               "Japan");
			validTimeZones.put("PRC",               "CTT");
			validTimeZones.put("NST",               "NZ");
		}
	}

	/**
	 * Retorna al igual que {@link java.util.TimeZone#getAvailableIDs()}
	 * la lista de todos los <i>ID</i> de time zones, pero quitando algunas zonas que
	 * <code>TimeZone</code> repite, como por ejemplo <strike><code>America/Buenos_Aires</code></strike>,
	 * que también es retornada por el mismo método cómo
	 * <code>America/Argentina/Buenos_Aires</code>, siendo válida la segunda
	 * según el estándar de la <i>tz database</i>.<br/>
	 * También a las zonas <i>"GMT"</i> les quita el prefijo <i>"Etc/"</i>
	 * y le agrega minuto 00 (ejemplo Etc/GMT-3 -> GMT-3:00) ya que el primero no es
	 * estándar, de todos modos sin el prefijo se puede utilizar con el método
	 * {@link java.util.TimeZone#getTimeZone(String)} para obtener la zona.<br/>
	 * Otras zonas redundantes también son removidas, como por ejemplo
	 * <strike><code>BET</code></strike> -> <code>Brazil/East</code>,
	 * o <strike><code>JST</code></strike> -> <code>Japan</code>.<br/>
	 * De todos modos no se debe tomar a estos listados como más válidos
	 * que el retornado por la clase útil de Java, solo puede ser más
	 * conveniente si queremos reducir y normalizar la cantidad de opciones,
	 * por ejemplo para listarlas en una aplicación.
	 * 
	 * @return lista de ids de las zonas
	 */
	public static List<String> getAvailableIDs() {
		if(timeZoneIDs==null) {
			String[] allTimeZones = TimeZone.getAvailableIDs();
			timeZoneIDs = new ArrayList<String>(allTimeZones.length);
			synchronized(timeZoneIDs) {
				for(int i=0; i<allTimeZones.length; i++) {
					String key = validTimeZones.get(allTimeZones[i]);
					if(key!=null && (key.equals(allTimeZones[i]) || allTimeZones[i].startsWith("Etc/GMT"))
							&& !timeZones.contains(key)) {
						timeZoneIDs.add(key);
					} else {
						logger.debug("Invalid time zone '" + allTimeZones[i] + "'");
					}
				}
				return timeZoneIDs;
			}
		}
		return timeZoneIDs;
	}

	/**
	 * Retorna similar a {@link java.util.TimeZone#getAvailableIDs()}
	 * la lista de todos los time zones, pero quitando algunas zonas que
	 * <code>TimeZone</code> repite, como por ejemplo <strike><code>America/Buenos_Aires</code></strike>,
	 * que también es retornada por el mismo método cómo
	 * <code>America/Argentina/Buenos_Aires</code>, siendo válida la segunda
	 * según el estándar de la <i>tz database</i>.<br/>
	 * También a las zonas <i>"GMT"</i> les quita el prefijo <i>"Etc/"</i>
	 * y le agrega minuto 00 (ejemplo Etc/GMT-3 -> GMT-3:00) ya que el primero no es
	 * estándar, de todos modos sin el prefijo se puede utilizar con el método
	 * {@link java.util.TimeZone#getTimeZone(String)} para obtener la zona.<br/>
	 * Otras zonas redundantes también son removidas, como por ejemplo
	 * <strike><code>BET</code></strike> -> <code>Brazil/East</code>,
	 * o <strike><code>JST</code></strike> -> <code>Japan</code>.<br/>
	 * De todos modos no se debe tomar a estos listados como más válidos
	 * que el retornado por la clase útil de Java, solo puede ser más
	 * conveniente si queremos reducir y normalizar la cantidad de opciones,
	 * por ejemplo para listarlas en una aplicación.
	 * 
	 * @return lista de {@link java.util.TimeZone} de las zonas
	 */
	public static List<TimeZone> getAvailableTimeZones() {
		if(timeZones==null) {
			String[] allTimeZones = TimeZone.getAvailableIDs();
			timeZones = new ArrayList<TimeZone>(allTimeZones.length);
			synchronized(timeZones) {
				for(int i=0; i<allTimeZones.length; i++) {
					String key = validTimeZones.get(allTimeZones[i]);
					if(key!=null && (key.equals(allTimeZones[i]) || allTimeZones[i].startsWith("Etc/GMT"))
							&& !timeZones.contains(key)) {
						timeZones.add(TimeZone.getTimeZone(key));
					} else {
						logger.debug("Invalid time zone '" + allTimeZones[i] + "'");
					}
				}
				return timeZones;
			}
		}
		return timeZones;
	}

	/**
	 * Borra la caché de los time zones devueltos por
	 * {@link #getAvailableTimeZones()} y {@link #getAvailableIDs()}
	 */
	public synchronized static void cleanAvailableTimeZonesCache() {
		timeZones = null;
		timeZoneIDs = null;
	}
}
