package mr.common.security.service;

import static mr.common.format.XmlUtils.getDocument;
import static mr.common.format.XmlUtils.isTagNode;
import static mr.common.format.XmlUtils.nextNode;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import static mr.common.format.XmlUtils.*;
import mr.common.security.model.Role;
import mr.common.security.model.User;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Parsea a partir de un objeto {@link
 * mr.common.security.model.User User} un XML
 * con la información del mismo, y visceversa.<br/>
 * La definición XSD de como debe ser el XML con
 * información de usuarios está en <a href= 
 * "http://mr-common.googlecode.com/files/users-1.0.xsd">
 * mr-common</a>.
 * @author Mariano Ruiz
 */
public class UserXmlBinder {

	public static final String XMLNS_USERS = "http://mr-common.googlecode.com/schema/users";
	public static final String TAG_USERS = "users";
	public static final String TAG_USER = "user";
	public static final String TAG_ROLES = "roles";
	public static final String TAG_ROLE = "role";
	public static final String PROP_USERNAME = "username";
	public static final String PROP_PASSWORD = "password";
	public static final String PROP_EMAILADDRESS = "emailAddress";
	public static final String PROP_COMMONNAME = "commonName";
	public static final String PROP_ENABLED = "enabled";
	public static final String PROP_ID = "id";

	@Resource
	private UserService userService;

	/**
	 * Parsea a XML el objeto <code>user</code>.
	 */
	public String userToXml(User user) {
		StringBuilder builder = new StringBuilder(500);
		builder.append(encodingHeader(XML_ENCODING_UTF8));
		builder.append(tag(TAG_USERS));
			builder.append(userToUserXmlTag(user));
		builder.append(endTag(TAG_USERS));
		return builder.toString();
	}

	private String userToUserXmlTag(User user) {
		StringBuilder builder = new StringBuilder(500);
		builder.append(tag(TAG_USER,
		                   prop(PROP_USERNAME, user.getUsername()),
		                   prop(PROP_PASSWORD, user.getPassword()),
		                   prop(PROP_EMAILADDRESS, user.getEmailAddress()),
		                   prop(PROP_COMMONNAME, user.getCommonName()),
		                   prop(PROP_ENABLED, new Boolean(user.isEnabled()).toString())
		              ));
			if(!user.getRoles().isEmpty()) {
				builder.append(tag(TAG_ROLES));
					for(int i=0; i<user.getRoles().size(); i++) {
						builder.append(closedTag(TAG_ROLE,
									prop(PROP_ID, user.getRoles().get(i).getAuthority())
								));
					}
				builder.append(endTag(TAG_ROLES));
			}
		builder.append(endTag(TAG_USER));
		return builder.toString();
	}

	/**
	 * Parsea a XML la lista de objetos <code>users</code>.
	 */
	public String usersToXml(List<User> users) {
		StringBuilder builder = new StringBuilder(500);
		builder.append(encodingHeader(XML_ENCODING_UTF8));
		builder.append(tag(TAG_USERS));
			for(User user: users) {
				builder.append(userToUserXmlTag(user));
			}
		builder.append(endTag(TAG_USERS));
		return builder.toString();
	}

	/**
	 * Parsea a objeto el XML con datos de usuarios.
	 */
	public User xmlToUser(String xml) {
		List<User> users = xmlToUsers(xml);
		if(users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}

	/**
	 * Parsea a una lista de objetos el XML con datos de usuarios.
	 */
	public List<User> xmlToUsers(String xml) {
		if(xml==null) {
			throw new NullPointerException("xml == null");
		}
		Document dom = getDocument(xml);
		NodeList userNodes = dom.getElementsByTagName(TAG_USER);
		List<User> users = new ArrayList<User>(userNodes.getLength());
		for(int i=0; i<userNodes.getLength(); i++) {
			Node userNode = userNodes.item(0);
			User user = userService.getUserInstance();
			user.setUsername(userNode.getAttributes().getNamedItem(PROP_USERNAME).getNodeValue());
			user.setEmailAddress(userNode.getAttributes().getNamedItem(PROP_EMAILADDRESS).getNodeValue());
			List<Role> roles = new ArrayList<Role>();
			NodeList rolesNode = nextNode(userNode.getChildNodes().item(0)).getChildNodes();
			for(int j=0; i<rolesNode.getLength(); j++) {
				Node roleNode = rolesNode.item(j);
				if(isTagNode(roleNode) && roleNode.getNodeName().equals(TAG_ROLE)) {
					roles.add(userService.getRole(rolesNode.item(j).getAttributes().getNamedItem(PROP_ID).getNodeValue()));
				}
			}
			user.setRoles(roles);
			users.add(user);
		}
		return users;
	}
}
