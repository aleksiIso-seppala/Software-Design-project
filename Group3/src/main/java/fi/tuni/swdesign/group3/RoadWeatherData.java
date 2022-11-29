/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * A Class describing the data from FMI
 * @author jukka
 */
public class RoadWeatherData extends RoadData{
    private float temperature;
    private float wind;
    private float cloudiness;
    private float AVGTemperature;
    private float MAXTemperature;
    private float MINTemperature;

    // String is the time (2, 4, 6 etc) and then the forecast
    // forecasts are the same as the observed so no need to do a new
    // class for them
    private TreeMap<String, RoadWeatherData> forecasts;
    //private TreeMap<String, float>

    /**
     * Default constructor for RoadWeatherData
     * @param location as a String as in name of the location ie. city
     * @param coordinates of the data as a String
     * @param time of the data as a String
     */
    public RoadWeatherData(String location, String coordinates, String time){
        super(location, coordinates, time);
    }

    /**
     * Getter for the temperature
     * @return temperature as a float
     */
    public float getTemperature() {
        return temperature;
    }

    /**
     * Setter for the temperature
     * @param temperature as float
     */
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    /**
     * Getter for the windspeed
     * @return windspeed as float
     */
    public float getWind() {
        return wind;
    }

    /**
     * Setter for the windspeed
     * @param wind speed of the wind as float
     */
    public void setWind(float wind) {
        this.wind = wind;
    }

    /**
     * Getter for the cloudiness
     * @return cloudiness level as float
     */
    public float getCloudiness() {
        return cloudiness;
    }

    /**
     * Setter for the cloudiness
     * @param cloudiness level as float
     */
    public void setCloudiness(float cloudiness) {
        this.cloudiness = cloudiness;
    }

    /**
     * Getter for the forecasts
     * @return forecasts in a HashMap containing data every 2h for 12hrs
     */
    public TreeMap<String, RoadWeatherData> getForecasts() {
        return forecasts;
    }

    /**
     * Setter for the forecasts
     * @param forecasts stored in a HashMap
     */
    public void setForecasts(TreeMap<String, RoadWeatherData> forecasts) {
        this.forecasts = forecasts;
    }

    /**
     * Getter for the average temperature
     * @return average temperature as a float
     */
    public float getAVGTemperature() {
        return AVGTemperature;
    }

    /**
     * Setter for the average temperature
     * @param AVGTemperature as float 
     */
    public void setAVGTemperature(float AVGTemperature) {
        this.AVGTemperature = AVGTemperature;
    }

    /**
     * Getter for the maximum temperature
     * @return maximum temperature as float
     */
    public float getMAXTemperature() {
        return MAXTemperature;
    }

    /**
     * Setter for the maximum temperature
     * @param MAXTemperature as float
     */
    public void setMAXTemperature(float MAXTemperature) {
        this.MAXTemperature = MAXTemperature;
    }

    /**
     * Getter for the minimum temperature
     * @return minimum temperature as float
     */
    public float getMINTemperature() {
        return MINTemperature;
    }

    /**
     * Setter for the minimum temperature
     * @param MINTemperature as float
     */
    public void setMINTemperature(float MINTemperature) {
        this.MINTemperature = MINTemperature;
    }

    /**
     * Override method for toString()
     * @return the object stringified
     */
    @Override
    public String toString() {
        return "RoadWeatherData{" + "location=" + location + ", time=" + time + ", temperature=" + temperature + ", wind=" + wind + ", cloudiness=" + cloudiness + ", AVGTemperature=" + AVGTemperature + ", MAXTemperature=" + MAXTemperature + ", MINTemperature=" + MINTemperature + '}';
    }
}
