package gameserver.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author caoxin
 */
public final class TextUtils {

    private static final Logger logger = LoggerFactory.getLogger(TextUtils.class);
    private static final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    public static boolean isEmpty(final CharSequence s) {
        if (s == null) {
            return true;
        }
        return s.length() == 0;
    }

    public static boolean isBlank(final CharSequence s) {
        if (s == null) {
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static JSONObject toJsonObject(String xml) {
        JSONObject jsonObject = null;
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(xml)));
            NodeList nodeList = document.getChildNodes();
            int nodeListCount = nodeList.getLength();
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < nodeListCount; i++) {
                toMap(nodeList.item(i), map);
            }
            jsonObject = new JSONObject(map);
        } catch (ParserConfigurationException e) {
            logger.error(e.getMessage());
        } catch (SAXException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return jsonObject;
    }

    private static void toMap(Node node, Map<String, Object> map) {

        String nodeName = node.getNodeName();
        NodeList childNodes = node.getChildNodes();
        int childNodeCount = childNodes.getLength();

        if (childNodeCount == 1) {
            map.put(nodeName, node.getFirstChild().getNodeValue());
        } else {
            Map<String, Object> childMap = new HashMap<String, Object>();
            map.put(nodeName, childMap);
            for (int i = 0; i < childNodeCount; i++) {
                toMap(childNodes.item(i), childMap);
            }
        }
    }
}