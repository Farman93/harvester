package ru.umeta.harvester.vivasys;

import gov.loc.mods.v3.IdentifierDefinition;
import gov.loc.mods.v3.ModsDocument;

import gov.loc.mods.v3.NameDefinition;
import gov.loc.mods.v3.TitleInfoDefinition;
import org.apache.xmlbeans.XmlException;
import org.xml.sax.SAXException;


import javax.xml.transform.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by k.kosolapov on 04.09.14.
 */
public class ConnectToVivasys {
    final static String db_connect_string = "jdbc:sqlserver://vivasys.vivaldi.ru:1433;";
    final static String dbName = "databaseName=bibtest";
    final static String dbUser = "oleg.shorin";
    final static String dbPass = "Maktaba_mtihani";

    static class UrlMetadata implements Comparable<UrlMetadata>{
        final private String url;

        public String getUrl() {
            return url;
        }

        public String getXml() {
            return xml;
        }

        final private String xml;
        public UrlMetadata(String url, String xml) {
            this.url = url;
            this.xml = xml;
        }

        @Override
        public int compareTo(UrlMetadata urlMetadata) {
            return (this.xml.compareTo(urlMetadata.getXml()));
        }
    }

    private static Connection getLocalConnection() throws ClassNotFoundException, SQLException, FileNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        final ResourceBundle bundle = ResourceBundle.getBundle("database");
        final String server = bundle.getString("database");
        final String user = bundle.getString("user");
        final String pass = bundle.getString("pass");
        return DriverManager.getConnection("jdbc:sqlserver://"+ server, user, pass);
    }



    public static void main(String[] args)
            throws ClassNotFoundException, SQLException, IOException, SAXException, TransformerException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            try (Connection connLocal = getLocalConnection()) {
                for (int i = 1; i < 100000; i++) {
                    final File file = new File(String.format("C:\\vyvasys\\vyvasys_row1-99999\\vyvasys_row%d.xml",i));
                    ModsDocument modsDocument = null;
                    try {
                        modsDocument = ModsDocument.Factory.parse(file);
                    } catch (XmlException e) {
                        e.printStackTrace();
                    }
                    String fullXml = modsDocument.xmlText();
                    final IdentifierDefinition[] identifierArray = modsDocument.getMods().getIdentifierArray();
                    final IdentifierDefinition identifier = identifierArray.length > 0 ? identifierArray[0] : null;
                    String isbn = null;
                    if (identifier != null) {
                        isbn = "isbn".compareTo(identifier.getType()) == 0 ? identifier.getStringValue() : "null";
                    } else {
                        isbn = "null";
                    }

                    final TitleInfoDefinition[] titleInfoArray = modsDocument.getMods().getTitleInfoArray();
                    final String title = titleInfoArray.length > 0 ? titleInfoArray[0].toString() : "null";
                    final NameDefinition[] nameArray = modsDocument.getMods().getNameArray();
                    final String name = nameArray.length > 0 ? nameArray[0].toString() : "null";

                    final String query = "INSERT INTO dbo.Document(title, isbn, xml, author_name) VALUES(?,?,?,?)";
                    PreparedStatement statement = connLocal.prepareStatement(query);
                    statement.setString(1, title);
                    statement.setString(2, isbn);
                    statement.setString(3, fullXml);
                    statement.setString(4, name);
                    //Statement statement = connLocal.createStatement();
                    statement.executeUpdate();
                    if (i % 10000 == 0) {
                        System.out.println(i);
                    }

            }
        }



       /* Collections.sort(urlMetadatas);
        PrintWriter writer = new PrintWriter("c://export//vivasys_mods_titles.xml", "UTF-8");
        i = 0;
        for(UrlMetadata urlMetadata : urlMetadatas) {

            String url = urlMetadata.getUrl();
            String xml = urlMetadata.getXml();
            writer.println("+++++++++++++++++++++++++++++++++++++");
            writer.println(url);
            writer.println("-------------------------------------");
            writer.println(xml);
            TransformerFactory factory = new TransformerFactoryImpl();
            Source xslt = new StreamSource(new File("C://import//modsrdf.xsl"));
            Transformer transformer = factory.newTransformer(xslt);

            File xmlFile = File.createTempFile("ru.umeta.harvesting.vivasys.tmpXmlFile", ".xml");

            PrintWriter tmpWriter = new PrintWriter(xmlFile);
            tmpWriter.print(xml);
            tmpWriter.close();

            Source text = new StreamSource(xmlFile);
            transformer.transform(text, new StreamResult(new File("c://export//output" + i + ".xml")));
            i++;
        }
        writer.close();*/
    }
}
