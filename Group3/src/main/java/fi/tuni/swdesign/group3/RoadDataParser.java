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
    
    private static void ReadMaintenanceTasks(String minX, String maxX,String minY, String maxY) throws MalformedURLException, IOException{
        
        String sUrl = "https://tie.digitraffic.fi/api/maintenance/v1/tracking/routes/latest?";        
        String coordinates = "xMin="+minX+"&yMin="+minY+"&xMax"+maxX+"&yMax"+"&yMax"+maxY; 
        String fullUrl = sUrl+coordinates+"&domain=state-roads";
        
        URL url = new URL(sUrl);
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
            
            ArrayList<String> tasks = null;
            for(var inner : taskNames){
                String test = inner.getAsString();
                tasks.add(test);
            }
        }
        
    }
    
    public static void main(String args[]) throws IOException {
        // TODO code application logic here
        ReadMaintenanceTasks("19","32","59","72");
    }
}
