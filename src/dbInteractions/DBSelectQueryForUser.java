package dbInteractions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dbElem.Query;

//check whether the login is already taken or not 

public class DBSelectQueryForUser implements DBProcedure{
	public static ArrayList<Query> dbConnect(String login) 
	{
		try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        //System.out.println("DBSelectUser connected");
	       
	        Statement statement = conn.createStatement();
	        login = SQLString.get(login);
		   	String que1 = "exec SelectQueryForUser @lg = " + login + ";";
	        ResultSet rs1 = statement.executeQuery(que1);
	        ArrayList<Query> lst = new ArrayList<Query>();
	        
	        if (rs1.next()) {
	        	Query q = new Query(rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4),rs1.getString(5),
	        			rs1.getString(6),rs1.getString(7),rs1.getString(8),rs1.getString(9),rs1.getString(10),rs1.getString(11));
	        	lst.add(q);
	        } else {
	        	conn.close();
	        	return null;
	        }
	        while (rs1.next()) {
	        	Query q = new Query(rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4),rs1.getString(5),
	        			rs1.getString(6),rs1.getString(7),rs1.getString(8),rs1.getString(9),rs1.getString(10),rs1.getString(11));
	        	lst.add(q);
	        }
	        	
	        conn.close();
	        return lst;
		} catch (Exception e) {
	    	System.err.println("DBSelectQueryForUser. An error has occured");
	    	e.printStackTrace();
	    	return null;
		}
	}
}

