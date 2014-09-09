package dbInteractions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBCheckCookie implements DBProcedure {
	final static String db_connect_string = "jdbc:sqlserver://localhost:1433;";
	final static String dbName = "databaseName=HarvestingSchedule";
	final static String dbUser = "UserLogin";
	final static String dbPssw = "p6X4mkyA7ZgxsfKQ";
	
	public static String dbConnect(int cookie) 
	{
		try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        //System.out.println("connected");
	        String name = null;
	        Statement statement = conn.createStatement();
		   	String que1 = "exec CheckCookie @ck = " + cookie + ";";
	        ResultSet rs1 = statement.executeQuery(que1);
	        if (rs1.next()) {
	        	name = rs1.getString("login");
	        }
	        conn.close();
	        return name;
		} catch (Exception e) {
	    	System.err.println("DBCheckPass. An error has occured");
	    	e.printStackTrace();
	    	return null;
		}
	}

}
