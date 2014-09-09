package ru.umeta.harvesting.geonetwork;


import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;

import org.jdom.JDOMException;

import java.io.StringReader;
import java.io.Reader;
import java.io.IOException;


public class Response {
    String responseString;
	public Document responseDocument ;

 
 
 public Response (String responseString){
	 //
	 this.responseString = responseString;
	 //
	 SAXBuilder builder = new SAXBuilder();
	 Reader in = new StringReader(responseString); 
	 try {
		 responseDocument = builder.build(in);
		 
		 
		 Element root;
		 root = responseDocument.getRootElement();
		 String test = root.getText();
		 
		 System.out.println(test);
		 
	 }
	 catch (JDOMException e){
		 //error
	 }
	 catch (IOException e){
		 //error
		 
	 }
 }  
}
