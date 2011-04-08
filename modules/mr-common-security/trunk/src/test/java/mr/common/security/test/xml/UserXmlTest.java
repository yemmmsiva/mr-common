package mr.common.security.test.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import mr.common.format.XmlUtils;

import org.junit.Test;
import org.xml.sax.SAXException;


public class UserXmlTest {

	@Test
	public void testValidateXML() throws ParserConfigurationException, SAXException, IOException {
		File xmlUsers = new File(getTestPrefixResourcePath() + "users.xml");
		File xsdUsers = new File(getMainPrefixResourcePath() + "schema/users-1.0.xsd");
		XmlUtils.getAndValidateDocument(xmlUsers, xsdUsers);
	}

	private String getTestPrefixResourcePath() {
		return "src/test/resources/";
	}

	private String getMainPrefixResourcePath() {
		return "src/main/resources/";
	}
}
