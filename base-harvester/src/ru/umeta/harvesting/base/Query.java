//Класс, соответствующий сущности Query(Запрос) в БД.
package ru.umeta.harvesting.base;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Hashtable;


public class Query implements Serializable{
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEndURL() {
		return endURL;
	}

	public String getStartURL() {
		return startURL;
	}

	public String getProtocol_id() {
		return protocol_id;
	}

	public String getTime() {
		return time;
	}

	public String getReg() {
		return reg;
	}

	public String getUser_id() {
		return user_id;
	}

	public String getStruct_loc() {
		return struct_loc;
	}

	public String getLast_succ() {
		return last_succ;
	}

	public String getActive() {
		return active;
	}

	public String id;
	public String name;
	public String endURL;
	public String startURL;
	public String protocol_id;
	public String time;
	public String reg;
	public String user_id;
	public String struct_loc;
	public String last_succ;
	public String active;
	
	public Query() {
		this.id = null;
		this.name = null;
		this.endURL = null;
		this.startURL = null;
		this.protocol_id = null;
		this.time = null;
		this.reg = null;
		this.user_id = null;
		this.struct_loc = null;
		this.last_succ = null;
		this.active = null;
	}
	
	
	public Query(
			String id,
			String name,
			String endURL,
			String startURL,
			String protocol_id,
			String time,
			String reg,
			String user_id,
			String struct_loc,
			String last_succ,
			String active) {
		this.id = id;
		this.name = name;
		this.endURL = endURL;
		this.startURL = startURL;
		this.protocol_id = protocol_id;
		this.time = time;
		this.reg = reg;
		this.user_id = user_id;
		this.struct_loc = struct_loc;
		this.last_succ = last_succ;
		this.active = active;
	}
	
	public void println() {
		PrintStream out_ = System.out;
		out_.print(id + '\t');
		out_.print(name + '\t');
		out_.print(endURL + '\t');
		out_.print(startURL + '\t');
		out_.print(protocol_id + '\t');
		out_.print(time + '\t');
		out_.print(reg + '\t');
		out_.print(user_id + '\t');
		out_.print(struct_loc + '\t');
		out_.print(last_succ + '\t');
		out_.println(active);
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEndURL(String endURL) {
		this.endURL = endURL;
	}

	public void setStartURL(String startURL) {
		this.startURL = startURL;
	}

	public void setProtocol_id(String protocol_id) {
		this.protocol_id = protocol_id;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public void setStruct_loc(String struct_loc) {
		this.struct_loc = struct_loc;
	}

	public void setLast_succ(String last_succ) {
		this.last_succ = last_succ;
	}

	public void setActive(String active) {
		this.active = active;
	}

    public static Hashtable<String,String> getParameters(String xml) {
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes());

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            Hashtable<String,String> parameters = new Hashtable<String,String>();
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("structure");
            Node structNode = nList.item(0);
            NodeList childNodes = structNode.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node child = childNodes.item(i);
                Element element = (Element) child;
                parameters.put(child.getNodeName(), element.getTextContent());
            }



            return parameters;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
