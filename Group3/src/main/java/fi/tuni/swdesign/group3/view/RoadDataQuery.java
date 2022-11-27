/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import java.util.ArrayList;

/**
 * A class for storing the RoadData parameters given by the user into an
 * object.
 * @author Lauri Puoskari
 */
public class RoadDataQuery extends DataQuery {
    
    /**
     * ArrayList of the names of the maintenance tasks selected by the user.
     */
    private ArrayList<String> selectedTasks;
    /**
     * ArrayList of the types of condition forecasts selected by the user.
     */
    private ArrayList<String> selectedForecasts;
    /**
     * The amount of hours of the forecast selected by the user as a string.
     */
    private String forecastTime;
    
    /**
     * A constructor in which the data type of the query is stored into the 
     * base class and the data structures are intialized.
     * @param dataType the data type of the DataQuery.
     */
    RoadDataQuery(String dataType) {
        super(dataType);
        this.selectedTasks = new ArrayList<>();
        this.selectedForecasts = new ArrayList<>();
    }
    
    /**
     * A setter-method for the maintenance tasks selected by the user.
     * @param selectedTasks an ArrayList of the names of the maintenance tasks.
     */
    public void setSelectedTasks(ArrayList<String> selectedTasks) {
        this.selectedTasks = selectedTasks;
    }
    
    /**
     * A getter-method for the maintenance tasks selected by the user.
     * @return an ArrayList of the names of the maintenance tasks.
     */
    public ArrayList<String> getSelectedTasks() {
        return this.selectedTasks;
    }
    
    /**
     * A setter-method for the condition forecasts selected by the user.
     * @param selectedForecasts an ArrayList of the types of condition forecasts.
     */
    public void setSelectedForecasts(ArrayList<String> selectedForecasts) {
        this.selectedForecasts = selectedForecasts;
    }
    
    /**
     * A getter-method for the condition forecasts selected by the user.
     * @return an ArrayList of the types of condition forecasts.
     */
    public ArrayList<String> getSelectedForecasts() {
        return this.selectedForecasts;
    }
    
    /**
     * A setter-method for the forecast time selected by the user.
     * @param forecastTime the amount of forecast hours as a string.
     */
    public void setForecastTime(String forecastTime) {
        this.forecastTime = forecastTime;
    }
    
    /**
     * A getter-method for the forecast time selected by the user.
     * @return the amount of forecast hours as a string.
     */
    public String getForecastTime() {
        return this.forecastTime;
    }

    /**
     * A method for printing the contents of the query. Implements the abstract
     * method of the class DataQuery.
     */
    @Override
    public void testPrint() {
        System.out.println(super.location + ": " + super.timelineStart[0] + " "
            + super.timelineStart[1] + " - " + super.timelineEnd[0] + " "
            + super.timelineEnd[1]);
        System.out.println(this.selectedTasks);
        System.out.println(this.selectedForecasts);
        System.out.println(this.forecastTime);
    }
}
