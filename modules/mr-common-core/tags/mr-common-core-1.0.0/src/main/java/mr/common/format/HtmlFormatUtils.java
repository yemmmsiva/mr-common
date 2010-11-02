package mr.common.format;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

/**
 * Clase con métodos encargados de realizar formateo de contenido HTML.
 * @author Mariano Ruiz
 */
public class HtmlFormatUtils {

	private static final Pattern htmlTags = Pattern.compile("<([^>]+)>");
	
	private static Map<Integer, String> codepoint2name = new HashMap<Integer, String>();
	private static Map<String, Integer> name2codepoint = new HashMap<String, Integer>();

	private static char[] unescaped = "<>\"'/&;\\".toCharArray();
	private static boolean[] lowmap = new boolean[128];
	private static boolean[] lowmapraw = new boolean[128];

	static {
		codepoint2name.put(8704,"&forall;");
		codepoint2name.put(8194,"&ensp;");
		codepoint2name.put(8195,"&emsp;");
		codepoint2name.put(8709,"&empty;");
		codepoint2name.put(8711,"&nabla;");
		codepoint2name.put(8712,"&isin;");
		codepoint2name.put(8201,"&thinsp;");
		codepoint2name.put(8715,"&ni;");
		codepoint2name.put(8204,"&zwnj;");
		codepoint2name.put(8205,"&zwj;");
		codepoint2name.put(8206,"&lrm;");
		codepoint2name.put(8207,"&rlm;");
		codepoint2name.put(34,"&quot;");
		codepoint2name.put(8721,"&sum;");
		codepoint2name.put(8722,"&minus;");
		codepoint2name.put(8211,"&ndash;");
		codepoint2name.put(8212,"&mdash;");
		codepoint2name.put(8727,"&lowast;");
		codepoint2name.put(8216,"&lsquo;");
		codepoint2name.put(8217,"&rsquo;");
		codepoint2name.put(8730,"&radic;");
		codepoint2name.put(175,"&macr;");
		codepoint2name.put(8220,"&ldquo;");
		codepoint2name.put(8221,"&rdquo;");
		codepoint2name.put(8734,"&infin;");
		codepoint2name.put(8224,"&dagger;");
		codepoint2name.put(8225,"&Dagger;");
		codepoint2name.put(8226,"&bull;");
		codepoint2name.put(38,"&amp;");
		codepoint2name.put(8743,"&and;");
		codepoint2name.put(8744,"&or;");
		codepoint2name.put(8745,"&cap;");
		codepoint2name.put(8746,"&cup;");
		codepoint2name.put(8747,"&int;");
		codepoint2name.put(8240,"&permil;");
		codepoint2name.put(8242,"&prime;");
		codepoint2name.put(8243,"&Prime;");
		codepoint2name.put(8756,"&there4;");
		codepoint2name.put(8713,"&notin;");
		codepoint2name.put(8249,"&lsaquo;");
		codepoint2name.put(8250,"&rsaquo;");
		codepoint2name.put(60,"&lt;");
		codepoint2name.put(62,"&gt;");
		codepoint2name.put(949,"&epsilon;");
		codepoint2name.put(9824,"&spades;");
		codepoint2name.put(8260,"&frasl;");
		codepoint2name.put(8773,"&cong;");
		codepoint2name.put(8776,"&asymp;");
		codepoint2name.put(8719,"&prod;");
		codepoint2name.put(9829,"&hearts;");
		codepoint2name.put(8800,"&ne;");
		codepoint2name.put(8801,"&equiv;");
		codepoint2name.put(9827,"&clubs;");
		codepoint2name.put(8804,"&le;");
		codepoint2name.put(8805,"&ge;");
		codepoint2name.put(9830,"&diams;");
		codepoint2name.put(8834,"&sub;");
		codepoint2name.put(8835,"&sup;");
		codepoint2name.put(8836,"&nsub;");
		codepoint2name.put(8838,"&sube;");
		codepoint2name.put(8839,"&supe;");
		codepoint2name.put(8853,"&oplus;");
		codepoint2name.put(8855,"&otimes;");
		codepoint2name.put(8218,"&sbquo;");
		codepoint2name.put(197,"&Aring;");
		codepoint2name.put(160,"&nbsp;");
		codepoint2name.put(161,"&iexcl;");
		codepoint2name.put(162,"&cent;");
		codepoint2name.put(163,"&pound;");
		codepoint2name.put(164,"&curren;");
		codepoint2name.put(165,"&yen;");
		codepoint2name.put(166,"&brvbar;");
		codepoint2name.put(167,"&sect;");
		codepoint2name.put(168,"&uml;");
		codepoint2name.put(169,"&copy;");
		codepoint2name.put(170,"&ordf;");
		codepoint2name.put(171,"&laquo;");
		codepoint2name.put(172,"&not;");
		codepoint2name.put(173,"&shy;");
		codepoint2name.put(174,"&reg;");
		codepoint2name.put(8733,"&prop;");
		codepoint2name.put(176,"&deg;");
		codepoint2name.put(177,"&plusmn;");
		codepoint2name.put(178,"&sup2;");
		codepoint2name.put(179,"&sup3;");
		codepoint2name.put(180,"&acute;");
		codepoint2name.put(8222,"&bdquo;");
		codepoint2name.put(182,"&para;");
		codepoint2name.put(183,"&middot;");
		codepoint2name.put(184,"&cedil;");
		codepoint2name.put(185,"&sup1;");
		codepoint2name.put(186,"&ordm;");
		codepoint2name.put(187,"&raquo;");
		codepoint2name.put(188,"&frac14;");
		codepoint2name.put(189,"&frac12;");
		codepoint2name.put(190,"&frac34;");
		codepoint2name.put(191,"&iquest;");
		codepoint2name.put(192,"&Agrave;");
		codepoint2name.put(193,"&Aacute;");
		codepoint2name.put(194,"&Acirc;");
		codepoint2name.put(195,"&Atilde;");
		codepoint2name.put(196,"&Auml;");
		codepoint2name.put(8901,"&sdot;");
		codepoint2name.put(198,"&AElig;");
		codepoint2name.put(199,"&Ccedil;");
		codepoint2name.put(200,"&Egrave;");
		codepoint2name.put(201,"&Eacute;");
		codepoint2name.put(202,"&Ecirc;");
		codepoint2name.put(203,"&Euml;");
		codepoint2name.put(204,"&Igrave;");
		codepoint2name.put(205,"&Iacute;");
		codepoint2name.put(206,"&Icirc;");
		codepoint2name.put(207,"&Iuml;");
		codepoint2name.put(208,"&ETH;");
		codepoint2name.put(209,"&Ntilde;");
		codepoint2name.put(210,"&Ograve;");
		codepoint2name.put(211,"&Oacute;");
		codepoint2name.put(212,"&Ocirc;");
		codepoint2name.put(213,"&Otilde;");
		codepoint2name.put(214,"&Ouml;");
		codepoint2name.put(8736,"&ang;");
		codepoint2name.put(216,"&Oslash;");
		codepoint2name.put(217,"&Ugrave;");
		codepoint2name.put(218,"&Uacute;");
		codepoint2name.put(219,"&Ucirc;");
		codepoint2name.put(220,"&Uuml;");
		codepoint2name.put(221,"&Yacute;");
		codepoint2name.put(222,"&THORN;");
		codepoint2name.put(223,"&szlig;");
		codepoint2name.put(224,"&agrave;");
		codepoint2name.put(225,"&aacute;");
		codepoint2name.put(226,"&acirc;");
		codepoint2name.put(227,"&atilde;");
		codepoint2name.put(228,"&auml;");
		codepoint2name.put(229,"&aring;");
		codepoint2name.put(230,"&aelig;");
		codepoint2name.put(231,"&ccedil;");
		codepoint2name.put(232,"&egrave;");
		codepoint2name.put(233,"&eacute;");
		codepoint2name.put(234,"&ecirc;");
		codepoint2name.put(235,"&euml;");
		codepoint2name.put(236,"&igrave;");
		codepoint2name.put(8658,"&rArr;");
		codepoint2name.put(238,"&icirc;");
		codepoint2name.put(239,"&iuml;");
		codepoint2name.put(240,"&eth;");
		codepoint2name.put(241,"&ntilde;");
		codepoint2name.put(242,"&ograve;");
		codepoint2name.put(243,"&oacute;");
		codepoint2name.put(244,"&ocirc;");
		codepoint2name.put(245,"&otilde;");
		codepoint2name.put(246,"&ouml;");
		codepoint2name.put(247,"&divide;");
		codepoint2name.put(248,"&oslash;");
		codepoint2name.put(249,"&ugrave;");
		codepoint2name.put(250,"&uacute;");
		codepoint2name.put(251,"&ucirc;");
		codepoint2name.put(252,"&uuml;");
		codepoint2name.put(253,"&yacute;");
		codepoint2name.put(254,"&thorn;");
		codepoint2name.put(255,"&yuml;");
		codepoint2name.put(8968,"&lceil;");
		codepoint2name.put(8969,"&rceil;");
		codepoint2name.put(8970,"&lfloor;");
		codepoint2name.put(8971,"&rfloor;");
		codepoint2name.put(8465,"&image;");
		codepoint2name.put(215,"&times;");
		codepoint2name.put(8472,"&weierp;");
		codepoint2name.put(8476,"&real;");
		codepoint2name.put(8482,"&trade;");
		codepoint2name.put(732,"&tilde;");
		codepoint2name.put(9002,"&rang;");
		codepoint2name.put(977,"&thetasym;");
		codepoint2name.put(8706,"&part;");
		codepoint2name.put(8364,"&euro;");
		codepoint2name.put(8501,"&alefsym;");
		codepoint2name.put(914,"&Beta;");
		codepoint2name.put(181,"&micro;");
		codepoint2name.put(710,"&circ;");
		codepoint2name.put(338,"&OElig;");
		codepoint2name.put(339,"&oelig;");
		codepoint2name.put(352,"&Scaron;");
		codepoint2name.put(353,"&scaron;");
		codepoint2name.put(8593,"&uarr;");
		codepoint2name.put(8764,"&sim;");
		codepoint2name.put(402,"&fnof;");
		codepoint2name.put(8707,"&exist;");
		codepoint2name.put(915,"&Gamma;");
		codepoint2name.put(8254,"&oline;");
		codepoint2name.put(376,"&Yuml;");
		codepoint2name.put(916,"&Delta;");
		codepoint2name.put(8230,"&hellip;");
		codepoint2name.put(237,"&iacute;");
		codepoint2name.put(8592,"&larr;");
		codepoint2name.put(913,"&Alpha;");
		codepoint2name.put(8594,"&rarr;");
		codepoint2name.put(8595,"&darr;");
		codepoint2name.put(8596,"&harr;");
		codepoint2name.put(917,"&Epsilon;");
		codepoint2name.put(918,"&Zeta;");
		codepoint2name.put(919,"&Eta;");
		codepoint2name.put(920,"&Theta;");
		codepoint2name.put(921,"&Iota;");
		codepoint2name.put(922,"&Kappa;");
		codepoint2name.put(923,"&Lambda;");
		codepoint2name.put(924,"&Mu;");
		codepoint2name.put(925,"&Nu;");
		codepoint2name.put(926,"&Xi;");
		codepoint2name.put(927,"&Omicron;");
		codepoint2name.put(928,"&Pi;");
		codepoint2name.put(929,"&Rho;");
		codepoint2name.put(931,"&Sigma;");
		codepoint2name.put(932,"&Tau;");
		codepoint2name.put(933,"&Upsilon;");
		codepoint2name.put(934,"&Phi;");
		codepoint2name.put(935,"&Chi;");
		codepoint2name.put(936,"&Psi;");
		codepoint2name.put(937,"&Omega;");
		codepoint2name.put(945,"&alpha;");
		codepoint2name.put(946,"&beta;");
		codepoint2name.put(947,"&gamma;");
		codepoint2name.put(948,"&delta;");
		codepoint2name.put(8629,"&crarr;");
		codepoint2name.put(950,"&zeta;");
		codepoint2name.put(951,"&eta;");
		codepoint2name.put(952,"&theta;");
		codepoint2name.put(953,"&iota;");
		codepoint2name.put(954,"&kappa;");
		codepoint2name.put(955,"&lambda;");
		codepoint2name.put(956,"&mu;");
		codepoint2name.put(957,"&nu;");
		codepoint2name.put(958,"&xi;");
		codepoint2name.put(959,"&omicron;");
		codepoint2name.put(960,"&pi;");
		codepoint2name.put(961,"&rho;");
		codepoint2name.put(962,"&sigmaf;");
		codepoint2name.put(963,"&sigma;");
		codepoint2name.put(964,"&tau;");
		codepoint2name.put(965,"&upsilon;");
		codepoint2name.put(966,"&phi;");
		codepoint2name.put(967,"&chi;");
		codepoint2name.put(968,"&psi;");
		codepoint2name.put(969,"&omega;");
		codepoint2name.put(9674,"&loz;");
		codepoint2name.put(8656,"&lArr;");
		codepoint2name.put(8657,"&uArr;");
		codepoint2name.put(978,"&upsih;");
		codepoint2name.put(8659,"&dArr;");
		codepoint2name.put(8660,"&hArr;");
		codepoint2name.put(982,"&piv;");
		codepoint2name.put(8869,"&perp;");
		codepoint2name.put(9001,"&lang;");
		
		for (int i=0; i<128; i++) {
			lowmapraw[i] = lowmap[i] = codepoint2name.containsKey(i);
		}
		for (char c : unescaped) {
			lowmap[c] = false;
		}
		for(Map.Entry<Integer, String> entry : codepoint2name.entrySet()) {
			name2codepoint.put(entry.getValue(), entry.getKey());
		}
	};


	/**
	 * Quita los tags HTML de un String.
	 * @param s String: contenido HTML (puede ser <code>null</code>)
	 * @return String - cadena sin los tags
	 * */
    public static String stripHTML(String html) {
        if (html == null) {
        	return "";
        }
        return htmlTags.matcher(html).replaceAll("");
    }


    /**
     * Transforma los caracteres HTML a su equivalente 'escapeado'.
     * @param s String: contenido HTML
     * @param escapeSpaces boolean: si se deben transformar los espacios en blanco
     * y las tabulaciones
     * @param convertToHtmlUnicodeEscapes boolean: si los caracteres no ASCII7
     * deben ser transformados a <code>&#</code><i><code>codepoint</code></i><code>;</code>
     * @return CharSequence
     */
	public static CharSequence escapeHtmlAsCharSequence(final String s,
			final boolean escapeSpaces,
			final boolean convertToHtmlUnicodeEscapes) {

		if (s == null) {
			return null;
		} else {
			int len = s.length();
			final StringBuffer buffer = new StringBuffer((int) (len * 1.1));
			for (int i = 0; i < len; i++) {
				final char c = s.charAt(i);
				switch (c) {
					case '\t':
						if (escapeSpaces) {
							// Assumption is four space tabs (sorry, but that's
							// just how it is!)
							buffer.append("&nbsp;&nbsp;&nbsp;&nbsp;");
						} else {
							buffer.append(c);
						}
						break;
					case ' ':
						if (escapeSpaces) {
							buffer.append("&nbsp;");
						} else {
							buffer.append(c);
						}
						break;
					case '<':
						buffer.append("&lt;");
						break;
					case '>':
						buffer.append("&gt;");
						break;
					case '&':
						buffer.append("&amp;");
						break;
					case '"':
						buffer.append("&quot;");
						break;
					case '\'':
						buffer.append("&#039;");
						break;
					default:
						if (convertToHtmlUnicodeEscapes) {
							int ci = 0xffff & c;
							if (ci < 160) {
								// nothing special only 7 Bit
								buffer.append(c);
							} else {
								// Not 7 Bit use the unicode system
								buffer.append("&#");
								buffer.append(new Integer(ci).toString());
								buffer.append(';');
							}
						} else {
							buffer.append(c);
						}
						break;
				}
			}
			return buffer;
		}
	}

    /**
     * Transforma los caracteres HTML a su equivalente 'escapeado'.
     * @param s String: contenido HTML
     * @param escapeSpaces boolean: si se deben transformar los espacios en blanco
     * y las tabulaciones
     * @param convertToHtmlUnicodeEscapes boolean: si los caracteres no ASCII7
     * deben ser transformados a <code>&#</code><i><code>codepoint</code></i><code>;</code>
     * @return String
     */
	public static String escapeHtml(final String s,
			final boolean escapeSpaces,
			final boolean convertToHtmlUnicodeEscapes) {
		return escapeHtmlAsCharSequence(s, escapeSpaces, convertToHtmlUnicodeEscapes).toString();
	}

    /**
     * Retorna un string agregando a errorListString el error pasado
     * con un bullet de prefijo si no es el único (formato HTML).
     * @param errorListString String: cadena que contiene los errores anteriores
     * @param error String: error a agregar
     * @return: String
     */
    public static String addToHTMLErrorList(String errorListString, String error) {
    	if(!StringUtils.hasText(errorListString)) {
    		errorListString = error;
    	} else {
    		if(errorListString.indexOf("&#8226; ")==-1) {
    			errorListString = "&#8226; " + errorListString + "<br />";
    		}
    		errorListString += "&#8226; " + error + "<br />";
    	}
    	return errorListString;
    }


	/**
	 * Reemplaza todos los caracteres HTML no reconocidos por su entidad numérica.
	 * @param s String: contenido HTML.
	 * @param escapeTags boolean: si es <code>false</code>, no reemplazará algunos caracteres
	 * necesarios para el markup html. No transformará : &lt;&gt;&quot;'&amp;;\
	 * @return String - HTML transformado
	 */
	public static String escapeEntities(String s, boolean escapeTags)
	{
		StringBuilder rv = new StringBuilder();

		boolean[] map = escapeTags ? lowmapraw : lowmap;
		int len = s.length();
		int lastindex = 0;
		for (int index = 0; index < len; index = s.offsetByCodePoints(index, 1)) {
			int cp = s.codePointAt(index);
			if (cp < 128) {
				if (map[cp]) {
					if (index != lastindex)
						rv.append(s, lastindex, index);
					rv.append(codepoint2name.get(cp));
					lastindex = s.offsetByCodePoints(index, 1);
				}
			} else {
				// No extended ASCII is unescapeable
				if (index != lastindex)
					rv.append(s, lastindex, index);
				
				if (codepoint2name.containsKey(cp)) { 
					rv.append(codepoint2name.get(cp));
				} else {
					rv.append("&#");
					rv.append(cp);
					rv.append(";");
				}

				lastindex = s.offsetByCodePoints(index, 1);
			}
		}
		if (lastindex != len)
			rv.append(s, lastindex, len);

		return rv.toString();
	}
}
