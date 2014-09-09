package dbInteractions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dbElem.Protocol;

public class DBSelectProtocols implements DBProcedure {
	public static ArrayList<Protocol> dbConnect() 
	{	
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        //System.out.println("DBSelectProtocols connected");
	       
	        Statement statement = conn.createStatement();
		   	String que1 = "exec SelectProtocols;";
	        ResultSet rs1 = statement.executeQuery(que1);
	        ArrayList<Protocol> lst = new ArrayList<Protocol>();
	        
	        if (rs1.next()) {
	        	Protocol q = new Protocol(rs1.getInt(1),rs1.getString(2));
	        	lst.add(q);
	        } else {
	        	conn.close();
	        	return null;
	        }
	        while (rs1.next()) {
	        	Protocol q = new Protocol(rs1.getInt(1),rs1.getString(2));
	        	lst.add(q);
	        }
	        	
	        conn.close();
	        return lst; 
		} catch (Exception e) {
	    	System.err.println("DBSelectProtocols. An error has occured");
	    	e.printStackTrace();
	    	return null;
		}
	}
}
