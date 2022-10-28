/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import java.util.HashMap;

/**
 *
 * @author jukka
 */
public class RoadWeatherData extends RoadData{
    private int temperature;
    private int wind;
    private String cloudiness;

    //String is the time (2, 4, 6 etc) and then the forecast
    // forecasts are the same as the observed so no need to do a new
    // class for them
    private HashMap<String, RoadWeatherData> forecasts;

    public RoadWeatherData(String location, String coordinates, String time){
        super(location, coordinates, time);
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getWind() {
        return wind;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }

    public String getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(String cloudiness) {
        this.cloudiness = cloudiness;
    }

    public HashMap<String, RoadWeatherData> getForecasts() {
        return forecasts;
    }

    public void setForecasts(HashMap<String, RoadWeatherData> forecasts) {
        this.forecasts = forecasts;
    }
}
