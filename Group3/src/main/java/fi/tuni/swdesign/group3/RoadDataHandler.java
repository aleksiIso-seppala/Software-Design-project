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
 *
 * @author jukka
 */
public class RoadDataHandler {
    private HashMap<String, HashMap<String, ArrayList<RoadData>>> database;
    private HashMap<String, ArrayList<String>> locations;
    
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
            
            RoadTrafficData data = RoadDataParser.
                    getRoadData(location, loc.get(0).toString(),
                            loc.get(1).toString(), loc.get(2).toString(),
                            loc.get(3).toString(), roadConditions, 
                            maintenanceTasks);
            
            return data;
            
        } catch (IOException e){
            System.out.print("error");
            return null;
        }
    }
    
    public RoadTrafficData fetchData(String location, String minX, String maxX, String minY, String maxY){
        try {
            JsonObject roadCond = RoadDataGetter.getRoadConditionData(location, minX, maxX, minY, maxY);
            JsonObject maintTasks = RoadDataGetter.getMaintenanceTaskData(minX, maxX, minY, maxY);
            
            RoadTrafficData data = RoadDataParser.getRoadData(location, minX, maxX, minY, maxY, roadCond, maintTasks);
            
            return data;
            
        } catch (IOException e){
            System.out.println("error");
            return null;
        }
    }
    
    public RoadWeatherData fetchWeatherData(String location){
        return null;
    }
    
    public RoadWeatherData fetchWeatherData(String location, String max){
        return null;
    }
    
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
    
    public ArrayList<RoadData> getRoadData(String time, String location){
        if(this.database.containsKey(time)){
            if(this.database.get(time).containsKey(location)){
                return this.database.get(time).get(location);
            }
        }
        return null;
    }
    
    public void saveDataBase(){
        
    }
    
    public void loadDataBase(){
        
    }
    
}
