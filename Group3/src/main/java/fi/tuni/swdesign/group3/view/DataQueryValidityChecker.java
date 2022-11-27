/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * An abstract base class for checking the validity of the DataQuery created
 * using the parameters given by the user.
 * @author Lauri Puoskari
 */
public abstract class DataQueryValidityChecker {
    /**
     * Constant strings used in checking the validity and communicating
     * the result.
     */
    protected static final String DQ_IS_VALID = "Data query is valid.";
    protected static final String INVALID_LOCATION = "Invalid location!";
    protected static final String INVALID_START_TIME = "Invalid starting time/date!";
    protected static final String INVALID_END_TIME = "Invalid ending time/date!";
    protected static final String NO_PARAMS = "No data parameters chosen!";
    protected static final String DATE_TIME_FORMAT = "HH.mm dd.MM.uuuu";
    /**
     * Current instance of the MainView.
     */
    protected MainView mainView;
    /**
     * The DataQuery to be checked.
     */
    private DataQuery query;
    
    /**
     * A constructor in which the current instance of the MainView and the
     * DataQuery to be checked are stored.
     * @param mainView the current instance of the MainView.
     * @param query the DataQuery to be checked.
     */
    DataQueryValidityChecker(MainView mainView, DataQuery query) {
        this.mainView = mainView;
        this.query = query;
    }
    
    /**
     * A Factory method for creating the different subclasses of this abstract
     * base class.
     * @param mainView the current instance of the MainView.
     * @param query the DataQuery to be checked.
     * @return an instance of the chosen subclass of DataQueryValidityChecker.
     */
    public static DataQueryValidityChecker makeDataQueryValidityChecker(
            MainView mainView, DataQuery query) {
        if (query instanceof RoadDataQuery roadDataQuery) {
            return new RoadDQValidityChecker(mainView, roadDataQuery);
        }
        else if (query instanceof WeatherDataQuery weatherDataQuery) {
            return new WeatherDQValidityChecker(mainView, weatherDataQuery);
        }
        else if (query instanceof CombinedDataQuery combinedDataQuery) {
            return new CombinedDQValidityChecker(mainView, combinedDataQuery);
        }
        return null;
    }
    
    /**
     * A method for checking the validity of the location of the DataQuery.
     * @return A string communicating the result of the check.
     */
    protected String checkLocationValidity() {
        if (!this.mainView.getLocations().contains(this.query.getLocation())) {
            return INVALID_LOCATION;
        }
        return DQ_IS_VALID;
    }
    
    /**
     * A method for checking the validity of the starting and ending times of
     * the DataQuery.
     * @return A string communicating the result of the check.
     */
    protected String checkDateTimeValidity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        String startTimeDate = this.query.getTimelineStart()[0] + " " 
                + this.query.getTimelineStart()[1];
        try {
            LocalDateTime dateTime = LocalDateTime.parse(startTimeDate, formatter);
        }
        catch (DateTimeParseException exception) {
            return INVALID_START_TIME;
        }
        
        String endTimeDate = this.query.getTimelineEnd()[0] + " " 
                + this.query.getTimelineEnd()[1];
        try {
            LocalDateTime dateTime = LocalDateTime.parse(endTimeDate, formatter);
        }
        catch (DateTimeParseException exception) {
            return INVALID_END_TIME;
        }
        return DQ_IS_VALID;
    }
    
    /**
     * An abstract method for checking the validity of the DataQuery.
     * @return A string communicating the result of the check.
     */
    public abstract String checkDataQueryValidity();
}
