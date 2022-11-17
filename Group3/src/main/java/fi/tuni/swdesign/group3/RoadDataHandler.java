/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.JsonObject;
import java.io.IOException;

/**
 * A class which handles the fetching and saving of data from the two different
 * APIs. Also provides the database for the program.
 * @author jukka
 */
public class RoadDataHandler {
    private final HashMap<String, HashMap<String, ArrayList<RoadData>>> database;
    private final HashMap<String, ArrayList<String>> locations;
    
    /**
     * Default builder for roadDataHandler
     */
    RoadDataHandler(){
        database = new HashMap<>();
        locations = new HashMap<>();
        locations.put("Suomi", new ArrayList<>(List.of("19", "32", "59", "72")));
        locations.put("Helsinki", new ArrayList<>(List.of()));
        locations.put("Kuopio", new ArrayList<>(List.of()));
        locations.put("Oulu", new ArrayList<>(List.of()));
        locations.put("Tampere", new ArrayList<>(List.of("23", "24", "62", "63")));
        locations.put("Rovaniemi", new ArrayList<>(List.of()));
    }
    
    /**
     * Method for fetching the data from digitraffic with hardcoded locations
     * @param location the wanted location as a string
     * @return RoadTrafficData object that is used to save or visualize data
     */
    public RoadTrafficData fetchData(String location){
        try {
            
            ArrayList loc = this.locations.get(location);
            
            JsonObject roadConditions = RoadDataGetter.
                    getRoadConditionData(location, loc.get(0).toString(),
                            loc.get(1).toString(), loc.get(2).toString(),
                            loc.get(3).toString());
                     
            JsonObject maintenanceTasks = RoadDataGetter.
                    getMaintenanceTaskData(loc.get(0).toString(),
                            loc.get(1).toString(), loc.get(2).toString(),
                            loc.get(3).toString());
            
            ArrayList<JsonObject> trafficMsgs = RoadDataGetter.getTrafficMessageData();
            
            RoadTrafficData data = RoadDataParser.
                    getRoadData(location, loc.get(0).toString(),
                            loc.get(1).toString(), loc.get(2).toString(),
                            loc.get(3).toString(), roadConditions, 
                            maintenanceTasks, trafficMsgs);
            
            return data;
            
        } catch (IOException e){
            System.out.print("error");
            return null;
        }
    }
    
    /**
     * Method for fetching the data from digitraffic with given coordinate range
     * @param location name as a string
     * @param minX minumun longitude coorinate as string
     * @param maxX maximum longitude coorinate as string
     * @param minY minumun latitude coorinate as string
     * @param maxY maximum longitude coorinate as string
     * @return RoadTrafficData object that is used to save or visualize data
     */
    public RoadTrafficData fetchData(String location, String minX, String maxX, String minY, String maxY){
        try {
            JsonObject roadCond = RoadDataGetter.getRoadConditionData(location, minX, maxX, minY, maxY);
            JsonObject maintTasks = RoadDataGetter.getMaintenanceTaskData(minX, maxX, minY, maxY);
            ArrayList<JsonObject> trafficMsgs = RoadDataGetter.getTrafficMessageData();
            
            RoadTrafficData data = RoadDataParser.getRoadData(location, minX,
                    maxX, minY, maxY, roadCond, maintTasks, trafficMsgs);
            
            return data;
            
        } catch (IOException e){
            System.out.println("error");
            return null;
        }
    }
    
    /**
     * Method for fetching the data from FMI with hardcoded location coordinates
     * @param location
     * @return RoadWeatherData object that is used to save or visualize data
     */
    public RoadWeatherData fetchWeatherData(String location){
        return null;
    }
    
    /**
     * Method for fetching the data from FMI with given location coordinates
     * @param location
     * @param max
     * @return RoadWeatherData object that is used to save or visualize data
     */
    public RoadWeatherData fetchWeatherData(String location, String max){
        return null;
    }
    
    /**
     * Method for storing data to the database
     * @param time of the data to be stored
     * @param location name as a string
     * @param data the object to store
     */
    public void addData(String time, String location, RoadData data){
        ArrayList<RoadData> newData;
        HashMap<String, ArrayList<RoadData>> locationData;
        
        if(this.database.containsKey(time)){
            if(this.database.get(time).containsKey(location)){
                this.database.get(time).get(location).add(data);
            } else {
                newData = new ArrayList<>();
                newData.add(data);
                this.database.get(time).put(location, newData);
            }
        } else {
            newData = new ArrayList<>();
            newData.add(data);
            
            locationData = new HashMap<>();
            locationData.put(location, newData);
            
            this.database.put(time, locationData);
        }
    }
    
    /**
     * Method for getting data from the database
     * @param time of the data we wannt as a string
     * @param location name as a string
     * @return RoadData object containing the data
     */
    public ArrayList<RoadData> getRoadData(String time, String location){
        if(this.database.containsKey(time)){
            if(this.database.get(time).containsKey(location)){
                return this.database.get(time).get(location);
            }
        }
        return null;
    }
    
    /**
     * Method for saving the current database as JSON
     */
    public void saveDataBase(){
        
    }
    
    /**
     * Method for loading the saved database from a JSON file
     */
    public void loadDataBase(){
        
    }
    
}
