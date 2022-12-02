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

import fi.tuni.swdesign.group3.view.*;
import java.util.TreeMap;

/**
 * A class which handles the fetching and saving of data from the two different
 * APIs. Also provides the database for the program.
 * @author jukka, Aleksi Iso-Seppälä
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
        //digiTraficLocations.put("Suomi", new ArrayList<>(List.of("19.1", "32", "59.1", "72")));
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
    public RoadWeatherData fetchWeatherDataFuture(String location, String time, String futureTime){
        try {
            
            ArrayList loc = this.digiTraficLocations.get(location);
            org.w3c.dom.Document doc = RoadDataGetterFMI.getDOMDocument("fmi::forecast::harmonie::surface::point::simple", "", "", "", "", loc.get(2).toString(),
                            loc.get(0).toString(), time, futureTime, 
                            "temperature,windspeedms");
                    
            RoadWeatherData data = RoadDataParserXML.getDOMParsedDocument(doc, location, "", "", "", "", 
                    loc.get(2).toString(), loc.get(0).toString(), time);
            return data;
        } catch (IOException e){
            return null;
        }
    }
    
    public RoadWeatherData fetchWeatherDataPast(String location, String time, String futureTime){
        try {
            
            ArrayList loc = this.digiTraficLocations.get(location);
            org.w3c.dom.Document doc = RoadDataGetterFMI.getDOMDocument("fmi::observations::weather::simple", loc.get(0).toString().split("\\.",2)[0],
                            loc.get(2).toString().split("\\.",2)[0], loc.get(1).toString(),
                            loc.get(3).toString(), "", "", time, futureTime, 
                            "t2m,ws_10min,n_man,TA_PT1H_AVG,TA_PT1H_MAX,TA_PT1H_MIN");
                    
            RoadWeatherData data = RoadDataParserXML.getDOMParsedDocument(doc, location, loc.get(0).toString(),
                            loc.get(2).toString(), loc.get(1).toString(),
                            loc.get(3).toString(), "", "", time);
            return data;
        } catch (IOException e){
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
 * Method for saving the database containing the weather and road data, also contains 
 * a function call to savePreferences() since they must be saved with the datasets
 * <p>
 * The DataQuery must not be null, but either of the RoadTrafficData or the 
 * RoadWeatherData can be null if only one of those needs to be saved.
 * Only if you want to save both the roadData and the weatherData do you need
 * to input both of those parameters.
 * 
 * @param roadData, roadData object to be saved
 * @param weatherData, weatherData object to be saved
 * @param preference, DataQyery object to be saved
 * @param datasetName, name of the dataset
 * @return "OK" if it works, otherwise it returns an error message
 * @throws IOException 
 */
    public String saveDataBase(RoadTrafficData roadData,RoadWeatherData weatherData,
            DataQuery preference, String datasetName) throws IOException{
        
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //String fileName = "SavedData.json";
        //JsonArray saveData = new JsonArray();
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String fileName = "SavedData.json";
        Reader reader = Files.newBufferedReader(Paths.get(fileName));
        JsonArray saveData = gson.fromJson(reader, JsonArray.class);
        if(saveData == null){
            saveData = new JsonArray();
        }
        Writer writer = new FileWriter(fileName);
        
        //check if datasetName is taken
        for(var data : saveData){
            var dataO = data.getAsJsonObject();
            if(dataO.get("datasetName").getAsString().equals(datasetName)){
                gson.toJson(saveData, writer);
                writer.close();
                reader.close();
                return "Dataset ID already taken!";
            }
        }
        
        // saving roadData
        if(roadData != null){
            JsonObject roadPackage = new JsonObject();
            roadPackage.addProperty("dataType", "RoadTrafficData");
            roadPackage.addProperty("datasetName", datasetName);
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
                forecastObject.addProperty("coordinates",forecastData.getCoordinates());
                
                forecastObject.addProperty("windSpeed", forecastData.getWindSpeed());
                forecastObject.addProperty("temperature", forecastData.getTemperature());
                forecastObject.addProperty("overAllCondition", forecastData.getOverAllCondition());
                forecastObject.addProperty("weatherSymbol", forecastData.getWeatherSymbol());       

                forecastObject.addProperty("precipitation", forecastData.getPrecipitation());
                forecastObject.addProperty("friction", forecastData.getFriction());
                forecastObject.addProperty("overAllcondition", forecastData.getOverAllcondition());
                forecastObject.addProperty("visibility", forecastData.getVisibility());
                forecastObject.addProperty("windCondition",forecastData.getWindCondition());
                forecastObject.addProperty("winterSlipperiness",forecastData.isWinterSlipperines());

                roadForecasts.add(forecastObject);
            }
            roadPackage.add("roadForecasts",roadForecasts);

            saveData.add(roadPackage);
        }
        
        //saving weatherData
        if(weatherData != null){
            JsonObject weatherPackage = new JsonObject();
            weatherPackage.addProperty("dataType", "RoadWeatherData");
            weatherPackage.addProperty("datasetName", datasetName);
            
            weatherPackage.addProperty("location",weatherData.getLocation());
            weatherPackage.addProperty("coordinates",weatherData.getCoordinates());
            weatherPackage.addProperty("time",weatherData.getTime());
            
            weatherPackage.addProperty("temperature",weatherData.getTemperature());
            weatherPackage.addProperty("wind",weatherData.getWind());
            weatherPackage.addProperty("cloudiness",weatherData.getCloudiness());
            weatherPackage.addProperty("AVGTemperature",weatherData.getAVGTemperature());
            weatherPackage.addProperty("MAXTemperature",weatherData.getMAXTemperature());
            weatherPackage.addProperty("MINTemperature",weatherData.getMINTemperature());
            
            
            JsonArray avgMonthlyTemp = new JsonArray();
            if(weatherData.getMonthylAverage() != null){
                for(var temp : weatherData.getMonthylAverage().entrySet()){
                    JsonObject tempO = new JsonObject();
                    tempO.addProperty("date",temp.getKey());
                    Float[] floatList = temp.getValue();
                    tempO.addProperty("dailyAVG",floatList[0].toString());
                    tempO.addProperty("dailyMIN",floatList[1].toString());
                    tempO.addProperty("dailyMAX",floatList[2].toString());

                    avgMonthlyTemp.add(tempO);
                }
                weatherPackage.add("avgMonthlyTemp", avgMonthlyTemp);                
            }
            
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
        
        //saving preference
        String errors = savePreferences(preference, datasetName);
        if(!errors.equals("OK")){
            return errors;
        }
        gson.toJson(saveData, writer);
        writer.close();
        reader.close();
        return "OK";
    }
    
/**
 * Method for loading the database from a json file. The function creates new RoadData
 * Objects from the data in the json file. Has a parameter for the dataset
 * name that was used when saving the database. If the name does not mactch, you
 * will get a null value from the function.
 * <p>
 * The return value is a list with RoadData objects inside. If the saved data had
 * both RoadWeatherData and RoadTrafficData, you will get both of them inside the list.
 * otherwise you will get a list with only one RoadData object
 * 
 * @param datasetName, name of the dataset to load
 * @return null value if unsuccesful, otherwise RoadData[] list containing the created
 * objects
 * @throws IOException 
 */
    public RoadData[] loadDataBase(String datasetName) throws IOException{
        
        Gson gson = new Gson();
        String fileName = "SavedData.json";
        Reader reader = Files.newBufferedReader(Paths.get(fileName));
        JsonArray response = gson.fromJson(reader, JsonArray.class);
        if(response == null){
            return null;
        }
        RoadData[] returnData = {null, null};
        
        for(var data : response){
            JsonObject roadData = (JsonObject) data;
            
            if(!roadData.get("datasetName").getAsString().equals(datasetName)){
                continue;
            }
            
            //type roadTrafficData
            if(roadData.get("dataType").getAsString().equals("RoadTrafficData")){
                
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

                    var forecastObject = new RoadTrafficDataForecast(fLocation, fCoordinates, fTime);                    
                    if(forecastData.has("precipitation")){
                        String precipitation = forecastData.get("precipitation").getAsString();                        
                        forecastObject.setPrecipitation(precipitation);
                    }
                    if(forecastData.has("friction")){
                        String precipitation = forecastData.get("friction").getAsString();                        
                        forecastObject.setFriction(precipitation);
                    }
                    if(forecastData.has("overAllcondition")){
                        String overAllcondition = forecastData.get("overAllcondition").getAsString();                        
                        forecastObject.setOverAllcondition(overAllcondition);
                    }
                    if(forecastData.has("visibility")){
                        String visibility = forecastData.get("visibility").getAsString();                        
                        forecastObject.setPrecipitation(visibility);
                    }
                    if(forecastData.has("windCondition")){
                        String windCondition = forecastData.get("windCondition").getAsString();                        
                        forecastObject.setWindCondition(windCondition);
                    }
                    if(forecastData.has("winterSlipperiness")){
                        boolean winterSlipperiness = forecastData.get("winterSlipperiness").getAsBoolean();                        
                        forecastObject.setWinterSlipperines((winterSlipperiness));
                    }

                    forecastObject.setWindSpeed(fWindSpeed);
                    forecastObject.setTemperature(fTemperature);
                    forecastObject.setOverAllCondition(fOverAllCondition);
                    forecastObject.setWeatherSymbol(fWeatherSymbol);                    
                    
                    forecastsMap.put(fTime, forecastObject);                    
                }
                roadTrafficData.setForecasts(forecastsMap);
                returnData[0] = roadTrafficData;
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
                
                //getting avg amounts for month
                if(roadData.has("avgMonthlyTemp")){
                    JsonArray avgArray = roadData.getAsJsonArray("avgMonthlyTemp");                    
                    TreeMap<String, Float[]> avgMonthlyTemp = new TreeMap<>();

                    for(var avgdata : avgArray){
                        JsonObject avgO = (JsonObject) avgdata;
                        String date = avgO.get("date").getAsString();
                        float dailyAVG = avgO.get("dailyAVG").getAsFloat();
                        float dailyMIN = avgO.get("dailyMIN").getAsFloat();
                        float dailyMAX = avgO.get("dailyMAX").getAsFloat();

                        Float[] floatList = {dailyAVG,dailyMIN,dailyMAX};
                        avgMonthlyTemp.put(date, floatList);
                    }
                    roadWeatherData.setMonthlyAverage(avgMonthlyTemp);
                }


                
                //getting forecast data
                JsonArray forecastArray = roadData.getAsJsonArray("weatherForecasts");
                TreeMap<String, RoadWeatherData> forecasts = new TreeMap<>();
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
                    
                    forecasts.put(fTime, roadForecastData);
                }
                roadWeatherData.setForecasts(forecasts);
                returnData[1] = roadWeatherData;
            }
            
            //name not matching
            else{
            }
        }
        reader.close();
        //no data matching the name
        if(returnData[0] == null && returnData[1] == null){
            return null;
        }
        //roadTrafficData found
        else if(returnData[1] == null){
            RoadData[] newReturn = {returnData[0]};
            return newReturn;
        }
        //roadweatherData found
        else if(returnData[0] == null){
            RoadData[] newReturn = {returnData[1]};
            return newReturn;
        }
        //both found
        else{
            return returnData;
        }
    }
    
    /**
     * Method for saving the preferences for data visualization from the GUI. Creates
     * a list to JsonFile so multiple datasets can be saved. Datasets are separated
     * by the datasetName parameter.
     * <p>
     * returns "OK" if the save was succesful, otherwise it returns the error message.
     * 
     * @param dataQuery, object containing the preferences
     * @param datasetName the name of the dataset
     * @return a String containing the status of the save
     * @throws IOException 
     */
    public String savePreferences(DataQuery dataQuery, String datasetName) throws IOException{
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String fileName = "SavedPreferences.json";
        Reader reader = Files.newBufferedReader(Paths.get(fileName));
        JsonArray saveData = gson.fromJson(reader, JsonArray.class);
        if(saveData == null){
            saveData = new JsonArray();
        }        
        
        Writer writer = new FileWriter(fileName);
        
        //check if datasetName is taken
        for(var data : saveData){
            var dataO = data.getAsJsonObject();
            if(dataO.get("datasetName").getAsString().equals(datasetName)){
                gson.toJson(saveData, writer);
                writer.close();
                reader.close();
                return "dataset name already taken!";
            }
        }
        
        JsonObject userPreferences = new JsonObject();
        
        String dataType = dataQuery.getDataType();
        String location = dataQuery.getLocation();
        String[] timelineStart = dataQuery.getTimelineStart();
        String timelineStartH = timelineStart[0];
        String timelineStartM = timelineStart[1];
        String[] timelineEnd = dataQuery.getTimelineEnd();
        String timelineEndH = timelineEnd[0];
        String timelineEndM = timelineEnd[1]; 
        
        userPreferences.addProperty("datasetName",datasetName);
        userPreferences.addProperty("dataType", dataType);
        userPreferences.addProperty("location", location);
        userPreferences.addProperty("timelineStartH",timelineStartH);
        userPreferences.addProperty("timelineStartM",timelineStartM);
        userPreferences.addProperty("timelineEndH",timelineEndH);
        userPreferences.addProperty("timelineEndM",timelineEndM);        
        
        if(dataQuery instanceof RoadDataQuery){
            RoadDataQuery query = (RoadDataQuery) dataQuery;
            
            ArrayList<String> selectedTasks = query.getSelectedTasks();
            JsonArray jsonSelectedTasks = new JsonArray();
            for(var task : selectedTasks){
                jsonSelectedTasks.add(task);
            }
            
            ArrayList<String> selectedForecasts = query.getSelectedForecasts();
            JsonArray jsonSelectedForecasts = new JsonArray();
            for(var task : selectedForecasts){
                jsonSelectedForecasts.add(task);
            }
            
            String forecastTime = query.getForecastTime();
            
            userPreferences.addProperty("forecastTime", forecastTime);
            userPreferences.add("selectedTasks", jsonSelectedTasks);
            userPreferences.add("selectedForecasts", jsonSelectedForecasts);
            
        }
        else if(dataQuery instanceof WeatherDataQuery){
            WeatherDataQuery query = (WeatherDataQuery) dataQuery;
            
            ArrayList<String> selectedObsParams = query.getSelectedObsParams();
            JsonArray jsonSelectedObs = new JsonArray();
            for(var param : selectedObsParams){
                jsonSelectedObs.add(param);
            }
            
            ArrayList<String> selectedPreParams = query.getSelectedPreParams();
            JsonArray jsonSelectedPre = new JsonArray();
            for(var param : selectedPreParams){
                jsonSelectedPre.add(param);
            }
            
            ArrayList<String> selectedPreMonthParams = query.getSelectedPerMonthParams();
            JsonArray jsonSelectedPreMonth = new JsonArray();
            for(var param : selectedPreMonthParams){
                jsonSelectedPreMonth.add(param);
            }
            
            userPreferences.add("selectedObsParams",jsonSelectedObs);
            userPreferences.add("selectedPreParams",jsonSelectedPre);
            userPreferences.add("selectedPreMonthParams",jsonSelectedPreMonth);
            
        }
        else if(dataQuery instanceof CombinedDataQuery){
            CombinedDataQuery query = (CombinedDataQuery) dataQuery;
            
            //roadData
            ArrayList<String> selectedTasks = query.getSubRoadDQ().getSelectedTasks();
            JsonArray jsonSelectedTasks = new JsonArray();
            for(var task : selectedTasks){
                jsonSelectedTasks.add(task);
            }
            
            ArrayList<String> selectedForecasts = query.getSubRoadDQ().getSelectedForecasts();
            JsonArray jsonSelectedForecasts = new JsonArray();
            for(var task : selectedForecasts){
                jsonSelectedForecasts.add(task);
            }
            String forecastTime = query.getSubRoadDQ().getForecastTime();
            
            //weatherData
            ArrayList<String> selectedObsParams = query.getSubWeatherDQ().getSelectedObsParams();
            JsonArray jsonSelectedObs = new JsonArray();
            for(var param : selectedObsParams){
                jsonSelectedObs.add(param);
            }
            
            ArrayList<String> selectedPreParams = query.getSubWeatherDQ().getSelectedPreParams();
            JsonArray jsonSelectedPre = new JsonArray();
            for(var param : selectedPreParams){
                jsonSelectedPre.add(param);
            }
            
            ArrayList<String> selectedPreMonthParams = query.getSubWeatherDQ().getSelectedPerMonthParams();
            JsonArray jsonSelectedPreMonth = new JsonArray();
            for(var param : selectedPreMonthParams){
                jsonSelectedPreMonth.add(param);
            }
            
            //setting parameters
            userPreferences.addProperty("forecastTime", forecastTime);
            userPreferences.add("selectedTasks", jsonSelectedTasks);
            userPreferences.add("selectedForecasts", jsonSelectedForecasts);            
            userPreferences.add("selectedObsParams",jsonSelectedObs);
            userPreferences.add("selectedPreParams",jsonSelectedPre);
            userPreferences.add("selectedPreMonthParams",jsonSelectedPreMonth);
         
        }
        saveData.add(userPreferences);
        
        gson.toJson(saveData,writer);
        writer.close();
        return "OK";
    }
    
    /**
     * A method for loading the user preferences for GUI. Loads the preferences
     * from the json file. Firstly checks if the datasetName matches anything
     * in the json file.
     * <p>
     * Returns a null object if the loading was unsuccesful, otherwise returns the
     * DataQuery object (which can be either RoadData, WeatherData or Combined Data
     * object.
     * 
     * @param datasetName A string containing the name of the preference
     * @return DataQuery containing the preferences
     * @throws IOException 
     */
    public DataQuery loadPreferences(String datasetName) throws IOException{
        
        Gson gson = new Gson();
        String fileName = "SavedPreferences.json";
        Reader reader = Files.newBufferedReader(Paths.get(fileName));
        JsonArray response = gson.fromJson(reader, JsonArray.class);
        if(response == null){
            return null;
        }
        for(var preference : response){
            JsonObject preferenceO = (JsonObject) preference;
            
            if(!datasetName.equals(preferenceO.get("datasetName").getAsString())){
                continue;
            }
            
            String dataType = preferenceO.get("dataType").getAsString();
            String location = preferenceO.get("location").getAsString();
            String timelineStartH = preferenceO.get("timelineStartH").getAsString();
            String timelineStartM = preferenceO.get("timelineStartM").getAsString();
            String timelineEndH = preferenceO.get("timelineEndH").getAsString();
            String timelineEndM = preferenceO.get("timelineEndM").getAsString();
            
            String[] timelineStart = {timelineStartH,timelineStartM};
            String[] timelineEnd = {timelineEndH,timelineEndM};
            
            
            if(dataType.equals("Road data")){
                
                JsonArray selectedTasks = preferenceO.getAsJsonArray("selectedTasks");
                ArrayList<String> tasksList = new ArrayList<>();
                for(var obs : selectedTasks){
                    tasksList.add(obs.getAsString());
                }
                
                JsonArray selectedForecasts = preferenceO.getAsJsonArray("selectedForecasts");
                ArrayList<String> ForecastsList = new ArrayList<>();
                for(var obs : selectedForecasts){
                    ForecastsList.add(obs.getAsString());
                }                
                
                String forecastTime = preferenceO.get("forecastTime").getAsString();
                
                var roadDataQuery = DataQueryFactory.makeDataQuery(dataType);
                RoadDataQuery castedData = (RoadDataQuery) roadDataQuery;
                
                castedData.setLocation(location);
                castedData.setTimelineStart(timelineStart);
                castedData.setTimelineEnd(timelineEnd);
                castedData.setSelectedTasks(tasksList);
                castedData.setSelectedForecasts(ForecastsList);
                castedData.setForecastTime(forecastTime);
                
                return castedData;
                
            }
            
            
            else if(dataType.equals("Weather data")){
                
                JsonArray selectedObs = preferenceO.getAsJsonArray("selectedObsParams");
                ArrayList<String> obsList = new ArrayList<>();
                for(var obs : selectedObs){
                    obsList.add(obs.getAsString());
                }

                JsonArray selectedPre = preferenceO.getAsJsonArray("selectedPreParams");
                ArrayList<String> preList = new ArrayList<>();
                for(var obs : selectedPre){
                    preList.add(obs.getAsString());
                }
                
                JsonArray selectedPreMonth = preferenceO.getAsJsonArray("selectedPreMonthParams");
                ArrayList<String> preMonthList = new ArrayList<>();
                for(var obs : selectedPreMonth){
                    preMonthList.add(obs.getAsString());
                }
                
                var weatherDataQuery = DataQueryFactory.makeDataQuery(dataType);
                WeatherDataQuery castedData = (WeatherDataQuery) weatherDataQuery;
                
                castedData.setLocation(location);
                castedData.setSelectedObsParams(obsList);
                castedData.setSelectedPreParams(preList);
                castedData.setSelectedPerMonthParams(preMonthList);
                castedData.setTimelineStart(timelineStart);
                castedData.setTimelineEnd(timelineEnd);
                
                return castedData;
                
            }
            else if(dataType.equals("Combined data")){
                
                //roadData
                JsonArray selectedTasks = preferenceO.getAsJsonArray("selectedTasks");
                ArrayList<String> tasksList = new ArrayList<>();
                for(var obs : selectedTasks){
                    tasksList.add(obs.getAsString());
                }
                
                JsonArray selectedForecasts = preferenceO.getAsJsonArray("selectedForecasts");
                ArrayList<String> ForecastsList = new ArrayList<>();
                for(var obs : selectedForecasts){
                    ForecastsList.add(obs.getAsString());
                }                 
                String forecastTime = preferenceO.get("forecastTime").getAsString();
                
                //weatherData
                JsonArray selectedObs = preferenceO.getAsJsonArray("selectedObsParams");
                ArrayList<String> obsList = new ArrayList<>();
                for(var obs : selectedObs){
                    obsList.add(obs.getAsString());
                }

                JsonArray selectedPre = preferenceO.getAsJsonArray("selectedPreParams");
                ArrayList<String> preList = new ArrayList<>();
                for(var obs : selectedPre){
                    preList.add(obs.getAsString());
                }
                
                JsonArray selectedPreMonth = preferenceO.getAsJsonArray("selectedPreMonthParams");
                ArrayList<String> preMonthList = new ArrayList<>();
                for(var obs : selectedPreMonth){
                    preMonthList.add(obs.getAsString());
                }
                
                var combinedDataQuery = DataQueryFactory.makeDataQuery(dataType);
                var weatherDataQuery = DataQueryFactory.makeDataQuery("Weather data");
                var roadDataQuery = DataQueryFactory.makeDataQuery("Road data");
                var castedweatherDQ = (WeatherDataQuery) weatherDataQuery;
                var castedroadDQ = (RoadDataQuery) roadDataQuery;
                
                CombinedDataQuery castedData = (CombinedDataQuery) combinedDataQuery;
                castedData.setSubRoadDQ(castedroadDQ);
                castedData.setSubWeatherDQ(castedweatherDQ);
                
                castedData.setLocation(location);
                castedData.setTimelineStart(timelineStart);
                castedData.setTimelineEnd(timelineEnd);
                
                castedData.getSubRoadDQ().setLocation(location);
                castedData.getSubRoadDQ().setTimelineStart(timelineStart);
                castedData.getSubRoadDQ().setTimelineEnd(timelineEnd);
                castedData.getSubRoadDQ().setSelectedTasks(tasksList);
                castedData.getSubRoadDQ().setSelectedForecasts(ForecastsList);
                castedData.getSubRoadDQ().setForecastTime(forecastTime);
                
                castedData.getSubWeatherDQ().setLocation(location);
                castedData.getSubWeatherDQ().setTimelineStart(timelineStart);
                castedData.getSubWeatherDQ().setTimelineEnd(timelineStart);
                castedData.getSubWeatherDQ().setSelectedObsParams(obsList);
                castedData.getSubWeatherDQ().setSelectedPreParams(preList);
                castedData.getSubWeatherDQ().setSelectedPerMonthParams(preMonthList);

                return castedData;
            }
            
        }
        return null;
    }
    
    /**
     * Method for getting the hardcoded locations which will then be added
     * to the GUI
     * @return ArrayList which contains the locations as a string
     */
    public HashMap<String, ArrayList<String>> getHardCodedLocations(){
        return this.digiTraficLocations;
    }
    
    /**
     * Method for getting the maintenancetask names for the GUI
     * @return ArrayList containing the maintenancetask names
     */
    public ArrayList<String> getMaintenanceTaskNames(){
        try {
            JsonArray arr = RoadDataGetterDigitraffic.getMaintenanceTaskNamesData();
            ArrayList<String> names = new ArrayList<>();
            names = RoadDataParserJSON.readMaintenanceTaskNames(arr,"clean");
            return names;
        } catch (Exception e) {
        }
        return null;
    }
    
    /**
     * Method for getting monthly averages of temperatures for a given location
     * @param location given as a String
     * @param month, given as a String
     * @return TreeMap containing the monthly averages
     */
    public TreeMap<String, Float[]> fetchMonthlyAverages(String location, String month){
        try {
            ArrayList loc = this.digiTraficLocations.get(location);
            String[] parsed = month.split("-");
            
            
            TreeMap<String, Float[]> monthlyData = RoadDataParserXML.getMonthlyTemperatureData(location, loc.get(0).toString().split("\\.",2)[0],
                            loc.get(2).toString().split("\\.",2)[0], loc.get(1).toString(),
                            
                            loc.get(3).toString(), parsed[0]+"-"+parsed[1]);
            return monthlyData;
        } catch (Exception e){
        }
        return null;
    }
}

