package mr.common.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import mr.common.format.FormatUtils;


/**
 * @author Mariano Ruiz
 */
public class EncodeUtils {

    /**
     * Devuelve en hexadecimal la firma MD5 del texto.
     * @param text String
     * @return String
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md5(String text)
             throws NoSuchAlgorithmException, UnsupportedEncodingException  {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return FormatUtils.convertToHex(md5hash);
    }

    /**
     * Devuelve en base 64 la firma MD5 del texto.
     * @param text String
     * @return String
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md5AsBase64(String text)
             throws NoSuchAlgorithmException, UnsupportedEncodingException  {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return new String(Base64.encodeBase64(md5hash));
    }
}
