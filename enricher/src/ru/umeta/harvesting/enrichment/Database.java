package ru.umeta.harvesting.enrichment;

import java.io.FileNotFoundException;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by k.kosolapov on 16.09.2014.
 */
public class Database {

    private Connection getConnection() throws ClassNotFoundException, SQLException, FileNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        final ResourceBundle bundle = ResourceBundle.getBundle("database");
        final String server = bundle.getString("database");
        final String user = bundle.getString("user");
        final String pass = bundle.getString("pass");
        return DriverManager.getConnection("jdbc:sqlserver://"+ server, user, pass);
    }

    public List<Document> checkNew() throws ClassNotFoundException, SQLException, FileNotFoundException {
        try (Connection conn = getConnection()) {
            List<Document> documents = new ArrayList<>();
            Statement statement = conn.createStatement();
            String query = "SELECT id, title, isbn, xml FROM dbo.Document WHERE enriched_id IS NULL";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String isbn = resultSet.getString("isbn");
                String xml = resultSet.getSQLXML("xml").toString();
                documents.add(new Document(id, title, isbn, xml));
            }
            return documents;
        }
    }

    public Document getEnrichedDocument(Document source) throws FileNotFoundException, SQLException, ClassNotFoundException {
        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            final String sourceIsbn = source.isbn;
            final String sourceTitle = source.title;
            String query = "SELECT id, title, isbn, xml FROM dbo.EnrichedDocument WHERE isbn IS NOT NULL AND isbn = " + sourceIsbn;
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String xml = resultSet.getSQLXML("xml").toString();

                return (new Document(id, title, sourceIsbn, xml));
            } else {
                query = "SELECT id, title, isbn, xml FROM dbo.EnrichedDocument WHERE TITLE = " + sourceTitle;
                resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String isbn = resultSet.getString("isbn");
                    String xml = resultSet.getSQLXML("xml").toString();
                    return (new Document(id, sourceTitle, isbn, xml));
                } else {
                    return null;
                }
            }
        }

    }

    public boolean createEnrichedDocument(Document source) throws FileNotFoundException, SQLException, ClassNotFoundException {
        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            final int id = source.id;
            final String title = source.title;
            final String isbn = source.isbn;
            final String xml = source.xml;
            String query = MessageFormat.format("INSERT INTO dbo.EnrichedDocument(title, isbn, xml) VALUES({0},{1},{2})", title, isbn, xml);
            return statement.execute(query);
        }
    }

    public boolean setEnrichedForDocument(int source_id, int enriched_id) throws FileNotFoundException, SQLException, ClassNotFoundException {
        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            String query = MessageFormat.format("UPDATE dbo.Document SET enriched_id = {0} WHERE id = {1}", enriched_id, source_id);
            return statement.execute(query);
        }
    }
}
