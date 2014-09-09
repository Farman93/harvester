package dbInteractions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import wrap.IntWrapper;
import wrap.StringWrapper;


public class DBSelectQuery implements DBProcedure {
	final static String db_connect_string = "jdbc:sqlserver://localhost:1433;";
	final static String dbName = "databaseName=HarvestingSchedule";
	final static String dbUser = "QueryLogin";
	final static String dbPssw = "fvpQas26yPAkggCF";
	public static Boolean dbConnect(int qid, 
			StringWrapper eURL, // end URL
			StringWrapper sURL, // start URL
		    IntWrapper pid,
		    StringWrapper sloc//structure location
		    ) 
	{
		try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        //System.out.println("DBSelectQuery connected");
	       
	        Statement statement = conn.createStatement();
	        
		   	String que1 = "exec SelectQueryForId @qid = " + qid + ";";
	        ResultSet rs1 = statement.executeQuery(que1);
	        if ( rs1.next() ) {
		        eURL.value = rs1.getString("endURL");
		        sURL.value = rs1.getString("startURL");
		        pid.value = Integer.parseInt(rs1.getString("protocol_id"));
		        sloc.value = rs1.getString("struct_loc");
		        boolean flag = rs1.getString("active").compareTo("1") == 0;
		        conn.close();
		        return flag;
	        } else {
	        	conn.close();
	        	System.out.println("Somehow there are no rows(");
	        	return false;
	        }
		} catch (Exception e) {
	    	System.err.println("DBSelectQuery. An error has occured");
	    	e.printStackTrace();
	    	return false;
		}
	}
}
