/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import com.google.gson.JsonArray;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Map;

/**
 * A class which handles the fetching and saving of data from the two different
 * APIs. Also provides the database for the program.
 * @author jukka
 */
public class RoadDataHandler {
    private final HashMap<String, HashMap<String, ArrayList<RoadData>>> database;
    private final HashMap<String, ArrayList<String>> digiTraficLocations;
    
    /**
     * Default builder for roadDataHandler
     */
    public RoadDataHandler(){
        database = new HashMap<>();
        digiTraficLocations = new HashMap<>();
        digiTraficLocations.put("Suomi", new ArrayList<>(List.of("19.1", "32", "59.1", "72")));
        digiTraficLocations.put("Helsinki", new ArrayList<>(List.of("24.93545", "25", "60.16952", "61")));
        digiTraficLocations.put("Kuopio", new ArrayList<>(List.of("27.67703","28", "62.89238", "63")));
        digiTraficLocations.put("Oulu", new ArrayList<>(List.of("25.46816", "26", "65.01236", "66" )));
        digiTraficLocations.put("Tampere", new ArrayList<>(List.of("23.78712", "24", "61.49911", "62")));
        digiTraficLocations.put("Rovaniemi", new ArrayList<>(List.of("25.71667", "26", "66.5","67")));
    }
    
    /**
     * Method for fetching the data from digitraffic with hardcoded locations
     * @param location the wanted location as a string
     * @return RoadTrafficData object that is used to save or visualize data
     */
    public RoadTrafficData fetchRoadData(String location){
        try {
            
            ArrayList loc = this.digiTraficLocations.get(location);
            
            JsonObject roadConditions = RoadDataGetterDigitraffic.
                    getRoadConditionData(location, loc.get(0).toString(),
                            loc.get(1).toString(), loc.get(2).toString(),
                            loc.get(3).toString());
                    
            JsonObject maintenanceTasks = RoadDataGetterDigitraffic.
                    getMaintenanceTaskData(loc.get(0).toString().split("\\.",2)[0],
                            loc.get(1).toString(), loc.get(2).toString().split("\\.",2)[0],
                            loc.get(3).toString());
            
            ArrayList<JsonObject> trafficMsgs = RoadDataGetterDigitraffic.getTrafficMessageData();
            
            RoadTrafficData data = RoadDataParserJSON.
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
    public RoadTrafficData fetchRoadData(String location, String minX, String maxX, String minY, String maxY){
        try {
            JsonObject roadCond = RoadDataGetterDigitraffic.getRoadConditionData(location, minX, maxX, minY, maxY);
            JsonObject maintTasks = RoadDataGetterDigitraffic.getMaintenanceTaskData(minX, maxX, minY, maxY);
            ArrayList<JsonObject> trafficMsgs = RoadDataGetterDigitraffic.getTrafficMessageData();
            
            RoadTrafficData data = RoadDataParserJSON.getRoadData(location, minX,
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
     * @param time
     * @param futureTime
     * @return RoadWeatherData object that is used to save or visualize data
     */
    public RoadWeatherData fetchWeatherData(String location, String time, String futureTime){
        try {
            
            ArrayList loc = this.digiTraficLocations.get(location);
            org.w3c.dom.Document doc = RoadDataGetterFMI.getDOMDocument("fmi::observations::weather::simple", loc.get(0).toString(),
                            loc.get(2).toString(), loc.get(1).toString(),
                            loc.get(3).toString(), "", "", time, futureTime, 
                            "t2m,ws_10min,n_man,TA_PT1H_AVG,TA_PT1H_MAX,TA_PT1H_MIN");
            
            RoadWeatherData data = RoadDataParserXML.getDOMParsedDocument(doc, loc.get(0).toString(),
                            loc.get(2).toString(), loc.get(1).toString(),
                            loc.get(3).toString(), "", "", time);
            return data;
        } catch (IOException e){
            System.out.println("error");
            return null;
        }
    }
    
    /**
     * Method for fetching the data from FMI with given location coordinates
     * NOT YET IMPLEMENTED
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
    
    /**
     * Method for getting the hardcoded locations which will then be added
     * to the GUI
     * @return ArrayList which contains the locations as a string
     */
    public HashMap<String, ArrayList<String>> getHardCodedLocations(){
        return this.digiTraficLocations;
    }
    
    public ArrayList<String> getMaintenanceTaskNames(){
        try {
            JsonArray arr = RoadDataGetterDigitraffic.getMaintenanceTaskNamesData();
            ArrayList<String> names = new ArrayList<>();
            names = RoadDataParserJSON.readMaintenanceTaskNames(arr);
            return names;
        } catch (Exception e) {
        System.out.println("Error in maintenanceTasknames");
        }
        return null;
    }
}
