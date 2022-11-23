/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Amanda Dieuaide
 */
public class RoadDataParserXML implements RoadDataParser{
    /**
     * Method that uses the w3cDocument created while fetching the data (in RoadDataGetterFMI)
     * It goes through the different elements/nodes of the document and sets the data queried by the User
     * With the setRoadWeatherData() function
     * @param w3cDocument
     * Location values:
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     * Specific location values for Predicted values:
     * @param lat
     * @param lon
     * @param startTime
     * @return
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException 
     */
    protected static RoadWeatherData getDOMParsedDocument(org.w3c.dom.Document w3cDocument, String minX, String maxX, String minY, String maxY, String lat, String lon, String startTime) throws MalformedURLException, ProtocolException, IOException {
        RoadWeatherData roadWeatherData = null;
        
        //Check if there's a document
        if(w3cDocument == null){
            System.out.println("No document could be fetched.");
            return roadWeatherData;
        }
        
        HashMap<String, RoadWeatherData> forecasts = new HashMap<>();
        
        String coordinates;
        if(minX.length() != 0){
            coordinates = minX+"/"+minY+"/"+maxX+"/"+maxY;
        } else {
            //Forecast queries only work with latitude/longitude values
            //Math.floor and Math.ceil give respectively the min and max int rounded value of a float
            coordinates = Math.floor(Float.parseFloat(lat)) + "/" + Math.floor(Float.parseFloat(lon)) + "/" + Math.ceil(Float.parseFloat(lat)) + "/" + Math.ceil(Float.parseFloat(lon));
        }
        roadWeatherData = new RoadWeatherData("suomi", coordinates, startTime);
     
        //Get elmt by tag name
        NodeList weatherForecastMembers = w3cDocument.getElementsByTagName("BsWfs:BsWfsElement");
        String time = startTime;
        
        //Get all elements in a 12h timestamp
        RoadWeatherData newRoadWeatherData = new RoadWeatherData("suomi", coordinates, time);
        for(int i = 0; i < weatherForecastMembers.getLength(); i++){
            Node weatherForecastMember = weatherForecastMembers.item(i);
            
            if(weatherForecastMember.getNodeType() == Node.ELEMENT_NODE){
                Element weatherForecastElement = (Element) weatherForecastMember;
                //System.out.println("BsWfsElement id: " + weatherForecastElement.getAttribute("gml:id") + "\n");
                
                //Only get values for hours startTime+0 to startTime+12
                if(weatherForecastElement.getAttribute("gml:id").equals("BsWfsElement.1.8.1")){
                    break;
                }
                NodeList weatherForecastElementDetails = weatherForecastElement.getChildNodes();
                
                //Get details from BsWfs elmts
                String param = "";
                String value = "";
                
                for(int j = 0; j < weatherForecastElementDetails.getLength(); j++){
                    Node detail = weatherForecastElementDetails.item(j);
                    if(detail.getNodeType() == Node.ELEMENT_NODE){
                        Element detailElt = (Element) detail;
                        
                        //Change time value for Forecast up to 12h after startTime
                        if(detailElt.getTagName().equals("BsWfs:Time") && !time.equals(detailElt.getTextContent())){
                            time = detailElt.getTextContent();
                            newRoadWeatherData = new RoadWeatherData("suomi", coordinates, time);
                        }
                        
                        //Specific Details
                        if(detailElt.getTagName().equals("BsWfs:ParameterName")){
                            param = detailElt.getTextContent();
                        }
                        if(detailElt.getTagName().equals("BsWfs:ParameterValue")){
                            value = detailElt.getTextContent();
                        }
                        
                        if(!param.isEmpty() && !value.isEmpty()){
                            if(time.equals(startTime)){
                                roadWeatherData = setRoadWeatherData(roadWeatherData, param, value);
                            } else {
                                newRoadWeatherData = setRoadWeatherData(newRoadWeatherData, param, value);
                            }
                        }
                        
                    }
                }
            }
            if(!newRoadWeatherData.time.equals(startTime)){
                forecasts.put(time, newRoadWeatherData);
            }
        }
        roadWeatherData.setForecasts(forecasts);
        return roadWeatherData;
    }
    
    /**
     * Method to set the fetched data from the API into the RoadWeatherData object
     * Depending on the parameters in the query
     * @param roadWeatherData
     * @param param
     * @param value
     * @return 
     */
    protected static RoadWeatherData setRoadWeatherData(RoadWeatherData roadWeatherData, String param, String value){
        float floatValue = Float.parseFloat(value);

        if(param.equals("t2m") || param.equals("temperature")){
            //System.out.println("The temperature was " + weatherData.get(param) + " °C.");
            roadWeatherData.setTemperature(floatValue);
        }
        if(param.equals("ws_10min") || param.equals("windspeedms")){
            //System.out.println("The wind speed was " + weatherData.get(param) + " m/s.");
            roadWeatherData.setWind(floatValue);
        }
        if(param.equals("n_man")){
            //System.out.println("The cloud cover was " + weatherData.get(param));
            roadWeatherData.setCloudiness(floatValue);
        }
        if(param.equals("TA_PT1H_AVG")){
            //System.out.println("The average temperature was " + weatherData.get(param) + " °C.");
            roadWeatherData.setAVGTemperature(floatValue);
        }
        if(param.equals("TA_PT1H_MAX")){
            //System.out.println("The maximum temperature was " + weatherData.get(param) + " °C.");
            roadWeatherData.setMAXTemperature(floatValue);
        }
        if(param.equals("TA_PT1H_MIN")){
            //System.out.println("The minimum temperature was " + weatherData.get(param) + " °C.");
            roadWeatherData.setMINTemperature(floatValue);
        }
        return roadWeatherData;
    }

    public static void main(String[] args) throws Exception {
        //Test: Parsing Observed and Predicted Weather Data using data fetched by RoadDataGetterFMI
        System.out.println("\nEx: Observed temperature, wind speed and cloudiness for the coordinate area [23,61,24,62] between 10am and 10pm on 2022-11-16 in 2h intervals");
        org.w3c.dom.Document observationDOM = RoadDataGetterFMI.getDOMDocument("fmi::observations::weather::simple", "23", "61", "24", "62", "", "", "2022-11-16T10:00:00Z", "2022-11-16T22:00:00Z", "t2m,ws_10min,n_man,TA_PT1H_AVG,TA_PT1H_MAX,TA_PT1H_MIN");
        RoadWeatherData test = getDOMParsedDocument(observationDOM, "23", "61", "24", "62", "", "", "2022-11-16T10:00:00Z");
        if(test != null){
            System.out.println(test.toString());
            System.out.println(test.getForecasts());
        }
                
        System.out.println("\nEx: Forecast for the temperature and the wind speed in 2h intervals for Tampere area (coordinates 61.49911 and 23.78712) for 30/11/2022 (! starting time has to be in the future else values are NaN) using the HARMONIE weather model");
        org.w3c.dom.Document forecastDOM = RoadDataGetterFMI.getDOMDocument("fmi::forecast::harmonie::surface::point::simple", "", "", "", "", "61.49911", "23.78712", "2022-11-25T06:00:00Z", "2022-11-25T18:00:00Z", "temperature,windspeedms");
        RoadWeatherData test2 = getDOMParsedDocument(forecastDOM, "", "", "", "", "61.49911", "23.78712", "2022-11-25T06:00:00Z");
        if(test2 != null){
            System.out.println(test2.toString());
            System.out.println(test2.getForecasts());
        }
    }
}
