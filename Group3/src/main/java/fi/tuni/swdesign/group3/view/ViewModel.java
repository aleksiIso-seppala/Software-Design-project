package fi.tuni.swdesign.group3.view;
import fi.tuni.swdesign.group3.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javafx.event.ActionEvent;

/**
 *
 * @author jukka
 */
public class ViewModel {
    private final Model model;
    private RoadTrafficData trafficData;
    private RoadWeatherData weatherData;
    
    /**
     * Default constructor for a viewModel
     * @param m Model to reference to viewModel
     */
    ViewModel(Model m){
        this.model = m;
    }
     
    /**
     * 
     * @param parameters
     * @return 
     */
    public RoadTrafficData onRoadDataCalculateButton(HashMap<String, String> parameters){
        RoadTrafficData data = model.getRoadTrafficData(parameters.get("location"));
        return data;
    }
    
    public RoadWeatherData onWeatherDataCalculateButton(ActionEvent e, HashMap<String, String> parameters){
        RoadWeatherData data = model.getRoadWeatherData(parameters.get("location"), 
                parameters.get("time"), parameters.get("futuretime"));
        return data;
    }
    
    public void onCombinedDataCalculateButton(ActionEvent e, HashMap<String, String> parameters){
        RoadTrafficData rdata = model.getRoadTrafficData(parameters.get("location"));
        RoadWeatherData wdata = model.getRoadWeatherData(parameters.get("location"), 
                parameters.get("time"), parameters.get("futuretime"));
                
    }
    
    public ArrayList<String> getLocations(){
        return model.getLocations();
    }
    
    public ArrayList<String> getMaintenanceTaskTypes(){
        return model.getMaintenaneTaskTypes();
    }
    
    
}
