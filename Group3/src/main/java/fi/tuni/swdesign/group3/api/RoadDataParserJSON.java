/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.swdesign.group3.api;

import com.google.gson.*;
import fi.tuni.swdesign.group3.classes.RoadTrafficData;
import fi.tuni.swdesign.group3.classes.RoadTrafficDataForecast;
import java.util.ArrayList;
import java.util.HashMap;



/**
 * A class for parsing the RoadData from Json to an usable form. Generally is 
 * meant to be used with the RoadDataGetterDigiTraffic class which generates the
 * JsonObjects needed for the functions in this class.
 * 
 * @author Aleksi Iso-Seppälä
 */
public class RoadDataParserJSON implements RoadDataParser{
    
    /**
     * Returns a complete RoadTrafficData object with all the different road data information
     * filled using the corresponding functions. 
     * <p>
     * X-coordinates must be set between 10 and 32. Y-
     * decimals must be set between 59 and 72. Coordinates may contain decimals and are
     * to be set in WGS84 format.
     * 
     * @param location A string that defines the city which the coordinates belong to
     * @param minX A string for the minimum longitude coordinate
     * @param maxX A string for the maximum longitude coordinate
     * @param minY A string for the minimum latitude coordinate
     * @param maxY A string for the maximum latitude coordinate
     * @param condition A JsonObject for the roadCondition data
     * @param tasks A JsonObject for the road maintenance task data
     * @param messageData an ArrayList for the JsonObjects for the traffic message data
     * @return A roadTrafficData object
     */
    public static RoadTrafficData getRoadData(String location, String minX, String maxX,
            String minY, String maxY, JsonObject condition, JsonObject tasks, ArrayList<JsonObject> messageData){
        
        var roadData = readFirstCondition(location, minX, maxX, minY, maxY, condition);
        
        if(tasks != null){
            var maintenanceTasks = readMaintenanceTasks(tasks);
            roadData.setMaintenanceTasks(maintenanceTasks);
        }
        if(messageData != null){
            int messages = readTrafficMessages(messageData);
            roadData.setNumberOfTrafficMessages(messages);            
        }

        
        return roadData;
    }
    
    /**
     * A function that goes through the maintenance tasknames jsonarray and gets all
     * the different possible names of maintenance tasks. The JsonArray needs to be
     * generated via the getter function getMaintenanceTaskNamesData()
     * <p>
     * The gathered data is put to an ArrayList with the Names of the possible
     * task names.
     * 
     * @param textType type the parse the text to wanted format
     * @param response The starting point of the Json data Array
     * @return An ArrayList with all the names of the maintenance tasks
     */
    public static ArrayList<String> readMaintenanceTaskNames(JsonArray response, String textType){
        
        ArrayList<String> tasks = new ArrayList<>();
        for(var object: response){
            JsonObject task = (JsonObject) object;
            String id = task.get("id").getAsString();
            String nameFi = task.get("nameFi").getAsString();
            String nameSv = task.get("nameSv").getAsString();
            String nameEn = task.get("nameEn").getAsString();
            
            if(textType.equals("clean")){
                tasks.add(nameEn);                
            }
            else if(textType.equals("original")){
                tasks.add(id);
            }

        }
        return tasks;

    }
    
    /**
     * Function that goes through all the maintenance tasks inside the JsonObject.
     * JsonObject is generated with the getter function getMaintenanceTaskData().
     * <p>
     * The gathered data is then put into a HashMap with the name of
     * the task and the number of occurences of that task type.
     * 
     * @param response The starting point of the JsonObject data
     * @return A HashMap with the different task types found and the number of cases
     * corresponding to the task type
     */
    public static HashMap<String, Integer> readMaintenanceTasks(JsonObject response){
        
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
    
    /**
     * A function that goes through the roadCondition data that and gets the 
     * first complete set of data from it. This includes an observation of the
     * current condition and multiple forecasts of coming conditions.
     * <p>
     * An object RoadTrafficData is created and the observation and forecasts
     * are converted to usable data and put to the object with setter functions
     * <p>
     * The inital JsonObject is to be created with corresponding getter function
     * getRoadConditionData().
     * 
     * X-coordinates must be set between 10 and 32. Y-
     * decimals must be set between 59 and 72. Coordinates may contain decimals and are
     * to be set in WGS84 format.
     * 
     * @param location The name of the location for which the coordinates belong to
     * @param minX A string for the minimum longitude coordinate
     * @param maxX A string for the maximum longitude coordinate
     * @param minY A string for the minimum latitude coordinate
     * @param maxY A string for the maximum latitude coordinate
     * @param response The JsonObject for the roadCondition data 
     * @return A RoadTrafficData with the correct gathered road conditions
     */
    public static RoadTrafficData readFirstCondition(String location, String minX, String maxX,
            String minY, String maxY, JsonObject response){
        
        String coordinates = minX+"/"+minY+"/"+maxX+"/"+maxY; 
        
        JsonArray weatherData = response.getAsJsonArray("weatherData");
        
        var data = weatherData.get(0);
        JsonObject allData = (JsonObject) data;
        JsonArray roadConditions = allData.getAsJsonArray("roadConditions");

        RoadTrafficData trafficData = new RoadTrafficData(location, coordinates,"0");
        HashMap<String, RoadTrafficDataForecast> forecasts = new HashMap<>();

        for(var road : roadConditions){

            JsonObject condition = road.getAsJsonObject();
            String type = condition.get("type").getAsString();

            String time = condition.get("forecastName").getAsString();
            time = time.substring(0, time.length()-1);
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
                    trafficForecast.setOverAllcondition(roadCondition); 
                }
                if(forecastCondition.has("frictionCondition")){
                    String frictionCondition = forecastCondition.get("frictionCondition").getAsString();
                    trafficForecast.setFriction(frictionCondition);
                }
                if(forecastCondition.has("windCondition")){
                    String windCondition = forecastCondition.get("windCondition").getAsString();
                    trafficForecast.setWindCondition(windCondition);
                }
                if(forecastCondition.has("winterSlipperiness")){
                    Boolean winterSlipperiness = forecastCondition.get("winterSlipperiness").getAsBoolean();
                    trafficForecast.setWinterSlipperines(winterSlipperiness);
                }
                
                forecasts.put(time, trafficForecast);

            }
        }
        trafficData.setForecasts(forecasts);
        return trafficData;
         
    }
    
    /**
     * A function that gathers the total amount of traffic messages between all the
     * different possible traffic message types. The function goes through an 
     * ArrayList containing the JsonObjects for the message types and counts the
     * amount of messages.
     * <p>
     * The initial ArrayList is to be created with the corresponding getter
     * function getTrafficMessageData()
     * 
     * @param data An ArrayList Of JsonObjects of the trafficmessage data
     * @return An integer of the total amount of traffic messages found.
     */
    public static int readTrafficMessages(ArrayList<JsonObject> data){
        
        int messages = 0;
        for(var situation : data){
            JsonArray trafficMessages = situation.getAsJsonArray("features");
            messages += trafficMessages.size();
        }
        return messages;
        
    }
    
}
