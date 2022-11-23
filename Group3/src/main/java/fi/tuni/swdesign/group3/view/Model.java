package fi.tuni.swdesign.group3.view;
import fi.tuni.swdesign.group3.*;

/**
 *
 * @author jukka
 */
public class Model {
    private final RoadDataHandler handler;

    /**
     * Default constructor for Model
     * Initializes the dataHandler facade to access and save data
     */
    Model(){
        this.handler = new RoadDataHandler();
    }
    
    /**
     * Method for updating data, not yet implemented
     */
    public void updateData(){
        
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
    public RoadWeatherData getRoadWeatherData(String location, String time, String futureTime){
        return this.handler.fetchWeatherData(location, time, futureTime);
    } 
}
