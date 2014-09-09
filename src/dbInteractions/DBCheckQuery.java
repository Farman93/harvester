package dbInteractions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class DBCheckQuery implements DBProcedure {
	public static Boolean dbConnect(int qid) 
	{
		try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        //System.out.println("DBCheckQuery connected");
	       
	        Statement statement = conn.createStatement();
	        
		   	String que1 = "exec SelectQueryForQid @qid = " + qid + ";";
	        ResultSet rs1 = statement.executeQuery(que1);
	        if ( rs1.next() ) {
	        	conn.close();
	        	return true;
	        } else {
	        	conn.close();
	        	return false;
	        }
		        
		} catch (Exception e) {
	    	System.err.println("DBCheckQuery. An error has occured");
	    	e.printStackTrace();
	    	return false;
		}
	}
}
