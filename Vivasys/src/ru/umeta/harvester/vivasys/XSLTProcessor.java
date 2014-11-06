package ru.umeta.harvester.vivasys;

import net.sf.saxon.TransformerFactoryImpl;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by k.kosolapov on 14.10.2014.
 */
public class XSLTProcessor {
    private XSLTProcessor(){};

    public static void process(File input, File xsl) throws TransformerException, IOException {
        TransformerFactory factory = new TransformerFactoryImpl();
        Source xslSource = new StreamSource(xsl);
        Transformer transformer = factory.newTransformer(xslSource);



        Source text = new StreamSource(input);
        transformer.transform(text, new StreamResult(new File(input.getPath()+xsl.getName()+".xml")));

    }

    public static void process(String inputPath, String xslPath) throws TransformerException, IOException {
        process(new File(inputPath), new File(xslPath));
    }


    public static void main(String[] args) {
        try {
            process("C:\\vyvasys\\vyvasys_row10xxxx\\vyvasys_row109978.xml", "C:\\work\\MODS2MARC21slim.xsl");
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
