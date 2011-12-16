package mr.common.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.springframework.util.ResourceUtils;


/**
 * Serializa o des-serializa cualquier objeto que cumpla con las
 * especificaciones de serialización.
 *
 * @author Mariano Ruiz
 */
public class ObjectSerializer {

	/**
	 * Escribe el objeto serializado en el archivo pasado.
	 */
	public static void write(String filePath, Object obj) {
		try {
			FileOutputStream fout = new FileOutputStream(
					ResourceUtils.getFile(filePath));
			ObjectOutput out = new ObjectOutputStream(fout);
			out.writeObject(obj);
			fout.close();
			out.close();
		} catch (Throwable e) {
			throw new RuntimeException("Error to serialize the object.", e);
		}
	}

	/**
	 * Retorna el objeto serializado en el archivo. 
	 */
	public static Object read(String filePath) {
		Object obj = null;
		try {
			FileInputStream fin = new FileInputStream(
					ResourceUtils.getFile(filePath));
			ObjectInputStream objStream = new ObjectInputStream(fin);
			obj = objStream.readObject();
			objStream.close();
			fin.close();
		} catch (Throwable e) {
			throw new RuntimeException("Error to unserialize the object.", e);

		}
		return obj;
	}
}
