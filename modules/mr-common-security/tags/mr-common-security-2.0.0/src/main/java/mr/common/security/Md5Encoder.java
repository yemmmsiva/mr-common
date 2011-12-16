package mr.common.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import mr.common.format.FormatUtils;


/**
 * Encoder que usa el algoritmo <i>MD5</i>
 * para codificar texto, y lo devuelve en formato
 * hexadecimal (32 caracteres).
 *
 * @author Mariano Ruiz
 */
public class Md5Encoder extends Encoder {

	private MessageDigest md;

	public Md5Encoder() {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String encode(String text) {
        byte[] md5hash = new byte[32];
        try {
			md.update(text.getBytes("iso-8859-1"), 0, text.length());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
        md5hash = md.digest();
        return FormatUtils.convertToHex(md5hash);
	}

	public boolean isDecodable() {
		return false;
	}
}
