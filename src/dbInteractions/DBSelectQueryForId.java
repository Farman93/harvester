package dbInteractions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import dbElem.Query;

public class DBSelectQueryForId implements DBProcedure {
	public static Query dbConnect(int qid) 
	{
		try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        //System.out.println("DBSelectUser connected");
	       
	        Statement statement = conn.createStatement();
		   	String que1 = "exec SelectQueryForId " + qid + ";";
	        ResultSet rs1 = statement.executeQuery(que1);
	        Query qr;
	        
	        if (rs1.next()) {
	        	qr = new Query(rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4),rs1.getString(5),
	        			rs1.getString(6),rs1.getString(7),rs1.getString(8),rs1.getString(9),rs1.getString(10),rs1.getString(11));
	        } else {
	        	conn.close();
	        	return null;
	        }
	        
	        	
	        conn.close();
	        return qr;
		} catch (Exception e) {
	    	System.err.println("DBSelectQueryForUser. An error has occured");
	    	e.printStackTrace();
	    	return null;
		}
	}
}
