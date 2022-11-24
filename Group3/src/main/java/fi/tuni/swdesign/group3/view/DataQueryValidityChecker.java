/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Lauri
 */
public abstract class DataQueryValidityChecker {
    protected MainView mainView;
    private DataQuery query;
    
    DataQueryValidityChecker(MainView mainView, DataQuery query) {
        this.mainView = mainView;
        this.query = query;
    }
    
    public static DataQueryValidityChecker makeDataQueryValidityChecker(
            MainView mainView, DataQuery query) {
        if (query instanceof RoadDataQuery) {
            return new RoadDQValidityChecker(mainView, (RoadDataQuery) query);
        }
        else if (query instanceof WeatherDataQuery) {
            return new WeatherDQValidityChecker(mainView, (WeatherDataQuery) query);
        }
        else if (query instanceof CombinedDataQuery) {
            return new CombinedDQValidityChecker(mainView, (CombinedDataQuery) query);
        }
        return null;
    }
    
    protected String checkLocationValidity() {
        if (!this.mainView.getLocations().contains(this.query.getLocation())) {
            return "Invalid location!";
        }
        return "Data query is valid.";
    }
    
    protected String checkDateTimeValidity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm dd.MM.uuuu");
        String startTimeDate = this.query.getTimelineStart()[0] + " " 
                + this.query.getTimelineStart()[1];
        try {
            LocalDateTime dateTime = LocalDateTime.parse(startTimeDate, formatter);
        }
        catch (DateTimeParseException exception) {
            return "Invalid starting time/date!";
        }
        
        String endTimeDate = this.query.getTimelineEnd()[0] + " " 
                + this.query.getTimelineEnd()[1];
        try {
            LocalDateTime dateTime = LocalDateTime.parse(endTimeDate, formatter);
        }
        catch (DateTimeParseException exception) {
            return "Invalid ending time/date!";
        }
        return "Data query is valid.";
    }
    
    public abstract String checkDataQueryValidity();
}
