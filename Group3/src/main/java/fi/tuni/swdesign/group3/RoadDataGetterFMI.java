/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.jdom2.Document;
import org.jdom2.input.DOMBuilder;
import org.xml.sax.SAXException;

/**
 *
 * @author Amanda Dieuaide
 */
public class RoadDataGetterFMI {

    private static Document getDOMParsedDocument(final String fileName) throws MalformedURLException, ProtocolException, IOException {
        Document document = null;
        try {
            URL url = new URL(fileName);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Accept-Encoding", "jdom2");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            org.w3c.dom.Document w3cDocument = documentBuilder.parse(con.getInputStream());
            document = new DOMBuilder().build(w3cDocument);
            System.out.println(con.getInputStream());
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static void main(String[] args) throws Exception {
        //Document doc = getDOMParsedDocument("http://opendata.fmi.fi/wfs?service=WFS&version=2.0.0&request=describeStoredQueries");
        //System.out.println(doc);
        Document doc = getDOMParsedDocument("https://opendata.fmi.fi/wfs?request=getFeature&version=2.0.0&storedquery_id=fmi::observations::weather::multipointcoverage&bbox=21,61,22,62");
        System.out.println(doc);
    }

}
