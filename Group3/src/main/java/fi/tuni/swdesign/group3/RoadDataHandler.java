/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import com.google.gson.JsonObject;
import java.io.IOException;

/**
 *
 * @author jukka
 */
public class RoadDataHandler {
    private HashMap<String, HashMap<String, ArrayList<RoadData>>> database;
    
    RoadDataHandler(){
        database = new HashMap<>();
    }
    
    public void fetchData(){
        try {
            JsonObject roadConditions = RoadDataGetter.getRoadConditionData("Suomi","19","32","59","72");
                     
            JsonObject maintenanceTasks = RoadDataGetter.getMaintenanceTaskData("19","32","59","72");
            
            RoadTrafficData data = RoadDataParser.getRoadData("Suomi","19","32","59","72", roadConditions, maintenanceTasks);
            
            System.out.print("windspeed:"+data.getWindSpeed()+" ; @"+data.getCoordinates());
            
        } catch (IOException e){
            System.out.print("error");
        }
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
    
}
