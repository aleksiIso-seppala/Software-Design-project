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
    private float temperature;
    private float wind;
    private float cloudiness;
    private float AVGTemperature;
    private float MAXTemperature;
    private float MINTemperature;

    //String is the time (2, 4, 6 etc) and then the forecast
    // forecasts are the same as the observed so no need to do a new
    // class for them
    private HashMap<String, RoadWeatherData> forecasts;

    public RoadWeatherData(String location, String coordinates, String time){
        super(location, coordinates, time);
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getWind() {
        return wind;
    }

    public void setWind(float wind) {
        this.wind = wind;
    }

    public float getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(float cloudiness) {
        this.cloudiness = cloudiness;
    }

    public HashMap<String, RoadWeatherData> getForecasts() {
        return forecasts;
    }

    public void setForecasts(HashMap<String, RoadWeatherData> forecasts) {
        this.forecasts = forecasts;
    }

    public float getAVGTemperature() {
        return AVGTemperature;
    }

    public void setAVGTemperature(float AVGTemperature) {
        this.AVGTemperature = AVGTemperature;
    }

    public float getMAXTemperature() {
        return MAXTemperature;
    }

    public void setMAXTemperature(float MAXTemperature) {
        this.MAXTemperature = MAXTemperature;
    }

    public float getMINTemperature() {
        return MINTemperature;
    }

    public void setMINTemperature(float MINTemperature) {
        this.MINTemperature = MINTemperature;
    }

    @Override
    public String toString() {
        return "RoadWeatherData{" + "location=" + location + ", time=" + time + ", temperature=" + temperature + ", wind=" + wind + ", cloudiness=" + cloudiness + ", AVGTemperature=" + AVGTemperature + ", MAXTemperature=" + MAXTemperature + ", MINTemperature=" + MINTemperature + '}';
    }
}
