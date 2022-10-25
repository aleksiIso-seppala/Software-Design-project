/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.swdesign.group3;

import java.net.URL;
import java.net.MalformedURLException;
import com.google.gson.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;


/**
 *
 * @author Aleksi Iso-Seppälä
 */
public class RoadDataParser {

    /**
     * @param args the command line arguments
     */
    
    public static RoadTrafficData getRoadData(String location, String minX, String maxX,
            String minY, String maxY) throws IOException{
        
        var roadData = readFirstCondition(location, minX, maxX, minY, maxY);
        var maintenanceTasks = readMaintenanceTasks(minX, maxX, minY, maxY);
        roadData.setMaintenanceTasks(maintenanceTasks);
        
        int messages = readTrafficMessages("TRAFFIC_ANNOUNCEMENT");
        messages += readTrafficMessages("EXEMPTED_TRANSPORT");
        messages += readTrafficMessages("WEIGHT_RESTRICTION");
        messages += readTrafficMessages("ROAD_WORK");
        roadData.setNumberOfTrafficMessages(messages);
        
        return roadData;
    }
    
    
    private static ArrayList<String> readAllMaintenanceTasks() throws MalformedURLException, IOException{
        
        String sUrl = "https://tie.digitraffic.fi/api/maintenance/v1/tracking/tasks";        

        URL url = new URL(sUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("Accept-Encoding", "gzip");

        GZIPInputStream gzipInput = new GZIPInputStream(con.getInputStream());
        InputStreamReader reader = new InputStreamReader(gzipInput, "UTF-8");
        
        Gson gson = new Gson();
        JsonArray response = gson.fromJson(reader, JsonArray.class);
        
        ArrayList<String> tasks = new ArrayList<>();
        for(var object: response){
            JsonObject task = (JsonObject) object;
            String id = task.get("id").getAsString();
            String nameFi = task.get("nameFi").getAsString();
            String nameSv = task.get("nameSv").getAsString();
            String nameEn = task.get("nameEn").getAsString();
            tasks.add(nameEn);
        }
        return tasks;

    }
    
    private static HashMap<String, Integer> readMaintenanceTasks(String minX, String maxX,
            String minY, String maxY) throws MalformedURLException, IOException{
        
        String sUrl = "https://tie.digitraffic.fi/api/maintenance/v1/tracking/routes/latest?";        
        String coordinates = "xMin="+minX+"&yMin="+minY+"&xMax"+maxX+"&yMax"+maxY; 
        String fullUrl = sUrl+coordinates+"&domain=all";
        
        URL url = new URL(fullUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("Accept-Encoding", "gzip");

        GZIPInputStream gzipInput = new GZIPInputStream(con.getInputStream());
        InputStreamReader reader = new InputStreamReader(gzipInput, "UTF-8");
        
        Gson gson = new Gson();
        JsonObject response = gson.fromJson(reader, JsonObject.class);
        JsonArray features = response.getAsJsonArray("features");
        
        HashMap<String, Integer> taskTypes = new HashMap<>();
        for(var feature : features){
            JsonObject task = (JsonObject) feature;
            JsonObject properties = task.getAsJsonObject("properties");
            String id = properties.get("id").getAsString();
            JsonArray taskNames = properties.getAsJsonArray("tasks");
            
            for(var inner : taskNames){
                String taskName = inner.getAsString();
                taskTypes.merge(taskName, 1, Integer::sum);
            }
        }
        return taskTypes;
        
    }
    
    public static void readRoadConditions(String minX, String maxX, String minY, String maxY) throws MalformedURLException, IOException{
        
        String sUrl = "https://tie.digitraffic.fi/api/v3/data/road-conditions/";        
        String coordinates = minX+"/"+minY+"/"+maxX+"/"+maxY; 
        String fullUrl = sUrl+coordinates;
        
        URL url = new URL(fullUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("Accept-Encoding", "gzip");
        
        GZIPInputStream gzipInput = new GZIPInputStream(con.getInputStream());
        InputStreamReader reader = new InputStreamReader(gzipInput, "UTF-8");
        
        Gson gson = new Gson();
        JsonObject response = gson.fromJson(reader, JsonObject.class);
        JsonArray weatherData = response.getAsJsonArray("weatherData");
        
        for(var data: weatherData){
            JsonObject forecast = (JsonObject) data;
            JsonArray roadConditions = forecast.getAsJsonArray("roadConditions");
            for(var road : roadConditions){
                JsonObject condition = road.getAsJsonObject();
                String time = condition.get("forecastName").getAsString();
                String overallRoadCondition = condition.get("overallRoadCondition").getAsString();
                String weatherSymbol = condition.get("weatherSymbol").getAsString();

                if(condition.get("type").getAsString().equals("FORECAST")){
                    JsonObject forecastCondition = condition.getAsJsonObject("forecastConditionReason");

                    forecastCondition.get("precipitationCondition").getAsString();
                    forecastCondition.get("roadCondition").getAsString();

                }
            }
        }
    }
    
    public static RoadTrafficData readFirstCondition(String location, String minX, String maxX,
            String minY, String maxY) throws MalformedURLException, IOException{
        
        String sUrl = "https://tie.digitraffic.fi/api/v3/data/road-conditions/";        
        String coordinates = minX+"/"+minY+"/"+maxX+"/"+maxY; 
        String fullUrl = sUrl+coordinates;
        URL url = new URL(fullUrl);
        
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("Accept-Encoding", "gzip");
        
        GZIPInputStream gzipInput = new GZIPInputStream(con.getInputStream());
        InputStreamReader reader = new InputStreamReader(gzipInput, "UTF-8");
        
        Gson gson = new Gson();
        JsonObject response = gson.fromJson(reader, JsonObject.class);
        JsonArray weatherData = response.getAsJsonArray("weatherData");
        
        var data = weatherData.get(0);
        JsonObject allData = (JsonObject) data;
        JsonArray roadConditions = allData.getAsJsonArray("roadConditions");

        RoadTrafficData trafficData = new RoadTrafficData(location, coordinates,"0h");
        HashMap<String, RoadTrafficDataForecast> forecasts = new HashMap<>();

        for(var road : roadConditions){

            JsonObject condition = road.getAsJsonObject();
            String type = condition.get("type").getAsString();

            String time = condition.get("forecastName").getAsString(); 
            String overallRoadCondition = condition.get("overallRoadCondition").getAsString();
            String weatherSymbol = condition.get("weatherSymbol").getAsString();
            String temperature = condition.get("temperature").getAsString();
            double windSpeed = condition.get("windSpeed").getAsDouble();

            if(type.equals("OBSERVATION")){
                
                trafficData.setOverAllCondition(overallRoadCondition);
                trafficData.setWeatherSymbol(weatherSymbol);
                trafficData.setTemperature(temperature);
                trafficData.setWindSpeed(windSpeed);                
            }
            else if(type.equals("FORECAST")){
                RoadTrafficDataForecast trafficForecast = 
                        new RoadTrafficDataForecast(location,coordinates,time);                    

                trafficForecast.setOverAllCondition(overallRoadCondition);
                trafficForecast.setWeatherSymbol(weatherSymbol);
                trafficForecast.setTemperature(temperature);
                trafficForecast.setWindSpeed(windSpeed);  

                JsonObject forecastCondition = 
                        condition.getAsJsonObject("forecastConditionReason");

                if(forecastCondition.has("precipitationCondition")){
                    String precipitation = forecastCondition.get("precipitationCondition").getAsString();
                    trafficForecast.setPrecipitation(precipitation);              
                }

                if(forecastCondition.has("roadCondition")){
                    String roadCondition = forecastCondition.get("roadCondition").getAsString();
                    trafficForecast.setOverAllCondition(roadCondition); 
                }

                forecasts.put(time, trafficForecast);

            }
        }
        trafficData.setForecasts(forecasts);
        return trafficData;
         
    }
    
    public static int readTrafficMessages(String sType) throws MalformedURLException, IOException{
        
        String sUrl = "https://tie.digitraffic.fi/api/traffic-message/v1/messages?"
                + "inactiveHours=0&includeAreaGeometry=true&situationType=";
        String fullUrl = sUrl+sType;
        URL url = new URL(fullUrl);
        
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("Accept-Encoding", "gzip");
        
        GZIPInputStream gzipInput = null;
        try {
            gzipInput = new GZIPInputStream(con.getInputStream());
        } catch (IOException ex) {
            return 0;

        }
        
        InputStreamReader reader = new InputStreamReader(gzipInput, "UTF-8");
        
        Gson gson = new Gson();
        JsonObject response = gson.fromJson(reader, JsonObject.class);
        JsonArray trafficMessages = response.getAsJsonArray("features");
        
        return trafficMessages.size();
        
//        for(var element : trafficMessages){
//            JsonObject trafficMessage = (JsonObject) element;
//            
//            JsonObject geometry = trafficMessage.get("geometry").getAsJsonObject();
//            JsonArray coordinates = geometry.get("coordinates").getAsJsonArray();
//            for(var coordinate : coordinates){
//                
//            }
//            
//            JsonObject properties = trafficMessage.get("properties").getAsJsonObject();
//            String situationType = properties.get("situationType").getAsString();
//            JsonArray announcements = properties.get("announcements").getAsJsonArray();
//            
//            for (var object : announcements){
//                JsonObject announcement = (JsonObject) object;
//                
//                String title = announcement.get("title").getAsString();
//                JsonObject location = announcement.get("location").getAsJsonObject();
//                String description = location.get("description").getAsString();
//                
//                JsonArray features = announcement.get("features").getAsJsonArray();
//                
//                for(var feature : features){
//                    JsonObject featureO = (JsonObject) feature;
//                    String name = featureO.get("name").getAsString(); 
//                }
//
//            }
//        }
    }
    
    public static void main(String args[]) throws IOException {
        // TODO code application logic here
        // readMaintenanceTasks("19","32","59","72");
        // readRoadConditions("19","32","59","72");
        // readFirstCondition("Suomi","19","32","59","72");
        // readTrafficMessages("TRAFFIC_ANNOUNCEMENT");
        getRoadData("Suomi","19","32","59","72");
        
    }
}
