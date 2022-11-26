/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import com.google.gson.*;
import com.google.gson.JsonArray;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.JsonObject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public void saveDataBase(RoadTrafficData roadData,RoadWeatherData weatherData) throws IOException{
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String fileName = "SavedData.json";
        Writer writer = new FileWriter(fileName);
        JsonArray saveData = new JsonArray();
        
        // saving roadData
        if(roadData != null){
            JsonObject roadPackage = new JsonObject();
            roadPackage.addProperty("dataType", "RoadTrafficData");
            
            roadPackage.addProperty("location",roadData.getLocation());
            roadPackage.addProperty("coordinates",roadData.getCoordinates());
            roadPackage.addProperty("time",roadData.getTime());
            
            roadPackage.addProperty("numberOfTrafficMessages", roadData.getNumberOfTrafficMessages());
            roadPackage.addProperty("windSpeed", roadData.getWindSpeed());
            roadPackage.addProperty("temperature", roadData.getTemperature());
            roadPackage.addProperty("overAllCondition", roadData.getOverAllCondition());
            roadPackage.addProperty("weatherSymbol", roadData.getWeatherSymbol());

            JsonObject maintenanceTasks = new JsonObject();

            for(var task : roadData.getMaintenanceTasks().entrySet()){
                maintenanceTasks.addProperty(task.getKey(),task.getValue());
            }
            roadPackage.add("maintenanceTasks", maintenanceTasks);

            JsonArray roadForecasts = new JsonArray();
            for(var forecast : roadData.getForecasts().entrySet()){
                JsonObject forecastObject = new JsonObject();
                
                forecastObject.addProperty("time", forecast.getKey());

                var forecastData = forecast.getValue();
                
                forecastObject.addProperty("location",forecastData.getLocation());
                forecastObject.addProperty("coordinate",forecastData.getCoordinates());
                
                forecastObject.addProperty("windSpeed", forecastData.getWindSpeed());
                forecastObject.addProperty("temperature", forecastData.getTemperature());
                forecastObject.addProperty("overAllCondition", forecastData.getOverAllCondition());
                forecastObject.addProperty("weatherSymbol", forecastData.getWeatherSymbol());       

                forecastObject.addProperty("precipitation", forecastData.getPrecipitation());
                forecastObject.addProperty("friction", forecastData.getFriction());
                forecastObject.addProperty("overAllcondition", forecastData.getOverAllcondition());
                forecastObject.addProperty("visibility", forecastData.getVisibility());

                roadForecasts.add(forecastObject);
            }
            roadPackage.add("roadForecasts",roadForecasts);

            saveData.add(roadPackage);
        }
        
        //saving weatherData
        if(weatherData != null){
            JsonObject weatherPackage = new JsonObject();
            weatherPackage.addProperty("dataType", "RoadWeatherData");
            
            weatherPackage.addProperty("location",weatherData.getLocation());
            weatherPackage.addProperty("coordinates",weatherData.getCoordinates());
            weatherPackage.addProperty("time",weatherData.getTime());
            
            weatherPackage.addProperty("temperature",weatherData.getTemperature());
            weatherPackage.addProperty("wind",weatherData.getWind());
            weatherPackage.addProperty("cloudiness",weatherData.getCloudiness());
            weatherPackage.addProperty("AVGTemperature",weatherData.getAVGTemperature());
            weatherPackage.addProperty("MAXTemperature",weatherData.getMAXTemperature());
            weatherPackage.addProperty("MINTemperature",weatherData.getMINTemperature());

            JsonArray weatherForecasts = new JsonArray();
            for(var forecast : weatherData.getForecasts().entrySet()){
                JsonObject forecastObject = new JsonObject();
                forecastObject.addProperty("time", forecast.getKey());
                
                var forecastData = forecast.getValue();
                forecastObject.addProperty("location", forecastData.getLocation());
                forecastObject.addProperty("coordinates", forecastData.getCoordinates());
                
                forecastObject.addProperty("temperature",forecastData.getTemperature());
                forecastObject.addProperty("wind",forecastData.getWind());
                forecastObject.addProperty("cloudiness",forecastData.getCloudiness());
                forecastObject.addProperty("AVGTemperature",forecastData.getAVGTemperature());
                forecastObject.addProperty("MAXTemperature",forecastData.getMAXTemperature());
                forecastObject.addProperty("MINTemperature",forecastData.getMINTemperature()); 

                weatherForecasts.add(forecastObject);
            }
            weatherPackage.add("weatherForecasts", weatherForecasts);
            saveData.add(weatherPackage);
        }
        
        gson.toJson(saveData, writer);
        writer.close();
        
    }
    
    /**
     * Method for loading the saved database from a JSON file
     */
    public void loadDataBase() throws IOException{
        
        Gson gson = new Gson();
        String fileName = "SavedData.json";
        Reader reader = Files.newBufferedReader(Paths.get(fileName));
        JsonArray response = gson.fromJson(reader, JsonArray.class);
        
        for(var data : response){
            JsonObject roadData = (JsonObject) data;
            
            //type roadTrafficData
            if(roadData.get("dataType").getAsString().equals("roadTrafficData")){
                
                //getting RoadData information
                String location = roadData.get("location").getAsString();
                String coordinates = roadData.get("coordinates").getAsString();
                String time = roadData.get("time").getAsString();
                
                //getting roadTrafficData information
                int numberOfTrafficMessages = roadData.get("numberOfTrafficMessages").getAsInt();
                double windSpeed = roadData.get("windSpeed").getAsDouble();
                String temperature = roadData.get("temperature").getAsString();
                String overAllCondition = roadData.get("overAllCondition").getAsString();
                String weatherSymbol = roadData.get("weatherSymbol").getAsString();
                
                //getting maintenance tasks
                var maintenanceTasks = roadData.get("maintenanceTasks").getAsJsonObject();                
                HashMap<String, Integer> maintenanceTaskMap = new HashMap<>();
                
                maintenanceTasks.keySet().forEach(keyStr ->{
                   int amount = maintenanceTasks.get(keyStr).getAsInt();
                   String taskName = keyStr;
                   maintenanceTaskMap.put(keyStr, amount);
                });
                
                //creating roadTrafficData class object
                RoadTrafficData roadTrafficData = new RoadTrafficData(location, coordinates, time);
                roadTrafficData.setNumberOfTrafficMessages(numberOfTrafficMessages);
                roadTrafficData.setWindSpeed(windSpeed);
                roadTrafficData.setTemperature(temperature);
                roadTrafficData.setOverAllCondition(overAllCondition);
                roadTrafficData.setWeatherSymbol(weatherSymbol);
                roadTrafficData.setMaintenanceTasks(maintenanceTaskMap);                
                
                //getting road forecasts
                JsonArray roadForecasts = roadData.get("roadForecasts").getAsJsonArray();
                HashMap<String, RoadTrafficDataForecast> forecastsMap = new HashMap<>();
                
                for(var forecast : roadForecasts){
                    JsonObject forecastData = (JsonObject) forecast;
                    
                    String fLocation = forecastData.get("location").getAsString();
                    String fCoordinates = forecastData.get("coordinates").getAsString();
                    String fTime = forecastData.get("time").getAsString();
                    
                    double fWindSpeed = forecastData.get("windSpeed").getAsDouble();
                    String fTemperature = forecastData.get("temperature").getAsString();
                    String fOverAllCondition = forecastData.get("overAllCondition").getAsString();
                    String fWeatherSymbol = forecastData.get("weatherSymbol").getAsString();                   
                    
                    String precipitation = forecastData.get("precipitation").getAsString();
                    String friction = forecastData.get("friction").getAsString();
                    String overAllcondition = forecastData.get("overAllcondition").getAsString();
                    String visibility = forecastData.get("visibility").getAsString();
                    boolean winterSlipperiness = forecastData.get("winterSlipperiness").getAsBoolean();
                    
                    var forecastObject = new RoadTrafficDataForecast(fLocation, fCoordinates, fTime);
                    forecastObject.setWindSpeed(fWindSpeed);
                    forecastObject.setTemperature(fTemperature);
                    forecastObject.setOverAllCondition(fOverAllCondition);
                    forecastObject.setWeatherSymbol(fWeatherSymbol);
                    
                    forecastObject.setPrecipitation(precipitation);
                    forecastObject.setFriction(friction);
                    forecastObject.setOverAllcondition(overAllcondition);
                    forecastObject.setVisibility(visibility);
                    forecastObject.setWinterSlipperines(winterSlipperiness);
                    
                    forecastsMap.put(fTime, forecastObject);                    
                }
                roadTrafficData.setForecasts(forecastsMap);    
            }
            
            //type RoadWeatherData
            else if(roadData.get("dataType").getAsString().equals("RoadWeatherData")){
                
                //getting RoadData information
                String location = roadData.get("location").getAsString();
                String coordinates = roadData.get("coordinates").getAsString();
                String time = roadData.get("time").getAsString();
                
                //getting roadWeatherData information
                float temperature = roadData.get("temperature").getAsFloat();
                float wind = roadData.get("wind").getAsFloat();
                float cloudiness = roadData.get("cloudiness").getAsFloat();
                float AVGTemperature = roadData.get("AVGTemperature").getAsFloat();
                float MAXTemperaturee = roadData.get("MAXTemperature").getAsFloat();
                float MINTemperature = roadData.get("MINTemperature").getAsFloat();
                
                //creating weather object
                var roadWeatherData = new RoadWeatherData(location,coordinates,time);
                roadWeatherData.setTemperature(temperature);
                roadWeatherData.setWind(wind);
                roadWeatherData.setCloudiness(cloudiness);
                roadWeatherData.setAVGTemperature(AVGTemperature);
                roadWeatherData.setMAXTemperature(MAXTemperaturee);
                roadWeatherData.setMINTemperature(MINTemperature);
                
                //getting forecast data
                JsonArray forecastArray = roadData.getAsJsonArray("weatherForecasts");
                HashMap<String, RoadWeatherData> forecasts = new HashMap<>();
                for(var forecastData : forecastArray){
                    JsonObject forecastObject = (JsonObject) forecastData;
                    
                    String fLocation = forecastObject.get("location").getAsString();
                    String fCoordinates = forecastObject.get("coordinates").getAsString();
                    String fTime = forecastObject.get("time").getAsString();
                    
                    float fTemperature = forecastObject.get("temperature").getAsFloat();
                    float fWind = forecastObject.get("wind").getAsFloat();
                    float fCloudiness = forecastObject.get("cloudiness").getAsFloat();
                    float fAVGTemperature = forecastObject.get("AVGTemperature").getAsFloat();
                    float fMAXTemperaturee = forecastObject.get("MAXTemperature").getAsFloat();
                    float fMINTemperature = forecastObject.get("MINTemperature").getAsFloat();
                    
                    var roadForecastData = new RoadWeatherData(fLocation,fCoordinates,fTime);
                    roadForecastData.setTemperature(fTemperature);
                    roadForecastData.setWind(fWind);
                    roadForecastData.setCloudiness(fCloudiness);
                    roadForecastData.setAVGTemperature(fAVGTemperature);
                    roadForecastData.setMAXTemperature(fMAXTemperaturee);
                    roadForecastData.setMINTemperature(fMINTemperature);
                    
                    forecasts.put(time, roadForecastData);                    
                }
                roadWeatherData.setForecasts(forecasts);                
            }
            
            //type not valid
            else{
                System.out.println("not valid datatype");
                continue;
            }
        }  
        
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
