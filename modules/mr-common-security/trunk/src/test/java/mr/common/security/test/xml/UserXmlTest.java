package mr.common.security.test.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import mr.common.format.XmlUtils;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class UserXmlTest {

	File xmlUsers = new File(getTestPrefixResourcePath() + "users.xml");
	File xsdUsers = new File(getMainPrefixResourcePath() + "schema/users-1.0.xsd");

	@Test
	public void testValidateXml() throws ParserConfigurationException, SAXException, IOException {
		XmlUtils.getAndValidateDocument(xmlUsers, xsdUsers);
	}

	@Test
	public void testXmlUsers() throws ParserConfigurationException, SAXException, IOException {
		Document dom = XmlUtils.getDocument(xmlUsers);
	}

	private String getTestPrefixResourcePath() {
		return "src/test/resources/";
	}

	private String getMainPrefixResourcePath() {
		return "src/main/resources/";
	}
}
