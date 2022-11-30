package fi.tuni.swdesign.group3.view;
import fi.tuni.swdesign.group3.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;

/**
 *
 * @author jukka
 */
public class Model {
    private final RoadDataHandler handler;
    private ArrayList<String> locations;
    private ArrayList<String> mntTaskTypes;

    /**
     * Default constructor for Model
     * Initializes the dataHandler facade to access and save data
     */
    Model(){
        this.handler = new RoadDataHandler();
    }
    
    /**
     * Method for saving data to dataset with given name
     * @param data1, first object or null
     * @param data2, second object or null
     * @param name, name of the dataset
     * @return true if saving was succesfull, false otherwise
     * @throws IOException 
     */
    public boolean saveData(RoadData data1, RoadData data2, String name) throws IOException{
        if(data2 == null){
            return handler.saveDataBase((RoadTrafficData)data1, null, name);
        } else if (data1 == null){
            return handler.saveDataBase(null, (RoadWeatherData)data2, name);
        } else {
            return handler.saveDataBase((RoadTrafficData)data1, (RoadWeatherData)data2, name);
        }
    }
    
    /**
     * Method that Fetches data from Digitraffic API for visualizing purposes
     * @param location of the data we want to fetch
     * @return RoadTrafficData class
     */
    public RoadTrafficData getRoadTrafficData(String location){
        return this.handler.fetchRoadData(location);
    }
    
    /**
     * Method that Fetches data from FMI API for visualizing purposes
     * @param location of the data we want to fetch
     * @param time current time as string
     * @param futureTime time+12hr
     * @return RoadWeatherData class
     */
    public RoadWeatherData getRoadWeatherDataPast(String location, String time, String futureTime){
        return this.handler.fetchWeatherDataPast(location, time, futureTime);
    } 
    
    public RoadWeatherData getRoadWeatherDataFuture(String location, String time, String futureTime){
        return this.handler.fetchWeatherDataFuture(location, time, futureTime);
    }
    
    public ArrayList<String> getLocations(){
        if(this.locations == null){
            this.locations = new ArrayList<>();
            handler.getHardCodedLocations().forEach((key, value) -> 
                    locations.add(key));
            Collections.sort(locations);
        }
        return this.locations;
    }
    
    public ArrayList<String> getMaintenaneTaskTypes(){
        if(this.mntTaskTypes == null){
            this.mntTaskTypes = new ArrayList<>();
            this.mntTaskTypes = handler.getMaintenanceTaskNames();
        }
        return this.mntTaskTypes;
    }
    
    public TreeMap<String, Float[]> getMonthlyAverages(String location, String month){
        return this.handler.fetchMonthlyAverages(location, month);
    }
}
