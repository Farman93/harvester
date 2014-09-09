package dbInteractions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//check whether the login is already taken or not 

public class DBSelectUser implements DBProcedure{
	final static String db_connect_string = "jdbc:sqlserver://localhost:1433;";
	final static String dbName = "databaseName=HarvestingSchedule";
	final static String dbUser = "UserLogin";
	final static String dbPssw = "p6X4mkyA7ZgxsfKQ";
	public static Boolean dbConnect(String login) 
	{
		try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        //System.out.println("DBSelectUser connected");
	       
	        Statement statement = conn.createStatement();
	        login = SQLString.get(login);
		   	String que1 = "exec SelectUser @lg = " + login + ";";
	        ResultSet rs1 = statement.executeQuery(que1);
	        Boolean f = rs1.next();
	        conn.close();
	        return f;
		} catch (Exception e) {
	    	System.err.println("DBSelectUser. An error has occured");
	    	e.printStackTrace();
	    	return false;
		}
	}
}

