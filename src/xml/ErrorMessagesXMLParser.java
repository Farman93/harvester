package xml;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Hashtable;
 
public class ErrorMessagesXMLParser {
	public static final int RU = 1;
	public static final int EN = 2;
	
	public static String[] main(String path, int lang) {
	    try {
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			doc.getDocumentElement().normalize();
		 
			NodeList nList = doc.getElementsByTagName("Error");
			String[] msgArr = new String[nList.getLength()];
		 
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int id = Integer.parseInt(eElement.getAttribute("id"));
					switch (lang) {
						case RU : 
							msgArr[id] = eElement.getElementsByTagName("MessageRU").item(0).getTextContent();
							break;
						case EN:
							msgArr[id] = eElement.getElementsByTagName("MessageEN").item(0).getTextContent();
							break;
					}
				}
			}
			return msgArr;
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}


}