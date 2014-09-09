package dbInteractions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Statement;

import wrap.IntWrapper;


//get datetime, schedule id and query id for the next harvest

public class DBCheckNextHarvest implements DBProcedure{
	final static String db_connect_string = "jdbc:sqlserver://localhost:1433;";
	final static String dbName = "databaseName=HarvestingSchedule";
	final static String dbUser = "UserNextHarvest";
	final static String dbPssw = "D594jc7QbCc5sDva";
	public static Boolean dbConnect(SQLDatetime datetime, IntWrapper sid, IntWrapper qid) 
	{
		try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        //System.out.println("DBCheckNextHarvest connected");
	       
	        Statement statement = conn.createStatement();
		   	String que1 = "exec CheckNextSchedule";
	        ResultSet rs1 = statement.executeQuery(que1);
	        if (!rs1.next())
	        	return false;
	        qid.value = Integer.parseInt(rs1.getString("query_id"));
	        sid.value = Integer.parseInt(rs1.getString("id"));
	        datetime.d = Integer.parseInt(rs1.getString("d"));
	        datetime.mo = Integer.parseInt(rs1.getString("mo"));
	        datetime.y = Integer.parseInt(rs1.getString("y"));
	        datetime.h = Integer.parseInt(rs1.getString("h"));
	        datetime.mi = Integer.parseInt(rs1.getString("mi"));
	        datetime.s = Integer.parseInt(rs1.getString("s"));
	        conn.close();
	        return true;
		} catch (Exception e) {
	    	System.err.println("DBCheckNextHarvest. An error has occured");
	    	e.printStackTrace();
	    	return false;
		}
	}
}
