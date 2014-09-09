package dbInteractions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import timePackage.AlarmStarter;

public class DBUpdateQuery implements DBProcedure {
	public static Boolean dbConnect(int qid, 
			String name,
			String eURL,
			String sURL,
		    String pid,
		    String time,
		    String reg,
		    String sloc
		    ) 
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        name = SQLString.get(name);
	        eURL = SQLString.get(eURL);
	        sURL = SQLString.get(sURL);
	        time = SQLString.get(time);
	        sloc = SQLString.get(sloc);
	        Statement statement = conn.createStatement();
		   	String que1 = "exec UpdateQuery " 
		   			+ qid + ","
		   			+ name + "," 
		   			+ eURL + "," 
		   			+ sURL + "," 
		   			+ pid + "," 
		   			+ time + "," 
		   			+ reg + "," 
		   			+ sloc + ";";
		   	statement.execute(que1);
		   	conn.close();
		   	AlarmStarter.run();
	        return true;
		} catch (Exception e) {
	    	System.err.println("DBSelectQuery. An error has occured");
	    	e.printStackTrace();
	    	return false;
		}
	}
}
