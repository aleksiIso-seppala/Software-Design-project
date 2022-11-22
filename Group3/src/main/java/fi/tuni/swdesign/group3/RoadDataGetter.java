/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.swdesign.group3;
import fi.tuni.swdesign.group3.view.*;
import fi.tuni.swdesign.group3.RoadDataParser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
/**
 *
 * @author Aleksi
 */
public class RoadDataGetter {
    
    public static JsonArray getMaintenanceTaskNamesData() throws 
            MalformedURLException, IOException{
        
        String sUrl = "https://tie.digitraffic.fi/api/maintenance/v1/tracking/tasks";        

        URL url = new URL(sUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("Accept-Encoding", "gzip");
        
        GZIPInputStream gzipInput = null;
        try{
            gzipInput = new GZIPInputStream(con.getInputStream());
        }
        catch (IOException ex){
            System.out.println("Something went wrong");
            return null;
        }
        InputStreamReader reader = new InputStreamReader(gzipInput, "UTF-8");
        
        Gson gson = new Gson();
        JsonArray response = gson.fromJson(reader, JsonArray.class);
        return response;
        
    }
    
    public static JsonObject getMaintenanceTaskData(String minX, String maxX,
            String minY, String maxY) throws MalformedURLException, IOException{
        
        String sUrl = "https://tie.digitraffic.fi/api/maintenance/v1/tracking/routes/latest?";        
        String coordinates = "xMin="+minX+"&yMin="+minY+"&xMax="+maxX+"&yMax="+maxY; 
        String fullUrl = sUrl+coordinates+"&domain=all";
        
        URL url = new URL(fullUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("Accept-Encoding", "gzip");
        
        GZIPInputStream gzipInput = null;
        try{
            gzipInput = new GZIPInputStream(con.getInputStream());
        }
        catch(IOException ex){
            int rCode = con.getResponseCode();
            if(rCode == 200){
                System.out.println("No maintenance tasks found.");
            }
            if(rCode == 400){
                System.out.println("Invalid coordinates");
            }    
            return null;
        }
        InputStreamReader reader = new InputStreamReader(gzipInput, "UTF-8");
        
        Gson gson = new Gson();
        JsonObject response = gson.fromJson(reader, JsonObject.class);
        
        return response;
    }
    
    public static JsonObject getRoadConditionData(String location, String minX, String maxX,
            String minY, String maxY) throws MalformedURLException, IOException{
        
        String sUrl = "https://tie.digitraffic.fi/api/v3/data/road-conditions/";        
        String coordinates = minX+"/"+minY+"/"+maxX+"/"+maxY; 
        String fullUrl = sUrl+coordinates;
        URL url = new URL(fullUrl);
        
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("Accept-Encoding", "gzip");
        
        GZIPInputStream gzipInput = null;
        try{
            gzipInput = new GZIPInputStream(con.getInputStream());           
        }
        catch(IOException ex){
            int rCode = con.getResponseCode();
            if(rCode == 200){
                System.out.println("No Road Conditions found.");
            }
            if(rCode == 400){
                System.out.println("Invalid coordinates");
            }
            return null;
        }
        InputStreamReader reader = new InputStreamReader(gzipInput, "UTF-8");       

        Gson gson = new Gson();
        JsonObject response = gson.fromJson(reader, JsonObject.class);
        
        return response;
    }
    
    public static ArrayList<JsonObject> getTrafficMessageData() throws MalformedURLException, IOException{
        
        ArrayList<JsonObject> messageData = new ArrayList<>();
        
        String sUrl = "https://tie.digitraffic.fi/api/traffic-message/v1/messages?"
                + "inactiveHours=0&includeAreaGeometry=true&situationType=";
        
        ArrayList<String> situationTypes = new ArrayList<>();
        situationTypes.add("TRAFFIC_ANNOUNCEMENT");
        situationTypes.add("EXEMPTED_TRANSPORT");
        situationTypes.add("WEIGHT_RESTRICTION");
        situationTypes.add("ROAD_WORK");
        
        for(var situation : situationTypes){
            String fullUrl = sUrl+situation;
            URL url = new URL(fullUrl);
            
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Accept-Encoding", "gzip");

            GZIPInputStream gzipInput = null;
            try {
                gzipInput = new GZIPInputStream(con.getInputStream());
            } catch (IOException ex) {
                continue;

            }

            InputStreamReader reader = new InputStreamReader(gzipInput, "UTF-8");

            Gson gson = new Gson();
            JsonObject response = gson.fromJson(reader, JsonObject.class);
            messageData.add(response);
        }
        
        if(messageData.size() == 0){
            System.out.println("No traffic messages.");
            return null;
        }
        
        return messageData;
    }
    
    public static void main(String args[]) throws IOException {
        // TODO code application logic here
        // var response = getRoadConditionData("suomi","100","100","100","100");
        // RoadDataParser.readFirstCondition("suomi", "19", "32", "59", "72", response);
        int messages = RoadDataParser.readTrafficMessages(getTrafficMessageData());
        System.out.println(messages);
    }
}
