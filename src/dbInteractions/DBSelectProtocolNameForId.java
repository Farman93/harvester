package dbInteractions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class DBSelectProtocolNameForId implements DBProcedure {
	public static String dbConnect(int pid) 
	{	
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	       
	        Statement statement = conn.createStatement();
		   	String que1 = "exec SelectProtocolForId " + pid + ";";
	        ResultSet rs1 = statement.executeQuery(que1);
	        String name;
	        if (rs1.next()) {
	        	name = rs1.getString(2);
	        } else {
	        	conn.close();
	        	return null;
	        }
	     
	        conn.close();
	        return name; 
		} catch (Exception e) {
	    	System.err.println("DBSelectProtocols. An error has occured");
	    	e.printStackTrace();
	    	return null;
		}
	}
}
