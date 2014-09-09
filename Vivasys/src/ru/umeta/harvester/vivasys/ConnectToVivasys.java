package ru.umeta.harvester.vivasys;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by k.kosolapov on 04.09.14.
 */
public class ConnectToVivasys {
    final static String db_connect_string = "jdbc:sqlserver://vivasys.vivaldi.ru:1433;";
    final static String dbName = "databaseName=bibtest";
    final static String dbUser = "oleg.shorin";
    final static String dbPass = "Maktaba_mtihani";
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        final Connection conn = DriverManager.getConnection(db_connect_string + dbName, dbUser, dbPass);
        final ResultSet resultSet = conn.createStatement().executeQuery("SELECT TOP 10000 * FROM DocumentRecord");
        final List<String> xmlList = new ArrayList<>();
        while (resultSet.next()) {
            String fullXml = resultSet.getSQLXML("Metadata").getString();
            String cutXml = "'";
            xmlList.add(cutXml);
        }
        Collections.sort(xmlList);
        for(String xml : xmlList) {
            if(xml.contains("<title>Ин")) {
                System.out.println("-------------------------------------");
                System.out.println(xml);
                System.out.println("-------------------------------------");
            }
        }
        conn.close();
    }
}
