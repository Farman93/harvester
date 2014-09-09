package dbInteractions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import wrap.IntWrapper;


//Checking if the password for the input login match the hash

public class DBCheckPass implements DBProcedure{
	final static String db_connect_string = "jdbc:sqlserver://localhost:1433;";
	final static String dbName = "databaseName=HarvestingSchedule";
	final static String dbUser = "UserLogin";
	final static String dbPssw = "p6X4mkyA7ZgxsfKQ";
	public static Boolean dbConnect(String login, String password, IntWrapper uid) 
	{
		try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        //System.out.println("DBCheckPass connected");
	       
	        Statement statement = conn.createStatement();
		   	String que1 = "exec CheckPass @lg = '" + login + "', @pw = '" + password + "';";
	        ResultSet rs1 = statement.executeQuery(que1);
	        Boolean f = rs1.next();
	        if (f)
	        	uid.value = rs1.getInt("id");
	        conn.close();
	        return f;
		} catch (Exception e) {
	    	System.err.println("DBCheckPass. An error has occured");
	    	e.printStackTrace();
	    	return false;
		}
	}
}

