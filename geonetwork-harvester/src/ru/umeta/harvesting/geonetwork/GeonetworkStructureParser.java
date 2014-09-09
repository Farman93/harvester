package ru.umeta.harvesting.geonetwork;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import common.HarvesterInfo;

import java.io.*;
 
public class GeonetworkStructureParser {
	public static final int RU = 1;
	public static final int EN = 2;
	
	public static void parseStructIntoHinfo(String struct, HarvesterInfo hinfo) {
	    try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			InputStream stream = new ByteArrayInputStream(struct.getBytes("UTF-8"));
			Document doc = dBuilder.parse(stream);
		 
			doc.getDocumentElement().normalize();
		 
			NodeList nList = doc.getElementsByTagName("struct");
			 
			int temp = 0;
			Node nNode = nList.item(temp);
	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				hinfo.SEARCHER.freetext = eElement.getElementsByTagName("freetext").item(0).getTextContent();
				hinfo.SEARCHER.title = eElement.getElementsByTagName("title").item(0).getTextContent();
				hinfo.SEARCHER.keywords = eElement.getElementsByTagName("keywords").item(0).getTextContent();
				hinfo.SEARCHER.abstract_ = eElement.getElementsByTagName("abstract").item(0).getTextContent();
				
			}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public static void main(String[] args) {
		HarvesterInfo hinfo = new HarvesterInfo();
		parseStructIntoHinfo(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
					"<struct xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">" +
						"<freetext type = \"xs:string\">text</freetext>" +
						"<title type = \"xs:string\">title</title>" +
						"<keywords type = \"xs:string\">keywords</keywords>" +
						"<abstract type = \"xs:string\">abstract</abstract>" +
					"</struct>",hinfo);
	}
}