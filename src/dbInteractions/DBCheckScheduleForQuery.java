package dbInteractions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dbElem.ScheduleElement;

//Выдает 5 последних неудачных попыток запроса
public class DBCheckScheduleForQuery implements DBProcedure {
	final static String db_connect_string = "jdbc:sqlserver://localhost:1433;";
	final static String dbName = "databaseName=HarvestingSchedule";
	final static String dbUser = "QueryLogin";
	final static String dbPssw = "fvpQas26yPAkggCF";
	public static ArrayList<ScheduleElement> dbConnect(int qid, int uid) 
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        //System.out.println("DBSelectUser connected");
	       
	        Statement statement = conn.createStatement();
		   	String que1 = "exec CheckScheduleForQuery " + qid + "," + uid + ";";
	        ResultSet rs1 = statement.executeQuery(que1);
	        ArrayList<ScheduleElement> lst = new ArrayList<ScheduleElement>();
	        
	        if (rs1.next()) {
	        	ScheduleElement q = new ScheduleElement(rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4));
	        	lst.add(q);
	        } else {
	        	conn.close();
	        	return null;
	        }
	        while (rs1.next()) {
	        	ScheduleElement q = new ScheduleElement(rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4));
	        	lst.add(q);
	        }
	        	
	        conn.close();
	        return lst;
		        
		} catch (Exception e) {
	    	System.err.println("DBCheckScheduleForQuery. An error has occured");
	    	e.printStackTrace();
	    	return null;
		}
	}
}
