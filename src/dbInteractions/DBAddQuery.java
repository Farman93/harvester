package dbInteractions;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import timePackage.AlarmStarter;

//Добавляет запрос в БД
public class DBAddQuery implements DBProcedure{
	final static String db_connect_string = "jdbc:jtds:sqlserver://localhost:1433";
	final static String dbName = "/HarvestingSchedule";
	final static String dbUser = "QueryLogin";
	final static String dbPssw = "fvpQas26yPAkggCF";
	public static int dbConnect(
			String name,
			String eURL,
			String sURL,
		    String pid,
		    String time,
		    String reg,
		    String uid,
		    String sloc) 
			/*@name nvarchar(max),
		    @eURL nvarchar(max),
		    @sURL nvarchar(max),
		    @pid int,
		    @time datetime,
		    @reg int,
		    @uid int,
		    @sloc nvarchar(max)*/
	{
		Connection conn = null;
		ResultSet generatedKeys = null;
		CallableStatement statementInsert = null;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
	        conn = DriverManager.getConnection(db_connect_string+dbName, dbUser, dbPssw);
	        //System.out.println("DBAddQuery connected");// TODO Убрать в финальной версии, либо переместить вывод в лог
	        
	        
		   	/*String que1 = "exec AddQuery ?,?,?,?,?,?,?,?";
		   	statement = conn.prepareStatement(que1, Statement.RETURN_GENERATED_KEYS);
		    statement.setQueryTimeout(90);
		    statement.setString(1,name);
		    statement.setString(2,eURL);
		    statement.setString(3,sURL);
		    statement.setString(4,pid);
		    statement.setString(5,time);
		    statement.setString(6,reg);
		    statement.setString(7,uid);
		    statement.setString(8,sloc);
		   	int affectedRows = statement.executeUpdate();
		   	
		   	if (affectedRows == 0) {
	            throw new SQLException("Creating query failed, no rows affected.");
	        }

	        generatedKeys = statement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            System.out.println(generatedKeys.getString(1));
	        	query_id = generatedKeys.getInt(1);
	        } else {
	            throw new SQLException("Creating query failed, no generated key obtained.");
	        }*/
	        int query_id;
	        
	        String que1 = "exec AddQuery ?,?,?,?,?,?,?,?,?;";
		   	statementInsert = conn.prepareCall(que1);
		    statementInsert.setString(1,name);
		    statementInsert.setString(2,eURL);
		    statementInsert.setString(3,sURL);
		    statementInsert.setString(4,pid);
		    statementInsert.setString(5,time);
		    statementInsert.setString(6,reg);
		    statementInsert.setString(7,uid);
		    statementInsert.setString(8,sloc);
		    statementInsert.registerOutParameter(9,java.sql.Types.INTEGER);
	        statementInsert.executeUpdate();
	        query_id = statementInsert.getInt(9);
	        AlarmStarter.run();
	        return query_id;
		} catch (Exception e) {
	    	System.err.println("DBAddQuery. An error has occured");// TODO Убрать в финальной версии, либо переместить вывод в лог
	    	e.printStackTrace();
	    	return -1;
		}
		finally {
		    if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException logOrIgnore) {}
		    if (statementInsert != null) try { statementInsert.close(); } catch (SQLException logOrIgnore) {}
		    if (conn != null) try { conn.close(); } catch (SQLException logOrIgnore) {}
		}
	}
}
