/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import fi.tuni.swdesign.group3.*;
import javafx.scene.control.TabPane;
/**
 * An abstract base class for visualizing data using charts and tables.
 * @author Lauri Puoskari
 */
public abstract class DataVisualizer {
    /**
     * A constant representing the width of a cell of a GridPane.
     */
    protected static final int GRID_CELL_WIDTH = 110;
    /**
     * A constant representing the height of a cell of a GridPane.
     */
    protected static final int GRID_CELL_HEIGHT = 50;
    /**
     * A constant representing the length of a vertical gap between visual
     * elements.
     */
    protected static final int V_GAP = 10;
    /**
     * A constant string used communicating to user that no data was found.
     */
    protected static final String NO_DATA_STR = "No data was found.";
    /**
     * A constant string representing maintenance tasks.
     */
    protected static final String MAINTENANCE_TASKS = "Maintenance tasks";
    /**
     * A constant string representing condition forecasts.
     */
    protected static final String COND_FORECAST = "Condition forecast";
    /**
     * A constant string representing maintenance tasks used as a title.
     */
    protected static final String MAINT_TASKS_TITLE = "Maintenance tasks: ";
    /**
     * A constant string representing a line with spaces around it.
     */
    protected static final String LINE_WITH_SPACES = " - ";
    /**
     * A constant string representing a maintenance task used as a label for
     * an axis of a chart.
     */
    protected static final String MAINT_TASK_AXIS_LABEL = "Maintenance task";
    /**
     * A constant string representing amount used as a label for an axis of a
     * chart.
     */
    protected static final String AMOUNT_AXIS_LABEL = "Amount";
    /**
     * A constant string representing condition forecast used as a title.
     */
    protected static final String COND_FORECAST_TITLE = "Road condition forecast: ";
    /**
     * A constant string representing forecast time in hours used as a title.
     */
    protected static final String FORECAST_TIME_TITLE = "Forecast time (hrs)";
    /**
     * A constant string representing precipitation used as a title.
     */
    protected static final String PRECIPITATION_TITLE = "Precipitation";
    /**
     * A constant string representing winter slipperiness used as a title.
     */
    protected static final String WINTER_SLIP_TITLE = "Winter slipperiness";
    /**
     * A constant string representing overall condition used as a title.
     */
    protected static final String OVERALL_COND_TITLE = "Overall condition";
    /**
     * A constant string representing yes.
     */
    protected static final String YES = "Yes";
    /**
     * A constant string representing no.
     */
    protected static final String NO = "No";
    /**
     * A constant string representing the amount of traffic messages.
     */
    protected static final String TRAFFIC_MSG_AMOUNT = "Amount of traffic messages: ";
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
    protected static final String PER_MONTH_VALUES = "Values per month";
    /**
     * A constant string representing observed values used as a title.
     */
    protected static final String OBS_VALUES_TITLE = "Observed weather values: ";
    /**
     * A constant string representing temperature used as a title.
     */
    protected static final String TEMPERATURE_TITLE = "Temperature (°C)";
    /**
     * A constant string representing wind speed used as a title.
     */
    protected static final String WIND_SPEED_TITLE = "Wind speed (m/s)";
    /**
     * A constant string representing cloudiness used as a title.
     */
    protected static final String CLOUDINESS_TITLE = "Cloudiness";
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
     * A constant string representing predicted weather values used as a title.
     */
    protected static final String PRE_VALUES_TITLE = "Predicted weather values: ";
    /**
     * A constant string representing weather value units used as a label for an
     * axis of a chart.
     */
    protected static final String WEATHER_VALUE_AXIS_LABEL = "°C / m/s";
    /**
     * A constant string representing daily values per month used as a title.
     */
    protected static final String DAILY_VALUE_TITLE = "Daily values per month: ";
    /**
     * A constant string representing a date used as a label for an axos of a 
     * chart.
     */
    protected static final String DATE_AXIS_LABEL = "Date (yyyy-mm-dd)";
    /**
     * A constant string representing average temperatures.
     */
    protected static final String AVG_TEMPERATURE = "Average temperature";
    /**
     * A constant string representing maximum and minimum temperatures.
     */
    protected static final String MAX_MIN_TEMPERATURE = "Max & min temperatures";
    /**
     * The current instance of MainView.
     */
    protected MainView mainView;
    
    /**
     * A constructor in which the current instance of MainView is stored.
     * @param mainView the current instance of MainView.
     */
    DataVisualizer(MainView mainView) {
        this.mainView = mainView;
    }
    
    /**
     * A factory-method for creating a DataVisualizer corresponding the type
     * of the data given as a parameter.
     * @param mainView current instance of MainView.
     * @param data the data to be visualized.
     * @return DataVisualizer corresponding the type of the data.
     */
    public static DataVisualizer makeDataVisualizer(MainView mainView, 
            RoadData data, DataQuery query) {
        if (data instanceof RoadTrafficData roadTrafficData) {
            if (query instanceof CombinedDataQuery combinedDataQuery) {
                return new RoadDataVisualizer(mainView, roadTrafficData, combinedDataQuery.getSubRoadDQ());
            }
            return new RoadDataVisualizer(mainView, roadTrafficData, (RoadDataQuery) query);
        }
        else if (data instanceof RoadWeatherData roadWeatherData) {
            if (query instanceof CombinedDataQuery combinedDataQuery) {
                return new WeatherDataVisualizer(mainView, roadWeatherData, combinedDataQuery.getSubWeatherDQ());
            }
            return new WeatherDataVisualizer(mainView, roadWeatherData, (WeatherDataQuery) query);
        }
        else return null;
    }
    
    /**
     * An abstract method for visualizing the data.
     */
    public abstract void visualizeData(TabPane chartTabPane);
}
