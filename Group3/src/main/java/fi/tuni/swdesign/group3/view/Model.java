package fi.tuni.swdesign.group3.view;
import fi.tuni.swdesign.group3.*;
import java.io.IOException;
import java.util.ArrayList;
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
     * @param futureTime time to fetch to 
     * @return RoadWeatherData class
     */
    public RoadWeatherData getRoadWeatherDataPast(String location, String time, String futureTime){
        return this.handler.fetchWeatherDataPast(location, time, futureTime);
    } 
    
    /**
     * Method that Fetches data from FMI API for visualizing purposes
     * @param location of the data we want to fetch
     * @param time current time as string
     * @param futureTime time to fetch to
     * @return RoadWeatherData class
     */
    public RoadWeatherData getRoadWeatherDataFuture(String location, String time, String futureTime){
        return this.handler.fetchWeatherDataFuture(location, time, futureTime);
    }
    
    /**
     * Getter for the locations to GUI dropdown menu
     * @return ArrayList containing the location names
     */
    public ArrayList<String> getLocations(){
        if(this.locations == null){
            this.locations = new ArrayList<>();
            handler.getHardCodedLocations().forEach((key, value) -> 
                    locations.add(key));
            Collections.sort(locations);
        }
        return this.locations;
    }
    
    /**
     * Getter for maintenancetask types for GUI
     * @return ArrayList containing the task names
     */
    public ArrayList<String> getMaintenaneTaskTypes(){
        if(this.mntTaskTypes == null){
            this.mntTaskTypes = new ArrayList<>();
            this.mntTaskTypes = handler.getMaintenanceTaskNames();
        }
        return this.mntTaskTypes;
    }
    
    /**
     * Method for saving the user GUI preferences
     * @param query, containing the preferences
     * @return true if saving was succesfull, false otherwise
     * @throws IOException 
     */
    public boolean savePreferences(DataQuery query) throws IOException{
        return this.handler.savePreferences(query);
    }
    
    /**
     * Method for loading the user preferences from saved file
     * @return the preferences in dataQuery object
     * @throws IOException 
     */
    public DataQuery loadPreferences() throws IOException{
        return this.handler.loadPreferences();
    }
    
    /**
     * Getter for the monthly average temperaturs
     * @param location, location we want the data from
     * @param month, we want the data of
     * @return TreeMap containing the dates and values
     */
    public TreeMap<String, Float[]> getMonthlyAverages(String location, String month){
        return this.handler.fetchMonthlyAverages(location, month);
    }
    
    /**
     * Method for loading data from the database
     * @param name, name of the database
     * @return List of roaddata objects
     * @throws IOException 
     */
    public RoadData[] loadData(String name) throws IOException{
        return this.handler.loadDataBase(name);
    }
}
