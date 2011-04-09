package mr.common.security.test.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import mr.common.format.XmlUtils;
import mr.common.security.model.Role;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class UserXmlTest {

	File xmlUsers = new File(getTestPrefixResourcePath() + "users.xml");
	File xsdUsers = new File(getMainPrefixResourcePath() + "schema/users-1.0.xsd");

	/**
	 * Verifica que el archivo XML de usuarios cumpla con las
	 * especificaciones del XSD.
	 */
	@Test
	public void testValidateXml()
	  throws ParserConfigurationException, SAXException, IOException {
		XmlUtils.getAndValidateDocument(xmlUsers, xsdUsers);
	}

	/**
	 * Verifica que el XML contenga ciertos campos con
	 * determinados valores de prueba.
	 */
	@Test
	public void testXmlUsers()
	  throws ParserConfigurationException, SAXException, IOException {
		Document dom = XmlUtils.getDocument(xmlUsers);
		Node userNode = dom.getElementsByTagName("user").item(0);
		assertNotNull("The XML file don't constain any user.", userNode);
		assertEquals(userNode.getAttributes().getNamedItem("username").getNodeValue(), "admin");
		assertEquals(userNode.getAttributes().getNamedItem("emailAddress").getNodeValue(), "marianoruiz@mrdev.com.ar");
		boolean hasAdminRole = false;
		NodeList rolesNode = XmlUtils.nextNode(userNode.getChildNodes().item(0)).getChildNodes();
		for(int i=0; i<rolesNode.getLength(); i++) {
			if(XmlUtils.isTagNode(rolesNode.item(i))
					&& rolesNode.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(Role.ROLE_ADMIN)) {
				hasAdminRole = true;
			}
		}
		assertTrue("User not have role ADMIN", hasAdminRole);
	}

	private String getTestPrefixResourcePath() {
		return "src/test/resources/";
	}

	private String getMainPrefixResourcePath() {
		return "src/main/resources/";
	}
}
