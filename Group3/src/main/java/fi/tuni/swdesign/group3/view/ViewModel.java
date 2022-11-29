package fi.tuni.swdesign.group3.view;
import fi.tuni.swdesign.group3.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.function.BiConsumer;
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
     * @param query
     * @return 
     */
    public RoadData[] onCalculateButtonPress(DataQuery query){
        RoadData[] toReturn = new RoadData[2];
        if("Road data".equals(query.dataType)){
            RoadTrafficData data = model.getRoadTrafficData(query.location);
            toReturn[0] = data;
            return toReturn;
            
        } else if ("Weather data".equals(query.dataType)){
            
            String start = this.parseTime(query.timelineStart[1], query.timelineStart[0]);
            String end = this.parseTime(query.timelineEnd[1], query.timelineEnd[0]);
            
            WeatherDataQuery wquery = (WeatherDataQuery)query;
            
            RoadWeatherData data;
            
            if(LocalDateTime.now(ZoneId.of("Europe/Helsinki")).toString().replace(".", ":").compareTo(start) < 0){
                data = model.getRoadWeatherDataFuture(query.location, start, end);
            } else {
                data = model.getRoadWeatherDataPast(query.location, start, end);  
            }
            
            if(!wquery.getSelectedPerMonthParams().isEmpty()){
                TreeMap<String, Float[]> avgs = model.getMonthlyAverages(query.location, query.timelineStart[1]);
                data.setMonthlyAverage(avgs);
            }
            
            toReturn[0] = data;
            return toReturn;
        
        } else if ("Combined data".equals(query.dataType)){
            
            CombinedDataQuery cbquery = (CombinedDataQuery) query;
            
            String start = this.parseTime(cbquery.timelineStart[1], cbquery.timelineStart[0]);
            String end = this.parseTime(cbquery.timelineStart[1], cbquery.timelineEnd[0]);
            
            RoadTrafficData data = model.getRoadTrafficData(cbquery.location);
            
            RoadWeatherData data2;
            
            if(LocalDateTime.now(ZoneId.of("Europe/Helsinki")).toString().replace(".", ":").compareTo(start) < 0){
                data2= model.getRoadWeatherDataFuture(cbquery.location, start, end);
            } else {
                data2 = model.getRoadWeatherDataPast(cbquery.location, start, end);
            }
            
            toReturn[0] = data;
            toReturn[1] = data2;
            return toReturn;
        }
        return null;
    }
    
    /*
    public RoadWeatherData onWeatherDataCalculateButton(ActionEvent e, HashMap<String, String> parameters){
        RoadWeatherData data = model.getRoadWeatherData(parameters.get("location"), 
                parameters.get("time"), parameters.get("futuretime"));
        return data;
    }
    
    public void onCombinedDataCalculateButton(ActionEvent e, HashMap<String, String> parameters){
        RoadTrafficData rdata = model.getRoadTrafficData(parameters.get("location"));
        RoadWeatherData wdata = model.getRoadWeatherData(parameters.get("location"), 
                parameters.get("time"), parameters.get("futuretime"));
                
    }*/
    
    public ArrayList<String> getLocations(){
        return model.getLocations();
    }
    
    public ArrayList<String> getMaintenanceTaskTypes(){
        return model.getMaintenaneTaskTypes();
    }
    
    private String parseTime(String day, String time){
            //Some parsing needed for the user input.
            String date = day.replace(".", "-");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateQuery = LocalDate.parse(date, formatter).format(formatter2);
            
            StringBuilder parsedDate = new StringBuilder(dateQuery);
            parsedDate.append("T").append(time.replace(".", ":")).append(":00Z");
            
            return parsedDate.toString();
    }
    
    
}
