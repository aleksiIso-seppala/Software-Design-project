package fi.tuni.swdesign.group3.view;
import fi.tuni.swdesign.group3.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

/**
 * Class describing the viewModel
 * @author jukka
 */
public class ViewModel {
    private final Model model;
    
    /**
     * Default constructor for a viewModel
     * @param m Model to reference to viewModel
     */
    ViewModel(Model m){
        this.model = m;
    }
     
    /**
     * Method for getting the wanted data based on user inputs
     * @param query, DataQuery object containing the user inputted preferences
     * @return RoadData object, which can be either TrafficData or WeatherData
     *         or both in an Array.
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
                String date = query.timelineStart[1].replace(".", "-");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dateQuery = LocalDate.parse(date, formatter).format(formatter2); 
                TreeMap<String, Float[]> avgs = model.getMonthlyAverages(query.location, dateQuery);
                data.setMonthlyAverage(avgs);
                
                data.getMonthylAverage().forEach((key, value) -> {
                    System.out.println(key+":"+Arrays.toString(value));
                });
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
            
            if(!cbquery.getSubWeatherDQ().getSelectedPerMonthParams().isEmpty()){
                String date = query.timelineStart[1].replace(".", "-");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dateQuery = LocalDate.parse(date, formatter).format(formatter2); 
                TreeMap<String, Float[]> avgs = model.getMonthlyAverages(query.location, dateQuery);
                
                data2.setMonthlyAverage(avgs);
                
                data2.getMonthylAverage().forEach((key, value) -> {
                    System.out.println(key+":"+Arrays.toString(value));
                });
                
            }
            
            toReturn[0] = data;
            toReturn[1] = data2;
            return toReturn;
        }
        return null;
    }
    
    /**
     * Getter for the locations to show in GUI dropdown menu
     * @return the location names as an ArrayList
     */
    public ArrayList<String> getLocations(){
        return model.getLocations();
    }
    
    /**
     * Getter for the maintenance task types to show in the GUI dropdown
     * @return the task names as an ArrayList
     */
    public ArrayList<String> getMaintenanceTaskTypes(){
        return model.getMaintenaneTaskTypes();
    }
    
    /**
     * Method for saving data from GUI
     * @param id, name of the dataset to save
     * @param data, the object to save
     * @return true if saving was succesfull, false otherwise
     * @throws IOException 
     */
    public boolean saveData(String id, RoadData data) throws IOException{
        if(data instanceof RoadTrafficData roadTrafficData){
            return model.saveData(roadTrafficData, null, id);
        } else if (data instanceof RoadWeatherData roadWeatherData){
            return model.saveData(null, roadWeatherData, id);
        }
        return false;
    }
    
    /**
     * Method for saving combined data from GUI
     * @param id, name of the dataset to save
     * @param data1, RoadTrafficData object
     * @param data2, RoadWeatherData object
     * @return true if saving was succesfull, false otherwise
     * @throws IOException 
     */
    public boolean saveData(String id, RoadTrafficData data1, RoadWeatherData data2) throws IOException{
        return model.saveData(data1, data2, id);
    }
    
    /**
     * Method for loading from the database to GUI
     * @param name, name of the dataset
     * @return RoadData object list
     * @throws IOException 
     */
    public RoadData[] loadDataBase(String name) throws IOException{
        return this.model.loadData(name);
    }
    
    /**
     * Method for saving the user GUI preferences
     * @param query, containing the preferences
     * @return true if saving was succesfull, false otherwise
     * @throws IOException 
     */
    public boolean savePreferences(DataQuery query) throws IOException{
        return this.model.savePreferences(query);
    }
    
    /**
     * Method for loading the user preferences from saved file
     * @return the preferences in dataQuery object
     * @throws IOException 
     */
    public DataQuery loadPreferences() throws IOException{
        return this.model.loadPreferences();
    }
    
    /**
     * Helper function to parse the user inputted time to correct format for
     * data fetching
     * @param day, day, month, year in dd.MM.yyyy format
     * @param time, hours and minutes in hh.mm format
     * @return the parsed time in ISO 8601 format ie. yyyy-MM-ddThh:mm:ssZ
     */
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
