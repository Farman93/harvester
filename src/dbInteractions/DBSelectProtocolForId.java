package dbInteractions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import dbElem.Protocol;


public class DBSelectProtocolForId implements DBProcedure {
	public static Protocol dbConnect(int pid) 
	{	
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	       
	        Statement statement = conn.createStatement();
		   	String que1 = "exec SelectProtocolForId " + pid + ";";
	        ResultSet rs1 = statement.executeQuery(que1);
	        
	        String name;
	        String path;
	        String class_;
	        String xml;
	        if (rs1.next()) {
	        	name = rs1.getString(2);
	        	class_ = rs1.getString(3);
	        	path = rs1.getString(4);
	        	xml = rs1.getString(5);
	        } else {
	        	conn.close();
	        	return null;
	        }
	        
	        conn.close();
	        Protocol result = new Protocol(pid, name);
	        result.setClass_(class_);
	        result.setPath(path);
	        result.setXml(xml);
	        return result; 
		} catch (Exception e) {
	    	System.err.println("DBSelectProtocols. An error has occured");
	    	e.printStackTrace();
	    	return null;
		}
	}
}
