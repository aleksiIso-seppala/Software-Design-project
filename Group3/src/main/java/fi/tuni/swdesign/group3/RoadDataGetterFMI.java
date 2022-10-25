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
import java.util.HashMap;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Amanda Dieuaide
 */
public class RoadDataGetterFMI {

    private static void getDOMParsedDocument(String queryName, String minX, String maxX, String minY, String maxY, String lat, String lon, String timestep, String startTime, String endTime, String parameters) throws MalformedURLException, ProtocolException, IOException {
        //Placeholder Data stockage -> TODO: create WeatherData Class
        HashMap<String, String> weatherData = new HashMap<String, String>();
        
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
            if(timestep.length() != 0){
                fileName = fileName + "&timestep=" + timestep;
            }
            
            //Add Start/End time
            if(startTime.length() != 0){
                fileName = fileName + "&starttime=" + startTime + "&endtime=" + endTime;
            }
            
            //Add params
            fileName = fileName + "&parameters=" + parameters;
            //System.out.println(fileName);
            
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
                //System.out.println(infoString);
                
                //Get Document
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = factory.newDocumentBuilder();
                org.w3c.dom.Document w3cDocument = documentBuilder.parse(con.getInputStream());
                
                //Normalize XML
                w3cDocument.getDocumentElement().normalize();
                
                //Get elmt by tag name
                NodeList weatherForecastMembers = w3cDocument.getElementsByTagName("BsWfs:BsWfsElement");
                
                //Get only first 3 elmts
                for(int i = 0; i < 3; i++){
                    Node weatherForecastMember = weatherForecastMembers.item(i);
                    if(weatherForecastMember.getNodeType() == Node.ELEMENT_NODE){
                        Element weatherForecastElement = (Element) weatherForecastMember;
                        //System.out.println("BsWfsElement id: " + weatherForecastElement.getAttribute("gml:id") + "\n");
                        
                        NodeList weatherForecastElementDetails = weatherForecastElement.getChildNodes();
                        
                        //Get details from BsWfs elmts
                        String param = "";
                        String value = "";
                                
                        for(int j = 0; j < weatherForecastElementDetails.getLength(); j++){
                            Node detail = weatherForecastElementDetails.item(j);
                            if(detail.getNodeType() == Node.ELEMENT_NODE){
                                Element detailElt = (Element) detail;
                                //System.out.println(detailElt.getTagName() + ": " + detailElt.getTextContent());
                                
                                //Specific Details
                                //TODO: put data in WeatherData Object
                                if(detailElt.getTagName().equals("BsWfs:ParameterName")){
                                    param = detailElt.getTextContent();
                                }
                                if(detailElt.getTagName().equals("BsWfs:ParameterValue")){
                                    value = detailElt.getTextContent();
                                }
                            }
                            if(param.length() != 0 && value.length() != 0){
                                weatherData.put(param, value);
                                //Reset
                                param = "";
                                value = "";
                            }
                        }
                        
                    }
                }
                readWeatherData(weatherData);
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    
    //TODO: user getter from WeatherData object rather than placeholder HashMap
    private static void readWeatherData(HashMap<String, String> weatherData){
        for (String param : weatherData.keySet()) {
            if(param.equals("t2m") || param.equals("temperature")){
                System.out.println("The temperature was " + weatherData.get(param) + " °C.");
            }
            if(param.equals("ws_10min") || param.equals("windspeedms")){
                System.out.println("The wind speed was " + weatherData.get(param) + " m/s.");
            }
            if(param.equals("n_man")){
                System.out.println("The cloud cover was " + weatherData.get(param));
            }
            if(param.equals("TA_PT1H_AVG")){
                System.out.println("The average temperature was " + weatherData.get(param) + " °C.");
            }
            if(param.equals("TA_PT1H_MAX")){
                System.out.println("The maximum temperature was " + weatherData.get(param) + " °C.");
            }
            if(param.equals("TA_PT1H_MIN")){
                System.out.println("The minimum temperature was " + weatherData.get(param) + " °C.");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        //Test General Function
        //String queryName, String minX, String maxX, String minY, String maxY, String lat, String lon, String timestep, String startTime, String endTime, String parameters
        //Ex: temperature, wind speed and cloudiness for the coordinate area [23,61,24,62] for the last 12 hours in 30 minute intervals
        System.out.println("\nEx: temperature, wind speed and cloudiness for the coordinate area [23,61,24,62] for the last 12 hours in 30 minute intervals");
        getDOMParsedDocument("fmi::observations::weather::simple", "23", "61", "24", "62", "", "", "30", "", "", "t2m,ws_10min,n_man");
        
        //Ex: hourly observations for the average, maximum and minimum temperature for the area [23,61,24,62] between 11am and 4pm on 19th January, 2021
        System.out.println("\nEx: hourly observations for the average, maximum and minimum temperature for the area [23,61,24,62] between 11am and 4pm on 19th January, 2021");
        getDOMParsedDocument("fmi::observations::weather::hourly::simple", "23", "61", "24", "62", "", "", "", "2021-01-19T09:00:00Z", "2021-01-19T14:00:00Z", "TA_PT1H_AVG,TA_PT1H_MAX,TA_PT1H_MIN");

        //Ex: forecast for the temperature and the wind speed in 30 minute intervals for Tampere area (coordinates 61.49911 and 23.78712) for the next 24 hours (! starting time has to be in the future else values are NaN) using the HARMONIE weather model
        System.out.println("\nEx: forecast for the temperature and the wind speed in 30 minute intervals for Tampere area (coordinates 61.49911 and 23.78712) for the next 24 hours (! starting time has to be in the future else values are NaN) using the HARMONIE weather model \n");
        getDOMParsedDocument("fmi::forecast::harmonie::surface::point::simple", "", "", "", "", "61.49911", "23.78712", "30", "2022-10-27T06:00:00Z", "2022-10-28T06:00:00Z", "temperature,windspeedms");
    }

}
