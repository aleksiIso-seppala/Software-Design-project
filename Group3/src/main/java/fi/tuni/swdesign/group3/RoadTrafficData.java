/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jukka
 * 
 */
public class RoadTrafficData extends RoadData{
    private HashMap<String,Integer> maintenanceTasks;
    private ArrayList<String> trafficMessages;
    private int numberOfTrafficMessages;
    private double windSpeed;
    private String temperature;
    private String overAllCondition;
    private String weatherSymbol;
    
    // String is the time (2, 4, 6 etc) and then the forecast
    private HashMap<String, RoadTrafficDataForecast> forecasts;
    
    
    public RoadTrafficData(String location, String coordinates, String time){
        super(location, coordinates, time);
        this.maintenanceTasks = new HashMap<>();
        this.trafficMessages = new ArrayList<>();
        this.forecasts = new HashMap<>();
    }

    public HashMap<String,Integer> getMaintenanceTasks() {
        return maintenanceTasks;
    }

    public void setMaintenanceTasks(HashMap<String,Integer> maintenanceTasks) {
        this.maintenanceTasks = maintenanceTasks;
    }

    public ArrayList<String> getTrafficMessages() {
        return trafficMessages;
    }

    public void setTrafficMessages(ArrayList<String> trafficMessages) {
        this.trafficMessages = trafficMessages;
    }

    public int getNumberOfTrafficMessages() {
        return numberOfTrafficMessages;
    }

    public void setNumberOfTrafficMessages(int numberOfTrafficMessages) {
        this.numberOfTrafficMessages = numberOfTrafficMessages;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getOverAllCondition() {
        return overAllCondition;
    }

    public void setOverAllCondition(String overAllCondition) {
        this.overAllCondition = overAllCondition;
    }

    public String getWeatherSymbol() {
        return weatherSymbol;
    }

    public void setWeatherSymbol(String weatherSymbol) {
        this.weatherSymbol = weatherSymbol;
    }

    public HashMap<String, RoadTrafficDataForecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(HashMap<String, RoadTrafficDataForecast> forecasts) {
        this.forecasts = forecasts;
    }    
}
