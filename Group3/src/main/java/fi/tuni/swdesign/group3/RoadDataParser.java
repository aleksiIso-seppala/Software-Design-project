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
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;


/**
 *
 * @author Aleksi Iso-Seppälä
 */
public class RoadDataParser {

    /**
     * @param args the command line arguments
     */
    
    private static void readAllMaintenanceTasks() throws MalformedURLException, IOException{
        
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
    
        for(var object: response){
            JsonObject task = (JsonObject) object;
            String id = task.get("id").getAsString();
            String nameFi = task.get("nameFi").getAsString();
            String nameSv = task.get("nameSv").getAsString();
            String nameEn = task.get("nameEn").getAsString();
            
        }
        
        
     
    }
    
    private static void readMaintenanceTasks(String minX, String maxX,String minY, String maxY) throws MalformedURLException, IOException{
        
        String sUrl = "https://tie.digitraffic.fi/api/maintenance/v1/tracking/routes/latest?";        
        String coordinates = "xMin="+minX+"&yMin="+minY+"&xMax"+maxX+"&yMax"+maxY; 
        String fullUrl = sUrl+coordinates+"&domain=state-roads";
        
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
        
        for(var feature : features){
            JsonObject task = (JsonObject) feature;
            JsonObject properties = task.getAsJsonObject("properties");
            String id = properties.get("id").getAsString();
            JsonArray taskNames = properties.getAsJsonArray("tasks");
            
            ArrayList<String> tasks = new ArrayList<String>();
            for(var inner : taskNames){
                String taskName = inner.getAsString();
                tasks.add(taskName);
            }
        }
        
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

                if(condition.get("type").getAsString() != "OBSERVATION"){
                    JsonObject forecastCondition = condition.getAsJsonObject("forecastConditionReason");

                    forecastCondition.get("precipitationCondition").getAsString();
                    forecastCondition.get("roadCondition").getAsString();

                }
            }
        }
    }
    
    public static void main(String args[]) throws IOException {
        // TODO code application logic here
        readRoadConditions("19","32","59","72");
    }
}
