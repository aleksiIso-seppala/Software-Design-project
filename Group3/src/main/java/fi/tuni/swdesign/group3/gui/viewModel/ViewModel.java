package fi.tuni.swdesign.group3.gui.viewModel;
import fi.tuni.swdesign.group3.classes.*;
import fi.tuni.swdesign.group3.gui.view.DataQuery;
import fi.tuni.swdesign.group3.gui.model.Model;
import fi.tuni.swdesign.group3.gui.view.CombinedDataQuery;
import fi.tuni.swdesign.group3.gui.view.WeatherDataQuery;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        if(null != query.dataType)switch (query.dataType) {
            case "Road data" -> {
                RoadTrafficData data = model.getRoadTrafficData(query.getLocation());
                
                toReturn[0] = data;
                return toReturn;
                  
            }
            case "Weather data" -> {
                
                String start = this.parseTime(query.getTimelineStart()[1], query.getTimelineStart()[0]);
                String end = this.parseTime(query.getTimelineEnd()[1], query.getTimelineEnd()[0]);
                
                WeatherDataQuery wquery = (WeatherDataQuery)query;
                
                RoadWeatherData data;
                
                if(LocalDateTime.now(ZoneId.of("Europe/Helsinki")).toString().replace(".", ":").compareTo(start) < 0){
                    data = model.getRoadWeatherDataFuture(query.getLocation(), start, end);
                } else {
                    data = model.getRoadWeatherDataPast(query.getLocation(), start, end);
                }
                
                if(!wquery.getSelectedPerMonthParams().isEmpty()){
                    String date = query.getTimelineStart()[1].replace(".", "-");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String dateQuery = LocalDate.parse(date, formatter).format(formatter2);
                    TreeMap<String, Float[]> avgs = model.getMonthlyAverages(query.getLocation(), dateQuery);
                    data.setMonthlyAverage(avgs);              
                }
                
                toReturn[0] = data;
                return toReturn;
                
            }
            case "Combined data" -> {
                
                CombinedDataQuery cbquery = (CombinedDataQuery) query;
                
                String start = this.parseTime(query.getTimelineStart()[1], query.getTimelineStart()[0]);
                String end = this.parseTime(query.getTimelineEnd()[1], query.getTimelineEnd()[0]);
                
                RoadTrafficData data = model.getRoadTrafficData(cbquery.getLocation());
                
                RoadWeatherData data2;
                
                if(LocalDateTime.now(ZoneId.of("Europe/Helsinki")).toString().replace(".", ":").compareTo(start) < 0){
                    data2= model.getRoadWeatherDataFuture(cbquery.getLocation(), start, end);
                } else {
                    data2 = model.getRoadWeatherDataPast(cbquery.getLocation(), start, end);
                }
                
                if(!cbquery.getSubWeatherDQ().getSelectedPerMonthParams().isEmpty()){
                    String date = query.getTimelineStart()[1].replace(".", "-");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String dateQuery = LocalDate.parse(date, formatter).format(formatter2);
                    TreeMap<String, Float[]> avgs = model.getMonthlyAverages(query.getLocation(), dateQuery);
                    
                    data2.setMonthlyAverage(avgs); 
                }
                
                toReturn[0] = data;
                toReturn[1] = data2;
                return toReturn;
            }
            default -> {
            }
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
     * @param query, containing the prefences for GUI
     * @return true if saving was succesfull, false otherwise
     * @throws IOException 
     */
    public String saveData(String id, RoadData data, DataQuery query) throws IOException{
        if(data instanceof RoadTrafficData roadTrafficData){
            return model.saveData(roadTrafficData, null, query, id);
        } else if (data instanceof RoadWeatherData roadWeatherData){
            return model.saveData(null, roadWeatherData, query, id);
        }
        return "No data to be saved!";
    }
    
    /**
     * Method for saving combined data from GUI
     * @param id, name of the dataset to save
     * @param data1, RoadTrafficData object
     * @param data2, RoadWeatherData object
     * @param query, containing the prefences for GUI
     * @return true if saving was succesfull, false otherwise
     * @throws IOException 
     */
    public String saveData(String id, RoadTrafficData data1, RoadWeatherData data2, DataQuery query) throws IOException{
        if (data1 == null & data2 == null) {
            return "No data to be saved!";
        }
        else {
            return model.saveData(data1, data2, query, id);
        }
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
     * @param prefId, the name to save the preferences
     * @return true if saving was succesfull, false otherwise
     * @throws IOException 
     */
    public String savePreferences(DataQuery query, String prefId) throws IOException{
        return this.model.savePreferences(query, prefId);
    }
    
    /**
     * Method for loading the user preferences from saved file
     * @param prefId the name of the file to save to
     * @return the preferences in dataQuery object
     * @throws IOException 
     */
    public DataQuery loadPreferences(String prefId) throws IOException{
        return this.model.loadPreferences(prefId);
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
