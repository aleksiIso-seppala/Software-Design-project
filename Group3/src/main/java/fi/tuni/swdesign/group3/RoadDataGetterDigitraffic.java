/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.swdesign.group3;
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
 * A class for getting the RoadData from Digitraffic using correct url addresses
 * and then converting them to JsonObjects/JsonArrays containing the correct data.
 * Generally is meant to be used with the RoadDataParserJSON which parses the Json
 * Objects to an usable format.
 * 
 * @author Aleksi Iso-Seppälä
 */
public class RoadDataGetterDigitraffic implements RoadDataGetter {
    
    /**
     * A dunction that gets the JsonArray containing all the different maintenance
     * task type names. It gets the function from Digitraffic using an url and
     * then converting it to the JsonArray.
     * <p>
     * The returned JsonArray is to be used with the corresponsing parser functions
     * so the data can be used.
     * 
     * @return returns a JsonArray with the data on the task names.
     * @throws MalformedURLException if there is something wrong with the url address
     * @throws IOException if there goes something wrong with getting the correct data
     * from the URL address.
     */
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
            return null;
        }
        InputStreamReader reader = new InputStreamReader(gzipInput, "UTF-8");
        
        Gson gson = new Gson();
        JsonArray response = gson.fromJson(reader, JsonArray.class);
        return response;
        
    }
    
    /**
     * A function that gets the JsonObject containing the data 
     * for active maintenance tasks. It gets the data from Digitraffic using an
     * url with the correct coordinate specifications.
     * <p>
     * The coordinates work so the x-coordinates must be set between 10 and 32. Y-
     * decimals must be set between 59 and 72. Coordinates may contain decimals and are
     * to be set in WGS84 format.
     * <p>
     * The returned JsonObject is to be used with the corresponsing parser functions
     * so the data can be used.
     * 
     * @param minX A string for the minimum longitude coordinate
     * @param maxX A string for the maximum longitude coordinate
     * @param minY A string for the minimum latitude coordinate
     * @param maxY A string for the maximum latitude coordinate
     * @return The JsonObject containing the data
     * @throws MalformedURLException if there is something wrong with the url address
     * @throws IOException if there no maintenanceTasks were found or if the given
     * coordinates were invalid.
     */
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
            return null;
        }
        InputStreamReader reader = new InputStreamReader(gzipInput, "UTF-8");
        
        Gson gson = new Gson();
        JsonObject response = gson.fromJson(reader, JsonObject.class);
        
        return response;
    }
    
    /**
     * A function that gets the JsonObject containing the data 
     * for road conditions. It gets the data from Digitraffic using an
     * url with the correct coordinate specifications.
     * <p>
     * The coordinates work so the x-coordinates must be set between 10 and 32. Y-
     * decimals must be set between 59 and 72. Coordinates may contain decimals and are
     * to be set in WGS84 format.
     * <p>
     * The returned JsonObject is to be used with the corresponsing parser functions
     * so the data can be used.
     * 
     * @param location A string that defines the city which the coordinates belong to
     * @param minX A string for the minimum longitude coordinate
     * @param maxX A string for the maximum longitude coordinate
     * @param minY A string for the minimum latitude coordinate
     * @param maxY A string for the maximum latitude coordinate
     * @return The JsonObject containing the data
     * @throws MalformedURLException if there is something wrong with the url address
     * @throws IOException if there no road conditions were found or if the given
     * coordinates were invalid.
     */
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
            return null;
        }
        InputStreamReader reader = new InputStreamReader(gzipInput, "UTF-8");       

        Gson gson = new Gson();
        JsonObject response = gson.fromJson(reader, JsonObject.class);
        
        return response;
    }
    
    /**
     * A function that gets the JsonObject containing the data 
     * for traffic messages. It gets the data from Digitraffic using an
     * url with the correct specifications.
     * <p>
     * The function goes through multiple types of traffic message types and combines
     * every JsonObject that contained any messages to an ArrayList. 
     * <p>
     * The returned ArrayList is to be used with the corresponsing parser functions
     * so the data can be used.
     * 
     * @return The ArrayList containing the data
     * @throws MalformedURLException if there is something wrong with the url address
     * @throws IOException if there are no traffic messages available for the specific
     * message type. Note that this does not stop the function, as it goes on normally after
     * it
     */
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
        
        if(messageData.isEmpty()){
            return null;
        }
        
        return messageData;
    }
    
}
