package mr.common.test.spring.context;


import static org.junit.Assert.assertEquals;
import mr.common.test.spring.CommonAbstractTest;

import org.junit.Test;


/**
 * Prueba que el contexto de Spring se levante correctamente,
 * y que los archivos de propiedades se lean.
 * @author Mariano Ruiz
 */
public class ContextTest extends CommonAbstractTest {

	@Test
	public void testProperties() {
		assertEquals(getProperties().getProperty("app.prop1"), "Property 1");
		assertEquals(getProperties().getProperty("app.prop2"), "Property 2");
	}
}
