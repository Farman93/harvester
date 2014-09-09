package dbInteractions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

//Добавляет запрос в БД
public class DBCheckQueryExistance implements DBProcedure{
	public static Boolean dbConnect(
			String eURL,
			String sURL,
		    String pid,
		    String time,
		    String reg,
		    String uid,
		    String sloc) 
			/*
		    @eURL nvarchar(max),
		    @sURL nvarchar(max),
		    @pid int,
		    @time datetime,
		    @reg int,
		    @uid int,
		    @sloc nvarchar(max)*/
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        //System.out.println("DBCheckQueryExistance connected");// TODO Убрать в финальной версии, либо переместить вывод в лог
	        eURL = SQLString.get(eURL);
	        sURL = SQLString.get(sURL);
	        time = SQLString.get(time);
	        sloc = SQLString.get(sloc);
	        ResultSet rs1;
	        Statement statement = conn.createStatement();
		   	String que1 = "exec CheckQueryExistance " 
		   			+ eURL + "," 
		   			+ sURL + "," 
		   			+ pid + "," 
		   			+ time + "," 
		   			+ reg + "," 
		   			+ uid + "," 
		   			+ sloc + ";";
		   	rs1 = statement.executeQuery(que1);
		   	boolean f = rs1.next();
		   	conn.close();
	        return f;
		} catch (Exception e) {
	    	System.err.println("DBCheckQueryExistance. An error has occured");// TODO Убрать в финальной версии, либо переместить вывод в лог
	    	e.printStackTrace();
	    	return false;
		}
	}
}
