package dbInteractions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

//Adding a user with specific login and password

public class DBAddUser implements DBProcedure{
	final static String db_connect_string = "jdbc:sqlserver://localhost:1433;";
	final static String dbName = "databaseName=HarvestingSchedule";
	final static String dbUser = "UserLogin";
	final static String dbPssw = "p6X4mkyA7ZgxsfKQ";
	public static Boolean dbConnect(String login, String password) 
	{
		try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        //System.out.println("DBAddUser connected");
	       
	        Statement statement = conn.createStatement();
	        login = SQLString.get(login);
		   	String que1 = "exec AddUser @lg = " + login + ", @pw = '" + password + "';";
	        Boolean rs1 = statement.execute(que1);
	        conn.close();
	        return rs1;
		} catch (Exception e) {
	    	System.err.println("DBAddUser. An error has occured");
	    	e.printStackTrace();
	    	
	    	return false;
		}
	}
}
