package ru.umeta.harvester.vivasys;

import net.sf.saxon.TransformerFactoryImpl;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
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
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException, IOException, SAXException, TransformerException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        final Connection conn = DriverManager.getConnection(db_connect_string + dbName, dbUser, dbPass);
        final ResultSet resultSet = conn.createStatement().executeQuery(
                "SELECT Url, Metadata FROM DocumentRecord");
        final List<UrlMetadata> urlMetadatas = new ArrayList<>();
        int i = 0;
        while (resultSet.next()) {
            i++;
            String fullXml = resultSet.getSQLXML("Metadata").getString();
            String url = resultSet.getString("Url");
            String titleInfo = null;
            try {
                titleInfo = fullXml.split("<titleInfo>")[1].split("</titleInfo>")[0];


            } catch(Exception e) {
                //System.out.print(fullXml);
                continue;
            }
            if (titleInfo.contains("Их имена забыться не должны")) {
                urlMetadatas.add(new UrlMetadata(url, fullXml));
            }

            if (i % 10000 == 0) {
                System.out.println(i);
            }
        }
        Collections.sort(urlMetadatas);
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
        writer.close();
        conn.close();
    }
}
