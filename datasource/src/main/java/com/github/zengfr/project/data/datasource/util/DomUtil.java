package com.github.zengfr.project.data.datasource.util;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomUtil {
	public static String getAttribute(Node node, String attributeName) {
		Node attributeNode = node.getAttributes().getNamedItem(attributeName);
		if (attributeNode != null) {
			String attribute = attributeNode.getNodeValue();
			if (attribute != null) {
				attribute = attribute.trim();
			}
			return attribute;
		}
		return null;
	}

	public static String getAttribute(Node node, String attributeName, String defaultValue) {
		String attribute = defaultValue;
		try {
			attribute = node.getAttributes().getNamedItem(attributeName).getNodeValue();
		} catch (NullPointerException e) {
		}
		if (attribute != null && !attribute.trim().isEmpty()) {
			return attribute.trim();
		}
		return defaultValue;
	}

	public static Node getChildNode(Node node, String name) {
		NodeList children = node.getChildNodes();
		Node found = null;
		for (int i = 0; i < children.getLength(); i++) {
			if (!children.item(i).getNodeName().equalsIgnoreCase(name))
				continue;
			found = children.item(i);
			break;
		}
		return found;
	}

	public static List<Node> getChildNodes(Node node, String name) {
		List<Node> nodes = new ArrayList<>();
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (!children.item(i).getNodeName().equalsIgnoreCase(name))
				continue;
			nodes.add(children.item(i));
		}
		return nodes;
	}

	public static boolean hasAttribute(Node node, String attributeName) {
		return node.getAttributes().getNamedItem(attributeName) != null;
	}
}
