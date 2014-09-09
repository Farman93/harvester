package dbInteractions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBUpdateScheduleStatus implements DBProcedure {
	final static String db_connect_string = "jdbc:sqlserver://localhost:1433;";
	final static String dbName = "databaseName=HarvestingSchedule";
	final static String dbUser = "ScheduleLogin";
	final static String dbPssw = "jEBNhTKsn9cugTup";
	public static Boolean dbConnect(
			int sid,
			int status) 
	{
		try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        //System.out.println("HHHHHH.DBUpdateScheduleStatus connected");
	        Statement statement = conn.createStatement();
		   	String que1 = "exec UpdateStatusForSchedule " 
		   			+ sid + "," 
		   			+ status + ";";
		   	Boolean rs1 = statement.execute(que1);
		   	conn.close();
	        return rs1;
		} catch (Exception e) {
	    	System.err.println("DBUpdateScheduleStatus. An error has occured");
	    	e.printStackTrace();
	    	return false;
		}
	}
}
