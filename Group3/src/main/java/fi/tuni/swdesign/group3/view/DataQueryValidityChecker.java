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
     * A constant representing the maximum amount of days in the timeline.
     */
    private static final int MAX_DAYS = 7;
    /**
     * A constant representing the index of the time.
     */
    private static final int TIME_I = 0;
    /**
     * A constant representing the index of the date.
     */
    private static final int DATE_I = 1;
    /**
     * A constant string informing that the DataQuery is valid.
     */
    protected static final String DQ_IS_VALID = "Data query is valid.";
    /**
     * A constant string informing that the location is invalid.
     */
    protected static final String INVALID_LOCATION = "Invalid location!";
    /**
     * A constant string informing that the starting time or date is invalid.
     */
    protected static final String INVALID_START_TIME = "Invalid starting time/date!";
    /**
     * A constant string informing that the ending time or date is invalid.
     */
    protected static final String INVALID_END_TIME = "Invalid ending time/date!";
    /**
     * A constant string informing that no parameters were chosen.
     */
    protected static final String NO_PARAMS = "No data parameters chosen!";
    /**
     * A constant string informing that the timeline is inversed and it's not
     * valid.
     */
    protected static final String INVERSED_TIMELINE = "Timeline cannot be inversed!";
    /**
     * A constant string informing that the timeline is too long and it's not
     * valid.
     */
    protected static final String TIMELINE_TOO_LONG = "Timeline is too long (max 7 days)!";
    /**
     * A constant string representig a space.
     */
    protected static final String SPACE = " ";
    /**
     * A constant string representing the format in which the DateTime should
     * be in.
     */
    protected static final String DATE_TIME_FORMAT = "HH.mm dd.MM.uuuu";
    /**
     * Current instance of the MainView.
     */
    protected MainView mainView;
    /**
     * The DataQuery to be checked.
     */
    private final DataQuery query;
    
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
     * Null if query type is not implemented.
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
        String startTimeStr = this.query.getTimelineStart()[TIME_I] + SPACE 
                + this.query.getTimelineStart()[DATE_I];
        LocalDateTime startDateTime;
        // Checking if starting time and date are of valid format.
        try {
            startDateTime = LocalDateTime.parse(startTimeStr, formatter);
        }
        catch (DateTimeParseException exception) {
            return INVALID_START_TIME;
        }
        
        String endTimeStr = this.query.getTimelineEnd()[TIME_I] + SPACE 
                + this.query.getTimelineEnd()[DATE_I];
        LocalDateTime endDateTime;
        // Checking if ending time and date are of valid format.
        try {
            endDateTime = LocalDateTime.parse(endTimeStr, formatter);
        }
        catch (DateTimeParseException exception) {
            return INVALID_END_TIME;
        }
        // Checking if timeline is inversed and if its length exceeds the
        // maximum amount.
        if (startDateTime.isAfter(endDateTime)) {
            return INVERSED_TIMELINE;
        }
        if (endDateTime.compareTo(startDateTime) > MAX_DAYS) {
            return TIMELINE_TOO_LONG;
        }
        else if (endDateTime.compareTo(startDateTime) == MAX_DAYS
                & endDateTime.toLocalTime().compareTo(startDateTime.toLocalTime()) > 0) {
            return TIMELINE_TOO_LONG;
        }
        return DQ_IS_VALID;
    }
    
    /**
     * An abstract method for checking the validity of the DataQuery.
     * @return A string communicating the result of the check.
     */
    public abstract String checkDataQueryValidity();
}