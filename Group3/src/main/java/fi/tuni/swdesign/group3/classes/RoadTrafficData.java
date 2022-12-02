/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.classes;

import java.util.HashMap;

/**
 * Class describing RoadTrafficData which is fetched from Digitraffic API.
 * Extends RoadData
 * @author jukka
 */
public class RoadTrafficData extends RoadData{
    private HashMap<String,Integer> maintenanceTasks;
    private int numberOfTrafficMessages;
    private double windSpeed;
    private String temperature;
    private String overAllCondition;
    private String weatherSymbol;
    
    // String is the time (2, 4, 6 etc) and then the forecast
    private HashMap<String, RoadTrafficDataForecast> forecasts;
    
    /**
     * Default constructor for RoadTrafficData
     * @param location name of the location as a string i.e the city
     * @param coordinates of the location as a string
     * @param time of the data fetched as a string
     */
    public RoadTrafficData(String location, String coordinates, String time){
        super(location, coordinates, time);
        this.maintenanceTasks = new HashMap<>();
        this.forecasts = new HashMap<>();
    }

    /**
     * Getter for the maintenance tasks
     * @return HashMap of the maintenance tasks
     */
    public HashMap<String,Integer> getMaintenanceTasks() {
        return maintenanceTasks;
    }

    /**
     * Setter for the maintenance tasks
     * @param maintenanceTasks HashMap of the tasks
     */
    public void setMaintenanceTasks(HashMap<String,Integer> maintenanceTasks) {
        this.maintenanceTasks = maintenanceTasks;
    }

    /**
     * Getter for the number of traffic messages
     * @return number of traffic messages as int
     */
    public int getNumberOfTrafficMessages() {
        return numberOfTrafficMessages;
    }

    /**
     * Setter for the number of traffic messages
     * @param numberOfTrafficMessages as int
     */
    public void setNumberOfTrafficMessages(int numberOfTrafficMessages) {
        this.numberOfTrafficMessages = numberOfTrafficMessages;
    }

    /**
     * Getter for windspeed
     * @return the windspeed as a double
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * Setter for the windspeed
     * @param windSpeed as double
     */
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * Getter for the temperature
     * @return the temperature as a String
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * Setter for the temperature
     * @param temperature as a String
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * Getter for the overall road condition
     * @return the condition of the road as string
     */
    public String getOverAllCondition() {
        return overAllCondition;
    }

    /**
     * Setter for the overall road condition
     * @param overAllCondition as a String
     */
    public void setOverAllCondition(String overAllCondition) {
        this.overAllCondition = overAllCondition;
    }

    /**
     * Getter for the weather symbol
     * @return String which has the weater symbol id
     */
    public String getWeatherSymbol() {
        return weatherSymbol;
    }

    /**
     * Setter for the weather symbol
     * @param weatherSymbol id as a String
     */
    public void setWeatherSymbol(String weatherSymbol) {
        this.weatherSymbol = weatherSymbol;
    }

    /**
     * Getter for the forecasts
     * @return forecasts for every 2h for 12hrs as a HashMap
     */
    public HashMap<String, RoadTrafficDataForecast> getForecasts() {
        return forecasts;
    }

    /**
     * Setter for the forecasts
     * @param forecasts stored in a HashMap of RoadTrafficDataForecast objects
     */
    public void setForecasts(HashMap<String, RoadTrafficDataForecast> forecasts) {
        this.forecasts = forecasts;
    }    
}
