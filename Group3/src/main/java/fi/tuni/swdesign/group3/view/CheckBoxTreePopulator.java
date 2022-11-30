/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import javafx.scene.control.TreeView;

/**
 * An abstract base class for populating the checkbox treeview in the different
 * datatabs.
 * @author Lauri Puoskari
 */
public abstract class CheckBoxTreePopulator {
    
    /**
     * A constant for a short gap between horizontal components.
     */
    protected static final int SHORT_H_GAP = 10;
    /**
     * A constant string representing road data.
     */
    protected static final String ROAD_DATA = "Road data";
    /**
     * A constant string representing weather data.
     */
    protected static final String WEATHER_DATA = "Weather data";
    /**
     * A constant string representing combined data.
     */
    protected static final String COMBINED_DATA = "Combined data";
    /**
     * A constant string representing maintenance.
     */
    protected static final String MAINTENANCE = "Maintenance";
    /**
     * A constant string representing condition forecast.
     */
    protected static final String COND_FORECAST = "Condition forecast";
    /**
     * A constant string representing precipitation.
     */
    protected static final String PRECIPITATION = "Precipitation";
    /**
     * A constant string representing winter slipperiness.
     */
    protected static final String WINTER_SLIP = "Winter slipperiness";
    /**
     * A constant string representing overall condition.
     */
    protected static final String OVERALL_COND = "Overall condition";
    /**
     * A constant string representing the forecast time in hours.
     */
    protected static final String FORECAST_TIME = "Time (hours)";
    /**
     * A constant string representing the forecast time of two hours.
     */
    protected static final String TWO_HOURS = "2";
    /**
     * A constant string representing the forecast time of four hours.
     */
    protected static final String FOUR_HOURS = "4";
    /**
     * A constant string representing the forecast time of six hours.
     */
    protected static final String SIX_HOURS = "6";
    /**
     * A constant string representing the forecast time of twelve hours.
     */
    protected static final String TWELVE_HOURS = "12";
    /**
     * A constant string representing observed values.
     */
    protected static final String OBSERVED_VALUES = "Observed values";
    /**
     * A constant string representing predicted values.
     */
    protected static final String PREDICTED_VALUES = "Predicted values";
    /**
     * A constant string representing daily values per month.
     */
    protected static final String VALUES_PER_MONTH = "Values per month";
    /**
     * A constant string representing average temperature.
     */
    protected static final String AVG_TEMPERATURE = "Average temperature";
    /**
     * A constant string representing maximum and minimum values of temperature.
     */
    protected static final String MAX_MIN_TEMPERATURE = "Max & min temperatures";
    /**
     * A constant string representing temperature.
     */
    protected static final String TEMPERATURE = "Temperature";
    /**
     * A constant string representing wind speed.
     */
    protected static final String WIND_SPEED = "Wind speed";
    /**
     * A constant string representing cloudiness.
     */
    protected static final String CLOUDINESS = "Cloudiness";
    /**
     * The current instance of the MainView.
     */
    protected MainView mainView;
    
    
    /**
     * Constructor in which the instance of the MainView is stored.
     * @param mainView the current instance of MainView.
     */
    CheckBoxTreePopulator(MainView mainView) {
        this.mainView = mainView;
    }
    
    /**
     * A Factory method for creating the different subclasses of this abstract
     * base class.
     * @param mainView the current instance of the MainView.
     * @param dataType String which determines which subclass will be constructed.
     * @return an instance of the chosen subclass of CheckBoxTreePopulator.
     */
    public static CheckBoxTreePopulator makeCBTPopulator(MainView mainView, String dataType) {
        switch (dataType) {
            case ROAD_DATA -> {
                return new RoadDataCBTPopulator(mainView);
            }
            case WEATHER_DATA -> {
                return new WeatherDataCBTPopulator(mainView);
            }
            case COMBINED_DATA -> {
                return new CombinedDataCBTPopulator(mainView);
            }
            default -> { return null;
            }
        }
    }
    
    /**
     * An abstract method for populating the checkbox tree of a datatab.
     * @param checkBoxTree the TreeView component of the datatab.
     */
    public abstract void populateCheckBoxTree(TreeView checkBoxTree);
}
