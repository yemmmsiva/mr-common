package mr.common.security;

import java.util.Random;
import java.util.UUID;


/**
 * Implementa {@link RandomString}, y genera cadenas aleatorias
 * de longitud fija, usando números hexadecimales de longitud fija.
 *
 * @author Mariano Ruiz
 */
public class UUIDRandomString implements RandomString {

	private int numChars = 6;
	private Random random;

	/**
	 * Crear el el generador de string con longitud fija
	 * de texto de 6 caracteres.
	 */
	public UUIDRandomString() {
		random = new Random();
	}

	/**
	 * Crear el el generador de string con longitud fija
	 * de texto aleatorios.
	 * @param numChars cantidad de caracteres, entre 1
	 * y 32.
	 */
	public UUIDRandomString(int numChars) {
		this();
		if(numChars<1) {
			throw new IllegalArgumentException("numChars < 1.");
		} else if(numChars>32) {
			throw new IllegalArgumentException("numChars > 32.");
		}
		this.numChars = numChars;
	}

	public String nextString() {
		String text = UUID.randomUUID().toString();
		int offset = random.nextInt(32-numChars+1);
		char[] buffer = new char[numChars];
		int i = 0, j = 0;
		while(j<numChars) {
			if(text.charAt(i+offset)!='-') {
				buffer[j] = text.charAt(i+offset);
				j++;
			}
			i++;
		}
		return new String(buffer);
	}
}
