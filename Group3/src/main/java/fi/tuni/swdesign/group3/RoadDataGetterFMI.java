/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Amanda Dieuaide
 */
public class RoadDataGetterFMI {

    public static org.w3c.dom.Document getDOMDocument(String queryName, String minX, String maxX, String minY, String maxY, String lat, String lon, String startTime, String endTime, String parameters) throws MalformedURLException, ProtocolException, IOException {
        org.w3c.dom.Document w3cDocument = null;
        
        if(checkIfCorrectQuery(queryName, parameters)){
            System.out.println("Incorrect query.");
            return w3cDocument;
        }

        try {
            String fileName = "http://opendata.fmi.fi/wfs?service=WFS&version=2.0.0&request=getFeature&storedquery_id=";
            
            //Add query type
            fileName = fileName + queryName;
            
            //Add coordinates to query
            if(minX.length() != 0){
                String bbox = minX + "," + maxX + "," + minY + "," + maxY;
                fileName = fileName + "&bbox=" + bbox;
            } else {
                String latlon = lat + "," + lon;
                fileName = fileName + "&latlon=" + latlon;
            }
            
            //Add Timestep
            String timestep = "120"; //Always 2h by 2h to get the mandatory values more easily
            if(timestep.length() != 0){
                fileName = fileName + "&timestep=" + timestep;
            }
            
            //Add Start/End time
            if(startTime.length() != 0){
                fileName = fileName + "&starttime=" + startTime + "&endtime=" + endTime;
            }
            
            //Add params
            fileName = fileName + "&parameters=" + parameters;

            URL url = new URL(fileName);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            
            //Check if the connection is made
            int resCode = con.getResponseCode();
            if(resCode != 200){
                throw new RuntimeException("HttpResponseCode: " + resCode);
            } else {
                //Test: Get data from the API
                StringBuilder infoString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                
                while(scanner.hasNext()){
                    infoString.append(scanner.nextLine());
                }
                
                scanner.close();

                //Get Document
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = factory.newDocumentBuilder();
                w3cDocument = documentBuilder.parse(con.getInputStream());
                
                //Normalize XML
                w3cDocument.getDocumentElement().normalize();
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return w3cDocument;
    }

    private static boolean checkIfCorrectQuery(String query, String param){
        return (query.contains("fmi::forecast") && (param.contains("TA_PT1H") || param.contains("t2m") || param.contains("ws_10min") || param.contains("n_man")))
                || (query.equals("fmi::observations") && (param.contains("TA_PT1H") || param.contains("temperature") || param.contains("windspeedms")));
    }
}